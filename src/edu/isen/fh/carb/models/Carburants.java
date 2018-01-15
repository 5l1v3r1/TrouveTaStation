package edu.isen.fh.carb.models;

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
     * Parse le fichier xml de models
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
     * Donne en fonction de la ville les stations-services et leurs models correspondant
     *
     * @param ville
     * @return
     */
    public List<Station> stationsOf(String ville) {
        List<Station> listStations = new ArrayList<Station>();
        int j;
        String[] Array;
        for (List<String> listOfList : listOfLists) {

            if (listOfList.get(1).equals("ville:" + ville) || listOfList.get(1).equals("ville:" + ville.toUpperCase())) {
                j = 0;
                Station station = new Station();

                for (String x : listOfList) {
                    Array = listOfList.get(j).split(":");

                    switch (Array[0]) {
                        case "adresse":
                            station.setAdresse(Array[1]);
                            break;
                        case "Gazole":
                            station.setGazole(Array[1]);
                            break;
                        case "SP95":
                            station.setSP95(Array[1]);
                            break;
                        case "SP98":
                            station.setSP98(Array[1]);
                            break;
                        case "E10":
                            station.setE10(Array[1]);
                            break;
                        case "E85":
                            station.setE85(Array[1]);
                            break;
                        case "GPLc":
                            station.setGPLc(Array[1]);
                            break;
                    }
                    ++j;
                }
                listStations.add(station);
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
