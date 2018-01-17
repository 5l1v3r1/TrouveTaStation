package edu.isen.fh.carb.models;

public class Station {
    private String adresse = "";
    private Float gazole = Float.parseFloat("0");
    private Float SP95 = Float.parseFloat("0");
    private Float SP98 = Float.parseFloat("0");
    private Float E10 = Float.parseFloat("0");
    private Float E85 = Float.parseFloat("0");
    private Float GPLc = Float.parseFloat("0");

    /**
     * Constructeur Basic
     */
    public Station() {
    }

    /**
     * Constructeur Complet
     * @param adresse
     * @param gazole
     * @param sp95
     * @param sp98
     * @param e10
     * @param e85
     * @param gpLc
     */
    public Station(String adresse, Float gazole, Float sp95, Float sp98, Float e10, Float e85, Float gpLc) {
        this.adresse = adresse;
        this.gazole = gazole;
        this.SP95 = sp95;
        this.SP98 = sp98;
        this.E10 = e10;
        this.E85 = e85;
        this.GPLc = gpLc;
    }

    /**
     *
     * @return
     */
    public String getAdresse() {
        return adresse;
    }

    /**
     *
     * @param adresse
     */
    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    /**
     *
     * @return
     */
    public Float getGazole() {
        return gazole;
    }

    /**
     *
     * @param gazole
     */
    public void setGazole(String gazole) {
        this.gazole = Float.parseFloat(gazole);
    }

    /**
     *
     * @return
     */
    public Float getSP95() {
        return SP95;
    }

    /**
     *
     * @param SP95
     */
    public void setSP95(String SP95) {
        this.SP95 = Float.parseFloat(SP95);
    }

    /**
     *
     * @return
     */
    public Float getSP98() {
        return SP98;
    }

    /**
     *
     * @param SP98
     */
    public void setSP98(String SP98) {
        this.SP98 = Float.parseFloat(SP98);
    }

    /**
     *
     * @return
     */
    public Float getE10() {
        return E10;
    }

    /**
     *
     * @param e10
     */
    public void setE10(String e10) { E10 = Float.parseFloat(e10);}

    /**
     *
     * @return
     */
    public Float getE85() {
        return E85;
    }

    /**
     *
     * @param e85
     */
    public void setE85(String e85) {
        E85 = Float.parseFloat(e85);
    }

    /**
     *
     * @return
     */
    public Float getGPLc() {
        return GPLc;
    }

    /**
     *
     * @param GPLc
     */
    public void setGPLc(String GPLc) {
        this.GPLc = Float.parseFloat(GPLc);
    }
}
