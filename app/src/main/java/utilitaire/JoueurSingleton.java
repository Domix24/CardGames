package utilitaire;

import android.content.Context;

/**
 * Classe singleton pour les informations du joueur (dont la monnaie)
 * Created by Maxime on 2016-02-15.
 */
public class JoueurSingleton {
    private static JoueurSingleton instance = null;
    private static String nomJoueur;
    private static float monnaie;

    protected JoueurSingleton() {
        // Existe seulement pour empecher l'instantiation
    }

    /**
     * Si l'instance n'a pas encore été créée
     * @return L'instance du joueur
     */
    public static JoueurSingleton getInstance() {
        if (instance == null) {
            initialiserJoueur();
        }
        return instance;
    }

    /**
     * @return Retourne la monnaie totale du joueur
     */
    public float getMonnaie() {
        return monnaie;
    }

    /**
     * Enlève le montant demandé dans la monnaie totale du joueur et la retourne
     * Si le montant total est inférieur au montant demandé, retourner toute la monnaie restante
     * @return Le montant demandé
     */
    public float getMontant(float montant) {
        if (monnaie < montant) {
            montant = monnaie;
        }

        monnaie -= montant;
        return montant;
    }

    /**
     * @param montant à ajouter dans la monnaie totale
     */
    public void AddMontant(float montant) {
        monnaie += montant;
    }

    private static void initialiserJoueur() {
        nomJoueur = "Kitty";
        monnaie = 50;
    }
    public String AvoirLeNom()
    {
        return this.nomJoueur;
    }

}
