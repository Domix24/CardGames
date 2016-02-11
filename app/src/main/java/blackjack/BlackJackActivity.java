package blackjack;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import utilitaire.Carte;

import com.example.utilisateur.jeudepatience.R;

public class BlackJackActivity extends Activity {
    int indexJoueur;
    int indexCroupier;
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
            switch (indexJoueur) {
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
            if (nouvelleCarte == null)
                nouvelleCarte = jeu.pigerUneCarte();
            if (nouvelleCarte != null && indexJoueur <= 8) {
                jeu.cartesJoueur[indexJoueur++] = nouvelleCarte;
                image.setVisibility(View.VISIBLE);
                image.setImageResource(jeu.trouverIdCarte(nouvelleCarte.nom));

                int[] points = jeu.calculerPoints(jeu.cartesJoueur);
                if (points[1] > 21) {
                    pointsJoueur.setText(points[0] + " points");
                } else {
                    pointsJoueur.setText(points[0] + " / " + points[1] + " points");
                }
                if (points[0] == 21 || points[1] == 21)
                    faireJouerCroupier();
                if (points[0] > 21) {
                    Toast.makeText(getApplicationContext(), "Vous avez perdu :(", Toast.LENGTH_LONG).show();
                    estTermine = true;
                }
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
            faireJouerCroupier();
        }
    }

    /**
     * Réinitialiser tout les paramètres du jeu.
     */
    private void reinitialiserLeJeu() {
        indexJoueur = 0;
        indexCroupier = 0;
        jeu.cartesJoueur = new Carte[9];
        jeu.cartesCroupier = new Carte[9];
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
        if (nouvelleCarte == null)
            nouvelleCarte = jeu.pigerUneCarte();
        if (nouvelleCarte != null && indexJoueur <= 8) {
            jeu.cartesJoueur[indexJoueur++] = nouvelleCarte;
            image.setVisibility(View.VISIBLE);
            image.setImageResource(jeu.trouverIdCarte(nouvelleCarte.nom));

            int[] points = jeu.calculerPoints(jeu.cartesJoueur);
            if (points[1] > 21) {
                pointsJoueur.setText(points[0] + " points");
            } else {
                pointsJoueur.setText(points[0] + " / " + points[1] + " points");
            }
        }
        // Première carte du croupier
        image = (ImageView) findViewById(R.id.imgDealer1);
        nouvelleCarte = jeu.pigerUneCarte();
        if (nouvelleCarte != null) {
            jeu.cartesCroupier[indexCroupier++] = nouvelleCarte;
            image.setVisibility(View.VISIBLE);
            image.setImageResource(jeu.trouverIdCarte(nouvelleCarte.nom));
        }
        int pointCroupier = 0;
        int[] pointageCroupier = jeu.calculerPoints(jeu.cartesCroupier);
        if (pointageCroupier[1] > 21)
            pointCroupier = pointageCroupier[0];
        else
            pointCroupier = pointageCroupier[1];

        if (pointageCroupier[1] > 21) {
            pointsCroupier.setText(pointageCroupier[0] + " points");
        } else {
            pointsCroupier.setText(pointageCroupier[0] + " / " + pointageCroupier[1] + " points");
        }

        // Deuxième carte du joueur
        image = (ImageView) findViewById(R.id.imgCarte2);
        nouvelleCarte = jeu.pigerUneCarte();
        if (nouvelleCarte == null)
            nouvelleCarte = jeu.pigerUneCarte();
        if (nouvelleCarte != null && indexJoueur <= 8) {
            jeu.cartesJoueur[indexJoueur++] = nouvelleCarte;
            image.setVisibility(View.VISIBLE);
            image.setImageResource(jeu.trouverIdCarte(nouvelleCarte.nom));

            int[] points = jeu.calculerPoints(jeu.cartesJoueur);
            if (points[1] > 21) {
                pointsJoueur.setText(points[0] + " points");
            } else {
                pointsJoueur.setText(points[0] + " / " + points[1] + " points");
            }
            if (points[1] == 21) {
                faireJouerCroupier();
            }
        }
    }

    /**
     * Le croupier joue jusqu'à ce que son score soit plus haut ou égale que le score du joueur.
     */
    private void faireJouerCroupier() {
        int pointJoueur;
        int[] pointTotalJoueur = jeu.calculerPoints(jeu.cartesJoueur);
        if (pointTotalJoueur[1] > 21)
            pointJoueur = pointTotalJoueur[0];
        else
            pointJoueur = pointTotalJoueur[1];

        int pointCroupier = 0;
        int[] pointageCroupier = jeu.calculerPoints(jeu.cartesCroupier);
        if (pointageCroupier[1] > 21)
            pointCroupier = pointageCroupier[0];
        else
            pointCroupier = pointageCroupier[1];

        while (pointJoueur > pointCroupier && indexCroupier < 9) {
            ImageView image = (ImageView) findViewById(R.id.imgDealer1);
            switch (indexCroupier) {
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
                jeu.cartesCroupier[indexCroupier++] = nouvelleCarte;
                image.setVisibility(View.VISIBLE);
                image.setImageResource(jeu.trouverIdCarte(nouvelleCarte.nom));
            }
            pointageCroupier = jeu.calculerPoints(jeu.cartesCroupier);
            if (pointageCroupier[1] > 21)
                pointCroupier = pointageCroupier[0];
            else
                pointCroupier = pointageCroupier[1];

            if (pointageCroupier[1] > 21) {
                pointsCroupier.setText(pointageCroupier[0] + " points");
            } else {
                pointsCroupier.setText(pointageCroupier[0] + " / " + pointageCroupier[1] + " points");
            }

        }
        estTermine = true;
        int gagnant = jeu.determinerGagnant(jeu.cartesJoueur, jeu.cartesCroupier);
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
        effacerImage();
        for (int i = 0; i < 9; i++) {
            if (jeu.cartesJoueur[i] == null && i != 0) {
                indexJoueur = i - 1;
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
                indexCroupier = i - 1;
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
        if (jeu.cartesCroupier.length >= 2) {
            estTermine = true;
        }
        int pointCroupier;
        int[] pointageCroupier = jeu.calculerPoints(jeu.cartesCroupier);
        if (pointageCroupier[1] > 21)
            pointCroupier = pointageCroupier[0];
        else
            pointCroupier = pointageCroupier[1];

        if (pointageCroupier[1] > 21) {
            pointsCroupier.setText(pointageCroupier[0] + " points");
        } else {
            pointsCroupier.setText(pointageCroupier[0] + " / " + pointageCroupier[1] + " points");
        }
        int[] points = jeu.calculerPoints(jeu.cartesJoueur);
        if (points[1] > 21) {
            pointsJoueur.setText(points[0] + " points");
        } else {
            pointsJoueur.setText(points[0] + " / " + points[1] + " points");
        }
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
}
