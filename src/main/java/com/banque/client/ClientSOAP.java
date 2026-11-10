package com.banque.client;

import jakarta.xml.ws.Service;
import javax.xml.namespace.QName;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * Client SOAP — appelle BanqueWS.
 *
 * Deux approches sont montrées :
 *   1. Appel « brut » : on construit l'enveloppe SOAP en XML à la main
 *      et on l'envoie via HTTP POST (correspond à la diapositive 11).
 *   2. Appel via proxy JAX-WS (commenté, nécessite le WSDL compilé).
 *
 * Le serveur doit être lancé AVANT ce client.
 */
public class ClientSOAP {

    private static final String SERVICE_URL = "http://localhost:8585/";

    public static void main(String[] args) throws Exception {

        System.out.println("═══════════════════════════════════════════");
        System.out.println("  Client SOAP — appel de BanqueWS");
        System.out.println("═══════════════════════════════════════════\n");

        // ─── 1. Appel conversion(100) ───────────────────────
        System.out.println("► Appel : conversion(100)");
        System.out.println("  Requête SOAP envoyée :\n");

        String soapConversion = """
                <soapenv:Envelope
                    xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
                    xmlns:ban="http://banque.com/">
                  <soapenv:Header/>
                  <soapenv:Body>
                    <ban:conversion>
                      <montant>100</montant>
                    </ban:conversion>
                  </soapenv:Body>
                </soapenv:Envelope>
                """;

        System.out.println(indent(soapConversion));
        String response1 = sendSoap(soapConversion);
        System.out.println("  Réponse SOAP reçue :\n");
        System.out.println(indent(response1));
        System.out.println();

        // ─── 2. Appel getCompte(1) ──────────────────────────
        System.out.println("► Appel : getCompte(1)");

        String soapGetCompte = """
                <soapenv:Envelope
                    xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
                    xmlns:ban="http://banque.com/">
                  <soapenv:Header/>
                  <soapenv:Body>
                    <ban:getCompte>
                      <code>1</code>
                    </ban:getCompte>
                  </soapenv:Body>
                </soapenv:Envelope>
                """;

        String response2 = sendSoap(soapGetCompte);
        System.out.println("  Réponse SOAP reçue :\n");
        System.out.println(indent(response2));
        System.out.println();

        // ─── 3. Appel listComptes() ─────────────────────────
        System.out.println("► Appel : listComptes()");

        String soapList = """
                <soapenv:Envelope
                    xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
                    xmlns:ban="http://banque.com/">
                  <soapenv:Header/>
                  <soapenv:Body>
                    <ban:listComptes/>
                  </soapenv:Body>
                </soapenv:Envelope>
                """;

        String response3 = sendSoap(soapList);
        System.out.println("  Réponse SOAP reçue :\n");
        System.out.println(indent(response3));

        System.out.println("\n═══════════════════════════════════════════");
        System.out.println("  ✓ Tous les appels ont réussi !");
        System.out.println("═══════════════════════════════════════════");
    }

    /**
     * Envoie une enveloppe SOAP par HTTP POST — exactement comme
     * la capture montrée à la diapositive 11.
     */
    private static String sendSoap(String soapXml) throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(SERVICE_URL))
                .header("Content-Type", "text/xml; charset=utf-8")
                .header("SOAPAction", "\"\"")
                .POST(HttpRequest.BodyPublishers.ofString(soapXml))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

    /** Indente chaque ligne pour l'affichage. */
    private static String indent(String text) {
        return text.lines()
                .map(line -> "    " + line)
                .reduce("", (a, b) -> a + "\n" + b)
                .trim();
    }
}
