package de.hka.zaelfahrtendisposition;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            // Importieren der CSV-Daten
            List<Fahrt> erhebungsstand = DataImporter.importCSV(this, R.raw.erhebungsstand);
            //List<Fahrt> zaehlfahrten = DataImporter.importCSV(this, R.raw.zaehlfahrten);

            // Filtern der Fahrten nach einem bestimmten Wochentag, z.B. "Montag"
            String gewuenschteTagesgruppe = "Samstag";
            int anzahlFahrten = 50;
            List<Fahrt> gefilterteListe = filterList(erhebungsstand, gewuenschteTagesgruppe, anzahlFahrten);

            // Bewerten der importierten Daten
            List<Fahrt> bewertung = DataEvaluator.evaluateData(gefilterteListe);
        } catch (IOException e) {
            // Fehlerbehandlung bei IO-Ausnahmen
            e.printStackTrace();
        }
    }

    // Methode zum Filtern der Fahrten nach Wochentag
    private List<Fahrt> filterList(List<Fahrt> fahrten, String wochentag, int anzahlFahrten) {
        List<Fahrt> gefilterteListe = new ArrayList<>();
        int count = 0;
        for (Fahrt fahrt : fahrten) {
            if (fahrt.getTagesgruppe().equalsIgnoreCase(wochentag)) {
                gefilterteListe.add(fahrt);
                count++;
                if (count >= anzahlFahrten) {
                    break;  // Stoppe die Schleife, wenn die maximale Anzahl erreicht ist
                }
            }
        }
        return gefilterteListe;
    }
}
