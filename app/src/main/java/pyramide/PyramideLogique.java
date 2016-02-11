package pyramide;

import java.util.ArrayList;
import java.util.List;

import utilitaire.Carte;
import utilitaire.JeuDeCarte;

/**
 * Classe contenant la logique du jeu Pyramide
 * @author Maxime Noel, Jean-Michel Lavoie le 2016-02-11.
 */
public class PyramideLogique {
    private JeuDeCarte paquet;
    private ArrayList<Carte[]> pyramideArray;
    private List<Carte> lstStock;
    private List<Carte> lstWaste;

    public void PyramideLogique() {
        CommencerNouvellePartie();
    }

    /**
     * Méthode commençant une nouvelle partie
     */
    public void CommencerNouvellePartie() {
        paquet = new JeuDeCarte();
        pyramideArray = new ArrayList<Carte[]>();
        lstStock.clear();
        lstWaste.clear();
        RemplirPyramide();
        RemplirStock();
    }

    public void EnvoyerCarteAuWaste() {
        lstWaste.add(lstStock.get(lstStock.size() - 1));
        lstStock.remove(lstStock.size() - 1);
    }

    public Carte GetCarteDessusWaste() {
        return lstWaste.get(lstWaste.size() - 1);
    }

    public ArrayList<Carte[]> GetCartesPyramide() {
        return pyramideArray;
    }

    /**
     * Enlève la carte de la pyramide si c'est un roi non-obstrué par une autre carte
     */
    public void EnleverCartes(int rangée, int colonne) {
        
    }

    private void RemplirPyramide() {
        pyramideArray.add(new Carte[1]);
        pyramideArray.add(new Carte[2]);
        pyramideArray.add(new Carte[3]);
        pyramideArray.add(new Carte[4]);
        pyramideArray.add(new Carte[5]);
        pyramideArray.add(new Carte[6]);
        pyramideArray.add(new Carte[7]);

        for(int i=0;i<7;i++) {
            if (i == 0)
                pyramideArray.get(0)[i] = paquet.PigerUneCarte();
            if (i <= 1)
                pyramideArray.get(1)[i] = paquet.PigerUneCarte();
            if (i <= 2)
                pyramideArray.get(2)[i] = paquet.PigerUneCarte();
            if (i <= 3)
                pyramideArray.get(3)[i] = paquet.PigerUneCarte();
            if (i <= 4)
                pyramideArray.get(4)[i] = paquet.PigerUneCarte();
            if (i <= 5)
                pyramideArray.get(5)[i] = paquet.PigerUneCarte();
            if (i <= 6) {
                pyramideArray.get(6)[i] = paquet.PigerUneCarte();
            }
        }
    }

    private void RemplirStock() {
        for (int i = 0; i < 24; i++) {
            lstStock.add(paquet.PigerUneCarte());
        }
    }

    /**
     * @param rangée Rangée dans laquelle la carte à enlever est située
     * @param colonne Colonne dans laquelle la carte à enlever est située
     * @return Vrai si la carte est disponible, Faux si la carte est bloquée
     */
    private boolean DéterminerDisponibilité(int rangée, int colonne) {
        boolean disponible = false;

        if (rangée < 7) {
            if (rangée == 7) {
                if (colonne >= 0 && colonne < 7)
                    disponible = true;
            }
            else {
                // vérifier si les deux cartes en dessous sont encore là
                // Si l'index n'existe pas, il n'y a donc pas de carte (catch)
                try {
                    if (pyramideArray.get(rangée + 1)[colonne] != null)
                        disponible = false;
                }
                catch (Exception e) {
                    disponible = true;
                }

                try {
                    if (pyramideArray.get(rangée + 1)[colonne + 1] != null)
                        disponible = false;
                }
                catch (Exception e) {
                    disponible = true;
                }
            }
        }

        return disponible;
    }


}
