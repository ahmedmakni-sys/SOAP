package com.banque;

import jakarta.xml.ws.Endpoint;

/**
 * Serveur — publie le service web SOAP.
 *
 * Correspond à la diapositive 19 :
 *   Endpoint.publish("http://0.0.0.0:8585/", new BanqueService());
 *
 * Une fois lancé :
 *   - Le service écoute sur http://localhost:8585/
 *   - Le WSDL est disponible à http://localhost:8585/?wsdl
 *   - On peut tester avec SoapUI, Postman, ou le client Java fourni.
 */
public class Serveur {

    public static void main(String[] args) {
        String url = "http://0.0.0.0:8585/";

        System.out.println("╔══════════════════════════════════════════════╗");
        System.out.println("║       BanqueWS — Service web SOAP           ║");
        System.out.println("╠══════════════════════════════════════════════╣");
        System.out.println("║  Service : http://localhost:8585/            ║");
        System.out.println("║  WSDL    : http://localhost:8585/?wsdl       ║");
        System.out.println("╠══════════════════════════════════════════════╣");
        System.out.println("║  Opérations :                               ║");
        System.out.println("║    • conversion(montant) → montant × 11     ║");
        System.out.println("║    • getCompte(code)     → un compte        ║");
        System.out.println("║    • listComptes()       → tous les comptes ║");
        System.out.println("╚══════════════════════════════════════════════╝");
        System.out.println();

        Endpoint.publish(url, new BanqueService());

        System.out.println("✓ Service publié. Appuyez sur Ctrl+C pour arrêter.");
    }
}
