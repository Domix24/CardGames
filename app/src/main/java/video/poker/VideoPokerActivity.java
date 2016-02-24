package video.poker;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.NumberPicker;
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
    NumberPicker mise;
    TextView argent;
    JoueurSingleton joueur = JoueurSingleton.getInstance();
    Toast message;

    /**
     * Lorsque l'activité est crée
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_poker);
        mise = (NumberPicker)findViewById(R.id.nbpMise);
        mise.setMinValue(5);
        mise.setMaxValue(5000);
        mise.setValue(5);
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

    /**
     * Permet de reinitialiser le jeu
     */
    private void reinitialiserJeu(){
        jeu.reinitialiserJeu();
        carteValider.clear();
        mettreCarteANull(carteValider);
        mettreCarteANull(jeu.paquetPremier);
        mettreCarteANull(jeu.paquetFinal);
    }

    /**
     * Replacer le jeu
     */
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

    /**
     * Replacer les images dans le jeu
     * @param cartes les cartes a placer.
     */
    private void placerCartes(JeuDeCarte cartes){
        if (cartes.get(0).nom != "null"){
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
        else
            effacerImage();
    }

    /**
     * Lorsqu'on clique sur valider, valider les cartes et compter les points
     * @param v
     */
    public void onValiderVideoClick(View v) {
        if (jeu.aMisé){
            jeu.validerCartes(carteValider);
            replacerJeu();
            jeu.aMisé = false;
            float gain = jeu.compterPoints();
            joueur.addMontant(gain);
            float monnaie = joueur.getMonnaie();

            argent.setText(getString(R.string.argent) + String.valueOf(monnaie));
            if (gain != 0)
                message.setText(getString(R.string.blackjack_gagnier) + " " +String.valueOf(gain));
            else
                message.setText(R.string.blackjack_perdu);
            message.show();
            reinitialiserJeu();
        }

    }

    /**
     * Lorsqu'on clique sur miser, commencer la partie
     * @param v la vue du bouton
     */
    public void onMiserClick(View v) {
        if (!jeu.aMisé) {
            float miseTemp = mise.getValue();
            jeu.mise = joueur.getMontant(miseTemp);
            if (jeu.mise != 0){
                jeu.aMisé = true;
                float monnaie = joueur.getMonnaie();
                argent.setText(getString(R.string.argent) + String.valueOf(monnaie));
                // Commencer
                jeu.passerCartes();
                replacerJeu();
            }
        }
    }

    /**
     * Lorsqu'on clique sur une carte, la selectionner
     * @param v la view du bouton
     */
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

    /**
     * Met le nom des cartes a "null"
     * @param cartes le paquet de carte
     */
    private void mettreCarteANull(JeuDeCarte cartes){
        Carte cartenull = new Carte(1,1,1,"null", JeuDeCarte.type.Carre);
        for (int i = 0; i < 5; i++){
            cartes.add(cartenull);
        }
    }

    /**
     * Permet de valider ou de d'invalider une carte.
     * @param carte la carte a valider
     * @param index l'index dans le jeu de carte
     * @param img l'image de la carte
     */
    private void validerCarte(Carte carte, int index,ImageView img){
        if (carteValider.get(index).nom == "null"){
            carteValider.set(index, carte);
            img.setBackgroundColor(Color.GREEN);
        }
        else{
            Carte cartenull = new Carte(1,1,1,"null", JeuDeCarte.type.Carre);
            carteValider.set(index, cartenull);
            img.setBackgroundColor(Color.TRANSPARENT);
        }
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
    public void onGridClick(View v)
    {
        ImageView img = (ImageView)this.findViewById(R.id.grille);
        img.setVisibility(View.INVISIBLE);
        Button btnValider = (Button)this.findViewById(R.id.btnValiderVideo);
        btnValider.setVisibility(View.VISIBLE);
        Button btnMiser = (Button)this.findViewById(R.id.btnMiser);
        btnMiser.setVisibility(View.VISIBLE);
        NumberPicker nbp = (NumberPicker)this.findViewById(R.id.nbpMise);
        nbp.setVisibility(View.VISIBLE);
        TextView txtArgent = (TextView)this.findViewById(R.id.lblArgent);
        txtArgent.setVisibility(View.VISIBLE);
        Button btnAfficher = (Button)this.findViewById(R.id.btnAfficher);
        btnAfficher.setVisibility(View.VISIBLE);
        if(jeu.aMisé) {
            ImageView image = (ImageView) findViewById(R.id.imgVideo1);
            image.setVisibility(View.VISIBLE);
            image = (ImageView) findViewById(R.id.imgVideo2);
            image.setVisibility(View.VISIBLE);
            image = (ImageView) findViewById(R.id.imgVideo3);
            image.setVisibility(View.VISIBLE);
            image = (ImageView) findViewById(R.id.imgVideo4);
            image.setVisibility(View.VISIBLE);
            image = (ImageView) findViewById(R.id.imgVideo5);
            image.setVisibility(View.VISIBLE);
        }
    }
    public void onAfficherClick(View v)
    {
      ImageView img = (ImageView)this.findViewById(R.id.grille);
        img.setVisibility(View.VISIBLE);
        Button btnValider = (Button)this.findViewById(R.id.btnValiderVideo);
        btnValider.setVisibility(View.INVISIBLE);
        Button btnMiser = (Button)this.findViewById(R.id.btnMiser);
        btnMiser.setVisibility(View.INVISIBLE);
        Button btnAfficher = (Button)this.findViewById(R.id.btnAfficher);
        btnAfficher.setVisibility(View.INVISIBLE);
        NumberPicker nbp = (NumberPicker)this.findViewById(R.id.nbpMise);
        nbp.setVisibility(View.INVISIBLE);
        TextView txtArgent = (TextView)this.findViewById(R.id.lblArgent);
        txtArgent.setVisibility(View.INVISIBLE);
        ImageView image = (ImageView) findViewById(R.id.imgVideo1);
        image.setVisibility(View.INVISIBLE);
        image = (ImageView) findViewById(R.id.imgVideo2);
        image.setVisibility(View.INVISIBLE);
        image = (ImageView) findViewById(R.id.imgVideo3);
        image.setVisibility(View.INVISIBLE);
        image = (ImageView) findViewById(R.id.imgVideo4);
        image.setVisibility(View.INVISIBLE);
        image = (ImageView) findViewById(R.id.imgVideo5);
        image.setVisibility(View.INVISIBLE);
    }
}
