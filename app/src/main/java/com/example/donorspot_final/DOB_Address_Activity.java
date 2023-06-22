package com.example.donorspot_final;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.HashMap;

public class DOB_Address_Activity extends AppCompatActivity {

    DatabaseReference databaseReference = FirebaseDatabase.getInstance("https://donorspot-9b8f9-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference();
    private FirebaseAuth mAuth;
    private NumberPicker dayPicker, monthPicker, yearPicker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dob_address);

        String state = ((EditText)findViewById(R.id.state)).getText().toString();
        String city = ((EditText)findViewById(R.id.City)).getText().toString();
        String pincode = ((EditText)findViewById(R.id.pin_code)).getText().toString();

        String address = "address: " + state + ", " + city + ", " + pincode;


        Button calendarButton = findViewById(R.id.calendar_button);
        dayPicker = findViewById(R.id.day_picker);
        monthPicker = findViewById(R.id.month_picker);
        yearPicker = findViewById(R.id.year_picker);

        //number picker values and ranges
        dayPicker.setMinValue(1);
        dayPicker.setMaxValue(31);

        monthPicker.setMinValue(1);
        monthPicker.setMaxValue(12);

        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        yearPicker.setMinValue(currentYear - 100);
        yearPicker.setMaxValue(currentYear);

        calendarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the current date from the date pickers
                int day = dayPicker.getValue();
                int month = monthPicker.getValue();
                int year = yearPicker.getValue();

                // Create a new DatePickerDialog and set the initial date to the current date
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        DOB_Address_Activity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                // Set the new date in the date pickers
                                dayPicker.setValue(dayOfMonth);
                                monthPicker.setValue(monthOfYear + 1);
                                yearPicker.setValue(year);
                            }
                        },
                        year,
                        month - 1,
                        day
                );

                // Show the date picker dialog
                datePickerDialog.show();
            }
        });


        // next button //
        AppCompatButton nextToPickBlood = findViewById(R.id.next_to_pick_blood);
        // Set an OnClickListener to the button
        nextToPickBlood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the selected date from the date pickers
                int day = dayPicker.getValue();
                int month = monthPicker.getValue();
                int year = yearPicker.getValue();

                // Create a string representation of the selected date
                String dob = String.format("%02d/%02d/%04d", day, month, year);

                // Get the address from the EditText fields
                String state = ((EditText)findViewById(R.id.state)).getText().toString();
                String city = ((EditText)findViewById(R.id.City)).getText().toString();
                String pincode = ((EditText)findViewById(R.id.pin_code)).getText().toString();
                String address = "address: " + state + ", " + city + ", " + pincode;

                // Create a HashMap to store the date of birth and address
                HashMap<String, String> dobAddressMap = new HashMap<>();
                dobAddressMap.put("date_of_birth", dob);
                dobAddressMap.put("address", address);

                // Store the dobAddressMap in the Firebase Realtime Database
                FirebaseUser currentUser = mAuth.getCurrentUser();
                if (currentUser != null) {
                    String userId = currentUser.getUid();
                    databaseReference.child("users").child(userId).child("dob_address").setValue(dobAddressMap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        // Start the PickBloodActivity
                                        Intent intent = new Intent(DOB_Address_Activity.this, PickBloodActivity.class);
                                        startActivity(intent);
                                    } else {
                                        Toast.makeText(DOB_Address_Activity.this, "Failed to store date of birth and address",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });


        // back button //
        TextView backToRegister = findViewById(R.id.back_to_register);
        backToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        @SuppressLint("CutPasteId")
        Button calendarButtonAnim = findViewById(R.id.calendar_button);
        calendarButtonAnim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the current date from the date pickers
                int day = dayPicker.getValue();
                int month = monthPicker.getValue();
                int year = yearPicker.getValue();

                // Create a new DatePickerDialog and set the initial date to the current date
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        DOB_Address_Activity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                // Set the new date in the date pickers
                                dayPicker.setValue(dayOfMonth);
                                monthPicker.setValue(monthOfYear + 1);
                                yearPicker.setValue(year);
                            }
                        },
                        year,
                        month - 1,
                        day
                );

                // Show the date picker dialog
                datePickerDialog.show();
            }
        });
        mAuth = FirebaseAuth.getInstance();

    }
}