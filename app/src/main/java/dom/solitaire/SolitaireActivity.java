package dom.solitaire;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.utilisateur.jeudepatience.R;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class SolitaireActivity extends Activity {

    Solitaire jeuSolitaire;
    String premiereCarte;
    String deuxièmeCarte;
    int rangéeDestination;
    int colonneDestination;
    int rangéeOrigine;
    int colonneOrigine;
    ImageView imageSélectionnée;
    Field[] campos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solitaire);
        campos = R.id.class.getFields();
        premiereCarte = "";
        jeuSolitaire = new Solitaire();
        jeuSolitaire.Initialiser(16421);

    }

    public void onClick(View v){
        String tempChar;
        rafraîchirJeu();
        for (Field f : campos)
            try {
                if (f.getInt(null) == v.getId()) {
                    if (premiereCarte == "") {
                        imageSélectionnée = (ImageView) findViewById(v.getId());
                        imageSélectionnée.setBackgroundColor(Color.rgb(0, 128, 46));
                        premiereCarte = f.getName();
                        String[] s = premiereCarte.split("C");
                        tempChar = s[1];
                        rangéeOrigine = Integer.parseInt(tempChar);
                        tempChar = s[2];
                        colonneOrigine = Integer.parseInt(tempChar);
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
    }


    public void rafraîchirJeu()
    {

        List[] ll = jeuSolitaire.avoirTableau();
        for (int i = 0; i < 7; i++)
        {
            for (int j = 0; j < 13; j++) {
                if (j < ll[i].size()) {
                    Solitaire.CarteColonne cc = (Solitaire.CarteColonne) ll[i].get(j);
                    ImageView img = (ImageView) this.findViewById(this.getBaseContext().getResources().getIdentifier("C" + j + "C" + i
                            , "id", this.getBaseContext().getPackageName()));
                    if (img != null)
                        img.setImageResource(jeuSolitaire.trouverIdCarte(cc.carte.nom));
                } else {
                    ImageView img = (ImageView) this.findViewById(this.getBaseContext().getResources().getIdentifier("C" + j + "C" + i
                            , "id", this.getBaseContext().getPackageName()));
                    if (img != null)
                        img.setVisibility(View.INVISIBLE);
                }
            }
        }
    }

}

