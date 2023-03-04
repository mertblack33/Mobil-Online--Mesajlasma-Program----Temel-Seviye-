package com.mert.mymessange;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mert.mymessange.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    String eposta,sifre,isim;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        sharedPreferences = this.getSharedPreferences("com.mert.mymessange", Context.MODE_PRIVATE);
        if (firebaseUser != null){
            Intent intent = new Intent(MainActivity.this,Message.class);
            startActivity(intent);
            finish();
        }
    }


    public void kayit(View view){
        eposta = binding.editTextTextEmailAddress.getText().toString();
        sifre = binding.editTextTextPassword.getText().toString();
        isim = binding.editTextTextPersonName.getText().toString();
        System.out.println(eposta+sifre+isim);
        if (eposta.equals("") || sifre.equals("") || isim.equals("")){
            Toast.makeText(MainActivity.this, "İsim Mail E POSTAYI KONTROL ET", Toast.LENGTH_SHORT).show();

       }else {
            firebaseAuth.createUserWithEmailAndPassword(eposta,sifre).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    sharedPreferences.edit().putString("isim",isim).apply();
                    Intent intent = new Intent(MainActivity.this,Message.class);
                    startActivity(intent);
                    finish();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(MainActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void giris(View view){
        eposta = binding.editTextTextEmailAddress.getText().toString();
        sifre = binding.editTextTextPassword.getText().toString();
        isim = binding.editTextTextPersonName.getText().toString();
        if (eposta.equals("") || sifre.equals("") || isim.equals("")){
            Toast.makeText(MainActivity.this, "İsim Mail E POSTAYI KONTROL ET", Toast.LENGTH_SHORT).show();

        }else {
            firebaseAuth.signInWithEmailAndPassword(eposta,sifre).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    sharedPreferences.edit().putString("isim",isim).apply();
                    Intent intent = new Intent(MainActivity.this,Message.class);
                    startActivity(intent);
                    finish();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(MainActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}