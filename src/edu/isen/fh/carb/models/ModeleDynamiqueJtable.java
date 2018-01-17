package edu.isen.fh.carb.models;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class ModeleDynamiqueJtable extends AbstractTableModel {
    /**
     * Liste des Stations
     */
    private final List<Station> stationList = new ArrayList<Station>();
    /**
     * Entètes du tableau
     */
    private final String[] entetes = {"Adresse", "Gazole", "SP95", "SP98", "E10", "E85", "GPLc"};

    /**
     * Constructeur simple
     */
    public ModeleDynamiqueJtable() {
        super();
    }

    /**
     * Compte les lignes du tableau
     * @return
     */
    public int getRowCount() {
        return stationList.size();
    }

    /**
     * Compte les colonnes du tableau
     * @return
     */
    public int getColumnCount() {
        return entetes.length;
    }

    /**
     * Retourne le nom de la colonne
     * @param columnIndex
     * @return
     */
    public String getColumnName(int columnIndex) {
        return entetes[columnIndex];
    }

    /**
     * Retourne n'importe quelle valeur du tableau
     * @param rowIndex
     * @param columnIndex
     * @return
     */
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                return stationList.get(rowIndex).getAdresse();
            case 1:
                return stationList.get(rowIndex).getGazole();
            case 2:
                return stationList.get(rowIndex).getSP95();
            case 3:
                return stationList.get(rowIndex).getSP98();
            case 4:
                return stationList.get(rowIndex).getE10();
            case 5:
                return stationList.get(rowIndex).getE85();
            case 6:
                return stationList.get(rowIndex).getGPLc();
            default:
                return null; //Ne devrait jamais arriver
        }
    }

    /**
     * Ajoute une station dans le tableau
     * @param station
     */
    public void addStation(Station station) {
        stationList.add(station);

        fireTableRowsInserted(stationList.size() - 1, stationList.size() - 1);
    }

    /**
     * Supprime une station du tableau
     * @param rowIndex
     */
    public void removeStations(int rowIndex) {
        stationList.remove(rowIndex);
        fireTableRowsDeleted(rowIndex, rowIndex);
    }

    /**
     * Supprime toutes les stations du tableau
     */
    public void removeAllStations() {
        for(int i=stationList.size()-1; i >= 0;i--){
            this.removeStations(i);
        }
    }
}