package edu.isen.fhgd.fft.controller;


import edu.isen.fhgd.fft.carburants.Carburants;
import edu.isen.fhgd.fft.vue.Fenetre;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    public void notifyAction(int choix) {
        switch (choix) {
            case 1:
                if(!this.model.isExiste()) {
                    this.model.getHttpStream();
                    this.model.unzipTo("./src/xmlFile");
                    this.model.parseFile("./src/xmlFile/PrixCarburants_instantane.xml");
                    this.model.setExiste(true);
                }else{
                    LOGGER.debug("Le fichier à déjà était téléchargé");
                    this.model.notifyObservers();
                }
                break;
            case 2:

                break;
            case 3:

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
