package com.example.android.miwok;

import android.graphics.drawable.ColorDrawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;

public class FamilyMembers extends AppCompatActivity {

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

        final ArrayList<Word> familyMember = new ArrayList<>();
        familyMember.add(new Word("Father", "पिता , बाबू", R.drawable.family_father, R.raw.father));
        familyMember.add(new Word("Mother", "माँ , माए", R.drawable.family_mother, R.raw.mother));
//        familyMember.add(new Word("Husband", "पति , भतार"));
//        familyMember.add(new Word("Wife", "बीबी , भार्या"));
        familyMember.add(new Word("Son", "पूत , बेटा", R.drawable.family_son, R.raw.son));
        familyMember.add(new Word("Daughter", "बेटी", R.drawable.family_daughter, R.raw.daughter));
        familyMember.add(new Word("Brother", "भाई", R.drawable.family_older_brother, R.raw.brother));
        familyMember.add(new Word("Sister", "बहिन , बहिनि", R.drawable.family_older_sister, R.raw.sister));
//        familyMember.add(new Word("Uncle", "चाचा"));
//        familyMember.add(new Word("Aunt", "चाची"));
//        familyMember.add(new Word("Maternal Uncle", "मामा"));
//        familyMember.add(new Word("Maternal Aunt", "मामी"));

        WordAdapter wordAdapter = new WordAdapter(this, familyMember, R.color.Family);

        GridView listView = (GridView) findViewById(R.id.list);

        listView.setAdapter(wordAdapter);

        //Setting onItemClickListener to the list of  FamilyMembers
        listView.setOnItemClickListener(new GridView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //When gridView is clicked

                if (t != null) t.cancel();
                if (mediaPlayer != null) {
                    mediaPlayer.release();
                    mediaPlayer = null;
                }

                Word word = familyMember.get(position);

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