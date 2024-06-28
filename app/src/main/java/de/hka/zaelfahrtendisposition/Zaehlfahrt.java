package de.hka.zaelfahrtendisposition;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Zaehlfahrt implements Parcelable {

    String Tagesgruppe;
    String Linie;
    int Richtung;
    String Abfahrtszeit;
    String Starthaltestelle;
    String Datum;
    String Fahrzeug;

    private String abfahrtszeitString;
    private LocalTime abfahrtszeit;

    // Verwendetes Datumsformat für den DateTimeFormatter
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    public Zaehlfahrt(String Linie, int Richtung, String Starthaltestelle, String Abfahrtszeit, String Datum, String Tagesgruppe, String Fahrzeug){
        this.Tagesgruppe = Tagesgruppe;
        this.Linie = Linie;
        this.Richtung = Richtung;
        this.Abfahrtszeit = Abfahrtszeit+":00";
        this.Starthaltestelle = Starthaltestelle;
        this.Datum = Datum;
        this.Fahrzeug = Fahrzeug;
    }

    protected Zaehlfahrt(Parcel in) {
        Tagesgruppe = in.readString();
        Linie = in.readString();
        Richtung = in.readInt();
        Abfahrtszeit = in.readString();
        Starthaltestelle = in.readString();
        Datum = in.readString();
        Fahrzeug = in.readString();
    }

    public static final Creator<Zaehlfahrt> CREATOR = new Creator<Zaehlfahrt>() {
        @Override
        public Zaehlfahrt createFromParcel(Parcel in) {
            return new Zaehlfahrt(in);
        }

        @Override
        public Zaehlfahrt[] newArray(int size) {
            return new Zaehlfahrt[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(Tagesgruppe);
        dest.writeString(Linie);
        dest.writeInt(Richtung);
        dest.writeString(Abfahrtszeit);
        dest.writeString(Starthaltestelle);
        dest.writeString(Datum);
        dest.writeString(Fahrzeug);
    }

    public String getFahrzeug() {
        return Fahrzeug;
    }

    public String getDatum() {
        return Datum;
    }

    public int getRichtung() {
        return Richtung;
    }

    public String getAbfahrtszeit() {
        return Abfahrtszeit;
    }

    public String getLinie() {
        return Linie;
    }

    public String getStarthaltestelle() {
        return Starthaltestelle;
    }

    public String getTagesgruppe() {
        if(Tagesgruppe.compareTo("Schule") == 0){
            return "Montag - Freitag Schule";
        } else if (Tagesgruppe.compareTo("Sonntag") == 0 || Tagesgruppe.compareTo("Feiertag") == 0) {
            return "Sonn-/Feiertag";
        }else if (Tagesgruppe.compareTo("Ferien inkl. Br�ckentag") == 0) {
            return "Montag - Freitag Ferien";
        }else if (Tagesgruppe.compareTo("Samstag") == 0) {
            return "Samstag";
        }else {
            Log.d("Zaehlfahrt", "Der Zahlfant wurde folgende Tagesgruppe zugewiesen: " + Tagesgruppe);
            return Tagesgruppe;
        }
    }

    public void setAbfahrtszeit(String abfahrtszeit) {
        Abfahrtszeit = abfahrtszeit;
    }

    public void setDatum(String Datum) {
        this.Datum = Datum;
    }

    public void setFahrzeug(String Fahrzeug) {
        this.Fahrzeug = Fahrzeug;
    }

    public void setLinie(String linie) {
        Linie = linie;
    }

    public void setRichtung(int richtung) {
        Richtung = richtung;
    }

    public void setStarthaltestelle(String starthaltestelle) {
        Starthaltestelle = starthaltestelle;
    }

    public void setTagesgruppe(String tagesgruppe) {
        Tagesgruppe = tagesgruppe;
    }

    public LocalDate getParsedDatum() {
        try {
            return LocalDate.parse(Datum, DATE_FORMATTER);
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    public boolean isNewerThan(Zaehlfahrt other) {
        LocalDate thisDate = getParsedDatum();
        LocalDate otherDate = other.getParsedDatum();

        if (thisDate != null && otherDate != null) {
            if (thisDate.isAfter(otherDate)) {
                return true;
            } else if (thisDate.equals(otherDate)) {
                LocalTime thisTime = LocalTime.parse(this.Abfahrtszeit);
                LocalTime otherTime = LocalTime.parse(other.Abfahrtszeit);
                return thisTime.isAfter(otherTime);
            } else {
                return false;
            }
        }

        return false; // Falls das Parsen fehlschlägt
    }

    public void parseAbfahrtszeit() {
        if (abfahrtszeitString == null || abfahrtszeitString.isEmpty()) {
            // Hier könnten Sie eine Standard-Abfahrtszeit setzen oder eine Fehlerbehandlung durchführen
            System.err.println("Abfahrtszeit ist null oder leer!");
            return;
        }
        try {
            this.abfahrtszeit = LocalTime.parse(abfahrtszeitString, DateTimeFormatter.ofPattern("HH:mm:ss"));
        } catch (DateTimeParseException e) {
            // Fehlerbehandlung, falls das Parsen fehlschlägt
            System.err.println("Fehler beim Parsen der Abfahrtszeit: " + e.getMessage());
        }
    }
}
