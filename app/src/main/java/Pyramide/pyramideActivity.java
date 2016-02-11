package Pyramide;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.utilisateur.jeudepatience.Pyramide;
import com.example.utilisateur.jeudepatience.R;

import java.lang.reflect.Field;

import Utilitaire.Carte;

public class pyramideActivity extends Activity {

    Pyramide jeuDePyramide;
    Field[] campos;
    String premiereCarte;
    String deuxièmeCarte;
    int rangéeDestination;
    int colonneDestination;
    int rangéeOrigine;
    int colonneOrigine;
    ImageView imageSélectionnée;
    boolean estCrée = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pyramide);
        premiereCarte="";
        deuxièmeCarte="";
        rangéeDestination = -1;
        colonneDestination = -1;
        rangéeOrigine = -1;
        colonneOrigine = -1;
        jeuDePyramide = new Pyramide();
        campos = R.id.class.getFields();
        afficherPyramide();
        estCrée = true;
    }

    /**
     * S'il reste des cartes dans le stock, envoyer celle du dessus au dessus du waste
     * @param v Vue qui déclenche l'event
     */
    public void OnClickStock(View v) {
        jeuDePyramide.transfererStockAWaste();


        TextView lblStock = (TextView)findViewById(R.id.cartesStock);
        int nbCartesStock = jeuDePyramide.cartesRestantes();
        lblStock.setText(Integer.toString(nbCartesStock));

        ImageView image = (ImageView)findViewById(R.id.R7C0);
        try {
            image.setImageResource(jeuDePyramide.trouverIdCarte(jeuDePyramide.sélectionnerDernierAuWaste().nom));
        }
        catch(Exception e){}

        if (nbCartesStock == 0) {
            image = (ImageView) findViewById(R.id.stock);
            image.setImageDrawable(null);
        }
        jeuDePyramide.checkConditionDeFin();
    }

    /**
     * Sélectionne une carte, si une est déj`asélectionnée, en sélectionne une deuxième.
     * Envoie le(s) carte(s) sélectionnée(s) à pyramide pour vérifier si on peut les enlever
     * @param v Objet qui a provoqué l'event
     */
    public void OnClickPyramide(View v) throws IllegalAccessException {
        char tempChar;

        for(Field f: campos)
        if(f.getInt(null)==v.getId())
            {
                if(premiereCarte=="") {
                    imageSélectionnée = (ImageView)findViewById(v.getId());
                    imageSélectionnée.setBackgroundColor(Color.BLUE);
                    premiereCarte = f.getName();
                    tempChar=premiereCarte.charAt(1);
                    rangéeOrigine=Character.getNumericValue(tempChar);
                    tempChar=premiereCarte.charAt(3);
                    colonneOrigine=Character.getNumericValue(tempChar);

                    // Envoyer la carte seule. Si elle est un roi, ça fonctionnera
                    if (jeuDePyramide.jouerDeuxCartes(rangéeOrigine, colonneOrigine) == true) {
                        imageSélectionnée.setImageDrawable(null);
                        imageSélectionnée.setBackgroundColor(Color.TRANSPARENT);
                        premiereCarte = "";
                        rangéeOrigine = -1;
                        colonneOrigine = -1;
                    }
                }
                else if(deuxièmeCarte=="") {
                    deuxièmeCarte = f.getName();
                    tempChar = deuxièmeCarte.charAt(1);
                    rangéeDestination = Character.getNumericValue(tempChar);
                    tempChar = deuxièmeCarte.charAt(3);
                    colonneDestination = Character.getNumericValue(tempChar);

                    // Envoyer la carte seule. Si elle est un roi, ça fonctionnera
                    jeuDePyramide.jouerDeuxCartes(rangéeOrigine, colonneOrigine, rangéeDestination, colonneDestination);

                    imageSélectionnée.setBackgroundColor(Color.TRANSPARENT);
                    premiereCarte = "";
                    deuxièmeCarte = "";
                }
                break;
            }
        jeuDePyramide.checkConditionDeFin();
        afficherPyramide();
    }

    /**
     * Mets à jour les images de la pyramide et du dessus du waste.
     * Si la carte est null, ne pas afficher d'image
     */
    public void afficherPyramide() {
        if (estCrée){
            Toast.makeText(getApplicationContext(), "Partie terminée.", Toast.LENGTH_LONG).show();
        }
        for(int i =0;i<jeuDePyramide.lstJeu.size();i++)
            for(int j=0;j<jeuDePyramide.lstJeu.get(i).length;j++)
                try{
                    Carte carteAAfficher =jeuDePyramide.lstJeu.get(i)[j];
                    String tag = "R" +Integer.toString(i,0)+"C"+Integer.toString(j,0);
                    for(Field f: campos)
                    {
                        if(tag.contains(f.getName())) {
                            ImageView img=(ImageView)this.findViewById(this.getBaseContext().getResources().getIdentifier("R" + i + "C" + j
                                    , "id", this.getBaseContext().getPackageName()));
                            if (carteAAfficher == null) {
                                img.setImageDrawable(null);
                                img.setClickable(false);
                            }
                            else {
                                img.setImageResource(jeuDePyramide.trouverIdCarte(carteAAfficher.nom));
                                img.setClickable(true);
                            }
                        }
                    }
                }catch(Exception e){}
        ImageView image = (ImageView)findViewById(R.id.R7C0);
        try {
            image.setImageResource(jeuDePyramide.trouverIdCarte(jeuDePyramide.sélectionnerDernierAuWaste().nom));
            image.setClickable(true);
        }
        catch(Exception e){}
    }
}
