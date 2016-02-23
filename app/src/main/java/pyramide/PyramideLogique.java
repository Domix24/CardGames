package pyramide;

import java.util.ArrayList;
import java.util.List;

import utilitaire.Carte;
import utilitaire.JeuAvecCartes;
import utilitaire.JeuDeCarte;

/**
 * Classe contenant la logique du jeu Pyramide
 * @author Maxime Noel, Jean-Michel Lavoie le 2016-02-11.
 */
public class PyramideLogique extends JeuAvecCartes {
    private ArrayList<Carte[]> pyramideArray;
    private List<Carte> lstStock;
    private List<Carte> lstWaste;
    private boolean partieTerminée;
    private boolean partieGagnée;

    public PyramideLogique() {
        commencerNouvellePartie();
    }

    /**
     * Méthode commençant une nouvelle partie
     */
    public void commencerNouvellePartie() {
        paquet = new JeuDeCarte();
        pyramideArray = new ArrayList<Carte[]>();
        lstStock = new ArrayList<Carte>();
        lstWaste = new ArrayList<Carte>();
        partieTerminée = false;
        partieGagnée = false;
        envoyerPaquetAPyramide();
        remplirStock();
    }

    /**
     * Envoyer la carte du dessus du stock sur le dessus du waste
     */
    public void envoyerCarteAuWaste() {
        lstWaste.add(lstStock.get(lstStock.size() - 1));
        lstStock.remove(lstStock.size() - 1);
    }

    /**
     * @return La carte sur le dessus du waste pour pouvoir l'afficher et s'en servir dans les paires
     */
    public Carte getCarteDessusWaste() {
        return lstWaste.get(lstWaste.size() - 1);
    }

    /**
     * @return L'array d'array de cartes représentant la pyramide (pour pouvoir l'afficher)
     */
    public ArrayList<Carte[]> getCartesPyramide() {
        return pyramideArray;
    }

    /**
     * @return Le nombre de cartes dans la liste du stock
     */
    public int getNombreStock() {
        return lstStock.size();
    }

    /**
     * Enlève la carte de la pyramide si c'est un roi non-obstrué par une autre carte
     * Pour cliquer le waste, passer rangée 7, colonne pas importante
     * @param rangée
     * @param colonne
     * @return True si la carte a été enlevée de la pyramide, false sinon
     */
    public boolean enleverCartes(int rangée, int colonne) {
        boolean carteEnlevée = false;

        try { // Si le try plante, c'est que un paramètre en dehors de la pyramide a été passé
            if (rangée <= 7 && rangée >= 0) {
                if (rangée == 7) { // Rangée 7 est le waste
                    if (lstWaste.get(lstWaste.size() - 1).numero == 13) {
                        enleverDessusWaste();
                        carteEnlevée = true;
                    }
                } else {
                    if (pyramideArray.get(rangée)[colonne] != null &&
                            pyramideArray.get(rangée)[colonne].numero == 13) {
                        if (déterminerDisponibilité(rangée, colonne)) {
                            pyramideArray.get(rangée)[colonne] = null;
                            carteEnlevée = true;
                        }
                    }
                }
            }
        } catch (Exception e) {} // Si le try plante, c'est que un paramètre en dehors de la pyramide a été passé

        return carteEnlevée;
    }

    /**
     * Enlève les deux cartes de la pyramide si elles ont un total de valeurs de 13
     * et qu'elles sont ni l'une ni l'autre obstruée
     * Pour cliquer le waste, passer rangée 7, colonne pas importante
     * @param rangée1 Rangée de la première carte
     * @param colonne1 Colonne de la première carte
     * @param rangée2 Rangée de la deuxième carte
     * @param colonne2 Colonne de la deuxième carte
     * @return True si les cartes ont été enlevées, False sinon
     */
    public boolean enleverCartes(int rangée1, int colonne1, int rangée2, int colonne2) {
        boolean cartesEnlevées = false;

        try { // Si le try plante, c'est que un paramètre en dehors de la pyramide a été passé
            if (rangée1 <= 7 && rangée2 <= 7 &&
                    rangée1 >= 0 && rangée2 >= 0) {
                if (rangée1 == 7) { // Vérifier dabord si le waste a été cliqué
                    if (lstWaste.get(lstWaste.size() - 1).numero + pyramideArray.get(rangée2)[colonne2].numero == 13) {
                        if (déterminerDisponibilité(rangée2, colonne2) == true) {
                            enleverDessusWaste();
                            pyramideArray.get(rangée2)[colonne2] = null;
                            cartesEnlevées = true;
                        }
                    }
                } else if (rangée2 == 7) {
                    if (lstWaste.get(lstWaste.size() - 1).numero + pyramideArray.get(rangée1)[colonne1].numero == 13) {
                        if (déterminerDisponibilité(rangée1, colonne1) == true) {
                            enleverDessusWaste();
                            pyramideArray.get(rangée1)[colonne1] = null;
                            cartesEnlevées = true;
                        }
                    }
                } else { // Si aucune carte ne venait du waste
                    if (pyramideArray.get(rangée1)[colonne1] != null &&
                            pyramideArray.get(rangée2)[colonne2] != null) {

                        if ((pyramideArray.get(rangée1)[colonne1].numero + pyramideArray.get(rangée2)[colonne2].numero) == 13) {

                            if (déterminerDisponibilité(rangée1, colonne1) == true &&
                                    déterminerDisponibilité(rangée2, colonne2) == true) {

                                pyramideArray.get(rangée1)[colonne1] = null;
                                pyramideArray.get(rangée2)[colonne2] = null;
                                cartesEnlevées = true;
                            }
                        }
                    }
                }
            }
        } catch (Exception e) {} // Si le try plante, c'est que un paramètre en dehors de la pyramide a été passé

        return cartesEnlevées;
    }

    /**
     * Vérifie si la partie est terminée. Elle est terminée soit par une victoire ou une défaite
     * Elle n'est pas terminée s'il reste des mouvements possibles
     * @return True si partie terminée, faux sinon
     */
    public boolean vérifierFinPartie() {
        List<Carte> lstCartesDisponiblesRestantes = new ArrayList<Carte>();

        if (pyramideArray.get(0)[0] == null) {
            partieTerminée = true;
            partieGagnée = true;
        }
        else if (lstStock.size() <= 0) { // Ne vérifier la fin de partie que si le stock est vide
            lstCartesDisponiblesRestantes.add(lstWaste.get(lstWaste.size() - 1));
            for (int i = pyramideArray.size() - 1; i >= 0; i--) {
                for (int j = pyramideArray.get(i).length - 1; j >= 0; j--) {
                    if (pyramideArray.get(i)[j] != null) {
                        if (déterminerDisponibilité(i, j) == true) {
                            lstCartesDisponiblesRestantes.add(pyramideArray.get(i)[j]);
                        }
                    }
                }
            }

            partieTerminée = true;
            for (int i = 0; i < lstCartesDisponiblesRestantes.size(); i++) {
                if (lstCartesDisponiblesRestantes.get(i).numero == 13) {
                    partieTerminée = false;
                } else {
                    for (int j = 0; j < lstCartesDisponiblesRestantes.size(); j++) {
                        if (lstCartesDisponiblesRestantes.get(i).numero + lstCartesDisponiblesRestantes.get(j).numero == 13) {
                            partieTerminée = false;
                        }
                    }
                }
            }
        }

        return partieTerminée;
    }

    /**
     * @return Vrai si la partie est gagnée, faux sinon
     */
    public boolean vérifierPartieGagnée() {
        return partieGagnée;
    }

    /**
     * Piger 28 cartes du paquet et les envoyer dans la pyramide
     */
    private void envoyerPaquetAPyramide() {
        // Vérifier que le paquet est "neuf"
        if (paquet.size() == 52) {
            List<Carte> lstTemp = new ArrayList<Carte>();


            for (int i = 0; i < 28; i++) {
                lstTemp.add(paquet.pigerUneCarte());
            }

            remplirPyramide(lstTemp);
        }
    }

    /**
     * Remplir les 7 rangées de la pyramide avec les cartes du paquet
     * @param lstpyramide La liste des 28 cartes à mettre dans la pyramide
     */
    private void remplirPyramide(List<Carte> lstpyramide) {
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
    private void remplirStock() {
        for (int i = 0; i < 24; i++) {
            lstStock.add(paquet.pigerUneCarte());
        }
    }

    /**
     * @param rangée Rangée dans laquelle la carte à enlever est située
     * @param colonne Colonne dans laquelle la carte à enlever est située
     * @return Vrai si la carte est disponible, Faux si la carte est bloquée
     */
    private boolean déterminerDisponibilité(int rangée, int colonne) {
        boolean disponible = true;

        if (rangée > 6 || rangée < 0) {
            disponible = false;
        }
        else if (rangée < 6) {
            // vérifier si les deux cartes en dessous sont encore là
            if (pyramideArray.get(rangée + 1)[colonne] != null)
                disponible = false;

            if (pyramideArray.get(rangée + 1)[colonne + 1] != null)
                disponible = false;
        }


        return disponible;
    }

    /**
     * Enlève la carte sur le dessus du waste
     */
    private void enleverDessusWaste() {
        lstWaste.remove(lstWaste.size() - 1);
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