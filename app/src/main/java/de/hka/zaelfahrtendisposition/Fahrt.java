package de.hka.zaelfahrtendisposition;

public class Fahrt {

    String Tagesgruppe;
    String Linie;
    int Richtung;
    String Abfahrtszeit;
    String Starthaltestelle;
    int geplant;
    int erhoben;
    int guetepruefungOk;

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
}
