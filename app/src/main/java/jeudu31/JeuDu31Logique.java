package jeudu31;

import java.util.ArrayList;
import java.util.List;

import utilitaire.Carte;
import utilitaire.JeuAvecCartes;
import utilitaire.JeuDeCarte;
import utilitaire.JoueurSingleton;

/**
 * Created by Jean-michel Lavoie on 15/02/2016.
 */
public class JeuDu31Logique extends JeuAvecCartes {
    List<JoueurDu31> lstJoueurs;
    List<JoueurDu31> lstGagnants;
    JoueurSingleton idJoueur;
    JoueurDu31 perdant;
    float sommeArgent;
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
        int nbrJoueur=3;
        lstJoueurs = new ArrayList<JoueurDu31>();
        paquetTémoin = new ArrayList<Carte>();
        idJoueur=JoueurSingleton.getInstance();
        recommencer(nbrJoueur);
        simulerUneManche();
    }

    /**
     * Mise une somme et les joueurs fictifs misent la même somme.
     * @param somme Valeur du textbox.
     */
    public void miserUneSomme(float somme)
    {
        try {
            sommeArgent = idJoueur.getMontant(somme);
            sommeArgent += sommeArgent * lstJoueurs.size();
        }
        catch(Exception e){}//Le montant recu est invalide.

    }
    public void simulerUneManche()
    {
        miserUneSomme(75);
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
        lstJoueurs.clear();
        cogneur=-1;
        sommeArgent=0;
        joueurCourant=0;
        perdant=null;
        paquetTémoin.clear();
        paquet= new JeuDeCarte();
        for(int i=0;i<nbrJoueurs;i++)
            lstJoueurs.add(new JoueurDu31());
        lstJoueurs.get(0).déterminerNom(idJoueur.getNom());
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
     * Facilite la prise de décision.
     */
    public void ordonnerCroissantJoueursSelonValeur()
    {
        ArrayList<JoueurDu31> joueurTémoin = new ArrayList<JoueurDu31>();
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
        ajouterMontant();
    }

    /**
     * Ajoute le montant dans le lot divisé par le nbr de gagnant.
     */
    public void ajouterMontant()
    {
        for(JoueurDu31 joueur : lstJoueurs)
        {
            if(joueur.nom==idJoueur.getNom())
                idJoueur.AddMontant(sommeArgent/lstGagnants.size());
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
