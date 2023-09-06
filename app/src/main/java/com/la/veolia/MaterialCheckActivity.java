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

public class MaterialCheckActivity extends AppCompatActivity {
    DriveServiceHelper driveServiceHelper;

    EditText codigo_chequeo, numero_chequeo, elaboracion_chequeo, responsable1_chequeo,
    revision_salida_chequeo, responsable2_chequeo, revision_llegada_chequeo, responsable3_chequeo,
    cliente_chequeo, ciudad_chequeo, orden_servicio_chequeo, numero_puntos_chequeo,
    numero_personal_chequeo, personal_asignado1_chequeo, personal_asignado2_chequeo,
            personal_asignado3_chequeo, observaciones_chequeo, jefe_laboratorio_chequeo;
    Button button_save_material ,t1,t2,t3,t4,t5;
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
        setContentView(R.layout.activity_material_check);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        codigo_chequeo = findViewById(R.id.edit_text_codigo_chequeo);
        numero_chequeo = findViewById(R.id.edit_text_numero_chequeo);
        elaboracion_chequeo = findViewById(R.id.edit_text_elaboracion_material);
        responsable1_chequeo = findViewById(R.id.edit_text_responsable1_material);
        revision_salida_chequeo = findViewById(R.id.edit_text_revision_salida_material);
        responsable2_chequeo = findViewById(R.id.edit_text_responsable2_material);
        revision_llegada_chequeo = findViewById(R.id.edit_text_revision_llegada_material);
        responsable3_chequeo = findViewById(R.id.edit_text_responsable3_material);
        cliente_chequeo = findViewById(R.id.edit_text_cliente_material);
        ciudad_chequeo = findViewById(R.id.edit_text_ciudad_material);
        orden_servicio_chequeo = findViewById(R.id.edit_text_orden_material);
        numero_puntos_chequeo = findViewById(R.id.edit_text_numero_puntos_material);
        numero_personal_chequeo = findViewById(R.id.edit_text_numero_personal_material);
        personal_asignado1_chequeo = findViewById(R.id.edit_text_personal_asignado1_material);
        personal_asignado2_chequeo = findViewById(R.id.edit_text_personal_asignado2_material);
        personal_asignado3_chequeo = findViewById(R.id.edit_text_personal_asignado3_material);
        observaciones_chequeo = findViewById(R.id.edit_text_observaciones_material);
        jefe_laboratorio_chequeo = findViewById(R.id.edit_text_jefe_lab_material);
        button_save_material= findViewById(R.id.button_save_material);

        t1 = findViewById(R.id.table_1_buttonm);
        t2 = findViewById(R.id.table_3_buttonm);
        t3 = findViewById(R.id.table_4_buttonm);
        t4 = findViewById(R.id.table_5_buttonm);
        t5 = findViewById(R.id.table_6_buttonm);


        revision_salida_chequeo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    showDatePickerDialog(3);
                }catch (Exception e){
                    Toast.makeText(MaterialCheckActivity.this, ""+e, Toast.LENGTH_SHORT).show();
                }
            }
        });


        elaboracion_chequeo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    showDatePickerDialog(1);
                }catch (Exception e){
                    Toast.makeText(MaterialCheckActivity.this, ""+e, Toast.LENGTH_SHORT).show();
                }
            }
        });


        revision_llegada_chequeo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    showDatePickerDialog(2);
                }catch (Exception e){
                    Toast.makeText(MaterialCheckActivity.this, ""+e, Toast.LENGTH_SHORT).show();
                }
            }
        });
        t1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(codigo_chequeo.getText().toString().equals("")){
                    Toast.makeText(MaterialCheckActivity.this, "Debes llenar el código", Toast.LENGTH_SHORT).show();
                }else {
                    Intent intenta = new Intent(MaterialCheckActivity.this, TableOneMaterialCheckActivity.class);
                    intenta.putExtra("codigo_chequeo", codigo_chequeo.getText().toString());
                    startActivity(intenta);
                }
            }
        });

        t2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(codigo_chequeo.getText().toString().equals("")){
                    Toast.makeText(MaterialCheckActivity.this, "Debes llenar el código", Toast.LENGTH_SHORT).show();
                }else {
                    Intent intenta = new Intent(MaterialCheckActivity.this, TableTwoMaterialCheckActivity.class);
                    intenta.putExtra("codigo_chequeo", codigo_chequeo.getText().toString());
                    startActivity(intenta);
                }
            }
        });
        t3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(codigo_chequeo.getText().toString().equals("")){
                    Toast.makeText(MaterialCheckActivity.this, "Debes llenar el código", Toast.LENGTH_SHORT).show();
                }else {
                    Intent intenta = new Intent(MaterialCheckActivity.this, TableThreeMaterialCheckActivity.class);
                    intenta.putExtra("codigo_chequeo", codigo_chequeo.getText().toString());
                    startActivity(intenta);
                }
            }
        });
        t4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(codigo_chequeo.getText().toString().equals("")){
                    Toast.makeText(MaterialCheckActivity.this, "Debes llenar el código", Toast.LENGTH_SHORT).show();
                }else {
                    Intent intenta = new Intent(MaterialCheckActivity.this, TableFourMaterialCheckActivity.class);
                    intenta.putExtra("codigo_chequeo", codigo_chequeo.getText().toString());
                    startActivity(intenta);
                }
            }
        });
        t5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(codigo_chequeo.getText().toString().equals("")){
                    Toast.makeText(MaterialCheckActivity.this, "Debes llenar el código", Toast.LENGTH_SHORT).show();
                }else {
                    Intent intenta = new Intent(MaterialCheckActivity.this, TableFiveMaterialCheckActivity.class);
                    intenta.putExtra("codigo_chequeo", codigo_chequeo.getText().toString());
                    startActivity(intenta);
                }
            }
        });

        button_save_material.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder Bien = new AlertDialog.Builder(MaterialCheckActivity.this);
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
                            Toast.makeText(MaterialCheckActivity.this, "Permiso concedido, puedes continuar", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(MaterialCheckActivity.this, "Permiso denegado, no puedes continuar.", Toast.LENGTH_SHORT).show();
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

    private void showDatePickerDialog(int id){

        switch (id){
        case 1 :
            DatePickerFragment newFragment = DatePickerFragment.newIntance(new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                    final String selectedDate = year + "/" + (month + 1) + "/" + day;
                    elaboracion_chequeo.setText(selectedDate);
                }
            });
            newFragment.show(MaterialCheckActivity.this.getSupportFragmentManager(), "datePicker");
            break;
        case 2:
            DatePickerFragment newFragment2 = DatePickerFragment.newIntance(new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                    final String selectedDate = year + "/" + (month + 1) + "/" + day;
                    revision_llegada_chequeo.setText(selectedDate);
                }
            });
            newFragment2.show(MaterialCheckActivity.this.getSupportFragmentManager(), "datePicker");
            break;

        case 3:
            DatePickerFragment newFragment3 = DatePickerFragment.newIntance(new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                    final String selectedDate = year + "/" + (month + 1) + "/" + day;
                    revision_salida_chequeo.setText(selectedDate);
                }
            });
            newFragment3.show(MaterialCheckActivity.this.getSupportFragmentManager(), "datePicker");
            break;
        default:
            break;
        }

    }
    private void verifyCodeInFile(){
        SharedPreferences preferencesId = getSharedPreferences("EquipoVeolia", Context.MODE_PRIVATE);
        final String nameTeam = preferencesId.getString("NombreEquipo","E1");
        File ruteFolder = new File(getFilesDir()+"/ArchivosGeneradosVeolia");
        File file = new File(ruteFolder, "FChequeo"+nameTeam+".csv");
        boolean isCreate = false;
        if(!file.exists()){
            Toast.makeText(MaterialCheckActivity.this, "No se encuentra el archivo...", Toast.LENGTH_SHORT).show();
        }else{
            String cadena;
            String[] arreglo = {};

            try{
                FileReader fileReader =  new FileReader(file);
                BufferedReader bufferedReader = new BufferedReader(fileReader);

                while((cadena = bufferedReader.readLine()) != null){
                    arreglo = cadena.split(",");
                }
                boolean value = contains(arreglo, codigo_chequeo.getText().toString());
                if(value){
                    AlertDialog.Builder Bien = new AlertDialog.Builder(MaterialCheckActivity.this);
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
                Toast.makeText(MaterialCheckActivity.this, "Error: probablemente las tablas no se han creado aún...", Toast.LENGTH_SHORT).show();
            }

        }
    }





    private void validateTableOne(){
        SharedPreferences preferencesId = getSharedPreferences("EquipoVeolia", Context.MODE_PRIVATE);
        final String nameTeam = preferencesId.getString("NombreEquipo","E1");
        File ruteFolder = new File(getFilesDir()+"/ArchivosGeneradosVeolia");
        File file = new File(ruteFolder, "FChequeo1"+nameTeam+".csv");
        boolean isCreate = false;
        if(!file.exists()){
            AlertDialog.Builder Bien = new AlertDialog.Builder(MaterialCheckActivity.this);
            Bien.setMessage("No se ha creado el archivo de la tabla uno, ingresa y registra los datos para el código asignado.")
                    .setCancelable(false)
                    .setPositiveButton("Entendido", new DialogInterface.OnClickListener() {
                        @RequiresApi(api = Build.VERSION_CODES.M)
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent TableOneChainCustody = new Intent(MaterialCheckActivity.this, TableOneMaterialCheckActivity.class);
                            TableOneChainCustody.putExtra("codigo_chequeo", codigo_chequeo.getText().toString());
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
                boolean value = contains(arreglo, codigo_chequeo.getText().toString());
                if(value){
                    validateTableTwo();
                }else{
                    AlertDialog.Builder Bien = new AlertDialog.Builder(MaterialCheckActivity.this);
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
                Toast.makeText(MaterialCheckActivity.this, ""+e, Toast.LENGTH_SHORT).show();
            }

        }
    }



    private void validateTableTwo(){
        SharedPreferences preferencesId = getSharedPreferences("EquipoVeolia", Context.MODE_PRIVATE);
        final String nameTeam = preferencesId.getString("NombreEquipo","E1");
        File ruteFolder = new File(getFilesDir()+"/ArchivosGeneradosVeolia");
        File file = new File(ruteFolder, "FChequeo2"+nameTeam+".csv");
        boolean isCreate = false;
        if(!file.exists()){
            AlertDialog.Builder Bien = new AlertDialog.Builder(MaterialCheckActivity.this);
            Bien.setMessage("No se ha creado el archivo de la tabla dos, ingresa y registra los datos para el código asignado.")
                    .setCancelable(false)
                    .setPositiveButton("Entendido", new DialogInterface.OnClickListener() {
                        @RequiresApi(api = Build.VERSION_CODES.M)
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent TableTwoChainCustody = new Intent(MaterialCheckActivity.this, TableTwoMaterialCheckActivity.class);
                            TableTwoChainCustody.putExtra("codigo_chequeo", codigo_chequeo.getText().toString());
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
                boolean value = contains(arregloTD, codigo_chequeo.getText().toString());
                if(value){
                    validateTableThree();
                }else{
                    AlertDialog.Builder Bien = new AlertDialog.Builder(MaterialCheckActivity.this);
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
                Toast.makeText(MaterialCheckActivity.this, "Error: probablemente las tablas no se han creado aún...", Toast.LENGTH_LONG).show();
            }

        }
    }



    private void validateTableThree(){
        SharedPreferences preferencesId = getSharedPreferences("EquipoVeolia", Context.MODE_PRIVATE);
        final String nameTeam = preferencesId.getString("NombreEquipo","E1");
        File ruteFolder = new File(getFilesDir()+"/ArchivosGeneradosVeolia");
        File file = new File(ruteFolder, "FChequeo3"+nameTeam+".csv");
        boolean isCreate = false;
        if(!file.exists()){
            AlertDialog.Builder Bien = new AlertDialog.Builder(MaterialCheckActivity.this);
            Bien.setMessage("No se ha creado el archivo de la tabla tres, ingresa y registra los datos para el código asignado.")
                    .setCancelable(false)
                    .setPositiveButton("Entendido", new DialogInterface.OnClickListener() {
                        @RequiresApi(api = Build.VERSION_CODES.M)
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent TableTwoChainCustody = new Intent(MaterialCheckActivity.this, TableThreeMaterialCheckActivity.class);
                            TableTwoChainCustody.putExtra("codigo_chequeo", codigo_chequeo.getText().toString());
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
                boolean value = contains(arregloTD, codigo_chequeo.getText().toString());
                if(value){
                    validateTableFour();
                }else{
                    AlertDialog.Builder Bien = new AlertDialog.Builder(MaterialCheckActivity.this);
                    Bien.setMessage("No has registrado los datos de la tabla 3, estos se deben crear para este código")
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
                Toast.makeText(MaterialCheckActivity.this, "Error: probablemente las tablas no se han creado aún...", Toast.LENGTH_LONG).show();
            }

        }
    }



    private void validateTableFour(){
        SharedPreferences preferencesId = getSharedPreferences("EquipoVeolia", Context.MODE_PRIVATE);
        final String nameTeam = preferencesId.getString("NombreEquipo","E1");
        File ruteFolder = new File(getFilesDir()+"/ArchivosGeneradosVeolia");
        File file = new File(ruteFolder, "FChequeo4"+nameTeam+".csv");
        boolean isCreate = false;
        if(!file.exists()){
            AlertDialog.Builder Bien = new AlertDialog.Builder(MaterialCheckActivity.this);
            Bien.setMessage("No se ha creado el archivo de la tabla cuatro, ingresa y registra los datos para el código asignado.")
                    .setCancelable(false)
                    .setPositiveButton("Entendido", new DialogInterface.OnClickListener() {
                        @RequiresApi(api = Build.VERSION_CODES.M)
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent TableTwoChainCustody = new Intent(MaterialCheckActivity.this, TableFourMaterialCheckActivity.class);
                            TableTwoChainCustody.putExtra("codigo_chequeo", codigo_chequeo.getText().toString());
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
                boolean value = contains(arregloTD, codigo_chequeo.getText().toString());
                if(value){
                    validateTableFive();
                }else{
                    AlertDialog.Builder Bien = new AlertDialog.Builder(MaterialCheckActivity.this);
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
                Toast.makeText(MaterialCheckActivity.this, "Error: probablemente las tablas no se han creado aún...", Toast.LENGTH_LONG).show();
            }

        }
    }


    private void validateTableFive(){
        SharedPreferences preferencesId = getSharedPreferences("EquipoVeolia", Context.MODE_PRIVATE);
        final String nameTeam = preferencesId.getString("NombreEquipo","E1");
        File ruteFolder = new File(getFilesDir()+"/ArchivosGeneradosVeolia");
        File file = new File(ruteFolder, "FChequeo5"+nameTeam+".csv");
        boolean isCreate = false;
        if(!file.exists()){
            AlertDialog.Builder Bien = new AlertDialog.Builder(MaterialCheckActivity.this);
            Bien.setMessage("No se ha creado el archivo de la tabla cinco, ingresa y registra los datos para el código asignado.")
                    .setCancelable(false)
                    .setPositiveButton("Entendido", new DialogInterface.OnClickListener() {
                        @RequiresApi(api = Build.VERSION_CODES.M)
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent TableTwoChainCustody = new Intent(MaterialCheckActivity.this, TableFiveMaterialCheckActivity.class);
                            TableTwoChainCustody.putExtra("codigo_chequeo", codigo_chequeo.getText().toString());
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
                boolean value = contains(arregloTD, codigo_chequeo.getText().toString());
                if(value){
                    verifyCodeInFile();
                }else{
                    AlertDialog.Builder Bien = new AlertDialog.Builder(MaterialCheckActivity.this);
                    Bien.setMessage("No has registrado los datos de la tabla 5, estos se deben crear para este código")
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
                Toast.makeText(MaterialCheckActivity.this, "Error: probablemente las tablas no se han creado aún...", Toast.LENGTH_LONG).show();
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
        File filecrate = new File(ruteFolder, "FChequeo"+nameTeam+".csv");
        String nameFileInDrive = "FChequeo"+nameTeam+".csv";
        try {
            filecrate.createNewFile();
            String[] infoCreate = {codigo_chequeo.getText().toString(),numero_chequeo.getText().toString(),
                    elaboracion_chequeo.getText().toString(),revision_salida_chequeo.getText().toString(),
                    revision_llegada_chequeo.getText().toString(),responsable1_chequeo.getText().toString(),
                    responsable2_chequeo.getText().toString(),responsable3_chequeo.getText().toString(),
                    cliente_chequeo.getText().toString(),ciudad_chequeo.getText().toString(),
                    orden_servicio_chequeo.getText().toString(),numero_puntos_chequeo.getText().toString(),
                    numero_personal_chequeo.getText().toString(),personal_asignado1_chequeo.getText().toString(),
                    personal_asignado2_chequeo.getText().toString(),personal_asignado3_chequeo.getText().toString(),
                    observaciones_chequeo.getText().toString(),jefe_laboratorio_chequeo.getText().toString(),};





            CSVWriter fileWriter = new CSVWriter(new OutputStreamWriter(new FileOutputStream(filecrate, true),
                    "ISO-8859-1"), ',',
                    CSVWriter.NO_QUOTE_CHARACTER,
                    CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                    CSVWriter.DEFAULT_LINE_END);

            fileWriter.writeNext(infoCreate);
            fileWriter.close();

            ProgressDialog progressDialog = new ProgressDialog(MaterialCheckActivity.this);
            progressDialog.setTitle("Archivo creado, sincronizando con Google Drive.");
            progressDialog.setMessage("Por favor, espera.");
            progressDialog.show();


            try {
                driveServiceHelper.createFileDrive(nameFileInDrive, filecrate.getPath()).addOnSuccessListener(new OnSuccessListener<String>() {
                    @Override
                    public void onSuccess(String s) {
                        progressDialog.dismiss();
                        Toast.makeText(MaterialCheckActivity.this, "El archivo ya está en Google Drive con tu cuenta de Google", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(MaterialCheckActivity.this, "No se pudo subir el archivo a Google Drive", Toast.LENGTH_SHORT).show();
                    }
                });
            }catch (Exception e){
                progressDialog.dismiss();
                Toast.makeText(MaterialCheckActivity.this, "Ocurrio un problema al subir el archivo a drive", Toast.LENGTH_SHORT).show();
            }





            Toast.makeText(MaterialCheckActivity.this, "Datos agregados al archivo: " + filecrate.getPath(), Toast.LENGTH_LONG).show();
        }catch (IOException ie){

            Toast.makeText(MaterialCheckActivity.this, "Ocurrio un problema al crear el archivo."+ ie, Toast.LENGTH_LONG).show();
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
                    File filecrate = new File(ruteFolder, "FChequeo"+nameTeam+".csv");
                if(!filecrate.exists()){
                    filecrate.createNewFile();
                    String[] titles = {"Codigo", "Numero", "Elaboracion", "revision salida", "Revision llegada",
                            "Responsable1", "Responsable 2", "Responsable 3", "Cliente", "Ciudad",
                            "Orden", "Puntos", "Numero personal", "Personal asigando 1",
                            "Personal asigando 2", "Personal asigando 3", "Observaciones", "Jefe",};
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
                    Toast.makeText(MaterialCheckActivity.this, "Por favor, acepta los permisos para la aplicación "+ app_name, Toast.LENGTH_LONG).show();
                    finish();
                }catch (Exception e){
                    Intent intent = new Intent();
                    intent.setAction(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                    activityResultLauncher.launch(intent);
                    Toast.makeText(MaterialCheckActivity.this, "Por favor, acepta los permisos para la aplicación "+ app_name, Toast.LENGTH_LONG).show();
                    finish();
                }
            }


        }else{
            int permissionResult = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if(permissionResult == PackageManager.PERMISSION_DENIED){
                AlertDialog.Builder Bien = new AlertDialog.Builder(MaterialCheckActivity.this);
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
                File filecrate = new File(ruteFolder, "FChequeo"+nameTeam+".csv");
                if(!filecrate.exists()){
                    try {
                        filecrate.createNewFile();
                        String[] titles = {"Codigo", "Numero", "Elaboracion", "revision salida", "Revision llegada",
                                "Responsable1", "Responsable 2", "Responsable 3", "Cliente", "Ciudad",
                                "Orden", "Puntos", "Numero personal", "Personal asigando 1",
                                "Personal asigando 2", "Personal asigando 3", "Observaciones", "Jefe",};
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
                        GoogleAccountCredential credential = GoogleAccountCredential.usingOAuth2(MaterialCheckActivity.this, Collections.singleton(DriveScopes.DRIVE_FILE));
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