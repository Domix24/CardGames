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

import utilitaire.Carte;

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
    Solitaire.CarteColonne premierecc;
    int[] carteFondations = {R.id.FondationCoeur, R.id.FondationPique, R.id.FondationCarre, R.id.FondationTrefle};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solitaire);
        campos = R.id.class.getFields();
        premiereCarte = "";
        jeuSolitaire = new Solitaire();
        jeuSolitaire.Initialiser(16421);
        rafraîchirJeu();

    }

    public void onClick(View v){
        String tempChar;
        for (Field f : campos)
            try {
                if (f.getInt(null) == v.getId()) {
                    if (premiereCarte == "") {
                        imageSélectionnée = (ImageView) findViewById(v.getId());
                        premierecc = (Solitaire.CarteColonne)imageSélectionnée.getTag();
                        if (premierecc.estDévoilée) {
                            imageSélectionnée.setBackgroundColor(Color.rgb(0, 128, 46));
                            premiereCarte = f.getName();
                            String[] s = premiereCarte.split("C");
                            tempChar = s[1];
                            rangéeOrigine = Integer.parseInt(tempChar);
                            tempChar = s[2];
                            colonneOrigine = Integer.parseInt(tempChar);
                        }
                    }
                    else
                    {
                        deuxièmeCarte = f.getName();
                        String[] s = deuxièmeCarte.split("C");
                        tempChar = s[2];
                        colonneDestination = Integer.parseInt(tempChar);
                        if (deuxièmeCarte == premiereCarte) {
                            imageSélectionnée.setBackgroundColor(Color.argb(0, 0, 0, 0));
                            premiereCarte = "";
                            deuxièmeCarte = "";
                        }
                        else if (jeuSolitaire.DéplacerPaquetVersAutreColonne(premierecc.carte, colonneOrigine, rangéeOrigine, colonneDestination));
                        {
                            rafraîchirJeu();
                        }
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
    }

    public  void onFondationClick(View v)
    {
        if (premiereCarte != "")
        {
            if (jeuSolitaire.PlacerCarteDansFoundations(premierecc.carte, colonneOrigine))
            {
                premiereCarte = "";
                rafraîchirJeu();
            }
        }
    }


    public void rafraîchirJeu()
    {

        List[] ll = jeuSolitaire.avoirTableau();
        for (int i = 0; i < 7; i++)
        {
            int plusque13 = 0;
            if (ll[i].size() > 13) {
                plusque13 = ll[i].size() - 13;
            }
            for (int j = 0; j < 13; j++) {
                if (j < ll[i].size()) {
                    Solitaire.CarteColonne cc = (Solitaire.CarteColonne) ll[i].get(j + plusque13);
                    ImageView img = (ImageView) this.findViewById(this.getBaseContext().getResources().getIdentifier("C" + j + "C" + i
                            , "id", this.getBaseContext().getPackageName()));
                    if (cc.estDévoilée) {
                        img.setImageResource(jeuSolitaire.trouverIdCarte(cc.carte.nom));
                        img.setTag(cc);
                        img.setVisibility(View.VISIBLE);
                        img.setBackgroundColor(Color.argb(0, 0, 0, 0));
                    }

                    else {
                        img.setImageResource(R.drawable.back);
                        img.setTag(cc);
                        img.setVisibility(View.VISIBLE);
                        img.setBackgroundColor(Color.argb(0, 0, 0, 0));
                    }
                } else {
                    ImageView img = (ImageView) this.findViewById(this.getBaseContext().getResources().getIdentifier("C" + j + "C" + i
                            , "id", this.getBaseContext().getPackageName()));
                    if (img != null) {
                        img.setVisibility(View.INVISIBLE);
                        img .setTag(null);
                    }
                }
            }
        }


        Carte[] fondations = jeuSolitaire.avoirFondations();
        for (int i =0 ;i < fondations.length; i++){
            ImageView img = (ImageView) this.findViewById(carteFondations[i]);
            if (fondations[i] != null)
                img.setImageResource(jeuSolitaire.trouverIdCarte(fondations[i].nom));
            else
                img.setImageResource(R.drawable.diamonds_10);
        }

    }


    public  void Recommencer(View v)
    {
        campos = R.id.class.getFields();
        premiereCarte = "";
        jeuSolitaire = new Solitaire();
        jeuSolitaire.Initialiser(16421);
        rafraîchirJeu();
    }
}

