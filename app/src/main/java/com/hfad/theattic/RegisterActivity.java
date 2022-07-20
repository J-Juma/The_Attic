package com.hfad.theattic;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    // Declaring instances of the views
    private Button registerBtn;
    private EditText emailField, usernameField, passwordField;
    private TextView loginTxtView;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference userDetailsReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        // inflating the tool bar
        Toolbar toolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        // Initializing the views
        loginTxtView = findViewById(R.id.loginTxtView);
        registerBtn = findViewById(R.id.registerBtn);
        emailField = findViewById(R.id.emailField);
        usernameField = findViewById(R.id.usernameField);
        passwordField = findViewById(R.id.passwordField);
        // Initialize an Instance of Firebase Authentication by calling the getInstance() method
        mAuth = FirebaseAuth.getInstance();
        // Initialize an Instance of Firebase Database by calling the getInstance() method
        database = FirebaseDatabase.getInstance();

        userDetailsReference = database.getReference().child("Users");
        loginTxtView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent loginIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(loginIntent);
            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //create a toast
                Toast.makeText(RegisterActivity.this, "LOADING...", Toast.LENGTH_LONG).show();
                //get the username entered
                final String username = usernameField.getText().toString().trim();
                //get the email entered
                final String email = emailField.getText().toString().trim();
                //get the password entered
                final String password = passwordField.getText().toString().trim();
                //Validate to ensure that the user has entered email and username
                if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(username) &&
                        !TextUtils.isEmpty(password)) {

                    mAuth.createUserWithEmailAndPassword(email,
                            password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            String user_id = mAuth.getCurrentUser().getUid();
                            DatabaseReference current_user_db = userDetailsReference.child(user_id);
                            // set the Username and Image on the users' unique path (current_users_db).
                            current_user_db.child("Username").setValue(username);
                            current_user_db.child("Image").setValue("Default");
                            // make a Toast to show the user that they’ve been successfully registered and then
                            Toast.makeText(RegisterActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                            Intent profIntent = new Intent(RegisterActivity.this, ProfileActivity.class);
                            profIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(profIntent);
                        }
                    });
                } else {
                    Toast.makeText(RegisterActivity.this, "Complete all fields", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
