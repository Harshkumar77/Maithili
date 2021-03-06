package com.example.android.miwok;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.graphics.drawable.ColorDrawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;

public class Colors extends AppCompatActivity {

    private Toast t;

    private MediaPlayer mediaPlayer;

    private AudioManager audioManager;

    private AudioManager.OnAudioFocusChangeListener onAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {
            if (mediaPlayer != null) {
                if (focusChange == AudioManager.AUDIOFOCUS_GAIN_TRANSIENT ||
                        focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK ||
                        focusChange == AudioManager.AUDIOFOCUS_LOSS) {
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
            mediaPlayer = null;
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

        final ArrayList<Word> colors = new ArrayList<>();
        colors.add(new Word("Red", "????????????", R.drawable.color_red, R.raw.red));
//        colors.add(new Word("Orange", "???????????????"));
//        colors.add(new Word("Blue", "????????????"));
        colors.add(new Word("Yellow", "????????????", R.drawable.color_mustard_yellow, R.raw.yellow));
        colors.add(new Word("Green", "???????????????", R.drawable.color_green, R.raw.green));
//        colors.add(new Word("Purple", "??????????????????"));
        colors.add(new Word("Black", "????????????", R.drawable.color_black, R.raw.black));
        colors.add(new Word("White", "???????????????", R.drawable.color_white, R.raw.white));
        colors.add(new Word("Gray", "??????", R.drawable.color_gray, R.raw.gray));
        colors.add(new Word("Brown", "???????????????", R.drawable.color_brown, R.raw.brown));

        WordAdapter wordAdapter = new WordAdapter(this, colors, R.color.Color);

        GridView listView = (GridView) findViewById(R.id.list);

        listView.setAdapter(wordAdapter);

        //Setting onItemClickListener to the list of Colors
        listView.setOnItemClickListener(new GridView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //When gridView is clicked

                if (t != null) t.cancel();
                if (mediaPlayer != null) mediaPlayer.release();

                Word word = colors.get(position);

                int result = audioManager.requestAudioFocus(onAudioFocusChangeListener,
                        AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
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