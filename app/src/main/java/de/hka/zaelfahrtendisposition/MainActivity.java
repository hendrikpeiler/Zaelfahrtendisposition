package de.hka.zaelfahrtendisposition;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    // Globale boolean-Variablen zum Festlegen, welche Tage berücksichtigt werden sollen
    private boolean schultagEinbeziehen = false;
    private boolean ferientagEinbeziehen = false;
    private boolean samstagEinbeziehen = true;
    private boolean sonnFeiertagEinbeziehen = false;

    // Globale Variable zum Speichern der Linien
    private Set<String> linienSet = new HashSet<>();

    // Globale Variable zum Speichern der Richtungen
    private boolean[] richtungenEinbeziehen = new boolean[2]; // Index 0: Richtung 1, Index 1: Richtung 2

    // Liste zum Speichern der Linien, nach denen gefiltert werden soll
    private List<String> filterLinien = new ArrayList<>();

    // Globale Variablen für den Zeitraum
    private LocalTime startZeit = LocalTime.MIN; // Standardmäßig auf 00:00:00 setzen
    private LocalTime endZeit = LocalTime.MAX; // Standardmäßig auf 24:00:00 setzen

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn_search = this.findViewById(R.id.btn_search);
        Button btn_scedule = this.findViewById(R.id.btn_scedule);
        Button btn_line = this.findViewById(R.id.btn_line);
        Button btn_direction = this.findViewById(R.id.btn_direction);
        Button btn_daygroup = this.findViewById(R.id.btn_daygroup);

        btn_search.setOnClickListener(v -> {
            try {
                // Importieren der CSV-Daten
                List<Fahrt> erhebungsstandListe = DatenImporteurErhebungsstand.importiereCSV(this, R.raw.erhebungsstand);

                // Linien sammeln
                sammleLinien(erhebungsstandListe);

                // Setzen der Richtungen, die einbezogen werden sollen
                richtungenEinbeziehen[0] = true; // Richtung 1 einbeziehen
                richtungenEinbeziehen[1] = false; // Richtung 2 einbeziehen

                // Setzen der Linien, nach denen gefiltert werden soll
                filterLinien.add("811000"); // Filter nach Linie 811000
                filterLinien.add("312080"); // Filter nach Linie 312080
                filterLinien.add("315790"); // Filter nach Linie 315790

                // Filtern der Fahrten basierend auf den boolean-Variablen
                List<Fahrt> gefilterteListe = filterListeNachTagen(erhebungsstandListe);

                // Filtern der Fahrten nach Richtungen
                gefilterteListe = filterListeNachRichtungen(gefilterteListe);

                // Filtern der Fahrten nach Linien
                gefilterteListe = filterListeNachLinien(gefilterteListe);

                // Filtern der Fahrten nach Abfahrtszeitraum
                gefilterteListe = filterListeNachAbfahrtszeit(gefilterteListe);

                // Bewerten der importierten Daten
                List<Fahrt> bewertung = DatenAuswerter.evaluiereDaten(gefilterteListe);

                // Starten der SearchActivity und Übergeben der bewertung-Liste
                Intent activity_search = new Intent(this, SearchActivity.class);
                ArrayList<Fahrt> bewertungList = new ArrayList<>(bewertung);
                activity_search.putParcelableArrayListExtra("bewertung_list", bewertungList);
                this.startActivity(activity_search);

            } catch (IOException e) {
                // Fehlerbehandlung bei IO-Ausnahmen
                e.printStackTrace();
            }
        });

        btn_scedule.setOnClickListener(v -> {
            Intent activity_scedule = new Intent(this, SceduleActivity.class);
            this.startActivity(activity_scedule);
        });

        btn_line.setOnClickListener(v -> {
            Intent activity_line = new Intent(this, LineActivity.class);
            this.startActivity(activity_line);
        });

        btn_direction.setOnClickListener(v -> {
            Intent activity_direction = new Intent(this, DirectionActivity.class);
            this.startActivity(activity_direction);
        });

        btn_daygroup.setOnClickListener(v -> {
            Intent activity_daygroup = new Intent(this, DaygroupActivity.class);
            this.startActivity(activity_daygroup);
        });
    }

    // Methode zum Filtern der Fahrten nach den gewünschten Tagen
    private List<Fahrt> filterListeNachTagen(List<Fahrt> fahrten) {
        List<Fahrt> gefilterteListe = new ArrayList<>();
        for (Fahrt fahrt : fahrten) {
            String tagesgruppe = fahrt.getTagesgruppe().toLowerCase();
            if ((schultagEinbeziehen && tagesgruppe.equals("montag - freitag schule")) ||
                    (ferientagEinbeziehen && tagesgruppe.equals("montag - freitag ferien")) ||
                    (samstagEinbeziehen && tagesgruppe.equals("samstag")) ||
                    (sonnFeiertagEinbeziehen && tagesgruppe.equals("sonn-/feiertag"))) {
                gefilterteListe.add(fahrt);
            }
        }
        return gefilterteListe;
    }

    // Methode zum Sammeln der Linien
    private void sammleLinien(List<Fahrt> fahrten) {
        for (Fahrt fahrt : fahrten) {
            linienSet.add(fahrt.getLinie());
        }

        // Linien ins Logcat schreiben
        for (String linie : linienSet) {
            Log.d("MainActivity", "Linie: " + linie);
        }
    }

    // Methode zum Filtern der Fahrten nach Richtungen
    private List<Fahrt> filterListeNachRichtungen(List<Fahrt> fahrten) {
        List<Fahrt> gefilterteListe = new ArrayList<>();
        for (Fahrt fahrt : fahrten) {
            int richtung = fahrt.getRichtung();
            if (richtung >= 1 && richtung <= 2 && richtungenEinbeziehen[richtung - 1]) {
                gefilterteListe.add(fahrt);
            }
        }
        return gefilterteListe;
    }

    // Methode zum Filtern der Fahrten nach Linien
    private List<Fahrt> filterListeNachLinien(List<Fahrt> fahrten) {
        List<Fahrt> gefilterteListe = new ArrayList<>();
        for (Fahrt fahrt : fahrten) {
            if (filterLinien.contains(fahrt.getLinie())) {
                gefilterteListe.add(fahrt);
            }
        }
        return gefilterteListe;
    }

    // Methode zum Filtern der Fahrten nach Abfahrtszeitraum
    private List<Fahrt> filterListeNachAbfahrtszeit(List<Fahrt> fahrten) {
        List<Fahrt> gefilterteListe = new ArrayList<>();
        for (Fahrt fahrt : fahrten) {
            String abfahrtszeitString = fahrt.getAbfahrtszeit();
            try {
                LocalTime abfahrtszeit = LocalTime.parse(abfahrtszeitString, DateTimeFormatter.ofPattern("HH:mm:ss"));
                if (!abfahrtszeit.isBefore(startZeit) && !abfahrtszeit.isAfter(endZeit)) {
                    gefilterteListe.add(fahrt);
                }
            } catch (DateTimeParseException e) {
                Log.e("MainActivity", "Fehler beim Parsen der Abfahrtszeit: " + e.getMessage());
            }
        }
        return gefilterteListe;
    }
}
