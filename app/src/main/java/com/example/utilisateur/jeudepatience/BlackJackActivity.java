package com.example.utilisateur.jeudepatience;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class BlackJackActivity extends Activity {
    boolean estTermine = false;
    BlackJack jeu = BlackJack.avoirInstance();
    TextView pointsJoueur;
    TextView pointsCroupier;

    @Override
    /**
     * S'éxécute à la création pour reinitialiser le jeu.
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_black_jack);
        pointsJoueur = (TextView) findViewById(R.id.lblPointsJoueur);
        pointsCroupier = (TextView) findViewById(R.id.lblPointsCroupier);
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
        if (!estTermine) {
            ImageView image = (ImageView) findViewById(R.id.imgCarte1);
            switch (jeu.indexJoueur) {
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
            Carte nouvelleCarte = jeu.pigerUneCarte();
            if (nouvelleCarte != null)
            {
                jeu.jouerPourJoueur(nouvelleCarte);
                image.setVisibility(View.VISIBLE);
                image.setImageResource(jeu.trouverIdCarte(nouvelleCarte.nom));
                mettreÀJourPoints();
            }

        } else
            reinitialiserLeJeu();
    }

    /**
     * Permet de faire jouer le croupier, lorsque le joueur décide de garder ses cartes.
     *
     * @param v le bouton cliqué
     */
    public void onHoldClick(View v) {
        if (!estTermine) {
            jeu.faireJouerCroupier();
        }
    }

    /**
     * Réinitialiser tout les paramètres du jeu.
     */
    private void reinitialiserLeJeu() {
        estTermine = false;
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
        ImageView image = (ImageView) findViewById(R.id.imgCarte1);
        Carte nouvelleCarte = jeu.pigerUneCarte();
        jeu.jouerPourJoueur(nouvelleCarte);
        if (nouvelleCarte != null && jeu.indexJoueur < 12) {
            image.setVisibility(View.VISIBLE);
            image.setImageResource(jeu.trouverIdCarte(nouvelleCarte.nom));
        }
        // Première carte du croupier
        image = (ImageView) findViewById(R.id.imgDealer1);
        nouvelleCarte = jeu.pigerUneCarte();
        jeu.jouerPourCroupier(nouvelleCarte);
        if (nouvelleCarte != null) {
            image.setVisibility(View.VISIBLE);
            image.setImageResource(jeu.trouverIdCarte(nouvelleCarte.nom));
        }

        // Deuxième carte du joueur
        image = (ImageView) findViewById(R.id.imgCarte2);
        nouvelleCarte = jeu.pigerUneCarte();
        jeu.jouerPourJoueur(nouvelleCarte);

        if (nouvelleCarte != null) {
            image.setVisibility(View.VISIBLE);
            image.setImageResource(jeu.trouverIdCarte(nouvelleCarte.nom));
        }
        mettreÀJourPoints();
        mettreÀJourAffichage();
    }

    /**
     * Le croupier joue jusqu'à ce que son score soit plus haut ou égale que le score du joueur.
     */
    private void faireJouerCroupier() {
        while (pointJoueur > pointCroupier && jeu.indexCroupier < 12) {
            ImageView image = (ImageView) findViewById(R.id.imgDealer1);
            switch (jeu.indexCroupier) {
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
            Carte nouvelleCarte = jeu.pigerUneCarte();
            if (nouvelleCarte != null) {
                jeu.cartesCroupier[jeu.indexCroupier++] = nouvelleCarte;
                image.setVisibility(View.VISIBLE);
                image.setImageResource(jeu.trouverIdCarte(nouvelleCarte.nom));
            }
            jeu.calculerPoints();
            //jeu.pointageCroupier = jeu.calculerPoints(jeu.cartesCroupier);
            if (jeu.pointageCroupier[1] > 21)
                pointCroupier = jeu.pointageCroupier[0];
            else
                pointCroupier = jeu.pointageCroupier[1];
            mettreÀJourPoints();
        }
        estTermine = true;
        int gagnant = jeu.determinerGagnant();
        switch (gagnant) {
            case 0:
                Toast.makeText(getApplicationContext(), "Vous avez gagné !", Toast.LENGTH_LONG).show();
                break;
            case 1:
                Toast.makeText(getApplicationContext(), "Vous avez perdu :(", Toast.LENGTH_LONG).show();
                break;
            case 2:
                Toast.makeText(getApplicationContext(), "Match nul :/", Toast.LENGTH_LONG).show();
                break;
        }
    }

    /**
     * Si le onCreate est appellé et qu'une partie est en cours, replacer les cartes et le pointage.
     */
    private void replacerJeu() {
        mettreÀJourAffichage();
        if (jeu.cartesCroupier.length >= 2) {
            estTermine = true;
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
    private void mettreÀJourAffichage(){
        effacerImage();
        for (int i = 0; i < 9; i++) {
            if (jeu.cartesJoueur[i] == null && i != 0) {
                jeu.indexJoueur = i - 1;
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
        for (int i = 0; i < 9; i++) {
            if (jeu.cartesCroupier[i] == null && i != 0) {
                jeu.indexCroupier = i - 1;
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
