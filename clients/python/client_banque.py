"""
Client SOAP en Python pour BanqueWS (le service Java JAX-WS).

But : prouver l'interopérabilité promise par SOAP.
  - Le serveur est écrit en Java (JAX-WS + JAXB).
  - Ce client est écrit en Python, avec la bibliothèque zeep.
  - Ils ne partagent AUCUN code : seulement le contrat WSDL.

Contrairement à ClientSOAP.java (qui écrit l'enveloppe XML à la main),
ici on n'écrit pas une seule balise : zeep lit le WSDL, découvre les
opérations et leurs paramètres, puis construit les enveloppes tout seul.

Lancement (le serveur Java doit tourner) :
    pip install -r requirements.txt
    python client_banque.py
    python client_banque.py http://autre-machine:8585/?wsdl
"""

import sys

from lxml import etree
from zeep import Client
from zeep.plugins import HistoryPlugin

WSDL_URL = sys.argv[1] if len(sys.argv) > 1 else "http://localhost:8585/?wsdl"


def titre(texte):
    print("\n" + "═" * 55)
    print("  " + texte)
    print("═" * 55)


def afficher_xml(element):
    """Affiche une enveloppe SOAP indentée (comme la diapositive 11)."""
    print(etree.tostring(element, pretty_print=True, encoding="unicode"))


def main():
    # HistoryPlugin garde une copie du dernier message envoyé et reçu,
    # pour montrer le XML que zeep a généré à notre place.
    historique = HistoryPlugin()

    # ─── 1. Lire le contrat ─────────────────────────────────────
    titre("1. Lecture du contrat WSDL")
    print("  Source :", WSDL_URL)
    try:
        client = Client(WSDL_URL, plugins=[historique])
    except Exception as erreur:
        print("\n  ✗ Impossible de lire le WSDL :", erreur)
        print("  → Le serveur Java est-il lancé ? (java -jar target/soap-demo-1.0.jar)")
        sys.exit(1)

    # Ce que le client a appris du WSDL, sans voir le code Java :
    for service in client.wsdl.services.values():
        print("  Service :", service.name)
        for port in service.ports.values():
            print("  Port    :", port.name)
            for nom, operation in port.binding._operations.items():
                print(f"    • {nom}({operation.input.signature()})")

    # ─── 2. conversion(montant) ─────────────────────────────────
    titre("2. Appel : conversion(montant=100)")
    resultat = client.service.conversion(montant=100)
    print("  Résultat Python :", resultat, type(resultat).__name__)
    print("\n  Enveloppe générée par zeep (on ne l'a pas écrite) :\n")
    afficher_xml(historique.last_sent["envelope"])
    print("  Enveloppe renvoyée par le serveur Java :\n")
    afficher_xml(historique.last_received["envelope"])

    # ─── 3. getCompte(code) : un objet JAXB ─────────────────────
    titre("3. Appel : getCompte(code=1)")
    compte = client.service.getCompte(code=1)
    # Le <compte> XML produit par JAXB redevient un objet Python.
    print(f"  code  = {compte.code}")
    print(f"  solde = {compte.solde}")
    print(f"  date  = {compte.date}")

    # ─── 4. listComptes() : une collection ──────────────────────
    titre("4. Appel : listComptes()")
    comptes = client.service.listComptes()
    for c in comptes:
        print(f"  Compte {c.code} : {c.solde:>9.1f} DT  ({c.date})")

    titre("✓ Python a appelé Java uniquement grâce au WSDL")


if __name__ == "__main__":
    main()
