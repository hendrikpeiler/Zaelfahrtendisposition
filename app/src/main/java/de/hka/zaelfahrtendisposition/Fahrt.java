package de.hka.zaelfahrtendisposition;

import android.os.Parcel;
import android.os.Parcelable;

public class Fahrt implements Parcelable {

    String Tagesgruppe;
    String Linie;
    int Richtung;
    String Abfahrtszeit;
    String Starthaltestelle;
    int geplant;
    int erhoben;
    int guetepruefungOk;

    Zaehlfahrt neuesteZaehlfahrt;

    public Fahrt(String Tagesgruppe, String Linie, int Richtung, String Abfahrtszeit, String Starthaltestelle, int geplant, int erhoben, int guetepruefungOk){
        this.Tagesgruppe = Tagesgruppe;
        this.Linie = Linie;
        this.Richtung = Richtung;
        this.Abfahrtszeit = Abfahrtszeit;
        this.Starthaltestelle = Starthaltestelle;
        this.geplant = geplant;
        this.erhoben = erhoben;
        this.guetepruefungOk = guetepruefungOk;
    }

    protected Fahrt(Parcel in) {
        Tagesgruppe = in.readString();
        Linie = in.readString();
        Richtung = in.readInt();
        Abfahrtszeit = in.readString();
        Starthaltestelle = in.readString();
        geplant = in.readInt();
        erhoben = in.readInt();
        guetepruefungOk = in.readInt();
        neuesteZaehlfahrt = in.readParcelable(Zaehlfahrt.class.getClassLoader());
    }

    public static final Creator<Fahrt> CREATOR = new Creator<Fahrt>() {
        @Override
        public Fahrt createFromParcel(Parcel in) {
            return new Fahrt(in);
        }

        @Override
        public Fahrt[] newArray(int size) {
            return new Fahrt[size];
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
        dest.writeInt(geplant);
        dest.writeInt(erhoben);
        dest.writeInt(guetepruefungOk);
        dest.writeParcelable(neuesteZaehlfahrt, flags);
    }

    public int getErhoben() {
        return erhoben;
    }

    public int getGeplant() {
        return geplant;
    }

    public int getGuetepruefungOk() {
        return guetepruefungOk;
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
        return Tagesgruppe;
    }

    public void setAbfahrtszeit(String abfahrtszeit) {
        Abfahrtszeit = abfahrtszeit;
    }

    public void setErhoben(int erhoben) {
        this.erhoben = erhoben;
    }

    public void setGeplant(int geplant) {
        this.geplant = geplant;
    }

    public void setGuetepruefungOk(int guetepruefungOk) {
        this.guetepruefungOk = guetepruefungOk;
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

    public int getPruefQuote(){
        return (int) (((double) guetepruefungOk) / ((double) geplant) * 100);
    }

    public Zaehlfahrt getNeuesteZaehlfahrt(){
        return neuesteZaehlfahrt;
    }

    public void setNeuesteZaehlfahrt(Zaehlfahrt neuereZaehlfahrt){
        neuesteZaehlfahrt = neuereZaehlfahrt;
    }
}
