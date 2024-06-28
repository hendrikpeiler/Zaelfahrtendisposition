package de.hka.zaelfahrtendisposition;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DatenImporteurZaehlfahrten {


    public static List<Zaehlfahrt> importiereCSV(Context context, int resourceId) throws IOException {
        List<Zaehlfahrt> data = new ArrayList<>();
        InputStream is = context.getResources().openRawResource(resourceId);
        try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
            String line;
            String[] headers = br.readLine().split(";");  // Annahme: Die CSV-Datei verwendet Semikolon als Trennzeichen

            while ((line = br.readLine()) != null) {
                String[] values = line.split(";");
                // Erstellen Sie ein neues Fahrt-Objekt und fügen Sie es der Liste hinzu
                Zaehlfahrt zaehlfahrt = new Zaehlfahrt(values[0], Integer.parseInt(values[1]), values[2], values[3], values[4], values[5],values[6]);
                data.add(zaehlfahrt);

                // Daten zur Kontrolle ins Logcat schreiben
                Log.d("DatenImporteurZaehlfahrt", "Eingefügte Daten: " + "Linie: " + zaehlfahrt.getLinie() + ", Richtung: " + zaehlfahrt.getRichtung() + ", Tagesgruppe: " + zaehlfahrt.getTagesgruppe() +
                        ", Starthaltestelle: " + zaehlfahrt.getStarthaltestelle() + ", Datum: " + zaehlfahrt.getDatum() + ", Fahrzeug: " + zaehlfahrt.getFahrzeug());
            }

        }
        return data;
    }
}
