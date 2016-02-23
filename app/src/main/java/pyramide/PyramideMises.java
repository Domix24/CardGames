package pyramide;

import java.util.Random;

import utilitaire.JoueurSingleton;

/**
 * Created by Maxime on 2016-02-18.
 */
public class PyramideMises {

    private float montantMise;
    private JoueurSingleton joueur;

    public PyramideMises(float miseDépart) {
        joueur = JoueurSingleton.getInstance();

        montantMise = joueur.getMontant(miseDépart);
    }

    public float getMontantMise() {
        return montantMise;
    }

    public void perdre() {
        montantMise = 0;
    }

    public void gagnerPremierTourStock() {
        montantMise *= 10;
        joueur.addMontant(montantMise);
    }

    public void chanceGagnerPeu() {
        Random rnd = new Random();
        joueur.addMontant(rnd.nextInt(10) + 1);
    }
}
