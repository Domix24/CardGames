package jeudu31;

import java.util.ArrayList;
import java.util.List;

import utilitaire.Carte;
import utilitaire.JeuAvecCartes;

/**
 * Created by Jean-Michel Lavoie on 11/02/2016.
 */
public class Joueur extends JeuAvecCartes{
    List<Carte> JeuEnMain;
    int SommeCarrés;
    int SommeCoeurs;
    int SommeTrèfles;
    int SommePiques;
    @Override
    public void Initialiser(int seed) {

    }
    public Joueur(boolean test)
    {
        JeuEnMain = new ArrayList<Carte>();
        if(!test)
        {
            for(int i=0;i<5;i++)
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
    public void RetournerCarteAuPaquet(Carte ct)
    {
        if(!paquet.contains(ct) && ct!=null && JeuEnMain.contains(ct))
            paquet.add(ct);
    }
    public void CalculerSommeMêmeCouleur()
    {
        SommeCarrés=0;
        SommeCoeurs=0;
        SommePiques=0;
        SommeTrèfles=0;
        for(Carte ct: JeuEnMain)
        {
            switch(ct.typeCarte) {
                case Carre:
                    if(ct.numero>=10 || ct.numero==1)
                        SommeCarrés+=10;
                    else
                        SommeCarrés+=ct.numero;
                    break;
                case Coeur:
                    if(ct.numero>=10 || ct.numero==1)
                        SommeCoeurs+=10;
                    else
                        SommeCoeurs+=ct.numero;
                break;
                case Pique:
                    if(ct.numero>=10 || ct.numero==1)
                        SommePiques+=10;
                    else
                        SommePiques+=ct.numero;
                    break;
                case Trèfle:
                    if(ct.numero>=10 || ct.numero==1)
                        SommeTrèfles+=10;
                    else
                        SommeTrèfles+=ct.numero;
                    break;
            }
        }
    }
    public boolean DétecterMêmeValeur()
    {
        int[] NbrMêmeCarte = new int[13];
        for(Carte ct: JeuEnMain)
        {
           NbrMêmeCarte[ct.numero]+=1;
        }
        for(int i=0;i<NbrMêmeCarte.length;i++)
            if(NbrMêmeCarte[i]==4)
                return true;
        return false;
    }
}
