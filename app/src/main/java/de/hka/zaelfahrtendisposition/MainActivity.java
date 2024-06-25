package de.hka.zaelfahrtendisposition;

import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    // Globale boolean-Variablen zum Festlegen, welche Tage berücksichtigt werden sollen
    private boolean schultagEinbeziehen = true;
    private boolean ferientagEinbeziehen = true;
    private boolean samstagEinbeziehen = true;
    private boolean sonnFeiertagEinbeziehen = true;

    // Globale Variable zum Speichern der Linien
    private Set<String> linienSet = new HashSet<>();
    private Set<String> ausgewaehlteLinien = new HashSet<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            // Importieren der CSV-Daten
            List<Fahrt> erhebungsstandListe = DatenImporteur.importiereCSV(this, R.raw.erhebungsstand);

            // Linien sammeln und Auswahl initialisieren
            sammleLinien(erhebungsstandListe);

            // Beispiel: Linien auswählen
            waehleLinieAus("811000");
            waehleLinieAus("312180");

            // Filtern der Fahrten basierend auf den boolean-Variablen und der Linienauswahl
            List<Fahrt> gefilterteListe = filterListeNachTagenUndLinien(erhebungsstandListe);

            // Bewerten der importierten Daten
            List<Fahrt> bewertung = DatenAuswerter.evaluiereDaten(gefilterteListe);

        } catch (IOException e) {
            // Fehlerbehandlung bei IO-Ausnahmen
            e.printStackTrace();
        }
    }

    // Methode zum Filtern der Fahrten nach den gewünschten Tagen und ausgewählten Linien
    private List<Fahrt> filterListeNachTagenUndLinien(List<Fahrt> fahrten) {
        List<Fahrt> gefilterteListe = new ArrayList<>();
        for (Fahrt fahrt : fahrten) {
            String tagesgruppe = fahrt.getTagesgruppe().toLowerCase();
            String linie = fahrt.getLinie();

            // Überprüfen, ob die Fahrt zu einem gewünschten Tag gehört und ob die Linie ausgewählt ist
            if (istTagGewuenscht(tagesgruppe) && istLinieAusgewaehlt(linie)) {
                gefilterteListe.add(fahrt);
            }
        }
        return gefilterteListe;
    }

    // Methode zum Überprüfen, ob ein Tag berücksichtigt werden soll
    private boolean istTagGewuenscht(String tagesgruppe) {
        return (schultagEinbeziehen && tagesgruppe.equals("montag - freitag schule")) ||
                (ferientagEinbeziehen && tagesgruppe.equals("montag - freitag ferien")) ||
                (samstagEinbeziehen && tagesgruppe.equals("samstag")) ||
                (sonnFeiertagEinbeziehen && tagesgruppe.equals("sonn-/feiertag"));
    }

    // Methode zum Sammeln der Linien und Initialisieren der Auswahl
    private void sammleLinien(List<Fahrt> fahrten) {
        for (Fahrt fahrt : fahrten) {
            String linie = fahrt.getLinie();
            linienSet.add(linie);
        }
    }

    // Methode zum Auswählen einer Linie
    private void waehleLinieAus(String linie) {
        ausgewaehlteLinien.add(linie);
    }

    // Methode zum Überprüfen, ob eine Linie ausgewählt ist
    private boolean istLinieAusgewaehlt(String linie) {
        return ausgewaehlteLinien.contains(linie);
    }
}
