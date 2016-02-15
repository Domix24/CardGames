package videopoker;

import utilitaire.Carte;
import utilitaire.JeuAvecCartes;
import utilitaire.JeuDeCarte;

/**
 * Created by Mikael Andries-Gounant on 12/02/2016.
 */
public class VideoPoker extends JeuAvecCartes {
    private static VideoPoker instance = null;
    Carte[] paquetPremier = null;
    JeuDeCarte paquetFinal = null;
    boolean aPremièresCartes = false;
    int nbCartesGardées = 0;

    /**
     * Constructeur
     */
    private VideoPoker() {
        paquet = new JeuDeCarte();
    }


    /**
     * Permet d'avoir l'instance du jeu
     *
     * @return l'instance du jeu BlackJack
     */
    public static VideoPoker avoirInstance() {
        if (instance == null) {
            instance = new VideoPoker();
        }

        return instance;
    }

    public void reinitialiserJeu() {
        paquet = new JeuDeCarte();
        paquetFinal = null;
        paquetPremier = null;
        aPremièresCartes = false;
        nbCartesGardées = 0;
    }

    /**
     * Passe les cartes, si le joueur n'a pas encore eu de cartes, passé 5 cartes sinon passé les cartes qui manquent
     */
    private void passerCartes() {
        if (aPremièresCartes) {
            for (int i = nbCartesGardées; i < 5; i++) {
                paquetFinal.add(paquet.PigerUneCarte());
            }
        } else {
            aPremièresCartes = true;
            for (int i = 0; i < 5; i++) {
                paquetPremier[i] = paquet.PigerUneCarte();
            }
        }
    }

    /**
     * Lorsque le joueur a décidé quel cartes il voulait garder, garder les cartes, en passer d'autres et vérifier les points
     */
    public void validerCartes(Carte[] cartes) {
        for (int i = 0; i < 5; i++) {
            if (cartes[i] != null) {
                paquetFinal.add(cartes[i]);
                nbCartesGardées++;
            } else {
                break;
            }
        }
        passerCartes();
        int nbJetons = compterPoints();
    }

    private int compterPoints() {
        int nbJetons = 0;
        paquetFinal.OrdonnerCartesCroissant();
        if (siValetouMieux()) {
            nbJetons = 10;
        }

        if (siDeuxPairs()) {
            nbJetons = 25;
        }
        if (siTroisPareil()) {
            nbJetons = 50;
        }
        if (siSuiteSansAs() || siSuiteAvecAs()) {
            nbJetons = 75;
        }
        if (siCouleur()) {
            nbJetons = 125;
        }
        if (siMaisonPleine()) {
            nbJetons = 250;
        }
        if (siQuatrePareil()) {
            nbJetons = 500;
        }
        if (siSuiteCouleur()) {
            nbJetons = 1000;
        }
        if (siSuiteCouleurRoyal()) {
            nbJetons = 5000;
        }

        return nbJetons;
    }

    /**
     * Vérifie s'il y a un carte avec une valeur de plus de 10
     *
     * @return vrai s'il y a un valet ou mieux faux si non
     */
    private boolean siValetouMieux() {
        for (int i = 0; i < 5; i++) {
            if (paquetFinal.get(i).numero >= 11) {
                return true;
            }
        }
        return false;
    }

    /**
     * Vérifie s'il y a deux pairs
     *
     * @return vrai s'il y a deux pairs faux sinon
     */
    private boolean siDeuxPairs() {
        boolean aUnePair = false;
        if (paquetFinal.get(0).numero == paquetFinal.get(1).numero)
            aUnePair = true;
        if (paquetFinal.get(1).numero == paquetFinal.get(2).numero) {
            if (aUnePair) {
                return true;
            } else
                aUnePair = true;
        }

        if (paquetFinal.get(2).numero == paquetFinal.get(3).numero) {
            if (aUnePair) {
                return true;
            } else
                aUnePair = true;
        }

        if (paquetFinal.get(3).numero == paquetFinal.get(4).numero) {
            if (aUnePair) {
                return true;
            } else
                return false;
        }
        return false;
    }

    /**
     * Vérifie s'il y a 3 cartes pareil
     *
     * @return vrai s'il y a 3 cartes pareil faux sinon
     */
    private boolean siTroisPareil() {
        if (paquetFinal.get(0).numero == paquetFinal.get(1).numero && paquetFinal.get(1).numero == paquetFinal.get(2).numero) {
            return true;
        } else if (paquetFinal.get(2).numero == paquetFinal.get(3).numero && paquetFinal.get(3).numero == paquetFinal.get(4).numero) {
            return true;
        } else if (paquetFinal.get(1).numero == paquetFinal.get(2).numero && paquetFinal.get(2).numero == paquetFinal.get(3).numero) {
            return true;
        }
        return false;
    }

    /**
     * Si c'est une suite
     *
     * @return vrai si c'est une suite, faux sinon
     */
    private boolean siSuiteSansAs() {
        //Vérifie si c'est une suite sans compter que l'as peut suivre le roi
        for (int i = 0; i < 4; i++) {
            if (!(paquetFinal.get(i).numero == paquetFinal.get(i + 1).numero + 1)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Si c'est une suite
     *
     * @return vrai si c'est une suite, faux sinon
     */
    private boolean siSuiteAvecAs() {
        for (int i = 0; i < 4; i++) {
            if (i == 0) {
                if (!(paquetFinal.get(i).numero == 1 && paquetFinal.get(1).numero == 10)) {
                    return false;
                }
            } else if (!(paquetFinal.get(i).numero == paquetFinal.get(i + 1).numero + 1)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Si c'est une couleur
     *
     * @return vrai si c'est une couleur, faux sinon
     */
    private boolean siCouleur() {
        if (paquetFinal.get(0).couleur == paquetFinal.get(1).couleur && paquetFinal.get(1).couleur == paquetFinal.get(2).couleur &&
                paquetFinal.get(2).couleur == paquetFinal.get(3).couleur && paquetFinal.get(3).couleur == paquetFinal.get(4).couleur)
            return true;
        return false;
    }

    /**
     * Vérifie s'il y a 3 cartes pareil
     *
     * @return la valeur des 3 cartes pareil
     */
    private int siTroisPourMaison() {
        if (paquetFinal.get(0).numero == paquetFinal.get(1).numero && paquetFinal.get(1).numero == paquetFinal.get(2).numero) {
            return paquetFinal.get(0).numero;
        } else if (paquetFinal.get(2).numero == paquetFinal.get(3).numero && paquetFinal.get(3).numero == paquetFinal.get(4).numero) {
            return paquetFinal.get(2).numero;
        }
        return 0;
    }

    /**
     * Vérifie s'il y a une pair
     *
     * @param exception la valeur qu'il ne faut pas compter
     * @return vrai s'il y a une pair, faux sinon
     */
    private boolean siPair(int exception) {
        if (paquetFinal.get(0).numero == paquetFinal.get(1).numero) {
            if (paquetFinal.get(0).numero != exception)
                return true;
        }
        if (paquetFinal.get(1).numero == paquetFinal.get(2).numero) {
            if (paquetFinal.get(1).numero != exception)
                return true;
        }

        if (paquetFinal.get(2).numero == paquetFinal.get(3).numero) {
            if (paquetFinal.get(2).numero != exception)
                return true;
        }

        if (paquetFinal.get(3).numero == paquetFinal.get(4).numero) {
            if (paquetFinal.get(3).numero != exception)
                return true;
        }
        return false;
    }

    /**
     * S'il y a trois pareils et une pair
     *
     * @return vrai si c'est une maison pleine, sinon faux
     */
    private boolean siMaisonPleine() {
        int troisPareil = siTroisPourMaison();
        if (troisPareil == 0) {
            return false;
        }
        return siPair(troisPareil);
    }

    /**
     * Vérifie s'il y a 4 cartes pareil
     *
     * @return vrai s'il y a 4 cartes pareil faux sinon
     */
    private boolean siQuatrePareil() {
        if (paquetFinal.get(0).numero == paquetFinal.get(1).numero && paquetFinal.get(1).numero == paquetFinal.get(2).numero && paquetFinal.get(2).numero == paquetFinal.get(3).numero) {
            return true;
        } else if (paquetFinal.get(1).numero == paquetFinal.get(2).numero && paquetFinal.get(2).numero == paquetFinal.get(3).numero && paquetFinal.get(3).numero == paquetFinal.get(4).numero) {
            return true;
        }
        return false;
    }

    private boolean siSuiteCouleur() {
        //Vérifie si c'est une suite sans compter que l'as peut suivre le roi
        int couleur = paquetFinal.get(0).couleur;
        for (int i = 0; i < 4; i++) {
            if (!(paquetFinal.get(i).numero == paquetFinal.get(i + 1).numero + 1)) {
                return false;
            } else if (paquetFinal.get(i).couleur != couleur || paquetFinal.get(i + 1).couleur != couleur)
                return false;
        }
        return true;
    }

    private boolean siSuiteCouleurRoyal() {
        int couleur = paquetFinal.get(0).couleur;
        for (int i = 0; i < 4; i++) {
            if (i == 0) {
                if (!(paquetFinal.get(i).numero == 1 && paquetFinal.get(1).numero == 10)) {
                    return false;
                }
            } else if (!(paquetFinal.get(i).numero == paquetFinal.get(i + 1).numero + 1)) {
                return false;
            } else if (paquetFinal.get(i).couleur != couleur || paquetFinal.get(i + 1).couleur != couleur)
                return false;
        }
        return true;
    }

    @Override
    public void Initialiser(int seed) {

    }
}
