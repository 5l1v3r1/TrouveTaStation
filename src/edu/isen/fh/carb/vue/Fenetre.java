package edu.isen.fh.carb.vue;

import edu.isen.fh.carb.controller.CarbController;
import edu.isen.fh.carb.models.Carburants;

import edu.isen.fh.carb.models.FloatComparator;
import edu.isen.fh.carb.models.ModeleDynamiqueJtable;
import edu.isen.fh.carb.models.Station;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import static javax.swing.ListSelectionModel.SINGLE_SELECTION;

/**
 * Fenêtre de l'application
 */
public class Fenetre extends JFrame implements Observer {
    /**
     * Logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(Fenetre.class);
    /**
     * Modèle dynamique du JTable
     */
    private ModeleDynamiqueJtable modeleJTable = new ModeleDynamiqueJtable();
    /**
     * Tableau JTable central
     */
    private JTable tableau;
    /**
     * Contrôleur de l'application
     */
    private CarbController controller;
    /**
     * Choix d'action à effectuer
     */
    private int choixActuel;
    /**
     * Text field de la ville
     */
    private JTextField t1;
    /**
     * Icone du wifi
     */
    private ImageIcon icon;
    /**
     * JLabel du wifi
     */
    private JLabel statusLabel;

    /**
     * Constructeur simple
     *
     * @param controller Contrôleur de l'application
     */
    public Fenetre(CarbController controller) {
        this.setTitle("Trouve ta station");
        this.setSize(700, 500);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.controller = controller;
        this.choixActuel = 0;
        //TODO rajouter automate CB dans le Parser, Station (boolean) true false, Tableau
        //TODO faire les tests

        //-------------------------------Panneau haut------------------------------------>
        JLabel p1 = new JLabel("Ville :");
        t1 = new JTextField();
        //----Raccourci ENTER sur le JTextField------------------------>
        Action action = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                choixActuel = 1;
                controller.notifyAction(choixActuel,"");
            }};
        String keyStrokeAndKey = "ENTER";
        KeyStroke keyStroke = KeyStroke.getKeyStroke(keyStrokeAndKey);
        t1.getInputMap().put(keyStroke, keyStrokeAndKey);
        t1.getActionMap().put(keyStrokeAndKey, action);
        //------------------------------------------------------------->
        JButton button = new JButton("Rechercher");
        button.addActionListener(actionEvent -> {
            this.choixActuel = 1;
            this.controller.notifyAction(choixActuel,"");
        });

        ImageIcon refreshIcon = new ImageIcon(new ImageIcon("src/icons/refresh-button.png").getImage().getScaledInstance(16,16,Image.SCALE_DEFAULT));

        JButton reloadButton = new JButton(refreshIcon);
        reloadButton.addActionListener(actionEvent -> {
            this.choixActuel = 2;
            this.controller.notifyAction(choixActuel,"");
        });

        // Icone wifi
        icon = new ImageIcon(new ImageIcon("src/icons/no-wifi.png").getImage().getScaledInstance(16,16,Image.SCALE_DEFAULT));
        statusLabel = new JLabel();
        statusLabel.setIcon(icon);

        // Group Layout
        JPanel pan = new JPanel();
        GroupLayout layout = new GroupLayout(pan);
        pan.setLayout(layout);

        // Turn on automatically adding gaps between components
        layout.setAutoCreateGaps(true);

        // Turn on automatically creating gaps between components that touch
        // the edge of the container and the container.
        layout.setAutoCreateContainerGaps(true);

        // Create a sequential group for the horizontal axis.
        GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();
        hGroup.addGroup(layout.createParallelGroup().
                addComponent(p1));
        hGroup.addGroup(layout.createParallelGroup().
                addComponent(t1));
        hGroup.addGroup(layout.createParallelGroup().
                addComponent(button));
        hGroup.addGroup(layout.createParallelGroup().
                addComponent(reloadButton));
        hGroup.addGroup(layout.createParallelGroup().
                addComponent(statusLabel));
        layout.setHorizontalGroup(hGroup);

        // Create a sequential group for the vertical axis.
        GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();
        vGroup.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).
                addComponent(p1).addComponent(t1).addComponent(button).addComponent(reloadButton).addComponent(statusLabel));
        layout.setVerticalGroup(vGroup);

        this.getContentPane().add(pan, BorderLayout.NORTH);

        //-------------------------------Panneau centre---------------------------------->

        tableau = new JTable(modeleJTable);
        tableau.setSelectionMode(SINGLE_SELECTION);
        // Alternance des couleurs du fond des cases
        UIManager.put("Table.alternateRowColor", new Color(211, 213, 213));

        // Taille de la première colonne
        tableau.getColumnModel().getColumn(0).setPreferredWidth(350);

        // Tri
        TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(tableau.getModel());
        sorter.setSortable(0, false);// On enlève le tri inutile sur la colonne "Adresse"

        // On ajoute le tri avec le FloatComparator sur les colonnes des carburants
        sorter.setComparator(1, new FloatComparator());
        sorter.setComparator(2, new FloatComparator());
        sorter.setComparator(3, new FloatComparator());
        sorter.setComparator(4, new FloatComparator());
        sorter.setComparator(5, new FloatComparator());
        sorter.setComparator(6, new FloatComparator());

        tableau.setRowSorter(sorter);

        getContentPane().add(new JScrollPane(tableau), BorderLayout.CENTER);

        //-------------------------------Panneau bas------------------------------------->

        JButton openBrowserButton = new JButton("Ouvrir la selection dans maps");

        openBrowserButton.addActionListener(actionEvent -> {
            int selection = tableau.getSelectedRow();
            if (!(selection == -1)) // Si une ligne a était sélectionnée
            {
                selection = tableau.getRowSorter().convertRowIndexToModel(selection);
                this.choixActuel = 3;
                this.controller.notifyAction(choixActuel, modeleJTable.getValueAt(selection,0)+" "+t1.getText());
            }else{
                LOGGER.error("Aucune selection");
            }
        });

        this.getContentPane().add(openBrowserButton, BorderLayout.SOUTH);

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
        LOGGER.info("Mise à jour de la vue");
        if (o instanceof Carburants) {
            LOGGER.info("Affichage des valeurs");

            Carburants carbu = (Carburants) o;

            // Modification de l'icone en fonction du status
            icon = new ImageIcon(new ImageIcon("src/icons/"+carbu.getStatus()+".png").getImage().getScaledInstance(16,16,Image.SCALE_DEFAULT));
            statusLabel.setIcon(icon);

            List<Station> listStations;
            String ville = t1.getText();
            listStations = carbu.stationsOf(ville);

            // On supprime toutes les anciennes stations du JTables
            modeleJTable.removeAllStations();

            // On ajoute toutes les stations de la listStations dans le tableau
            for (int i = 0; i < listStations.size(); i++) {
                modeleJTable.addStation(listStations.get(i));
            }
        }
    }
}
