package utilitaire;

/**
 * Created by Nakourou on 2016-02-11.
 */
public abstract class JeuAvecCartes extends Jeu {

    public void forcerJeuDeCarte(JeuDeCarte paquet)
    {
        this.paquet = paquet;
    }

    protected  JeuDeCarte paquet;
}
