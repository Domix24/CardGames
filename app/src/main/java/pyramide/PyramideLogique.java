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

    public PyramideLogique() {
        CommencerNouvellePartie();
    }

    /**
     * Méthode commençant une nouvelle partie
     */
    public void CommencerNouvellePartie() {
        paquet = new JeuDeCarte();
        pyramideArray = new ArrayList<Carte[]>();
        lstStock = new ArrayList<Carte>();
        lstWaste = new ArrayList<Carte>();
        EnvoyerPaquetAPyramide();
        RemplirStock();
    }

    /**
     * Envoyer la carte du dessus du stock sur le dessus du waste
     */
    public void EnvoyerCarteAuWaste() {
        lstWaste.add(lstStock.get(lstStock.size() - 1));
        lstStock.remove(lstStock.size() - 1);
    }

    /**
     * @return La carte sur le dessus du waste pour pouvoir l'afficher et s'en servir dans les paires
     */
    public Carte GetCarteDessusWaste() {
        return lstWaste.get(lstWaste.size() - 1);
    }

    /**
     * @return L'array d'array de cartes représentant la pyramide (pour pouvoir l'afficher)
     */
    public ArrayList<Carte[]> GetCartesPyramide() {
        return pyramideArray;
    }

    /**
     * @return Le nombre de cartes dans la liste du stock
     */
    public int GetNombreStock() {
        return lstStock.size();
    }

    /**
     * Enlève la carte de la pyramide si c'est un roi non-obstrué par une autre carte
     * @param rangée
     * @param colonne
     * @return True si la carte a été enlevée de la pyramide, false sinon
     */
    public boolean EnleverCartes(int rangée, int colonne) {
        boolean carteEnlevée = false;

        if (pyramideArray.get(rangée)[colonne] != null &&
                pyramideArray.get(rangée)[colonne].numero == 13) {
            if (DéterminerDisponibilité(rangée, colonne)) {
                pyramideArray.get(rangée)[colonne] = null;
                carteEnlevée = true;
            }
        }

        return carteEnlevée;
    }

    /**
     * Enlève les deux cartes de la pyramide si elles ont un total de valeurs de 13
     * et qu'elles sont ni l'une ni l'autre obstruée
     * @param rangée1 Rangée de la première carte
     * @param colonne1 Colonne de la première carte
     * @param rangée2 Rangée de la deuxième carte
     * @param colonne2 Colonne de la deuxième carte
     * @return True si les cartes ont été enlevées, False sinon
     */
    public boolean EnleverCartes(int rangée1, int colonne1, int rangée2, int colonne2) {
        boolean cartesEnlevées = false;

        if (pyramideArray.get(rangée1)[colonne1] != null &&
                pyramideArray.get(rangée2)[colonne2] != null) {

            if ((pyramideArray.get(rangée1)[colonne1].numero + pyramideArray.get(rangée2)[colonne2].numero) == 13) {

                if (DéterminerDisponibilité(rangée1, colonne1) == true &&
                        DéterminerDisponibilité(rangée2, colonne2) == true) {

                    pyramideArray.get(rangée1)[colonne1] = null;
                    pyramideArray.get(rangée2)[colonne2] = null;

                    cartesEnlevées = true;
                }
            }
        }

        return cartesEnlevées;
    }

    /**
     * Vérifie si la partie est terminée. Elle est terminée soit par une victoire ou une défaite
     * Elle n'est pas terminée s'il reste des mouvements possibles
     * @return True si partie terminée, faux sinon
     */
    public boolean vérifierFinPartie() {
        boolean partieTerminée = false;
        boolean pyramideVide = false;


        // Si la pyramide est vide, la partie est gagnée
        do {

        } while (pyramideVide);

        return false;
    }

    /**
     * Piger 28 cartes du paquet et les envoyer dans la pyramide
     */
    private void EnvoyerPaquetAPyramide() {
        // Vérifier que le paquet est "neuf"
        if (paquet.size() == 52) {
            List<Carte> lstTemp = new ArrayList<Carte>();


            for (int i = 0; i < 28; i++) {
                lstTemp.add(paquet.pigerUneCarte());
            }

            RemplirPyramide(lstTemp);
        }
    }

    /**
     * Remplir les 7 rangées de la pyramide avec les cartes du paquet
     * @param lstpyramide La liste des 28 cartes à mettre dans la pyramide
     */
    private void RemplirPyramide(List<Carte> lstpyramide) {
        if (lstpyramide.size() == 28) {
            pyramideArray.add(new Carte[1]);
            pyramideArray.add(new Carte[2]);
            pyramideArray.add(new Carte[3]);
            pyramideArray.add(new Carte[4]);
            pyramideArray.add(new Carte[5]);
            pyramideArray.add(new Carte[6]);
            pyramideArray.add(new Carte[7]);

            for (int i = 0; i < 7; i++) {
                if (i == 0) {
                    pyramideArray.get(0)[i] = lstpyramide.get(lstpyramide.size() - 1);
                    lstpyramide.remove(lstpyramide.size() - 1);
                }
                if (i <= 1) {
                    pyramideArray.get(1)[i] = lstpyramide.get(lstpyramide.size() - 1);
                    lstpyramide.remove(lstpyramide.size() - 1);
                }
                if (i <= 2) {
                    pyramideArray.get(2)[i] = lstpyramide.get(lstpyramide.size() - 1);
                    lstpyramide.remove(lstpyramide.size() - 1);
                }
                if (i <= 3) {
                    pyramideArray.get(3)[i] = lstpyramide.get(lstpyramide.size() - 1);
                    lstpyramide.remove(lstpyramide.size() - 1);
                }
                if (i <= 4) {
                    pyramideArray.get(4)[i] = lstpyramide.get(lstpyramide.size() - 1);
                    lstpyramide.remove(lstpyramide.size() - 1);
                }
                if (i <= 5) {
                    pyramideArray.get(5)[i] = lstpyramide.get(lstpyramide.size() - 1);
                    lstpyramide.remove(lstpyramide.size() - 1);
                }
                if (i <= 6) {
                    pyramideArray.get(6)[i] = lstpyramide.get(lstpyramide.size() - 1);
                    lstpyramide.remove(lstpyramide.size() - 1);
                }
            }
        }
    }

    /**
     * Rempli le stock avec les 24 cartes restantes dans le paquet
     */
    private void RemplirStock() {
        for (int i = 0; i < 24; i++) {
            lstStock.add(paquet.pigerUneCarte());
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

    /**
     * Retourne l'id de la carte (ex: pour pouvoir afficher l'image associée à la carte)
     * @param nom Le nom de l'objet carte
     * @return L'ID de la carte
     */
    public int trouverIdCarte(String nom) {
        return paquet.trouverIdCarte(nom);
    }

}
