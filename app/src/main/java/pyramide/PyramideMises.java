package pyramide;

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

    public void gagnerPremierTourStock() {
        montantMise *= 10;
        joueur.addMontant(montantMise);
    }
}
