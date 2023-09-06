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

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;

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

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.http.FileContent;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class ChainCustodyActivity extends AppCompatActivity {

    DriveServiceHelper driveServiceHelper;
    EditText codigo_cadena, cliente, ciudad, orden_servicio, responsable,
    observaciones_generales_cadena, responable_entrega_cadena,
    responsable_recepcion_cadena, recipientes_cadena;
    Button button_save, table1_button_cadena, table2_button_cadena;
    Spinner entrega_cadena, sello_cadena;
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
        setContentView(R.layout.activity_chain_custody);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        codigo_cadena = findViewById(R.id.edit_text_codigo_Cadena);
        cliente = findViewById(R.id.edit_text_cliente);
        ciudad = findViewById(R.id.edit_text_ciudad);
        orden_servicio = findViewById(R.id.edit_text_orden_serv);
        responsable =findViewById(R.id.edit_text_responsable);
        observaciones_generales_cadena = findViewById(R.id.edit_text_Observaciones_generales_cadena);
        responable_entrega_cadena = findViewById(R.id.edit_text_responsable_entrega_cadena);
        responsable_recepcion_cadena = findViewById(R.id.edit_text_responsable_recepcion_cadena);
        recipientes_cadena = findViewById(R.id.edit_text_recipientes_cadena);
        entrega_cadena = findViewById(R.id.spinner_Entrega_Cadena);
        sello_cadena = findViewById(R.id.spinner_sello_cadena);
        table1_button_cadena = findViewById(R.id.table_1_button_cadena);
        table2_button_cadena = findViewById(R.id.table_2_button_cadena);
        button_save = findViewById(R.id.button_save);
        table1_button_cadena.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(codigo_cadena.getText().toString().equals("")){
                    Toast.makeText(ChainCustodyActivity.this, "Debes llenar el código", Toast.LENGTH_SHORT).show();
                }else {
                    Intent TableOneChainCustody = new Intent(ChainCustodyActivity.this, TableOneChainCustodyActivity.class);
                    TableOneChainCustody.putExtra("codigo_chain_custody", codigo_cadena.getText().toString());
                    startActivity(TableOneChainCustody);
                }
            }
        });
        table2_button_cadena.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(codigo_cadena.getText().toString().equals("")){
                    Toast.makeText(ChainCustodyActivity.this, "Debes llenar el código", Toast.LENGTH_SHORT).show();
                }else {
                    Intent TableTwoChainCustody = new Intent(ChainCustodyActivity.this, TableTwoChainCustodyActivity.class);
                    TableTwoChainCustody.putExtra("codigo_chain_custody", codigo_cadena.getText().toString());
                    startActivity(TableTwoChainCustody);
                }
            }
        });
        button_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder Bien = new AlertDialog.Builder(ChainCustodyActivity.this);
                Bien.setMessage("¿Seguro que desea guardar estos registros? (Debe entender que el código no se podrá repetir en el archivo.)")
                        .setCancelable(false)
                        .setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
                            @RequiresApi(api = Build.VERSION_CODES.M)
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                validateTableOne();
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
                            Toast.makeText(ChainCustodyActivity.this, "Permiso concedido, puedes continuar", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(ChainCustodyActivity.this, "Permiso denegado, no puedes continuar.", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                }
            }
        });
        createFile();
    }

    private void validateTableOne(){
        SharedPreferences preferencesId = getSharedPreferences("EquipoVeolia", Context.MODE_PRIVATE);
        final String nameTeam = preferencesId.getString("NombreEquipo","E1");
        File ruteFolder = new File(getFilesDir()+"/ArchivosGeneradosVeolia");
        File file = new File(ruteFolder, "BCadena1"+nameTeam+".csv");
        boolean isCreate = false;
        if(!file.exists()){
            AlertDialog.Builder Bien = new AlertDialog.Builder(ChainCustodyActivity.this);
            Bien.setMessage("No se ha creado el archivo de la tabla uno, ingresa y registra los datos para el código asignado.")
                    .setCancelable(false)
                    .setPositiveButton("Entendido", new DialogInterface.OnClickListener() {
                        @RequiresApi(api = Build.VERSION_CODES.M)
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent TableOneChainCustody = new Intent(ChainCustodyActivity.this, TableOneChainCustodyActivity.class);
                            TableOneChainCustody.putExtra("codigo_chain_custody", codigo_cadena.getText().toString());
                            startActivity(TableOneChainCustody);
                        }
                    });
            AlertDialog Titulo = Bien.create();
            Titulo.setTitle("Faltan archivos");
            Titulo.show();
        }else{
            String cadena;
            String[] arreglo = {};

            try{
                FileReader fileReader =  new FileReader(file);
                BufferedReader bufferedReader = new BufferedReader(fileReader);

                while((cadena = bufferedReader.readLine()) != null){
                    arreglo = cadena.split(",");
                }
                boolean value = contains(arreglo, codigo_cadena.getText().toString());
                if(value){
                    validateTableTwo();
                }else{
                    AlertDialog.Builder Bien = new AlertDialog.Builder(ChainCustodyActivity.this);
                    Bien.setMessage("No has registrado los datos de la tabla 1, estos se deben crear para este código")
                            .setCancelable(false)
                            .setPositiveButton("Entendido", new DialogInterface.OnClickListener() {
                                @RequiresApi(api = Build.VERSION_CODES.M)
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            });
                    AlertDialog Titulo = Bien.create();
                    Titulo.setTitle("Faltan archivos");
                    Titulo.show();
                }

            }catch (Exception e){
                Toast.makeText(ChainCustodyActivity.this, ""+e, Toast.LENGTH_SHORT).show();
            }

        }
    }



    private void validateTableTwo(){
        SharedPreferences preferencesId = getSharedPreferences("EquipoVeolia", Context.MODE_PRIVATE);
        final String nameTeam = preferencesId.getString("NombreEquipo","E1");
        File ruteFolder = new File(getFilesDir()+"/ArchivosGeneradosVeolia");
        File file = new File(ruteFolder, "BCadena2"+nameTeam+".csv");
        boolean isCreate = false;
        if(!file.exists()){
            AlertDialog.Builder Bien = new AlertDialog.Builder(ChainCustodyActivity.this);
            Bien.setMessage("No se ha creado el archivo de la tabla dos, ingresa y registra los datos para el código asignado.")
                    .setCancelable(false)
                    .setPositiveButton("Entendido", new DialogInterface.OnClickListener() {
                        @RequiresApi(api = Build.VERSION_CODES.M)
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent TableTwoChainCustody = new Intent(ChainCustodyActivity.this, TableTwoChainCustodyActivity.class);
                            TableTwoChainCustody.putExtra("codigo_chain_custody", codigo_cadena.getText().toString());
                            startActivity(TableTwoChainCustody);
                        }
                    });
            AlertDialog Titulo = Bien.create();
            Titulo.setTitle("Faltan archivos");
            Titulo.show();
        }else{
            String cadena;
            String[] arregloTD = {};

            try{
                FileReader fileReader =  new FileReader(file);
                BufferedReader bufferedReader = new BufferedReader(fileReader);

                while((cadena = bufferedReader.readLine()) != null){
                    arregloTD = cadena.split(",");
                }
                boolean value = contains(arregloTD, codigo_cadena.getText().toString());
                if(value){
                    verifyCodeInFile();
                }else{
                    AlertDialog.Builder Bien = new AlertDialog.Builder(ChainCustodyActivity.this);
                    Bien.setMessage("No has registrado los datos de la tabla 2, estos se deben crear para este código")
                            .setCancelable(false)
                            .setPositiveButton("Entendido", new DialogInterface.OnClickListener() {
                                @RequiresApi(api = Build.VERSION_CODES.M)
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            });
                    AlertDialog Titulo = Bien.create();
                    Titulo.setTitle("Faltan archivos");
                    Titulo.show();
                }

            }catch (Exception e){
                Toast.makeText(ChainCustodyActivity.this, "Error: probablemente las tablas no se han creado aún...", Toast.LENGTH_LONG).show();
            }

        }
    }




    public static boolean contains(String[] arreglo, String codigo){
        return Arrays.asList(arreglo).contains(codigo);
    }


    private void verifyCodeInFile(){
        SharedPreferences preferencesId = getSharedPreferences("EquipoVeolia", Context.MODE_PRIVATE);
        final String nameTeam = preferencesId.getString("NombreEquipo","E1");
        File ruteFolder = new File(getFilesDir()+"/ArchivosGeneradosVeolia");
        File file = new File(ruteFolder, "BCadena"+nameTeam+".csv");
        boolean isCreate = false;
        if(!file.exists()){
            Toast.makeText(ChainCustodyActivity.this, "No se encuentra el archivo...", Toast.LENGTH_SHORT).show();
        }else{
            String cadena;
            String[] arreglo = {};

            try{
                FileReader fileReader =  new FileReader(file);
                BufferedReader bufferedReader = new BufferedReader(fileReader);

                while((cadena = bufferedReader.readLine()) != null){
                    arreglo = cadena.split(",");
                }
                boolean value = contains(arreglo, codigo_cadena.getText().toString());
                if(value){
                    AlertDialog.Builder Bien = new AlertDialog.Builder(ChainCustodyActivity.this);
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
                Toast.makeText(ChainCustodyActivity.this, "Error: probablemente las tablas no se han creado aún...", Toast.LENGTH_SHORT).show();
            }

        }
    }





    private void saveFile(){
        if(!codigo_cadena.equals("") && !cliente.equals("") && !ciudad.equals("") && !orden_servicio.equals("") &&
         !responsable.equals("") && !observaciones_generales_cadena.equals("") && !responable_entrega_cadena.equals("") &&
          !responsable_recepcion_cadena.equals("") && !recipientes_cadena.equals("")){
            File ruteFolder = new File(getFilesDir()+"/ArchivosGeneradosVeolia");
            if (!ruteFolder.exists()) {
                createFile();
            }
            SharedPreferences preferencesId = getSharedPreferences("EquipoVeolia", Context.MODE_PRIVATE);
            final String nameTeam = preferencesId.getString("NombreEquipo","E1");
            File filecrate = new File(ruteFolder, "BCadena"+nameTeam+".csv");
            String nameFileInDrive = "BCadena"+nameTeam+".csv";
            try {
                filecrate.createNewFile();
                String[] infoCreate = {codigo_cadena.getText().toString(), cliente.getText().toString(),ciudad.getText().toString(),
                        orden_servicio.getText().toString(), responsable.getText().toString(),
                        observaciones_generales_cadena.getText().toString(),entrega_cadena.getSelectedItem().toString(),
                        sello_cadena.getSelectedItem().toString(),responable_entrega_cadena.getText().toString(),responsable_recepcion_cadena.getText().toString(),
                        recipientes_cadena.getText().toString()};
                CSVWriter fileWriter = new CSVWriter(new OutputStreamWriter(new FileOutputStream(filecrate, true),
                        "ISO-8859-1"), ',',
                        CSVWriter.NO_QUOTE_CHARACTER,
                        CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                        CSVWriter.DEFAULT_LINE_END);

                fileWriter.writeNext(infoCreate);
                fileWriter.close();

                ProgressDialog progressDialog = new ProgressDialog(ChainCustodyActivity.this);
                progressDialog.setTitle("Archivo creado, sincronizando con Google Drive.");
                progressDialog.setMessage("Por favor, espera.");
                progressDialog.show();


                try {
                    driveServiceHelper.createFileDrive(nameFileInDrive, filecrate.getPath()).addOnSuccessListener(new OnSuccessListener<String>() {
                        @Override
                        public void onSuccess(String s) {
                            progressDialog.dismiss();
                            Toast.makeText(ChainCustodyActivity.this, "El archivo ya está en Google Drive con tu cuenta de Google", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(ChainCustodyActivity.this, "No se pudo subir el archivo a Google Drive", Toast.LENGTH_SHORT).show();
                        }
                    });
                }catch (Exception e){
                    progressDialog.dismiss();
                    Toast.makeText(ChainCustodyActivity.this, "Ocurrio un problema al subir el archivo a drive", Toast.LENGTH_SHORT).show();
                }





                Toast.makeText(ChainCustodyActivity.this, "Datos agregados al archivo: " + filecrate.getPath(), Toast.LENGTH_LONG).show();
            }catch (IOException ie){

                Toast.makeText(ChainCustodyActivity.this, "Ocurrio un problema al crear el archivo."+ ie, Toast.LENGTH_LONG).show();
            }

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
                            File filecrate = new File(ruteFolder, "BCadena"+nameTeam+".csv");
                        if(!filecrate.exists()){
                            filecrate.createNewFile();
                            String[] titles = {"Código", "Cliente", "Ciudad", "Orden de servicio", "Responsable",
                                    "Observaciones", "Entrega", "Sello", "Responsable Entrega", "Responsable Recepción",
                                    "Recipientes"};
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
                            Toast.makeText(ChainCustodyActivity.this, "Por favor, acepta los permisos para la aplicación "+ app_name, Toast.LENGTH_LONG).show();
                            finish();
                        }catch (Exception e){
                            Intent intent = new Intent();
                            intent.setAction(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                            activityResultLauncher.launch(intent);
                            Toast.makeText(ChainCustodyActivity.this, "Por favor, acepta los permisos para la aplicación "+ app_name, Toast.LENGTH_LONG).show();
                            finish();
                        }
                         }


        }else{
            int permissionResult = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if(permissionResult == PackageManager.PERMISSION_DENIED){
                AlertDialog.Builder Bien = new AlertDialog.Builder(ChainCustodyActivity.this);
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
                File filecrate = new File(ruteFolder, "BCadena"+nameTeam+".csv");
                if(!filecrate.exists()){
                    try {
                        filecrate.createNewFile();
                        String[] titles = {"Código", "Cliente", "Ciudad", "Orden de servicio", "Responsable",
                                "Observaciones", "Entrega", "Sello", "Responsable Entrega", "Responsable Recepción",
                                "Recipientes"};
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
                        GoogleAccountCredential credential = GoogleAccountCredential.usingOAuth2(ChainCustodyActivity.this, Collections.singleton(DriveScopes.DRIVE_FILE));
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