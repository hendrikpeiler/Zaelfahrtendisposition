package de.hka.zaelfahrtendisposition;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DispositionGenerator {
    public static List<Map<String, String>> generateDispositionSuggestions(Map<String, Integer> bewertung, int threshold) {
        return bewertung.entrySet().stream()
                .filter(entry -> entry.getValue() >= threshold)
                .map(entry -> {
                    String[] parts = entry.getKey().split("-");
                    return Map.of(
                            "Linie", parts[0],
                            "Fahrt", parts[1],
                            "Tagesart", parts[2],
                            "Gültige Zählungen", entry.getValue().toString()
                    );
                })
                .collect(Collectors.toList());
    }
}