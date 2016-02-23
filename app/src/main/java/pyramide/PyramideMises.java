package pyramide;

import java.util.Random;

import utilitaire.JoueurSingleton;

/**
 * Created by Maxime on 2016-02-18.
 */
public class PyramideMises {

    private float montantMise;
    private JoueurSingleton joueur;
    private int randomMaxPourGagner;
    private int dernierMontantChanceGagné;

    public PyramideMises(float miseDépart) {
        joueur = JoueurSingleton.getInstance();

        montantMise = joueur.getMontant(miseDépart);
        randomMaxPourGagner = 0;
    }

    public float getMontantMise() {
        return montantMise;
    }

    public int getDernierMontantChanceGagné() {
        return dernierMontantChanceGagné;
    }

    public void perdre() {
        montantMise = 0;
    }

    public void gagnerPremierTourStock() {
        montantMise *= 10;
        joueur.addMontant(montantMise);
    }

    public boolean chanceGagnerPeu() {
        Random rnd = new Random();

        if (rnd.nextInt(25) < randomMaxPourGagner) {
            dernierMontantChanceGagné = rnd.nextInt(3) + 1;
            joueur.addMontant(dernierMontantChanceGagné);
            randomMaxPourGagner = 0;
            return true;
        }
        else
            randomMaxPourGagner++;

        return false;
    }
}
