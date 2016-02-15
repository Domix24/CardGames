package jeudu31;

import java.util.ArrayList;
import java.util.List;

import utilitaire.Carte;
import utilitaire.JeuAvecCartes;
import utilitaire.JeuDeCarte;

/**
 * Created by Jean-Michel Lavoie on 11/02/2016.
 */
public class Joueur extends JeuAvecCartes{
    JeuDeCarte JeuEnMain;
    protected int SommeCarrés;
    protected int SommeCoeurs;
    protected int SommeTrèfles;
    protected int SommePiques;
    @Override
    public void Initialiser(int seed) {

    }

    /**
     * As vaut 1 ou 11
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
     * @param test
     */
    public Joueur(boolean test)
    {
        JeuEnMain = new JeuDeCarte();
        JeuEnMain.clear();
        if(!test)
        {
            for(int i=0;i<3;i++)
            {
                JeuEnMain.add(paquet.PigerUneCarte());
            }
        }
    }
    public List<Carte> AvoirLaMain()
    {
        return JeuEnMain;
    }
    public void AjouterCarteALaMain(Carte ct)
    {
        if(!JeuEnMain.contains(ct) && ct!=null)
             JeuEnMain.add(ct);
    }
    public void EnleverCarteALaMain(Carte ct)
    {
        if(JeuEnMain.contains(ct) && ct!=null)
            JeuEnMain.remove(ct);
    }

    /**
     *
     * @return Retourne si oui ou non le joueur a plafonné sa valeur de carte.
     */
    public boolean Plafonne()
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
    public void CalculerSommeMêmeCouleur()
    {
        SommeCarrés=0;
        SommeCoeurs=0;
        SommePiques=0;
        SommeTrèfles=0;
        JeuEnMain.OrdonnerCartesDécroissant();
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
                            if (ct.numero == 1 && SommeCarrés + 11 < 32)
                                SommeCarrés += 11;
                            else SommeCarrés += 1;
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
                            if (ct.numero == 1 && SommeCoeurs + 11 < 32)
                                SommeCoeurs += 11;
                            else SommeCoeurs += 1;
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
                            if (ct.numero == 1 && SommePiques + 11 < 32)
                                SommePiques += 11;
                            else SommePiques += 1;
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
                            if (ct.numero == 1 && SommeTrèfles + 11 < 32)
                                SommeTrèfles += 11;
                            else SommeTrèfles += 1;
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
    public boolean DétecterMêmeValeur()
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
}
