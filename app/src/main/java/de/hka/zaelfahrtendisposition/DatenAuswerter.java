package de.hka.zaelfahrtendisposition;

import android.util.Log;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class DatenAuswerter {
    public static List<Fahrt> evaluiereDaten(List<Fahrt> erhebungsstand) {
        // Sortiere die Liste der Fahrten basierend auf der Prüfquote (niedrigste zuerst)
        Collections.sort(erhebungsstand, new Comparator<Fahrt>() {
            @Override
            public int compare(Fahrt fahrt1, Fahrt fahrt2) {
                // Vergleiche die Prüfquote der beiden Fahrten (umgekehrt)
                return Double.compare(fahrt1.getPruefQuote(), fahrt2.getPruefQuote());
            }
        });

        // Logge die sortierte Liste für Kontrollzwecke
        for (Fahrt fahrt : erhebungsstand) {
            Log.d("DatenAuswerter", "Ausgewertete Daten: Linie: " + fahrt.getLinie() + ", Richtung: " + fahrt.getRichtung() + ", Tagesgruppe: " + fahrt.getTagesgruppe() +
                    ", Starthaltestelle: " + fahrt.getStarthaltestelle() + ", Abfahrtszeit: " + fahrt.getAbfahrtszeit() +
                    ", Prüfquote: " + fahrt.getPruefQuote() + "%");
        }

        // Die sortierte Liste der Fahrten wird zurückgegeben
        return erhebungsstand;
    }
}
