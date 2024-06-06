package de.hka.zaelfahrtendisposition;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DataImporter {
    public static List<Map<String, String>> importCSV(String filePath) throws IOException {
        try (CSVParser parser = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(Files.newBufferedReader(Path.of(filePath)))) {
            List<CSVRecord> records = parser.getRecords();
            return records.stream()
                    .map(CSVRecord::toMap)
                    .collect(Collectors.toList());
        }
    }
}