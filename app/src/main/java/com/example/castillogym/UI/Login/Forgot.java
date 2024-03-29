package com.example.castillogym.UI.Login;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.castillogym.R;
import com.google.firebase.auth.FirebaseAuth;

public class Forgot extends AppCompatActivity {

    EditText txt_email_login;
    FirebaseAuth mUser;
    Button btnForgot;
    private ProgressDialog loadingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);

        btnForgot = findViewById(R.id.btnForgot);
        txt_email_login = findViewById(R.id.txt_email_login);
        loadingBar = new ProgressDialog(this);

        mUser = FirebaseAuth.getInstance();

        btnForgot.setOnClickListener(v -> {
            changePassword();
        });
    }

    private void changePassword() {
        String email = txt_email_login.getText().toString().trim();

        if(email.isEmpty()){
            txt_email_login.setError(getString(R.string.requerido));
            txt_email_login.requestFocus();
        } else {
            loadingBar.setTitle(R.string.cargar);
            loadingBar.setMessage(getString(R.string.sendingEmailRP));
            loadingBar.setCanceledOnTouchOutside(false);
            loadingBar.show();

            mUser.sendPasswordResetEmail(email).addOnCompleteListener(task -> {
                if(task.isSuccessful()){
                    mUser.signOut();
                    loadingBar.dismiss();

                    Toast.makeText(Forgot.this, R.string.resetPassword, Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Forgot.this, Login.class));
                } else {
                    loadingBar.dismiss();
                    Toast.makeText(Forgot.this, getString(R.string.errorResetPassword), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
