package com.example.shoesstore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Signin extends AppCompatActivity {

    private TextView tvSignUp;
    private EditText edUserName,edPassword;
    private Button btnSignIn;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        initUI();
        initListener();
    }



    private void initUI() {
        progressDialog=new ProgressDialog(this);
        tvSignUp=findViewById(R.id.tvSignUp);
        edUserName=findViewById(R.id.edEmail);
        edPassword=findViewById(R.id.edPassword);
        btnSignIn=findViewById(R.id.btnSignIn);
    }
    private void initListener() {
        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(Signin.this,Signup.class);
                startActivity(i);

            }
        });
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickSignIn();
            }
        });
    }

    private void onClickSignIn() {
        progressDialog.show();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth = FirebaseAuth.getInstance();
        String strEmail=edUserName.getText().toString().trim();
        if (strEmail.equals("")){
            Toast.makeText(Signin.this,"Please fill the email",Toast.LENGTH_LONG).show();
        }
        String strPassword=edPassword.getText().toString().trim();
        mAuth.signInWithEmailAndPassword(strEmail, strPassword)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            progressDialog.dismiss();
                            // Sign in success, update UI with the signed-in user's information
                            Intent i=new Intent(Signin.this,HomePage.class);
                            startActivity(i);
                            finishAffinity();

                        } else {
                            // If sign in fails, display a message to the user.
                            progressDialog.dismiss();
                            Toast.makeText(Signin.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();


                        }
                    }
                });
    }


}