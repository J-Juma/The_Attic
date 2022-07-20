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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    private EditText loginEmail, loginPass;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabaseUsers;
    private Button loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // inflating the tool bar
        Toolbar toolbar = findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        // initializing the views
        loginBtn = findViewById(R.id.loginBtn);
        loginEmail =findViewById(R.id.login_email);
        loginPass = findViewById(R.id.login_password);

        // Initializing the Firebase Authentication instance
        mAuth = FirebaseAuth.getInstance();
        // Initializing the database reference where you have the child node Users
        mDatabaseUsers = FirebaseDatabase.getInstance().getReference().child("Users");
        //Set on click listener on the login button
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(LoginActivity.this, "PROCESSING....", Toast.LENGTH_LONG).show();
                // get the email and password entered by the user
                String email = loginEmail.getText().toString().trim();
                String password = loginPass.getText().toString().trim();
                if (!TextUtils.isEmpty(email)&& !TextUtils.isEmpty(password)){
                    mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                checkUserExistence();
                            } else {
                                Toast.makeText(LoginActivity.this, "Couldn't login, User not found", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }else {
                    //if the fields for email and password were not completed show a toast
                    Toast.makeText(LoginActivity.this, "Complete all fields",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    // checking if the user exists
    public void checkUserExistence(){
        // checking the user existence of the user using the user id in users database reference
        final String user_id = mAuth.getCurrentUser().getUid();
        mDatabaseUsers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // getting a dataSnapshot of the users database reference to determine if current user exists
                if (dataSnapshot.hasChild(user_id)){
                    //if the users exists direct the user to the Main Activity
                    Intent mainPage = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(mainPage);
                }else {
                    //if the user id does not exist show a toast
                    Toast.makeText(LoginActivity.this, "User not registered!",
                            Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
