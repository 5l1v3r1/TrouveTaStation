package edu.isen.fh.carb.models;

import java.util.Comparator;

public class FloatComparator implements Comparator<Float> {
    @Override
    public int compare(Float f1, Float f2) {
        // Les 2 premières conditions me sont utiles pour afficher les 0.0 à la fin du tableau
        // Car 0.0 indique qu'il n'y a pas de valeurs
        if (f1.equals(0.0f)){
            f1 = 3.0f;
        }
        if (f2.equals(0.0f)){
            f2 = 3.0f;
        }
        return Float.compare(f1,f2);
    }
}