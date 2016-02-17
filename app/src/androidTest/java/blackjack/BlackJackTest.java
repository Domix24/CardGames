package blackjack;

import android.graphics.Color;
import android.util.Log;

import junit.framework.Assert;
import junit.framework.TestCase;

import java.io.Console;

import utilitaire.Carte;
import utilitaire.JeuDeCarte;

/**
 * Plan de test de la logique du jeu de blackjack
 * @author Guillaume Demers-Loiselle
 */
public class BlackJackTest extends TestCase {



    public void setUp() throws Exception {
        super.setUp();

    }



    /**
     * Valide si le singleton fonctionne pour créé une instance et en retourné une
     * @throws Exception
     */
    public void testAvoirInstance() throws Exception {
        BlackJack instance = null;
        instance = BlackJack.avoirInstance();
        assertTrue(instance != null);

    }

    /**
     * Test la logique de calcule, le test regarde principalement si la gestion des Aces sont bien géré et le dépassement de points
     * Étapes:
     * -1 : main vide
     * -2 : Deux Aces
     * -3 : Deux Aces
     * -4 : Trois Aces
     * -5 : Trois Aces + 7
     * -6 : Trois Aces + deux 7
     * @throws Exception
     */
    public void testCalculerPoints() throws Exception {
        BlackJack instance = null;
        instance = BlackJack.avoirInstance();
        instance.reinitialiser();
        instance.calculerPoints();
        assertTrue("Étape 1-1", instance.pointageJoueur[0] == 0);
        assertTrue("Étape 1-2",instance.pointageJoueur[1] > 21);
        assertTrue("Étape 1-3", instance.pointageCroupier[0] == 0);
        assertTrue("Étape 1-4", instance.pointageCroupier[1] > 21);
        JeuDeCarte cartes = new JeuDeCarte();
        cartes.clear();
        Carte carteTemp  = new Carte(7, Color.RED,0,"" + JeuDeCarte.type.Carre + ""+ (1), JeuDeCarte.type.Carre);
        cartes.add(carteTemp);
        cartes.add(carteTemp);
        cartes.add(carteTemp);
        cartes.add(carteTemp);
        carteTemp  = new Carte(1, Color.RED,0,"" + JeuDeCarte.type.Carre + ""+ (1), JeuDeCarte.type.Carre);
        cartes.add(carteTemp);
        cartes.add(carteTemp);
        cartes.add(carteTemp);
        cartes.add(carteTemp);

        cartes.add(carteTemp);
        cartes.add(carteTemp);
        instance.reinitialiser();

        instance.forcerJeuDeCarte(cartes);
        Carte nouvelleCarte = instance.pigerUneCarte();
        if (nouvelleCarte != null && instance.indexJoueur < 12) {
            instance.jouerPourJoueur(nouvelleCarte);
        }
        nouvelleCarte = instance.pigerUneCarte();
        if (nouvelleCarte != null) {
            instance.jouerPourCroupier(nouvelleCarte);
        }
        nouvelleCarte = instance.pigerUneCarte();
        if (nouvelleCarte != null) {
            instance.jouerPourJoueur(nouvelleCarte);
        }
        nouvelleCarte = instance.pigerUneCarte();
        if (nouvelleCarte != null) {
            instance.jouerPourCroupier(nouvelleCarte);
        }
        instance.calculerPoints();
        assertTrue("Étape 2-1:" + instance.pointageJoueur[0], instance.pointageJoueur[0] == 2);
        assertTrue("Étape 2-2:" + instance.pointageJoueur[1],instance.pointageJoueur[1] == 12);

        assertTrue("Étape 2-3:" + instance.pointageCroupier[0],instance.pointageCroupier[0] == 2);
        assertTrue("Étape 2-4:" + instance.pointageCroupier[1],instance.pointageCroupier[1] == 12);
        nouvelleCarte = instance.pigerUneCarte();
        if (nouvelleCarte != null) {
            instance.jouerPourJoueur(nouvelleCarte);
        }
        nouvelleCarte = instance.pigerUneCarte();
        if (nouvelleCarte != null) {
            instance.jouerPourCroupier(nouvelleCarte);
        }

        instance.calculerPoints();
        assertTrue("Étape 3-1:" + instance.pointageJoueur[0], instance.pointageJoueur[0] == 3);
        assertTrue("Étape 3-2:" + instance.pointageJoueur[1],instance.pointageJoueur[1] == 13);

        assertTrue("Étape 3-3:" + instance.pointageCroupier[0],instance.pointageCroupier[0] == 3);
        assertTrue("Étape 3-4:" + instance.pointageCroupier[1],instance.pointageCroupier[1] == 13);
        nouvelleCarte = instance.pigerUneCarte();
        if (nouvelleCarte != null) {
            instance.jouerPourJoueur(nouvelleCarte);
        }
        nouvelleCarte = instance.pigerUneCarte();
        if (nouvelleCarte != null) {
            instance.jouerPourCroupier(nouvelleCarte);
        }
        instance.calculerPoints();
        assertTrue("Étape 4-1:" + instance.pointageJoueur[0], instance.pointageJoueur[0] == 10);
        assertTrue("Étape 4-2:" + instance.pointageJoueur[1],instance.pointageJoueur[1] == 20);

        assertTrue("Étape 4-3:" + instance.pointageCroupier[0],instance.pointageCroupier[0] == 10);
        assertTrue("Étape 4-4:" + instance.pointageCroupier[1],instance.pointageCroupier[1] == 20);
        nouvelleCarte = instance.pigerUneCarte();
        if (nouvelleCarte != null) {
            instance.jouerPourJoueur(nouvelleCarte);
        }
        nouvelleCarte = instance.pigerUneCarte();
        if (nouvelleCarte != null) {
            instance.jouerPourCroupier(nouvelleCarte);
        }
        instance.calculerPoints();
        assertTrue("Étape 5-1:" + instance.pointageJoueur[0], instance.pointageJoueur[0] == 17);
        assertTrue("Étape 5-2:" + instance.pointageJoueur[1],instance.pointageJoueur[1] == 27);

        assertTrue("Étape 5-3:" + instance.pointageCroupier[0],instance.pointageCroupier[0] == 17);
        assertTrue("Étape 5-4:" + instance.pointageCroupier[1],instance.pointageCroupier[1] == 27);
    }

    /**
     * Méthode de test avec des scores donnée pour savoir qui gagne avec le system de gestion des aces
     * @throws Exception
     */
    public void testDeterminerGagnant() throws Exception {

        BlackJack instance = null;
        instance = BlackJack.avoirInstance();
        instance.reinitialiser();
        instance.pointageJoueur[0] = 11;
        instance.pointageJoueur[1] = 20;
        instance.pointageCroupier[0] = 10;
        instance.pointageCroupier[1] = 21;
        instance.determinerGagnant();
        assertTrue("Étape 1-1 : croupier doit gagnier : "+instance.drapeauFinPartie, instance.drapeauFinPartie == 1);
        instance.pointageJoueur[1] = 21;
        instance.determinerGagnant();
        assertTrue("Étape 1-2 : Égalité : " + instance.drapeauFinPartie, instance.drapeauFinPartie == 2);
        instance.pointageCroupier[1] = 20;
        instance.determinerGagnant();
        assertTrue("Étape 1-3 : joueur doit gagnier : " + instance.drapeauFinPartie, instance.drapeauFinPartie == 3);
        instance.pointageJoueur[0] = 17;
        instance.pointageJoueur[1] = 49;
        instance.pointageCroupier[0] = 18;
        instance.pointageCroupier[1] = 51;
        instance.determinerGagnant();
        assertTrue("Étape 2-1 : croupier doit gagnier : "+instance.drapeauFinPartie, instance.drapeauFinPartie == 1);
        instance.pointageJoueur[0] = 18;
        instance.determinerGagnant();
        assertTrue("Étape 2-2 : Égalité : "+instance.drapeauFinPartie, instance.drapeauFinPartie == 2);
        instance.pointageCroupier[0] = 17;
        instance.determinerGagnant();
        assertTrue("Étape 2-3 : joueur doit gagnier : "+instance.drapeauFinPartie, instance.drapeauFinPartie == 3);

    }

    /**
     * test les méthode de piger carte joueur et croupier
     * @throws Exception
     */
    public void testPigerUneCarte() throws Exception {
        BlackJack instance = null;
        instance = BlackJack.avoirInstance();
        instance.reinitialiser();
        Carte carteTemp  = new Carte(2, Color.RED,0,"" + JeuDeCarte.type.Carre + ""+ (2), JeuDeCarte.type.Carre);
        instance.jouerPourJoueur(null);
        instance.jouerPourCroupier(null);
        assertTrue("Étape 1-1 : Main joueur doit être vide : " + instance.indexJoueur, instance.indexJoueur == 0);
        assertTrue("Étape 1-2 : Main croupier doit être vide : " + instance.indexCroupier, instance.indexCroupier == 0);
        instance.jouerPourJoueur(carteTemp);
        instance.jouerPourCroupier(carteTemp);
        assertTrue("Étape 2-1 : Main joueur doit être 1 : " + instance.indexJoueur, instance.indexJoueur == 1);
        assertTrue("Étape 2-2 : Main croupier doit être 1 : " + instance.indexCroupier, instance.indexCroupier == 1);
        instance.jouerPourJoueur(carteTemp);
        instance.jouerPourCroupier(carteTemp);
        assertTrue("Étape 3-1 : Main joueur doit être 2 : " + instance.indexJoueur, instance.indexJoueur == 2);
        assertTrue("Étape 3-2 : Main croupier doit être 2 : " + instance.indexCroupier, instance.indexCroupier == 2);



    }


}