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

public class TableOneCaudalVolActivity extends AppCompatActivity {
    DriveServiceHelper driveServiceHelper;
    Button button_save_caudalvol_tabla2;
    int REQUEST_CODE = 200;
    ActivityResultLauncher<Intent> activityResultLauncher;
    Spinner c2f1,c2f2,c2f3,c2f4,c2f5;
    EditText c1f1,c1f2,c1f3,c1f4,c1f5,
            c3f1,c3f2,c3f3,c3f4,c3f5,
            c6f1,c6f2,c6f3,c6f4,c6f5,
            c7f1,c7f2,c7f3,c7f4,c7f5,
            c4f1,c4f2,c4f3,c4f4,c4f5,
            c5f1,c5f2,c5f3,c5f4,c5f5,
            c8f1,c8f2,c8f3,c8f4,c8f5, codigo;
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_one_caudal_vol);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        codigo = findViewById(R.id.edit_text_codigo_Caudal_vol_tabla2);
        c1f1 = findViewById(R.id.edit_text_columna1_fila1_tabla2_caudal_vol_12);
        c1f2 = findViewById(R.id.edit_text_columna1_fila2_tabla2_caudal_vol_12);
        c1f3 = findViewById(R.id.edit_text_columna1_fila3_tabla2_caudal_vol_12);
        c1f4 = findViewById(R.id.edit_text_columna1_fila4_tabla2_caudal_vol_12);
        c1f5 = findViewById(R.id.edit_text_columna1_fila5_tabla2_caudal_vol_12);

        c2f1 = findViewById(R.id.edit_text_columna2_fila1_tabla2_caudal_vol_12);
        c2f2 = findViewById(R.id.edit_text_columna2_fila2_tabla2_caudal_vol_12);
        c2f3 = findViewById(R.id.edit_text_columna2_fila3_tabla2_caudal_vol_12);
        c2f4 = findViewById(R.id.edit_text_columna2_fila4_tabla2_caudal_vol_12);
        c2f5 = findViewById(R.id.edit_text_columna2_fila5_tabla2_caudal_vol_12);


        c3f1 = findViewById(R.id.edit_text_columna3_fila1_tabla2_caudal_vol_12);
        c3f2 = findViewById(R.id.edit_text_columna3_fila2_tabla2_caudal_vol_12);
        c3f3 = findViewById(R.id.edit_text_columna3_fila3_tabla2_caudal_vol_12);
        c3f4 = findViewById(R.id.edit_text_columna3_fila4_tabla2_caudal_vol_12);
        c3f5 = findViewById(R.id.edit_text_columna3_fila5_tabla2_caudal_vol_12);

        c4f1 = findViewById(R.id.edit_text_columna4_fila1_tabla2_caudal_vol_12);
        c4f2 = findViewById(R.id.edit_text_columna4_fila2_tabla2_caudal_vol_12);
        c4f3 = findViewById(R.id.edit_text_columna4_fila3_tabla2_caudal_vol_12);
        c4f4 = findViewById(R.id.edit_text_columna4_fila4_tabla2_caudal_vol_12);
        c4f5 = findViewById(R.id.edit_text_columna4_fila5_tabla2_caudal_vol_12);


        c5f1 = findViewById(R.id.edit_text_columna5_fila1_tabla2_caudal_vol_12);
        c5f2 = findViewById(R.id.edit_text_columna5_fila2_tabla2_caudal_vol_12);
        c5f3 = findViewById(R.id.edit_text_columna5_fila3_tabla2_caudal_vol_12);
        c5f4 = findViewById(R.id.edit_text_columna5_fila4_tabla2_caudal_vol_12);
        c5f5 = findViewById(R.id.edit_text_columna5_fila5_tabla2_caudal_vol_12);


        c6f1 = findViewById(R.id.edit_text_columna6_fila1_tabla2_caudal_vol_12);
        c6f2 = findViewById(R.id.edit_text_columna6_fila2_tabla2_caudal_vol_12);
        c6f3 = findViewById(R.id.edit_text_columna6_fila3_tabla2_caudal_vol_12);
        c6f4 = findViewById(R.id.edit_text_columna6_fila4_tabla2_caudal_vol_12);
        c6f5 = findViewById(R.id.edit_text_columna6_fila5_tabla2_caudal_vol_12);


        c7f1 = findViewById(R.id.edit_text_columna7_fila1_tabla2_caudal_vol_12);
        c7f2 = findViewById(R.id.edit_text_columna7_fila2_tabla2_caudal_vol_12);
        c7f3 = findViewById(R.id.edit_text_columna7_fila3_tabla2_caudal_vol_12);
        c7f4 = findViewById(R.id.edit_text_columna7_fila4_tabla2_caudal_vol_12);
        c7f5 = findViewById(R.id.edit_text_columna7_fila5_tabla2_caudal_vol_12);



        c8f1 = findViewById(R.id.edit_text_columna8_fila1_tabla2_caudal_vol_12);
        c8f2 = findViewById(R.id.edit_text_columna8_fila2_tabla2_caudal_vol_12);
        c8f3 = findViewById(R.id.edit_text_columna8_fila3_tabla2_caudal_vol_12);
        c8f4 = findViewById(R.id.edit_text_columna8_fila4_tabla2_caudal_vol_12);
        c8f5 = findViewById(R.id.edit_text_columna8_fila5_tabla2_caudal_vol_12);

        String codigoo = getIntent().getStringExtra("codigo_caudal_vol");
        codigo.setText(codigoo);
        codigo.setEnabled(false);
        button_save_caudalvol_tabla2 = findViewById(R.id.button_save_caudal_vol_tabla2);
        button_save_caudalvol_tabla2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder Bien = new AlertDialog.Builder(TableOneCaudalVolActivity.this);
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
                            Toast.makeText(TableOneCaudalVolActivity.this, "Permiso concedido, puedes continuar", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(TableOneCaudalVolActivity.this, "Permiso denegado, no puedes continuar.", Toast.LENGTH_SHORT).show();
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
        File file = new File(ruteFolder, "DCaudalVol2"+nameTeam+".csv");
        boolean isCreate = false;
        if(!ruteFolder.exists()){
            Toast.makeText(TableOneCaudalVolActivity.this, "No se encuentra el archivo...", Toast.LENGTH_SHORT).show();
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
                    AlertDialog.Builder Bien = new AlertDialog.Builder(TableOneCaudalVolActivity.this);
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
                Toast.makeText(TableOneCaudalVolActivity.this, "Error: probablemente la tabla no se han creado aún...", Toast.LENGTH_SHORT).show();
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
        File filecrate = new File(ruteFolder, "DCaudalVol2"+nameTeam+".csv");
        String nameFileInDrive = "DCaudalVol2"+nameTeam+".csv";
        try {
            filecrate.createNewFile();
            String[] infoCreate = {codigo.getText().toString(), c1f1.getText().toString(),c1f2.getText().toString(),c1f3.getText().toString(),
                    c1f4.getText().toString(),c1f5.getText().toString(),
                    c2f1.getSelectedItem().toString(),c2f2.getSelectedItem().toString(),c2f3.getSelectedItem().toString(),
                    c2f4.getSelectedItem().toString(),c2f5.getSelectedItem().toString(),
                    c3f1.getText().toString(),c3f2.getText().toString(),c3f3.getText().toString(),
                    c3f4.getText().toString(),c3f5.getText().toString(),
                    c4f1.getText().toString(),c4f2.getText().toString(),c4f3.getText().toString(),
                    c4f4.getText().toString(),c4f5.getText().toString(),
                    c5f1.getText().toString(),c5f2.getText().toString(),c5f3.getText().toString(),
                    c5f4.getText().toString(),c5f5.getText().toString(),
                    c6f1.getText().toString(),c6f2.getText().toString(),c6f3.getText().toString(),
                    c6f4.getText().toString(),c6f5.getText().toString(),
                    c7f1.getText().toString(),c7f2.getText().toString(),c7f3.getText().toString(),
                    c7f4.getText().toString(),c7f5.getText().toString(),
                    c8f1.getText().toString(),c8f2.getText().toString(),c8f3.getText().toString(),
                    c8f4.getText().toString(),c8f5.getText().toString(),};





            CSVWriter fileWriter = new CSVWriter(new OutputStreamWriter(new FileOutputStream(filecrate, true),
                    "ISO-8859-1"), ',',
                    CSVWriter.NO_QUOTE_CHARACTER,
                    CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                    CSVWriter.DEFAULT_LINE_END);

            fileWriter.writeNext(infoCreate);
            fileWriter.close();

            ProgressDialog progressDialog = new ProgressDialog(TableOneCaudalVolActivity.this);
            progressDialog.setTitle("Archivo creado, sincronizando con Google Drive.");
            progressDialog.setMessage("Por favor, espera.");
            progressDialog.show();


            try {
                driveServiceHelper.createFileDrive(nameFileInDrive, filecrate.getPath()).addOnSuccessListener(new OnSuccessListener<String>() {
                    @Override
                    public void onSuccess(String s) {
                        progressDialog.dismiss();
                        Toast.makeText(TableOneCaudalVolActivity.this, "El archivo ya está en Google Drive con tu cuenta de Google", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(TableOneCaudalVolActivity.this, "No se pudo subir el archivo a Google Drive", Toast.LENGTH_SHORT).show();
                    }
                });
            }catch (Exception e){
                progressDialog.dismiss();
                Toast.makeText(TableOneCaudalVolActivity.this, "Ocurrio un problema al subir el archivo a drive", Toast.LENGTH_SHORT).show();
            }





            Toast.makeText(TableOneCaudalVolActivity.this, "Datos agregados al archivo: " + filecrate.getPath(), Toast.LENGTH_LONG).show();
        }catch (IOException ie){

            Toast.makeText(TableOneCaudalVolActivity.this, "Ocurrio un problema al crear el archivo."+ ie, Toast.LENGTH_LONG).show();
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
                    File filecrate = new File(ruteFolder, "DCaudalVol2"+nameTeam+".csv");
                if(!filecrate.exists()){
                    filecrate.createNewFile();
                    String[] titles = {"Codigo",
                            //columna1
                            "C1 F1",
                            "C1 F2",
                            "C1 F3",
                            "C1 F4",
                            "C1 F5",
                            //columna2
                            "C2 F1",
                            "C2 F2",
                            "C2 F3",
                            "C2 F4",
                            "C2 F5",
                            //columna3
                            "C3 F1",
                            "C3 F2",
                            "C3 F3",
                            "C3 F4",
                            "C3 F5",
                            //columna4
                            "C4 F1",
                            "C4 F2",
                            "C4 F3",
                            "C4 F4",
                            "C4 F5",
                            //columna5
                            "C5 F1",
                            "C5 F2",
                            "C5 F3",
                            "C5 F4",
                            "C5 F5",
                            //columna6
                            "C6 F1",
                            "C6 F2",
                            "C6 F3",
                            "C6 F4",
                            "C6 F5",
                            //columna7
                            "C7 F1",
                            "C7 F2",
                            "C7 F3",
                            "C7 F4",
                            "C7 F5",
                            //columna8
                            "C8 F1",
                            "C8 F2",
                            "C8 F3",
                            "C8 F4",
                            "C8 F5",};
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
                    Toast.makeText(TableOneCaudalVolActivity.this, "Por favor, acepta los permisos para la aplicación "+ app_name, Toast.LENGTH_LONG).show();
                    finish();
                }catch (Exception e){
                    Intent intent = new Intent();
                    intent.setAction(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                    activityResultLauncher.launch(intent);
                    Toast.makeText(TableOneCaudalVolActivity.this, "Por favor, acepta los permisos para la aplicación "+ app_name, Toast.LENGTH_LONG).show();
                    finish();
                }
            }


        }else{
            int permissionResult = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if(permissionResult == PackageManager.PERMISSION_DENIED){
                AlertDialog.Builder Bien = new AlertDialog.Builder(TableOneCaudalVolActivity.this);
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
                File filecrate = new File(ruteFolder, "DCaudalVol2"+nameTeam+".csv");
                if(!filecrate.exists()){
                    try {
                        filecrate.createNewFile();
                        String[] titles = {"Codigo",
                                //columna1
                                "C1 F1",
                                "C1 F2",
                                "C1 F3",
                                "C1 F4",
                                "C1 F5",
                                //columna2
                                "C2 F1",
                                "C2 F2",
                                "C2 F3",
                                "C2 F4",
                                "C2 F5",
                                //columna3
                                "C3 F1",
                                "C3 F2",
                                "C3 F3",
                                "C3 F4",
                                "C3 F5",
                                //columna4
                                "C4 F1",
                                "C4 F2",
                                "C4 F3",
                                "C4 F4",
                                "C4 F5",
                                //columna5
                                "C5 F1",
                                "C5 F2",
                                "C5 F3",
                                "C5 F4",
                                "C5 F5",
                                //columna6
                                "C6 F1",
                                "C6 F2",
                                "C6 F3",
                                "C6 F4",
                                "C6 F5",
                                //columna7
                                "C7 F1",
                                "C7 F2",
                                "C7 F3",
                                "C7 F4",
                                "C7 F5",
                                //columna8
                                "C8 F1",
                                "C8 F2",
                                "C8 F3",
                                "C8 F4",
                                "C8 F5",};
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
                        GoogleAccountCredential credential = GoogleAccountCredential.usingOAuth2(TableOneCaudalVolActivity.this, Collections.singleton(DriveScopes.DRIVE_FILE));
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