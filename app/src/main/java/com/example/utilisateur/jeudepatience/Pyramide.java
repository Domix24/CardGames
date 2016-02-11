package com.example.utilisateur.jeudepatience;

import java.util.ArrayList;
import java.util.List;

/**
 * Créer par Jean-Michel Lavoie  on 29/01/2016.
 */
public class Pyramide extends JeuAvecCartes{
    public ArrayList<Carte[]> lstJeu = new ArrayList<>();
    List<Carte> lstStock=new ArrayList<Carte>();
    int  nbUtilisé;
    int nbGagnes;
    int nbPerdus;
    //Forme de la pyramide
    //R0CO
    //R1C0,R1C1
    //R2C0,R2C1,R2C2
    //R3C0,R3C1,R3C2,R3C3
    //R4C0,R4C1,R4C2,R4C3,R4C4
    //R5C0,R5C1,R5C2,R5C3,R5C4,R5C5
    //R6C0,R6C1,R6C2,R6C3,R6C4,R6C5,R6C6
    //R7C0
    public Pyramide()
    {
        recommencer();
    }

    /**
     * Appelé quand la partie est déjà détermiée gagnée pour recommencer la partie
     * @return Le nombre de parties gagnées cette session
     */
    public int gagne()
    {
        recommencer();
        return ++nbGagnes;
    }

    /**
     * Appelé quand la partie est déjà détermiée perdue pour recommencer la partie
     * @return Le nombre de parties perdues cette session
     */
    public int perd()
    {
        recommencer();
        return ++nbPerdus;
    }

    /**
     * @return Nombre de parties gagnées
     */
    public int getNbGagnes()
    {
        return  nbGagnes;
    }

    /**
     * @return Nombre de parties perdues
     */
    public int getNbPerdus()
    {
        return nbPerdus;
    }

    /**
     * Met à 0 toutes les listes et les repopule.
     */
    public void recommencer()
    {
        nbUtilisé=0;
        lstJeu.clear();
        lstStock.clear();
        paquet = new JeuDeCarte();

        remplirPyramide();
        remplirStock();
    }

    /**
     * Initialise le tableau de la pyramide
     */
    public void remplirPyramide()
    {
        lstJeu.add(new Carte[1]);
        lstJeu.add(new Carte[2]);
        lstJeu.add(new Carte[3]);
        lstJeu.add(new Carte[4]);
        lstJeu.add(new Carte[5]);
        lstJeu.add(new Carte[6]);
        lstJeu.add(new Carte[7]);
        lstJeu.add(new Carte[24]);
        for(int i=0;i<7;i++) {
            if(i==0)
                lstJeu.get(0)[i] = paquet.PigerUneCarte();
            if(i<=1)
                lstJeu.get(1)[i] = paquet.PigerUneCarte();
            if(i<=2)
                lstJeu.get(2)[i] = paquet.PigerUneCarte();
            if(i<=3)
                lstJeu.get(3)[i] = paquet.PigerUneCarte();
            if(i<=4)
                lstJeu.get(4)[i] = paquet.PigerUneCarte();
            if(i<=5)
                lstJeu.get(5)[i] = paquet.PigerUneCarte();
            if(i<=6) {
                lstJeu.get(6)[i] = paquet.PigerUneCarte();
                lstJeu.get(6)[i].disponible=true;
            }
        }
    }

    /**
     * @return La carte sur le dessus du waste
     */
    public Carte sélectionnerDernierAuWaste()
    {
        int i=24;
        do {
            i--;
        }while(i>0 && lstJeu.get(7)[i]==null);
       return lstJeu.get(7)[i];
    }

    /**
     * Envoyer la carte du dessus du stock sur le dessus du waste
     */
    public void transfererStockAWaste()
    {
        if (lstStock.size() > 0) {
            try {
                lstJeu.get(7)[lstJeu.get(7).length] = lstStock.get(lstStock.size()-1);
                lstJeu.get(7)[lstJeu.get(7).length].disponible = true;
            }
            catch (Exception e) {
                lstJeu.get(7)[0] = lstStock.get(lstStock.size()-1);
                lstJeu.get(7)[0].disponible = true;
            }

            lstStock.remove(lstStock.size() - 1);
        }
    }

    /**
     * @return Le nombre de cartes dans le stock
     */
    public int cartesRestantes()
    {
        return lstStock.size();
    }

    /**
     * @param ct Ajoute l'objet carte au stock
     */
    public void ajouterCarteAuStock(Carte ct)
    {
        if(!lstStock.contains(ct))
            lstStock.add(ct);
    }

    /**
     * Remplir le stock avec les 24 cartes restantes après avoir mis les premières dans la pyramide
     */
    private void remplirStock()
    {
        for(int i=0;i<24;i++)
            lstStock.add(paquet.PigerUneCarte());
    }

    /**
     * @param ct Enlever cet objet carte au stock
     */
    public void enleverCarteAuStock(Carte ct)
    {
        if(lstStock.contains(ct))
            lstStock.remove(ct);
    }

    /**
     * Est utilisé pour la carte qui se joue seule (le roi).
     * @param rangée1 Coordonnée du roi
     * @param colonne1 Coordonnée du roi
     * @return
     */
    public boolean jouerDeuxCartes(int rangée1,int colonne1)
    {
        refreshDisponibilite();
        //Check si le mouvement est invalide.
        if(lstJeu.get(rangée1)[colonne1]!=null)
            if(lstJeu.get(rangée1)[colonne1].disponible==false)
                return false;

        //1 carte du Stock
        if(rangée1==7)
        {
            for(int i=23;i>=0;i--)
            {
                if(lstJeu.get(rangée1)[i]!=null)
                    if(lstJeu.get(rangée1)[i].numero==13) {
                        lstJeu.get(rangée1)[i] = null;
                        nbUtilisé++;
                        return true;
                    }
            }
        }
        //1 carte de la table de jeu
        if(lstJeu.get(rangée1)[colonne1]!=null)
        if(lstJeu.get(rangée1)[colonne1].numero==13)
        {
            lstJeu.get(rangée1)[colonne1]=null;
            nbUtilisé++;
            return true;
        }
        return  false;
    }

    /**
     * Victoire si 52 cartes sont utilisées.
     * Défaites si dans la liste de comparaison aucune somme ne donne 13.
     */
    public void checkConditionDeFin()
    {
        boolean continuer=false;
        List<Carte> lstComparaison = new ArrayList<Carte>();
        //Condition de victoire
        if(nbUtilisé==52) {
            gagne();return;
        }
        //Condition de défaite
        for(int i=0;i<6;i++)
            for(int u=0;u<=i;u++)
            {
                if(lstJeu.get(i)[u]!=null && lstJeu.get(i)[u].disponible==true)
                    lstComparaison.add(lstJeu.get(i)[u]);
            }
        if(sélectionnerDernierAuWaste()!=null)
            lstComparaison.add(sélectionnerDernierAuWaste());
        for(int i=0;i<lstStock.size();i++)
            lstComparaison.add(lstStock.get(i));
        for(int i=0;i<lstComparaison.size();i++)
            for(int u=0;u<lstComparaison.size();u++)
            {
                if(i!=u) {
                    if (lstComparaison.get(i).numero + lstComparaison.get(u).numero == 13)
                        continuer = true;
                    if(lstComparaison.get(i).numero==13)
                        continuer=true;
                }
            }
        if(!continuer)
            perd();
    }

    /**
     * Met à jour la liste des cartes qui sont jouables.
     */
    public void refreshDisponibilite()
    {
        for(int i=0;i<6;i++)
            for(int u=0;u<=i;u++)
            {
                lstJeu.get(i)[u].disponible = true;
                if(lstJeu.get(i+1)[u]!=null)
                    lstJeu.get(i)[u].disponible = false;

                if (lstJeu.get(i+1)[u+1]!=null)
                    lstJeu.get(i)[u].disponible = false;
            }
        for(int i = lstJeu.get(7).length - 2;i>=0;i--)
        {
            try {
                if (lstJeu.get(7)[i + 1] != null)
                        lstJeu.get(7)[i].disponible = false;
                else
                    if(lstJeu.get(7)[i] != null)
                        lstJeu.get(7)[i].disponible =true;
            }
            //Exception si aucune carte après.
            catch (Exception e){lstJeu.get(7)[i].disponible = true;}
        }
    }

    /**
     * Est utilisé pour jouer 2 cartes en utilisant des coordonnées. Si le déplacement est valide,
     * enlève les deux cartes de la pyramide
     * @param rangée1 Coordonnée de la première carte
     * @param colonne1 Coordonnée de la première carte
     * @param rangée2 Coordonnée de la deuxième carte
     * @param colonne2 Coordonnée de la deuxième carte
     * @return retourne vrai si le déplacement est valide.
     */
    public boolean jouerDeuxCartes(int rangée1,int colonne1,int rangée2,int colonne2)
    {
        refreshDisponibilite();
        //Check si le mouvement est invalide.
        if(lstJeu.get(rangée1)[colonne1]!=null)
            if(lstJeu.get(rangée1)[colonne1].disponible==false)
                return false;
        if(lstJeu.get(rangée2)[colonne2]!=null)
            if(lstJeu.get(rangée2)[colonne2].disponible==false)
                return false;

        //Valeur à 7 indique que c'est une carte dans le waste.
        if(rangée1==7)
        {
            for(int i=23;i>=0;i--)
            {
                if(lstJeu.get(rangée1)[i]!=null && lstJeu.get(rangée2)[colonne2]!=null)
                    if(lstJeu.get(rangée1)[i].numero+lstJeu.get(rangée2)[colonne2].numero==13) {
                        lstJeu.get(rangée1)[i] = null;
                        lstJeu.get(rangée2)[colonne2] = null;
                        nbUtilisé+=2;
                        return true;
                    }
            }
        }
        //Valeur à 7 indique que c'est une carte dans le waste.
        if(rangée2==7)
        {
            for(int i=23;i>=0;i--)
            {
                if(lstJeu.get(rangée1)[colonne1]!=null && lstJeu.get(rangée2)[i]!=null)
                    if(lstJeu.get(rangée1)[colonne1].numero+lstJeu.get(rangée2)[i].numero==13) {
                        lstJeu.get(rangée1)[colonne1] = null;
                        lstJeu.get(rangée2)[i] = null;
                        nbUtilisé+=2;
                        return true;
                    }
            }
        }
        //2 cartes de la table de jeu
        if(lstJeu.get(rangée1)[colonne1]!=null && lstJeu.get(rangée2)[colonne2]!=null)
            if(lstJeu.get(rangée1)[colonne1].numero+lstJeu.get(rangée2)[colonne2].numero==13)
            {
                lstJeu.get(rangée1)[colonne1]=null;
                lstJeu.get(rangée2)[colonne2]=null;
                nbUtilisé+=2;
                return true;
            }
        //SI nous sommes la c'est pcq le combo ne fait pas 13.
        return false;
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

    @Override
    public void Initialiser(int seed) {

    }
}