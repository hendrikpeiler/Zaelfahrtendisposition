package de.hka.zaelfahrtendisposition;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            // Importieren der CSV-Daten
            List<Fahrt> erhebungsstand = DatenImporteur.importCSV(this, R.raw.erhebungsstand);
            //List<Fahrt> zaehlfahrten = DataImporter.importCSV(this, R.raw.zaehlfahrten);

            // Filtern der Fahrten nach einem bestimmten Wochentag und maximale Anzahl an Fahrten
            List<Fahrt> gefilterteListe;
            boolean filtern = false;
            if (filtern) {
                String gewuenschteTagesgruppe = "Samstag";
                int anzahlFahrten = 38;
                gefilterteListe = filterList(erhebungsstand, gewuenschteTagesgruppe, anzahlFahrten);
            }else{
                gefilterteListe = erhebungsstand;
            }

            // Bewerten der importierten Daten
            List<Fahrt> bewertung = DatenAuswerter.evaluateData(gefilterteListe);
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
                    break;
                }
            }
        }
        return gefilterteListe;
    }
}
