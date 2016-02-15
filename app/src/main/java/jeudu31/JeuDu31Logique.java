package jeudu31;

import java.util.ArrayList;
import java.util.List;

import utilitaire.Carte;
import utilitaire.JeuAvecCartes;
import utilitaire.JeuDeCarte;

/**
 * Created by Jean-michel Lavoie on 15/02/2016.
 */
public class JeuDu31Logique extends JeuAvecCartes {
    List<Joueur> lstJoueurs;
    List<Joueur> lstGagnants;
    Joueur perdant;
    int joueurCourant;
    int cogneur;
    List<Carte> paquetTémoin;
    @Override
    public void Initialiser(int seed) {
        
    }

    /**
     *
     */
    public void JeuDu31Logique()
    {
        cogneur=-1;
        joueurCourant=0;
        lstJoueurs = new ArrayList<Joueur>();
        lstJoueurs.add(new Joueur());
        paquetTémoin = new ArrayList<Carte>();
    }

    /**
     *  Remet à 0 toutes les variables qui sont spécifiques à une manche.
     * @param nbrJoueurs Nombre de joueurs qui vont jouer dans la manche.
     */
    public void recommencer(int nbrJoueurs)
    {
        lstGagnants.clear();
        cogneur=-1;
        joueurCourant=0;
        perdant=null;
        lstJoueurs.clear();
        paquetTémoin.clear();
        paquet= new JeuDeCarte();
        for(int i=0;i<nbrJoueurs;i++)
            lstJoueurs.add(new Joueur());
    }

    /**
     * Pige une carte pour le joueur courant.
     * @return retourne vrai si le mouvement s'est bien déroulé.
     */
    public boolean jouerUnePige()
    {
        if(lstJoueurs.get(joueurCourant).Cogne)
            if(lstJoueurs.get(joueurCourant).ajouterCarteALaMain(paquet.PigerUneCarte()))
                return true;
        return  false;
    }

    /**
     * Le joueur choisie la carte qu'il ne veut pas garder,
     * @param ct la carte à enlever de sa main.
     * @return retourne vrai si le mouvement s'est bien déroulé.
     */
    public boolean jouerUnChoix(Carte ct)
    {
        if(lstJoueurs.get(joueurCourant).Cogne)
            if(lstJoueurs.get(joueurCourant).enleverCarteALaMain(ct)) {
                paquetTémoin.add(ct);
                return true;
            }
        return false;
    }

    /**
     * Annonce son intentino de finir la manche courante.
     */
    public void annoncerFinDeManche()
    {
        lstJoueurs.get(joueurCourant).cogner();
        cogneur=joueurCourant;
    }

    /**
     *  PAsser au joueur suivant.
     */
    public void joueurSuivant()
    {
        joueurCourant++;
    }

    /**
     * Ordonne les joueurs selon leur score actuel.
     */
    public void ordonnerCroissantJoueursSelonValeur()
    {
        ArrayList<Joueur> joueurTémoin = new ArrayList<Joueur>();
        if(lstJoueurs.size()>1) {
            for (int i = 0; i < lstJoueurs.size(); i++)
                joueurTémoin.add(lstJoueurs.get(i));
            for (int i = 0; i < lstJoueurs.size(); i++) {
                for (int u = 0; u < lstJoueurs.size(); u++) {
                    if (joueurTémoin.get(i).retournePlusHauteValeur() < joueurTémoin.get(u).retournePlusHauteValeur()) {
                        joueurTémoin.set(u, joueurTémoin.get(i));
                        joueurTémoin.set(i, lstJoueurs.get(u));
                        lstJoueurs.set(i, joueurTémoin.get(i));
                        lstJoueurs.set(u, joueurTémoin.get(u));
                    }
                }
            }
        }
    }

    /**
     * Détermine le ou les joueurs avec la plus haute valeur.
     */
    public void détermineGagnant()
    {
        ordonnerCroissantJoueursSelonValeur();
        int plusHauteValeur=lstJoueurs.get(lstJoueurs.size()-1).retournePlusHauteValeur();
        lstGagnants.add(lstJoueurs.get(lstJoueurs.size() - 1));
        for(int i=lstJoueurs.size()-2;i>0;i--)
            if(lstJoueurs.get(i).retournePlusHauteValeur()==plusHauteValeur) {
                lstGagnants.add(lstJoueurs.get(i));
            }
    }

    /**
     * Prend le joueur avec le plus basse valeur.
     */
    public void déterminePerdant()
    {
        if(!lstGagnants.contains(lstJoueurs.get(0)))
            perdant=lstJoueurs.get(0);
    }
}
