package de.hka.zaelfahrtendisposition;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.annotation.Nullable;
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
    private boolean richtung1Einbeziehen = true;
    private boolean richtung2Einbeziehen = true;

    // Liste zum Speichern der Linien, nach denen gefiltert werden soll
    private List<String> filterLinien = new ArrayList<>();

    // Globale Variablen für den Zeitraum
    private LocalTime startZeit = LocalTime.MIN; // Standardmäßig auf 00:00:00 setzen
    private LocalTime endZeit = LocalTime.MAX.minusSeconds(1); // Standardmäßig auf 23:59:59 setzen

    private static final int REQUEST_CODE_SCHEDULE = 1; // Request-Code für SceduleActivity
    private static final int REQUEST_CODE_DIRECTION = 2; // Request-Code für DirectionActivity
    private static final int REQUEST_CODE_DAYGROUP = 3; // Request-Code für DaygroupActivity

    private boolean hasReceivedSchedule = false; // Flag, ob Start- und Endzeit empfangen wurde

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btn_search = findViewById(R.id.btn_search);
        Button btn_scedule = findViewById(R.id.btn_scedule);
        Button btn_line = findViewById(R.id.btn_line);
        Button btn_direction = findViewById(R.id.btn_direction);
        Button btn_daygroup = findViewById(R.id.btn_daygroup);

        btn_search.setOnClickListener(v -> {
            try {
                // Importieren der CSV-Daten
                List<Fahrt> erhebungsstandListe = DatenImporteurErhebungsstand.importiereCSV(this, R.raw.erhebungsstand);

                // Linien sammeln
                sammleLinien(erhebungsstandListe);

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
                startActivity(activity_search);

            } catch (IOException e) {
                // Fehlerbehandlung bei IO-Ausnahmen
                e.printStackTrace();
            }
        });

        btn_scedule.setOnClickListener(v -> {
            Intent activity_scedule = new Intent(this, SceduleActivity.class);

            // Übergeben der Start- und Endzeit als Extras, falls bereits empfangen
            if (hasReceivedSchedule) {
                activity_scedule.putExtra("start_time", startZeit.toString());
                Log.d("MainActivity", "Startzeit übergeben: " + startZeit.toString());
                activity_scedule.putExtra("end_time", endZeit.toString());
            }

            startActivityForResult(activity_scedule, REQUEST_CODE_SCHEDULE);
        });

        btn_line.setOnClickListener(v -> {
            // Intent für die LineActivity erstellen
            Intent activity_line = new Intent(this, LineActivity.class);

            // Linienliste an die LineActivity übergeben
            ArrayList<String> linienListe = new ArrayList<>(linienSet); // Beispiel: linienSet aus deinem Code
            activity_line.putStringArrayListExtra("linienListe", linienListe);

            // LineActivity starten
            startActivity(activity_line);
        });

        btn_direction.setOnClickListener(v -> {
            Intent activity_direction = new Intent(this, DirectionActivity.class);
            activity_direction.putExtra("richtung1", richtung1Einbeziehen);
            activity_direction.putExtra("richtung2", richtung2Einbeziehen);
            startActivityForResult(activity_direction, REQUEST_CODE_DIRECTION);
        });

        btn_daygroup.setOnClickListener(v -> {
            Intent activity_daygroup = new Intent(this, DaygroupActivity.class);
            activity_daygroup.putExtra("schultag", schultagEinbeziehen);
            activity_daygroup.putExtra("ferientag", ferientagEinbeziehen);
            activity_daygroup.putExtra("samstag", samstagEinbeziehen);
            activity_daygroup.putExtra("sonnFeiertag", sonnFeiertagEinbeziehen);
            startActivityForResult(activity_daygroup, REQUEST_CODE_DAYGROUP);
        });
    }

    // Diese Methode wird aufgerufen, wenn die SceduleActivity, DirectionActivity oder DaygroupActivity geschlossen wird
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_SCHEDULE && resultCode == RESULT_OK && data != null) {
            // Start- und Endzeit aus den Intent-Extras auslesen
            String startTimeStr = data.getStringExtra("start_time");
            String endTimeStr = data.getStringExtra("end_time");

            try {
                // Konvertierung der Strings in LocalTime-Objekte
                startZeit = LocalTime.parse(startTimeStr, DateTimeFormatter.ISO_LOCAL_TIME);
                endZeit = LocalTime.parse(endTimeStr, DateTimeFormatter.ISO_LOCAL_TIME);
                hasReceivedSchedule = true; // Flag setzen, dass Zeit empfangen wurde

                Log.d("MainActivity", "Startzeit empfangen: " + startZeit.toString());
                Log.d("MainActivity", "Endzeit empfangen: " + endZeit.toString());

            } catch (DateTimeParseException e) {
                Log.e("MainActivity", "Fehler beim Parsen der Zeit: " + e.getMessage());
            }
        } else if (requestCode == REQUEST_CODE_DIRECTION && resultCode == RESULT_OK && data != null) {
            // Richtungen aus den Intent-Extras auslesen
            richtung1Einbeziehen = data.getBooleanExtra("richtung1", true);
            richtung2Einbeziehen = data.getBooleanExtra("richtung2", true);

            Log.d("MainActivity", "Richtung 1 einbeziehen: " + richtung1Einbeziehen);
            Log.d("MainActivity", "Richtung 2 einbeziehen: " + richtung2Einbeziehen);
        } else if (requestCode == REQUEST_CODE_DAYGROUP && resultCode == RESULT_OK && data != null) {
            // Tagesgruppen aus den Intent-Extras auslesen
            schultagEinbeziehen = data.getBooleanExtra("schultag", false);
            ferientagEinbeziehen = data.getBooleanExtra("ferientag", false);
            samstagEinbeziehen = data.getBooleanExtra("samstag", false);
            sonnFeiertagEinbeziehen = data.getBooleanExtra("sonnFeiertag", false);

            Log.d("MainActivity", "Schultag einbeziehen: " + schultagEinbeziehen);
            Log.d("MainActivity", "Ferientag einbeziehen: " + ferientagEinbeziehen);
            Log.d("MainActivity", "Samstag einbeziehen: " + samstagEinbeziehen);
            Log.d("MainActivity", "Sonn-/Feiertag einbeziehen: " + sonnFeiertagEinbeziehen);
        }
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
    private List<String> sammleLinien(List<Fahrt> fahrten) {
        List<String> linienListe = new ArrayList<>();

        for (Fahrt fahrt : fahrten) {
            linienListe.add(fahrt.getLinie());
        }

        // Linien ins Logcat schreiben
        for (String linie : linienListe) {
            Log.d("MainActivity", "Linie: " + linie);
        }

        return linienListe;
    }

    // Methode zum Filtern der Fahrten nach Richtungen
    private List<Fahrt> filterListeNachRichtungen(List<Fahrt> fahrten) {
        List<Fahrt> gefilterteListe = new ArrayList<>();
        for (Fahrt fahrt : fahrten) {
            int richtung = fahrt.getRichtung();
            if (richtung >= 1 && richtung <= 2 && ((richtung == 1 && richtung1Einbeziehen) || (richtung == 2 && richtung2Einbeziehen))) {
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
