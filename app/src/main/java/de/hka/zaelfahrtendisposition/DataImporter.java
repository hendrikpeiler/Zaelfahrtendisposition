package de.hka.zaelfahrtendisposition;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataImporter {

    public static List<Map<String, String>> importCSV(String filePath) throws IOException {
        List<Map<String, String>> data = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            String[] headers = br.readLine().split(";");  // Annahme: Die CSV-Datei verwendet Semikolon als Trennzeichen

            while ((line = br.readLine()) != null) {
                String[] values = line.split(";");
                Map<String, String> row = new HashMap<>();
                for (int i = 0; i < headers.length; i++) {
                    row.put(headers[i], values[i]);
                }
                data.add(row);
            }
        }
        return data;
    }
}
