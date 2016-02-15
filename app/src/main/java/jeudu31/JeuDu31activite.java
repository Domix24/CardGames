
package jeudu31;

import android.os.Bundle;
import android.app.Activity;

import com.example.utilisateur.jeudepatience.R;

public class JeuDu31activite extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jeudu31activite);
        JeuDu31Logique jeu = new JeuDu31Logique();
    }

}