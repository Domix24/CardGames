package BlackJack;

import Utilitaire.Carte;
import Utilitaire.JeuDeCarte;

/**
 * Created by Mikael Andries-Gounant on 29/01/2016.
 */
public class BlackJack{

    JeuDeCarte paquet;
    boolean estCree = false;
    private static BlackJack instance = null;
    Carte[] cartesJoueur;
    Carte[] cartesCroupier;
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
     * Méthode qui calcule les points d'une main.
     * @param cartes les cartes pour calculer les points
     * @return le pointage sans compter les as dans le premier index et le pointage en comptant les as dans le deuxième
     */
    int[] calculerPoints(Carte[] cartes)
    {
        int[] pointage = new int[2];
        boolean aUnAs = false;
        boolean aDeuxAs = false;
        for (int i = 0; i < cartes.length; i++){
            if (cartes[i] == null && i != 0)
                break;
            if (cartes[i] == null && 1 == 0){
                pointage[0] = 0;
                break;
            }
            if (cartes[i].numero >= 10)
                pointage[0] += 10;
            else
                pointage[0] += cartes[i].numero;
            if (cartes[i].numero == 1 && aUnAs){
                aDeuxAs = true;
            }
            if (cartes[i].numero == 1)
                aUnAs = true;
        }
        if (aUnAs){
            for (int i = 0; i < cartes.length; i++){
                if (cartes[i] == null)
                    break;
                if (cartes[i].numero >= 10)
                    pointage[1] += 10;
                else if(cartes[i].numero == 1)
                    pointage[1] += 11;
                else
                    pointage[1] += cartes[i].numero;
            }
        }
        else if (aDeuxAs){
            boolean estPremierAs = true;
            for (int i = 0; i < cartes.length; i++){
                if (cartes[i] == null)
                    break;
                if (cartes[i].numero >= 10)
                    pointage[1] += 10;
                else if(cartes[i].numero == 1 && estPremierAs)
                {
                    pointage[1] += 11;
                    estPremierAs = false;
                }
                else
                    pointage[1] += cartes[i].numero;
            }
        }
        else
        pointage[1] = 50;

        return pointage;
    }

    /**
     * Calculer les points, puis détermine le gagnant
     * @param cartesJoueur les cartes du joueur
     * @param cartesCroupier les cartes du croupier
     * @return 0 si le joueur gagne 1 si le croupier gagne et 2 si c'est un match nul
     */
    int determinerGagnant(Carte[] cartesJoueur, Carte[] cartesCroupier)
    {
        int[] pointageJoueur = calculerPoints(cartesJoueur);
        int[] pointageCroupier = calculerPoints(cartesCroupier);
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
    }
}
