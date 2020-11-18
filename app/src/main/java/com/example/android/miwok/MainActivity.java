package com.example.android.miwok;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    /**
     * When app is launched
     * activity_main the parent layout
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        int beta = 5;
        getSupportActionBar().hide();
        // Find the View that shows the numbers category
        TextView numbers = (TextView) findViewById(R.id.Numbers);

// Set a click listener on that View
        numbers.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the numbers View is clicked on.
            @Override
            public void onClick(View view) {
                Intent numbersIntent = new Intent(MainActivity.this, Number.class);
                startActivity(numbersIntent);
            }
        });

        // Find the View that shows the numbers category
        TextView FamilyMember = (TextView) findViewById(R.id.Family_members);

// Set a click listener on that View
        FamilyMember.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the numbers View is clicked on.
            @Override
            public void onClick(View view) {
                Intent numbersIntent = new Intent(MainActivity.this, FamilyMembers.class);
                startActivity(numbersIntent);
            }
        });

        // Find the View that shows the numbers category
        TextView Color = (TextView) findViewById(R.id.Color);

// Set a click listener on that View
        Color.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the numbers View is clicked on.
            @Override
            public void onClick(View view) {
                Intent numbersIntent = new Intent(MainActivity.this, Colors.class);
                startActivity(numbersIntent);
            }
        });

        // Find the View that shows the numbers category
        TextView Phrase = (TextView) findViewById(R.id.Phrase);

// Set a click listener on that View
        Phrase.setOnClickListener(new View.OnClickListener() {
            // The code in this method will be executed when the numbers View is clicked on.
            @Override
            public void onClick(View view) {
                Intent numbersIntent = new Intent(MainActivity.this, Phrase.class);
                startActivity(numbersIntent);
            }
        });
    }
}