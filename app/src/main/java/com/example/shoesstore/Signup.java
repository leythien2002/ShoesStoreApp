package com.example.shoesstore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

public class Signup extends AppCompatActivity {
    private EditText edEmail,edUserName,edPassword;
    private Button btnSignUp;
    private TextView tvSignIn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        initUI();
        initListener();
    }
    private void initUI() {
        tvSignIn=findViewById(R.id.tvSignUp);
        edEmail=findViewById(R.id.edEmail);
        edUserName=findViewById(R.id.edUserName);
        edPassword=findViewById(R.id.edPassword);
        btnSignUp=findViewById(R.id.btnSignUp);
    }
    private void initListener() {
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickSignUp();
            }
        });
//        tvSignIn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i=new Intent(Signup.this,Signup.class);
//                startActivity(i);
//            }
//        });

    }

    private void onClickSignUp() {
        String strEmail=edEmail.getText().toString().trim();
        if (strEmail.equals("")){
            Toast.makeText(Signup.this,"Please fill the email",Toast.LENGTH_LONG).show();
        }
        else{
            String strPassword=edPassword.getText().toString().trim();
            FirebaseAuth mAuth = FirebaseAuth.getInstance();
            mAuth.createUserWithEmailAndPassword(strEmail, strPassword)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Intent i=new Intent(Signup.this,MenuSelection.class);
                                startActivity(i);
                                finishAffinity();//dong tat ca activity bao gom(signup va man hinh signin o phia truoc)
                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(Signup.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
        }

    }
}