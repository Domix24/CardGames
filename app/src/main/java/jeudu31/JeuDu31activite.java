
package jeudu31;

import android.graphics.Color;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.utilisateur.jeudepatience.R;

import org.w3c.dom.Text;

import java.lang.reflect.Field;

import utilitaire.Carte;

public class JeuDu31activite extends Activity {
    Field[] campos;//Variable contenant la colleciton des contrôles.
    String premiereCarte;
    String deuxiemeCarte;
    ImageView imageSélectionnée;
    ImageView imagePremière;
    int couleur1;
    int couleur2;
    int position=0;
    JeuDu31Logique jeu;
    TextView lblIndication;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jeudu31activite);
        jeu = new JeuDu31Logique();
        campos = R.id.class.getFields();
        premiereCarte="";
        deuxiemeCarte="";
        couleur2=Color.TRANSPARENT;
        couleur1=Color.TRANSPARENT;
    }

    /**
     * Initilise
     */
    @Override
    protected  void onStart()
    {
        super.onStart();
        lblIndication = (TextView) this.findViewById(this.getBaseContext().getResources().getIdentifier("lblIndications"
                , "id", this.getBaseContext().getPackageName()));
        miseAJourImageCarteDejaPiger();
        ordonnerImages();
        NumberPicker numberPicker= (NumberPicker) this.findViewById(this.getBaseContext().getResources().getIdentifier("nbPicker"
                , "id", this.getBaseContext().getPackageName()));
        numberPicker.setMaxValue(999999999);
        joueurHumanFin();
        TextView lblArgent = (TextView) this.findViewById(this.getBaseContext().getResources().getIdentifier("lblArgent"
                , "id", this.getBaseContext().getPackageName()));
        lblArgent.setText(String.valueOf(jeu.idJoueur.getMonnaie()));
        ImageView img = (ImageView) this.findViewById(this.getBaseContext().getResources().getIdentifier("imgRecommencer"
                , "id", this.getBaseContext().getPackageName()));
        img.setClickable(false);
        lblIndication.setText("Mettez une mise");
    }

    /**
     * Met à jour le paquet de carte visible pour aovri sa carte du dessus.
     */
    public void miseAJourImageCarteDejaPiger()
    {
        ImageView img = (ImageView) this.findViewById(this.getBaseContext().getResources().getIdentifier("CarteDejaPiger"
                , "id", this.getBaseContext().getPackageName()));
        if(jeu.paquetTémoin.size()==0) {
            img.setImageDrawable(null);
            img.setClickable(false);
        }
        else
        {
            img.setImageResource(jeu.trouverIdCarte(jeu.paquetTémoin.get(jeu.paquetTémoin.size()-1).nom));
            img.setClickable(true);
        }
    }

    /**
     * Remet le jeu à son état originel et désactive le bouton restart.
     * @param v
     */
    public void onClickRecommencer(View v)
    {
        jeu.recommencer(3);
        miseAJourImageCarteDejaPiger();
        mettreAJourImageGauche(null);
        ordonnerImages();
        Button btnmiser = (Button) this.findViewById(this.getBaseContext().getResources().getIdentifier("btnMiser"
                , "id", this.getBaseContext().getPackageName()));
        btnmiser.setClickable(true);
        TextView lblArgent = (TextView) this.findViewById(this.getBaseContext().getResources().getIdentifier("lblArgent"
                , "id", this.getBaseContext().getPackageName()));
        lblArgent.setText(String.valueOf(jeu.idJoueur.getMonnaie()));
        lblIndication.setText("Mettez une mise");
        ImageView img = (ImageView) this.findViewById(this.getBaseContext().getResources().getIdentifier("imgRecommencer"
                , "id", this.getBaseContext().getPackageName()));
        img.setClickable(false);
        for(int i=1;i<7;i++) {
            img = (ImageView) this.findViewById(this.getBaseContext().getResources().getIdentifier("CarteEnemi" + i
                    , "id", this.getBaseContext().getPackageName()));
            img.setImageResource(R.drawable.back);
        }
    }
    /**
     * Ordonne les images selon leur place dans les mains du joueur.
     */
    public void ordonnerImages()
    {
      ImageView  img = (ImageView) this.findViewById(this.getBaseContext().getResources().getIdentifier("CarteJouer" + 1
                , "id", this.getBaseContext().getPackageName()));
        img.setImageDrawable(null);
        img.setClickable(false);
        for(int i=2;i<5;i++) {
            img = (ImageView) this.findViewById(this.getBaseContext().getResources().getIdentifier("CarteJouer" + i
                    , "id", this.getBaseContext().getPackageName()));
            for(int a=0;a<jeu.lstJoueurs.size();a++)
            if(jeu.lstJoueurs.get(a).nom==jeu.idJoueur.getNom())
            img.setImageResource(jeu.trouverIdCarte(jeu.lstJoueurs.get(a).avoirLaMain().get(i-2).nom));
        }
    }

    /**
     *  Met à jour l'image de la carte à gauche
     * @param ct carte qu iest à gauche.
     */
    public void mettreAJourImageGauche(Carte ct)
    {
            ImageView img = (ImageView) this.findViewById(this.getBaseContext().getResources().getIdentifier("CarteJouer" + 1
                    , "id", this.getBaseContext().getPackageName()));
            if (ct != null && jeu.lstJoueurs.get(0).jeuEnMain.size()==4) {
                img.setImageResource(jeu.trouverIdCarte(ct.nom));
                img.setClickable(true);
            } else if(jeu.lstJoueurs.get(0).jeuEnMain.size()!=4)
            {
                img.setImageDrawable(null);
                img.setClickable(false);
            }
    }

    /**
     *
     * @param v Paquet central
     */
    public void onClickPaquetDeCartes(View v) {
        mettreAJourImageGauche(jeu.jouerUnePige());
    }

    /**
     *
     * @param v Paquet central avec cartes visible.
     */
    public void onClickPigerCarteAVue(View v)
    {
        mettreAJourImageGauche(jeu.pigerUneCarteAVue());
        miseAJourImageCarteDejaPiger();
    }

    /**
     *
     * @param v Carte cliqué dans le propre jeu du joueur.
     * @throws IllegalAccessException
     */
    public void onClickJeuDuJoueur(View v) throws IllegalAccessException {
        changerSélection(v);

        for(Field f: campos)
            if(f.getInt(null)==v.getId()) {
                String str = f.getName();
                char dernier = str.charAt(str.length()-1);
                position=Character.getNumericValue(dernier);
                if(position==1)
                    position=4;
                else
                    //Pour le décalage causé par la derniere carte.
                    position--;
            }
        mettreCarteAVue();
                }

    /**
     * Met à jour le visuel des cartes et joue IA.
     */
    public void mettreCarteAVue() {
        if (jeu.lstJoueurs.get(0).avoirLaMain().size() == 4) {
            //Pour convertir en index tableau.
            position--;
            jeu.jouerUnChoix(jeu.lstJoueurs.get(0).avoirLaMain().get(position));
            if(!jeu.calculerSiFin()) {
                ordonnerImages();
                joueurHumanFin();
                if (jeu.jouerOrdinateurs()) {
                    miseAJourImageCarteDejaPiger();
                    joueurHumanDébut();
                    for(JoueurDu31 joueur: jeu.lstJoueurs)
                        if(joueur.Cogne)
                        {
                            Button btnCogner = (Button) this.findViewById(this.getBaseContext().getResources().getIdentifier("btnCogner"
                                    , "id", this.getBaseContext().getPackageName()));
                            btnCogner.setClickable(false);
                            lblIndication.setText("Un opposant a cogné!");
                        }
                } else {
                    procedureFinDeManche();
                }
            }
            else
                procedureFinDeManche();
        }
    }

    /**
     * Instructions pour le calcul de fin de manche et pour recommencer.
     */
    public void procedureFinDeManche()
    {
        joueurHumanFin();
        jeu.détermineGagnant();
        afficherFin();
        jeu.déterminePerdant();
        ImageView img = (ImageView) this.findViewById(this.getBaseContext().getResources().getIdentifier("imgRecommencer"
                , "id", this.getBaseContext().getPackageName()));
        img.setClickable(true);
        lblIndication.setText("Appuyez sur recommencer");

    }

    /**
     * S'occupe de l'affichage final des cartes pour les 3 joueurs.
     */
    public void afficherFin()
    {
        boolean joueurPerd=true;
        for(JoueurDu31 joueur : jeu.lstGagnants) {
            if (joueur.nom.contains("ordinateur")) {
                ImageView img;

                switch (Character.getNumericValue(joueur.nom.substring(10, 11).charAt(0))) {
                    case 1:
                        for (int i = 1; i < 4; i++) {
                            img = (ImageView) this.findViewById(this.getBaseContext().getResources().getIdentifier("CarteEnemi" + i
                                    , "id", this.getBaseContext().getPackageName()));
                            img.setImageResource(jeu.trouverIdCarte(joueur.jeuEnMain.get(i - 1).nom));
                        }
                        break;
                    case 2:
                        for (int i = 4; i < 7; i++) {
                            img = (ImageView) this.findViewById(this.getBaseContext().getResources().getIdentifier("CarteEnemi" + i
                                    , "id", this.getBaseContext().getPackageName()));
                            img.setImageResource(jeu.trouverIdCarte(joueur.jeuEnMain.get(i - 4).nom));
                        }
                        break;
                }
            }
            if(joueur.nom.contains(jeu.idJoueur.getNom())) {
                Toast.makeText(getApplicationContext(), getString(R.string.blackjack_gagner)
                        , Toast.LENGTH_LONG).show();
                joueurPerd=false;
            }
        }

        if(joueurPerd)
        {
            Toast.makeText(getApplicationContext(), getString(R.string.blackjack_perdu)
                    , Toast.LENGTH_LONG).show();
        }
        ordonnerImages();
    }
    /**
     * Remet disponible les actions du joueur.
     */
    public void joueurHumanDébut()
    {
        ImageView img;
        for(int i=2;i<5;i++) {
            img = (ImageView) this.findViewById(this.getBaseContext().getResources().getIdentifier("CarteJouer" + i
                    , "id", this.getBaseContext().getPackageName()));
            img.setClickable(true);
        }
        img = (ImageView) this.findViewById(this.getBaseContext().getResources().getIdentifier("CarteDejaPiger"
                , "id", this.getBaseContext().getPackageName()));
        img.setClickable(true);
        img = (ImageView) this.findViewById(this.getBaseContext().getResources().getIdentifier("PacketCarte"
                , "id", this.getBaseContext().getPackageName()));
        img.setClickable(true);
        Button btncogner = (Button) this.findViewById(this.getBaseContext().getResources().getIdentifier("btnCogner"
                , "id", this.getBaseContext().getPackageName()));
        btncogner.setClickable(true);
    }

    /**
     * Termine le tour du joueur human et l'empeche d'agir.
     */
    public void joueurHumanFin()
    {
        ImageView img;
        for(int i=2;i<5;i++) {
            img = (ImageView) this.findViewById(this.getBaseContext().getResources().getIdentifier("CarteJouer" + i
                    , "id", this.getBaseContext().getPackageName()));
            img.setClickable(false);
        }
        img = (ImageView) this.findViewById(this.getBaseContext().getResources().getIdentifier("CarteDejaPiger"
                , "id", this.getBaseContext().getPackageName()));
        img.setClickable(false);
        img = (ImageView) this.findViewById(this.getBaseContext().getResources().getIdentifier("PacketCarte"
                , "id", this.getBaseContext().getPackageName()));
        img.setClickable(false);
        Button btncogner = (Button) this.findViewById(this.getBaseContext().getResources().getIdentifier("btnCogner"
                , "id", this.getBaseContext().getPackageName()));
        btncogner.setClickable(false);
    }

    /**
     *
     * @param v Change la carte sélectionnée selon le joueur.
     */
    public void changerSélection(View v)
    {
        if(imageSélectionnée!=null) {
            //Swap des couleurs de click/unclick
            if (couleur1 == Color.TRANSPARENT)
                couleur1 = Color.BLUE;
            else
                couleur1 = Color.TRANSPARENT;
            imageSélectionnée.setBackgroundColor(couleur1);
            imageSélectionnée = (ImageView) findViewById(v.getId());
        }
        //Pour le premier click
        if(imageSélectionnée==null) {
            imageSélectionnée = (ImageView) findViewById(v.getId());
        }
        if(imageSélectionnée!=imagePremière) {
            //Swap des couleurs de click/unclick
            if (couleur1 == Color.TRANSPARENT)
                couleur1 = Color.BLUE;
            else
                couleur1 = Color.TRANSPARENT;
            imageSélectionnée.setBackgroundColor(couleur1);
        }
        if(imageSélectionnée==imagePremière && couleur1==Color.BLUE)
        {
            couleur1=Color.TRANSPARENT;
            imageSélectionnée.setBackgroundColor(couleur1);
        }
        else if(imageSélectionnée==imagePremière && couleur1== Color.TRANSPARENT)
        {
            couleur1=Color.BLUE;
            imageSélectionnée.setBackgroundColor(couleur1);
        }
        if(couleur1==Color.TRANSPARENT)
            imageSélectionnée=null;
        imagePremière=(ImageView) findViewById(v.getId());
    }

    /**
     * Annoncer son intention de finir la manche.
     * @param v
     */
    public void onClickCogner(View v)
    {
        jeu.annoncerFinDeManche();
        joueurHumanFin();
        jeu.jouerOrdinateurs();
        procedureFinDeManche();
    }

    /**
     * Placer une mise.
     * @param v
     */
    public void onClickMiser(View v)
    {
        NumberPicker numberPicker= (NumberPicker) this.findViewById(this.getBaseContext().getResources().getIdentifier("nbPicker"
                , "id", this.getBaseContext().getPackageName()));
        if(jeu.miserUneSomme(numberPicker.getValue())) {
            joueurHumanDébut();
            Button btnmiser = (Button) this.findViewById(this.getBaseContext().getResources().getIdentifier("btnMiser"
                    , "id", this.getBaseContext().getPackageName()));
            btnmiser.setClickable(false);
            lblIndication.setText("Jouez");
        }
    }
}