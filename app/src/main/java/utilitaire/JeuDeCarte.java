package utilitaire;

import android.graphics.Color;

import com.example.utilisateur.jeudepatience.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Created by Jean-Michel Lavoie on 29/01/2016.
 */
public class JeuDeCarte extends ArrayList<Carte> {
    Map<String,Integer> dictionnaire = new HashMap<String,Integer>();
    public enum type{Trèfle,Carre,Coeur,Pique};
    public JeuDeCarte()
    {
        for(int i=0;i<13;i++)
        {
            Carte carteTemp  = new Carte(i+1,Color.RED,0,"" + type.Carre + ""+ (i+1), type.Carre);
            add(carteTemp);
            Carte carteTemp2  = new Carte(i+1,Color.BLACK,1,"" + type.Trèfle + ""+ (i+1), type.Trèfle);
            add(carteTemp2);
            Carte carteTemp3  = new Carte(i+1,Color.BLACK,2,"" + type.Pique + ""+ (i+1), type.Pique);
            add(carteTemp3);
            Carte carteTemp4  = new Carte(i+1,Color.RED,3,"" + type.Coeur + ""+ (i+1), type.Coeur);
            add(carteTemp4);
        }
        mélangerPaquet();
        remplirDictionnaire();
    }

    /**
     * Permet de remplir un dictionnaire avec tout les noms de cartes ainsi qu'avec l'id de l'image
     */
    private void remplirDictionnaire()
    {
        // Carre
        dictionnaire.put("Carre1", R.drawable.diamonds_a);
        dictionnaire.put("Carre2", R.drawable.diamonds_2);
        dictionnaire.put("Carre3", R.drawable.diamonds_3);
        dictionnaire.put("Carre4", R.drawable.diamonds_4);
        dictionnaire.put("Carre5", R.drawable.diamonds_5);
        dictionnaire.put("Carre6", R.drawable.diamonds_6);
        dictionnaire.put("Carre7", R.drawable.diamonds_7);
        dictionnaire.put("Carre8", R.drawable.diamonds_8);
        dictionnaire.put("Carre9", R.drawable.diamonds_9);
        dictionnaire.put("Carre10", R.drawable.diamonds_10);
        dictionnaire.put("Carre11", R.drawable.diamonds_j);
        dictionnaire.put("Carre12", R.drawable.diamonds_q);
        dictionnaire.put("Carre13", R.drawable.diamonds_k);

        // Trèfle
        dictionnaire.put("Trèfle1", R.drawable.clubs_a);
        dictionnaire.put("Trèfle2", R.drawable.clubs_2);
        dictionnaire.put("Trèfle3", R.drawable.clubs_3);
        dictionnaire.put("Trèfle4", R.drawable.clubs_4);
        dictionnaire.put("Trèfle5", R.drawable.clubs_5);
        dictionnaire.put("Trèfle6", R.drawable.clubs_6);
        dictionnaire.put("Trèfle7", R.drawable.clubs_7);
        dictionnaire.put("Trèfle8", R.drawable.clubs_8);
        dictionnaire.put("Trèfle9", R.drawable.clubs_9);
        dictionnaire.put("Trèfle10", R.drawable.clubs_10);
        dictionnaire.put("Trèfle11", R.drawable.clubs_j);
        dictionnaire.put("Trèfle12", R.drawable.clubs_q);
        dictionnaire.put("Trèfle13", R.drawable.clubs_k);

        //Coeur
        dictionnaire.put("Coeur1", R.drawable.hearts_a);
        dictionnaire.put("Coeur2", R.drawable.hearts_2);
        dictionnaire.put("Coeur3", R.drawable.hearts_3);
        dictionnaire.put("Coeur4", R.drawable.hearts_4);
        dictionnaire.put("Coeur5", R.drawable.hearts_5);
        dictionnaire.put("Coeur6", R.drawable.hearts_6);
        dictionnaire.put("Coeur7", R.drawable.hearts_7);
        dictionnaire.put("Coeur8", R.drawable.hearts_8);
        dictionnaire.put("Coeur9", R.drawable.hearts_9);
        dictionnaire.put("Coeur10", R.drawable.hearts_10);
        dictionnaire.put("Coeur11", R.drawable.hearts_j);
        dictionnaire.put("Coeur12", R.drawable.hearts_q);
        dictionnaire.put("Coeur13", R.drawable.hearts_k);

        // Pique
        dictionnaire.put("Pique1", R.drawable.spades_a);
        dictionnaire.put("Pique2", R.drawable.spades_2);
        dictionnaire.put("Pique3", R.drawable.spades_3);
        dictionnaire.put("Pique4", R.drawable.spades_4);
        dictionnaire.put("Pique5", R.drawable.spades_5);
        dictionnaire.put("Pique6", R.drawable.spades_6);
        dictionnaire.put("Pique7", R.drawable.spades_7);
        dictionnaire.put("Pique8", R.drawable.spades_8);
        dictionnaire.put("Pique9", R.drawable.spades_9);
        dictionnaire.put("Pique10", R.drawable.spades_10);
        dictionnaire.put("Pique11", R.drawable.spades_j);
        dictionnaire.put("Pique12", R.drawable.spades_q);
        dictionnaire.put("Pique13", R.drawable.spades_k);
    }


    /**
     * Méthode qui permet de trouver l'id d'une image pour une carte
     * @param nom le champ nom de l'objet carte
     * @return l'id de la ressource d'image correspondant à la carte
     */
    public int trouverIdCarte(String nom)
    {
        return dictionnaire.get(nom);
    }

    public ArrayList<Carte> RetourneCartesRestantes()
    {
        return (ArrayList<Carte>)this.clone();
    }

    public void ordonnerCartes()
    {
        ArrayList<Carte> paquetTemoin = (ArrayList<Carte>)this.clone();
        if(this.size()>1) {
            for (int i = 0; i < this.size(); i++) {
                for(int u=0;u<this.size();u++) {
                    //SI ma carte actuel est < que ma carte précédente , swapper actuel et précédente.
                    if (paquetTemoin.get(i).numero < paquetTemoin.get(u).numero) {
                        paquetTemoin.set(u,paquetTemoin.get(i));
                        paquetTemoin.set(i, this.get(u));
                        this.set(i, paquetTemoin.get(i));
                        this.set(u, paquetTemoin.get(u));
                    }
                }
            }
        }
    }
    public void ordonnerCartesCroissant()
    {
        ArrayList<Carte> paquetTemoin = (ArrayList<Carte>)this.clone();
        if(this.size()>1) {
            for (int i = 0; i < this.size(); i++) {
                for(int u=0;u<this.size();u++) {
                    //SI ma carte actuel est < que ma carte précédente , swapper actuel et précédente.
                    if (paquetTemoin.get(i).numero < paquetTemoin.get(u).numero) {
                        paquetTemoin.set(u,paquetTemoin.get(i));
                        paquetTemoin.set(i, this.get(u));
                        this.set(i, paquetTemoin.get(i));
                        this.set(u, paquetTemoin.get(u));
                    }
                    if (paquetTemoin.get(i).sorte < paquetTemoin.get(u).sorte) {
                        paquetTemoin.set(u,paquetTemoin.get(i));
                        paquetTemoin.set(i, this.get(u));
                        this.set(i, paquetTemoin.get(i));
                        this.set(u, paquetTemoin.get(u));
                    }
                }
            }
        }
    }
    public void ordonnerCartesDécroissant()
    {
        ArrayList<Carte> paquetTemoin = (ArrayList<Carte>)this.clone();
        if(this.size()>1) {
            for (int i = 0; i < this.size(); i++) {
                for(int u=0;u<this.size();u++) {
                    //SI ma carte actuel est > que ma carte précédente , swapper actuel et précédente.
                    if (paquetTemoin.get(i).numero > paquetTemoin.get(u).numero) {
                        paquetTemoin.set(u,paquetTemoin.get(i));
                        paquetTemoin.set(i, this.get(u));
                        this.set(i, paquetTemoin.get(i));
                        this.set(u, paquetTemoin.get(u));
                    }
                    if (paquetTemoin.get(i).sorte > paquetTemoin.get(u).sorte) {
                        paquetTemoin.set(u,paquetTemoin.get(i));
                        paquetTemoin.set(i, this.get(u));
                        this.set(i, paquetTemoin.get(i));
                        this.set(u, paquetTemoin.get(u));
                    }
                }
            }
        }
    }
    public void mélangerPaquet()
    {
        List<Carte> paquetmélangeur = new ArrayList<Carte>();
        Random rng = new Random();
        int position=0;
        while(this.size()>0)
        {
            position = rng.nextInt(this.size());
            Carte carteTemp = this.get(position);
            if(!paquetmélangeur.contains(carteTemp))
            {
                paquetmélangeur.add(carteTemp);
                this.remove(carteTemp);
            }
        }
        for(Carte ct: paquetmélangeur)
            this.add(ct);
        paquetmélangeur.clear();

    }

    /**
     * Méthode pour donnée à un paquet de cartes une ordre décider d'avance pour les tests
     * @param cartes
     */
    public void placerJeuCarte(ArrayList<Carte> cartes)
    {
        this.clear();
        for (Carte carte: cartes) {
            this.add(carte);
        }
    }

    /**
     * 
     * @return retourne la carte sur le dessus et refait le paquet avec la carte en moins. Si aucune carte n'a été pigée, retourne null.
     */
    public Carte pigerUneCarte() {
        Carte carteTemporaire = null;
        if (this.size()>0) {
            carteTemporaire=this.get(this.size()-1);
            this.remove(carteTemporaire);
        }
        return carteTemporaire;
    }

    public Carte pigerDessus() {
        Carte carteTemporaire = null;
        if (this.size()>0) {
            carteTemporaire=this.get(0);
            this.remove(carteTemporaire);
        }
        return carteTemporaire;
    }
}
