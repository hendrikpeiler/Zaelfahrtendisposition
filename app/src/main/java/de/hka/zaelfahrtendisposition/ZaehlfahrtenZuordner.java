package de.hka.zaelfahrtendisposition;

import android.util.Log;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ZaehlfahrtenZuordner {
    public static void weiseNeuesteZaehlfahrtZu(List<Fahrt> fahrten, List<Zaehlfahrt> zaehlfahrten) {
        // Methode aufrufen, um jeder Fahrt die neueste passende Zaehlfahrt zuzuweisen
        assignNeuesteZaehlfahrt(fahrten, zaehlfahrten);

        // Überprüfen, ob die Zuweisung korrekt durchgeführt wurde
        for (Fahrt fahrt : fahrten) {
            if(fahrt.getNeuesteZaehlfahrt() != null) {
                Log.d("ZaehlfahrtenZuordner","Fahrt: " + fahrt.getLinie() + ", "+fahrt.getTagesgruppe() +", " + fahrt.getRichtung() + ", "
                        + fahrt.getStarthaltestelle() + ", " + fahrt.getAbfahrtszeit() + " -> Neueste Zaehlfahrt: "
                        + fahrt.getNeuesteZaehlfahrt().getDatum());
            } else {
                Log.e("ZaehlfahrtenZuordner","Keine passende Zaehlfahrt gefunden für Fahrt: " + fahrt.getLinie() +", "+fahrt.getTagesgruppe() + ", "
                        + fahrt.getRichtung() + ", " + fahrt.getStarthaltestelle() + ", " + fahrt.getAbfahrtszeit());
            }
        }
    }
    public static void assignNeuesteZaehlfahrt(List<Fahrt> fahrten, List<Zaehlfahrt> zaehlfahrten) {
        // Map, um die neueste Zaehlfahrt für jede Kombination von Linie, Richtung, Starthaltestelle, Abfahrtszeit zu speichern
        Map<String, Zaehlfahrt> neuesteZaehlfahrtMap = new HashMap<>();

        // Vorab die Abfahrtszeiten der Zaehlfahrten parsen
        for (Zaehlfahrt zaehlfahrt : zaehlfahrten) {
            zaehlfahrt.parseAbfahrtszeit(); // Methode zum Parsen aufrufen
        }

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
