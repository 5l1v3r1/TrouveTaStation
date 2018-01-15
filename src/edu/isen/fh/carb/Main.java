package edu.isen.fh.carb;

import edu.isen.fh.carb.controller.CarbController;
import edu.isen.fh.carb.models.Carburants;
import edu.isen.fh.carb.vue.Fenetre;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        // Initialisation de l'objet Carburant
        Carburants carb = new Carburants();
        // Création du contrôleur
        CarbController controller = new CarbController(carb);
        // Création de la fenêtre
        Fenetre fen = new Fenetre(controller);
        // Ajout de l'observer
        carb.addObserver(fen);
        controller.setCarburants(carb);
        controller.setFen(fen);
    }
}
