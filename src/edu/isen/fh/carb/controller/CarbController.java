package edu.isen.fh.carb.controller;


import edu.isen.fh.carb.models.Carburants;
import edu.isen.fh.carb.vue.Fenetre;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;
import java.net.URI;

/**
 * Contrôleur de la FFT
 */
public class CarbController {
    /**
     * Logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(CarbController.class);
    /**
     * Modèle de données Carburants
     */
    private Carburants model;

    /**
     * Default constructor
     *
     * @param model
     */
    public CarbController(Carburants model) {
        this.model = model;
    }

    /**
     * Setter de la FFT
     *
     * @param carburants
     */
    public void setCarburants(Carburants carburants) {
        this.model = carburants;
    }



    /**
     * Notification d'une action par la vue
     *
     * @param choix
     */
    public void notifyAction(int choix, String adresse) {
        switch (choix) {
            case 1:
                this.model.notifyObservers();
                break;
            case 2:
                this.model.getHttpStream();
                this.model.unzipTo("./src/xmlFile");
                this.model.parseFile("./src/xmlFile/PrixCarburants_instantane.xml");
                break;
            case 3:
                adresse = model.splitAdresse(adresse);
                Desktop d = Desktop.getDesktop();
                try {
                    d.browse(new URI("https://www.google.fr/maps/place/" + adresse));
                } catch (Exception e) {
                    LOGGER.error("Adresse : " + adresse);
                    LOGGER.error("URI : " + e);
                }
                break;
            default:
                break;
        }
    }
}
