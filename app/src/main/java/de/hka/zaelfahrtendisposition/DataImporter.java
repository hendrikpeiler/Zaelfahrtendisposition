package de.hka.zaelfahrtendisposition;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataImporter {

    public static List<Fahrt> importCSV(Context context, int resourceId) throws IOException {
        List<Fahrt> data = new ArrayList<>();
        InputStream is = context.getResources().openRawResource(resourceId);
        try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
            String line;
            String[] headers = br.readLine().split(";");  // Annahme: Die CSV-Datei verwendet Semikolon als Trennzeichen

            while ((line = br.readLine()) != null) {
                String[] values = line.split(";");
                // Erstellen Sie ein neues Fahrt-Objekt und fügen Sie es der Liste hinzu
                Fahrt fahrt = new Fahrt(values[0], values[1], Integer.parseInt(values[2]), values[3], values[4], Integer.parseInt(values[5]), Integer.parseInt(values[6]), Integer.parseInt(values[7]));
                data.add(fahrt);

                // Daten zur Kontrolle ins Logcat schreiben
                Log.d("DataImporter", "Eingefügte Daten: " + "Linie: " + fahrt.getLinie() + ", Richtung: " + fahrt.getRichtung() + ", Tagesgruppe: " + fahrt.getTagesgruppe() +
                        ", Starthaltestelle: " + fahrt.getStarthaltestelle() + ", Abfahrtszeit: " + fahrt.getAbfahrtszeit());
            }

        }
        return data;
    }
}
