package com.example.utilisateur.jeudepatience;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import blackjack.BlackJackActivity;
import pyramide.pyramideActivity;
import video.poker.VideoPokerActivity;

public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void onBlackJackClick(View v){
        Intent ouvrirBlackJack = new Intent(this, BlackJackActivity.class);
        startActivity(ouvrirBlackJack);
    }
    public void onPyramidClick(View v){
        Intent ouvrirPyramid = new Intent(this, pyramideActivity.class);
        startActivity(ouvrirPyramid);
    }
    public void onVideoPokerClick(View v){
        Intent ouvrirVideoPoker = new Intent(this, VideoPokerActivity.class);
        startActivity(ouvrirVideoPoker);
    }
}
