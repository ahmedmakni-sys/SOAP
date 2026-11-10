package com.banque;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;

/**
 * Démonstration JAXB — correspond à la diapositive 18.
 *
 * Montre la sérialisation (marshal) d'un objet Compte en XML
 * et affiche le résultat, exactement comme dans la présentation.
 *
 * Sortie attendue :
 *   <?xml version="1.0" encoding="UTF-8" standalone="yes"?>
 *   <compte>
 *       <code>1</code>
 *       <solde>7500.0</solde>
 *       <date>2026-11-10</date>
 *   </compte>
 */
public class DemoJAXB {

    public static void main(String[] args) throws Exception {

        System.out.println("═══════════════════════════════════════════");
        System.out.println("  Démonstration JAXB : marshal / unmarshal");
        System.out.println("═══════════════════════════════════════════\n");

        // Créer un objet Java
        Compte compte = new Compte(1, 7500.0, java.time.LocalDate.now().toString());
        System.out.println("Objet Java :");
        System.out.println("  " + compte);
        System.out.println();

        // Marshal : objet Java → XML
        System.out.println("Sortie de marshal() :\n");
        JAXBContext context = JAXBContext.newInstance(Compte.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(compte, System.out);

        System.out.println("\n");
        System.out.println("L'objet en mémoire est devenu du XML pour le réseau.");
        System.out.println("JAX-WS fait exactement cela en coulisses pour chaque réponse SOAP.");
    }
}
