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
    private Carburants model = null;
    /**
     * Fenêtre d'affichage
     */
    private Fenetre fen;

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
     * Récupération de la fenêtre
     *
     * @return
     */
    public Fenetre getFen() {
        return fen;
    }

    /**
     * Setter de fenêtre
     *
     * @param fen
     */
    public void setFen(Fenetre fen) {
        this.fen = fen;
    }

    /**
     * Notification d'une action par la vue
     *
     * @param choix
     */
    public void notifyAction(int choix, String adresse) {
        switch (choix) {
            case 1:
                if (!this.model.isExiste()) {
                    this.model.getHttpStream();
                    this.model.unzipTo("./src/xmlFile");
                    this.model.parseFile("./src/xmlFile/PrixCarburants_instantane.xml");
                } else {
                    LOGGER.info("Le fichier à déjà était téléchargé");
                    this.model.notifyObservers();
                }
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
                    d.browse(new URI("https://www.google.fr/maps/place/"+adresse));
                } catch (Exception e) {
                    LOGGER.error("URI : " + e);
                }
                break;
            case 4:

                break;
            case 5:

                break;
            case 6:

                break;
            default:
                break;
        }
    }
}
