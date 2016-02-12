package blackjack;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import utilitaire.Carte;

import com.example.utilisateur.jeudepatience.R;

public class BlackJackActivity extends Activity {
    BlackJack jeu = BlackJack.avoirInstance();
    TextView pointsJoueur;
    TextView pointsCroupier;
    Toast message;

    @Override
    /**
     * S'éxécute à la création pour reinitialiser le jeu.
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_black_jack);
        pointsJoueur = (TextView) findViewById(R.id.lblPointsJoueur);
        pointsCroupier = (TextView) findViewById(R.id.lblPointsCroupier);
        message = Toast.makeText(this, "", Toast.LENGTH_SHORT);

        if (!jeu.estCree) {
            reinitialiserLeJeu();
        } else
            replacerJeu();
    }

    /**
     * Permet de rajouter une carte dans les cartes du joueur et d'afficher son nombre de points.
     *
     * @param v Le bouton cliqué
     */
    public void onHitClick(View v) {
        if (!jeu.estTermine) {
            Carte nouvelleCarte = jeu.pigerUneCarte();
            if (nouvelleCarte != null) {
                jeu.jouerPourJoueur(nouvelleCarte);
                mettreÀJourAffichage();
                mettreÀJourPoints();
                if (jeu.estTermine){
                    afficherGagnant();
                }
            }
        } else{
            reinitialiserLeJeu();
        }
    }

    /**
     * Permet de faire jouer le croupier, lorsque le joueur décide de garder ses cartes.
     *
     * @param v le bouton cliqué
     */
    public void onHoldClick(View v) {
        if (!jeu.estTermine) {
            jeu.faireJouerCroupier();
            mettreÀJourPoints();
            mettreÀJourAffichage();
            if (jeu.estTermine){
                afficherGagnant();
            }
        }
        else{
            reinitialiserLeJeu();
        }
    }

    /**
     * Permet d'afficher le résutat de la partie.
     */
    private void afficherGagnant(){
        jeu.determinerGagnant();
        message.setText(jeu.message);
        message.show();
    }
    /**
     * Réinitialiser tout les paramètres du jeu.
     */
    private void reinitialiserLeJeu() {
        jeu.cartesJoueur = new Carte[9];
        jeu.cartesCroupier = new Carte[9];
        jeu.reinitialiser();

        effacerImage();

        // Reinitialiser les textes
        pointsJoueur.setText("0 points");
        pointsCroupier.setText("0 points");

        // Commencer le jeu
        passerPremieresCartes();

    }

    /**
     * Donne 1 carte au joueur, une carte au croupier, puis une autre au joueur.
     */
    private void passerPremieresCartes() {

        // Première carte du joueur
        Carte nouvelleCarte = jeu.pigerUneCarte();
        if (nouvelleCarte != null && jeu.indexJoueur < 12) {
            jeu.jouerPourJoueur(nouvelleCarte);
        }
        // Première carte du croupier
        nouvelleCarte = jeu.pigerUneCarte();
        if (nouvelleCarte != null) {
            jeu.jouerPourCroupier(nouvelleCarte);
        }

        // Deuxième carte du joueur
        nouvelleCarte = jeu.pigerUneCarte();
        if (nouvelleCarte != null) {
            jeu.jouerPourJoueur(nouvelleCarte);
        }
        mettreÀJourPoints();
        mettreÀJourAffichage();
    }

    /**
     * Si le onCreate est appellé et qu'une partie est en cours, replacer les cartes et le pointage.
     */
    private void replacerJeu() {
        mettreÀJourAffichage();
        if (jeu.cartesCroupier.length >= 2) {
            jeu.estTermine = true;
        }
        mettreÀJourPoints();
    }

    /**
     * Rendre les images de cartes invisibles.
     */
    private void effacerImage() {
        // Reinitialiser les images
        ImageView image = (ImageView) findViewById(R.id.imgCarte1);
        image.setVisibility(View.INVISIBLE);

        image = (ImageView) findViewById(R.id.imgCarte2);
        image.setVisibility(View.INVISIBLE);

        image = (ImageView) findViewById(R.id.imgCarte3);
        image.setVisibility(View.INVISIBLE);

        image = (ImageView) findViewById(R.id.imgCarte4);
        image.setVisibility(View.INVISIBLE);

        image = (ImageView) findViewById(R.id.imgCarte5);
        image.setVisibility(View.INVISIBLE);

        image = (ImageView) findViewById(R.id.imgCarte6);
        image.setVisibility(View.INVISIBLE);

        image = (ImageView) findViewById(R.id.imgCarte7);
        image.setVisibility(View.INVISIBLE);

        image = (ImageView) findViewById(R.id.imgCarte8);
        image.setVisibility(View.INVISIBLE);

        image = (ImageView) findViewById(R.id.imgCarte9);
        image.setVisibility(View.INVISIBLE);

        image = (ImageView) findViewById(R.id.imgDealer1);
        image.setVisibility(View.INVISIBLE);

        image = (ImageView) findViewById(R.id.imgDealer2);
        image.setVisibility(View.INVISIBLE);

        image = (ImageView) findViewById(R.id.imgDealer3);
        image.setVisibility(View.INVISIBLE);

        image = (ImageView) findViewById(R.id.imgDealer4);
        image.setVisibility(View.INVISIBLE);

        image = (ImageView) findViewById(R.id.imgDealer5);
        image.setVisibility(View.INVISIBLE);

        image = (ImageView) findViewById(R.id.imgDealer6);
        image.setVisibility(View.INVISIBLE);

        image = (ImageView) findViewById(R.id.imgDealer7);
        image.setVisibility(View.INVISIBLE);

        image = (ImageView) findViewById(R.id.imgDealer8);
        image.setVisibility(View.INVISIBLE);

        image = (ImageView) findViewById(R.id.imgDealer9);
        image.setVisibility(View.INVISIBLE);
    }

    /**
     * Affiche toutes les cartes
     */
    private void mettreÀJourAffichage() {
        effacerImage();
        for (int i = 0; i < 12; i++) {
            if (jeu.cartesJoueur[i] == null && i != 0) {
                break;
            }
            if (jeu.cartesJoueur[i] == null && i == 0) {
                reinitialiserLeJeu();
                break;
            }
            ImageView image = (ImageView) findViewById(R.id.imgCarte1);
            switch (i) {
                case 0:
                    image = (ImageView) findViewById(R.id.imgCarte1);
                    break;
                case 1:
                    image = (ImageView) findViewById(R.id.imgCarte2);
                    break;
                case 2:
                    image = (ImageView) findViewById(R.id.imgCarte3);
                    break;
                case 3:
                    image = (ImageView) findViewById(R.id.imgCarte4);
                    break;
                case 4:
                    image = (ImageView) findViewById(R.id.imgCarte5);
                    break;
                case 5:
                    image = (ImageView) findViewById(R.id.imgCarte6);
                    break;
                case 6:
                    image = (ImageView) findViewById(R.id.imgCarte7);
                    break;
                case 7:
                    image = (ImageView) findViewById(R.id.imgCarte8);
                    break;
                case 8:
                    image = (ImageView) findViewById(R.id.imgCarte9);
                    break;
            }
            image.setImageResource(jeu.trouverIdCarte(jeu.cartesJoueur[i].nom));
            image.setVisibility(View.VISIBLE);
        }
        for (int i = 0; i < 12; i++) {
            if (jeu.cartesCroupier[i] == null && i != 0) {
                break;
            }
            if (jeu.cartesCroupier[i] == null && i == 0) {
                reinitialiserLeJeu();
                break;
            }
            ImageView image = (ImageView) findViewById(R.id.imgCarte1);
            switch (i) {
                case 0:
                    image = (ImageView) findViewById(R.id.imgDealer1);
                    break;
                case 1:
                    image = (ImageView) findViewById(R.id.imgDealer2);
                    break;
                case 2:
                    image = (ImageView) findViewById(R.id.imgDealer3);
                    break;
                case 3:
                    image = (ImageView) findViewById(R.id.imgDealer4);
                    break;
                case 4:
                    image = (ImageView) findViewById(R.id.imgDealer5);
                    break;
                case 5:
                    image = (ImageView) findViewById(R.id.imgDealer6);
                    break;
                case 6:
                    image = (ImageView) findViewById(R.id.imgDealer7);
                    break;
                case 7:
                    image = (ImageView) findViewById(R.id.imgDealer8);
                    break;
                case 8:
                    image = (ImageView) findViewById(R.id.imgDealer9);
                    break;
            }
            image.setImageResource(jeu.trouverIdCarte(jeu.cartesCroupier[i].nom));
            image.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Méthode qui va afficher et mettre a jour les points du joueur et du croupier.
     */
    private void mettreÀJourPoints() {
        jeu.calculerPoints();

        // Calculer les points du croupier
        if (jeu.pointageCroupier[1] > 21) {
            pointsCroupier.setText(jeu.pointageCroupier[0] + " points");
        } else {
            pointsCroupier.setText(jeu.pointageCroupier[0] + " / " + jeu.pointageCroupier[1] + " points");
        }

        // Calculer les points du joueur
        jeu.pointageJoueur = jeu.pointageJoueur;
        if (jeu.pointageJoueur[1] > 21) {
            pointsJoueur.setText(jeu.pointageJoueur[0] + " points");
        } else {
            pointsJoueur.setText(jeu.pointageJoueur[0] + " / " + jeu.pointageJoueur[1] + " points");
        }
    }
}
