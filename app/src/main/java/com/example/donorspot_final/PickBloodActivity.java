package com.example.donorspot_final;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

public class PickBloodActivity extends AppCompatActivity {

    private AppCompatButton selectedSquareButton = null;
    private AppCompatButton selectedSmallerButton = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_blood);

        findViewById(R.id.A).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectSquareButton((AppCompatButton) view);
            }
        });

        // Repeat for the other square buttons
        findViewById(R.id.B).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectSquareButton((AppCompatButton) view);
            }
        });

        findViewById(R.id.O).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectSquareButton((AppCompatButton) view);
            }
        });

        findViewById(R.id.AB).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectSquareButton((AppCompatButton) view);
            }
        });

        findViewById(R.id.positive).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectSmallerButton((AppCompatButton) view);
            }
        });

        findViewById(R.id.negative).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectSmallerButton((AppCompatButton) view);
            }
        });

        /////////////////
        // back button //
        /////////////////
        TextView backToDOB = findViewById(R.id.back_to_dob);
        backToDOB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        /////////////////////
        // continue button //
        /////////////////////
        AppCompatButton continueButton = findViewById(R.id.continue_button);
        // Set an OnClickListener to the button
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the DOBAddress activity
                Intent intent = new Intent(PickBloodActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void selectSquareButton(AppCompatButton button) {
        if (selectedSquareButton != null) {
            selectedSquareButton.setSelected(false);
        }
        button.setSelected(true);
        selectedSquareButton = button;
    }

    private void selectSmallerButton(AppCompatButton button) {
        if (selectedSmallerButton != null) {
            selectedSmallerButton.setSelected(false);
        }
        button.setSelected(true);
        selectedSmallerButton = button;
    }

}
