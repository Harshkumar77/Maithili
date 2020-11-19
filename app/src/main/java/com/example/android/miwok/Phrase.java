package com.example.android.miwok;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.AudioManager;
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

    private AudioManager audioManager ;

    private AudioManager.OnAudioFocusChangeListener onAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if (mediaPlayer != null) {
                if (focusChange ==AudioManager.AUDIOFOCUS_GAIN_TRANSIENT ||
                        focusChange ==AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK ||
                        focusChange ==AudioManager.AUDIOFOCUS_LOSS) {
                    mediaPlayer.release();
                    mediaPlayer = null;
                }
            }
        }
    };

    private MediaPlayer.OnCompletionListener mediaCompletionListener = new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            if (mediaPlayer != null) mediaPlayer.release();
            mediaPlayer=null;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.words_list);

        if (getSupportActionBar() != null)
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(getBaseContext(), R.color.Phrase)));

        audioManager= (AudioManager) getSystemService(AUDIO_SERVICE);

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

                int result = audioManager.requestAudioFocus(onAudioFocusChangeListener,
                        AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED){
                    t = Toast.makeText(getBaseContext(), word.getMiwokTranslation(), Toast.LENGTH_SHORT);
                    t.show();
                    mediaPlayer = MediaPlayer.create(getBaseContext(), word.getmAudio());
                    mediaPlayer.start();
                    mediaPlayer.setOnCompletionListener(mediaCompletionListener);

                }

            }
        });

    }

    @Override
    protected void onStop() {
        super.onStop();
        if (t != null) t.cancel();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}