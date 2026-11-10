package com.banque;

import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Service web SOAP — BanqueWS
 *
 * Correspond aux diapositives 16-19 de la présentation :
 *   - @WebService  → nom du service dans le WSDL
 *   - @WebMethod   → opération exposée
 *   - @WebParam    → nom lisible du paramètre (sinon arg0, arg1…)
 *
 * Trois opérations :
 *   1. conversion(montant) → montant × 11  (EUR → DT)
 *   2. getCompte(code)     → un compte fictif
 *   3. listComptes()       → liste de comptes
 */
@WebService(serviceName = "BanqueWS")
public class BanqueService {

    /**
     * Convertit un montant d'euros en dinars tunisiens (taux fixe × 11).
     * C'est l'exemple principal de la présentation.
     */
    @WebMethod(operationName = "conversion")
    public double conversion(@WebParam(name = "montant") double montant) {
        return montant * 11;
    }

    /**
     * Retourne un compte à partir de son code.
     * Illustre la sérialisation JAXB : l'objet Compte est
     * automatiquement converti en XML dans la réponse SOAP.
     */
    @WebMethod(operationName = "getCompte")
    public Compte getCompte(@WebParam(name = "code") int code) {
        return new Compte(code, 7500.0 + code * 1000, LocalDate.now().toString());
    }

    /**
     * Retourne une liste de comptes.
     * Montre que JAXB gère aussi les collections.
     */
    @WebMethod(operationName = "listComptes")
    public List<Compte> listComptes() {
        List<Compte> comptes = new ArrayList<>();
        String today = LocalDate.now().toString();
        comptes.add(new Compte(1, 7500.0, today));
        comptes.add(new Compte(2, 12000.0, today));
        comptes.add(new Compte(3, 3200.0, today));
        return comptes;
    }
}
