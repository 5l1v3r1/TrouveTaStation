package edu.isen.fhgd.fft.carburants;

import edu.isen.fhgd.fft.zip.MyZip;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import java.util.ArrayList;
import java.util.List;
import java.net.URL;
import java.net.HttpURLConnection;
import java.io.InputStream;

import java.util.Observable;

public class Carburants extends Observable {
    /**
     * Variable permettant de savoir si le fichier à déjà était téléchargé
     */
    private boolean existe = false;
    /**
     * Logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(Carburants.class);

    /**
     * Liste des stations services
     */
    private List<List<String>> listOfLists = new ArrayList<List<String>>();

    /**
     * Flux de données
     */
    InputStream stream = null;

    /**
     * Constructeur Basic
     */
    public Carburants() {
    }

    /**
     * Va chercher l'Open-Stream via une requête http
     */
    public void getHttpStream() {
        try {
            String uri = "https://donnees.roulez-eco.fr/opendata/instantane";
            URL url = new URL(uri);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Accept", "application/xml");

            this.stream = connection.getInputStream();

            LOGGER.debug("Récupération du flux de données");
        } catch (Exception e) {
            LOGGER.error("Problème de récupération du flux de données : " + e);
        }
    }

    /**
     * Décompresse une archive zip
     *
     * @param chemin
     */
    public void unzipTo(String chemin) {
        MyZip zipFile = new MyZip();
        zipFile.unzip(this.stream, chemin);
    }

    /**
     * Parse le fichier xml de carburants
     *
     * @param chemin
     */
    public void parseFile(String chemin) {
        try {
            XMLReader xr = XMLReaderFactory.createXMLReader();
            CarburantsParser SaxRead = new CarburantsParser();

            xr.setContentHandler(SaxRead);
            xr.parse(chemin);

            this.listOfLists = SaxRead.getListOfLists();

            LOGGER.debug("Parsing Ok");

            this.notifyObservers();

        } catch (Exception e) {
            LOGGER.error("Problème lors du parsing : " + e);
        }
    }

    /**
     * Donne en fonction de la ville les stations-services et leurs carburants correspondant
     *
     * @param ville
     * @return
     */
    public List<List<String>> stationsOf(String ville) {
        List<List<String>> listStations = new ArrayList<List<String>>();
        int j, count = 0;
        String[] Array;
        for (int i = 0; i < listOfLists.size(); ++i) {

            if (listOfLists.get(i).get(1).equals("ville:" + ville) || listOfLists.get(i).get(1).equals("ville:" + ville.toUpperCase())) {
                j = 0;
                listStations.add(new ArrayList<String>());

                for (String x : listOfLists.get(i)) {

                    Array = listOfLists.get(i).get(j).split(":");

                    switch (Array[0]) {
                        case "adresse":

                            listStations.get(count).add(Array[0] + ":" + Array[1]);
                            break;
                        case "Gazole":

                            listStations.get(count).add(Array[0] + ":" + Array[1]);
                            break;
                        case "SP95":

                            listStations.get(count).add(Array[0] + ":" + Array[1]);
                            break;
                        case "SP98":

                            listStations.get(count).add(Array[0] + ":" + Array[1]);
                            break;
                        case "E10":

                            listStations.get(count).add(Array[0] + ":" + Array[1]);
                            break;
                        case "E85":

                            listStations.get(count).add(Array[0] + ":" + Array[1]);
                            break;
                        case "GPLc":

                            listStations.get(count).add(Array[0] + ":" + Array[1]);
                            break;
                    }
                    ++j;
                }
                count++;
            }
        }
        return listStations;
    }


    @Override
    public void notifyObservers() {
        LOGGER.debug("Notification de fin de calcul");
        setChanged(); // Set the changed flag to true, otherwise observers won't be notified.
        super.notifyObservers();
    }

    public List<List<String>> getListOfLists() {
        return listOfLists;
    }

    public boolean isExiste() {
        return existe;
    }

    public void setExiste(boolean existe) {
        this.existe = existe;
    }
}
