package blackjack;

import utilitaire.Carte;
import utilitaire.JeuAvecCartes;
import utilitaire.JeuDeCarte;

/**
 * Created by Mikael Andries-Gounant on 29/01/2016.
 */
public class BlackJack extends JeuAvecCartes {

    public boolean estCree = false;
    private static BlackJack instance = null;
    public Carte[] cartesJoueur;
    public Carte[] cartesCroupier;
    public int indexJoueur;
    public int indexCroupier;
    public int[] pointageJoueur;
    public int[] pointageCroupier;
    public boolean estTermine = false;
    public String message = "";
    public int drapeauFinPartie = 0;

    private BlackJack() {
        paquet = new JeuDeCarte();
    }

    /**
     * Permet d'avoir l'instance du jeu
     * @return l'instance du jeu BlackJack
     */
    public static BlackJack avoirInstance() {
        if (instance == null) {
            instance = new BlackJack();
        }

        return instance;
    }

    /**
     * Méthode qui calcule les points des joueurs.
     *
     * @return le pointage sans compter les as dans le premier index et le pointage en comptant les as dans le deuxième
     */
    public void calculerPoints() {
        pointageJoueur = new int[2];
        pointageCroupier = new int[2];
        calculerPointsCroupier();
        calculerPointsJoueur();
    }

    /**
     * Méthode qui calcule les points du joueur
     */
    private void calculerPointsJoueur() {
        boolean aUnAs = false;
        boolean aDeuxAs = false;
        for (int i = 0; i < cartesJoueur.length; i++) {
            if (cartesJoueur[i] == null && i != 0)
                break;
            if (cartesJoueur[i] == null && i == 0) {
                pointageJoueur[0] = 0;
            }
            if (cartesJoueur[i] == null && i == 0) {
                pointageJoueur[0] = 0;
                break;
            }
            if (cartesJoueur[i].numero >= 10)
                pointageJoueur[0] += 10;
            else
                pointageJoueur[0] += cartesJoueur[i].numero;
            if (cartesJoueur[i].numero == 1 && aUnAs) {
                aDeuxAs = true;
            }
            if (cartesJoueur[i].numero == 1)
                aUnAs = true;
        }
        if (aUnAs) {
            for (int i = 0; i < cartesJoueur.length; i++) {
                if (cartesJoueur[i] == null)
                    break;
                if (cartesJoueur[i].numero >= 10)
                    pointageJoueur[1] += 10;
                else if (cartesJoueur[i].numero == 1)
                    pointageJoueur[1] += 11;
                else
                    pointageJoueur[1] += cartesJoueur[i].numero;
            }
        } else if (aDeuxAs) {
            boolean estPremierAs = true;
            for (int i = 0; i < cartesJoueur.length; i++) {
                if (cartesJoueur[i] == null)
                    break;
                if (cartesJoueur[i].numero >= 10)
                    pointageJoueur[1] += 10;
                else if (cartesJoueur[i].numero == 1 && estPremierAs) {
                    pointageJoueur[1] += 11;
                    estPremierAs = false;
                } else
                    pointageJoueur[1] += cartesJoueur[i].numero;
            }
        } else
            pointageJoueur[1] = 50;
    }

    /**
     * Méthode qui calcule les points du croupier
     */
    private void calculerPointsCroupier() {
        boolean aUnAs = false;
        boolean aDeuxAs = false;
        for (int i = 0; i < cartesCroupier.length; i++) {
            if (cartesCroupier[i] == null && i != 0)
                break;
            if (cartesCroupier[i] == null && i == 0) {
                pointageCroupier[0] = 0;
                break;
            }
            if (cartesCroupier[i].numero >= 10)
                pointageCroupier[0] += 10;
            else
                pointageCroupier[0] += cartesCroupier[i].numero;
            if (cartesCroupier[i].numero == 1 && aUnAs) {
                aDeuxAs = true;
            }
            if (cartesCroupier[i].numero == 1)
                aUnAs = true;
        }
        if (aUnAs) {
            for (int i = 0; i < cartesCroupier.length; i++) {
                if (cartesCroupier[i] == null)
                    break;
                if (cartesCroupier[i].numero >= 10)
                    pointageCroupier[1] += 10;
                else if (cartesCroupier[i].numero == 1)
                    pointageCroupier[1] += 11;
                else
                    pointageCroupier[1] += cartesCroupier[i].numero;
            }
        } else if (aDeuxAs) {
            boolean estPremierAs = true;
            for (int i = 0; i < cartesCroupier.length; i++) {
                if (cartesCroupier[i] == null)
                    break;
                if (cartesCroupier[i].numero >= 10)
                    pointageCroupier[1] += 10;
                else if (cartesCroupier[i].numero == 1 && estPremierAs) {
                    pointageCroupier[1] += 11;
                    estPremierAs = false;
                } else
                    pointageCroupier[1] += cartesCroupier[i].numero;
            }
        } else
            pointageCroupier[1] = 50;
    }

    /**
     * Calculer les points, puis détermine le gagnant
     * Change le message pour savoir qui est le gagnant.
     */
    public void determinerGagnant() {

        int plusHautScoreJoueur;
        int plusHautScoreCroupier;
        estTermine = true;
        if (pointageJoueur[1] > 21)
            plusHautScoreJoueur = pointageJoueur[0];
        else
            plusHautScoreJoueur = pointageJoueur[1];
        if (pointageCroupier[1] > 21)
            plusHautScoreCroupier = pointageCroupier[0];
        else
            plusHautScoreCroupier = pointageCroupier[1];

        if (plusHautScoreJoueur > 21) {
            message = "Vous avez perdu :(";
            drapeauFinPartie = 1;
        }
        else if (plusHautScoreCroupier > 21) {
            message = "Vous avez Gagné :) !";
            drapeauFinPartie = 3;
        }
        else if (plusHautScoreJoueur > plusHautScoreCroupier) {
            message = "Vous avez Gagné :) !";
            drapeauFinPartie = 3;
        }
        else if (plusHautScoreJoueur == plusHautScoreCroupier) {
            message = "Égalité !";
            drapeauFinPartie = 2;
        }
        else if (plusHautScoreCroupier > plusHautScoreJoueur){
            message = "Vous avez perdu :(";
            drapeauFinPartie = 1;
        }
        else
            estTermine = false;
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
     * Permet de piger une carte.
     *
     * @return la carte sur le dessus du paquet.
     */
    public Carte pigerUneCarte() {
        return paquet.pigerUneCarte();
    }

    /**
     * Permet de reinitialiser le paquet de carte.
     */
    public void reinitialiser() {
        paquet = new JeuDeCarte();
        estCree = true;
        indexJoueur = 0;
        indexCroupier = 0;
        cartesJoueur = new Carte[9];
        cartesCroupier = new Carte[9];
        pointageJoueur = new int[2];
        pointageCroupier = new int[2];
        estTermine = false;
        message = "";
    }

    public void faireJouerCroupier() {
        if (!estTermine) {
            int pointsCroupier = 0;
            if (pointageCroupier[1] > 21)
                pointsCroupier = pointageCroupier[0];
            else
                pointsCroupier = pointageCroupier[1];
            while (pointsCroupier < 17 && indexCroupier < 12) {
                Carte nouvelleCarte = pigerUneCarte();
                if (nouvelleCarte != null) {
                    jouerPourCroupier(nouvelleCarte);
                    if (pointageCroupier[1] > 21)
                        pointsCroupier = pointageCroupier[0];
                    else
                        pointsCroupier = pointageCroupier[1];
                }
            }
            estTermine = true;
            calculerPoints();
            determinerGagnant();
        }
    }

    /**
     * Méthode qui joue pour un joueur (ajoute la carte au tableau de cartes, compte les points.
     *
     * @param carte la carte qui a été pigé
     */
    public void jouerPourJoueur(Carte carte) {
        if (!estTermine) {
            if (carte != null && indexJoueur < 12) {
                cartesJoueur[indexJoueur++] = carte;
                calculerPoints();
                if (pointageJoueur[0] == 21 || pointageJoueur[1] == 21)
                    faireJouerCroupier();
                if (pointageJoueur[0] > 21) {
                    estTermine = true;
                }
            }
        }
    }

    /**
     * Méthode qui joue pour le croupier (ajoute la carte au tableau de cartes, compte les points.
     *
     * @param carte la carte qui a été pigé
     */
    public void jouerPourCroupier(Carte carte) {
        if (carte != null && indexCroupier < 12) {
            cartesCroupier[indexCroupier++] = carte;
            calculerPoints();
        }
    }

    @Override
    public void Initialiser(int seed) {

    }

}
