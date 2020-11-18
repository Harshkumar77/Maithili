package com.example.android.miwok;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;

public class Phrase extends AppCompatActivity {

    private Toast t;

    private MediaPlayer mediaPlayer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.words_list);

        if (getSupportActionBar() != null)
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(getBaseContext(), R.color.Phrase)));

        //ArrayList of the numbers in English

        final ArrayList<Word> phrases = new ArrayList<>();

        phrases.add(new Word("How are you ? ","अहाँ केहन छी?",R.raw.how_are_you));
        phrases.add(new Word("I am fine.","हम ठीक छी । ",R.raw.i_am_fine));
        phrases.add(new Word("What is your name ?","अहके नाम कथि भेल ?",R.raw.what_is_your_name));
        phrases.add(new Word("My name is ______ .","हमार नाम _____ छी।",R.raw.my_name_is));
        WordAdapter wordAdapter = new WordAdapter(this, phrases, R.color.Phrase);

        GridView listView = (GridView) findViewById(R.id.list);

        listView.setAdapter(wordAdapter);


        //Setting onItemClickListener to the list of Numbers
        listView.setOnItemClickListener(new GridView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //When gridView is clicked

                if (t != null) t.cancel();
                if (mediaPlayer != null) mediaPlayer.release();

                Word word = phrases.get(position);
                t = Toast.makeText(getBaseContext(), word.getMiwokTranslation(), Toast.LENGTH_SHORT);
                t.show();
                mediaPlayer = MediaPlayer.create(getBaseContext(), word.getmAudio());
                mediaPlayer.start();
            }
        });

    }

    @Override
    protected void onStop() {
        super.onStop();
        if (t != null) t.cancel();
        if (mediaPlayer != null) mediaPlayer.release();

    }

}