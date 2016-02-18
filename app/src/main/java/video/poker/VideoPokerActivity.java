package video.poker;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.utilisateur.jeudepatience.R;

import utilitaire.Carte;
import utilitaire.JeuDeCarte;

public class VideoPokerActivity extends Activity {
    JeuDeCarte cartePresente = null;
    JeuDeCarte carteValider = new JeuDeCarte();
    VideoPoker jeu = VideoPoker.avoirInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_poker);

        if (!jeu.estCree) {
            reinitialiserJeu();

        } else
            replacerJeu();
    }
    private void reinitialiserJeu(){
        jeu.reinitialiserJeu();
        jeu.passerCartes();
        carteValider.clear();
        mettreCarteANull(carteValider);
        replacerJeu();
    }
    private void replacerJeu(){
        if (jeu.aValider){
            placerCartes(jeu.paquetFinal);
            cartePresente = jeu.paquetFinal;
        }
        else
        {
            placerCartes(jeu.paquetPremier);
            cartePresente = jeu.paquetPremier;
        }
    }
    private void placerCartes(JeuDeCarte cartes){
        ImageView image = (ImageView) findViewById(R.id.imgVideo1);
        image.setImageResource(jeu.trouverIdCarte(cartes.get(0).nom));

        image = (ImageView) findViewById(R.id.imgVideo2);
        image.setImageResource(jeu.trouverIdCarte(cartes.get(1).nom));

        image = (ImageView) findViewById(R.id.imgVideo3);
        image.setImageResource(jeu.trouverIdCarte(cartes.get(2).nom));

        image = (ImageView) findViewById(R.id.imgVideo4);
        image.setImageResource(jeu.trouverIdCarte(cartes.get(3).nom));

        image = (ImageView) findViewById(R.id.imgVideo5);
        image.setImageResource(jeu.trouverIdCarte(cartes.get(4).nom));
    }
    public void onValiderVideoClick(View v) {
        jeu.validerCartes(carteValider);
        replacerJeu();
    }
    public void onRecommencerClick(View v) {
        jeu.reinitialiserJeu();
        jeu.passerCartes();
        replacerJeu();
    }
    public void onMiserClick(View v) {
        
    }
    public void onCardVideoClick(View v) {
        switch(v.getId()){
            case R.id.imgVideo1:
                validerCarte(cartePresente.get(0), 0);
                break;
            case R.id.imgVideo2:
                validerCarte(cartePresente.get(1), 1);
                break;
            case R.id.imgVideo3:
                validerCarte(cartePresente.get(2), 2);
                break;
            case R.id.imgVideo4:
                validerCarte(cartePresente.get(3), 3);
                break;
            case R.id.imgVideo5:
                validerCarte(cartePresente.get(4), 4);
                break;
        }
    }
    private void mettreCarteANull(JeuDeCarte cartes){
        Carte cartenull = new Carte(1,1,1,"null", JeuDeCarte.type.Carre);
        for (int i = 0; i < 5; i++){
            cartes.add(cartenull);
        }
    }
    private void validerCarte(Carte carte, int index){
        if (carteValider.get(index).nom == "null"){
            carteValider.set(index, carte);
        }
        else{
            Carte cartenull = new Carte(1,1,1,"null", JeuDeCarte.type.Carre);
            carteValider.set(index, cartenull);

        }
    }
}
