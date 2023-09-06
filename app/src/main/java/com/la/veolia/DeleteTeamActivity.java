package com.la.veolia;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class DeleteTeamActivity extends AppCompatActivity {
    EditText pass;
    Button btnConfirm;
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_team);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        pass=findViewById(R.id.edit_text_password);
        btnConfirm=findViewById(R.id.button_verify_pass);
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmPass();
            }
        });
    }
    private void confirmPass(){
        //Buscamos el equipo guardado actualmente
        SharedPreferences preferencesId = getSharedPreferences("PasswordTeam", Context.MODE_PRIVATE);
        final String passTeam = preferencesId.getString("Pass","Not Result");
        // Se solicita la contraseña
        if(pass.getText().toString().equals(passTeam)){
            //Si la contraseña es correcta, se le confirmará al usuario si desea cambiar de de equipo
            AlertDialog.Builder Createe = new AlertDialog.Builder(DeleteTeamActivity.this);
            Createe.setMessage("¿Seguro que deseas cambiar el equipo?")
                    .setCancelable(false)
                    .setPositiveButton("Si, cambiar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            /*
                            Tras la confirmación el equipo de borrará de la memoria
                            para dar paso a la selección de un nuevo equipo.
                            */
                            SharedPreferences preferencesTema = getSharedPreferences("EquipoVeolia", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editorTema = preferencesTema.edit();
                            editorTema.putString("NombreEquipo", "Not Result");
                            editorTema.commit();
                            finish();
                        }
                    }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            AlertDialog Tituloo = Createe.create();
            Tituloo.setTitle("Cambiar equipo!");
            Tituloo.show();
        }else{
            Toast.makeText(DeleteTeamActivity.this, "Contraseña incorrecta", Toast.LENGTH_SHORT).show();
        }
    }
}