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

public class TableOneChainCustodyActivity extends AppCompatActivity {
    DriveServiceHelper driveServiceHelper;
    EditText c1f1,c1f2, c1f3,c1f4,c1f5,c1f6,c1f7,c1f8,c1f9,c1f10,
            c2f1,c2f2, c2f3,c2f4,c2f5,c2f6,c2f7,c2f8,c2f9,c2f10,
            c3f1,c3f2, c3f3,c3f4,c3f5,c3f6,c3f7,c3f8,c3f9,c3f10,
            c4f1,c4f2, c4f3,c4f4,c4f5,c4f6,c4f7,c4f8,c4f9,c4f10,
            c6f1,c6f2, c6f3,c6f4,c6f5,c6f6,c6f7,c6f8,c6f9,c6f10,
            c7f1,c7f2, c7f3,c7f4,c7f5,c7f6,c7f7,c7f8,c7f9,c7f10, codigo_tabla1_cadena;
    Spinner c5f1,c5f2, c5f3,c5f4,c5f5,c5f6,c5f7,c5f8,c5f9,c5f10;
    Button button_save_cadena_tabla1;
    int REQUEST_CODE = 200;
    ActivityResultLauncher<Intent> activityResultLauncher;
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_one_chain_custody);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        button_save_cadena_tabla1 = findViewById(R.id.button_save_Cadena_tabla1);
        c5f1 = findViewById(R.id.Spinner_columna5_fila1_tabla1_cadena11);
        c5f2 = findViewById(R.id.Spinner_columna5_fila2_tabla1_cadena11);
        c5f3 = findViewById(R.id.Spinner_columna5_fila3_tabla1_cadena11);
        c5f4 = findViewById(R.id.Spinner_columna5_fila4_tabla1_cadena11);
        c5f5 = findViewById(R.id.Spinner_columna5_fila5_tabla1_cadena11);
        c5f6 = findViewById(R.id.Spinner_columna5_fila6_tabla1_cadena11);
        c5f7 = findViewById(R.id.Spinner_columna5_fila7_tabla1_cadena11);
        c5f8 = findViewById(R.id.Spinner_columna5_fila8_tabla1_cadena11);
        c5f9 = findViewById(R.id.Spinner_columna5_fila9_tabla1_cadena11);
        c5f10 = findViewById(R.id.Spinner_columna5_fila10_tabla1_cadena11);



        c1f1 = findViewById(R.id.edit_text_columna1_fila1_tabla1_cadena11);
        c1f2 = findViewById(R.id.edit_text_columna1_fila2_tabla1_cadena11);
        c1f3 = findViewById(R.id.edit_text_columna1_fila3_tabla1_cadena11);
        c1f4 = findViewById(R.id.edit_text_columna1_fila4_tabla1_cadena11);
        c1f5 = findViewById(R.id.edit_text_columna1_fila5_tabla1_cadena11);
        c1f6 = findViewById(R.id.edit_text_columna1_fila6_tabla1_cadena11);
        c1f7 = findViewById(R.id.edit_text_columna1_fila7_tabla1_cadena11);
        c1f8 = findViewById(R.id.edit_text_columna1_fila8_tabla1_cadena11);
        c1f9 = findViewById(R.id.edit_text_columna1_fila9_tabla1_cadena11);
        c1f10 = findViewById(R.id.edit_text_columna1_fila10_tabla1_cadena11);


        c2f1 = findViewById(R.id.edit_text_columna2_fila1_tabla1_cadena11);
        c2f2 = findViewById(R.id.edit_text_columna2_fila2_tabla1_cadena11);
        c2f3 = findViewById(R.id.edit_text_columna2_fila3_tabla1_cadena11);
        c2f4 = findViewById(R.id.edit_text_columna2_fila4_tabla1_cadena11);
        c2f5 = findViewById(R.id.edit_text_columna2_fila5_tabla1_cadena11);
        c2f6 = findViewById(R.id.edit_text_columna2_fila6_tabla1_cadena11);
        c2f7 = findViewById(R.id.edit_text_columna2_fila7_tabla1_cadena11);
        c2f8 = findViewById(R.id.edit_text_columna2_fila8_tabla1_cadena11);
        c2f9 = findViewById(R.id.edit_text_columna2_fila9_tabla1_cadena11);
        c2f10 = findViewById(R.id.edit_text_columna2_fila10_tabla1_cadena11);


        c3f1 = findViewById(R.id.edit_text_columna3_fila1_tabla1_cadena11);
        c3f2 = findViewById(R.id.edit_text_columna3_fila2_tabla1_cadena11);
        c3f3 = findViewById(R.id.edit_text_columna3_fila3_tabla1_cadena11);
        c3f4 = findViewById(R.id.edit_text_columna3_fila4_tabla1_cadena11);
        c3f5 = findViewById(R.id.edit_text_columna3_fila5_tabla1_cadena11);
        c3f6 = findViewById(R.id.edit_text_columna3_fila6_tabla1_cadena11);
        c3f7 = findViewById(R.id.edit_text_columna3_fila7_tabla1_cadena11);
        c3f8 = findViewById(R.id.edit_text_columna3_fila8_tabla1_cadena11);
        c3f9 = findViewById(R.id.edit_text_columna3_fila9_tabla1_cadena11);
        c3f10 = findViewById(R.id.edit_text_columna3_fila10_tabla1_cadena11);

        c4f1 = findViewById(R.id.edit_text_columna4_fila1_tabla1_cadena11);
        c4f2 = findViewById(R.id.edit_text_columna4_fila2_tabla1_cadena11);
        c4f3 = findViewById(R.id.edit_text_columna4_fila3_tabla1_cadena11);
        c4f4 = findViewById(R.id.edit_text_columna4_fila4_tabla1_cadena11);
        c4f5 = findViewById(R.id.edit_text_columna4_fila5_tabla1_cadena11);
        c4f6 = findViewById(R.id.edit_text_columna4_fila6_tabla1_cadena11);
        c4f7 = findViewById(R.id.edit_text_columna4_fila7_tabla1_cadena11);
        c4f8 = findViewById(R.id.edit_text_columna4_fila8_tabla1_cadena11);
        c4f9 = findViewById(R.id.edit_text_columna4_fila9_tabla1_cadena11);
        c4f10 = findViewById(R.id.edit_text_columna4_fila10_tabla1_cadena11);







        c6f1 = findViewById(R.id.edit_text_columna6_fila1_tabla1_cadena11);
        c6f2 = findViewById(R.id.edit_text_columna6_fila2_tabla1_cadena11);
        c6f3 = findViewById(R.id.edit_text_columna6_fila3_tabla1_cadena11);
        c6f4 = findViewById(R.id.edit_text_columna6_fila4_tabla1_cadena11);
        c6f5 = findViewById(R.id.edit_text_columna6_fila5_tabla1_cadena11);
        c6f6 = findViewById(R.id.edit_text_columna6_fila6_tabla1_cadena11);
        c6f7 = findViewById(R.id.edit_text_columna6_fila7_tabla1_cadena11);
        c6f8 = findViewById(R.id.edit_text_columna6_fila8_tabla1_cadena11);
        c6f9 = findViewById(R.id.edit_text_columna6_fila9_tabla1_cadena11);
        c6f10 = findViewById(R.id.edit_text_columna6_fila10_tabla1_cadena11);


        c7f1 = findViewById(R.id.edit_text_columna7_fila1_tabla1_cadena11);
        c7f2 = findViewById(R.id.edit_text_columna7_fila2_tabla1_cadena11);
        c7f3 = findViewById(R.id.edit_text_columna7_fila3_tabla1_cadena11);
        c7f4 = findViewById(R.id.edit_text_columna7_fila4_tabla1_cadena11);
        c7f5 = findViewById(R.id.edit_text_columna7_fila5_tabla1_cadena11);
        c7f6 = findViewById(R.id.edit_text_columna7_fila6_tabla1_cadena11);
        c7f7 = findViewById(R.id.edit_text_columna7_fila7_tabla1_cadena11);
        c7f8 = findViewById(R.id.edit_text_columna7_fila8_tabla1_cadena11);
        c7f9 = findViewById(R.id.edit_text_columna7_fila9_tabla1_cadena11);
        c7f10 = findViewById(R.id.edit_text_columna7_fila10_tabla1_cadena11);
        codigo_tabla1_cadena= findViewById(R.id.edit_text_codigo_Cadena_tabla1);
        String codigo = getIntent().getStringExtra("codigo_chain_custody");
        codigo_tabla1_cadena.setText(codigo);
        codigo_tabla1_cadena.setEnabled(false);
        button_save_cadena_tabla1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(codigo_tabla1_cadena.equals("")){
                    Toast.makeText(TableOneChainCustodyActivity.this, "completa el campo de código", Toast.LENGTH_LONG).show();
                }else{
                    AlertDialog.Builder Bien = new AlertDialog.Builder(TableOneChainCustodyActivity.this);
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
            }
        });
        singin();
        activityResultLauncher=registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if(result.getResultCode()== Activity.RESULT_OK){
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){
                        if (Environment.isExternalStorageManager()){
                            Toast.makeText(TableOneChainCustodyActivity.this, "Permiso concedido, puedes continuar", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(TableOneChainCustodyActivity.this, "Permiso denegado, no puedes continuar.", Toast.LENGTH_SHORT).show();
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
        File file = new File(ruteFolder, "BCadena1"+nameTeam+".csv");
        boolean isCreate = false;
        if(!ruteFolder.exists()){
            Toast.makeText(TableOneChainCustodyActivity.this, "No se encuentra el archivo...", Toast.LENGTH_SHORT).show();
        }else{
            String cadena;
            String[] arreglo = {};

            try{
                FileReader fileReader =  new FileReader(file);
                BufferedReader bufferedReader = new BufferedReader(fileReader);

                while((cadena = bufferedReader.readLine()) != null){
                    arreglo = cadena.split(",");
                }
                boolean value = contains(arreglo, codigo_tabla1_cadena.getText().toString());
                if(value){
                    AlertDialog.Builder Bien = new AlertDialog.Builder(TableOneChainCustodyActivity.this);
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
                Toast.makeText(TableOneChainCustodyActivity.this, "Error: probablemente la tabla no se han creado aún...", Toast.LENGTH_SHORT).show();
            }

        }
    }


    private void saveFile(){

        SharedPreferences preferencesId = getSharedPreferences("EquipoVeolia", Context.MODE_PRIVATE);
        final String nameTeam = preferencesId.getString("NombreEquipo","E1");
        File ruteFolder = new File(getFilesDir()+"/ArchivosGeneradosVeolia");
        if (!ruteFolder.exists()) {
            createFile();
        }
        File filecrate = new File(ruteFolder, "BCadena1"+nameTeam+".csv");
        String nameFileInDrive = "BCadena1"+nameTeam+".csv";
        try {
            filecrate.createNewFile();
            String[] infoCreate = {
                    codigo_tabla1_cadena.getText().toString(),
                    c5f1.getSelectedItem().toString(),c5f2.getSelectedItem().toString(),
                    c5f3.getSelectedItem().toString(),c5f4.getSelectedItem().toString(),c5f5.getSelectedItem().toString(),
                    c5f6.getSelectedItem().toString(),c5f7.getSelectedItem().toString(),c5f8.getSelectedItem().toString(),
                    c5f9.getSelectedItem().toString(),c5f10.getSelectedItem().toString(),

                    c1f1.getText().toString(),c1f2.getText().toString(),c1f3.getText().toString(),c1f4.getText().toString(),
                    c1f5.getText().toString(),c1f6.getText().toString(),c1f7.getText().toString(),c1f8.getText().toString(),
                    c1f9.getText().toString(),c1f10.getText().toString(),

                    c2f1.getText().toString(),c2f2.getText().toString(),c2f3.getText().toString(),c2f4.getText().toString(),
                    c2f5.getText().toString(),c2f6.getText().toString(),c2f7.getText().toString(),c2f8.getText().toString(),
                    c2f9.getText().toString(),c2f10.getText().toString(),

                    c3f1.getText().toString(),c3f2.getText().toString(),c3f3.getText().toString(),c3f4.getText().toString(),
                    c3f5.getText().toString(),c3f6.getText().toString(),c3f7.getText().toString(),c3f8.getText().toString(),
                    c3f9.getText().toString(),c3f10.getText().toString(),

                    c4f1.getText().toString(),c4f2.getText().toString(),c4f3.getText().toString(),c4f4.getText().toString(),
                    c4f5.getText().toString(),c4f6.getText().toString(),c4f7.getText().toString(),c4f8.getText().toString(),
                    c4f9.getText().toString(),c4f10.getText().toString(),

                    c6f1.getText().toString(),c6f2.getText().toString(),c6f3.getText().toString(),c6f4.getText().toString(),
                    c6f5.getText().toString(),c6f6.getText().toString(),c6f7.getText().toString(),c6f8.getText().toString(),
                    c6f9.getText().toString(),c6f10.getText().toString(),

                    c7f1.getText().toString(),c7f2.getText().toString(),c7f3.getText().toString(),c7f4.getText().toString(),
                    c7f5.getText().toString(),c7f6.getText().toString(),c7f7.getText().toString(),c7f8.getText().toString(),
                    c7f9.getText().toString(),c7f10.getText().toString(),};





            CSVWriter fileWriter = new CSVWriter(new OutputStreamWriter(new FileOutputStream(filecrate, true),
                    "ISO-8859-1"), ',',
                    CSVWriter.NO_QUOTE_CHARACTER,
                    CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                    CSVWriter.DEFAULT_LINE_END);

            fileWriter.writeNext(infoCreate);
            fileWriter.close();

            ProgressDialog progressDialog = new ProgressDialog(TableOneChainCustodyActivity.this);
            progressDialog.setTitle("Archivo creado, sincronizando con Google Drive.");
            progressDialog.setMessage("Por favor, espera.");
            progressDialog.show();


            try {
                driveServiceHelper.createFileDrive(nameFileInDrive, filecrate.getPath()).addOnSuccessListener(new OnSuccessListener<String>() {
                    @Override
                    public void onSuccess(String s) {
                        progressDialog.dismiss();
                        Toast.makeText(TableOneChainCustodyActivity.this, "El archivo ya está en Google Drive con tu cuenta de Google", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(TableOneChainCustodyActivity.this, "No se pudo subir el archivo a Google Drive", Toast.LENGTH_SHORT).show();
                    }
                });
            }catch (Exception e){
                progressDialog.dismiss();
                Toast.makeText(TableOneChainCustodyActivity.this, "Ocurrio un problema al subir el archivo a drive", Toast.LENGTH_SHORT).show();
            }





            Toast.makeText(TableOneChainCustodyActivity.this, "Datos agregados al archivo: " + filecrate.getPath(), Toast.LENGTH_LONG).show();
        }catch (IOException ie){

            Toast.makeText(TableOneChainCustodyActivity.this, "Ocurrio un problema al crear el archivo."+ ie, Toast.LENGTH_LONG).show();
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
                    File filecrate = new File(ruteFolder, "BCadena1"+nameTeam+".csv");
                if(!filecrate.exists()){
                    filecrate.createNewFile();
                    String[] titles = {"Codigo",
                            //Columna5
                            "C5 f1", "C5 f2", "C5 f3", "C5 f4", "C5 f5", "C5 f6", "C5 f7", "C5 f8",
                            "C5 f9", "C5 f10",
                            //Columna1
                            "C1 f1", "C1 f2", "C1 f3", "C1 f4", "C1 f5", "C1 f6", "C1 f7", "C1 f8",
                            "C1 f9", "C1 f10",
                            //Columna2
                            "C2 f1", "C2 f2", "C2 f3", "C2 f4", "C2 f5", "C2 f6", "C2 f7", "C2 f8",
                            "C2 f9", "C2 f10",
                            //Columna3
                            "C3 f1", "C3 f2", "C3 f3", "C3 f4", "C3 f5", "C3 f6", "C3 f7", "C3 f8",
                            "C3 f9", "C3 f10",
                            //Columna4
                            "C4 f1", "C4 f2", "C4 f3", "C4 f4", "C4 f5", "C4 f6", "C4 f7", "C4 f8",
                            "C4 f9", "C4 f10",
                            //Columna6
                            "C6 f1", "C6 f2", "C6 f3", "C6 f4", "C6 f5", "C6 f6", "C6 f7", "C6 f8",
                            "C6 f9", "C6 f10",
                            //Columna7
                            "C7 f1", "C7 f2", "C7 f3", "C7 f4", "C7 f5", "C7 f6", "C7 f7", "C7 f8",
                            "C7 f9", "C7 f10",};
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
                    Toast.makeText(TableOneChainCustodyActivity.this, "Por favor, acepta los permisos para la aplicación "+ app_name, Toast.LENGTH_LONG).show();
                    finish();
                }catch (Exception e){
                    Intent intent = new Intent();
                    intent.setAction(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                    activityResultLauncher.launch(intent);
                    Toast.makeText(TableOneChainCustodyActivity.this, "Por favor, acepta los permisos para la aplicación "+ app_name, Toast.LENGTH_LONG).show();
                    finish();
                }
            }


        }else{
            int permissionResult = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if(permissionResult == PackageManager.PERMISSION_DENIED){
                AlertDialog.Builder Bien = new AlertDialog.Builder(TableOneChainCustodyActivity.this);
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
                File filecrate = new File(ruteFolder, "BCadena1"+nameTeam+".csv");
                if(!filecrate.exists()){
                    try {
                        filecrate.createNewFile();
                        String[] titles = {"Codigo",
                                //Columna5
                                "C5 f1", "C5 f2", "C5 f3", "C5 f4", "C5 f5", "C5 f6", "C5 f7", "C5 f8",
                                "C5 f9", "C5 f10",
                                //Columna1
                                "C1 f1", "C1 f2", "C1 f3", "C1 f4", "C1 f5", "C1 f6", "C1 f7", "C1 f8",
                                "C1 f9", "C1 f10",
                                //Columna2
                                "C2 f1", "C2 f2", "C2 f3", "C2 f4", "C2 f5", "C2 f6", "C2 f7", "C2 f8",
                                "C2 f9", "C2 f10",
                                //Columna3
                                "C3 f1", "C3 f2", "C3 f3", "C3 f4", "C3 f5", "C3 f6", "C3 f7", "C3 f8",
                                "C3 f9", "C3 f10",
                                //Columna4
                                "C4 f1", "C4 f2", "C4 f3", "C4 f4", "C4 f5", "C4 f6", "C4 f7", "C4 f8",
                                "C4 f9", "C4 f10",
                                //Columna6
                                "C6 f1", "C6 f2", "C6 f3", "C6 f4", "C6 f5", "C6 f6", "C6 f7", "C6 f8",
                                "C6 f9", "C6 f10",
                                //Columna7
                                "C7 f1", "C7 f2", "C7 f3", "C7 f4", "C7 f5", "C7 f6", "C7 f7", "C7 f8",
                                "C7 f9", "C7 f10",};
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
                        GoogleAccountCredential credential = GoogleAccountCredential.usingOAuth2(TableOneChainCustodyActivity.this, Collections.singleton(DriveScopes.DRIVE_FILE));
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