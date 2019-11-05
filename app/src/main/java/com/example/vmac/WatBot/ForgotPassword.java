package com.example.vmac.WatBot;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity {

    private Button mForgotPassword;
    private TextView mLogin;
    private TextView mSignUp;
    private EditText email;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        mLogin = findViewById(R.id.login);
        mSignUp = findViewById(R.id.sign_up);
        email = findViewById(R.id.email_field);
        mForgotPassword = findViewById(R.id.reset_password);
        mAuth = FirebaseAuth.getInstance();

        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        mSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ForgotPassword.this,SignUp.class));
                finish();
            }
        });

        mForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mEmail = email.getText().toString().trim();
                if (TextUtils.isEmpty(mEmail)) {
                    Toast.makeText(getApplicationContext(), "Please enter email", Toast.LENGTH_SHORT).show();
                    email.requestFocus();
                    return;
                }
                else if (!Patterns.EMAIL_ADDRESS.matcher(mEmail).matches()) {
                    Toast.makeText(getApplicationContext(), "This is not an Email", Toast.LENGTH_SHORT).show();
                    email.requestFocus();
                    return;
                }
                else {
                    mAuth.sendPasswordResetEmail(mEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(ForgotPassword.this, "Reset email has been successfully sent to this email ID", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(ForgotPassword.this, LoginActivity.class));
                                finish();
                            }
                            else {
                                String error = task.getException().getMessage();
                                Toast.makeText(ForgotPassword.this, "Error Occurred: " + error, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }
}