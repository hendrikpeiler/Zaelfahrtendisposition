package de.hka.zaelfahrtendisposition;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataEvaluator {
    public static Map<String, Integer> evaluateData(List<Map<String, String>> erhebungsstand) {
        Map<String, Integer> bewertung = new HashMap<>();
        for (Map<String, String> row : erhebungsstand) {
            String key = row.get("Linie") + "-" + row.get("Richtung") + "-" + row.get("Tagesgruppe");
            int geplant = Integer.parseInt(row.get("Geplant"));
            int erhoben = Integer.parseInt(row.get("Erhoben"));
            int guetepruefungOk = Integer.parseInt(row.get("Güteprüfung ok"));
            bewertung.put(key, bewertung.getOrDefault(key, 0) + guetepruefungOk);
        }
        return bewertung;
    }
}
