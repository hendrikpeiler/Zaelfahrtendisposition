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
            List<Fahrt> erhebungsstand = DatenImporteur.importiereCSV(this, R.raw.erhebungsstand);
            //List<Fahrt> zaehlfahrten = DataImporter.importCSV(this, R.raw.zaehlfahrten);

            // Filtern der Fahrten nach einem bestimmten Wochentag und maximale Anzahl an Fahrten
            List<Fahrt> gefilterteListe;
            boolean filtern = true;
            if (filtern) {
                String gewuenschteTagesgruppe = "Samstag";
                gefilterteListe = filterList(erhebungsstand, gewuenschteTagesgruppe);
            }else{
                gefilterteListe = erhebungsstand;
            }

            // Bewerten der importierten Daten
            List<Fahrt> bewertung = DatenAuswerter.evaluiereDaten(gefilterteListe);
        } catch (IOException e) {
            // Fehlerbehandlung bei IO-Ausnahmen
            e.printStackTrace();
        }
    }

    // Methode zum Filtern der Fahrten nach Wochentag
    private List<Fahrt> filterList(List<Fahrt> fahrten, String wochentag) {
        List<Fahrt> gefilterteListe = new ArrayList<>();
        for (Fahrt fahrt : fahrten) {
            if (fahrt.getTagesgruppe().equalsIgnoreCase(wochentag)) {
                gefilterteListe.add(fahrt);
            }
        }
        return gefilterteListe;
    }
}
