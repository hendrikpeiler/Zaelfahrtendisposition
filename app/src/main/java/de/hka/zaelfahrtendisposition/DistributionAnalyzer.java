package de.hka.zaelfahrtendisposition;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DistributionAnalyzer {
    public static Map<String, Integer> analyzeDistribution(List<Map<String, String>> zaehlfahrten) {
        Map<String, Integer> verteilung = new HashMap<>();
        for (Map<String, String> row : zaehlfahrten) {
            String datum = row.get("Datum");
            verteilung.put(datum, verteilung.getOrDefault(datum, 0) + 1);
        }
        return verteilung;
    }
}
