package com.example.android.miwok;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.widget.GridView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;

public class Phrase extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.words_list);

        if(getSupportActionBar()!=null)
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(getBaseContext(), R.color.Phrase)));
        //Array list of phrases

        ArrayList<Word> phrases = new ArrayList<>();

        phrases.add(new Word("Harsh","Kumar"));
        phrases.add(new Word("",""));
        phrases.add(new Word("",""));
        phrases.add(new Word("",""));
        phrases.add(new Word("",""));
        phrases.add(new Word("",""));
        phrases.add(new Word("",""));
        phrases.add(new Word("",""));
        phrases.add(new Word("",""));
        phrases.add(new Word("",""));

        WordAdapter wordAdapter = new WordAdapter(this, phrases,R.color.Phrase);

        GridView listView = (GridView) findViewById(R.id.list);

        listView.setAdapter(wordAdapter);
    }
}