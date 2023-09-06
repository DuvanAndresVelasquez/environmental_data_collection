package com.la.veolia;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.opencsv.CSVWriter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.Collections;

public class TableTwoChainCustodyActivity extends AppCompatActivity {
    DriveServiceHelper driveServiceHelper;
    Button button_save_cadena_tabla2;
    int REQUEST_CODE = 200;
    ActivityResultLauncher<Intent> activityResultLauncher;

    EditText codigo,
    c12f1,c12f2,c12f3,c12f4,c12f5,c12f6,c12f7,c12f8,c12f9,c12f10,c12f11,c12f12,c12f13,c12f14,c12f15,c12f16,c12f17,c12f18,c12f19,c12f20,
    c10f1,c10f2,c10f3,c10f4,c10f5,c10f6,c10f7,c10f8,c10f9,c10f10,c10f11,c10f12,c10f13,c10f14,c10f15,c10f16,c10f17,c10f18,c10f19,c10f20,
    c13f1,c13f2,c13f3,c13f4,c13f5,c13f6,c13f7,c13f8,c13f9,c13f10,c13f11,c13f12,c13f13,c13f14,c13f15,c13f16,c13f17,c13f18,c13f19,c13f20,
    c15f1,c15f2,c15f3,c15f4,c15f5,c15f6,c15f7,c15f8,c15f9,c15f10,c15f11,c15f12,c15f13,c15f14,c15f15,c15f16,c15f17,c15f18,c15f19,c15f20;

    Spinner c11f1,c11f2,c11f3,c11f4,c11f5,c11f6,c11f7,c11f8,c11f9,c11f10,c11f11,c11f12,c11f13,c11f14,c11f15,c11f16,c11f17,c11f18,c11f19,c11f20,
            c14f1,c14f2,c14f3,c14f4,c14f5,c14f6,c14f7,c14f8,c14f9,c14f10,c14f11,c14f12,c14f13,c14f14,c14f15,c14f16,c14f17,c14f18,c14f19,c14f20;
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_two_chain_custody);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        codigo = findViewById(R.id.edit_text_codigo_Cadena_tabla2);

        c10f1 = findViewById(R.id.edit_text_columna10_fila2_tabla2_cadena_12);
        c10f2 = findViewById(R.id.edit_text_columna10_fila3_tabla2_cadena_12);
        c10f3 = findViewById(R.id.edit_text_columna10_fila4_tabla2_cadena_12);
        c10f4 = findViewById(R.id.edit_text_columna10_fila5_tabla2_cadena_12);
        c10f5 = findViewById(R.id.edit_text_columna10_fila6_tabla2_cadena_12);
        c10f6 = findViewById(R.id.edit_text_columna10_fila7_tabla2_cadena_12);
        c10f7 = findViewById(R.id.edit_text_columna10_fila8_tabla2_cadena_12);
        c10f8 = findViewById(R.id.edit_text_columna10_fila9_tabla2_cadena_12);
        c10f9 = findViewById(R.id.edit_text_columna10_fila10_tabla2_cadena_12);
        c10f10 = findViewById(R.id.edit_text_columna10_fila11_tabla2_cadena_12);
        c10f11 = findViewById(R.id.edit_text_columna10_fila12_tabla2_cadena_12);
        c10f12 = findViewById(R.id.edit_text_columna10_fila13_tabla2_cadena_12);
        c10f13 = findViewById(R.id.edit_text_columna10_fila14_tabla2_cadena_12);
        c10f14 = findViewById(R.id.edit_text_columna10_fila15_tabla2_cadena_12);
        c10f15 = findViewById(R.id.edit_text_columna10_fila16_tabla2_cadena_12);
        c10f16 = findViewById(R.id.edit_text_columna10_fila17_tabla2_cadena_12);
        c10f17 = findViewById(R.id.edit_text_columna10_fila18_tabla2_cadena_12);
        c10f18 = findViewById(R.id.edit_text_columna10_fila19_tabla2_cadena_12);
        c10f19 = findViewById(R.id.edit_text_columna10_fila20_tabla2_cadena_12);
        c10f20 = findViewById(R.id.edit_text_columna10_fila21_tabla2_cadena_12);




        c11f1 = findViewById(R.id.Spinner_columna11_fila2_tabla2_Cadena12);
        c11f2 = findViewById(R.id.Spinner_columna11_fila3_tabla2_Cadena12);
        c11f3 = findViewById(R.id.Spinner_columna11_fila4_tabla2_Cadena12);
        c11f4 = findViewById(R.id.Spinner_columna11_fila5_tabla2_Cadena12);
        c11f5 = findViewById(R.id.Spinner_columna11_fila6_tabla2_Cadena12);
        c11f6 = findViewById(R.id.Spinner_columna11_fila7_tabla2_Cadena12);
        c11f7 = findViewById(R.id.Spinner_columna11_fila8_tabla2_Cadena12);
        c11f8 = findViewById(R.id.Spinner_columna11_fila9_tabla2_Cadena12);
        c11f9 = findViewById(R.id.Spinner_columna11_fila10_tabla2_Cadena12);
        c11f10 = findViewById(R.id.Spinner_columna11_fila11_tabla2_Cadena12);
        c11f11 = findViewById(R.id.Spinner_columna11_fila12_tabla2_Cadena12);
        c11f12 = findViewById(R.id.Spinner_columna11_fila13_tabla2_Cadena12);
        c11f13 = findViewById(R.id.Spinner_columna11_fila14_tabla2_Cadena12);
        c11f14 = findViewById(R.id.Spinner_columna11_fila15_tabla2_Cadena12);
        c11f15 = findViewById(R.id.Spinner_columna11_fila16_tabla2_Cadena12);
        c11f16 = findViewById(R.id.Spinner_columna11_fila17_tabla2_Cadena12);
        c11f17 = findViewById(R.id.Spinner_columna11_fila18_tabla2_Cadena12);
        c11f18 = findViewById(R.id.Spinner_columna11_fila19_tabla2_Cadena12);
        c11f19 = findViewById(R.id.Spinner_columna11_fila20_tabla2_Cadena12);
        c11f20 = findViewById(R.id.Spinner_columna11_fila21_tabla2_Cadena12);







        c12f1 = findViewById(R.id.edit_text_columna12_fila2_tabla2_cadena_12);
        c12f2 = findViewById(R.id.edit_text_columna12_fila3_tabla2_cadena_12);
        c12f3 = findViewById(R.id.edit_text_columna12_fila4_tabla2_cadena_12);
        c12f4 = findViewById(R.id.edit_text_columna12_fila5_tabla2_cadena_12);
        c12f5 = findViewById(R.id.edit_text_columna12_fila6_tabla2_cadena_12);
        c12f6 = findViewById(R.id.edit_text_columna12_fila7_tabla2_cadena_12);
        c12f7 = findViewById(R.id.edit_text_columna12_fila8_tabla2_cadena_12);
        c12f8 = findViewById(R.id.edit_text_columna12_fila9_tabla2_cadena_12);
        c12f9 = findViewById(R.id.edit_text_columna12_fila10_tabla2_cadena_12);
        c12f10 = findViewById(R.id.edit_text_columna12_fila11_tabla2_cadena_12);
        c12f11 = findViewById(R.id.edit_text_columna12_fila12_tabla2_cadena_12);
        c12f12 = findViewById(R.id.edit_text_columna12_fila13_tabla2_cadena_12);
        c12f13 = findViewById(R.id.edit_text_columna12_fila14_tabla2_cadena_12);
        c12f14 = findViewById(R.id.edit_text_columna12_fila15_tabla2_cadena_12);
        c12f15 = findViewById(R.id.edit_text_columna12_fila16_tabla2_cadena_12);
        c12f16 = findViewById(R.id.edit_text_columna12_fila17_tabla2_cadena_12);
        c12f17 = findViewById(R.id.edit_text_columna12_fila18_tabla2_cadena_12);
        c12f18 = findViewById(R.id.edit_text_columna12_fila19_tabla2_cadena_12);
        c12f19 = findViewById(R.id.edit_text_columna12_fila20_tabla2_cadena_12);
        c12f20 = findViewById(R.id.edit_text_columna12_fila21_tabla2_cadena_12);




        c13f1 = findViewById(R.id.edit_text_columna13_fila2_tabla2_cadena_12);
        c13f2 = findViewById(R.id.edit_text_columna13_fila3_tabla2_cadena_12);
        c13f3 = findViewById(R.id.edit_text_columna13_fila4_tabla2_cadena_12);
        c13f4 = findViewById(R.id.edit_text_columna13_fila5_tabla2_cadena_12);
        c13f5 = findViewById(R.id.edit_text_columna13_fila6_tabla2_cadena_12);
        c13f6 = findViewById(R.id.edit_text_columna13_fila7_tabla2_cadena_12);
        c13f7 = findViewById(R.id.edit_text_columna13_fila8_tabla2_cadena_12);
        c13f8 = findViewById(R.id.edit_text_columna13_fila9_tabla2_cadena_12);
        c13f9 = findViewById(R.id.edit_text_columna13_fila10_tabla2_cadena_12);
        c13f10 = findViewById(R.id.edit_text_columna13_fila11_tabla2_cadena_12);
        c13f11 = findViewById(R.id.edit_text_columna13_fila12_tabla2_cadena_12);
        c13f12 = findViewById(R.id.edit_text_columna13_fila13_tabla2_cadena_12);
        c13f13 = findViewById(R.id.edit_text_columna13_fila14_tabla2_cadena_12);
        c13f14 = findViewById(R.id.edit_text_columna13_fila15_tabla2_cadena_12);
        c13f15 = findViewById(R.id.edit_text_columna13_fila16_tabla2_cadena_12);
        c13f16 = findViewById(R.id.edit_text_columna13_fila17_tabla2_cadena_12);
        c13f17 = findViewById(R.id.edit_text_columna13_fila18_tabla2_cadena_12);
        c13f18 = findViewById(R.id.edit_text_columna13_fila19_tabla2_cadena_12);
        c13f19 = findViewById(R.id.edit_text_columna13_fila20_tabla2_cadena_12);
        c13f20 = findViewById(R.id.edit_text_columna13_fila21_tabla2_cadena_12);




        c14f1 = findViewById(R.id.Spinner_columna14_fila2_tabla2_Cadena12);
        c14f2 = findViewById(R.id.Spinner_columna14_fila3_tabla2_Cadena12);
        c14f3 = findViewById(R.id.Spinner_columna14_fila4_tabla2_Cadena12);
        c14f4 = findViewById(R.id.Spinner_columna14_fila5_tabla2_Cadena12);
        c14f5 = findViewById(R.id.Spinner_columna14_fila6_tabla2_Cadena12);
        c14f6 = findViewById(R.id.Spinner_columna14_fila7_tabla2_Cadena12);
        c14f7 = findViewById(R.id.Spinner_columna14_fila8_tabla2_Cadena12);
        c14f8 = findViewById(R.id.Spinner_columna14_fila9_tabla2_Cadena12);
        c14f9 = findViewById(R.id.Spinner_columna14_fila10_tabla2_Cadena12);
        c14f10 = findViewById(R.id.Spinner_columna14_fila11_tabla2_Cadena12);
        c14f11 = findViewById(R.id.Spinner_columna14_fila12_tabla2_Cadena12);
        c14f12 = findViewById(R.id.Spinner_columna14_fila13_tabla2_Cadena12);
        c14f13 = findViewById(R.id.Spinner_columna14_fila14_tabla2_Cadena12);
        c14f14 = findViewById(R.id.Spinner_columna14_fila15_tabla2_Cadena12);
        c14f15 = findViewById(R.id.Spinner_columna14_fila16_tabla2_Cadena12);
        c14f16 = findViewById(R.id.Spinner_columna14_fila17_tabla2_Cadena12);
        c14f17 = findViewById(R.id.Spinner_columna14_fila18_tabla2_Cadena12);
        c14f18 = findViewById(R.id.Spinner_columna14_fila19_tabla2_Cadena12);
        c14f19 = findViewById(R.id.Spinner_columna14_fila20_tabla2_Cadena12);
        c14f20 = findViewById(R.id.Spinner_columna14_fila21_tabla2_Cadena12);





        c15f1 = findViewById(R.id.edit_text_columna15_fila2_tabla2_cadena_12);
        c15f2 = findViewById(R.id.edit_text_columna15_fila3_tabla2_cadena_12);
        c15f3 = findViewById(R.id.edit_text_columna15_fila4_tabla2_cadena_12);
        c15f4 = findViewById(R.id.edit_text_columna15_fila5_tabla2_cadena_12);
        c15f5 = findViewById(R.id.edit_text_columna15_fila6_tabla2_cadena_12);
        c15f6 = findViewById(R.id.edit_text_columna15_fila7_tabla2_cadena_12);
        c15f7 = findViewById(R.id.edit_text_columna15_fila8_tabla2_cadena_12);
        c15f8 = findViewById(R.id.edit_text_columna15_fila9_tabla2_cadena_12);
        c15f9 = findViewById(R.id.edit_text_columna15_fila10_tabla2_cadena_12);
        c15f10 = findViewById(R.id.edit_text_columna15_fila11_tabla2_cadena_12);
        c15f11 = findViewById(R.id.edit_text_columna15_fila12_tabla2_cadena_12);
        c15f12 = findViewById(R.id.edit_text_columna15_fila13_tabla2_cadena_12);
        c15f13 = findViewById(R.id.edit_text_columna15_fila14_tabla2_cadena_12);
        c15f14 = findViewById(R.id.edit_text_columna15_fila15_tabla2_cadena_12);
        c15f15 = findViewById(R.id.edit_text_columna15_fila16_tabla2_cadena_12);
        c15f16 = findViewById(R.id.edit_text_columna15_fila17_tabla2_cadena_12);
        c15f17 = findViewById(R.id.edit_text_columna15_fila18_tabla2_cadena_12);
        c15f18 = findViewById(R.id.edit_text_columna15_fila19_tabla2_cadena_12);
        c15f19 = findViewById(R.id.edit_text_columna15_fila20_tabla2_cadena_12);
        c15f20 = findViewById(R.id.edit_text_columna15_fila21_tabla2_cadena_12);


        String codigoo = getIntent().getStringExtra("codigo_chain_custody");
        codigo.setText(codigoo);
        codigo.setEnabled(false);

        button_save_cadena_tabla2 = findViewById(R.id.button_save_cadena_tabla2);
        button_save_cadena_tabla2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder Bien = new AlertDialog.Builder(TableTwoChainCustodyActivity.this);
                Bien.setMessage("¿Seguro que desea guardar estos registros? (Debe entender que el código no se podrá repetir en el archivo.)")
                        .setCancelable(false)
                        .setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
                            @RequiresApi(api = Build.VERSION_CODES.M)
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                verifyCodeInFile();
                            }
                        }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                AlertDialog Titulo = Bien.create();
                Titulo.setTitle("Guardar el archivo");
                Titulo.show();
            }
        });
        singin();
        activityResultLauncher=registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if(result.getResultCode()== Activity.RESULT_OK){
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){
                        if (Environment.isExternalStorageManager()){
                            Toast.makeText(TableTwoChainCustodyActivity.this, "Permiso concedido, puedes continuar", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(TableTwoChainCustodyActivity.this, "Permiso denegado, no puedes continuar.", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                }
            }
        });
        createFile();
    }



    public static boolean contains(String[] arreglo, String codigo){
        return Arrays.asList(arreglo).contains(codigo);
    }


    private void verifyCodeInFile(){
        SharedPreferences preferencesId = getSharedPreferences("EquipoVeolia", Context.MODE_PRIVATE);
        final String nameTeam = preferencesId.getString("NombreEquipo","E1");
        File ruteFolder = new File(getFilesDir()+"/ArchivosGeneradosVeolia");
        File file = new File(ruteFolder, "BCadena2"+nameTeam+".csv");
        boolean isCreate = false;
        if(!ruteFolder.exists()){
            Toast.makeText(TableTwoChainCustodyActivity.this, "No se encuentra el archivo...", Toast.LENGTH_SHORT).show();
        }else{
            String cadena;
            String[] arreglo = {};

            try{
                FileReader fileReader =  new FileReader(file);
                BufferedReader bufferedReader = new BufferedReader(fileReader);

                while((cadena = bufferedReader.readLine()) != null){
                    arreglo = cadena.split(",");
                }
                boolean value = contains(arreglo, codigo.getText().toString());
                if(value){
                    AlertDialog.Builder Bien = new AlertDialog.Builder(TableTwoChainCustodyActivity.this);
                    Bien.setMessage("No se pueden generar más registros con este código, por favor, utiliza otro.")
                            .setCancelable(false)
                            .setPositiveButton("Entendido", new DialogInterface.OnClickListener() {
                                @RequiresApi(api = Build.VERSION_CODES.M)
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            });
                    AlertDialog Titulo = Bien.create();
                    Titulo.setTitle("El código ya existe");
                    Titulo.show();
                }else{
                    saveFile();
                }

            }catch (Exception e){
                Toast.makeText(TableTwoChainCustodyActivity.this, "Error: probablemente la tabla no se han creado aún...", Toast.LENGTH_SHORT).show();
            }

        }
    }



    private void saveFile(){


        File ruteFolder = new File(getFilesDir()+"/ArchivosGeneradosVeolia");
        if (!ruteFolder.exists()) {
            createFile();
        }
        SharedPreferences preferencesId = getSharedPreferences("EquipoVeolia", Context.MODE_PRIVATE);
        final String nameTeam = preferencesId.getString("NombreEquipo","E1");
        File filecrate = new File(ruteFolder, "BCadena2"+nameTeam+".csv");
        String nameFileInDrive = "BCadena2"+nameTeam+".csv";
        try {
            filecrate.createNewFile();
            String[] infoCreate = {codigo.getText().toString(),

                    c10f1.getText().toString(),c10f2.getText().toString(),c10f3.getText().toString(),c10f4.getText().toString(),c10f5.getText().toString(),
                    c10f6.getText().toString(),c10f7.getText().toString(),c10f8.getText().toString(),c10f9.getText().toString(),c10f10.getText().toString(),
                    c10f11.getText().toString(),c10f12.getText().toString(),c10f13.getText().toString(),c10f14.getText().toString(),c10f15.getText().toString(),
                    c10f16.getText().toString(),c10f17.getText().toString(),c10f18.getText().toString(),c10f19.getText().toString(),c10f20.getText().toString(),

                    c11f1.getSelectedItem().toString(),c11f2.getSelectedItem().toString(),c11f3.getSelectedItem().toString(),c11f4.getSelectedItem().toString(),c11f5.getSelectedItem().toString(),
                    c11f6.getSelectedItem().toString(),c11f7.getSelectedItem().toString(),c11f8.getSelectedItem().toString(),c11f9.getSelectedItem().toString(),c11f10.getSelectedItem().toString(),
                    c11f11.getSelectedItem().toString(),c11f12.getSelectedItem().toString(),c11f13.getSelectedItem().toString(),c11f14.getSelectedItem().toString(),c11f15.getSelectedItem().toString(),
                    c11f16.getSelectedItem().toString(),c11f17.getSelectedItem().toString(),c11f18.getSelectedItem().toString(),c11f19.getSelectedItem().toString(),c11f20.getSelectedItem().toString(),

                    c12f1.getText().toString(),c12f2.getText().toString(),c12f3.getText().toString(),c12f4.getText().toString(),c12f5.getText().toString(),
                    c12f6.getText().toString(),c12f7.getText().toString(),c12f8.getText().toString(),c12f9.getText().toString(),c12f10.getText().toString(),
                    c12f11.getText().toString(),c12f12.getText().toString(),c12f13.getText().toString(),c12f14.getText().toString(),c12f15.getText().toString(),
                    c12f16.getText().toString(),c12f17.getText().toString(),c12f18.getText().toString(),c12f19.getText().toString(),c12f20.getText().toString(),

                    c13f1.getText().toString(),c13f2.getText().toString(),c13f3.getText().toString(),c13f4.getText().toString(),c13f5.getText().toString(),
                    c13f6.getText().toString(),c13f7.getText().toString(),c13f8.getText().toString(),c13f9.getText().toString(),c13f10.getText().toString(),
                    c13f11.getText().toString(),c13f12.getText().toString(),c13f13.getText().toString(),c13f14.getText().toString(),c13f15.getText().toString(),
                    c13f16.getText().toString(),c13f17.getText().toString(),c13f18.getText().toString(),c13f19.getText().toString(),c13f20.getText().toString(),

                    c14f1.getSelectedItem().toString(),c14f2.getSelectedItem().toString(),c14f3.getSelectedItem().toString(),c14f4.getSelectedItem().toString(),c14f5.getSelectedItem().toString(),
                    c14f6.getSelectedItem().toString(),c14f7.getSelectedItem().toString(),c14f8.getSelectedItem().toString(),c14f9.getSelectedItem().toString(),c14f10.getSelectedItem().toString(),
                    c14f11.getSelectedItem().toString(),c14f12.getSelectedItem().toString(),c14f13.getSelectedItem().toString(),c14f14.getSelectedItem().toString(),c14f15.getSelectedItem().toString(),
                    c14f16.getSelectedItem().toString(),c14f17.getSelectedItem().toString(),c14f18.getSelectedItem().toString(),c14f19.getSelectedItem().toString(),c14f20.getSelectedItem().toString(),


                    c15f1.getText().toString(),c15f2.getText().toString(),c15f3.getText().toString(),c15f4.getText().toString(),c15f5.getText().toString(),
                    c15f6.getText().toString(),c15f7.getText().toString(),c15f8.getText().toString(),c15f9.getText().toString(),c15f10.getText().toString(),
                    c15f11.getText().toString(),c15f12.getText().toString(),c15f13.getText().toString(),c15f14.getText().toString(),c15f15.getText().toString(),
                    c15f16.getText().toString(),c15f17.getText().toString(),c15f18.getText().toString(),c15f19.getText().toString(),c15f20.getText().toString(),};





            CSVWriter fileWriter = new CSVWriter(new OutputStreamWriter(new FileOutputStream(filecrate, true),
                    "ISO-8859-1"), ',',
                    CSVWriter.NO_QUOTE_CHARACTER,
                    CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                    CSVWriter.DEFAULT_LINE_END);

            fileWriter.writeNext(infoCreate);
            fileWriter.close();

            ProgressDialog progressDialog = new ProgressDialog(TableTwoChainCustodyActivity.this);
            progressDialog.setTitle("Archivo creado, sincronizando con Google Drive.");
            progressDialog.setMessage("Por favor, espera.");
            progressDialog.show();


            try {
                driveServiceHelper.createFileDrive(nameFileInDrive, filecrate.getPath()).addOnSuccessListener(new OnSuccessListener<String>() {
                    @Override
                    public void onSuccess(String s) {
                        progressDialog.dismiss();
                        Toast.makeText(TableTwoChainCustodyActivity.this, "El archivo ya está en Google Drive con tu cuenta de Google", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(TableTwoChainCustodyActivity.this, "No se pudo subir el archivo a Google Drive", Toast.LENGTH_SHORT).show();
                    }
                });
            }catch (Exception e){
                progressDialog.dismiss();
                Toast.makeText(TableTwoChainCustodyActivity.this, "Ocurrio un problema al subir el archivo a drive", Toast.LENGTH_SHORT).show();
            }





            Toast.makeText(TableTwoChainCustodyActivity.this, "Datos agregados al archivo: " + filecrate.getPath(), Toast.LENGTH_LONG).show();
        }catch (IOException ie){

            Toast.makeText(TableTwoChainCustodyActivity.this, "Ocurrio un problema al crear el archivo."+ ie, Toast.LENGTH_LONG).show();
        }


    }


    private void createFile(){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.R) {
            String app_name = getString(R.string.app_name);
            try {
                File ruteFolder = new File(getFilesDir()+"/ArchivosGeneradosVeolia");
                if (!ruteFolder.exists()) {
                    ruteFolder.mkdirs();
                }
                SharedPreferences preferencesId = getSharedPreferences("EquipoVeolia", Context.MODE_PRIVATE);
                final String nameTeam = preferencesId.getString("NombreEquipo","E1");
                    File filecrate = new File(ruteFolder, "BCadena2"+nameTeam+".csv");
                if(!filecrate.exists()){
                    filecrate.createNewFile();
                    String[] titles = {"Código",



                            //Columna1
                            "C1 f1","C1 f2",
                            "C1 f3",
                            "C1 f4",
                            "C1 f5",
                            "C1 f6",
                            "C1 f7",   "C1 f8",   "C1 f9",
                            "C1 f10","C1 f11","C1 f12","C1 f13","C1 f14", "C1 f15", "C1 f16", "C1 f17",
                            "C1 f18", "C1 f19", "C1 f20", "C1 f21",
                            //Columna2
                            "C2 f1", "C2 f2", "C2 f3", "C2 f4", "C2 f5", "C2 f6", "C2 f7", "C2 f8",
                            "C2 f9", "C2 f10", "C2 f11", "C2 f12", "C2 f13", "C2 f14", "C2 f15",
                            "C2 f16", "C2 f17", "C2 f18", "C2 f19", "C2 f20", "C2 f21",
                            //Columna3
                            "C3 f1", "C3 f2", "C3 f3", "C3 f4", "C3 f5", "C3 f6", "C3 f7", "C3 f8",
                            "C3 f9", "C3 f10", "C3 f11", "C3 f12", "C3 f13", "C3 f14", "C3 f15",
                            "C3 f16", "C3 f17", "C3 f18", "C3 f19", "C3 f20", "C3 f21",
                            //Columna4
                            "C4 f1", "C4 f2", "C4 f3", "C4 f4", "C4 f5", "C4 f6", "C4 f7", "C4 f8",
                            "C4 f9", "C4 f10", "C4f11", "C4 f12", "C4 f13", "C4 f14", "C4 f15",
                            "C4 f16", "C4 f17", "C4 f18", "C4 f19", "C4 f20", "C4 f21",
                            //Columna5
                            "C5 f1", "C5 f2", "C5 f3", "C5 f4", "C5 f5", "C5 f6", "C5 f7", "C5 f8",
                            "C5 f9", "C5 f10", "C5 f11", "C5 f12", "C5 f13", "C5 f14", "C5 f15",
                            "C5 f16", "C5 f17", "C5 f18", "C5 f19", "C5 f20", "C5 f21",
                            //Columna6
                            "C6 f1", "C6 f2", "C6 f3", "C6 f4", "C6 f5", "C6 f6", "C6 f7", "C6 f8",
                            "C6 f9", "C6 f10", "C6 f11", "C6 f12", "C6 f13", "C6 f14", "C6 f15",
                            "C6 f16", "C6 f17", "C6 f18", "C6 f19", "C6 f20", "C6 f21",
                            //Columna7
                            "C7 f1", "C7 f2", "C7 f3", "C7 f4", "C7 f5", "C7 f6", "C7 f7", "C7 f8",
                            "C7 f9","C7 f10", "C7 f11", "C7 f12", "C7 f13", "C7 f14", "C7 f15",
                            "C7 f16", "C7 f17", "C7 f18", "C7 f19", "C7 f20", "C7 f21",
                            //Columna8
                            "C8 f1", "C8 f2", "C8 f3", "C8 f4", "C8 f5", "C8 f6", "C8 f7", "C8 f8",
                            "C8 f9", "C8 f10", "C8 f11", "C8 f12", "C8 f13", "C8 f14", "C8 f15",
                            "C8 f16", "C8 f17", "C8 f18", "C8 f19", "C8 f20", "C8 f21",
                            //Columna9
                            "C9 f1", "C9 f2", "C9 f3", "C9 f4", "C9 f5", "C9 f6", "C9 f7", "C9 f8",
                            "C9 f9", "C9 f10", "C9 f11", "C9 f12", "C9 f13", "C9 f14", "C9 f15",
                            "C9 f16", "C9 f17", "C9 f18", "C9 f19", "C9 f20", "C9 f21",
                            //Columna10
                            "C10 f1", "C10 f2", "C10 f3", "C10 f4", "C10 f5", "C10 f6", "C10 f7",
                            "C10 f8", "C10 f9", "C10 f10", "C10 f11", "C10 f12", "C10 f13", "C10 f14",
                            "C10 f15", "C10 f16", "C10 f17", "C10 f18", "C10 f19", "C10 f20",
                            "C10 f21",

                            //Columna11
                            "C11 f1","C11 f2", "C11 f3", "C11 f4", "C11 f5", "C11 f6", "C11 f7", "C11 f8",
                            "C11 f9", "C11 f10", "C11 f11", "C11 f12", "C11 f13", "C11 f14",
                            "C11 f15", "C11 f16", "C11 f17", "C11 f18", "C11 f19", "C11 f20",
                            "C11 f21",

                            //Columna12
                            "C12 f2", "C12 f3", "C12 f4", "C12 f5", "C12 f6", "C12 f7", "C12 f8",
                            "C12 f9", "C12 f10", "C12 f11", "C12 f12", "C12 f13", "C12 f14",
                            "C12 f15", "C12 f16", "C12 f17", "C12 f18", "C12 f19", "C12 f20",
                            "C12 f21",
                            //Columna13
                            "C13 f2", "C13 f3", "C13 f4", "C13 f5", "C13 f6", "C13 f7", "C13 f8",
                            "C13 f9", "C13 f10", "C13 f11", "C13 f12", "C13 f13", "C13 f14",
                            "C13 f15", "C13 f16", "C13 f17", "C13 f18", "C13 f19", "C13 f20",
                            "C13 f21",

                            //Columna14
                            "C14 f1","C14 f2", "C14 f3", "C14 f4", "C14 f5", "C14 f6", "C14 f7", "C14 f8",
                            "C14 f9", "C14 f10", "C14 f11", "C14 f12", "C14 f13", "C14 f14",
                            "C14 f15", "C14 f16", "C14 f17", "C14 f18", "C14 f19", "C14 f20",
                            "C14 f21",

                            //Columna15
                            "C15 f2", "C15 f3", "C15 f4", "C15 f5", "C15 f6", "C15 f7", "C15 f8",
                            "C15 f9", "C15 f10", "C15 f11", "C15 f12", "C15 f13", "C15 f14",
                            "C15 f15", "C15 f16", "C15 f17", "C15 f18", "C15 f19", "C15 f20",
                            "C15 f21",};
                    CSVWriter fileWriter = new CSVWriter(new OutputStreamWriter(new FileOutputStream(filecrate, true),
                            "ISO-8859-1"), ',',
                            CSVWriter.NO_QUOTE_CHARACTER,
                            CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                            CSVWriter.DEFAULT_LINE_END);

                    fileWriter.writeNext(titles);
                    fileWriter.close();
                }
            }catch (IOException ie){
                try {
                    Intent intent = new Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                    intent.addCategory("android.intent.category.DEFAULT");
                    intent.setData(Uri.parse(String.format("package:%s", new Object[]{getApplicationContext().getPackageName()})));
                    activityResultLauncher.launch(intent);
                    Toast.makeText(TableTwoChainCustodyActivity.this, "Por favor, acepta los permisos para la aplicación "+ app_name, Toast.LENGTH_LONG).show();
                    finish();
                }catch (Exception e){
                    Intent intent = new Intent();
                    intent.setAction(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                    activityResultLauncher.launch(intent);
                    Toast.makeText(TableTwoChainCustodyActivity.this, "Por favor, acepta los permisos para la aplicación "+ app_name, Toast.LENGTH_LONG).show();
                    finish();
                }
            }


        }else{
            int permissionResult = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if(permissionResult == PackageManager.PERMISSION_DENIED){
                AlertDialog.Builder Bien = new AlertDialog.Builder(TableTwoChainCustodyActivity.this);
                Bien.setMessage("Aún no concedes pérmisos. Es obligatorio, para crear el archivo de manera exitosa.")
                        .setCancelable(false)
                        .setPositiveButton("Continúar con el pérmiso.", new DialogInterface.OnClickListener() {
                            @RequiresApi(api = Build.VERSION_CODES.M)
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE);
                            }
                        });
                AlertDialog Titulo = Bien.create();
                Titulo.setTitle("Aún no concedes permisos!");
                Titulo.show();
            }else{
                File ruteFolder = new File(getFilesDir()+"/ArchivosGeneradosVeolia");
                if (!ruteFolder.exists()) {
                    ruteFolder.mkdirs();
                }
                SharedPreferences preferencesId = getSharedPreferences("EquipoVeolia", Context.MODE_PRIVATE);
                final String nameTeam = preferencesId.getString("NombreEquipo","E1");
                File filecrate = new File(ruteFolder, "BCadena2"+nameTeam+".csv");
                if(!filecrate.exists()){
                    try {
                        filecrate.createNewFile();
                        String[] titles = {"Código",



                                //Columna1
                                "C1 f1","C1 f2",
                                "C1 f3",
                                "C1 f4",
                                "C1 f5",
                                "C1 f6",
                                "C1 f7",   "C1 f8",   "C1 f9",
                                "C1 f10","C1 f11","C1 f12","C1 f13","C1 f14", "C1 f15", "C1 f16", "C1 f17",
                                "C1 f18", "C1 f19", "C1 f20", "C1 f21",
                                //Columna2
                                "C2 f1", "C2 f2", "C2 f3", "C2 f4", "C2 f5", "C2 f6", "C2 f7", "C2 f8",
                                "C2 f9", "C2 f10", "C2 f11", "C2 f12", "C2 f13", "C2 f14", "C2 f15",
                                "C2 f16", "C2 f17", "C2 f18", "C2 f19", "C2 f20", "C2 f21",
                                //Columna3
                                "C3 f1", "C3 f2", "C3 f3", "C3 f4", "C3 f5", "C3 f6", "C3 f7", "C3 f8",
                                "C3 f9", "C3 f10", "C3 f11", "C3 f12", "C3 f13", "C3 f14", "C3 f15",
                                "C3 f16", "C3 f17", "C3 f18", "C3 f19", "C3 f20", "C3 f21",
                                //Columna4
                                "C4 f1", "C4 f2", "C4 f3", "C4 f4", "C4 f5", "C4 f6", "C4 f7", "C4 f8",
                                "C4 f9", "C4 f10", "C4f11", "C4 f12", "C4 f13", "C4 f14", "C4 f15",
                                "C4 f16", "C4 f17", "C4 f18", "C4 f19", "C4 f20", "C4 f21",
                                //Columna5
                                "C5 f1", "C5 f2", "C5 f3", "C5 f4", "C5 f5", "C5 f6", "C5 f7", "C5 f8",
                                "C5 f9", "C5 f10", "C5 f11", "C5 f12", "C5 f13", "C5 f14", "C5 f15",
                                "C5 f16", "C5 f17", "C5 f18", "C5 f19", "C5 f20", "C5 f21",
                                //Columna6
                                "C6 f1", "C6 f2", "C6 f3", "C6 f4", "C6 f5", "C6 f6", "C6 f7", "C6 f8",
                                "C6 f9", "C6 f10", "C6 f11", "C6 f12", "C6 f13", "C6 f14", "C6 f15",
                                "C6 f16", "C6 f17", "C6 f18", "C6 f19", "C6 f20", "C6 f21",
                                //Columna7
                                "C7 f1", "C7 f2", "C7 f3", "C7 f4", "C7 f5", "C7 f6", "C7 f7", "C7 f8",
                                "C7 f9","C7 f10", "C7 f11", "C7 f12", "C7 f13", "C7 f14", "C7 f15",
                                "C7 f16", "C7 f17", "C7 f18", "C7 f19", "C7 f20", "C7 f21",
                                //Columna8
                                "C8 f1", "C8 f2", "C8 f3", "C8 f4", "C8 f5", "C8 f6", "C8 f7", "C8 f8",
                                "C8 f9", "C8 f10", "C8 f11", "C8 f12", "C8 f13", "C8 f14", "C8 f15",
                                "C8 f16", "C8 f17", "C8 f18", "C8 f19", "C8 f20", "C8 f21",
                                //Columna9
                                "C9 f1", "C9 f2", "C9 f3", "C9 f4", "C9 f5", "C9 f6", "C9 f7", "C9 f8",
                                "C9 f9", "C9 f10", "C9 f11", "C9 f12", "C9 f13", "C9 f14", "C9 f15",
                                "C9 f16", "C9 f17", "C9 f18", "C9 f19", "C9 f20", "C9 f21",
                                //Columna10
                                "C10 f1", "C10 f2", "C10 f3", "C10 f4", "C10 f5", "C10 f6", "C10 f7",
                                "C10 f8", "C10 f9", "C10 f10", "C10 f11", "C10 f12", "C10 f13", "C10 f14",
                                "C10 f15", "C10 f16", "C10 f17", "C10 f18", "C10 f19", "C10 f20",
                                "C10 f21",

                                //Columna11
                                "C11 f1","C11 f2", "C11 f3", "C11 f4", "C11 f5", "C11 f6", "C11 f7", "C11 f8",
                                "C11 f9", "C11 f10", "C11 f11", "C11 f12", "C11 f13", "C11 f14",
                                "C11 f15", "C11 f16", "C11 f17", "C11 f18", "C11 f19", "C11 f20",
                                "C11 f21",

                                //Columna12
                                "C12 f2", "C12 f3", "C12 f4", "C12 f5", "C12 f6", "C12 f7", "C12 f8",
                                "C12 f9", "C12 f10", "C12 f11", "C12 f12", "C12 f13", "C12 f14",
                                "C12 f15", "C12 f16", "C12 f17", "C12 f18", "C12 f19", "C12 f20",
                                "C12 f21",
                                //Columna13
                                "C13 f2", "C13 f3", "C13 f4", "C13 f5", "C13 f6", "C13 f7", "C13 f8",
                                "C13 f9", "C13 f10", "C13 f11", "C13 f12", "C13 f13", "C13 f14",
                                "C13 f15", "C13 f16", "C13 f17", "C13 f18", "C13 f19", "C13 f20",
                                "C13 f21",

                                //Columna14
                                "C14 f1","C14 f2", "C14 f3", "C14 f4", "C14 f5", "C14 f6", "C14 f7", "C14 f8",
                                "C14 f9", "C14 f10", "C14 f11", "C14 f12", "C14 f13", "C14 f14",
                                "C14 f15", "C14 f16", "C14 f17", "C14 f18", "C14 f19", "C14 f20",
                                "C14 f21",

                                //Columna15
                                "C15 f2", "C15 f3", "C15 f4", "C15 f5", "C15 f6", "C15 f7", "C15 f8",
                                "C15 f9", "C15 f10", "C15 f11", "C15 f12", "C15 f13", "C15 f14",
                                "C15 f15", "C15 f16", "C15 f17", "C15 f18", "C15 f19", "C15 f20",
                                "C15 f21",};
                        CSVWriter fileWriter = new CSVWriter(new OutputStreamWriter(new FileOutputStream(filecrate, true),
                                "ISO-8859-1"), ',',
                                CSVWriter.NO_QUOTE_CHARACTER,
                                CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                                CSVWriter.DEFAULT_LINE_END);

                        fileWriter.writeNext(titles);
                        fileWriter.close();
                        //Toast.makeText(ChainCustodyActivity.this, "El archivo se agregó exitosamente en la ruta" + filecrate.getPath(), Toast.LENGTH_LONG).show();
                    }catch (IOException ie){
                        //Toast.makeText(ChainCustodyActivity.this, "Ocurrio un problema al crear el archivo."+ ie, Toast.LENGTH_LONG).show();
                    }
                }

            }
        }


    }



    private void singin(){
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .requestScopes(new Scope(DriveScopes.DRIVE_FILE))
                .build();

        GoogleSignInClient client = GoogleSignIn.getClient(this, googleSignInOptions);
        startActivityForResult(client.getSignInIntent(), 400);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 400:
                handleSingIntent(data);
                break;
        }
    }

    private void handleSingIntent(Intent data) {
        GoogleSignIn.getSignedInAccountFromIntent(data)
                .addOnSuccessListener(new OnSuccessListener<GoogleSignInAccount>() {
                    @Override
                    public void onSuccess(GoogleSignInAccount googleSignInAccount) {
                        GoogleAccountCredential credential = GoogleAccountCredential.usingOAuth2(TableTwoChainCustodyActivity.this, Collections.singleton(DriveScopes.DRIVE_FILE));
                        credential.setSelectedAccount(googleSignInAccount.getAccount());

                        Drive googleDriveService = new Drive.Builder(
                                AndroidHttp.newCompatibleTransport(),
                                new GsonFactory(),
                                credential)
                                .setApplicationName("Drive upload Veolia documents.")
                                .build();
                        driveServiceHelper = new DriveServiceHelper(googleDriveService);

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
    }

}