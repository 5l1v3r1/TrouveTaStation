package edu.isen.fh.carb.models;

public class Station {
    private String adresse = "";
    private String gazole = "";
    private String SP95 = "";
    private String SP98 = "";
    private String E10 = "";
    private String E85 = "";
    private String GPLc = "";

    public Station() {
    }

    public Station(String adresse, String gazole, String sp95, String sp98, String e10, String e85, String gpLc) {
        this.adresse = adresse;
        this.gazole = gazole;
        this.SP95 = sp95;
        this.SP98 = sp98;
        this.E10 = e10;
        this.E85 = e85;
        this.GPLc = gpLc;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getGazole() {
        return gazole;
    }

    public void setGazole(String gazole) {
        this.gazole = gazole;
    }

    public String getSP95() {
        return SP95;
    }

    public void setSP95(String SP95) {
        this.SP95 = SP95;
    }

    public String getSP98() {
        return SP98;
    }

    public void setSP98(String SP98) {
        this.SP98 = SP98;
    }

    public String getE10() {
        return E10;
    }

    public void setE10(String e10) {
        E10 = e10;
    }

    public String getE85() {
        return E85;
    }

    public void setE85(String e85) {
        E85 = e85;
    }

    public String getGPLc() {
        return GPLc;
    }

    public void setGPLc(String GPLc) {
        this.GPLc = GPLc;
    }
}
