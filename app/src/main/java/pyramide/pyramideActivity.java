package pyramide;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
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
    EditText nbpMise;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pyramide);
        joueur = JoueurSingleton.getInstance();
        pyramideMises = new PyramideMises(0);
        commencerNouvellePartie();

        nbpMise = (EditText)findViewById(R.id.nbpMisePyramide);
        // Code trouvé : http://stackoverflow.com/questions/2434532/android-set-hidden-the-keybord-on-press-enter-in-a-edittext
        // En date du 24 février 2016
        nbpMise.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == event.KEYCODE_ENTER) {
                    // Créer une nouvelle mise
                    float mise;
                    try {
                        mise = Float.parseFloat(nbpMise.getText().toString());
                    } catch (Exception e) {
                        mise = -1;
                    }
                    if (mise < 0)
                        mise = 0;
                    pyramideMises = new PyramideMises(mise);

                    afficherPyramide();

                    // Faire visuelement disparaitre le numberPicker
                    nbpMise.setVisibility(View.INVISIBLE);
                    nbpMise.clearFocus();
                    InputMethodManager in = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                    in.hideSoftInputFromWindow(nbpMise.getApplicationWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);

                    return true;
                }
                return false;
            }
        });
    }

    /**
     * S'il reste des cartes dans le stock, envoyer celle du dessus au dessus du waste
     * @param v Vue qui déclenche l'event
     */
    public void OnClickStock(View v) {
        if (nbpMise.getVisibility() == View.INVISIBLE) { // Ne jouer que si la mise a été faite
            jeuDePyramide.envoyerCarteAuWaste();

            if (jeuDePyramide.vérifierFinPartie() == true) {
                partieTerminée = true;
                partieGagnée = jeuDePyramide.vérifierPartieGagnée();
                if (partieGagnée == true) {
                    // La mise gagnée sera différente depandant du nombre de fois que le stock a été traversé
                    switch (jeuDePyramide.getNombreStock()) {
                        case 0 :
                            pyramideMises.gagner(1);
                            break;
                        case 1 :
                            pyramideMises.gagner(2);
                            break;
                        case 2 :
                            pyramideMises.gagner(5);
                            break;
                        default :
                            pyramideMises.gagner(0);
                            break;
                    }
                }
            }

            afficherPyramide();
        }
    }

    /**
     * Sélectionne une carte, si une est déjà sélectionnée, en sélectionne une deuxième.
     * Envoie le(s) carte(s) sélectionnée(s) à pyramide pour vérifier si on peut les enlever
     * Cet évènement click inclut aussi le click du waste qui est traité comme rangée 7 colonne 0
     * @param v Objet qui a provoqué l'event
     */
    public void OnClickPyramide(View v) throws IllegalAccessException {
        if (nbpMise.getVisibility() == View.INVISIBLE) {
            char tempChar;

            for (Field f : campos)
                if (f.getInt(null) == v.getId()) {
                    if (premiereCarte == "") {
                        imageSélectionnée = (ImageView) findViewById(v.getId());
                        imageSélectionnée.setBackgroundColor(Color.BLUE);
                        premiereCarte = f.getName();
                        tempChar = premiereCarte.charAt(1);
                        rangéeCarte1 = Character.getNumericValue(tempChar);
                        tempChar = premiereCarte.charAt(3);
                        colonneCarte1 = Character.getNumericValue(tempChar);

                        // Envoyer la carte seule. Si elle est un roi, ça fonctionnera
                        if (jeuDePyramide.enleverCartes(rangéeCarte1, colonneCarte1) == true) {
                            imageSélectionnée.setImageDrawable(null);
                            imageSélectionnée.setBackgroundColor(Color.TRANSPARENT);
                            premiereCarte = "";
                            rangéeCarte1 = -1;
                            colonneCarte1 = -1;

                            if (pyramideMises.chanceGagnerPeu() == true)
                                Toast.makeText(getApplicationContext(),
                                        (getString(R.string.pyramide_montantGagné) + Integer.toString(pyramideMises.getDernierMontantChanceGagné()) + "$"),
                                        Toast.LENGTH_LONG).show();
                        }
                    } else if (deuxièmeCarte == "") {
                        deuxièmeCarte = f.getName();
                        tempChar = deuxièmeCarte.charAt(1);
                        rangéeCarte2 = Character.getNumericValue(tempChar);
                        tempChar = deuxièmeCarte.charAt(3);
                        colonneCarte2 = Character.getNumericValue(tempChar);

                        // Envoyer la carte seule. Si elle est un roi, ça fonctionnera
                        if (jeuDePyramide.enleverCartes(rangéeCarte1, colonneCarte1, rangéeCarte2, colonneCarte2) == true) {
                            if (pyramideMises.chanceGagnerPeu() == true)
                                Toast.makeText(getApplicationContext(),
                                        (getString(R.string.pyramide_montantGagné) + Integer.toString(pyramideMises.getDernierMontantChanceGagné()) + "$"),
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
                // La mise gagnée sera différente depandant du nombre de fois que le stock a été traversé
                switch (jeuDePyramide.getNombreStock()) {
                    case 0 :
                        pyramideMises.gagner(1);
                        break;
                    case 1 :
                        pyramideMises.gagner(2);
                        break;
                    case 2 :
                        pyramideMises.gagner(5);
                        break;
                    default :
                        pyramideMises.gagner(0);
                        break;
                }
            }
            afficherPyramide();
        }
    }

    /**
     * Mets à jour les images de la pyramide et du dessus du waste.
     * Si la carte est null, ne pas afficher d'image
     */
    public void afficherPyramide() {
        if (partieTerminée) {
            if (partieGagnée == true) {
                Toast.makeText(getApplicationContext(), getString(R.string.pyramide_partieGagnée), Toast.LENGTH_LONG).show();
                Toast.makeText(getApplicationContext(), getString(R.string.pyramide_montantGagné) + pyramideMises.getMontantGagné() + "$", Toast.LENGTH_LONG).show();
            }
            else {
                Toast.makeText(getApplicationContext(), getString(R.string.pyramide_partiePerdue), Toast.LENGTH_LONG).show();
                Toast.makeText(getApplicationContext(), getString(R.string.pyramide_montantPerdu) + pyramideMises.getMontantMise() + "$", Toast.LENGTH_LONG).show();
                pyramideMises.perdre();
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
        lblMontant.setText(getString(R.string.pyramide_montantMise) + Float.toString(pyramideMises.getMontantMise()) + "$");
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
        pyramideMises = new PyramideMises(0);
        try { // Ne fonctionnera pas si c'est la première fois que la partie commence car il n'a pas été initialisé
            nbpMise.setVisibility(View.VISIBLE);
        } catch (Exception e) {}
        afficherPyramide();
    }
}
