package de.hka.zaelfahrtendisposition;

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
            String key = row.get("Linie") + "-" + row.get("Richtung") + "-" + row.get("Tagesgruppe");

            // Die Werte für "Geplant", "Erhoben", "Güteprüfung ok", "Gezählt" und "Gültig" werden aus der aktuellen Zeile extrahiert
            // und in Ganzzahlen umgewandelt
            int geplant = Integer.parseInt(row.get("Geplant"));
            int erhoben = Integer.parseInt(row.get("Erhoben"));
            int guetepruefungOk = Integer.parseInt(row.get("Güteprüfung ok"));

            // Der Wert für den aktuellen Schlüssel in der Bewertungsmap wird um den Wert von "Güteprüfung ok" erhöht.
            // Falls der Schlüssel noch nicht vorhanden ist, wird der Wert auf 0 gesetzt und dann um den Wert von "Güteprüfung ok" erhöht.
            bewertung.put(key, bewertung.getOrDefault(key, 0) + guetepruefungOk);

            // Hier könnten geplant und erhoben verwendet werden, wenn benötigt
        }
        // Die aggregierten Bewertungen werden zurückgegeben
        return bewertung;
    }
}
