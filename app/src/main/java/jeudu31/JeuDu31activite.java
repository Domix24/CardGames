
package jeudu31;

import android.graphics.Color;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.ImageView;

import com.example.utilisateur.jeudepatience.R;

import java.lang.reflect.Field;

public class JeuDu31activite extends Activity {
    Field[] campos;
    String premiereCarte;
    ImageView imageSélectionnée;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jeudu31activite);
        JeuDu31Logique jeu = new JeuDu31Logique();
        campos = R.id.class.getFields();
        premiereCarte="";
    }
    public void onClickJeuDuJoueur(View v) throws IllegalAccessException {
        for(Field f: campos)
            if(f.getInt(null)==v.getId()) {
                if (premiereCarte == "") {
                    if(imageSélectionnée!=null)
                        imageSélectionnée.setBackgroundColor(Color.TRANSPARENT);
                    imageSélectionnée = (ImageView) findViewById(v.getId());
                    imageSélectionnée.setBackgroundColor(Color.BLUE);
                    premiereCarte = f.getName();
                    premiereCarte = "";
                }
            }
    }
}