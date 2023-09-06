package com.la.veolia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class PasswordActivity extends AppCompatActivity {
    EditText pass1, pass2;
    LinearLayout la1, la2;
    Button BtnSave;
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        pass1 = findViewById(R.id.edit_text_actual_password);
        pass2 = findViewById(R.id.edit_text_new_password);
        la1 = findViewById(R.id.form_layout_p1);
        la2 = findViewById(R.id.form_layout_p2);
        BtnSave = findViewById(R.id.button_save_pass);
        BtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                savePass();
            }
        });
        verifyPassExist();
    }
    private void savePass(){
        /*
        Se valida que la entrada de la nueva contraseña no esté vacía
         */
        if(pass2.getText().toString().equals("")){
            Toast.makeText(PasswordActivity.this, "No hay una contraseña para guardar", Toast.LENGTH_SHORT).show();
        }else{
            //Buscamos la contraseña guardada en el dispositivo y validamos si es correcta
            SharedPreferences preferencesId = getSharedPreferences("PasswordTeam", Context.MODE_PRIVATE);
            final String passTeam = preferencesId.getString("Pass","Not Result");
            if(passTeam.equals("Not Result")){
                //Si no hay una contraseña gardada, se guardará la que se necesita
                SharedPreferences.Editor editorTema = preferencesId.edit();
                editorTema.putString("Pass", pass2.getText().toString());
                editorTema.commit();
                finish();
            }else{
                if(passTeam.equals(pass1.getText().toString())){
                    //Al validar, si l a contraseña es correcta, se actualizará
                    SharedPreferences.Editor editorTema = preferencesId.edit();
                    editorTema.putString("Pass", pass2.getText().toString());
                    editorTema.commit();
                    finish();
                }else{
                    Toast.makeText(PasswordActivity.this, "Contraseña incorrecta", Toast.LENGTH_SHORT).show();
                }
            }

        }
    }
    private void verifyPassExist(){
        SharedPreferences preferencesId = getSharedPreferences("PasswordTeam", Context.MODE_PRIVATE);
        final String passTeam = preferencesId.getString("Pass","Not Result");
        if(passTeam.equals("Not Result")){
            la1.setVisibility(View.GONE);
        }
    }
}