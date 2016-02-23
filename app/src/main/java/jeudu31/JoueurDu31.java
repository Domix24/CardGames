package jeudu31;

import java.util.List;

import utilitaire.Carte;
import utilitaire.JeuDeCarte;

/**
 * Created by Jean-Michel Lavoie on 11/02/2016.
 */
public class JoueurDu31{
    JeuDeCarte jeuEnMain;
    private int sommeCarrés;
    private int sommeCoeurs;
    private int sommeTrèfles;
    private int sommePiques;
    protected boolean Cogne;
    protected String nom;
    int[] nbrMêmeCarte;

    /**
     * As vaut 11
     figure vaut 10
     3 cartes départ
     3 pareil = 31 (3 Roi, 3 Dame,3 deux, etc)
     31 points de la meme sorte pour plafonner.
     Quand quelqu'un plafonne, il montre ses cartes et s'est la fin de la manche et il gagne.
     Au tour du joueur: il en pige 1 et discarte 1
     la carte discarte peut etre prise par le prochain joueur ( paquet de carte discarté)
     Toujours 3 cartes dans les main.
     cogner à son tour pour dire notre intention de provoquer une fin de manche.
     Ne peut piger une carte si tu cogne.
     Après qu'un joueur ait cogner, les autres ont 1 tour pour piger.
     main plus basse perd plus haute gagne
     si tu cogne et que tu perd tu paye en double.
     le plus haut ramasse tout
     si égalité on split le lot.
     */
    public JoueurDu31()
    {
        nom="";
        Cogne=false;
        jeuEnMain = new JeuDeCarte();
        jeuEnMain.clear();

    }

    /**
     *
     * @param nom Renvoie le nom du propriétaire.
     */
    public void déterminerNom(String nom)
    {
        this.nom=nom;
    }
    /**
     *
     * @return les cartes du joueur.
     */
    public List<Carte> avoirLaMain()
    {
        return jeuEnMain;
    }

    /**
     *
     * @param ct ajoute la carte dans la main du joueur
     * @return retourne null si non assignée.
     */
    public Carte ajouterCarteALaMain(Carte ct)
    {
        if(!jeuEnMain.contains(ct) && ct!=null) {
            jeuEnMain.add(ct);
            return ct;
        }
        return null;
    }

    /**
     *
     * @param ct ajoute la carte à la main
     * @return retounre le résultat de l'opération.
     */
    public boolean enleverCarteALaMain(Carte ct)
    {
        if(jeuEnMain.contains(ct) && ct!=null) {
            jeuEnMain.remove(ct);
            return true;
        }
        return false;
    }

    /**
     * Le joueur Cogne pour exprimer son intention de finir la manche.
     */
    public void cogner()
    {
        Cogne=true;
    }
    /**
     *
     * @return Retourne si oui ou non le joueur a plafonné sa valeur de carte.
     */
    public boolean plafonne()
    {
        if(sommeCarrés ==31)
            return true;
        else if (sommeCoeurs ==31)
            return true;
        else if(sommePiques ==31)
            return true;
        else if(sommeTrèfles ==31)
            return true;
        return false;
    }
    /**
     * Fait la somme des cartes en main de la même couleur.
     */
    public void calculerSommeMêmeCouleur()
    {
        sommeCarrés =0;
        sommeCoeurs =0;
        sommePiques =0;
        sommeTrèfles =0;
        jeuEnMain.ordonnerCartesDécroissant();
        for(Carte ct: jeuEnMain)
        {
            switch(ct.typeCarte) {
                case Carre:
                    if(ct.numero>=10) {
                        sommeCarrés += 10;
                    }
                    else {
                        if (ct.numero > 1 && ct.numero < 10)
                            sommeCarrés += ct.numero;
                        else {
                            if (ct.numero == 1)
                                sommeCarrés += 11;
                        }
                    }
                    break;
                case Coeur:
                    if(ct.numero>=10) {
                        sommeCoeurs += 10;
                    }
                    else {
                        if (ct.numero > 1 && ct.numero < 10)
                            sommeCoeurs += ct.numero;
                        else {
                            if (ct.numero == 1)
                                sommeCoeurs += 11;
                        }
                    }
                break;
                case Pique:
                    if(ct.numero>=10) {
                        sommePiques += 10;
                    }
                    else {
                        if (ct.numero > 1 && ct.numero < 10)
                            sommePiques += ct.numero;
                        else {
                            if (ct.numero == 1)
                                sommePiques += 11;
                        }
                    }
                    break;
                case Trèfle:
                    if(ct.numero>=10) {
                        sommeTrèfles += 10;
                    }
                    else {
                        if (ct.numero > 1 && ct.numero < 10)
                            sommeTrèfles += ct.numero;
                        else {
                            if (ct.numero == 1)
                                sommeTrèfles += 11;
                        }
                    }
                    break;
            }
        }
    }

    /**
     *
     * @return retourne le numéro de la carte si elle est en double ou plus.
     */
    public int chercheMêmeValeur()
    {
        for(int i=0;i<nbrMêmeCarte.length;i++)
            if(nbrMêmeCarte[i]>=2)
                return i;
        return 0;
    }
    /**
     *
     * @return retourne la quantité la plus grande de carte si le joueur en possède plusieurs..
     */
    public int détecterMêmeValeur()
    {
        nbrMêmeCarte = new int[13];
        for(Carte ct: jeuEnMain)
        {
           nbrMêmeCarte[ct.numero-1]+=1;
        }
        for(int i=0;i<nbrMêmeCarte.length;i++)
            if(nbrMêmeCarte[i]>=2)
                return nbrMêmeCarte[i];
        return 0;
    }

    /**
     *
     * @return Retourne la plus haute valeur dans la main du joueur.
     */
    public int retournePlusHauteValeur()
    {
        if(détecterMêmeValeur()==3)
            return 31;
        calculerSommeMêmeCouleur();
        if(sommeCarrés >= sommeTrèfles && sommeCarrés >= sommePiques && sommeCarrés >= sommeCoeurs)
            return sommeCarrés;
        if(sommePiques >= sommeCarrés && sommePiques >= sommeTrèfles && sommePiques >= sommeCoeurs)
            return sommePiques;
        if(sommeCoeurs >= sommeCarrés && sommeCoeurs >= sommePiques && sommeCoeurs >= sommeTrèfles)
            return sommeCoeurs;
        return sommeTrèfles;
    }

    /**
     *
     * @return retourne le numéro de la sorte qui a la puls haute valeur.
     */
    public int retournePlusHauteSorte()
    {
        calculerSommeMêmeCouleur();
        if(sommeCarrés >= sommeTrèfles && sommeCarrés >= sommePiques && sommeCarrés >= sommeCoeurs)
            return 0;
        if(sommePiques >= sommeCarrés && sommePiques >= sommeTrèfles && sommePiques >= sommeCoeurs)
            return  2;
        if(sommeCoeurs >= sommeCarrés && sommeCoeurs >= sommePiques && sommeCoeurs >= sommeTrèfles)
            return 3;
        return 1;
    }
}
