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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
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
import com.la.veolia.adapters.ListRegistroAdapter;
import com.la.veolia.adapters.ListRegistroAdapterMaterialCheck;
import com.la.veolia.db.DbHelper;
import com.la.veolia.db.dbCaptureTableOneCaudalVol;
import com.la.veolia.db.dbCaptureTableOneMaterialCheck;
import com.la.veolia.entitys.RegistroTableMaterialCheck;
import com.la.veolia.entitys.RegistrosTableOneCaudalVol;
import com.opencsv.CSVWriter;

import org.apache.commons.lang3.ArrayUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class TableOneMaterialCheckActivity extends AppCompatActivity {
    Button button_save_material_lista1, button_save_mareial_db, button_delete_material_db;
    DriveServiceHelper driveServiceHelper;
    int REQUEST_CODE = 200;
    ActivityResultLauncher<Intent> activityResultLauncher;
    Spinner c1f1, c4f1, c5f1, c6f1;
    EditText c2f1, c3f1,c7f1, codigo,edit_text_observaciones_Material_check;

    RecyclerView listaRegistros;
    ArrayList<RegistroTableMaterialCheck> listaArrayRegistros;
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_one_material_check);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        listaRegistros = findViewById(R.id.rv_material_check_one);
        listaRegistros.setLayoutManager(new LinearLayoutManager(TableOneMaterialCheckActivity.this));
        dbCaptureTableOneMaterialCheck dbCapture = new dbCaptureTableOneMaterialCheck(TableOneMaterialCheckActivity.this);
        listaArrayRegistros = new ArrayList<>();

        ListRegistroAdapterMaterialCheck adapter = new ListRegistroAdapterMaterialCheck(dbCapture.mostrarDatos());

        listaRegistros.setAdapter(adapter);
        edit_text_observaciones_Material_check = findViewById(R.id.edit_text_observaciones_Material_check);
        button_save_mareial_db = findViewById(R.id.button_save_material_db);
        button_delete_material_db = findViewById(R.id.button_delete_material_db);
        codigo = findViewById(R.id.edit_text_codigo_chequeo_tabla1);
        c1f1 = findViewById(R.id.spinner_columna1_fila1_tabla1_material1);
        c2f1 = findViewById(R.id.edit_text_columna11_fila1_tabla1_material1);

        c7f1 = findViewById(R.id.edit_text_columna111_fila1_tabla1_material1);
        c3f1 = findViewById(R.id.edit_text_columna2_fila1_tabla1_material1);
        c4f1 = findViewById(R.id.spinner_columna3_fila1_tabla1_material1);
        c5f1 = findViewById(R.id.spinner_columna4_fila1_tabla1_material1);
        c6f1 = findViewById(R.id.spinner_columna5_fila1_tabla1_material1);

        edit_text_observaciones_Material_check.setEnabled(false);

        String codigoo = getIntent().getStringExtra("codigo_chequeo");
        codigo.setText(codigoo);
        codigo.setEnabled(false);

        button_save_material_lista1 = findViewById(R.id.button_save_material_lista1);

        button_delete_material_db.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean delete =  dbCapture.deleteDates();
                if(delete=true){

                    Toast.makeText(TableOneMaterialCheckActivity.this, "Datos eliminados.", Toast.LENGTH_SHORT).show();
                    SharedPreferences preferencesObservacionesdelete = getSharedPreferences("Observaciones", Context.MODE_PRIVATE);
                    SharedPreferences.Editor observacionesName = preferencesObservacionesdelete.edit();
                    observacionesName.putString("ObservacionOneMaterial", "");
                    observacionesName.commit();
                    edit_text_observaciones_Material_check.setText("Sin datos editados");
                    onResume();
                }
            }
        });
        button_save_mareial_db.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(c3f1.getText().toString().equals("")){
                    Toast.makeText(TableOneMaterialCheckActivity.this, "Por favor, selecciona el item", Toast.LENGTH_LONG).show();
                }else{

                    DbHelper dbHelper = new DbHelper(TableOneMaterialCheckActivity.this);
                    SQLiteDatabase db = dbHelper.getWritableDatabase();
                    if (db != null) {

                        dbCaptureTableOneMaterialCheck bdCapture = new dbCaptureTableOneMaterialCheck(TableOneMaterialCheckActivity.this);

                        long id = bdCapture.insertarDatos(c1f1.getSelectedItem().toString(), c2f1.getText().toString(), c3f1.getText().toString(), c7f1.getText().toString(),
                                c4f1.getSelectedItem().toString(), c5f1.getSelectedItem().toString(),c6f1.getSelectedItem().toString());

                        if (id > 0) {
                            Toast.makeText(TableOneMaterialCheckActivity.this, "Se guardó el registro", Toast.LENGTH_SHORT).show();
                            c2f1.setText("");
                            c3f1.setText("");
                            c7f1.setText("");
                            c1f1.setSelection(0);
                            c4f1.setSelection(0);
                            c5f1.setSelection(0);
                            c6f1.setSelection(0);
                            listaArrayRegistros.clear();
                            listaArrayRegistros = new ArrayList<>();
                            dbCaptureTableOneMaterialCheck dbCapture = new dbCaptureTableOneMaterialCheck(TableOneMaterialCheckActivity.this);
                            ListRegistroAdapterMaterialCheck adapter = new ListRegistroAdapterMaterialCheck(dbCapture.mostrarDatos());
                            listaRegistros.setAdapter(adapter);
                        } else {
                            Toast.makeText(TableOneMaterialCheckActivity.this, "No se pudo guardar el registro", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(TableOneMaterialCheckActivity.this, "Nooooooo", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        int sizeDatess = dbCapture.showSizeDates();
        if(sizeDatess >= 40 ){
            button_save_mareial_db.setVisibility(View.GONE);
        }
        button_save_material_lista1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder Bien = new AlertDialog.Builder(TableOneMaterialCheckActivity.this);
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
                            Toast.makeText(TableOneMaterialCheckActivity.this, "Permiso concedido, puedes continuar", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(TableOneMaterialCheckActivity.this, "Permiso denegado, no puedes continuar.", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                }
            }
        });
        createFile();
    }


    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences preferencesObservaciones = getSharedPreferences("Observaciones", Context.MODE_PRIVATE);
        final String ObservacionesSaved = preferencesObservaciones.getString("ObservacionOneMaterial","");
        if(ObservacionesSaved.equals("")){
            edit_text_observaciones_Material_check.setText("Sin datos editados");
        }else{
            edit_text_observaciones_Material_check.setText(ObservacionesSaved);
        }
        listaArrayRegistros.clear();
        listaArrayRegistros = new ArrayList<>();
        dbCaptureTableOneMaterialCheck dbCapture = new dbCaptureTableOneMaterialCheck(TableOneMaterialCheckActivity.this);
        ListRegistroAdapterMaterialCheck adapter = new ListRegistroAdapterMaterialCheck( dbCapture.mostrarDatos());
        listaRegistros.setAdapter(adapter);
        int sizeDatess = dbCapture.showSizeDates();
        if(sizeDatess >= 40 ){
            button_save_mareial_db.setVisibility(View.GONE);
        }
    }



    public static boolean contains(String[] arreglo, String codigo){
        return Arrays.asList(arreglo).contains(codigo);
    }

    private void verifyCodeInFile(){
        SharedPreferences preferencesId = getSharedPreferences("EquipoVeolia", Context.MODE_PRIVATE);
        final String nameTeam = preferencesId.getString("NombreEquipo","E1");
        File ruteFolder = new File(getFilesDir()+"/ArchivosGeneradosVeolia");
        File file = new File(ruteFolder, "FChequeo1"+nameTeam+".csv");
        boolean isCreate = false;
        if(!ruteFolder.exists()){
            Toast.makeText(TableOneMaterialCheckActivity.this, "No se encuentra el archivo...", Toast.LENGTH_SHORT).show();
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
                    AlertDialog.Builder Bien = new AlertDialog.Builder(TableOneMaterialCheckActivity.this);
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
                Toast.makeText(TableOneMaterialCheckActivity.this, "Error: probablemente la tabla no se han creado aún...", Toast.LENGTH_SHORT).show();
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
        File filecrate = new File(ruteFolder, "FChequeo1"+nameTeam+".csv");
        String nameFileInDrive = "FChequeo1"+nameTeam+".csv";
        try {
            filecrate.createNewFile();
            String[] Codigo = {codigo.getText().toString()};
            String[] ObsEdit = {edit_text_observaciones_Material_check.getText().toString()};

            dbCaptureTableOneMaterialCheck db = new dbCaptureTableOneMaterialCheck(TableOneMaterialCheckActivity.this);
            String[] item = new String[40];
            String[] otra = new String[40];
            String[] observaciones = new String[40];
            String[] cantidad = new String[40];
            String[] revision_salida = new String[40];
            String[] revision_campo = new String[40];
            String[] revision_llegada = new String[40];



            try{
                for(int i = 1; i<=40 ; i++){
                    item[i-1] = db.mostrarItem(i);
                    otra[i-1] = db.mostrarOtra(i);
                    observaciones[i-1] = db.mostrarObservaciones(i);
                    cantidad[i-1] = db.mostrarCantidad(i);
                    revision_salida[i-1] = db.mostrarRevisionSalida(i);
                    revision_campo[i-1] = db.mostrarRevisionCampo(i);
                    revision_llegada[i-1] = db.mostrarRevisionLlegada(i);
                }
            }catch (Exception e){
                Toast.makeText(TableOneMaterialCheckActivity.this, ""+e, Toast.LENGTH_SHORT).show();
            }


            String[]  par1 =  ArrayUtils.addAll(Codigo, item);
            String[]  par2 =  ArrayUtils.addAll(par1, otra);
            String[]  par3 =  ArrayUtils.addAll(par2, observaciones);
            String[]  par4 =  ArrayUtils.addAll(par3, cantidad);
            String[]  par5 =  ArrayUtils.addAll(par4, revision_salida);

            String[]  par6 =  ArrayUtils.addAll(par5, revision_campo);
            String[]  par7 =  ArrayUtils.addAll(par6, revision_llegada);
            String[] infoCreate = ArrayUtils.addAll(par7, ObsEdit);


            CSVWriter fileWriter = new CSVWriter(new OutputStreamWriter(new FileOutputStream(filecrate, true),
                    "ISO-8859-1"), ',',
                    CSVWriter.NO_QUOTE_CHARACTER,
                    CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                    CSVWriter.DEFAULT_LINE_END);

            fileWriter.writeNext(infoCreate);
            fileWriter.close();

            ProgressDialog progressDialog = new ProgressDialog(TableOneMaterialCheckActivity.this);
            progressDialog.setTitle("Archivo creado, sincronizando con Google Drive.");
            progressDialog.setMessage("Por favor, espera.");
            progressDialog.show();


            try {
                driveServiceHelper.createFileDrive(nameFileInDrive, filecrate.getPath()).addOnSuccessListener(new OnSuccessListener<String>() {
                    @Override
                    public void onSuccess(String s) {
                        progressDialog.dismiss();
                        Toast.makeText(TableOneMaterialCheckActivity.this, "El archivo ya está en Google Drive con tu cuenta de Google", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(TableOneMaterialCheckActivity.this, "No se pudo subir el archivo a Google Drive", Toast.LENGTH_SHORT).show();
                    }
                });
            }catch (Exception e){
                progressDialog.dismiss();
                Toast.makeText(TableOneMaterialCheckActivity.this, "Ocurrio un problema al subir el archivo a drive", Toast.LENGTH_SHORT).show();
            }





            Toast.makeText(TableOneMaterialCheckActivity.this, "Datos agregados al archivo: " + filecrate.getPath(), Toast.LENGTH_LONG).show();
        }catch (IOException ie){

            Toast.makeText(TableOneMaterialCheckActivity.this, "Ocurrio un problema al crear el archivo."+ ie, Toast.LENGTH_LONG).show();
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
                File filecrate = new File(ruteFolder, "FChequeo1"+nameTeam+".csv");
                if(!filecrate.exists()){
                    filecrate.createNewFile();
                    String[] titles = {};
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
                    Toast.makeText(TableOneMaterialCheckActivity.this, "Por favor, acepta los permisos para la aplicación "+ app_name, Toast.LENGTH_LONG).show();
                    finish();
                }catch (Exception e){
                    Intent intent = new Intent();
                    intent.setAction(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                    activityResultLauncher.launch(intent);
                    Toast.makeText(TableOneMaterialCheckActivity.this, "Por favor, acepta los permisos para la aplicación "+ app_name, Toast.LENGTH_LONG).show();
                    finish();
                }
            }


        }else{
            int permissionResult = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if(permissionResult == PackageManager.PERMISSION_DENIED){
                AlertDialog.Builder Bien = new AlertDialog.Builder(TableOneMaterialCheckActivity.this);
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
                File filecrate = new File(ruteFolder, "FChequeo1"+nameTeam+".csv");
                if(!filecrate.exists()){
                    try {
                        filecrate.createNewFile();
                        String[] titles = {};
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
                        GoogleAccountCredential credential = GoogleAccountCredential.usingOAuth2(TableOneMaterialCheckActivity.this, Collections.singleton(DriveScopes.DRIVE_FILE));
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