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

public class Number extends AppCompatActivity {

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
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(getBaseContext(), R.color.StatusBar)));

        if (getWindow() != null)
            getWindow().setStatusBarColor(ContextCompat.getColor(getBaseContext(), R.color.StatusBar));


        audioManager = (AudioManager) getSystemService(AUDIO_SERVICE);

        //ArrayList of the numbers in English

        final ArrayList<Word> numbers = new ArrayList<>();
        numbers.add(new Word("Zero", "शून्य", R.drawable.color_white, R.raw.zero));
        numbers.add(new Word("One", "एक", R.drawable.number_one, R.raw.one));
        numbers.add(new Word("Two", "दो", R.drawable.number_two, R.raw.two));
        numbers.add(new Word("Three", "तीन", R.drawable.number_three, R.raw.three));
        numbers.add(new Word("Four", "चार", R.drawable.number_four, R.raw.four));
        numbers.add(new Word("Five", "पांच", R.drawable.number_five, R.raw.five));
        numbers.add(new Word("Six", "छह", R.drawable.number_six, R.raw.six));
        numbers.add(new Word("Seven", "सात", R.drawable.number_seven, R.raw.seven));
        numbers.add(new Word("Eight", "आठ", R.drawable.number_eight, R.raw.eight));
        numbers.add(new Word("Nine", "नौ", R.drawable.number_nine, R.raw.nine));
        numbers.add(new Word("Ten", "दस", R.drawable.number_ten, R.raw.ten));

        WordAdapter wordAdapter = new WordAdapter(this, numbers, R.color.Number);

        GridView listView = (GridView) findViewById(R.id.list);

        listView.setAdapter(wordAdapter);


        //Setting onItemClickListener to the list of Numbers
        listView.setOnItemClickListener(new GridView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //When gridView is clicked

                if (t != null) t.cancel();
                if (mediaPlayer != null) mediaPlayer.release();

                Word word = numbers.get(position);

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