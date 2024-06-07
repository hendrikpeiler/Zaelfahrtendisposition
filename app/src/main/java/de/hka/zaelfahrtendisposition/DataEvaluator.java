package de.hka.zaelfahrtendisposition;

import android.util.Log;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataEvaluator {
    public static Map<String, Integer> evaluateData(List<Map<String, String>> erhebungsstand) {
        // Eine HashMap wird erstellt, um die aggregierten Bewertungen zu speichern
        Map<String, Integer> bewertung = new HashMap<>();

        // Schleife durchläuft jede Zeile der erfassten Daten
        for (Map<String, String> row : erhebungsstand) {
            // Ein eindeutiger Schlüssel wird basierend auf "Linie", "Richtung" und "Tagesgruppe" erstellt
            String key = row.get("Linie") + "-" + row.get("Richtung") + "-" + row.get("Tagesgruppe") + "-" + row.get("Starthaltestelle") + "-" +
            row.get("Abfahrtszeit");

            // Die Werte für "Geplant", "Erhoben", "Güteprüfung ok", "Gezählt" und "Gültig" werden aus der aktuellen Zeile extrahiert
            // und in Ganzzahlen umgewandelt
            double geplant = Integer.parseInt(row.get("Geplant"));
            double erhoben = Integer.parseInt(row.get("Erhoben"));
            double guetepruefungOk = Integer.parseInt(row.get("Güteprüfung ok"));

            double pruefQuote = (guetepruefungOk/geplant);
            int pruefQuoteInt = (int) (pruefQuote*100);

            // Der Wert für den aktuellen Schlüssel in der Bewertungsmap wird um den Wert von "Güteprüfung ok" erhöht.
            // Falls der Schlüssel noch nicht vorhanden ist, wird der Wert auf 0 gesetzt und dann um den Wert von "Güteprüfung ok" erhöht.
            bewertung.put(key, pruefQuoteInt);

            Log.d("DataEvaluator",key + " Prüfquote: " + pruefQuoteInt +"%");

            // Hier könnten geplant und erhoben verwendet werden, wenn benötigt
        }
        // Die aggregierten Bewertungen werden zurückgegeben
        return bewertung;
    }
}
