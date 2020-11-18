package com.example.android.miwok;

import android.graphics.drawable.ColorDrawable;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.words_list);

        if(getSupportActionBar()!=null)
            getSupportActionBar().setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(getBaseContext(),R.color.Family)));

        final ArrayList<Word> familyMember = new ArrayList<>();
        familyMember.add(new Word("Father", "पिता , बाबू",R.drawable.family_father));
        familyMember.add(new Word("Mother", "माँ , माए",R.drawable.family_mother));
//        familyMember.add(new Word("Husband", "पति , भतार"));
//        familyMember.add(new Word("Wife", "बीबी , भार्या"));
        familyMember.add(new Word("Son", "पूत , बेटा",R.drawable.family_son));
        familyMember.add(new Word("Daughter", "बेटी",R.drawable.family_daughter));
        familyMember.add(new Word("Brother", "भाई",R.drawable.family_older_brother));
        familyMember.add(new Word("Sister", "बहिन , बहिनि",R.drawable.family_older_sister));
//        familyMember.add(new Word("Uncle", "चाचा"));
//        familyMember.add(new Word("Aunt", "चाची"));
//        familyMember.add(new Word("Maternal Uncle", "मामा"));
//        familyMember.add(new Word("Maternal Aunt", "मामी"));

        WordAdapter wordAdapter = new WordAdapter(this, familyMember,R.color.Family);

        GridView listView = (GridView) findViewById(R.id.list);

        listView.setAdapter(wordAdapter);

        //Setting onItemClickListener to the list of  FamilyMembers
        listView.setOnItemClickListener(new GridView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //When gridView is clicked
                if(t!=null){
                    t.cancel();
                }
                Word word = familyMember.get(position);
                t=Toast.makeText(getBaseContext(), word.getMiwokTranslation(), Toast.LENGTH_SHORT);
                t.show();
            }
        });

    }

    @Override
    protected void onStop() {
        super.onStop();
        if(t!=null){
            t.cancel();
        }

    }
}