package com.example.android.miwok;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;

public class Colors extends AppCompatActivity {

    private Toast t;

    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.words_list);

        if (getSupportActionBar() != null)
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(getBaseContext(), R.color.Color)));

        final ArrayList<Word> colors = new ArrayList<>();
        colors.add(new Word("Red", "ललका", R.drawable.color_red, R.raw.red));
//        colors.add(new Word("Orange", "नारंग"));
//        colors.add(new Word("Blue", "नीला"));
        colors.add(new Word("Yellow", "पियर", R.drawable.color_mustard_yellow, R.raw.yellow));
        colors.add(new Word("Green", "हरिअर", R.drawable.color_green, R.raw.green));
//        colors.add(new Word("Purple", "बैंगनी"));
        colors.add(new Word("Black", "करिआ", R.drawable.color_black, R.raw.black));
        colors.add(new Word("White", "उज्जर", R.drawable.color_white, R.raw.white));
        colors.add(new Word("Gray", "पह", R.drawable.color_gray, R.raw.gray));
        colors.add(new Word("Brown", "भुल्ल", R.drawable.color_brown, R.raw.brown));

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