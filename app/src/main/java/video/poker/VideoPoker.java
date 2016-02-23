package video.poker;

import utilitaire.Carte;
import utilitaire.JeuAvecCartes;
import utilitaire.JeuDeCarte;

/**
 * Créé par Mikael Andries-Gounant le 12/02/2016.
 */
public class VideoPoker extends JeuAvecCartes {
    private static VideoPoker instance = null;
    JeuDeCarte paquetPremier = new JeuDeCarte();
    JeuDeCarte paquetFinal = new JeuDeCarte();
    boolean aPremièresCartes = false;
    int nbCartesGardées = 0;
    boolean estCree = false;
    boolean aValider = false;
    boolean aMisé = false;
    float mise = 0.0f;

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
        paquetFinal.clear();
        paquetPremier.clear();
        aPremièresCartes = false;
        nbCartesGardées = 0;
        estCree = true;
        aValider = false;
        aMisé = false;
        mise = 0.0f;

    }

    /**
     * Passe les cartes, si le joueur n'a pas encore eu de cartes, passé 5 cartes sinon passé les cartes qui manquent
     */
    public JeuDeCarte passerCartes() {
        if (aPremièresCartes) {
            for (int i = nbCartesGardées; i < 5; i++) {
                paquetFinal.set(i, paquet.pigerUneCarte());
            }
            paquetFinal.ordonnerCartes();
            return paquetFinal;
        } else {
            aPremièresCartes = true;
            for (int i = 0; i < 5; i++) {
                paquetPremier.set(i, paquet.pigerUneCarte());
            }
            paquetPremier.ordonnerCartes();
            return paquetPremier;
        }
    }

    /**
     * Lorsque le joueur a décidé quel cartes il voulait garder, garder les cartes, en passer d'autres et vérifier les points
     */
    public JeuDeCarte validerCartes(JeuDeCarte cartes) {
        if (!aValider) {
            for (int i = 0; i < 5; i++) {
                if (cartes.get(i).nom != "null") {
                    paquetFinal.set(nbCartesGardées++, cartes.get(i));
                }
            }
            aValider = true;
            passerCartes();
            return paquetFinal;
        } else
            return null;

    }

    /**
     * compter les points du joueur
     *
     * @return
     */
    public float compterPoints() {
        /**paquetFinal.clear();
         Carte c1 = new Carte(11,-65536,0,"Carre11", JeuDeCarte.type.Carre);
         Carte c2 = new Carte(4,-16777216,2,"Pique4", JeuDeCarte.type.Pique);
         Carte c3 = new Carte(5,-16777216,2,"Pique5", JeuDeCarte.type.Pique);
         Carte c4 = new Carte(6,-16777216,1,"Trèfle6", JeuDeCarte.type.Trèfle);
         Carte c5 = new Carte(7,-65536,0,"Carre7", JeuDeCarte.type.Carre);

         paquetFinal.add(c1);
         paquetFinal.add(c2);
         paquetFinal.add(c3);
         paquetFinal.add(c4);
         paquetFinal.add(c5);
         **/
        float multiplicateur = 0;
        if (siValetouMieux()) {
            multiplicateur = 1;
        }

        if (siDeuxPairs()) {
            multiplicateur = 2;
        }
        if (siTroisPareil()) {
            multiplicateur = 3;
        }
        if (siSuiteSansAs() || siSuiteAvecAs()) {
            multiplicateur = 4;
        }
        if (siCouleur()) {
            multiplicateur = 6;
        }
        if (siMaisonPleine()) {
            multiplicateur = 9;
        }
        if (siQuatrePareil()) {
            multiplicateur = 25;
        }
        if (siSuiteCouleur()) {
            multiplicateur = 50;
        }
        if (siSuiteCouleurRoyal()) {
            multiplicateur = 250;
        }

        return mise * multiplicateur;
    }

    /**
     * Vérifie s'il y a une paire de valet ou mieux
     *
     * @return vrai s'il y a une paire de valet ou mieux faux si non
     */
    private boolean siValetouMieux() {
        int nbValet = 0;
        int nbDame = 0;
        int nbRoi = 0;
        int nbAs = 0;
        for (int i = 0; i < 5; i++) {
            if (paquetFinal.get(i).numero == 11) {
                nbValet++;
            } else if (paquetFinal.get(i).numero == 12)
                nbDame++;
            else if (paquetFinal.get(i).numero == 13)
                nbRoi++;
            else if (paquetFinal.get(i).numero == 1)
                nbAs++;
        }
        if (nbValet >= 2 || nbDame >= 2 || nbRoi >= 2 || nbAs >= 2)
            return true;
        else
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
        boolean estSuite = true;
        for (int i = 0; i < 4; i++) {
            if (!((paquetFinal.get(i).numero + 1) == (paquetFinal.get(i + 1).numero))) {
                estSuite = false;
            }
        }
        return estSuite;
    }

    /**
     * Si c'est une suite
     *
     * @return vrai si c'est une suite, faux sinon
     */
    private boolean siSuiteAvecAs() {
        // Replacer le paquet pour mieux compter
        JeuDeCarte cartes = new JeuDeCarte();
        cartes.clear();
        mettreCarteANull(cartes);
        int index = 0;
        int lastnumber = 9;
        for (int i = 0; i < 5; i++) {
            if (paquetFinal.get(i).numero > lastnumber) {
                cartes.set(index, paquetFinal.get(i));
                lastnumber = paquetFinal.get(i).numero;
                index++;
            }
        }
        lastnumber = 9;
        for (int i = 0; i < 5; i++) {
            if (paquetFinal.get(i).numero < lastnumber) {
                cartes.set(index, paquetFinal.get(i));
                index++;
            }
        }
        boolean estSuite = true;
        for (int i = 0; i < 4; i++) {
            if ((cartes.get(i).numero + 1) == (cartes.get(i + 1).numero)) {
                estSuite = true;
            } else if (cartes.get(i).numero == 13 && cartes.get(i + 1).numero == 1) {
                estSuite = true;
            } else {
                estSuite = false;
                break;
            }
        }
        return estSuite;
    }

    /**
     * Si c'est une couleur
     *
     * @return vrai si c'est une couleur, faux sinon
     */
    private boolean siCouleur() {
        if (paquetFinal.get(0).sorte == paquetFinal.get(1).sorte && paquetFinal.get(1).sorte == paquetFinal.get(2).sorte &&
                paquetFinal.get(2).sorte == paquetFinal.get(3).sorte && paquetFinal.get(3).sorte == paquetFinal.get(4).sorte)
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
        int couleur = paquetFinal.get(0).sorte;
        for (int i = 0; i < 4; i++) {
            if (i == 0) {
                if (!(paquetFinal.get(i).numero == 1 && paquetFinal.get(1).numero == 10)) {
                    return false;
                }
            } else if (!(paquetFinal.get(i).numero == paquetFinal.get(i + 1).numero + 1)) {
                return false;
            } else if (paquetFinal.get(i).sorte != couleur || paquetFinal.get(i + 1).sorte != couleur)
                return false;
        }
        return true;
    }

    /**
     * Méthode qui permet de trouver l'id d'une image pour une carte
     *
     * @param nom le champ nom de l'objet carte
     * @return l'id de la ressource d'image correspondant à la carte
     */
    public int trouverIdCarte(String nom) {
        return paquet.trouverIdCarte(nom);
    }

    /**
     * Met les cartes a null
     *
     * @param cartes le paquet de cartes
     */
    private void mettreCarteANull(JeuDeCarte cartes) {
        Carte cartenull = new Carte(1, 1, 1, "null", JeuDeCarte.type.Carre);
        for (int i = 0; i < 5; i++) {
            cartes.add(cartenull);
        }
    }


}
