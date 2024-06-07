package de.hka.zaelfahrtendisposition;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

public class MainActivity {
    public static void main(String[] args) {
        try {
            String erhebungsstandPath = "app/src/main/res/Erhebungsstand.csv";
            String zaehlfahrtenPath = "src/main/res/Zaehlfahrten.csv";

            //Schalke stinkt

            // Daten importieren
            List<Map<String, String>> erhebungsstand = DataImporter.importCSV(erhebungsstandPath);
            List<Map<String, String>> zaehlfahrten = DataImporter.importCSV(zaehlfahrtenPath);

            // Daten bewerten
            Map<String, Integer> bewertung = DataEvaluator.evaluateData(erhebungsstand);

            // Verteilungsanalyse
            Map<String, Integer> verteilung = DistributionAnalyzer.analyzeDistribution(zaehlfahrten);

            // Dispositionsvorschläge generieren
            List<Map<String, String>> vorschlaege = DispositionGenerator.generateDispositionSuggestions(bewertung, 5);

            // Vorschläge ausgeben
            vorschlaege.forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
