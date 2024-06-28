package de.hka.zaelfahrtendisposition;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ZaehlfahrtenZuordner {
    public static void weiseNeuesteZaehlfahrtZu(List<Fahrt> fahrten, List<Zaehlfahrt> zaehlfahrten) {
        // Methode aufrufen, um jeder Fahrt die neueste passende Zaehlfahrt zuzuweisen
        assignNeuesteZaehlfahrt(fahrten, zaehlfahrten);

        // Überprüfen, ob die Zuweisung korrekt durchgeführt wurde
        for (Fahrt fahrt : fahrten) {
            Zaehlfahrt neuesteZaehlfahrt = fahrt.getNeuesteZaehlfahrt();
            if (neuesteZaehlfahrt != null) {
                System.out.println("Fahrt: " + fahrt.getLinie() + ", " + fahrt.getRichtung() + ", "
                        + fahrt.getStarthaltestelle() + ", " + fahrt.getAbfahrtszeit() + " -> Neueste Zaehlfahrt: "
                        + neuesteZaehlfahrt.getDatum());
            } else {
                System.out.println("Keine passende Zaehlfahrt gefunden für Fahrt: " + fahrt.getLinie() + ", "
                        + fahrt.getRichtung() + ", " + fahrt.getStarthaltestelle() + ", " + fahrt.getAbfahrtszeit());
            }
        }
    }
    public static void assignNeuesteZaehlfahrt(List<Fahrt> fahrten, List<Zaehlfahrt> zaehlfahrten) {
        // Map, um die neueste Zaehlfahrt für jede Kombination von Linie, Richtung, Starthaltestelle, Abfahrtszeit zu speichern
        Map<String, Zaehlfahrt> neuesteZaehlfahrtMap = new HashMap<>();

        // Durchlaufen Sie die Liste der Zaehlfahrten und speichern Sie die neueste für jede Kombination
        for (Zaehlfahrt zaehlfahrt : zaehlfahrten) {
            String key = zaehlfahrt.getLinie() + "_" + zaehlfahrt.getRichtung() + "_" + zaehlfahrt.getStarthaltestelle() + "_" + zaehlfahrt.getAbfahrtszeit();
            Zaehlfahrt currentNeueste = neuesteZaehlfahrtMap.get(key);
            if (currentNeueste == null || zaehlfahrt.isNewerThan(currentNeueste)) {
                neuesteZaehlfahrtMap.put(key, zaehlfahrt);
            }
        }

        // Iterieren Sie über die Liste der Fahrten und weisen Sie die passende neueste Zaehlfahrt zu
        for (Fahrt fahrt : fahrten) {
            String key = fahrt.getLinie() + "_" + fahrt.getRichtung() + "_" + fahrt.getStarthaltestelle() + "_" + fahrt.getAbfahrtszeit();
            Zaehlfahrt neuesteZaehlfahrt = neuesteZaehlfahrtMap.get(key);
            if (neuesteZaehlfahrt != null) {
                fahrt.setNeuesteZaehlfahrt(neuesteZaehlfahrt);
            }
        }
    }
}
