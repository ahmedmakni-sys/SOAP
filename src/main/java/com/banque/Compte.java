package com.banque;

import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;
import java.util.Date;

/**
 * Classe Compte — correspond à la diapositive JAXB de la présentation.
 *
 * JAXB convertit cet objet Java en XML (marshal)
 * et reconstruit un objet à partir du XML reçu (unmarshal).
 */
@XmlRootElement
@XmlType(propOrder = {"code", "solde", "date"})
public class Compte {

    private int code;
    private double solde;
    private String date;

    /** Constructeur sans argument (requis par JAXB). */
    public Compte() {}

    public Compte(int code, double solde, String date) {
        this.code = code;
        this.solde = solde;
        this.date = date;
    }

    // ---- Getters / Setters ----

    public int getCode() { return code; }
    public void setCode(int code) { this.code = code; }

    public double getSolde() { return solde; }
    public void setSolde(double solde) { this.solde = solde; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    @Override
    public String toString() {
        return "Compte{code=" + code + ", solde=" + solde + ", date='" + date + "'}";
    }
}
