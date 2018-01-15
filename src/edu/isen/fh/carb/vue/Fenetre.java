package edu.isen.fh.carb.vue;

import edu.isen.fh.carb.controller.CarbController;
import edu.isen.fh.carb.models.Carburants;

import edu.isen.fh.carb.models.Station;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

/**
 * Fenêtre de l'application
 */
public class Fenetre extends JFrame implements Observer {
    /**
     * Logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(Fenetre.class);

    /**
     * Contrôleur de l'application
     */
    private CarbController controller;
    /**
     * Choix d'action à effectuer
     */
    private int choixActuel;
    /**
     * Affichage des valeurs
     */
    JTextField t1, t2, t3, t4, t5, t6, t7, t8, t9;
    JLabel p1, p2, p3, p4, p5, p6, p7, p8, p9;
    boolean zip = false;
    java.util.List<java.util.List<String>> listOfLists = new ArrayList<List<String>>();
    String ville = "";

    /**
     * Default constructor
     *
     * @param controller Contrôleur de l'application
     */
    public Fenetre(CarbController controller) {
        this.setTitle("Trouve ton gazole");
        this.setSize(400, 400);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.controller = controller;
        this.choixActuel = 0;

        // Panneau nord
        JPanel pan = new JPanel();
        JLabel label = new JLabel("Stations");
        pan.add(label);
        this.getContentPane().add(pan, BorderLayout.NORTH);


        // Panneau centre
        t1 = new JTextField();
        t2 = new JTextField();
        t3 = new JTextField();
        t4 = new JTextField();
        t5 = new JTextField();
        t6 = new JTextField();
        t7 = new JTextField();
        t8 = new JTextField();
        t9 = new JTextField();

        p1 = new JLabel("Ville :");
        p2 = new JLabel("champ2 :");
        p3 = new JLabel("champ3 :");
        p4 = new JLabel("champ4 :");
        p5 = new JLabel("champ5 :");
        p6 = new JLabel("champ6 :");
        p7 = new JLabel("champ7 :");
        p8 = new JLabel("champ8 :");
        p9 = new JLabel("champ9 :");


        JComponent pan2 = new JPanel();
        GroupLayout layout = new GroupLayout(pan2);
        pan2.setLayout(layout);

        // Turn on automatically adding gaps between components
        layout.setAutoCreateGaps(true);

        // Turn on automatically creating gaps between components that touch
        // the edge of the container and the container.
        layout.setAutoCreateContainerGaps(true);

        // Create a sequential group for the horizontal axis.

        GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();

        // The sequential group in turn contains two parallel groups.
        // One parallel group contains the labels, the other the text fields.
        // Putting the labels in a parallel group along the horizontal axis
        // positions them at the same x location.
        //
        // Variable indentation is used to reinforce the level of grouping.
        hGroup.addGroup(layout.createParallelGroup().
                addComponent(p1).addComponent(p2).addComponent(p3).addComponent(p4).addComponent(p5).addComponent(p6).addComponent(p7).addComponent(p8).addComponent(p9));
        hGroup.addGroup(layout.createParallelGroup().
                addComponent(t1).addComponent(t2).addComponent(t3).addComponent(t4).addComponent(t5).addComponent(t6).addComponent(t7).addComponent(t8).addComponent(t9));
        layout.setHorizontalGroup(hGroup);

        // Create a sequential group for the vertical axis.
        GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();

        // The sequential group contains two parallel groups that align
        // the contents along the baseline. The first parallel group contains
        // the first label and text field, and the second parallel group contains
        // the second label and text field. By using a sequential group
        // the labels and text fields are positioned vertically after one another.
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).
                addComponent(p1).addComponent(t1));
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).
                addComponent(p2).addComponent(t2));
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).
                addComponent(p3).addComponent(t3));
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).
                addComponent(p4).addComponent(t4));
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).
                addComponent(p5).addComponent(t5));
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).
                addComponent(p6).addComponent(t6));
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).
                addComponent(p7).addComponent(t7));
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).
                addComponent(p8).addComponent(t8));
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).
                addComponent(p9).addComponent(t9));
        layout.setVerticalGroup(vGroup);

        //Put the drawing area in a scroll pane.
        JScrollPane scroller = new JScrollPane(pan2);
        scroller.setPreferredSize(new Dimension(200, 200));

        this.getContentPane().add(scroller, BorderLayout.CENTER);


        // Panneau au sud
        Container pane = this.getContentPane();

        JButton button = new JButton("File");
        button.addActionListener(actionEvent -> {
            this.choixActuel = 1;
            this.controller.notifyAction(choixActuel);
        });

        GridLayout grid = new GridLayout(1, 1);
        this.setLayout(grid);
        JPanel panelSouth = new JPanel(grid);
        panelSouth.add(button);

        pane.add(panelSouth, BorderLayout.SOUTH);

        // Visibilité du centre
        this.setVisible(true);
    }

    /**
     * Mise à jour de la vue
     *
     * @param o
     * @param arg
     */
    @Override
    public void update(Observable o, Object arg) {
        LOGGER.debug("Mise à jour de la vue");
        if (o instanceof Carburants) {
            LOGGER.debug("Affichage des valeurs");

            Carburants carbu = (Carburants) o;

            List<Station> listStations;
            ville = t1.getText();
            ville = ville.substring(0, 1).toUpperCase() + ville.substring(1).toLowerCase();
            listStations = carbu.stationsOf(ville);

            for (int i = 0; i < listStations.size(); i++) {
                LOGGER.debug(listStations.get(i).getAdresse());
                LOGGER.debug(listStations.get(i).getGazole());
                LOGGER.debug(listStations.get(i).getSP95());
                LOGGER.debug(listStations.get(i).getSP98());
                LOGGER.debug(listStations.get(i).getE10());
                LOGGER.debug(listStations.get(i).getE85());
                LOGGER.debug(listStations.get(i).getGPLc());
            }
        }
    }
}
