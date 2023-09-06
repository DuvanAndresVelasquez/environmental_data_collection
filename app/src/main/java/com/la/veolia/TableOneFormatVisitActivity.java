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

public class TableOneFormatVisitActivity extends AppCompatActivity {
    Button button_save_tabla1_visit;
    DriveServiceHelper driveServiceHelper;
    int REQUEST_CODE = 200;
    ActivityResultLauncher<Intent> activityResultLauncher;
    EditText c1f1, c1f2,c1f3,c1f4,c1f5,c1f6,c1f7,
            c2f1, c2f2,c2f3,c2f4,c2f5,c2f6,c2f7,
            c3f1, c3f2,c3f3,c3f4,c3f5,c3f6,c3f7,
            c4f1, c4f2,c4f3,c4f4,c4f5,c4f6,c4f7,
            c5f1, c5f2,c5f3,c5f4,c5f5,c5f6,c5f7, codigo;
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_one_format_visit);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        codigo = findViewById(R.id.edit_text_codigo_visitas_tabla1);
        c1f1 = findViewById(R.id.edit_text_columna1_fila1_tabla1_visitas11);
        c1f2 = findViewById(R.id.edit_text_columna1_fila2_tabla1_visitas11);
        c1f3 = findViewById(R.id.edit_text_columna1_fila3_tabla1_visitas11);
        c1f4 = findViewById(R.id.edit_text_columna1_fila4_tabla1_visitas11);
        c1f5 = findViewById(R.id.edit_text_columna1_fila5_tabla1_visitas11);
        c1f6 = findViewById(R.id.edit_text_columna1_fila6_tabla1_visitas11);
        c1f7 = findViewById(R.id.edit_text_columna1_fila7_tabla1_visitas11);



        c2f1 = findViewById(R.id.edit_text_columna2_fila1_tabla1_visitas11);
        c2f2 = findViewById(R.id.edit_text_columna2_fila2_tabla1_visitas11);
        c2f3 = findViewById(R.id.edit_text_columna2_fila3_tabla1_visitas11);
        c2f4 = findViewById(R.id.edit_text_columna2_fila4_tabla1_visitas11);
        c2f5 = findViewById(R.id.edit_text_columna2_fila5_tabla1_visitas11);
        c2f6 = findViewById(R.id.edit_text_columna2_fila6_tabla1_visitas11);
        c2f7 = findViewById(R.id.edit_text_columna2_fila7_tabla1_visitas11);


        c3f1 = findViewById(R.id.edit_text_columna3_fila1_tabla1_visitas11);
        c3f2 = findViewById(R.id.edit_text_columna3_fila2_tabla1_visitas11);
        c3f3 = findViewById(R.id.edit_text_columna3_fila3_tabla1_visitas11);
        c3f4 = findViewById(R.id.edit_text_columna3_fila4_tabla1_visitas11);
        c3f5 = findViewById(R.id.edit_text_columna3_fila5_tabla1_visitas11);
        c3f6 = findViewById(R.id.edit_text_columna3_fila6_tabla1_visitas11);
        c3f7 = findViewById(R.id.edit_text_columna3_fila7_tabla1_visitas11);



        c4f1 = findViewById(R.id.edit_text_columna4_fila1_tabla1_visitas11);
        c4f2 = findViewById(R.id.edit_text_columna4_fila2_tabla1_visitas11);
        c4f3 = findViewById(R.id.edit_text_columna4_fila3_tabla1_visitas11);
        c4f4 = findViewById(R.id.edit_text_columna4_fila4_tabla1_visitas11);
        c4f5 = findViewById(R.id.edit_text_columna4_fila5_tabla1_visitas11);
        c4f6 = findViewById(R.id.edit_text_columna4_fila6_tabla1_visitas11);
        c4f7 = findViewById(R.id.edit_text_columna4_fila7_tabla1_visitas11);



        c5f1 = findViewById(R.id.edit_text_columna5_fila1_tabla1_visitas11);
        c5f2 = findViewById(R.id.edit_text_columna5_fila2_tabla1_visitas11);
        c5f3 = findViewById(R.id.edit_text_columna5_fila3_tabla1_visitas11);
        c5f4 = findViewById(R.id.edit_text_columna5_fila4_tabla1_visitas11);
        c5f5 = findViewById(R.id.edit_text_columna5_fila5_tabla1_visitas11);
        c5f6 = findViewById(R.id.edit_text_columna5_fila6_tabla1_visitas11);
        c5f7 = findViewById(R.id.edit_text_columna5_fila7_tabla1_visitas11);

        String codigoo = getIntent().getStringExtra("codigo_visitas");
        codigo.setText(codigoo);
        codigo.setEnabled(false);
        button_save_tabla1_visit = findViewById(R.id.button_save_tabla1_visit);
        button_save_tabla1_visit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder Bien = new AlertDialog.Builder(TableOneFormatVisitActivity.this);
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
                            Toast.makeText(TableOneFormatVisitActivity.this, "Permiso concedido, puedes continuar", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(TableOneFormatVisitActivity.this, "Permiso denegado, no puedes continuar.", Toast.LENGTH_SHORT).show();
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
        File file = new File(ruteFolder, "GVisitas1"+nameTeam+".csv");
        boolean isCreate = false;
        if(!ruteFolder.exists()){
            Toast.makeText(TableOneFormatVisitActivity.this, "No se encuentra el archivo...", Toast.LENGTH_SHORT).show();
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
                    AlertDialog.Builder Bien = new AlertDialog.Builder(TableOneFormatVisitActivity.this);
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
                Toast.makeText(TableOneFormatVisitActivity.this, "Error: probablemente la tabla no se han creado aún...", Toast.LENGTH_SHORT).show();
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
        File filecrate = new File(ruteFolder, "GVisitas1"+nameTeam+".csv");
        String nameFileInDrive = "GVisitas1"+nameTeam+".csv";
        try {
            filecrate.createNewFile();
            String[] infoCreate = {codigo.getText().toString(), c1f1.getText().toString(), c1f2.getText().toString(), c1f3.getText().toString(),
                    c1f4.getText().toString(), c1f5.getText().toString(), c1f6.getText().toString(), c1f7.getText().toString(),

                    c2f1.getText().toString(), c2f2.getText().toString(), c2f3.getText().toString(),
                    c2f4.getText().toString(), c2f5.getText().toString(), c2f6.getText().toString(), c2f7.getText().toString(),

                    c3f1.getText().toString(), c3f2.getText().toString(), c3f3.getText().toString(),
                    c3f4.getText().toString(), c3f5.getText().toString(), c3f6.getText().toString(), c3f7.getText().toString(),

                    c4f1.getText().toString(), c4f2.getText().toString(), c4f3.getText().toString(),
                    c4f4.getText().toString(), c4f5.getText().toString(), c4f6.getText().toString(), c1f7.getText().toString(),

                    c5f1.getText().toString(), c5f2.getText().toString(), c5f3.getText().toString(),
                    c5f4.getText().toString(), c5f5.getText().toString(), c5f6.getText().toString(), c1f7.getText().toString()};




            CSVWriter fileWriter = new CSVWriter(new OutputStreamWriter(new FileOutputStream(filecrate, true),
                    "ISO-8859-1"), ',',
                    CSVWriter.NO_QUOTE_CHARACTER,
                    CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                    CSVWriter.DEFAULT_LINE_END);

            fileWriter.writeNext(infoCreate);
            fileWriter.close();

            ProgressDialog progressDialog = new ProgressDialog(TableOneFormatVisitActivity.this);
            progressDialog.setTitle("Archivo creado, sincronizando con Google Drive.");
            progressDialog.setMessage("Por favor, espera.");
            progressDialog.show();


            try {
                driveServiceHelper.createFileDrive(nameFileInDrive, filecrate.getPath()).addOnSuccessListener(new OnSuccessListener<String>() {
                    @Override
                    public void onSuccess(String s) {
                        progressDialog.dismiss();
                        Toast.makeText(TableOneFormatVisitActivity.this, "El archivo ya está en Google Drive con tu cuenta de Google", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(TableOneFormatVisitActivity.this, "No se pudo subir el archivo a Google Drive", Toast.LENGTH_SHORT).show();
                    }
                });
            }catch (Exception e){
                progressDialog.dismiss();
                Toast.makeText(TableOneFormatVisitActivity.this, "Ocurrio un problema al subir el archivo a drive", Toast.LENGTH_SHORT).show();
            }





            Toast.makeText(TableOneFormatVisitActivity.this, "Datos agregados al archivo: " + filecrate.getPath(), Toast.LENGTH_LONG).show();
        }catch (IOException ie){

            Toast.makeText(TableOneFormatVisitActivity.this, "Ocurrio un problema al crear el archivo."+ ie, Toast.LENGTH_LONG).show();
        }


    }




    private void createFile(){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.R) {
            String app_name = getString(R.string.app_name);
            try {
                SharedPreferences preferencesId = getSharedPreferences("EquipoVeolia", Context.MODE_PRIVATE);
                final String nameTeam = preferencesId.getString("NombreEquipo","E1");
                File ruteFolder = new File(getFilesDir()+"/ArchivosGeneradosVeolia");
                if (!ruteFolder.exists()) {
                    ruteFolder.mkdirs();
                }
                File filecrate = new File(ruteFolder, "GVisitas1"+nameTeam+".csv");
                if(!filecrate.exists()){
                    filecrate.createNewFile();
                    String[] titles = {"Codigo",
                            //Columna 1
                            "C1 F1",
                            "C1 F2",
                            "C1 F3",
                            "C1 F4",
                            "C1 F5",
                            "C1 F6",
                            "C1 F7",

                            //Columna 2
                            "C2 F1",
                            "C2 F2",
                            "C2 F3",
                            "C2 F4",
                            "C2 F5",
                            "C2 F6",
                            "C2 F7",

                            //Columna 3
                            "C3 F1",
                            "C3 F2",
                            "C3 F3",
                            "C3 F4",
                            "C3 F5",
                            "C3 F6",
                            "C3 F7",

                            //Columna 4
                            "C4 F1",
                            "C4 F2",
                            "C4 F3",
                            "C4 F4",
                            "C4 F5",
                            "C4 F6",
                            "C4 F7",

                            //Columna 5
                            "C5 F1",
                            "C5 F2",
                            "C5 F3",
                            "C5 F4",
                            "C5 F5",
                            "C5 F6",
                            "C5 F7",
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
                    Toast.makeText(TableOneFormatVisitActivity.this, "Por favor, acepta los permisos para la aplicación "+ app_name, Toast.LENGTH_LONG).show();
                    finish();
                }catch (Exception e){
                    Intent intent = new Intent();
                    intent.setAction(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                    activityResultLauncher.launch(intent);
                    Toast.makeText(TableOneFormatVisitActivity.this, "Por favor, acepta los permisos para la aplicación "+ app_name, Toast.LENGTH_LONG).show();
                    finish();
                }
            }


        }else{
            int permissionResult = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if(permissionResult == PackageManager.PERMISSION_DENIED){
                AlertDialog.Builder Bien = new AlertDialog.Builder(TableOneFormatVisitActivity.this);
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
                File filecrate = new File(ruteFolder, "GVisitas1"+nameTeam+".csv");
                if(!filecrate.exists()){
                    try {
                        filecrate.createNewFile();
                        String[] titles = {"Codigo",
                                //Columna 1
                                "C1 F1",
                                "C1 F2",
                                "C1 F3",
                                "C1 F4",
                                "C1 F5",
                                "C1 F6",
                                "C1 F7",

                                //Columna 2
                                "C2 F1",
                                "C2 F2",
                                "C2 F3",
                                "C2 F4",
                                "C2 F5",
                                "C2 F6",
                                "C2 F7",

                                //Columna 3
                                "C3 F1",
                                "C3 F2",
                                "C3 F3",
                                "C3 F4",
                                "C3 F5",
                                "C3 F6",
                                "C3 F7",

                                //Columna 4
                                "C4 F1",
                                "C4 F2",
                                "C4 F3",
                                "C4 F4",
                                "C4 F5",
                                "C4 F6",
                                "C4 F7",

                                //Columna 5
                                "C5 F1",
                                "C5 F2",
                                "C5 F3",
                                "C5 F4",
                                "C5 F5",
                                "C5 F6",
                                "C5 F7",
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
                        GoogleAccountCredential credential = GoogleAccountCredential.usingOAuth2(TableOneFormatVisitActivity.this, Collections.singleton(DriveScopes.DRIVE_FILE));
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