package com.la.veolia;

import static org.apache.commons.lang3.ArrayUtils.contains;

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
import android.app.DatePickerDialog;
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
import android.widget.DatePicker;
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
import com.la.veolia.datepicker.DatePickerFragment;
import com.opencsv.CSVWriter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Arrays;
import java.util.Collections;

public class SampleLabelActivity extends AppCompatActivity {
    static final int DATE_ID = 0;
    DriveServiceHelper driveServiceHelper;
    EditText fecha_etiqueta, orden_etiqueta,
    nombre_etiqueta,edit_text_parametros_Etiqueta,
    hora_etiqueta, cual_tipo_etiqueta,cual_origen_etiqueta,
    cual_tratamiento_etiqueta, periodo_composicion_etiqueta,
    cual_parametros_etiqueta, verificacion_etiqueta1, verificacion_etiqueta2,
    verificacion_etiqueta3, municipio_etiqueta,sitio_etiqueta,direccion_etiqueta,
    ubicacion_etiqueta, codigo_etiqueta, observaciones_etiqueta, responsable_toma_etiqueta,
    responsable_entrega_etiqueta, recibido_laboratorio_etiqueta;
    Spinner responsable_toma_spinner,tipo_spinner, origen_spinner,tratamiento_spinner,
    tipo_toma_spinner, parametros_spinner, preservacion_spinner,preservacion_spinner_,preservacion_spinner__, direccion_spinner, analisis_spinner,
    transferencia_muestra_spinner;
    Button btn_save;
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
        setContentView(R.layout.activity_sample_label);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        fecha_etiqueta = findViewById(R.id.edit_text_Fecha_Etiqueta);
        orden_etiqueta = findViewById(R.id.edit_text_orden_Etiqueta);
        nombre_etiqueta = findViewById(R.id.edit_text_Nombre_muestra_Etiqueta);
        edit_text_parametros_Etiqueta = findViewById(R.id.edit_text_parametros_Etiqueta);
        hora_etiqueta = findViewById(R.id.edit_text_hora_etiqueta);
        cual_tipo_etiqueta = findViewById(R.id.edit_text_cual_tipo_Etiqueta);
        cual_origen_etiqueta = findViewById(R.id.edit_text_cual_origen_Etiqueta);
        cual_tratamiento_etiqueta = findViewById(R.id.edit_text_cual_tratamiento_Etiqueta);
        periodo_composicion_etiqueta = findViewById(R.id.edit_text_periodo_Etiqueta);
        cual_parametros_etiqueta = findViewById(R.id.edit_text_cual_parametros_Etiqueta);
        verificacion_etiqueta1 = findViewById(R.id.edit_text_verificacion_Etiqueta1);
        verificacion_etiqueta2 = findViewById(R.id.edit_text_verificacion_Etiqueta2);
        verificacion_etiqueta3 = findViewById(R.id.edit_text_verificacion_Etiqueta3);
        municipio_etiqueta = findViewById(R.id.edit_text_municipio_Etiqueta);
        sitio_etiqueta = findViewById(R.id.edit_text_sitio_Etiqueta);
        direccion_etiqueta = findViewById(R.id.edit_text_direccion_Etiqueta);
        ubicacion_etiqueta = findViewById(R.id.edit_text_ubicacion_Etiqueta);
        codigo_etiqueta = findViewById(R.id.edit_text_codigo_Etiqueta);
        observaciones_etiqueta = findViewById(R.id.edit_text_observaciones_Etiqueta);
        responsable_toma_etiqueta = findViewById(R.id.edit_text_responsable1_Etiqueta);
        responsable_entrega_etiqueta = findViewById(R.id.edit_text_responsable_Etiqueta);
        recibido_laboratorio_etiqueta = findViewById(R.id.edit_text_recibido_Etiqueta);
        responsable_toma_spinner = findViewById(R.id.spinner_responsanle_Etiqueta);
        tipo_spinner = findViewById(R.id.spinner_tipo_etiqueta);
        origen_spinner = findViewById(R.id.spinner_origen_Etiqueta);
        tratamiento_spinner = findViewById(R.id.spinner_tratamiento_etiqueta);
        tipo_toma_spinner = findViewById(R.id.spinner_tipo_toma_etiqueta);
        parametros_spinner = findViewById(R.id.spinner_parametros_etiqueta);
        preservacion_spinner = findViewById(R.id.spinner_tipo_preservacion);
        preservacion_spinner_ = findViewById(R.id.spinner_tipo_preservacion_);
        preservacion_spinner__= findViewById(R.id.spinner_tipo_preservacion__);
        direccion_spinner = findViewById(R.id.spinner_ubicacion_etiqueta);
        analisis_spinner =findViewById(R.id.spinner_tipo_analisis_etiqueta);
        transferencia_muestra_spinner = findViewById(R.id.spinner_realizo_etiqueta);
        btn_save = findViewById(R.id.btn_save_etiqueta);

        fecha_etiqueta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    showDatePickerDialog();
                }catch (Exception e){
                    Toast.makeText(SampleLabelActivity.this, ""+e, Toast.LENGTH_SHORT).show();
                }
            }
        });
        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder Bien = new AlertDialog.Builder(SampleLabelActivity.this);
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
                            Toast.makeText(SampleLabelActivity.this, "Permiso concedido, puedes continuar", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(SampleLabelActivity.this, "Permiso denegado, no puedes continuar.", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                }
            }
        });
        createFile();
    }

    private void showDatePickerDialog(){
        DatePickerFragment newFragment = DatePickerFragment.newIntance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                final String selectedDate = year+"/"+ (month+1) + "/" + day;
                fecha_etiqueta.setText(selectedDate);
            }
        });
        newFragment.show(SampleLabelActivity.this.getSupportFragmentManager(),"datePicker");
    }

    private void verifiFiles(){

    }

    public static boolean contains(String[] arreglo, String codigo){
        return Arrays.asList(arreglo).contains(codigo);
    }


    private void verifyCodeInFile(){
        File ruteFolder = new File(getFilesDir()+"/ArchivosGeneradosVeolia");
        File file = new File(ruteFolder, "AEtiquetaE1.csv");
        boolean isCreate = false;
        if(!ruteFolder.exists()){
            Toast.makeText(SampleLabelActivity.this, "No se encuentra el archivo...", Toast.LENGTH_SHORT).show();
        }else{
            String cadena;
            String[] arreglo = {};

            try{
                FileReader fileReader =  new FileReader(file);
                BufferedReader bufferedReader = new BufferedReader(fileReader);

                while((cadena = bufferedReader.readLine()) != null){
                    arreglo = cadena.split(",");
                }
                boolean value = contains(arreglo, codigo_etiqueta.getText().toString());
                if(value){
                    AlertDialog.Builder Bien = new AlertDialog.Builder(SampleLabelActivity.this);
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
                Toast.makeText(SampleLabelActivity.this, ""+e, Toast.LENGTH_SHORT).show();
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
            File filecrate = new File(ruteFolder, "AEtiqueta"+nameTeam+".csv");
            String nameFileInDrive = "AEtiqueta"+nameTeam+".csv";
            try {
                filecrate.createNewFile();
                String[] infoCreate = {codigo_etiqueta.getText().toString(),codigo_etiqueta.getText().toString(), fecha_etiqueta.getText().toString(),orden_etiqueta.getText().toString(),
                        nombre_etiqueta.getText().toString(), responsable_toma_spinner.getSelectedItem().toString(),
                        hora_etiqueta.getText().toString(),
                        tipo_spinner.getSelectedItem().toString(),cual_tipo_etiqueta.getText().toString(),origen_spinner.getSelectedItem().toString(),
                        cual_origen_etiqueta.getText().toString(), tratamiento_spinner.getSelectedItem().toString(),
                cual_tratamiento_etiqueta.getText().toString(), tipo_toma_spinner.getSelectedItem().toString(), periodo_composicion_etiqueta.getText().toString(),
                parametros_spinner.getSelectedItem().toString(),edit_text_parametros_Etiqueta.getText().toString(), cual_parametros_etiqueta.getText().toString(), preservacion_spinner.getSelectedItem().toString(),
                        preservacion_spinner_.getSelectedItem().toString(),preservacion_spinner__.getSelectedItem().toString(),
                verificacion_etiqueta1.getText().toString(),verificacion_etiqueta2.getText().toString(),verificacion_etiqueta3.getText().toString(),
                municipio_etiqueta.getText().toString(), sitio_etiqueta.getText().toString(), direccion_etiqueta.getText().toString(),
                direccion_spinner.getSelectedItem().toString(), ubicacion_etiqueta.getText().toString(), analisis_spinner.getSelectedItem().toString(),
                 observaciones_etiqueta.getText().toString(), responsable_toma_etiqueta.getText().toString(),transferencia_muestra_spinner.getSelectedItem().toString(),
                responsable_entrega_etiqueta.getText().toString(), recibido_laboratorio_etiqueta.getText().toString(),};





                CSVWriter fileWriter = new CSVWriter(new OutputStreamWriter(new FileOutputStream(filecrate, true),
                        "ISO-8859-1"), ',',
                        CSVWriter.NO_QUOTE_CHARACTER,
                        CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                        CSVWriter.DEFAULT_LINE_END);

                fileWriter.writeNext(infoCreate);
                fileWriter.close();

                ProgressDialog progressDialog = new ProgressDialog(SampleLabelActivity.this);
                progressDialog.setTitle("Archivo creado, sincronizando con Google Drive.");
                progressDialog.setMessage("Por favor, espera.");
                progressDialog.show();


                try {
                    driveServiceHelper.createFileDrive(nameFileInDrive, filecrate.getPath()).addOnSuccessListener(new OnSuccessListener<String>() {
                        @Override
                        public void onSuccess(String s) {
                            progressDialog.dismiss();
                            Toast.makeText(SampleLabelActivity.this, "El archivo ya está en Google Drive con tu cuenta de Google", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(SampleLabelActivity.this, "No se pudo subir el archivo a Google Drive", Toast.LENGTH_SHORT).show();
                        }
                    });
                }catch (Exception e){
                    progressDialog.dismiss();
                    Toast.makeText(SampleLabelActivity.this, "Ocurrio un problema al subir el archivo a drive", Toast.LENGTH_SHORT).show();
                }





                Toast.makeText(SampleLabelActivity.this, "Datos agregados al archivo: " + filecrate.getPath(), Toast.LENGTH_LONG).show();
            }catch (IOException ie){

                Toast.makeText(SampleLabelActivity.this, "Ocurrio un problema al crear el archivo."+ ie, Toast.LENGTH_LONG).show();
            }


    }


    private void createFile(){
        File ruteFolder = new File(getFilesDir()+"/ArchivosGeneradosVeolia");

        if (!ruteFolder.exists()) {
            ruteFolder.mkdirs();
        }
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.R) {
            String app_name = getString(R.string.app_name);
            try {

                SharedPreferences preferencesId = getSharedPreferences("EquipoVeolia", Context.MODE_PRIVATE);
                final String nameTeam = preferencesId.getString("NombreEquipo","E1");
                    File filecrate = new File(ruteFolder, "AEtiqueta"+nameTeam+".csv");
                if(!filecrate.exists()){
                    filecrate.createNewFile();
                    String[] titles = {"Codigo","Codigo","Fecha","Orden de etiqueta",
                            "Nombre muestra","Responsable","Hora","Tipo","Cual Tipo",
                            "Origen","Cual Origen","Tratamiento","Cual Tratamiento","Tipo Toma",
                            "Periodo","Parametros","Parametros2","Cual parametros","Tipo preservacion 1",
                            "Tipo preservacion 2","Tipo preservacion 3",
                            "Verificacion 1", "Verificacion 2", "Verificacion 3", "municipio",
                            "Sitio", "Direccion", "Ubicacion1", "Ubicacion2", "Tipo de analisis",
                            "Observaciones", "Responsable 1", "Realizo", "Responsable", "Recibido"};
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
                    Toast.makeText(SampleLabelActivity.this, "Por favor, acepta los permisos para la aplicación "+ app_name, Toast.LENGTH_LONG).show();
                    finish();
                }catch (Exception e){
                    Intent intent = new Intent();
                    intent.setAction(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                    activityResultLauncher.launch(intent);
                    Toast.makeText(SampleLabelActivity.this, "Por favor, acepta los permisos para la aplicación "+ app_name, Toast.LENGTH_LONG).show();
                    finish();
                }
            }


        }else{
            int permissionResult = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if(permissionResult == PackageManager.PERMISSION_DENIED){
                AlertDialog.Builder Bien = new AlertDialog.Builder(SampleLabelActivity.this);
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

                SharedPreferences preferencesId = getSharedPreferences("EquipoVeolia", Context.MODE_PRIVATE);
                final String nameTeam = preferencesId.getString("NombreEquipo","E1");
                File filecrate = new File(ruteFolder, "AEtiqueta"+nameTeam+".csv");
                if(!filecrate.exists()){
                    try {
                        filecrate.createNewFile();
                        String[] titles = {"Codigo","Codigo","Fecha","Orden de etiqueta",
                                "Nombre muestra","Responsable","Hora","Tipo","Cual Tipo",
                                "Origen","Cual Origen","Tratamiento","Cual Tratamiento","Tipo Toma",
                                "Periodo","Parametros","Parametros2","Cual parametros","Tipo preservacion",
                                "Tipo preservacion 2","Tipo preservacion 3",
                                "Verificacion 1", "Verificacion 2", "Verificacion 3", "municipio",
                                "Sitio", "Direccion", "Ubicacion1", "Ubicacion2", "Tipo de analisis",
                                "Observaciones", "Responsable 1", "Realizo", "Responsable", "Recibido"};
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
                        GoogleAccountCredential credential = GoogleAccountCredential.usingOAuth2(SampleLabelActivity.this, Collections.singleton(DriveScopes.DRIVE_FILE));
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