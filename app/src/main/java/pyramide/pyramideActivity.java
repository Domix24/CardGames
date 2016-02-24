package pyramide;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import utilitaire.Carte;
import utilitaire.JoueurSingleton;

import com.example.utilisateur.jeudepatience.R;

import java.lang.reflect.Field;

public class pyramideActivity extends Activity {

    PyramideLogique jeuDePyramide;
    Field[] campos;
    String premiereCarte;
    String deuxièmeCarte;
    int rangéeCarte2;
    int colonneCarte2;
    int rangéeCarte1;
    int colonneCarte1;
    ImageView imageSélectionnée;
    boolean partieTerminée;
    boolean partieGagnée;
    JoueurSingleton joueur;
    PyramideMises pyramideMises;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pyramide);
        joueur = JoueurSingleton.getInstance();
        pyramideMises = new PyramideMises(5);
        commencerNouvellePartie();
    }

    /**
     * S'il reste des cartes dans le stock, envoyer celle du dessus au dessus du waste
     * @param v Vue qui déclenche l'event
     */
    public void OnClickStock(View v) {
        jeuDePyramide.envoyerCarteAuWaste();

        if (jeuDePyramide.vérifierFinPartie() == true) {
            partieTerminée = true;
            partieGagnée = jeuDePyramide.vérifierPartieGagnée();
        }

        afficherPyramide();
    }

    /**
     * Sélectionne une carte, si une est déjà sélectionnée, en sélectionne une deuxième.
     * Envoie le(s) carte(s) sélectionnée(s) à pyramide pour vérifier si on peut les enlever
     * Cet évènement click inclut aussi le click du waste qui est traité comme rangée 7 colonne 0
     * @param v Objet qui a provoqué l'event
     */
    public void OnClickPyramide(View v) throws IllegalAccessException {
        char tempChar;

        for(Field f: campos)
        if(f.getInt(null)==v.getId()) {
                if(premiereCarte=="") {
                    imageSélectionnée = (ImageView)findViewById(v.getId());
                    imageSélectionnée.setBackgroundColor(Color.BLUE);
                    premiereCarte = f.getName();
                    tempChar=premiereCarte.charAt(1);
                    rangéeCarte1 =Character.getNumericValue(tempChar);
                    tempChar=premiereCarte.charAt(3);
                    colonneCarte1 =Character.getNumericValue(tempChar);

                    // Envoyer la carte seule. Si elle est un roi, ça fonctionnera
                    if (jeuDePyramide.enleverCartes(rangéeCarte1, colonneCarte1) == true) {
                        imageSélectionnée.setImageDrawable(null);
                        imageSélectionnée.setBackgroundColor(Color.TRANSPARENT);
                        premiereCarte = "";
                        rangéeCarte1 = -1;
                        colonneCarte1 = -1;

                        if (pyramideMises.chanceGagnerPeu() == true)
                            Toast.makeText(getApplicationContext(),
                                    (getString(R.string.pyramide_montantChance) + Integer.toString(pyramideMises.getDernierMontantChanceGagné()) + "$"),
                                    Toast.LENGTH_LONG).show();
                    }
                }
                else if(deuxièmeCarte=="") {
                    deuxièmeCarte = f.getName();
                    tempChar = deuxièmeCarte.charAt(1);
                    rangéeCarte2 = Character.getNumericValue(tempChar);
                    tempChar = deuxièmeCarte.charAt(3);
                    colonneCarte2 = Character.getNumericValue(tempChar);

                    // Envoyer la carte seule. Si elle est un roi, ça fonctionnera
                    if (jeuDePyramide.enleverCartes(rangéeCarte1, colonneCarte1, rangéeCarte2, colonneCarte2) == true) {
                        if (pyramideMises.chanceGagnerPeu() == true)
                            Toast.makeText(getApplicationContext(),
                                    (getString(R.string.pyramide_montantChance) + Integer.toString(pyramideMises.getDernierMontantChanceGagné()) + "$"),
                                    Toast.LENGTH_LONG).show();
                    }

                    imageSélectionnée.setBackgroundColor(Color.TRANSPARENT);
                    premiereCarte = "";
                    deuxièmeCarte = "";
                }
            }
        if (jeuDePyramide.vérifierFinPartie() == true) {
            partieTerminée = true;
            partieGagnée = jeuDePyramide.vérifierPartieGagnée();
        }
        afficherPyramide();
    }

    /**
     * Mets à jour les images de la pyramide et du dessus du waste.
     * Si la carte est null, ne pas afficher d'image
     */
    public void afficherPyramide() {
        if (partieTerminée) {
            if (partieGagnée == true) {
                Toast.makeText(getApplicationContext(), getString(R.string.pyramide_partieGagner), Toast.LENGTH_LONG).show();
            }
            else {
                Toast.makeText(getApplicationContext(), getString(R.string.pyramide_partiePerdue), Toast.LENGTH_LONG).show();
            }
        }

        // Afficher la pyramide
        for(int i =0;i< jeuDePyramide.getCartesPyramide().size();i++) {
            for (int j = 0; j < jeuDePyramide.getCartesPyramide().get(i).length; j++) {
                try {
                    Carte carteAAfficher = jeuDePyramide.getCartesPyramide().get(i)[j];
                    String tag = "R" + Integer.toString(i, 0) + "C" + Integer.toString(j, 0);
                    for (Field f : campos) {
                        if (tag.contains(f.getName())) {
                            ImageView img = (ImageView) this.findViewById(this.getBaseContext().getResources().getIdentifier("R" + i + "C" + j
                                    , "id", this.getBaseContext().getPackageName()));
                            if (carteAAfficher == null) {
                                img.setImageDrawable(null);
                                img.setClickable(false);
                            } else {
                                img.setImageResource(jeuDePyramide.trouverIdCarte(carteAAfficher.nom));
                                img.setClickable(true);
                            }
                        }
                    }
                }
                catch (Exception e) {
                }
            }
        }

        // Afficher le waste
        ImageView image = (ImageView)findViewById(R.id.R7C0);
        try {
            image.setImageResource(jeuDePyramide.trouverIdCarte(jeuDePyramide.getCarteDessusWaste().nom));
            image.setClickable(true);
        }
        catch(Exception e){
            image.setImageDrawable(null);
            image.setClickable(false);
        }

        // Afficher le stock
        image = (ImageView)findViewById(R.id.stock);
        if (jeuDePyramide.getNombreStock() == 0) {;
            image.setImageDrawable(null);
        }
        else {
            image.setImageResource(R.drawable.back);
            image.setClickable(true);
        }

        // Afficher le lbl du nombre de cartes dans le stock
        TextView lblStock = (TextView)findViewById(R.id.cartesStock);
        int nbCartesStock = jeuDePyramide.getNombreStock();
        lblStock.setText(Integer.toString(nbCartesStock));

        // Afficher les lbl des montants
        TextView lblMontant = (TextView)findViewById(R.id.montantTotal);
        String sdsa = Float.toString(joueur.getMonnaie());
        lblMontant.setText(getString(R.string.pyramide_montantTotal) + Float.toString(joueur.getMonnaie()) + "$");
        lblMontant = (TextView)findViewById(R.id.montantMise);
        lblMontant.setText(getString(R.string.pyramide_montantMise));
    }

    /**
     * Au click, commence une nouvelle partie
     * @param v
     */
    public void Restart(View v) {
        // Enlever la sélection visuelle de la carte
        try {
            ImageView img = (ImageView) findViewById(this.getBaseContext().getResources().getIdentifier(premiereCarte, "id", this.getBaseContext().getPackageName()));
            img.setBackgroundColor(Color.TRANSPARENT);
        } catch (Exception e) {}

        jeuDePyramide.commencerNouvellePartie();
        commencerNouvellePartie();
    }

    /**
     * Initialiser les variables pour commencer une nouvelle partie
     */
    private void commencerNouvellePartie() {
        premiereCarte="";
        deuxièmeCarte="";
        rangéeCarte2 = -1;
        colonneCarte2 = -1;
        rangéeCarte1 = -1;
        colonneCarte1 = -1;
        partieTerminée = false;
        partieGagnée = false;
        jeuDePyramide = new PyramideLogique();
        campos = R.id.class.getFields();
        afficherPyramide();
    }
}
