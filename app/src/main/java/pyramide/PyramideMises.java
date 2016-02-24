package pyramide;

import java.util.Random;

import utilitaire.JoueurSingleton;

/**
 * Created by Maxime on 2016-02-18.
 */
public class PyramideMises {

    private float montantMise;
    private float montantGagné;
    private JoueurSingleton joueur;
    private int randomMaxPourGagner;
    private int dernierMontantChanceGagné;

    /**
     * Initialiser la mise
     * @param miseDépart La mise de départ qui ne pourra pas être changée
     */
    public PyramideMises(float miseDépart) {
        joueur = JoueurSingleton.getInstance();

        montantMise = joueur.getMontant(miseDépart);
        montantGagné = 0;
        randomMaxPourGagner = 0;
    }

    /**
     * @return Le montant de la mise de départ
     */
    public float getMontantMise() {
        return montantMise;
    }

    /**
     * @return Le montant gagnée qui est à 0 par défaut
     */
    public float getMontantGagné() {
        return montantGagné;
    }

    /**
     * @return Le dernier montant gagné lors d'une paire de la pyramide
     */
    public int getDernierMontantChanceGagné() {
        return dernierMontantChanceGagné;
    }

    /**
     * Appeler quand la partie est perdue
     */
    public void perdre() {
        montantMise = 0;
    }

    /**
     * Mets à jour le montant gagné et l'ajoute dans la banque du joueurSingleton
     * @param facteur à mutliplier avec la mise de départ
     */
    public void gagner(int facteur) {
        montantGagné = montantMise * facteur;
        montantMise = 0;
        joueur.addMontant(montantGagné);
    }

    /**
     * Méthode appelée à chaque fois qu'une paire est faite
     * Le joueur a une petite chance de gagner un petit montant
     * La chance de gagner augmente à chaque tour et reset quand il gagne un montant
     * @return True s'il a gagné, false sinon
     */
    public boolean chanceGagnerPeu() {
        Random rnd = new Random();

        if (rnd.nextInt(30) < randomMaxPourGagner) {
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
