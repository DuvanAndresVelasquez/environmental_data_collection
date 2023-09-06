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

public class FormatVisitActivity extends AppCompatActivity {
    DriveServiceHelper driveServiceHelper;
    EditText codigo_visita, fecha_visita, hora_visita,numero_visita,razon_social_visita,
    nit_visita, ciudad_visita, direccion_visita, barrio_visita, telefono_visita, representante_visita,
    cc_visita, responsable_visita, cc_responsable_visita, cargo_visita, actividad_visita,
    autoridad_visita, numero_empleados_visita,  horas_dias_visita, turnos_visita, responsable1_visita,
    cc_responsable1_visita, atendio_visita, cc_atendio_visita, tipo_receptor_visita, nombre_receptor_visita,
    vertimientos_ndustriales_visita, vertimientos_muestrados_visita, caudal_recirculado_visita,
    diagrama_visita, observaciones_visita, realizado_por_visita, revisado_por_visita;
    Spinner recirculacion_visita;
    Button button_save_visita, table1, table2, table3, table4;

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
        setContentView(R.layout.activity_format_visit);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        codigo_visita = findViewById(R.id.edit_text_codigo_visitas);
        fecha_visita = findViewById(R.id.edit_text_fecha_visitas);
        hora_visita = findViewById(R.id.edit_text_hora_visitas);
        numero_visita = findViewById(R.id.edit_text_Numero_visitas);
        razon_social_visita = findViewById(R.id.edit_text_razo_social_visitas);
        nit_visita = findViewById(R.id.edit_text_NIT_visitas);
        ciudad_visita = findViewById(R.id.edit_text_ciudad_visitas);
        direccion_visita = findViewById(R.id.edit_text_direccion_visitas);
        barrio_visita = findViewById(R.id.edit_text_barrio_visitas);
        telefono_visita = findViewById(R.id.edit_text_telefono_visitas);
        representante_visita = findViewById(R.id.edit_text_representante_visitas);
        cc_visita = findViewById(R.id.edit_text_cedula_visitas);
        responsable_visita = findViewById(R.id.edit_text_responsable_visitas);
        cc_responsable_visita = findViewById(R.id.edit_text_cedula2_visitas);
        cargo_visita = findViewById(R.id.edit_text_cargo_visitas);
        actividad_visita = findViewById(R.id.edit_text_actividad_industrial_visitas);
        autoridad_visita = findViewById(R.id.edit_text_autoridad_ambiental_visitas);
        numero_empleados_visita = findViewById(R.id.edit_text_empleados_visitas);
        horas_dias_visita = findViewById(R.id.edit_text_horas_dias_visitas);
        turnos_visita = findViewById(R.id.edit_text_turnos_visitas);
        responsable1_visita = findViewById(R.id.edit_text_responsable_visita_visitas);
        cc_responsable1_visita = findViewById(R.id.edit_text_cedula3_visitas);
        atendio_visita = findViewById(R.id.edit_text_quien_atendio_visitas);
        cc_atendio_visita = findViewById(R.id.edit_text_cedula4_visitas);
        tipo_receptor_visita = findViewById(R.id.edit_text_tipo_receptor_visitas);
        nombre_receptor_visita = findViewById(R.id.edit_text_nombre_receptor_visitas);
        vertimientos_ndustriales_visita = findViewById(R.id.edit_text_numero_vertimientos_visitas);
        vertimientos_muestrados_visita = findViewById(R.id.edit_text_numero_vertimientos2_visitas);
        recirculacion_visita = findViewById(R.id.spinner_recirculacion_visitas);
        caudal_recirculado_visita = findViewById(R.id.edit_text_caudal_recirculado_visitas);
        diagrama_visita = findViewById(R.id.edit_text_diagrama_visitas);
        observaciones_visita = findViewById(R.id.edit_text_observaciones_visitas);
        realizado_por_visita = findViewById(R.id.edit_text_realizado_por_visitas);
        revisado_por_visita = findViewById(R.id.edit_text_revisado_por_visitas);
        button_save_visita = findViewById(R.id.button_save_visit);
        table1 = findViewById(R.id.table_1_button);
        table2 = findViewById(R.id.table_2_button);
        table3 = findViewById(R.id.table_3_button);
        table4 = findViewById(R.id.table_4_button);

        fecha_visita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    showDatePickerDialog();
                }catch (Exception e){
                    Toast.makeText(FormatVisitActivity.this, ""+e, Toast.LENGTH_SHORT).show();
                }
            }
        });
        table1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(codigo_visita.getText().toString().equals("")){
                    Toast.makeText(FormatVisitActivity.this, "Debes llenar el código", Toast.LENGTH_SHORT).show();
                }else {
                    Intent intenta = new Intent(FormatVisitActivity.this, TableOneFormatVisitActivity.class);
                    intenta.putExtra("codigo_visitas", codigo_visita.getText().toString());
                    startActivity(intenta);
                }
            }
        });
        table2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(codigo_visita.getText().toString().equals("")){
                    Toast.makeText(FormatVisitActivity.this, "Debes llenar el código", Toast.LENGTH_SHORT).show();
                }else {
                    Intent intenta = new Intent(FormatVisitActivity.this, TableTwoFormatVisitActivity.class);
                    intenta.putExtra("codigo_visitas", codigo_visita.getText().toString());
                    startActivity(intenta);
                }
            }
        });
        table3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(codigo_visita.getText().toString().equals("")){
                    Toast.makeText(FormatVisitActivity.this, "Debes llenar el código", Toast.LENGTH_SHORT).show();
                }else {
                    Intent intenta = new Intent(FormatVisitActivity.this, TableThreeFormatVisitActivity.class);
                    intenta.putExtra("codigo_visitas", codigo_visita.getText().toString());
                    startActivity(intenta);
                }
            }
        });
        table4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(codigo_visita.getText().toString().equals("")){
                    Toast.makeText(FormatVisitActivity.this, "Debes llenar el código", Toast.LENGTH_SHORT).show();
                }else {
                    Intent intenta = new Intent(FormatVisitActivity.this, TableFourFormatVisitActivity.class);
                    intenta.putExtra("codigo_visitas", codigo_visita.getText().toString());
                    startActivity(intenta);
                }
            }
        });
        button_save_visita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder Bien = new AlertDialog.Builder(FormatVisitActivity.this);
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
                            Toast.makeText(FormatVisitActivity.this, "Permiso concedido, puedes continuar", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(FormatVisitActivity.this, "Permiso denegado, no puedes continuar.", Toast.LENGTH_SHORT).show();
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

    private void showDatePickerDialog(){
        DatePickerFragment newFragment = DatePickerFragment.newIntance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                final String selectedDate = year+"/"+ (month+1) + "/" + day;
                fecha_visita.setText(selectedDate);
            }
        });
        newFragment.show(FormatVisitActivity.this.getSupportFragmentManager(),"datePicker");
    }
    private void verifyCodeInFile(){
        SharedPreferences preferencesId = getSharedPreferences("EquipoVeolia", Context.MODE_PRIVATE);
        final String nameTeam = preferencesId.getString("NombreEquipo","E1");
        File ruteFolder = new File(getFilesDir()+"/ArchivosGeneradosVeolia");
        File file = new File(ruteFolder, "GVisitas"+nameTeam+".csv");
        boolean isCreate = false;
        if(!file.exists()){
            Toast.makeText(FormatVisitActivity.this, "No se encuentra el archivo...", Toast.LENGTH_SHORT).show();
        }else{
            String cadena;
            String[] arreglo = {};

            try{
                FileReader fileReader =  new FileReader(file);
                BufferedReader bufferedReader = new BufferedReader(fileReader);

                while((cadena = bufferedReader.readLine()) != null){
                    arreglo = cadena.split(",");
                }
                boolean value = contains(arreglo, codigo_visita.getText().toString());
                if(value){
                    AlertDialog.Builder Bien = new AlertDialog.Builder(FormatVisitActivity.this);
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
                Toast.makeText(FormatVisitActivity.this, "Error: probablemente las tablas no se han creado aún...", Toast.LENGTH_SHORT).show();
            }

        }
    }





    private void validateTableOne(){
        SharedPreferences preferencesId = getSharedPreferences("EquipoVeolia", Context.MODE_PRIVATE);
        final String nameTeam = preferencesId.getString("NombreEquipo","E1");
        File ruteFolder = new File(getFilesDir()+"/ArchivosGeneradosVeolia");
        File file = new File(ruteFolder, "GVisitas1"+nameTeam+".csv");
        boolean isCreate = false;
        if(!file.exists()){
            AlertDialog.Builder Bien = new AlertDialog.Builder(FormatVisitActivity.this);
            Bien.setMessage("No se ha creado el archivo de la tabla uno, ingresa y registra los datos para el código asignado.")
                    .setCancelable(false)
                    .setPositiveButton("Entendido", new DialogInterface.OnClickListener() {
                        @RequiresApi(api = Build.VERSION_CODES.M)
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent TableOneChainCustody = new Intent(FormatVisitActivity.this, TableOneFormatVisitActivity.class);
                            TableOneChainCustody.putExtra("codigo_visitas", codigo_visita.getText().toString());
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
                boolean value = contains(arreglo, codigo_visita.getText().toString());
                if(value){
                    validateTableTwo();
                }else{
                    AlertDialog.Builder Bien = new AlertDialog.Builder(FormatVisitActivity.this);
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
                Toast.makeText(FormatVisitActivity.this, ""+e, Toast.LENGTH_SHORT).show();
            }

        }
    }



    private void validateTableTwo(){
        SharedPreferences preferencesId = getSharedPreferences("EquipoVeolia", Context.MODE_PRIVATE);
        final String nameTeam = preferencesId.getString("NombreEquipo","E1");
        File ruteFolder = new File(getFilesDir()+"/ArchivosGeneradosVeolia");
        File file = new File(ruteFolder, "GVisitas2"+nameTeam+".csv");
        boolean isCreate = false;
        if(!file.exists()){
            AlertDialog.Builder Bien = new AlertDialog.Builder(FormatVisitActivity.this);
            Bien.setMessage("No se ha creado el archivo de la tabla dos, ingresa y registra los datos para el código asignado.")
                    .setCancelable(false)
                    .setPositiveButton("Entendido", new DialogInterface.OnClickListener() {
                        @RequiresApi(api = Build.VERSION_CODES.M)
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent TableTwoChainCustody = new Intent(FormatVisitActivity.this, TableTwoFormatVisitActivity.class);
                            TableTwoChainCustody.putExtra("codigo_visitas", codigo_visita.getText().toString());
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
                boolean value = contains(arregloTD, codigo_visita.getText().toString());
                if(value){
                    validateTableThree();
                }else{
                    AlertDialog.Builder Bien = new AlertDialog.Builder(FormatVisitActivity.this);
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
                Toast.makeText(FormatVisitActivity.this, "Error: probablemente las tablas no se han creado aún...", Toast.LENGTH_LONG).show();
            }

        }
    }



    private void validateTableThree(){
        SharedPreferences preferencesId = getSharedPreferences("EquipoVeolia", Context.MODE_PRIVATE);
        final String nameTeam = preferencesId.getString("NombreEquipo","E1");
        File ruteFolder = new File(getFilesDir()+"/ArchivosGeneradosVeolia");
        File file = new File(ruteFolder, "GVisitas3"+nameTeam+".csv");
        boolean isCreate = false;
        if(!file.exists()){
            AlertDialog.Builder Bien = new AlertDialog.Builder(FormatVisitActivity.this);
            Bien.setMessage("No se ha creado el archivo de la tabla tres, ingresa y registra los datos para el código asignado.")
                    .setCancelable(false)
                    .setPositiveButton("Entendido", new DialogInterface.OnClickListener() {
                        @RequiresApi(api = Build.VERSION_CODES.M)
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent TableTwoChainCustody = new Intent(FormatVisitActivity.this, TableThreeFormatVisitActivity.class);
                            TableTwoChainCustody.putExtra("codigo_visitas", codigo_visita.getText().toString());
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
                boolean value = contains(arregloTD, codigo_visita.getText().toString());
                if(value){
                    validateTableFour();
                }else{
                    AlertDialog.Builder Bien = new AlertDialog.Builder(FormatVisitActivity.this);
                    Bien.setMessage("No has registrado los datos de la tabla 33, estos se deben crear para este código")
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
                Toast.makeText(FormatVisitActivity.this, "Error: probablemente las tablas no se han creado aún...", Toast.LENGTH_LONG).show();
            }

        }
    }



    private void validateTableFour(){
        SharedPreferences preferencesId = getSharedPreferences("EquipoVeolia", Context.MODE_PRIVATE);
        final String nameTeam = preferencesId.getString("NombreEquipo","E1");
        File ruteFolder = new File(getFilesDir()+"/ArchivosGeneradosVeolia");
        File file = new File(ruteFolder, "GVisitas4"+nameTeam+".csv");
        boolean isCreate = false;
        if(!file.exists()){
            AlertDialog.Builder Bien = new AlertDialog.Builder(FormatVisitActivity.this);
            Bien.setMessage("No se ha creado el archivo de la tabla 4, ingresa y registra los datos para el código asignado.")
                    .setCancelable(false)
                    .setPositiveButton("Entendido", new DialogInterface.OnClickListener() {
                        @RequiresApi(api = Build.VERSION_CODES.M)
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent TableTwoChainCustody = new Intent(FormatVisitActivity.this, TableFourFormatVisitActivity.class);
                            TableTwoChainCustody.putExtra("codigo_visitas", codigo_visita.getText().toString());
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
                boolean value = contains(arregloTD, codigo_visita.getText().toString());
                if(value){
                    verifyCodeInFile();
                }else{
                    AlertDialog.Builder Bien = new AlertDialog.Builder(FormatVisitActivity.this);
                    Bien.setMessage("No has registrado los datos de la tabla 4, estos se deben crear para este código")
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
                Toast.makeText(FormatVisitActivity.this, "Error: probablemente las tablas no se han creado aún...", Toast.LENGTH_LONG).show();
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
        File filecrate = new File(ruteFolder, "GVisitas"+nameTeam+".csv");
        String nameFileInDrive = "GVisitas"+nameTeam+".csv";
        try {
            filecrate.createNewFile();
            String[] infoCreate = {codigo_visita.getText().toString(),fecha_visita.getText().toString(),
                    hora_visita.getText().toString(),numero_visita.getText().toString(),
                    razon_social_visita.getText().toString(),direccion_visita.getText().toString(),
                    representante_visita.getText().toString(),responsable_visita.getText().toString(),
                    actividad_visita.getText().toString(),numero_empleados_visita.getText().toString(),
                    responsable1_visita.getText().toString(),atendio_visita.getText().toString(),
                    nit_visita.getText().toString(),barrio_visita.getText().toString(),
                    cc_visita.getText().toString(),cc_atendio_visita.getText().toString(),
                    autoridad_visita.getText().toString(), horas_dias_visita.getText().toString(),
                    cc_responsable1_visita.getText().toString(),cc_responsable_visita.getText().toString(),
                    ciudad_visita.getText().toString(),telefono_visita.getText().toString(),
                    cargo_visita.getText().toString(),turnos_visita.getText().toString(),
                    tipo_receptor_visita.getText().toString(),vertimientos_ndustriales_visita.getText().toString(),
                    recirculacion_visita.getSelectedItem().toString(),nombre_receptor_visita.getText().toString(),
                    vertimientos_muestrados_visita.getText().toString(),caudal_recirculado_visita.getText().toString(),
                    diagrama_visita.getText().toString(),observaciones_visita.getText().toString(),
                    realizado_por_visita.getText().toString(),revisado_por_visita.getText().toString(),
                    };





            CSVWriter fileWriter = new CSVWriter(new OutputStreamWriter(new FileOutputStream(filecrate, true),
                    "ISO-8859-1"), ',',
                    CSVWriter.NO_QUOTE_CHARACTER,
                    CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                    CSVWriter.DEFAULT_LINE_END);

            fileWriter.writeNext(infoCreate);
            fileWriter.close();

            ProgressDialog progressDialog = new ProgressDialog(FormatVisitActivity.this);
            progressDialog.setTitle("Archivo creado, sincronizando con Google Drive.");
            progressDialog.setMessage("Por favor, espera.");
            progressDialog.show();


            try {
                driveServiceHelper.createFileDrive(nameFileInDrive, filecrate.getPath()).addOnSuccessListener(new OnSuccessListener<String>() {
                    @Override
                    public void onSuccess(String s) {
                        progressDialog.dismiss();
                        Toast.makeText(FormatVisitActivity.this, "El archivo ya está en Google Drive con tu cuenta de Google", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(FormatVisitActivity.this, "No se pudo subir el archivo a Google Drive", Toast.LENGTH_SHORT).show();
                    }
                });
            }catch (Exception e){
                progressDialog.dismiss();
                Toast.makeText(FormatVisitActivity.this, "Ocurrio un problema al subir el archivo a drive", Toast.LENGTH_SHORT).show();
            }





            Toast.makeText(FormatVisitActivity.this, "Datos agregados al archivo: " + filecrate.getPath(), Toast.LENGTH_LONG).show();
        }catch (IOException ie){

            Toast.makeText(FormatVisitActivity.this, "Ocurrio un problema al crear el archivo."+ ie, Toast.LENGTH_LONG).show();
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
                    File filecrate = new File(ruteFolder, "GVisitas"+nameTeam+".csv");
                if(!filecrate.exists()){
                    filecrate.createNewFile();
                    String[] titles = {"Codigo", "Fecha", "Hora", "Numero", "Razon Social", "Direccion",
                            "representante", "Responsable", "Actividad industrial", "Empleados",
                            "Responsable visita", "Quien atendio", "Nit", "Barrio", "Cedula", "Cedula2",
                            "Autoridad ambiental", "Horas-dias", "Cedula3", "Cedula4", "Ciudad",
                            "Telefono", "Cargo", "Turnos", "Tipo receptor", "Numero Vertimientos",
                            "Recirculacion", "Nombre receptor", "Numero Vertimientos2", "Caudal recirculado",
                            "Diagrama", "Observaciones", "Realizado por", "Revisado por",};
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
                    Toast.makeText(FormatVisitActivity.this, "Por favor, acepta los permisos para la aplicación "+ app_name, Toast.LENGTH_LONG).show();
                    finish();
                }catch (Exception e){
                    Intent intent = new Intent();
                    intent.setAction(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                    activityResultLauncher.launch(intent);
                    Toast.makeText(FormatVisitActivity.this, "Por favor, acepta los permisos para la aplicación "+ app_name, Toast.LENGTH_LONG).show();
                    finish();
                }
            }


        }else{
            int permissionResult = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if(permissionResult == PackageManager.PERMISSION_DENIED){
                AlertDialog.Builder Bien = new AlertDialog.Builder(FormatVisitActivity.this);
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
                File filecrate = new File(ruteFolder, "GVisitas"+nameTeam+".csv");
                if(!filecrate.exists()){
                    try {
                        filecrate.createNewFile();
                        String[] titles = {"Codigo", "Fecha", "Hora", "Numero", "Razon Social", "Direccion",
                                "representante", "Responsable", "Actividad industrial", "Empleados",
                                "Responsable visita", "Quien atendio", "Nit", "Barrio", "Cedula", "Cedula2",
                                "Autoridad ambiental", "Horas-dias", "Cedula3", "Cedula4", "Ciudad",
                                "Telefono", "Cargo", "Turnos", "Tipo receptor", "Numero Vertimientos",
                                "Recirculacion", "Nombre receptor", "Numero Vertimientos2", "Caudal recirculado",
                                "Diagrama", "Observaciones", "Realizado por", "Revisado por",};
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
                        GoogleAccountCredential credential = GoogleAccountCredential.usingOAuth2(FormatVisitActivity.this, Collections.singleton(DriveScopes.DRIVE_FILE));
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