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

public class TableTwoMaterialCheckActivity extends AppCompatActivity {
    Button button_save_material_tabla2;
    DriveServiceHelper driveServiceHelper;
    int REQUEST_CODE = 200;

    ActivityResultLauncher<Intent> activityResultLauncher;
    EditText codigo,
    c1f1,c1f2,c1f3,c1f4,c1f5,c1f6,c1f7,c1f8,c1f9,c1f10,c1f11,c1f12,c1f13,c1f14,c1f15,
            c2f1,c2f2,c2f3,c2f4,c2f5,c2f6,c2f7,c2f8,c2f9,c2f10,c2f11,c2f12,c2f13,c2f14,c2f15,
            c3f1,c3f2,c3f3,c3f4,c3f5,c3f6,c3f7,c3f8,c3f9,c3f10,c3f11,c3f12,c3f13,c3f14,c3f15,
            c4f1,c4f2,c4f3,c4f4,c4f5,c4f6,c4f7,c4f8,c4f9,c4f10,c4f11,c4f12,c4f13,c4f14,c4f15,
            c5f1,c5f2,c5f3,c5f4,c5f5,c5f6,c5f7,c5f8,c5f9,c5f10,c5f11,c5f12,c5f13,c5f14,c5f15;
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_two_material_check);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        codigo = findViewById(R.id.edit_text_codigo_chequeo_tabla2);
        c1f1 = findViewById(R.id.edit_text_columna1_fila1_tabla2);
        c1f2 = findViewById(R.id.edit_text_columna1_fila2_tabla2);
        c1f3 = findViewById(R.id.edit_text_columna1_fila3_tabla2);
        c1f4 = findViewById(R.id.edit_text_columna1_fila4_tabla2);
        c1f5 = findViewById(R.id.edit_text_columna1_fila5_tabla2);
        c1f6 = findViewById(R.id.edit_text_columna1_fila6_tabla2);
        c1f7 = findViewById(R.id.edit_text_columna1_fila7_tabla2);
        c1f8 = findViewById(R.id.edit_text_columna1_fila8_tabla2);
        c1f9 = findViewById(R.id.edit_text_columna1_fila9_tabla2);
        c1f10 = findViewById(R.id.edit_text_columna1_fila10_tabla2);
        c1f11 = findViewById(R.id.edit_text_columna1_fila11_tabla2);
        c1f12 = findViewById(R.id.edit_text_columna1_fila12_tabla2);
        c1f13 = findViewById(R.id.edit_text_columna1_fila13_tabla2);
        c1f14 = findViewById(R.id.edit_text_columna1_fila14_tabla2);
        c1f15 = findViewById(R.id.edit_text_columna1_fila15_tabla2);



        c2f1 = findViewById(R.id.edit_text_columna2_fila1_tabla2);
        c2f2 = findViewById(R.id.edit_text_columna2_fila2_tabla2);
        c2f3 = findViewById(R.id.edit_text_columna2_fila3_tabla2);
        c2f4 = findViewById(R.id.edit_text_columna2_fila4_tabla2);
        c2f5 = findViewById(R.id.edit_text_columna2_fila5_tabla2);
        c2f6 = findViewById(R.id.edit_text_columna2_fila6_tabla2);
        c2f7 = findViewById(R.id.edit_text_columna2_fila7_tabla2);
        c2f8 = findViewById(R.id.edit_text_columna2_fila8_tabla2);
        c2f9 = findViewById(R.id.edit_text_columna2_fila9_tabla2);
        c2f10 = findViewById(R.id.edit_text_columna2_fila10_tabla2);
        c2f11 = findViewById(R.id.edit_text_columna2_fila11_tabla2);
        c2f12 = findViewById(R.id.edit_text_columna2_fila12_tabla2);
        c2f13 = findViewById(R.id.edit_text_columna2_fila13_tabla2);
        c2f14 = findViewById(R.id.edit_text_columna2_fila14_tabla2);
        c2f15 = findViewById(R.id.edit_text_columna2_fila15_tabla2);


        c3f1 = findViewById(R.id.edit_text_columna3_fila1_tabla2);
        c3f2 = findViewById(R.id.edit_text_columna3_fila2_tabla2);
        c3f3 = findViewById(R.id.edit_text_columna3_fila3_tabla2);
        c3f4 = findViewById(R.id.edit_text_columna3_fila4_tabla2);
        c3f5 = findViewById(R.id.edit_text_columna3_fila5_tabla2);
        c3f6 = findViewById(R.id.edit_text_columna3_fila6_tabla2);
        c3f7 = findViewById(R.id.edit_text_columna3_fila7_tabla2);
        c3f8 = findViewById(R.id.edit_text_columna3_fila8_tabla2);
        c3f9 = findViewById(R.id.edit_text_columna3_fila9_tabla2);
        c3f10 = findViewById(R.id.edit_text_columna3_fila10_tabla2);
        c3f11 = findViewById(R.id.edit_text_columna3_fila11_tabla2);
        c3f12 = findViewById(R.id.edit_text_columna3_fila12_tabla2);
        c3f13 = findViewById(R.id.edit_text_columna3_fila13_tabla2);
        c3f14 = findViewById(R.id.edit_text_columna3_fila14_tabla2);
        c3f15 = findViewById(R.id.edit_text_columna3_fila15_tabla2);



        c4f1 = findViewById(R.id.edit_text_columna4_fila1_tabla2);
        c4f2 = findViewById(R.id.edit_text_columna4_fila2_tabla2);
        c4f3 = findViewById(R.id.edit_text_columna4_fila3_tabla2);
        c4f4 = findViewById(R.id.edit_text_columna4_fila4_tabla2);
        c4f5 = findViewById(R.id.edit_text_columna4_fila5_tabla2);
        c4f6 = findViewById(R.id.edit_text_columna4_fila6_tabla2);
        c4f7 = findViewById(R.id.edit_text_columna4_fila7_tabla2);
        c4f8 = findViewById(R.id.edit_text_columna4_fila8_tabla2);
        c4f9 = findViewById(R.id.edit_text_columna4_fila9_tabla2);
        c4f10 = findViewById(R.id.edit_text_columna4_fila10_tabla2);
        c4f11 = findViewById(R.id.edit_text_columna4_fila11_tabla2);
        c4f12 = findViewById(R.id.edit_text_columna4_fila12_tabla2);
        c4f13 = findViewById(R.id.edit_text_columna4_fila13_tabla2);
        c4f14 = findViewById(R.id.edit_text_columna4_fila14_tabla2);
        c4f15 = findViewById(R.id.edit_text_columna4_fila15_tabla2);


        c5f1 = findViewById(R.id.edit_text_columna5_fila1_tabla2);
        c5f2 = findViewById(R.id.edit_text_columna5_fila2_tabla2);
        c5f3 = findViewById(R.id.edit_text_columna5_fila3_tabla2);
        c5f4 = findViewById(R.id.edit_text_columna5_fila4_tabla2);
        c5f5 = findViewById(R.id.edit_text_columna5_fila5_tabla2);
        c5f6 = findViewById(R.id.edit_text_columna5_fila6_tabla2);
        c5f7 = findViewById(R.id.edit_text_columna5_fila7_tabla2);
        c5f8 = findViewById(R.id.edit_text_columna5_fila8_tabla2);
        c5f9 = findViewById(R.id.edit_text_columna5_fila9_tabla2);
        c5f10 = findViewById(R.id.edit_text_columna5_fila10_tabla2);
        c5f11 = findViewById(R.id.edit_text_columna5_fila11_tabla2);
        c5f12 = findViewById(R.id.edit_text_columna5_fila12_tabla2);
        c5f13 = findViewById(R.id.edit_text_columna5_fila13_tabla2);
        c5f14 = findViewById(R.id.edit_text_columna5_fila14_tabla2);
        c5f15 = findViewById(R.id.edit_text_columna5_fila15_tabla2);

        String codigoo = getIntent().getStringExtra("codigo_chequeo");
        codigo.setText(codigoo);
        codigo.setEnabled(false);

        button_save_material_tabla2 = findViewById(R.id.button_save_material_tabla2);
        button_save_material_tabla2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder Bien = new AlertDialog.Builder(TableTwoMaterialCheckActivity.this);
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
                            Toast.makeText(TableTwoMaterialCheckActivity.this, "Permiso concedido, puedes continuar", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(TableTwoMaterialCheckActivity.this, "Permiso denegado, no puedes continuar.", Toast.LENGTH_SHORT).show();
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
        File file = new File(ruteFolder, "FChequeo2"+nameTeam+".csv");
        boolean isCreate = false;
        if(!ruteFolder.exists()){
            Toast.makeText(TableTwoMaterialCheckActivity.this, "No se encuentra el archivo...", Toast.LENGTH_SHORT).show();
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
                    AlertDialog.Builder Bien = new AlertDialog.Builder(TableTwoMaterialCheckActivity.this);
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
                Toast.makeText(TableTwoMaterialCheckActivity.this, "Error: probablemente la tabla no se han creado aún...", Toast.LENGTH_SHORT).show();
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
        File filecrate = new File(ruteFolder, "FChequeo2"+nameTeam+".csv");
        String nameFileInDrive = "FChequeo2"+nameTeam+".csv";
        try {
            filecrate.createNewFile();
            String[] infoCreate = {codigo.getText().toString(),
            c1f1.getText().toString(),c1f2.getText().toString(),c1f3.getText().toString(),c1f4.getText().toString(),c1f5.getText().toString(),
                    c1f6.getText().toString(),c1f7.getText().toString(),c1f8.getText().toString(),c1f9.getText().toString(),c1f10.getText().toString(),
                    c1f11.getText().toString(),c1f12.getText().toString(),c1f13.getText().toString(),c1f14.getText().toString(),c1f15.getText().toString(),

                    c2f1.getText().toString(),c2f2.getText().toString(),c2f3.getText().toString(),c2f4.getText().toString(),c2f5.getText().toString(),
                    c2f6.getText().toString(),c2f7.getText().toString(),c2f8.getText().toString(),c2f9.getText().toString(),c2f10.getText().toString(),
                    c2f11.getText().toString(),c2f12.getText().toString(),c2f13.getText().toString(),c2f14.getText().toString(),c2f15.getText().toString(),

                    c3f1.getText().toString(),c3f2.getText().toString(),c3f3.getText().toString(),c3f4.getText().toString(),c3f5.getText().toString(),
                    c3f6.getText().toString(),c3f7.getText().toString(),c3f8.getText().toString(),c3f9.getText().toString(),c3f10.getText().toString(),
                    c3f11.getText().toString(),c3f12.getText().toString(),c3f13.getText().toString(),c3f14.getText().toString(),c3f15.getText().toString(),

                    c4f1.getText().toString(),c4f2.getText().toString(),c4f3.getText().toString(),c4f4.getText().toString(),c4f5.getText().toString(),
                    c4f6.getText().toString(),c4f7.getText().toString(),c4f8.getText().toString(),c4f9.getText().toString(),c4f10.getText().toString(),
                    c4f11.getText().toString(),c4f12.getText().toString(),c4f13.getText().toString(),c4f14.getText().toString(),c4f15.getText().toString(),

                    c5f1.getText().toString(),c5f2.getText().toString(),c5f3.getText().toString(),c5f4.getText().toString(),c5f5.getText().toString(),
                    c5f6.getText().toString(),c5f7.getText().toString(),c5f8.getText().toString(),c5f9.getText().toString(),c5f10.getText().toString(),
                    c5f11.getText().toString(),c5f12.getText().toString(),c5f13.getText().toString(),c5f14.getText().toString(),c5f15.getText().toString(),};





            CSVWriter fileWriter = new CSVWriter(new OutputStreamWriter(new FileOutputStream(filecrate, true),
                    "ISO-8859-1"), ',',
                    CSVWriter.NO_QUOTE_CHARACTER,
                    CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                    CSVWriter.DEFAULT_LINE_END);

            fileWriter.writeNext(infoCreate);
            fileWriter.close();

            ProgressDialog progressDialog = new ProgressDialog(TableTwoMaterialCheckActivity.this);
            progressDialog.setTitle("Archivo creado, sincronizando con Google Drive.");
            progressDialog.setMessage("Por favor, espera.");
            progressDialog.show();


            try {
                driveServiceHelper.createFileDrive(nameFileInDrive, filecrate.getPath()).addOnSuccessListener(new OnSuccessListener<String>() {
                    @Override
                    public void onSuccess(String s) {
                        progressDialog.dismiss();
                        Toast.makeText(TableTwoMaterialCheckActivity.this, "El archivo ya está en Google Drive con tu cuenta de Google", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(TableTwoMaterialCheckActivity.this, "No se pudo subir el archivo a Google Drive", Toast.LENGTH_SHORT).show();
                    }
                });
            }catch (Exception e){
                progressDialog.dismiss();
                Toast.makeText(TableTwoMaterialCheckActivity.this, "Ocurrio un problema al subir el archivo a drive", Toast.LENGTH_SHORT).show();
            }





            Toast.makeText(TableTwoMaterialCheckActivity.this, "Datos agregados al archivo: " + filecrate.getPath(), Toast.LENGTH_LONG).show();
        }catch (IOException ie){

            Toast.makeText(TableTwoMaterialCheckActivity.this, "Ocurrio un problema al crear el archivo."+ ie, Toast.LENGTH_LONG).show();
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
                File filecrate = new File(ruteFolder, "FChequeo2"+nameTeam+".csv");
                if(!filecrate.exists()){
                    filecrate.createNewFile();
                    String[] titles = {"Codigo",
                            "1.1", "1.2", "1.3",
                            "1.4",
                            "1.5",
                            "1.6",
                            "1.7",
                            "1.8",
                            "1.9",
                            "1.10",
                            "1.11",
                            "1.12",
                            "1.13",
                            "1.14",
                            "1.15",

                            "2.1",
                            "2.2",
                            "2.3",
                            "2.4",
                            "2.5",
                            "2.6",
                            "2.7",
                            "2.8",
                            "2.9",
                            "2.10",
                            "2.11",
                            "2.12",
                            "2.13",
                            "2.14",
                            "2.15",

                            "3.1",
                            "3.2",
                            "3.3",
                            "3.4",
                            "3.5",
                            "3.6",
                            "3.7",
                            "3.8",
                            "3.9",
                            "3.10",
                            "3.11",
                            "3.12",
                            "3.13",
                            "3.14",
                            "3.15",

                            "4.1",
                            "4.2",
                            "4.3",
                            "4.4",
                            "4.5",
                            "4.6",
                            "4.7",
                            "4.8",
                            "4.9",
                            "4.10",
                            "4.11",
                            "4.12",
                            "4.13",
                            "4.14",
                            "4.15",

                            "5.1",
                            "5.2",
                            "5.3",
                            "5.4",
                            "5.5",
                            "5.6",
                            "5.7",
                            "5.8",
                            "5.9",
                            "5.10",
                            "5.11",
                            "5.12",
                            "5.13",
                            "5.14",
                            "5.15"

                    };
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
                    Toast.makeText(TableTwoMaterialCheckActivity.this, "Por favor, acepta los permisos para la aplicación "+ app_name, Toast.LENGTH_LONG).show();
                    finish();
                }catch (Exception e){
                    Intent intent = new Intent();
                    intent.setAction(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                    activityResultLauncher.launch(intent);
                    Toast.makeText(TableTwoMaterialCheckActivity.this, "Por favor, acepta los permisos para la aplicación "+ app_name, Toast.LENGTH_LONG).show();
                    finish();
                }
            }


        }else{
            int permissionResult = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if(permissionResult == PackageManager.PERMISSION_DENIED){
                AlertDialog.Builder Bien = new AlertDialog.Builder(TableTwoMaterialCheckActivity.this);
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
                File filecrate = new File(ruteFolder, "FChequeo2"+nameTeam+".csv");
                if(!filecrate.exists()){
                    try {
                        filecrate.createNewFile();
                        String[] titles = {"Codigo",
                                "1.1", "1.2", "1.3",
                                "1.4",
                                "1.5",
                                "1.6",
                                "1.7",
                                "1.8",
                                "1.9",
                                "1.10",
                                "1.11",
                                "1.12",
                                "1.13",
                                "1.14",
                                "1.15",

                                "2.1",
                                "2.2",
                                "2.3",
                                "2.4",
                                "2.5",
                                "2.6",
                                "2.7",
                                "2.8",
                                "2.9",
                                "2.10",
                                "2.11",
                                "2.12",
                                "2.13",
                                "2.14",
                                "2.15",

                                "3.1",
                                "3.2",
                                "3.3",
                                "3.4",
                                "3.5",
                                "3.6",
                                "3.7",
                                "3.8",
                                "3.9",
                                "3.10",
                                "3.11",
                                "3.12",
                                "3.13",
                                "3.14",
                                "3.15",

                                "4.1",
                                "4.2",
                                "4.3",
                                "4.4",
                                "4.5",
                                "4.6",
                                "4.7",
                                "4.8",
                                "4.9",
                                "4.10",
                                "4.11",
                                "4.12",
                                "4.13",
                                "4.14",
                                "4.15",

                                "5.1",
                                "5.2",
                                "5.3",
                                "5.4",
                                "5.5",
                                "5.6",
                                "5.7",
                                "5.8",
                                "5.9",
                                "5.10",
                                "5.11",
                                "5.12",
                                "5.13",
                                "5.14",
                                "5.15"

                        };
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
                        GoogleAccountCredential credential = GoogleAccountCredential.usingOAuth2(TableTwoMaterialCheckActivity.this, Collections.singleton(DriveScopes.DRIVE_FILE));
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