package jeudu31;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
    int nbrTour;
    int seed;
    List<Carte> paquetTémoin;


    public void Initialiser(int seed) {
        this.seed=seed;
    }

    /**
     *
     */
    public JeuDu31Logique()
    {
        int nbrJoueur=3;
        lstJoueurs = new ArrayList<JoueurDu31>();
        paquetTémoin = new ArrayList<Carte>();
        idJoueur=JoueurSingleton.getInstance();
        recommencer(nbrJoueur);
        nbrTour=-1;
    }

    /**
     * Mise une somme et les joueurs fictifs misent la même somme.
     * @param somme Valeur du textbox.
     */
    public boolean miserUneSomme(float somme)
    {
        try {
            sommeArgent = idJoueur.getMontant(somme);
            sommeArgent = sommeArgent * lstJoueurs.size();
            return true;
        }
        catch(Exception e){return  false;}//Le montant recu est invalide.

    }

    /**
     * enleve une carte de valeur différente.
     * @param valeur
     */
    public void enleverValeurDifférente(int valeur)
    {
        boolean valeurIntrouvé=true;
        for(Carte ct : lstJoueurs.get(joueurCourant).jeuEnMain)
        {
            if(ct.numero!=valeur) {
                valeurIntrouvé=false;
                jouerUnChoix(ct);
                break;
            }
        }
        //Si elles sont tous de même valeur.
        if(valeurIntrouvé)
        {
            Carte ct = lstJoueurs.get(joueurCourant).jeuEnMain.get(0);
            jouerUnChoix(ct);
        }
    }
    /**
     *
     * @return Retourne la carte si l'opération s'est bien déroulée.
     */
    public Carte pigerUneCarteAVue()
    {
        Carte ct=null;
        if(paquetTémoin.size()>0 && lstJoueurs.get(joueurCourant).jeuEnMain.size()==3) {
            ct=paquetTémoin.get(paquetTémoin.size() - 1);
            lstJoueurs.get(joueurCourant).ajouterCarteALaMain(ct);
            paquetTémoin.remove(paquetTémoin.size() - 1);
        }
        return ct;
    }
    /**
     *  Remet à 0 toutes les variables qui sont spécifiques à une manche.
     * @param nbrJoueurs Nombre de joueurs qui vont jouer dans la manche.
     */
    public void recommencer(int nbrJoueurs)
    {
        lstGagnants = new ArrayList<JoueurDu31>();
        lstJoueurs=new ArrayList<JoueurDu31>();
        cogneur=-1;
        sommeArgent=0;
        joueurCourant=0;
        perdant=null;
        nbrTour=-1;
        paquetTémoin=new ArrayList<Carte>();
        paquet= new JeuDeCarte();
        for(int i=0;i<nbrJoueurs;i++)
            lstJoueurs.add(new JoueurDu31());
        for(JoueurDu31 joueur: lstJoueurs)
            for(int i=0;i<nbrJoueurs;i++)
                joueur.ajouterCarteALaMain(paquet.pigerUneCarte());
        lstJoueurs.get(0).déterminerNom(idJoueur.getNom());
        for(int i=1;i<nbrJoueurs;i++)
            lstJoueurs.get(i).déterminerNom("ordinateur"+i);
    }

    /**
     * Pige une carte pour le joueur courant.
     * @return retourne une carte si le mouvement s'est bien déroulé.
     */
    public Carte jouerUnePige()
    {
        if(lstJoueurs.get(joueurCourant).Cogne==false && paquet.size()>0 && lstJoueurs.get(joueurCourant).jeuEnMain.size()==3) {
            //SI le paquet principal est vide.
            if (paquet.size() == 0) {
                for (int i = 0; i < paquetTémoin.size(); i++)
                    paquet.add(paquetTémoin.remove(0));
                paquet.mélangerPaquet();
            }
            Carte ct=paquet.pigerUneCarte();
            return lstJoueurs.get(joueurCourant).ajouterCarteALaMain(ct);
        }
        return null;
    }

    /**
     * Le joueur choisie la carte qu'il ne veut pas garder,
     * @param ct la carte à enlever de sa main.
     * @return retourne la carte si le mouvement s'est bien déroulé.
     */
    public Carte jouerUnChoix(Carte ct)
    {
        if(lstJoueurs.get(joueurCourant).Cogne==false && lstJoueurs.get(joueurCourant).jeuEnMain.size()==4)
            if(lstJoueurs.get(joueurCourant).enleverCarteALaMain(ct)) {
                paquetTémoin.add(ct);
                return ct;
            }
        return null;
    }

    /**
     * Calcul pour le joueur s'il a 31
     * @return retourne vrai s ic la fin sinon faux
     */
    public boolean calculerSiFin()
    {
        if(joueurCourant==0 && lstJoueurs.get(joueurCourant).retournePlusHauteValeur()==31)
            return true;
        return false;
    }
    /**
     * Annonce son intention de finir la manche courante.
     */
    public void annoncerFinDeManche()
    {
        lstJoueurs.get(joueurCourant).cogner();
        cogneur=joueurCourant;
        nbrTour=lstJoueurs.size()-1;
    }

    /**
     *  Passer au joueur suivant.
     */
    public void joueurSuivant()
    {
        if(joueurCourant+1<lstJoueurs.size())
            joueurCourant++;
        else
            joueurCourant=0;
        if(nbrTour>0)
            nbrTour--;
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

    }

    /**
     * Ajoute le montant dans le lot divisé par le nbr de gagnant.
     */
    public void ajouterMontant()
    {
        for(JoueurDu31 joueur : lstGagnants)
        {
            if(joueur.nom==idJoueur.getNom())
                idJoueur.addMontant(sommeArgent / lstGagnants.size());
        }
    }
    /**
     * Prend le joueur avec la plus basse valeur.
     */
    public void déterminePerdant()
    {
        if(!lstGagnants.contains(lstJoueurs.get(0)))
            perdant=lstJoueurs.get(0);
        if(perdant.Cogne && perdant.nom!="")
            idJoueur.getMontant(sommeArgent/lstJoueurs.size());
        else if(perdant.Cogne && perdant.nom.contains("ordinateur"))
            sommeArgent+=sommeArgent/lstGagnants.size();
        ajouterMontant();

    }
    /**
     * Retourne l'id de la carte (ex: pour pouvoir afficher l'image associée à la carte)
     * @param nom Le nom de l'objet carte
     * @return L'ID de la carte
     */
    public int trouverIdCarte(String nom) {
        return paquet.trouverIdCarte(nom);
    }

    /**
     * Enlève une carte qui est différente de la sorte cible.
     * @param sorte la sorte que nous voulnos garder.
     */
    public void enleverSorteDifférente(int sorte) {
        boolean sorteIntrouvé=true;
        for(Carte ct:lstJoueurs.get(joueurCourant).jeuEnMain)
        {
            if(ct.sorte!=sorte) {
                jouerUnChoix(ct);
                sorteIntrouvé=false;
                break;
            }
        }
        //Si aucune des cartes n'est de sorte différente alors enlever la plus faible.
        if(sorteIntrouvé)
        {
            Carte ctTemp=null;
            for(Carte ct:lstJoueurs.get(joueurCourant).jeuEnMain) {
                if(ctTemp==null)
                    ctTemp = ct;
                if (ctTemp != null && ctTemp.numero > ct.numero) {
                    ctTemp = ct;
                }
            }
            jouerUnChoix(ctTemp);
        }
    }

    /**
     * Joue le tour  des opposants.
     * 3 cartes
     si 2 cartes pareil
     aller chercher pour la 3e carte.
     sinon si valeur couleur >= 16
     alors aller pour plus de valeur de cette couleur.
     sinon prendre une carte aléatoire et la remplacer par la nouvelle carte.
     * @return true si on continue false si fin.
     */
    public boolean jouerOrdinateurs()
    {
        if(nbrTour!=0) {
            //Pour chaque IA
            for (int i = 1; i < lstJoueurs.size(); i++) {
                joueurSuivant();
                if(nbrTour==0)
                    return false;
                if (cogneur != joueurCourant) {
                    //Annonce une fin de manche sinon il joue.
                    if (lstJoueurs.get(joueurCourant).retournePlusHauteValeur() >= 21 && cogneur==-1)
                        annoncerFinDeManche();
                    else {
                        //Si 2 cartes ou + de la même valeurs
                        int memeCarte = lstJoueurs.get(joueurCourant).détecterMêmeValeur();
                        int noMemeCarte = lstJoueurs.get(joueurCourant).chercheMêmeValeur();
                        if (memeCarte > 0) {
                            iAJouerValeur(noMemeCarte);
                        }//sinon si la somme des cartes d'une couleur >=16
                        else if (lstJoueurs.get(joueurCourant).retournePlusHauteValeur() >= 16) {
                            iAJouerCouleur();
                        }//sinon prendre une carte aléatoire et la remplacer par une autre carte aléatoirement
                        //prise soit dans la paquet soit une carte à vue s'il n'y a aucune concordance.
                        else {
                            Random rng = new Random(seed);
                            boolean résultat = rng.nextBoolean();
                            if (résultat) {
                                if (pigerUneCarteAVue() == null)
                                    jouerUnePige();
                            } else
                                jouerUnePige();
                            //Recommencer les vérifications après la pige.
                            iAChoixFinal();
                        }
                        if (lstJoueurs.get(joueurCourant).retournePlusHauteValeur() == 31) {
                            nbrTour = 0;
                            return false;
                        }
                    }
                }
            }
            joueurSuivant();
        }
        else
        {
            return false;
        }
        return true;
    }

    /**
     * Décision ultime de IA
     */
    public void iAChoixFinal()
    {
        //Si 2 cartes ou + de la même valeurs
        int memeCarte=lstJoueurs.get(joueurCourant).chercheMêmeValeur();
        if(memeCarte>0)
        {
            enleverValeurDifférente(memeCarte);
        }//sinon si la somme sdes cartes d'une couleur >=16
        else if(lstJoueurs.get(joueurCourant).retournePlusHauteValeur()>=16)
        {
            int sorte=lstJoueurs.get(joueurCourant).retournePlusHauteSorte();
            enleverSorteDifférente(sorte);
        }//Si tjrs aucune possbilité on s'en remet au random.
        else
        {
            Random rng = new Random(seed);
            int position=rng.nextInt(3);
            jouerUnChoix(lstJoueurs.get(joueurCourant).jeuEnMain.get(position));
        }
    }
    /**
     * Joue en supposant que nous avons une somme de 16
     */
    public void iAJouerCouleur()
    {
        int sorte = lstJoueurs.get(joueurCourant).retournePlusHauteSorte();
        if(paquetTémoin.size()>0) {
            if (paquetTémoin.get(paquetTémoin.size() - 1).sorte == sorte) {
                pigerUneCarteAVue();
                enleverSorteDifférente(sorte);
            } else {
                jouerUnePige();
                enleverSorteDifférente(sorte);
            }
        }
        else {
            jouerUnePige();
            enleverSorteDifférente(sorte);
        }
    }

    /**
     * Joue e nsupposant que nous avons des même valeur.
     * @param memeCarte valeur carte cible
     */
    public void iAJouerValeur(int memeCarte) {
        if (paquetTémoin.size() > 0) {
            if (paquetTémoin.get(paquetTémoin.size() - 1).numero == memeCarte) {
                pigerUneCarteAVue();
                enleverValeurDifférente(memeCarte);
            } else {
                jouerUnePige();
                enleverValeurDifférente(memeCarte);
            }
        } else {
            jouerUnePige();
            enleverValeurDifférente(memeCarte);
        }
    }
}
