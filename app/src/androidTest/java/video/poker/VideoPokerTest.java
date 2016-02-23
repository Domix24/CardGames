package video.poker;

import android.graphics.Color;

import junit.framework.TestCase;

import utilitaire.Carte;
import utilitaire.JeuDeCarte;

/**
 * Plan de test de la logique du jeu de Video Poker
 *
 * Pour le plan de test nous allons testé les différentes victoires possible
 * @author Guillaume Demers-Loiselle
 */
public class VideoPokerTest extends TestCase {


    /**
     * Test d'initialisation du jeu
     * @throws Exception
     */
    public void testAvoirInstance() throws Exception {
        VideoPoker instance = null;
        instance = VideoPoker.avoirInstance();
        assertTrue(instance != null);

    }

    /**
     * Test des mains contenant une pair de carte pareil qui vallent valet ou mieux
     * Multiplicateur désiré: 1
     * @throws Exception
     */
    public void testAvoirPairFigures() throws Exception{
        VideoPoker instance = null;
        instance = VideoPoker.avoirInstance();
        instance.mise = 1;
        instance.paquetFinal.clear();

        instance.paquetFinal.add(new Carte(11, Color.RED, 0, "" + JeuDeCarte.type.Carre + "" + (1), JeuDeCarte.type.Carre));
        instance.paquetFinal.add(new Carte(11, Color.RED, 3, "" + JeuDeCarte.type.Coeur + "" + (1), JeuDeCarte.type.Coeur));
        instance.paquetFinal.add(new Carte(2, Color.RED, 3, "" + JeuDeCarte.type.Coeur + "" + (1), JeuDeCarte.type.Coeur));
        instance.paquetFinal.add(new Carte(3, Color.BLACK, 2, "" + JeuDeCarte.type.Pique + "" + (1), JeuDeCarte.type.Pique));
        instance.paquetFinal.add(new Carte(4, Color.BLACK, 1, "" + JeuDeCarte.type.Trèfle + "" + (1), JeuDeCarte.type.Trèfle));
        instance.paquetFinal.ordonnerCartes();
        float points = instance.compterPoints();
        assertTrue("Pair Valet pas détecté : " + points, points == 1);
        instance.paquetFinal.set(0, new Carte(12, Color.RED, 0, "" + JeuDeCarte.type.Carre + "" + (1), JeuDeCarte.type.Carre));
        instance.paquetFinal.set(1, new Carte(12, Color.RED, 3, "" + JeuDeCarte.type.Coeur + "" + (1), JeuDeCarte.type.Coeur));
        instance.paquetFinal.set(2, new Carte(2, Color.RED, 3, "" + JeuDeCarte.type.Coeur + "" + (1), JeuDeCarte.type.Coeur));
        instance.paquetFinal.set(3,new Carte(3, Color.BLACK, 2, "" + JeuDeCarte.type.Pique + "" + (1), JeuDeCarte.type.Pique));
        instance.paquetFinal.set(4,new Carte(4, Color.BLACK, 1, "" + JeuDeCarte.type.Trèfle + "" + (1), JeuDeCarte.type.Trèfle));
        instance.paquetFinal.ordonnerCartes();
        points = instance.compterPoints();
        assertTrue("Pair Reine pas détecté : " + points, points == 1);
        instance.paquetFinal.set(0, new Carte(13, Color.RED, 0, "" + JeuDeCarte.type.Carre + "" + (1), JeuDeCarte.type.Carre));
        instance.paquetFinal.set(1, new Carte(13, Color.RED, 3, "" + JeuDeCarte.type.Coeur + "" + (1), JeuDeCarte.type.Coeur));
        instance.paquetFinal.set(2, new Carte(2, Color.RED, 3, "" + JeuDeCarte.type.Coeur + "" + (1), JeuDeCarte.type.Coeur));
        instance.paquetFinal.set(3,new Carte(3, Color.BLACK, 2, "" + JeuDeCarte.type.Pique + "" + (1), JeuDeCarte.type.Pique));
        instance.paquetFinal.set(4,new Carte(4, Color.BLACK, 1, "" + JeuDeCarte.type.Trèfle + "" + (1), JeuDeCarte.type.Trèfle));
        instance.paquetFinal.ordonnerCartes();
        points = instance.compterPoints();
        assertTrue("Pair Roi pas détecté : " + points, points == 1);
        instance.paquetFinal.set(0, new Carte(1, Color.RED, 0, "" + JeuDeCarte.type.Carre + "" + (1), JeuDeCarte.type.Carre));
        instance.paquetFinal.set(1, new Carte(1, Color.RED, 3, "" + JeuDeCarte.type.Coeur + "" + (1), JeuDeCarte.type.Coeur));
        instance.paquetFinal.set(2, new Carte(2, Color.RED, 3, "" + JeuDeCarte.type.Coeur + "" + (1), JeuDeCarte.type.Coeur));
        instance.paquetFinal.set(3,new Carte(3, Color.BLACK, 2, "" + JeuDeCarte.type.Pique + "" + (1), JeuDeCarte.type.Pique));
        instance.paquetFinal.set(4,new Carte(4, Color.BLACK, 1, "" + JeuDeCarte.type.Trèfle + "" + (1), JeuDeCarte.type.Trèfle));
        instance.paquetFinal.ordonnerCartes();
        points = instance.compterPoints();
        assertTrue("Pair As pas détecté : " + points, points == 1);
        instance.paquetFinal.set(0, new Carte(9, Color.RED, 0, "" + JeuDeCarte.type.Carre + "" + (1), JeuDeCarte.type.Carre));
        instance.paquetFinal.set(1, new Carte(9, Color.RED, 3, "" + JeuDeCarte.type.Coeur + "" + (1), JeuDeCarte.type.Coeur));
        instance.paquetFinal.set(2, new Carte(2, Color.RED, 3, "" + JeuDeCarte.type.Coeur + "" + (1), JeuDeCarte.type.Coeur));
        instance.paquetFinal.set(3,new Carte(3, Color.BLACK, 2, "" + JeuDeCarte.type.Pique + "" + (1), JeuDeCarte.type.Pique));
        instance.paquetFinal.set(4,new Carte(4, Color.BLACK, 1, "" + JeuDeCarte.type.Trèfle + "" + (1), JeuDeCarte.type.Trèfle));
        instance.paquetFinal.ordonnerCartes();
        points = instance.compterPoints();
        assertTrue("Pair 9 compter comme une pair de figure: " +points, points == 0);
    }

    /**
     * Test des mains qui on deux pairs
     * Multiplicateur désiré: 2
     * @throws Exception
     */
    public void testAvoirDeuxPairs() throws Exception{
        VideoPoker instance = null;
        instance = VideoPoker.avoirInstance();
        instance.mise = 1;
        instance.paquetFinal.clear();

        instance.paquetFinal.add(new Carte(11, Color.RED, 0, "" + JeuDeCarte.type.Carre + "" + (1), JeuDeCarte.type.Carre));
        instance.paquetFinal.add(new Carte(11, Color.RED, 0, "" + JeuDeCarte.type.Carre + "" + (1), JeuDeCarte.type.Carre));
        instance.paquetFinal.add(new Carte(11, Color.RED, 0, "" + JeuDeCarte.type.Carre + "" + (1), JeuDeCarte.type.Carre));
        instance.paquetFinal.add(new Carte(11, Color.RED, 0, "" + JeuDeCarte.type.Carre + "" + (1), JeuDeCarte.type.Carre));
        instance.paquetFinal.add(new Carte(11, Color.RED, 0, "" + JeuDeCarte.type.Carre + "" + (1), JeuDeCarte.type.Carre));
        float points = 0;


        for (int x = 1 ; x < 6; x++)
        {
            instance.paquetFinal.set(0, new Carte(x, Color.RED, 0, "" + JeuDeCarte.type.Carre + "" + (1), JeuDeCarte.type.Carre));
            instance.paquetFinal.set(1, new Carte(x, Color.RED, 3, "" + JeuDeCarte.type.Coeur + "" + (1), JeuDeCarte.type.Coeur));
            instance.paquetFinal.set(2, new Carte(13, Color.RED, 3, "" + JeuDeCarte.type.Coeur + "" + (1), JeuDeCarte.type.Coeur));
            instance.paquetFinal.set(3, new Carte(13 - x, Color.BLACK, 2, "" + JeuDeCarte.type.Pique + "" + (1), JeuDeCarte.type.Pique));
            instance.paquetFinal.set(4, new Carte(13 - x, Color.BLACK, 1, "" + JeuDeCarte.type.Trèfle + "" + (1), JeuDeCarte.type.Trèfle));
            instance.paquetFinal.ordonnerCartes();
            points = instance.compterPoints();
            assertTrue("Double pair invalide: " + points + " Dans l'itération "+x+" /5", points == 2);
        }

    }

    /**
     * Test des mains qui on 3 de la même valeur de carte
     * Multiplicateur désiré: 3
     * @throws Exception
     */
    public void testAvoirTroisPareil() throws Exception{
        VideoPoker instance = null;
        instance = VideoPoker.avoirInstance();
        instance.mise = 1;
        instance.paquetFinal.clear();

        instance.paquetFinal.add(new Carte(11, Color.RED, 0, "" + JeuDeCarte.type.Carre + "" + (1), JeuDeCarte.type.Carre));
        instance.paquetFinal.add(new Carte(11, Color.RED, 0, "" + JeuDeCarte.type.Carre + "" + (1), JeuDeCarte.type.Carre));
        instance.paquetFinal.add(new Carte(11, Color.RED, 0, "" + JeuDeCarte.type.Carre + "" + (1), JeuDeCarte.type.Carre));
        instance.paquetFinal.add(new Carte(11, Color.RED, 0, "" + JeuDeCarte.type.Carre + "" + (1), JeuDeCarte.type.Carre));
        instance.paquetFinal.add(new Carte(11, Color.RED, 0, "" + JeuDeCarte.type.Carre + "" + (1), JeuDeCarte.type.Carre));
        float points = 0;


        for (int x = 1 ; x < 10; x++)
        {
            instance.paquetFinal.set(0, new Carte(x, Color.RED, 0, "" + JeuDeCarte.type.Carre + "" + (1), JeuDeCarte.type.Carre));
            instance.paquetFinal.set(1, new Carte(x, Color.RED, 3, "" + JeuDeCarte.type.Coeur + "" + (1), JeuDeCarte.type.Coeur));
            instance.paquetFinal.set(2, new Carte(13, Color.RED, 3, "" + JeuDeCarte.type.Coeur + "" + (1), JeuDeCarte.type.Coeur));
            instance.paquetFinal.set(3, new Carte(x, Color.BLACK, 2, "" + JeuDeCarte.type.Pique + "" + (1), JeuDeCarte.type.Pique));
            instance.paquetFinal.set(4, new Carte(12, Color.BLACK, 1, "" + JeuDeCarte.type.Trèfle + "" + (1), JeuDeCarte.type.Trèfle));
            instance.paquetFinal.ordonnerCartes();
            points = instance.compterPoints();
            assertTrue("3 Pareil invalide: " + points + " Dans l'itération "+x+" /10", points == 3);
        }
    }

    /**
     * Test des mains qui sont une suite ext 2 à 6
     * Multiplicateur désiré: 4
     * @throws Exception
     */
    public void testAvoirSuite() throws Exception{
        VideoPoker instance = null;
        instance = VideoPoker.avoirInstance();
        instance.mise = 1;
        instance.paquetFinal.clear();

        instance.paquetFinal.add(new Carte(11, Color.RED, 0, "" + JeuDeCarte.type.Carre + "" + (1), JeuDeCarte.type.Carre));
        instance.paquetFinal.add(new Carte(11, Color.RED, 0, "" + JeuDeCarte.type.Carre + "" + (1), JeuDeCarte.type.Carre));
        instance.paquetFinal.add(new Carte(11, Color.RED, 0, "" + JeuDeCarte.type.Carre + "" + (1), JeuDeCarte.type.Carre));
        instance.paquetFinal.add(new Carte(11, Color.RED, 0, "" + JeuDeCarte.type.Carre + "" + (1), JeuDeCarte.type.Carre));
        instance.paquetFinal.add(new Carte(11, Color.RED, 0, "" + JeuDeCarte.type.Carre + "" + (1), JeuDeCarte.type.Carre));
        float points = 0;


        for (int x = 1 ; x < 9; x++)
        {
            instance.paquetFinal.set(0, new Carte(x, Color.RED, 0, "" + JeuDeCarte.type.Carre + "" + (1), JeuDeCarte.type.Carre));
            instance.paquetFinal.set(1, new Carte(x+1, Color.RED, 3, "" + JeuDeCarte.type.Coeur + "" + (1), JeuDeCarte.type.Coeur));
            instance.paquetFinal.set(2, new Carte(x+2, Color.RED, 3, "" + JeuDeCarte.type.Coeur + "" + (1), JeuDeCarte.type.Coeur));
            instance.paquetFinal.set(3, new Carte(x+3, Color.BLACK, 2, "" + JeuDeCarte.type.Pique + "" + (1), JeuDeCarte.type.Pique));
            instance.paquetFinal.set(4, new Carte(x+4, Color.BLACK, 1, "" + JeuDeCarte.type.Trèfle + "" + (1), JeuDeCarte.type.Trèfle));
            instance.paquetFinal.ordonnerCartes();
            points = instance.compterPoints();
            assertTrue("Suite invalide: " + points + " Dans l'itération "+x+" /9", points == 4);
        }
    }

    /**
     * Test des mains qui on 5 cartes de la même couleur/symbole
     * le champ sorte (qui est un int) sert a la logique pour tester la couleur. c'est pour cela que le x n'a pas besoin de changer les types de cartes ou leur couleurs
     * Multiplicateur désiré: 6
     * @throws Exception
     */
    public void testAvoirCouleurs() throws Exception{
        VideoPoker instance = null;
        instance = VideoPoker.avoirInstance();
        instance.mise = 1;
        instance.paquetFinal.clear();

        instance.paquetFinal.add(new Carte(11, Color.RED, 0, "" + JeuDeCarte.type.Carre + "" + (1), JeuDeCarte.type.Carre));
        instance.paquetFinal.add(new Carte(11, Color.RED, 0, "" + JeuDeCarte.type.Carre + "" + (1), JeuDeCarte.type.Carre));
        instance.paquetFinal.add(new Carte(11, Color.RED, 0, "" + JeuDeCarte.type.Carre + "" + (1), JeuDeCarte.type.Carre));
        instance.paquetFinal.add(new Carte(11, Color.RED, 0, "" + JeuDeCarte.type.Carre + "" + (1), JeuDeCarte.type.Carre));
        instance.paquetFinal.add(new Carte(11, Color.RED, 0, "" + JeuDeCarte.type.Carre + "" + (1), JeuDeCarte.type.Carre));
        float points = 0;


        for (int x = 0 ; x < 4; x++)
        {
            instance.paquetFinal.set(0, new Carte(1, Color.RED, x, "" + JeuDeCarte.type.Carre + "" + (1), JeuDeCarte.type.Carre));
            instance.paquetFinal.set(1, new Carte(3, Color.RED, x, "" + JeuDeCarte.type.Coeur + "" + (1), JeuDeCarte.type.Coeur));
            instance.paquetFinal.set(2, new Carte(5, Color.RED, x, "" + JeuDeCarte.type.Coeur + "" + (1), JeuDeCarte.type.Coeur));
            instance.paquetFinal.set(3, new Carte(7, Color.BLACK, x, "" + JeuDeCarte.type.Pique + "" + (1), JeuDeCarte.type.Pique));
            instance.paquetFinal.set(4, new Carte(9, Color.BLACK, x, "" + JeuDeCarte.type.Trèfle + "" + (1), JeuDeCarte.type.Trèfle));
            instance.paquetFinal.ordonnerCartes();
            points = instance.compterPoints();
            assertTrue("Couleurs invalide: " + points + " Dans l'itération "+x+" /4", points == 6);
        }
    }

    /**
     * test des mains qui on une pair et 3 autres carte identique
     * Multiplicateur désiré: 9
     * @throws Exception
     */
    public void testAvoirMaisonPleine() throws Exception{
        VideoPoker instance = null;
        instance = VideoPoker.avoirInstance();
        instance.mise = 1;
        instance.paquetFinal.clear();

        instance.paquetFinal.add(new Carte(11, Color.RED, 0, "" + JeuDeCarte.type.Carre + "" + (1), JeuDeCarte.type.Carre));
        instance.paquetFinal.add(new Carte(11, Color.RED, 0, "" + JeuDeCarte.type.Carre + "" + (1), JeuDeCarte.type.Carre));
        instance.paquetFinal.add(new Carte(11, Color.RED, 0, "" + JeuDeCarte.type.Carre + "" + (1), JeuDeCarte.type.Carre));
        instance.paquetFinal.add(new Carte(11, Color.RED, 0, "" + JeuDeCarte.type.Carre + "" + (1), JeuDeCarte.type.Carre));
        instance.paquetFinal.add(new Carte(11, Color.RED, 0, "" + JeuDeCarte.type.Carre + "" + (1), JeuDeCarte.type.Carre));
        float points = 0;

        for (int x = 1 ; x < 6; x++)
        {
            instance.paquetFinal.set(0, new Carte(x, Color.RED, 0, "" + JeuDeCarte.type.Carre + "" + (1), JeuDeCarte.type.Carre));
            instance.paquetFinal.set(1, new Carte(x, Color.RED, 3, "" + JeuDeCarte.type.Coeur + "" + (1), JeuDeCarte.type.Coeur));
            instance.paquetFinal.set(2, new Carte(13-x, Color.RED, 3, "" + JeuDeCarte.type.Coeur + "" + (1), JeuDeCarte.type.Coeur));
            instance.paquetFinal.set(3, new Carte(x, Color.BLACK, 2, "" + JeuDeCarte.type.Pique + "" + (1), JeuDeCarte.type.Pique));
            instance.paquetFinal.set(4, new Carte(13 -x, Color.BLACK, 1, "" + JeuDeCarte.type.Trèfle + "" + (1), JeuDeCarte.type.Trèfle));
            instance.paquetFinal.ordonnerCartes();
            points = instance.compterPoints();
            assertTrue("Maison Pleine invalide: " + points + " Dans l'itération "+x+" /6", points == 9);
        }
    }

    /**
     * test des mains qui on 4 cartes pareils
     * Multiplicateur désiré: 25
     * @throws Exception
     */
    public void testAvoirQuatrePareil() throws Exception{
        VideoPoker instance = null;
        instance = VideoPoker.avoirInstance();
        instance.mise = 1;
        instance.paquetFinal.clear();

        instance.paquetFinal.add(new Carte(11, Color.RED, 0, "" + JeuDeCarte.type.Carre + "" + (1), JeuDeCarte.type.Carre));
        instance.paquetFinal.add(new Carte(11, Color.RED, 0, "" + JeuDeCarte.type.Carre + "" + (1), JeuDeCarte.type.Carre));
        instance.paquetFinal.add(new Carte(11, Color.RED, 0, "" + JeuDeCarte.type.Carre + "" + (1), JeuDeCarte.type.Carre));
        instance.paquetFinal.add(new Carte(11, Color.RED, 0, "" + JeuDeCarte.type.Carre + "" + (1), JeuDeCarte.type.Carre));
        instance.paquetFinal.add(new Carte(11, Color.RED, 0, "" + JeuDeCarte.type.Carre + "" + (1), JeuDeCarte.type.Carre));
        float points = 0;

        for (int x = 1 ; x < 12; x++)
        {
            instance.paquetFinal.set(0, new Carte(x, Color.RED, 0, "" + JeuDeCarte.type.Carre + "" + (1), JeuDeCarte.type.Carre));
            instance.paquetFinal.set(1, new Carte(x, Color.RED, 3, "" + JeuDeCarte.type.Coeur + "" + (1), JeuDeCarte.type.Coeur));
            instance.paquetFinal.set(2, new Carte(x, Color.RED, 3, "" + JeuDeCarte.type.Coeur + "" + (1), JeuDeCarte.type.Coeur));
            instance.paquetFinal.set(3, new Carte(x, Color.BLACK, 2, "" + JeuDeCarte.type.Pique + "" + (1), JeuDeCarte.type.Pique));
            instance.paquetFinal.set(4, new Carte(13, Color.BLACK, 1, "" + JeuDeCarte.type.Trèfle + "" + (1), JeuDeCarte.type.Trèfle));
            instance.paquetFinal.ordonnerCartes();
            points = instance.compterPoints();
            assertTrue("Quatre pareil invalide: " + points + " Dans l'itération "+x+" /6", points == 25);
        }
    }

    /**
     * test des mains qui on une suite de la même couleur/symbole
     * Multiplicateur désiré: 50
     * @throws Exception
     */
    public void testAvoirSuiteCouleurs() throws Exception{
        VideoPoker instance = null;
        instance = VideoPoker.avoirInstance();
        instance.mise = 1;
        instance.paquetFinal.clear();

        instance.paquetFinal.add(new Carte(11, Color.RED, 0, "" + JeuDeCarte.type.Carre + "" + (1), JeuDeCarte.type.Carre));
        instance.paquetFinal.add(new Carte(11, Color.RED, 0, "" + JeuDeCarte.type.Carre + "" + (1), JeuDeCarte.type.Carre));
        instance.paquetFinal.add(new Carte(11, Color.RED, 0, "" + JeuDeCarte.type.Carre + "" + (1), JeuDeCarte.type.Carre));
        instance.paquetFinal.add(new Carte(11, Color.RED, 0, "" + JeuDeCarte.type.Carre + "" + (1), JeuDeCarte.type.Carre));
        instance.paquetFinal.add(new Carte(11, Color.RED, 0, "" + JeuDeCarte.type.Carre + "" + (1), JeuDeCarte.type.Carre));
        float points = 0;


        for (int x = 1 ; x < 9; x++)
        {
            for(int y = 0 ; y < 4; y++)
            {
                instance.paquetFinal.set(0, new Carte(x, Color.RED, y, "" + JeuDeCarte.type.Carre + "" + (1), JeuDeCarte.type.Carre));
                instance.paquetFinal.set(1, new Carte(x + 1, Color.RED, y, "" + JeuDeCarte.type.Coeur + "" + (1), JeuDeCarte.type.Coeur));
                instance.paquetFinal.set(2, new Carte(x + 2, Color.RED, y, "" + JeuDeCarte.type.Coeur + "" + (1), JeuDeCarte.type.Coeur));
                instance.paquetFinal.set(3, new Carte(x + 3, Color.BLACK, y, "" + JeuDeCarte.type.Pique + "" + (1), JeuDeCarte.type.Pique));
                instance.paquetFinal.set(4, new Carte(x + 4, Color.BLACK, y, "" + JeuDeCarte.type.Trèfle + "" + (1), JeuDeCarte.type.Trèfle));
                instance.paquetFinal.ordonnerCartes();
                points = instance.compterPoints();
                assertTrue("Suite invalide: " + points + " Dans l'itération " + x + " /9 - "+ y + " /4", points == 50);
            }
        }
    }

    /**
     * Test des mains contenant une SuiteRoyale
     * Multiplicateur désiré: 250
     * @throws Exception
     */
    public void testAvoirSuiteRoyale() throws Exception{
        VideoPoker instance = null;
        instance = VideoPoker.avoirInstance();
        instance.mise = 1;
        instance.paquetFinal.clear();

        instance.paquetFinal.add(new Carte(11, Color.RED, 0, "" + JeuDeCarte.type.Carre + "" + (1), JeuDeCarte.type.Carre));
        instance.paquetFinal.add(new Carte(11, Color.RED, 0, "" + JeuDeCarte.type.Carre + "" + (1), JeuDeCarte.type.Carre));
        instance.paquetFinal.add(new Carte(11, Color.RED, 0, "" + JeuDeCarte.type.Carre + "" + (1), JeuDeCarte.type.Carre));
        instance.paquetFinal.add(new Carte(11, Color.RED, 0, "" + JeuDeCarte.type.Carre + "" + (1), JeuDeCarte.type.Carre));
        instance.paquetFinal.add(new Carte(11, Color.RED, 0, "" + JeuDeCarte.type.Carre + "" + (1), JeuDeCarte.type.Carre));
        float points = 0;


        for (int x = 0 ; x < 4; x++)
        {
            instance.paquetFinal.set(0, new Carte(1, Color.RED, x, "" + JeuDeCarte.type.Carre + "" + (1), JeuDeCarte.type.Carre));
            instance.paquetFinal.set(1, new Carte(10, Color.RED, x, "" + JeuDeCarte.type.Coeur + "" + (1), JeuDeCarte.type.Coeur));
            instance.paquetFinal.set(2, new Carte(11, Color.RED, x, "" + JeuDeCarte.type.Coeur + "" + (1), JeuDeCarte.type.Coeur));
            instance.paquetFinal.set(3, new Carte(12, Color.BLACK, x, "" + JeuDeCarte.type.Pique + "" + (1), JeuDeCarte.type.Pique));
            instance.paquetFinal.set(4, new Carte(13, Color.BLACK, x, "" + JeuDeCarte.type.Trèfle + "" + (1), JeuDeCarte.type.Trèfle));
            instance.paquetFinal.ordonnerCartes();
            points = instance.compterPoints();
            assertTrue("SuiteRoyale invalide: " + points + " Dans l'itération "+x+" /4", points == 250);
        }
    }



}