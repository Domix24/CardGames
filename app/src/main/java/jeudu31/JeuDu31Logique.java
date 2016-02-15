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
        lstJoueurs.add(new Joueur());
        paquetTémoin = new ArrayList<Carte>();
        simulerUneManche();
    }
    public void simulerUneManche()
    {
        int nbrTour=lstJoueurs.size()-1;
        while(perdant==null && nbrTour!=0) {
            if(cogneur!=-1)
                nbrTour--;
            jouerUnePige();
            pigerUneCarteAVue();
            jouerUnChoix(lstJoueurs.get(joueurCourant).JeuEnMain.get(0));
            if(lstJoueurs.get(joueurCourant).plafonne()) {
                détermineGagnant();
                déterminePerdant();
                nbrTour=0;
            }
            if(lstJoueurs.get(joueurCourant).Cogne)
                annoncerFinDeManche();
            if(cogneur!=-1 && nbrTour==0)
            {
                détermineGagnant();
                déterminePerdant();
            }
            joueurSuivant();
        }
        recommencer(lstJoueurs.size());
    }

    /**
     *
     * @return Retourne true si l'opération s'est bien déroulée.
     */
    public boolean pigerUneCarteAVue()
    {
        if(paquetTémoin.size()>0) {
            lstJoueurs.get(joueurCourant).ajouterCarteALaMain(paquetTémoin.get(paquetTémoin.size() - 1));
            paquetTémoin.remove(paquetTémoin.size() - 1);
            return true;
        }
        return false;
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
        if(lstJoueurs.get(joueurCourant).Cogne==false)
            if(lstJoueurs.get(joueurCourant).ajouterCarteALaMain(paquet.pigerUneCarte()))
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
        if(lstJoueurs.get(joueurCourant).Cogne==false)
            if(lstJoueurs.get(joueurCourant).enleverCarteALaMain(ct)) {
                paquetTémoin.add(ct);
                return true;
            }
        return false;
    }

    /**
     * Annonce son intention de finir la manche courante.
     */
    public void annoncerFinDeManche()
    {
        lstJoueurs.get(joueurCourant).cogner();
        cogneur=joueurCourant;
    }

    /**
     *  Passer au joueur suivant.
     */
    public void joueurSuivant()
    {
        if(joueurCourant+1<lstJoueurs.size()-1)
            joueurCourant++;
        else
            joueurCourant=0;
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
