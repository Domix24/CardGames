
package jeudu31;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.ImageView;

import com.example.utilisateur.jeudepatience.R;

import java.lang.reflect.Field;

public class JeuDu31activite extends Activity {
    Field[] campos;
    String premiereCarte;
    String deuxiemeCarte;
    ImageView imageSélectionnée;
    ImageView imagePremière;
    int couleur1=0;
    int couleur2=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jeudu31activite);
        JeuDu31Logique jeu = new JeuDu31Logique();
        campos = R.id.class.getFields();
        premiereCarte="";
        deuxiemeCarte="";
        couleur2=Color.TRANSPARENT;
        couleur1=Color.TRANSPARENT;
    }
    public void onClickJeuDuJoueur(View v) throws IllegalAccessException {
        /**
         * for(Field f: campos)
         if(f.getInt(null)==v.getId()) {
         if(premiereCarte=="") {
         */
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
    }