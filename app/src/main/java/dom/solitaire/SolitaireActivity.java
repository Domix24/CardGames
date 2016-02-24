package dom.solitaire;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

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
    int[] carteFondationsvide = {R.drawable.hearts_avide,R.drawable.spades_avide, R.drawable.diamonds_avide,R.drawable.clubs_avide};

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

    /*
    Lorsque l'utilisateur appuie sur une des cartes du jeu, celle-ci est sélectionnée, déplacée ou ajoutée au jeu
     */
    public void onClick(View v){
        String tempChar;
        for (Field f : campos)
            try {
                if (f.getInt(null) == v.getId()) {
                    if (premiereCarte == "") {
                        imageSélectionnée = (ImageView) findViewById(v.getId());
                        if (imageSélectionnée.getTag() != null) {
                            premierecc = (Solitaire.CarteColonne) imageSélectionnée.getTag();
                            if (premierecc.estDévoilée) {
                                imageSélectionnée.setBackgroundColor(Color.rgb(48, 0, 255));
                                premiereCarte = f.getName();
                                String[] s = premiereCarte.split("C");
                                tempChar = s[1];
                                rangéeOrigine = Integer.parseInt(tempChar);
                                tempChar = s[2];
                                colonneOrigine = Integer.parseInt(tempChar);
                            }
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
                        else if (premiereCarte == "carte" && jeuSolitaire.ajouterNouvelleCarteDansJeu(colonneDestination)){
                            rafraîchirJeu();
                            premiereCarte = "";
                            ImageView imageCarteJouable = (ImageView)findViewById(R.id.Card);
                            Carte carte = jeuSolitaire.PigerNouvelleCarte();
                            if (carte != null) {
                                imageCarteJouable.setImageResource(jeuSolitaire.trouverIdCarte(carte.nom));
                                imageCarteJouable.setTag(carte);
                                imageCarteJouable.setBackgroundColor(Color.argb(0, 0, 0, 0));
                            }
                        }
                        else if (jeuSolitaire.DéplacerPaquetVersAutreColonne(premierecc.carte, colonneOrigine, rangéeOrigine, colonneDestination))
                        {
                            rafraîchirJeu();
                            premiereCarte="";
                        }
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
    }

    /*
    Lorsque l'utilisateur appuie sur une fondation (4 paquets de cartes)
     */
    public  void onFondationClick(View v)
    {
        if (premiereCarte != "")
        {
            try {
                if (premiereCarte == "carte" && jeuSolitaire.placerNouvelleCarteDansFondations()) {
                    rafraîchirJeu();
                    premiereCarte = "";
                    ImageView imageCarteJouable = (ImageView) findViewById(R.id.Card);
                    Carte carte = jeuSolitaire.PigerNouvelleCarte();
                    imageCarteJouable.setImageResource(jeuSolitaire.trouverIdCarte(carte.nom));
                    imageCarteJouable.setTag(carte);
                    imageCarteJouable.setBackgroundColor(Color.argb(0, 0, 0, 0));
                } else if (jeuSolitaire.PlacerCarteDansFoundations(premierecc.carte, colonneOrigine)) {
                    premiereCarte = "";
                    rafraîchirJeu();
                }
            }
            catch (Exception e)
            {

            }
        }
    }


    /*
    Rafraichit le jeu apres chaque coup pour que le jeu ait l'air des données
     */
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
                    }

                    else {
                        img.setImageResource(R.drawable.back);
                    }
                    img.setTag(cc);
                    img.setVisibility(View.VISIBLE);
                    img.setBackgroundColor(Color.argb(0, 0, 0, 0));
                } else {
                    ImageView img = (ImageView) this.findViewById(this.getBaseContext().getResources().getIdentifier("C" + j + "C" + i
                            , "id", this.getBaseContext().getPackageName()));
                    if (img != null) {
                        if (j != 0) {
                            img.setVisibility(View.INVISIBLE);
                        }
                        img.setTag(null);
                        img.setImageResource(R.drawable.emptycard);
                        img.setBackgroundColor(Color.argb(0, 0, 0, 0));
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
                img.setImageResource(carteFondationsvide[i]);
        }

        ImageView imageCarteJouable = (ImageView)findViewById(R.id.Card);
        Carte carte = jeuSolitaire.avoirCarteSortie();
        if (carte == null) {
            imageCarteJouable.setTag(null);
            imageCarteJouable.setImageResource(R.drawable.emptycard);
            imageCarteJouable.setBackgroundColor(Color.argb(0, 0, 0, 0));
        }
        if (jeuSolitaire.avoirGrandeur()){
            imageCarteJouable = (ImageView)findViewById(R.id.DeckCard);
            imageCarteJouable.setImageResource(R.drawable.emptycard);
        }

        if (jeuSolitaire.avoirGagner()){
            Toast tt = Toast.makeText(getApplicationContext(), getString(R.string.pyramide_partieGagnée), Toast.LENGTH_LONG);
            tt.show();
        }

    }

    /*
    Permet de recommencer le jeu
     */
    public  void Recommencer(View v)
    {
        campos = R.id.class.getFields();
        premiereCarte = "";
        jeuSolitaire = new Solitaire();
        jeuSolitaire.Initialiser(16421);
        rafraîchirJeu();
        ImageView imageCarteJouable = (ImageView)findViewById(R.id.Card);
        imageCarteJouable.setImageResource(R.drawable.emptycard);
        imageCarteJouable = (ImageView)findViewById(R.id.DeckCard);
        imageCarteJouable.setImageResource(R.drawable.back);
    }

    /*
    Lorsque l'utilisateur appuie sur le paquet, une nouvelle carte sort
     */
    public void onPaquetClic(View v) {
       // System.err.println("Clic sur le paquet");

        ImageView imageCarteJouable = (ImageView)findViewById(R.id.Card);
        if (imageCarteJouable.getTag() != null) {
            Carte carteChangee = (Carte) imageCarteJouable.getTag();
            jeuSolitaire.AjouterCarteAuPaquet(carteChangee);
        }
        Carte carte = jeuSolitaire.PigerNouvelleCarte();
        if (carte != null) {
            imageCarteJouable.setImageResource(jeuSolitaire.trouverIdCarte(carte.nom));
            imageCarteJouable.setTag(carte);
        }
        else
        {
            imageCarteJouable.setTag(null);
            imageCarteJouable.setImageResource(R.drawable.emptycard);
            imageCarteJouable.setBackgroundColor(Color.argb(0, 0, 0, 0));
            imageCarteJouable = (ImageView)findViewById(R.id.DeckCard);
            imageCarteJouable.setImageResource(R.drawable.emptycard);
        }
        rafraîchirJeu();

    }

    /*
    Lorsqu'un utilisteur appiue sur la carte de sortie du paquet, celle-ci est sélectionnée
     */
    public void onSortiePaquetClick(View v){
        if (premiereCarte == ""){
            imageSélectionnée = (ImageView) findViewById(v.getId());
            if (imageSélectionnée.getTag() != null) {
                    imageSélectionnée.setBackgroundColor(Color.rgb(48, 0, 255));
                    premiereCarte = "carte";
            }
        }
        else if (premiereCarte == "carte"){
            imageSélectionnée = (ImageView) findViewById(v.getId());
            imageSélectionnée.setBackgroundColor(Color.argb(0, 0, 0, 0));
            premiereCarte = "";
        }
    }
}

