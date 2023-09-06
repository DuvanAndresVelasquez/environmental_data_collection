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

public class SamplePlanActivity extends AppCompatActivity {
    DriveServiceHelper driveServiceHelper;
    int REQUEST_CODE = 200;
    ActivityResultLauncher<Intent> activityResultLauncher;
    EditText codigo_plan, fecha_plan, numero_plan, cliente_plan, direccion_plan,
    contacto_plan, actividad_productiva_plan, usos_plan, jornada_plan, puntos_plan,
    ubicacion1_plan, tipo_descara1_plan, ubicacion2_plan, tipo_descara2_plan,
    requerimientos_plan, matriz_plan, tipo_muestra_plan, frecuencia_plan,parametros_plan,
    lista_materiales_plan, tecnicas_preservacion_plan, personal_tecnico_plan, personal_administrativo_plan,
    personal_toma_plan, fecha_probable_plan, hora_inicio_plan, fecha_ejecucion_plan,
    representante_cliente_plan, cargo_plan, responsable_toma_plan, observaciones_plan, realizado_plan,
    revisado_plan;
    Button button_save_plan, Plan_Firma1;
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample_plan);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        codigo_plan = findViewById(R.id.edit_text_codigo_plan);
        fecha_plan = findViewById(R.id.edit_text_fecha_plan);
        numero_plan = findViewById(R.id.edit_text_numero_plan);
        cliente_plan = findViewById(R.id.edit_text_cliente_plan);
        direccion_plan = findViewById(R.id.edit_text_direccion_plan);
        contacto_plan = findViewById(R.id.edit_text_contacto_plan);
        actividad_productiva_plan = findViewById(R.id.edit_text_actividad_plan);
        usos_plan = findViewById(R.id.edit_text_usos_plan);
        jornada_plan = findViewById(R.id.edit_text_jornada_plan);
        puntos_plan = findViewById(R.id.edit_text_puntos_plan);
        ubicacion1_plan = findViewById(R.id.edit_text_ubicacion_plan);
        tipo_descara1_plan = findViewById(R.id.edit_text_descarga_plan);
        ubicacion2_plan = findViewById(R.id.edit_text_ubicacion2_plan);
        tipo_descara2_plan = findViewById(R.id.edit_text_descarga2_plan);
        requerimientos_plan = findViewById(R.id.edit_text_requerimientos_plan);
        matriz_plan = findViewById(R.id.edit_text_matriz_plan);
        Plan_Firma1 = findViewById(R.id.Plan_Firma1);
        tipo_muestra_plan = findViewById(R.id.edit_text_tipo_muestra_plan);
        frecuencia_plan = findViewById(R.id.edit_text_frecuencia_plan);
        parametros_plan = findViewById(R.id.edit_text_parametros_plan);
        lista_materiales_plan = findViewById(R.id.edit_text_materiales_plan);
        tecnicas_preservacion_plan = findViewById(R.id.edit_text_tecnicas_plan);
        personal_tecnico_plan = findViewById(R.id.edit_text_personal_plan);
        personal_administrativo_plan = findViewById(R.id.edit_text_administrativo_plan);
        personal_toma_plan = findViewById(R.id.edit_text_personal_toma_plan);
        fecha_probable_plan = findViewById(R.id.edit_text_fecha_probable_plan);
        hora_inicio_plan = findViewById(R.id.edit_text_hora_inicio_plan);
        fecha_ejecucion_plan = findViewById(R.id.edit_text_fecha_ejecucion_plan);
        representante_cliente_plan = findViewById(R.id.edit_text_representante_plan);
        cargo_plan = findViewById(R.id.edit_text_cargo_plan);
        responsable_toma_plan = findViewById(R.id.edit_text_responsable_toma_plan);
        observaciones_plan = findViewById(R.id.edit_text_observaciones_plan);
        realizado_plan = findViewById(R.id.edit_text_realizado_por_plan);
        revisado_plan = findViewById(R.id.edit_text_revisado_por_plan);
        button_save_plan = findViewById(R.id.button_save_plan);
        fecha_ejecucion_plan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    showDatePickerDialogTwo();
                }catch (Exception e){
                    Toast.makeText(SamplePlanActivity.this, ""+e, Toast.LENGTH_SHORT).show();
                }
            }
        });
        fecha_probable_plan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    showDatePickerDialogThree();
                }catch (Exception e){
                    Toast.makeText(SamplePlanActivity.this, ""+e, Toast.LENGTH_SHORT).show();
                }
            }
        });
        fecha_plan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    showDatePickerDialog();
                }catch (Exception e){
                    Toast.makeText(SamplePlanActivity.this, ""+e, Toast.LENGTH_SHORT).show();
                }
            }
        });
        Plan_Firma1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(codigo_plan.getText().toString().isEmpty()){
                    Toast.makeText(SamplePlanActivity.this, "Primero debes ingresar el código.", Toast.LENGTH_LONG).show();
                }else{
                    Intent intent = new Intent(SamplePlanActivity.this, PlanFirmaActivity.class);
                    intent.putExtra("codigo_plan_muestreo", codigo_plan.getText().toString());
                    startActivity(intent);
                }

            }
        });
        button_save_plan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder Bien = new AlertDialog.Builder(SamplePlanActivity.this);
                Bien.setMessage("¿Seguro que desea guardar estos registros? (Debe entender que el código no se podrá repetir en el archivo.)")
                        .setCancelable(false)
                        .setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
                            @RequiresApi(api = Build.VERSION_CODES.M)
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                validateFirma();
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
                            Toast.makeText(SamplePlanActivity.this, "Permiso concedido, puedes continuar", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(SamplePlanActivity.this, "Permiso denegado, no puedes continuar.", Toast.LENGTH_SHORT).show();
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
                fecha_plan.setText(selectedDate);
            }
        });
        newFragment.show(SamplePlanActivity.this.getSupportFragmentManager(),"datePicker");
    }
    private void showDatePickerDialogTwo(){
        DatePickerFragment newFragment = DatePickerFragment.newIntance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                final String selectedDate = year+"/"+ (month+1) + "/" + day;
                fecha_ejecucion_plan.setText(selectedDate);
            }
        });
        newFragment.show(SamplePlanActivity.this.getSupportFragmentManager(),"datePicker");
    }

    private void showDatePickerDialogThree(){
        DatePickerFragment newFragment = DatePickerFragment.newIntance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                final String selectedDate = year+"/"+ (month+1) + "/" + day;
                fecha_probable_plan.setText(selectedDate);
            }
        });
        newFragment.show(SamplePlanActivity.this.getSupportFragmentManager(),"datePicker");
    }

    private void validateFirma(){
        File ruteFolder = new File(getFilesDir()+"/ArchivosGeneradosVeolia");
        File file = new File(ruteFolder, codigo_plan.getText().toString()+".png");
        boolean isCreate = false;
        if(!file.exists()){
            AlertDialog.Builder Bien = new AlertDialog.Builder(SamplePlanActivity.this);
            Bien.setMessage("Parece que no se ha guardado la firma para este código; ingresa y realiza la firma para poder guardar los datos.")
                    .setCancelable(false)
                    .setPositiveButton("Entendido", new DialogInterface.OnClickListener() {
                        @RequiresApi(api = Build.VERSION_CODES.M)
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent intent = new Intent(SamplePlanActivity.this, PlanFirmaActivity.class);
                            intent.putExtra("codigo_plan_muestreo", codigo_plan.getText().toString());
                            startActivity(intent);
                        }
                    });
            AlertDialog Titulo = Bien.create();
            Titulo.setTitle("Faltan archivos");
            Titulo.show();
        }else{
            verifyCodeInFile();
        }
    }



    public static boolean contains(String[] arreglo, String codigo){
        return Arrays.asList(arreglo).contains(codigo);
    }


    private void verifyCodeInFile(){
        SharedPreferences preferencesId = getSharedPreferences("EquipoVeolia", Context.MODE_PRIVATE);
        final String nameTeam = preferencesId.getString("NombreEquipo","E1");
        File ruteFolder = new File(getFilesDir()+"/ArchivosGeneradosVeolia");
        File file = new File(ruteFolder, "CPlan"+nameTeam+".csv");
        boolean isCreate = false;
        if(!ruteFolder.exists()){
            Toast.makeText(SamplePlanActivity.this, "No se encuentra el archivo...", Toast.LENGTH_SHORT).show();
        }else{
            String cadena;
            String[] arreglo = {};

            try{
                FileReader fileReader =  new FileReader(file);
                BufferedReader bufferedReader = new BufferedReader(fileReader);

                while((cadena = bufferedReader.readLine()) != null){
                    arreglo = cadena.split(",");
                }
                boolean value = contains(arreglo, codigo_plan.getText().toString());
                if(value){
                    AlertDialog.Builder Bien = new AlertDialog.Builder(SamplePlanActivity.this);
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
                Toast.makeText(SamplePlanActivity.this, "Error: probablemente las tablas no se han creado aún...", Toast.LENGTH_SHORT).show();
            }

        }
    }







    //En este método se guardará el archivo basado en cada formulario...
    private void saveFile(){
        File ruteFolder = new File(getFilesDir()+"/ArchivosGeneradosVeolia");
        if (!ruteFolder.exists()) {
            createFile();
        }
        SharedPreferences preferencesId = getSharedPreferences("EquipoVeolia", Context.MODE_PRIVATE);
        final String nameTeam = preferencesId.getString("NombreEquipo","E1");
        File filecrate = new File(ruteFolder, "CPlan"+nameTeam+".csv");
        String nameFileInDrive = "CPlan"+nameTeam+".csv";
        try {
            filecrate.createNewFile();
            String[] infoCreate = {codigo_plan.getText().toString(),fecha_plan.getText().toString(),numero_plan.getText().toString(),
                    cliente_plan.getText().toString(),direccion_plan.getText().toString(),contacto_plan.getText().toString(),
                    actividad_productiva_plan.getText().toString(),usos_plan.getText().toString(),jornada_plan.getText().toString(),
                    puntos_plan.getText().toString(),ubicacion1_plan.getText().toString(),tipo_descara1_plan.getText().toString(),
                    ubicacion2_plan.getText().toString(),tipo_descara2_plan.getText().toString(),requerimientos_plan.getText().toString(),
                    matriz_plan.getText().toString(),tipo_muestra_plan.getText().toString(),frecuencia_plan.getText().toString(),
                    parametros_plan.getText().toString(),lista_materiales_plan.getText().toString(),tecnicas_preservacion_plan.getText().toString(),
                    personal_tecnico_plan.getText().toString(),personal_administrativo_plan.getText().toString(),personal_toma_plan.getText().toString(),
                    fecha_probable_plan.getText().toString(),hora_inicio_plan.getText().toString(),fecha_ejecucion_plan.getText().toString(),
                    representante_cliente_plan.getText().toString(),
                    cargo_plan.getText().toString(),responsable_toma_plan.getText().toString(),observaciones_plan.getText().toString(),
                    realizado_plan.getText().toString(),revisado_plan.getText().toString(),};

            CSVWriter fileWriter = new CSVWriter(new OutputStreamWriter(new FileOutputStream(filecrate, true),
                    "ISO-8859-1"), ',',
                    CSVWriter.NO_QUOTE_CHARACTER,
                    CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                    CSVWriter.DEFAULT_LINE_END);

            fileWriter.writeNext(infoCreate);
            fileWriter.close();

            ProgressDialog progressDialog = new ProgressDialog(SamplePlanActivity.this);
            progressDialog.setTitle("Archivo creado, sincronizando con Google Drive.");
            progressDialog.setMessage("Por favor, espera.");
            progressDialog.show();


            try {
                driveServiceHelper.createFileDrive(nameFileInDrive, filecrate.getPath()).addOnSuccessListener(new OnSuccessListener<String>() {
                    @Override
                    public void onSuccess(String s) {
                        progressDialog.dismiss();
                        Toast.makeText(SamplePlanActivity.this, "El archivo ya está en Google Drive con tu cuenta de Google", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(SamplePlanActivity.this, "No se pudo subir el archivo a Google Drive", Toast.LENGTH_SHORT).show();
                    }
                });
            }catch (Exception e){
                progressDialog.dismiss();
                Toast.makeText(SamplePlanActivity.this, "Ocurrio un problema al subir el archivo a drive", Toast.LENGTH_SHORT).show();
            }





            Toast.makeText(SamplePlanActivity.this, "Datos agregados al archivo: " + filecrate.getPath(), Toast.LENGTH_LONG).show();
        }catch (IOException ie){

            Toast.makeText(SamplePlanActivity.this, "Ocurrio un problema al crear el archivo."+ ie, Toast.LENGTH_LONG).show();
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
                    File filecrate = new File(ruteFolder, "CPlan"+nameTeam+".csv");
                if(!filecrate.exists()){
                    filecrate.createNewFile();
                    String[] titles = {"Codigo", "fecha", "número", "cliente", "Dirección",
                            "Contacto", "Actividad", "Usos", "Jornada", "Puntos", "Ubicacion", "Descarga",
                            "Ubicacion2", "Descarga2", "Requerimientos", "matríz", "Tipo Muestra", "Frecuencía","Parametros","Materiales", "Técnicas", "Personal", "Administrativo", "Personal Toma",
                            "Fecha Probable", "Hora de inicio", "Fecha ejecucion", "Representante", "Cargo",
                            "Responsable toma", "Observaciones", "Realizado por", "Revisado por"};
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
                    Toast.makeText(SamplePlanActivity.this, "Por favor, acepta los permisos para la aplicación "+ app_name, Toast.LENGTH_LONG).show();
                    finish();
                }catch (Exception e){
                    Intent intent = new Intent();
                    intent.setAction(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                    activityResultLauncher.launch(intent);
                    Toast.makeText(SamplePlanActivity.this, "Por favor, acepta los permisos para la aplicación "+ app_name, Toast.LENGTH_LONG).show();
                    finish();
                }
            }


        }else{
            int permissionResult = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if(permissionResult == PackageManager.PERMISSION_DENIED){
                AlertDialog.Builder Bien = new AlertDialog.Builder(SamplePlanActivity.this);
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
                File filecrate = new File(ruteFolder, "CPlan"+nameTeam+".csv");
                if(!filecrate.exists()){
                    try {
                        filecrate.createNewFile();
                        String[] titles = {"Codigo", "fecha", "numero", "cliente", "Direccion",
                                "Contacto", "Actividad", "Usos", "Jornada", "Puntos", "Ubicacion", "Descarga",
                                "Ubicacion2", "Descarga2", "Requerimientos", "matriz", "Tipo Muestra", "Frecuencia",
                                "Parametros","Materiales", "Tecnicas", "Personal", "Administrativo", "Personal Toma",
                                "Fecha Probable", "Hora de inicio", "Fecha ejecucion", "Representante", "Cargo",
                                "Responsable toma", "Observaciones", "Realizado por", "Revisado por"};
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
                        GoogleAccountCredential credential = GoogleAccountCredential.usingOAuth2(SamplePlanActivity.this, Collections.singleton(DriveScopes.DRIVE_FILE));
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