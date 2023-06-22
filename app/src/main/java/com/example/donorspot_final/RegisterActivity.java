package com.example.donorspot_final;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {
    DatabaseReference databaseReference = FirebaseDatabase.getInstance("https://donorspot-9b8f9-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference();
    EditText editTextName, editTextPhone, editTextEmail, editTextPassword;

    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editTextName = findViewById(R.id.name);
        editTextPhone = findViewById(R.id.phone);
        editTextEmail = findViewById(R.id.email);
        editTextPassword = findViewById(R.id.password);

        // firebase authentication //
        mAuth = FirebaseAuth.getInstance();


        // back button //
        TextView backToLogin = findViewById(R.id.back_to_login);
        backToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        // next button //
        AppCompatButton nextToDOBAddress = findViewById(R.id.next_to_DOBAddress);
        // Set an OnClickListener to the button
        nextToDOBAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editTextName.getText().toString();
                String phone = editTextPhone.getText().toString();
                String email = editTextEmail.getText().toString();
                String password = editTextPassword.getText().toString();

                // Check if email is valid
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    Toast.makeText(RegisterActivity.this, "Please enter a valid email address", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Check if mobile number is valid
                String phoneNumber = editTextPhone.getText().toString();
                if (!Patterns.PHONE.matcher(phone).matches() || phone.length() != 10) {
                    Toast.makeText(RegisterActivity.this, "Please enter a valid phone number", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Check if all fields are filled in
                if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Check if password is at least 6 characters long
                if (password.length() < 6) {
                    Toast.makeText(RegisterActivity.this, "Password must be at least 6 characters long", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Call the signUp method if all fields are filled in
                signUp(email, password, name, phone);

                // Start the DOBAddress activity
                Intent intent = new Intent(RegisterActivity.this, DOB_Address_Activity.class);
                intent.putExtra("name", name);
                intent.putExtra("phone", phone);
                intent.putExtra("email", email);
                intent.putExtra("password", password);
                startActivity(intent);

            }
        });

    }

    private void signUp(String name, String phone, String email, String password) {
        User user = new User(name, phone, email, password);

        String userId = databaseReference.child("users").push().getKey();
        assert userId != null;
        databaseReference.child("users").child(userId).setValue(user);


        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign up success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            if (user != null){
                                String uid = user.getUid();
                                // Create a User object with the data
                                User userObj = new User(name, phone, email, password);
                                // Write the data to the database
                                databaseReference.child("users").child(uid).setValue(userObj);
                                //updateUI(user);
                            } else {
                                Log.w(TAG, "User is not authenticated");
                            }
                        } else {
                            // If sign up fails, display a message to the user.
                            Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }
                    }
                });
    }

}