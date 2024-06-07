package de.hka.zaelfahrtendisposition;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            // Importieren der CSV-Daten
            List<Map<String, String>> erhebungsstand = DataImporter.importCSV(this, R.raw.Erhebungsstand);
            List<Map<String, String>> zaehlfahrten = DataImporter.importCSV(this, R.raw.Zaehlfahrten);

            // Bewerten der importierten Daten
            Map<String, Integer> bewertung = DataEvaluator.evaluateData(erhebungsstand);

            // Analyse der Verteilung der importierten Daten
            Map<String, Integer> verteilung = DistributionAnalyzer.analyzeDistribution(zaehlfahrten);

            // Generieren von Dispositionsvorschlägen basierend auf der Bewertung
            List<Map<String, String>> vorschlaege = DispositionGenerator.generateDispositionSuggestions(bewertung, 5);

            // Ausgabe der generierten Vorschläge
            vorschlaege.forEach(System.out::println);
        } catch (IOException e) {
            // Fehlerbehandlung bei IO-Ausnahmen
            e.printStackTrace();
        }
    }
}
