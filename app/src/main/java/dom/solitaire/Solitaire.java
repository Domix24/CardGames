package dom.solitaire;

import android.app.Notification;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import utilitaire.Carte;
import utilitaire.JeuAvecCartes;
import utilitaire.JeuDeCarte;

/**
 * Created by dominic on 12/02/16.
 * Logique pour le jeu de solitaire
 */
public class Solitaire extends JeuAvecCartes {

    private Carte[] foundations; // va contenir la carte sur le dessus seulement
    //public enum type{Trèfle,Carre,Coeur,Pique};
    // 0. Coeur
    // 1. Pique typeCa
    // 2. Carreau (typeCarte = type.Carre
    // 3. Trèfle

    private Queue<Carte> waste;
    private JeuDeCarte paquet;
    private List<CarteColonne>[] tableau;
    private final int FOUNDATIONS_ARRAY_LENGTH = 4;
    private final int TABLEAU_COLUMNS_COUNT = 7;

    /**
     * Permet d'avoir la carte active...
     * @return la carte sur le dessus de la pile
     */
    public  Carte CarteActive() {
        return  waste.peek();
    }

    /**
     * Initialise le jeu
     * @param seed
     */
    @Override
    public void Initialiser(int seed) {
        InitialiserFoundations();
        waste = new LinkedBlockingQueue<>();

        paquet = new JeuDeCarte();

        paquet.MélangerPaquet();

        InitialiserTableau();
    }

    /**
     * Pige une nouvelle carte et remet le waste dans le paquet si plus de carte
     */
    public void PigerNouvelleCarte() {
        Carte carte = paquet.PigerUneCarte();

        if(carte == null)
        {
            while(!waste.isEmpty()) {
                paquet.add(waste.poll());
            }
            carte = paquet.PigerUneCarte();
        }
    }

    /**
     * Vérifie si les fondations sont pleines
     * @return vrai les fondations sont pleines
     */
    public boolean vérifierVictoire() {
        int compteur = 0;
        for(int i = 0; i < FOUNDATIONS_ARRAY_LENGTH; i++) {
            if(foundations[i] != null && foundations[i].numero == 13) {
                compteur++;
            }
        }
        return compteur==4;
    }

    /**
     * Remplis les fondations avec du vide
     */
    private void InitialiserFoundations() {
        foundations = new Carte[FOUNDATIONS_ARRAY_LENGTH];

        for(int i = 0; i < foundations.length; i++) {
            foundations[i] = null;
        }
    }

    /**
     * Initialise le tableau avec des cartes cachées et non-cachées
     */
    private void InitialiserTableau() {
        tableau = new List[TABLEAU_COLUMNS_COUNT];

        for(int i = 0; i < TABLEAU_COLUMNS_COUNT; i++) {
            tableau[i] = new ArrayList<>();
            for(int j = 0 ; j < i+2; j++) {
                tableau[i].add(new CarteColonne(paquet.PigerUneCarte(), j == i+1));
            }
        }
    }

    /**
     * Vérifie si la carte peut être mise dans la fondation
     * @param carte la carte à placer
     * @param colonne la colonne de la carte
     * @return vrai si peut jouer
     */
    public boolean PlacerCarteDansFoundations(Carte carte, int colonne) {
        if(tableau[colonne].get(tableau[colonne].size() - 1).carte != carte)
            return false;

        int foundationIndex = 0;
        if(carte.typeCarte == JeuDeCarte.type.Pique) {
            foundationIndex = 1;
        }
        else if(carte.typeCarte == JeuDeCarte.type.Carre) {
            foundationIndex = 2;
        }
        else if(carte.typeCarte == JeuDeCarte.type.Trèfle) {
            foundationIndex = 3;
        }

        if((foundations[foundationIndex] == null && carte.numero != 1) || (foundations[foundationIndex] != null && foundations[foundationIndex].numero + 1 == carte.numero))
            return false;

        foundations[foundationIndex] = carte;
        tableau[colonne].remove(tableau[colonne].size()-1);
        tableau[colonne].get(tableau[colonne].size()-1).devoilerCarte();

        return true;
    }

    /**
     * Déplace une carte / paquet vers une autre colonne
     * @param carte la carte à la base du paquet
     * @param colonneDepart la colonne de la base du paquet
     * @param ligneDepart la ligne de la base du paquet
     * @param colonneArrivée la colonne d'arrivée du paquet
     * @return vrai si la colonne peut être déplacée
     */
    public boolean DéplacerPaquetVersAutreColonne(Carte carte, int colonneDepart, int ligneDepart, int colonneArrivée) {

        if(tableau[colonneArrivée].size() == 0 && carte.numero != 13) {
            return false;
        }

        if(tableau[colonneArrivée].size() != 0) {
            if(tableau[colonneArrivée].get(tableau[colonneArrivée].size() - 1).carte.couleur == carte.couleur) {
                return false;
            }
        }

        // TODO: déplacement d'un tas de carte...

        return true;
    }


    /**
     * Classe pour le dévoillement d'une carte dans le jeu
     */
    public class CarteColonne {
        public Carte carte;
        public boolean estDévoilée;

        /**
         * Permet de créer une carte active
         * @param carte la carte
         * @param estDévoilée si la carte doit être immédiatement dévoilée
         */
        public CarteColonne(Carte carte, boolean estDévoilée) {
            this.carte = carte;
            this.estDévoilée = estDévoilée;
        }
        public CarteColonne(Carte carte) {
            this(carte, false);
        }
        public CarteColonne() {
            this(null);
        }

        /**
         * Dévoile la carte
         */
        public void devoilerCarte() {
            estDévoilée = true;
        }
    }
}
