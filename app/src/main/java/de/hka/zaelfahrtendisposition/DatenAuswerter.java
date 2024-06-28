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
                int pruefQuoteComparison = Double.compare(fahrt1.getPruefQuote(), fahrt2.getPruefQuote());

                // Wenn die Prüfquote gleich ist, nach dem Datum der neuesten Zählfahrt sortieren
                if (pruefQuoteComparison == 0) {
                    // Vergleiche die neuesten Zählfahrtdaten
                    if (fahrt1.getNeuesteZaehlfahrt() != null && fahrt2.getNeuesteZaehlfahrt() != null) {
                        return fahrt1.getNeuesteZaehlfahrt().getDatum().compareTo(fahrt2.getNeuesteZaehlfahrt().getDatum());
                    } else if (fahrt1.getNeuesteZaehlfahrt() != null) {
                        // fahrt2 hat keine Zählfahrt, also ist fahrt1 "jünger"
                        return 1;
                    } else if (fahrt2.getNeuesteZaehlfahrt() != null) {
                        // fahrt1 hat keine Zählfahrt, also ist fahrt2 "jünger"
                        return -1;
                    } else {
                        // Beide haben keine Zählfahrt
                        return 0; // Keine Änderung in der Reihenfolge
                    }
                }

                return pruefQuoteComparison;
            }
        });

        // Logge die sortierte Liste für Kontrollzwecke
        for (Fahrt fahrt : erhebungsstand) {
            Log.d("DatenAuswerter", "Ausgewertete Daten: Linie: " + fahrt.getLinie() + ", Richtung: " + fahrt.getRichtung() + ", Tagesgruppe: " + fahrt.getTagesgruppe() +
                    ", Starthaltestelle: " + fahrt.getStarthaltestelle() + ", Abfahrtszeit: " + fahrt.getAbfahrtszeit() +
                    ", Prüfquote: " + fahrt.getPruefQuote() + "Neueste Zaehlfahrt"+ fahrt.getNeuesteZaehlfahrt()+ "%");
        }

        // Die sortierte Liste der Fahrten wird zurückgegeben
        return erhebungsstand;
    }
}
