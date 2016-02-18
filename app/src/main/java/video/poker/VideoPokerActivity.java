package video.poker;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.utilisateur.jeudepatience.R;

import utilitaire.Carte;
import utilitaire.JeuDeCarte;
import utilitaire.JoueurSingleton;

public class VideoPokerActivity extends Activity {
    JeuDeCarte cartePresente = null;
    JeuDeCarte carteValider = new JeuDeCarte();
    VideoPoker jeu = VideoPoker.avoirInstance();
    TextView mise;
    TextView argent;
    JoueurSingleton joueur = JoueurSingleton.getInstance();
    Toast message;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_poker);
        mise = (TextView)findViewById(R.id.lblMise);
        argent = (TextView)findViewById(R.id.lblArgent);
        message = Toast.makeText(this, "", Toast.LENGTH_SHORT);
        float monnaie = joueur.getMonnaie();
        argent.setText(getString(R.string.argent) + String.valueOf(monnaie));
        if (!jeu.estCree) {
            reinitialiserJeu();
            effacerImage();
        } else
            replacerJeu();
    }
    private void reinitialiserJeu(){
        jeu.reinitialiserJeu();
        carteValider.clear();
        mettreCarteANull(carteValider);
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
        image.setVisibility(View.VISIBLE);
        image.setBackgroundColor(Color.TRANSPARENT);

        image = (ImageView) findViewById(R.id.imgVideo2);
        image.setImageResource(jeu.trouverIdCarte(cartes.get(1).nom));
        image.setVisibility(View.VISIBLE);
        image.setBackgroundColor(Color.TRANSPARENT);

        image = (ImageView) findViewById(R.id.imgVideo3);
        image.setImageResource(jeu.trouverIdCarte(cartes.get(2).nom));
        image.setVisibility(View.VISIBLE);
        image.setBackgroundColor(Color.TRANSPARENT);

        image = (ImageView) findViewById(R.id.imgVideo4);
        image.setImageResource(jeu.trouverIdCarte(cartes.get(3).nom));
        image.setVisibility(View.VISIBLE);
        image.setBackgroundColor(Color.TRANSPARENT);

        image = (ImageView) findViewById(R.id.imgVideo5);
        image.setImageResource(jeu.trouverIdCarte(cartes.get(4).nom));
        image.setVisibility(View.VISIBLE);
        image.setBackgroundColor(Color.TRANSPARENT);
    }
    public void onValiderVideoClick(View v) {
        if (jeu.aMisé){
            jeu.validerCartes(carteValider);
            replacerJeu();
            jeu.aMisé = false;
            float gain = jeu.compterPoints();
            joueur.AddMontant(gain);
            float monnaie = joueur.getMonnaie();

            argent.setText(R.string.argent + String.valueOf(monnaie));
            if (gain != 0)
                message.setText(getString(R.string.blackjack_gagnier) + " " +String.valueOf(gain));
            else
                message.setText(R.string.blackjack_perdu);
            message.show();
            reinitialiserJeu();
        }

    }
    public void onRecommencerClick(View v) {
        if (jeu.aMisé){
            jeu.reinitialiserJeu();
            jeu.passerCartes();
            replacerJeu();
        }

    }
    public void onMiserClick(View v) {
        if (!jeu.aMisé) {
            float miseTemp = Float.parseFloat(mise.getText().toString());
            jeu.mise = joueur.getMontant(miseTemp);
            if (jeu.mise != 0){
                jeu.aMisé = true;
            }
            float monnaie = joueur.getMonnaie();
            argent.setText(getString(R.string.argent) + String.valueOf(monnaie));
            // Commencer
            jeu.passerCartes();
            replacerJeu();
        }
    }
    public void onCardVideoClick(View v) {

        if (jeu.aMisé){
            ImageView imageSélectionnée;
            imageSélectionnée = (ImageView)findViewById(v.getId());

            switch(v.getId()){
                case R.id.imgVideo1:
                    validerCarte(cartePresente.get(0), 0,imageSélectionnée);
                    break;
                case R.id.imgVideo2:
                    validerCarte(cartePresente.get(1), 1,imageSélectionnée);
                    break;
                case R.id.imgVideo3:
                    validerCarte(cartePresente.get(2), 2,imageSélectionnée);
                    break;
                case R.id.imgVideo4:
                    validerCarte(cartePresente.get(3), 3,imageSélectionnée);
                    break;
                case R.id.imgVideo5:
                    validerCarte(cartePresente.get(4), 4,imageSélectionnée);
                    break;
            }
        }
    }
    private void mettreCarteANull(JeuDeCarte cartes){
        Carte cartenull = new Carte(1,1,1,"null", JeuDeCarte.type.Carre);
        for (int i = 0; i < 5; i++){
            cartes.add(cartenull);
        }
    }
    private void validerCarte(Carte carte, int index,ImageView img){
        if (carteValider.get(index).nom == "null"){
            carteValider.set(index, carte);
            message.setText("Carte " + carte.nom + " selectionné.");
            img.setBackgroundColor(Color.GREEN);
        }
        else{
            Carte cartenull = new Carte(1,1,1,"null", JeuDeCarte.type.Carre);
            carteValider.set(index, cartenull);
            message.setText("Carte " + carte.nom + " deselectionné.");
            img.setBackgroundColor(Color.TRANSPARENT);
        }
        message.show();
    }
    /**
     * Rendre les images de cartes invisibles.
     */
    private void effacerImage() {
        // Reinitialiser les images
        ImageView image = (ImageView) findViewById(R.id.imgVideo1);
        image.setVisibility(View.INVISIBLE);
        image.setBackgroundColor(Color.TRANSPARENT);

         image = (ImageView) findViewById(R.id.imgVideo2);
        image.setVisibility(View.INVISIBLE);
        image.setBackgroundColor(Color.TRANSPARENT);

         image = (ImageView) findViewById(R.id.imgVideo3);
        image.setVisibility(View.INVISIBLE);
        image.setBackgroundColor(Color.TRANSPARENT);

         image = (ImageView) findViewById(R.id.imgVideo4);
        image.setVisibility(View.INVISIBLE);
        image.setBackgroundColor(Color.TRANSPARENT);

         image = (ImageView) findViewById(R.id.imgVideo5);
        image.setVisibility(View.INVISIBLE);
        image.setBackgroundColor(Color.TRANSPARENT);
    }
}
