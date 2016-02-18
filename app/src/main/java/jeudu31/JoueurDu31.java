package jeudu31;

import java.util.List;

import utilitaire.Carte;
import utilitaire.JeuAvecCartes;
import utilitaire.JeuDeCarte;

/**
 * Created by Jean-Michel Lavoie on 11/02/2016.
 */
public class JoueurDu31 extends JeuAvecCartes {
    JeuDeCarte JeuEnMain;
    private int SommeCarrés;
    private int SommeCoeurs;
    private int SommeTrèfles;
    private int SommePiques;
    protected boolean Cogne;
    protected String nom;

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
        for(int i=0;i<3;i++)
            ajouterCarteALaMain(paquet.pigerUneCarte());

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
        return JeuEnMain;
    }

    /**
     *
     * @param ct ajoute la carte dans la main du joueur
     * @return retourne le résultat de l'opération
     */
    public boolean ajouterCarteALaMain(Carte ct)
    {
        if(!JeuEnMain.contains(ct) && ct!=null) {
            JeuEnMain.add(ct);
            return true;
        }
        return false;
    }

    /**
     *
     * @param ct ajoute la carte à la main
     * @return retounre le résultat de l'opération.
     */
    public boolean enleverCarteALaMain(Carte ct)
    {
        if(JeuEnMain.contains(ct) && ct!=null) {
            JeuEnMain.remove(ct);
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
        if(SommeCarrés==31)
            return true;
        else if (SommeCoeurs==31)
            return true;
        else if(SommePiques==31)
            return true;
        else if(SommeTrèfles==31)
            return true;
        return false;
    }
    /**
     * Fait la somme des cartes en main de la même couleur.
     */
    public void calculerSommeMêmeCouleur()
    {
        SommeCarrés=0;
        SommeCoeurs=0;
        SommePiques=0;
        SommeTrèfles=0;
        JeuEnMain.ordonnerCartesDécroissant();
        for(Carte ct: JeuEnMain)
        {
            switch(ct.typeCarte) {
                case Carre:
                    if(ct.numero>=10) {
                        SommeCarrés += 10;
                    }
                    else {
                        if (ct.numero > 1 && ct.numero < 10)
                            SommeCarrés += ct.numero;
                        else {
                            if (ct.numero == 1)
                                SommeCarrés += 11;
                        }
                    }
                    break;
                case Coeur:
                    if(ct.numero>=10) {
                        SommeCoeurs += 10;
                    }
                    else {
                        if (ct.numero > 1 && ct.numero < 10)
                            SommeCoeurs += ct.numero;
                        else {
                            if (ct.numero == 1)
                                SommeCoeurs += 11;
                        }
                    }
                break;
                case Pique:
                    if(ct.numero>=10) {
                        SommePiques += 10;
                    }
                    else {
                        if (ct.numero > 1 && ct.numero < 10)
                            SommePiques += ct.numero;
                        else {
                            if (ct.numero == 1)
                                SommePiques += 11;
                        }
                    }
                    break;
                case Trèfle:
                    if(ct.numero>=10) {
                        SommeTrèfles += 10;
                    }
                    else {
                        if (ct.numero > 1 && ct.numero < 10)
                            SommeTrèfles += ct.numero;
                        else {
                            if (ct.numero == 1)
                                SommeTrèfles += 11;
                        }
                    }
                    break;
            }
        }
    }

    /**
     *
     * @return retourne vrai si le joueur à par exemple 3 As sinon retourne faux.
     */
    public boolean détecterMêmeValeur()
    {
        int[] NbrMêmeCarte = new int[13];
        for(Carte ct: JeuEnMain)
        {
           NbrMêmeCarte[ct.numero]+=1;
        }
        for(int i=0;i<NbrMêmeCarte.length;i++)
            if(NbrMêmeCarte[i]==3)
                return true;
        return false;
    }

    /**
     *
     * @return Retourne la plus haute valeur dans la main du joueur.
     */
    public int retournePlusHauteValeur()
    {
        if(détecterMêmeValeur())
            return 31;
        calculerSommeMêmeCouleur();
        if(SommeCarrés>=SommeTrèfles && SommeCarrés>=SommePiques && SommeCarrés>=SommeCoeurs)
            return SommeCarrés;
        if(SommePiques>=SommeCarrés && SommePiques>=SommeTrèfles && SommePiques>=SommeCoeurs)
            return  SommePiques;
        if(SommeCoeurs>=SommeCarrés && SommeCoeurs>=SommePiques && SommeCoeurs>=SommeTrèfles)
            return SommeCoeurs;
        return SommeTrèfles;
    }
    @Override
    public void Initialiser(int seed) {

    }
}
