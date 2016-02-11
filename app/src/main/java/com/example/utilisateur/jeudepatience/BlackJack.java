package com.example.utilisateur.jeudepatience;

/**
 * Created by Mikael Andries-Gounant on 29/01/2016.
 */
public class BlackJack{

    JeuDeCarte paquet;
    boolean estCree = false;
    private static BlackJack instance = null;
    Carte[] cartesJoueur;
    Carte[] cartesCroupier;
    int indexJoueur;
    int indexCroupier;
    int[] pointageJoueur;
    int[] pointageCroupier;

    private  BlackJack()
    {
        paquet = new JeuDeCarte();
    }
    public static BlackJack avoirInstance(){
        if (instance == null){
            instance = new BlackJack();
        }

        return instance;
    }
    /**
     * Méthode qui calcule les points des joueurs.
     * @return le pointage sans compter les as dans le premier index et le pointage en comptant les as dans le deuxième
     */
    void calculerPoints()
    {
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
    private void calculerPointsCroupier(){
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
     * @return 0 si le joueur gagne 1 si le croupier gagne et 2 si c'est un match nul
     */
    int determinerGagnant()
    {
        int plusHautScoreJoueur;
        int plusHautScoreCroupier;

        if (pointageJoueur[1] > 21)
            plusHautScoreJoueur = pointageJoueur[0];
        else
        plusHautScoreJoueur = pointageJoueur[1];
        if (pointageCroupier[1] > 21)
            plusHautScoreCroupier = pointageCroupier[0];
        else
        plusHautScoreCroupier = pointageCroupier[1];
        if (plusHautScoreCroupier > 21)
            return 0;
        else if (plusHautScoreJoueur == plusHautScoreCroupier)
            return 2;
        else if (plusHautScoreJoueur > plusHautScoreCroupier)
            return 0;
        else
            return 1;
    }

    /**
     * Méthode qui permet de trouver l'id d'une image pour une carte
     * @param nom le champ nom de l'objet carte
     * @return l'id de la ressource d'image correspondant à la carte
     */
    int trouverIdCarte(String nom)
    {
        return paquet.trouverIdCarte(nom);
    }

    /**
     * Permet de piger une carte.
     * @return la carte sur le dessus du paquet.
     */
    Carte pigerUneCarte()
    {
        return paquet.PigerUneCarte();
    }

    /**
     * Permet de reinitialiser le paquet de carte.
     */
    void reinitialiser(){
        paquet = new JeuDeCarte();
        estCree = true;
        indexJoueur = 0;
        indexCroupier = 0;
        cartesJoueur = new Carte[9];
        cartesCroupier = new Carte[9];
        pointageJoueur = new int[2];
        pointageCroupier = new int[2];
    }
    void faireJouerCroupier(){
        int pointsCroupier = 0;
        if (pointageCroupier[1] > 21)
            pointsCroupier = pointageCroupier[0];
        else
            pointsCroupier = pointageCroupier[1];
        while(pointsCroupier < 17 && indexCroupier < 12){
            Carte nouvelleCarte = pigerUneCarte();
            if (nouvelleCarte)
        }
    }

    /**
     * Méthode qui joue pour un joueur (ajoute la carte au tableau de cartes, compte les points.
     *
     * @param carte la carte qui a été pigé
     */
    void jouerPourJoueur(Carte carte) {
        if (carte != null && indexJoueur < 12) {
            cartesJoueur[indexJoueur++] = carte;
            if (pointageJoueur[0] == 21 || pointageJoueur[1] == 21)
                //faireJouerCroupier();
            if (pointageJoueur[0] > 21) {
                //Toast.makeText(getApplicationContext(), "Vous avez perdu :(", Toast.LENGTH_LONG).show();
                //estTermine = true;
            }
            calculerPoints();
        }
    }
    /**
     * Méthode qui joue pour le croupier (ajoute la carte au tableau de cartes, compte les points.
     *
     * @param carte la carte qui a été pigé
     */
    void jouerPourCroupier(Carte carte) {
        if (carte != null && indexCroupier < 12) {
            cartesCroupier[indexCroupier++] = carte;
            if (pointageCroupier[0] == 21 || pointageCroupier[1] == 21)
                //faireJouerCroupier();
                if (pointageCroupier[0] > 21) {
                    //Toast.makeText(getApplicationContext(), "Vous avez perdu :(", Toast.LENGTH_LONG).show();
                    //estTermine = true;
                }
            calculerPoints();
        }
    }
}
