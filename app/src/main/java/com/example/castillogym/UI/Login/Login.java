package com.example.castillogym.UI.Login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.castillogym.MenuInicial;
import com.example.castillogym.Model.Users;
import com.example.castillogym.Prevalent.Prevalent;
import com.example.castillogym.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;


public class Login extends AppCompatActivity {

    //Declaración de variables
    private EditText InputEmail, InputPassword;
    private Button LoginButton;
    private ProgressDialog loadingBar;

    FirebaseAuth mAuth;
    private FirebaseAuth mUser;
    private DatabaseReference mDatabase;
    private final String parentDbName = "Admin";
    SharedPreferences sharedPreferences;

    private TextView AdminLink, UserLink, TipoUsuarioUser, TipoUsuarioAdmin, IrRegistrar, IrRecuperar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Variables Firebase
        mAuth = FirebaseAuth.getInstance();
        mUser = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        //Variables de los elementos de la pantalla
        LoginButton = findViewById(R.id.btnLogin);
        InputPassword = findViewById(R.id.txt_login_pass);
        InputEmail = findViewById(R.id.txt_email_login);
        loadingBar = new ProgressDialog(this);

        IrRegistrar = findViewById(R.id.go_registrar);
        IrRecuperar = findViewById(R.id.forgot);

        //Al presionar sobre el botón se inicia el método LoginUser()
        LoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginUser();
            }
        });


        //Al presionar el textView de registrarse se inicia la pantalla de registrar cuenta
       IrRegistrar.setOnClickListener(v -> {
           Intent i = new Intent(this, Registrar.class);
           startActivity(i);
       });

       //Al presionar el textView de olvidaste contraseña se inicia la pantalla de olvidar contraseña
       IrRecuperar.setOnClickListener(v -> {
           Intent i = new Intent(this, Forgot.class);
           startActivity(i);
       });
    }

    //Metodo iniciar Sesion
    private void LoginUser() {
        String msj2 = getResources().getString(R.string.msj_espera2);
        // Barra de progreso
        loadingBar.setTitle(R.string.cargar);
        loadingBar.setMessage(msj2);
        loadingBar.setCanceledOnTouchOutside(false);
        loadingBar.show();

        //Obtener los datos que escribio el usuario
        String email = InputEmail.getText().toString().trim();
        String password = InputPassword.getText().toString();

        //Condiciones para ver si no se dejan campos vacios
        if (TextUtils.isEmpty(email)) {
            loadingBar.dismiss();

            InputEmail.setError(getString(R.string.requerido));
            InputEmail.requestFocus();
        } else if (TextUtils.isEmpty(password)) {
            loadingBar.dismiss();

            InputPassword.setError(getString(R.string.requerido));
            InputPassword.requestFocus();
        } else {
            conexionFirebase(email, password);
        }
    }

    private void conexionFirebase(String email, String password){
        //método para iniciar sesión en firebase authentication
        mUser.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<AuthResult> task) {
                if (task.isSuccessful()) {

                    // Sign in success, update UI with the signed-in user's information
                    Log.d("msj", "signInWithEmail:success");
                    FirebaseUser user = mUser.getCurrentUser();

                    mDatabase.child(parentDbName).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for (final DataSnapshot snapshot1 : snapshot.getChildren()) {
                                mDatabase.child(parentDbName).child(snapshot1.getKey()).addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        Users usuario = snapshot1.getValue(Users.class);
                                        //String correo = usuario.getEmail();
                                        //String password1 = usuario.getPassword();

                                        sharedPreferences = getSharedPreferences("SHARED_PREF", MODE_PRIVATE);

                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.putString("tipoUs", parentDbName);
                                        editor.apply();

                                        if (user.isEmailVerified()) {

                                            if (parentDbName.equals("Admin")) {
                                                    loadingBar.dismiss();
                                                    Toast.makeText(Login.this, getString(R.string.welcome), Toast.LENGTH_SHORT).show();
                                                    Prevalent.currentOnlineUser = usuario;
                                                    Intent intent = new Intent(Login.this, MenuInicial.class);
                                                    startActivity(intent);
                                                    finish();
                                                }
                                            } else {
                                                loadingBar.dismiss();
                                                //Toast.makeText(LoginActivity.this, R.string.msj_error_login, Toast.LENGTH_SHORT).show();
                                            }

                                            /*
                                                else {
                                                    Toast.makeText(Login.this, R.string.msj_verificar, Toast.LENGTH_SHORT).show();
                                                    FirebaseAuth.getInstance().signOut();
                                                    Intent intent = getIntent();
                                                    finish();
                                                    startActivity(intent);
                                                } */
                                        }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {
                                    }
                                });
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                        }
                    });

                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("msj", "signInWithEmail:failure", task.getException());
                    Toast.makeText(Login.this, getString(R.string.authFailed), Toast.LENGTH_SHORT).show();

                    loadingBar.dismiss();
                }
            }
        });

    }

}







