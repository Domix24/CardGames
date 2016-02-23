package video.poker;

import junit.framework.TestCase;

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

    }

    /**
     * Test des mains qui on deux pairs
     * Multiplicateur désiré: 2
     * @throws Exception
     */
    public void testAvoirDeuxPairs() throws Exception{

    }

    /**
     * Test des mains qui on 3 de la même valeur de carte
     * Multiplicateur désiré: 3
     * @throws Exception
     */
    public void testAvoirTroisPareil() throws Exception{

    }

    /**
     * Test des mains qui sont une suite ext 2 à 6
     * Multiplicateur désiré: 4
     * @throws Exception
     */
    public void testAvoirSuite() throws Exception{

    }

    /**
     * Test des mains qui on 5 cartes de la même couleur/symbole
     * Multiplicateur désiré: 6
     * @throws Exception
     */
    public void testAvoirCouleurs() throws Exception{

    }

    /**
     * test des mains qui on une pair et 3 autres carte identique
     * Multiplicateur désiré: 9
     * @throws Exception
     */
    public void testAvoirMaisonPleine() throws Exception{

    }

    /**
     * test des mains qui on 4 cartes pareils
     * Multiplicateur désiré: 25
     * @throws Exception
     */
    public void testAvoirQuatrePareil() throws Exception{

    }

    /**
     * test des mains qui on une suite de la même couleur/symbole
     * Multiplicateur désiré: 50
     * @throws Exception
     */
    public void testAvoirSuiteCouleurs() throws Exception{

    }

    /**
     * Test des mains contenant une SuiteRoyale
     * Multiplicateur désiré: 250
     * @throws Exception
     */
    public void testAvoirSuiteRoyale() throws Exception{

    }



}