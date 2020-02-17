package com.hoyahacks.mealalert;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import android.widget.Button;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText mEmailField;
    private EditText mPasswordField;


    private Button mLoginBtn;

    private FirebaseAuth mAuth;

    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEmailField = findViewById(R.id.username);
        mPasswordField = findViewById(R.id.password);

        mLoginBtn = findViewById(R.id.button2);


        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                if(firebaseAuth.getCurrentUser() != null)    {

                    startActivity(new Intent(MainActivity.this, UserInfo.class));
                }

            }
        };


        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                initializeSignIn();
            }
        });


    }


    /*
    This Method is used to check the login status of the App during launch.
     */
    @Override
    protected void onStart()    {
        super.onStart();

        mAuth.addAuthStateListener(mAuthListener);

    }


    /*
    This Method handles the user login---- if the user's details match records on the database, a login is validated and sent over the server and a server token is initialized
     */

    private void initializeSignIn() {

        String email = mEmailField.getText().toString();
        String password = mPasswordField.getText().toString();

        if(email.equals("") || password.equals("")) {

            Toast.makeText(MainActivity.this,"One or More Field(s) Empty!!", Toast.LENGTH_LONG).show();
        }


        else    {
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if(!task.isSuccessful()){
                        Toast.makeText(MainActivity.this, "Failed to Sign-in!!!", Toast.LENGTH_LONG).show();
                    }

                }
            });

        }


    }






}
