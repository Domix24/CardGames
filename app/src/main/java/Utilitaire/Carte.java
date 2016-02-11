package Utilitaire;



/**
 * Created by Jean-Michel Lavoie on 29/01/2016.
 */
public class Carte{
    public int numero;
    public int couleur;
    public int sorte;
    public String nom;
    public JeuDeCarte.type typeCarte;
    public  boolean disponible=true;
    public Carte(int numero,int couleur, int sorte,String nom,JeuDeCarte.type type)
    {
        this.numero=numero;
        this.couleur=couleur;
        this.sorte=sorte;
        this.nom=nom;
        typeCarte=type;
    }
}
