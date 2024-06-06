package de.hka.zaelfahrtendisposition;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataEvaluator {
    public static Map<String, Integer> evaluateData(List<Map<String, String>> erhebungsstand) {
        Map<String, Integer> bewertung = new HashMap<>();
        for (Map<String, String> row : erhebungsstand) {
            String key = row.get("Linie") + "-" + row.get("Fahrt") + "-" + row.get("Tagesart");
            int gezaehlt = Integer.parseInt(row.get("Gezählt"));
            int gueltig = Integer.parseInt(row.get("Gültig"));
            bewertung.put(key, bewertung.getOrDefault(key, 0) + gueltig);
        }
        return bewertung;
    }
}
