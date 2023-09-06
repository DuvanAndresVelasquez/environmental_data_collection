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
import com.la.veolia.db.dbCaptureTableOneCaudalVol;
import com.la.veolia.entitys.RegistrosTableOneCaudalVol;
import com.opencsv.CSVWriter;

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
import android.text.Editable;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.List;
import java.util.stream.Stream;

public class TableOneCaudalaCanActivity extends AppCompatActivity {
    DriveServiceHelper driveServiceHelper;
    int REQUEST_CODE = 200;
    ActivityResultLauncher<Intent> activityResultLauncher;
    Button btn_add_data_tow, Calcular_tabla1_caudal_vol, button_save_caudal_vol_tabla1, button_delete_data;
    EditText edit_text_volumen_Caudal_vol_tabla1, edit_text_numero_Caudal_vol_tabla1, edit_text_codigo_Caudal_vol_tabla1, edit_text_observaciones_Caudal_vol_tabla1;
    TextView Text_view_qp, Text_view_n_qp;
    RecyclerView listaRegistros;
    List<RegistrosTableOneCaudalVol> listaArrayRegistros;

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_one_caudala_can);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        edit_text_observaciones_Caudal_vol_tabla1 = findViewById(R.id.edit_text_observaciones_Caudal_vol_tabla1);
        edit_text_volumen_Caudal_vol_tabla1 = findViewById( R.id.edit_text_volumen_Caudal_vol_tabla1);
        edit_text_numero_Caudal_vol_tabla1 = findViewById(R.id.edit_text_numero_Caudal_vol_tabla1);
        Text_view_qp = findViewById(R.id.Text_view_qp);
        Text_view_n_qp = findViewById(R.id.Text_view_n_qp);
        edit_text_codigo_Caudal_vol_tabla1 = findViewById(R.id.edit_text_codigo_Caudal_vol_tabla1);
        Calcular_tabla1_caudal_vol = findViewById(R.id.Calcular_tabla1_caudal_vol);
        btn_add_data_tow = findViewById(R.id.btn_add_data_tow);
        button_delete_data = findViewById(R.id.btn_delete_data_tow);
        button_save_caudal_vol_tabla1= findViewById(R.id.button_save_caudal_vol_tabla1);
        listaRegistros = findViewById(R.id.rv_caudal_thow);
        listaRegistros.setLayoutManager(new LinearLayoutManager(this));
        String codigoo = getIntent().getStringExtra("codigo_caudal_vol");
        edit_text_codigo_Caudal_vol_tabla1.setText(codigoo);
        edit_text_codigo_Caudal_vol_tabla1.setEnabled(false);
        edit_text_observaciones_Caudal_vol_tabla1.setEnabled(false);
        dbCaptureTableOneCaudalVol dbCapture = new dbCaptureTableOneCaudalVol(TableOneCaudalaCanActivity.this);

        SharedPreferences preferencesObservaciones = getSharedPreferences("Observaciones", Context.MODE_PRIVATE);
        final String ObservacionesSaved = preferencesObservaciones.getString("ObservacionOne","");
        if(ObservacionesSaved.equals("")){
            edit_text_observaciones_Caudal_vol_tabla1.setText("Sin datos editados");
        }else{
            edit_text_observaciones_Caudal_vol_tabla1.setText(ObservacionesSaved);
        }
        listaArrayRegistros = new ArrayList<>();
        ListRegistroAdapter adapter = new ListRegistroAdapter(dbCapture.mostrarDatos());

        listaRegistros.setAdapter(adapter);

        button_delete_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder Bien = new AlertDialog.Builder(TableOneCaudalaCanActivity.this);
                Bien.setMessage("Esta acción vaciará los datos registrados. ¿Deseas continuar?")
                        .setCancelable(false)
                        .setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                            @RequiresApi(api = Build.VERSION_CODES.M)
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Boolean delete =  dbCapture.deleteDates();
                                if(delete=true){
                                    Toast.makeText(TableOneCaudalaCanActivity.this, "Datos eliminados.", Toast.LENGTH_SHORT).show();
                                    SharedPreferences preferencesObservacionesdelete = getSharedPreferences("Observaciones", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor observacionesName = preferencesObservacionesdelete.edit();
                                    observacionesName.putString("ObservacionOne", "");
                                    observacionesName.commit();
                                    edit_text_observaciones_Caudal_vol_tabla1.setText("Sin datos editados");
                                    onResume();
                                }
                            }
                        })
                .setNegativeButton("No, gracias", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog Titulo = Bien.create();
                Titulo.setTitle("Vaciar datos!");
                Titulo.show();


            }
        });
        //Con este método se cálcula
        Calcular_tabla1_caudal_vol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edit_text_volumen_Caudal_vol_tabla1.getText().toString().isEmpty()){
                    Toast.makeText(TableOneCaudalaCanActivity.this, "No hay volúmen", Toast.LENGTH_SHORT).show();

                }else{
                    try {
                    Double volumentotal = Double.valueOf(edit_text_volumen_Caudal_vol_tabla1.getText().toString());
                    int numMuestras = dbCapture.showSizeDates()-1;
                    double Qls = Double.parseDouble(dbCapture.showQls());
                    Toast.makeText(TableOneCaudalaCanActivity.this, " "+Qls, Toast.LENGTH_SHORT).show();
                    double Qp  = (Qls)/numMuestras;
                    double nQp = numMuestras*(Qp);
                    double ConstanteVolumenAlicuota = volumentotal/nQp;
                    Text_view_qp.setText(" "+Qp);
                    Text_view_n_qp.setText(" "+nQp);
                    dbCaptureTableOneCaudalVol db = new dbCaptureTableOneCaudalVol(TableOneCaudalaCanActivity.this);

                    try{
                        for(int i = 1; i<=numMuestras; i++){
                            double QlsC =  Double.parseDouble(db.mostrarQls(i));
                            double VolumenAlicuota = QlsC*ConstanteVolumenAlicuota;
                            db.insertarCalculos(i, String.valueOf(VolumenAlicuota));
                              }
                        onResume();

                    }catch (Exception e){
                        Toast.makeText(TableOneCaudalaCanActivity.this, " "+e, Toast.LENGTH_SHORT).show();
                    }


                    }catch (Exception e){
                        Toast.makeText(TableOneCaudalaCanActivity.this, " "+e, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        button_save_caudal_vol_tabla1.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                if(edit_text_codigo_Caudal_vol_tabla1.getText().toString().isEmpty()
                || Text_view_qp.getText().toString().isEmpty()){
                    Toast.makeText(TableOneCaudalaCanActivity.this, "Datos incompletos, es posible que no hallas calculado o falte el código asignado..", Toast.LENGTH_SHORT).show();
                }else{
                    AlertDialog.Builder Bien = new AlertDialog.Builder(TableOneCaudalaCanActivity.this);
                    Bien.setMessage("¿Seguro que desea guardar estos registros? (Debe entender que el código no se podrá repetir en el archivo.)")
                            .setCancelable(false)
                            .setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
                                @RequiresApi(api = Build.VERSION_CODES.M)
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                    try {
                                        verifyCodeInFile();
                                        //saveFile();
                                    }catch (Exception e){
                                        Toast.makeText(TableOneCaudalaCanActivity.this, ""+e, Toast.LENGTH_SHORT).show();
                                    }
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
        btn_add_data_tow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent table1 = new Intent(TableOneCaudalaCanActivity.this, CaptureDatesTableOneCaudalVolActivity.class);
                startActivity(table1);
            }
        });



        singin();
        activityResultLauncher=registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if(result.getResultCode()== Activity.RESULT_OK){
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){
                        if (Environment.isExternalStorageManager()){
                            Toast.makeText(TableOneCaudalaCanActivity.this, "Permiso concedido, puedes continuar", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(TableOneCaudalaCanActivity.this, "Permiso denegado, no puedes continuar.", Toast.LENGTH_SHORT).show();
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


    @RequiresApi(api = Build.VERSION_CODES.N)
    private void verifyCodeInFile(){
        SharedPreferences preferencesId = getSharedPreferences("EquipoVeolia", Context.MODE_PRIVATE);
        final String nameTeam = preferencesId.getString("NombreEquipo","E1");
        File ruteFolder = new File(getFilesDir()+"/ArchivosGeneradosVeolia");
        File file = new File(ruteFolder, "DCaudalVol1"+nameTeam+".csv");
        boolean isCreate = false;
        if(!ruteFolder.exists()){
            Toast.makeText(TableOneCaudalaCanActivity.this, "No se encuentra el archivo...", Toast.LENGTH_SHORT).show();
        }else{
            String cadena;
            String[] arreglo = {};

            try{
                FileReader fileReader =  new FileReader(file);
                BufferedReader bufferedReader = new BufferedReader(fileReader);

                while((cadena = bufferedReader.readLine()) != null){
                    arreglo = cadena.split(",");
                }
                boolean value = contains(arreglo, edit_text_codigo_Caudal_vol_tabla1.getText().toString());
                if(value){
                    AlertDialog.Builder Bien = new AlertDialog.Builder(TableOneCaudalaCanActivity.this);
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
                Toast.makeText(TableOneCaudalaCanActivity.this, "Error: probablemente la tabla no se han creado aún...", Toast.LENGTH_SHORT).show();
            }

        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences preferencesObservaciones = getSharedPreferences("Observaciones", Context.MODE_PRIVATE);
        final String ObservacionesSaved = preferencesObservaciones.getString("ObservacionOne","");
        if(ObservacionesSaved.equals("")){
            edit_text_observaciones_Caudal_vol_tabla1.setText("Sin datos editados");
        }else{
            edit_text_observaciones_Caudal_vol_tabla1.setText(ObservacionesSaved);
        }
        listaArrayRegistros.clear();
        listaArrayRegistros = new ArrayList<>();
        dbCaptureTableOneCaudalVol dbCapture = new dbCaptureTableOneCaudalVol(TableOneCaudalaCanActivity.this);

        int sizeDates = dbCapture.showSizeDates();
        if(sizeDates > 0){
            edit_text_numero_Caudal_vol_tabla1.setText(""+sizeDates);
            Calcular_tabla1_caudal_vol.setVisibility(View.VISIBLE);
            button_delete_data.setVisibility(View.VISIBLE);
        }else{
            button_delete_data.setVisibility(View.GONE);
        }
        //Toast.makeText(TableOneCaudalaCanActivity.this, "Número de registros: "+sizeDates, Toast.LENGTH_SHORT).show();


        if(sizeDates>= 49){
            btn_add_data_tow.setVisibility(View.GONE);
        }
        ListRegistroAdapter adapter = new ListRegistroAdapter( dbCapture.mostrarDatos());

        listaRegistros.setAdapter(adapter);

        if(listaArrayRegistros.size() >= 49){
            btn_add_data_tow.setVisibility(View.GONE);
        }

        if(listaArrayRegistros.size() > 0){
            edit_text_numero_Caudal_vol_tabla1.setText(listaArrayRegistros.size());
        }

    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    private void saveFile(){

            File ruteFolder = new File(getFilesDir()+"/ArchivosGeneradosVeolia");
            if (!ruteFolder.exists()) {
                createFile();
            }
        SharedPreferences preferencesId = getSharedPreferences("EquipoVeolia", Context.MODE_PRIVATE);
        final String nameTeam = preferencesId.getString("NombreEquipo","E1");
            File filecrate = new File(ruteFolder, "DCaudalVol1"+nameTeam+".csv");
            String nameFileInDrive = "DCaudalVol1"+nameTeam+".csv";
            try {

                filecrate.createNewFile();
                String[] Codigo = {edit_text_codigo_Caudal_vol_tabla1.getText().toString()};
                String[] Qp = {Text_view_qp.getText().toString()};
                String[] nQp = {Text_view_n_qp.getText().toString()};
                String[] Observaciones = {edit_text_observaciones_Caudal_vol_tabla1.getText().toString()};
                String[] volumen_total = {edit_text_volumen_Caudal_vol_tabla1.getText().toString()};
                int numeroo = Integer.parseInt(edit_text_numero_Caudal_vol_tabla1.getText().toString())-1;
                String[] numero_total = {String.valueOf(numeroo)};
                CSVWriter fileWriter = new CSVWriter(new OutputStreamWriter(new FileOutputStream(filecrate, true),
                        "ISO-8859-1"), ',',
                        CSVWriter.NO_QUOTE_CHARACTER,
                        CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                        CSVWriter.DEFAULT_LINE_END);


                dbCaptureTableOneCaudalVol dbCapture = new dbCaptureTableOneCaudalVol(TableOneCaudalaCanActivity.this);
                int sizeDatess = dbCapture.showSizeDates();
                    filecrate.createNewFile();
                    dbCaptureTableOneCaudalVol db = new dbCaptureTableOneCaudalVol(TableOneCaudalaCanActivity.this);
                    String[] numeroMuestras = new String[50];
                    String[] horaMuestras = new String[50];
                String[] Tc = new String[50];
                String[] PhPatronUnd = new String[50];
                String[] PhPatronTc = new String[50];
                String[] PhMuestraUnd = new String[50];
                String[] PhMuestraTc = new String[50];
                String[] OxigenoMg = new String[50];
                String[] OxigenoTc = new String[50];
                String[] ConductividadUnd = new String[50];
                String[] ConductividadTc = new String[50];
                String[] Vl = new String[50];
                String[] Ts = new String[50];
                String[] Qls = new String[50];
                String[] VolumenAlicuota = new String[50];
                    try {
                        for (int i = 1; i <= 50; i++) {
                            numeroMuestras[i-1] = db.mostrarNumeroMuestra(i);
                            horaMuestras[i-1] = db.mostrarHoraMuestra(i);
                            Tc[i-1] = db.mostrarTc(i);
                            PhPatronUnd[i-1] = db.mostrarPhPatronUnd(i);
                            PhPatronTc[i-1] = db.mostrarPhPatronTc(i);
                            PhMuestraUnd[i-1] = db.mostrarPhMuestraUnd(i);
                            PhMuestraTc[i-1] = db.mostrarPhMuestraTc(i);
                            OxigenoMg[i-1]= db.mostrarOxigenoMg(i);
                            OxigenoTc[i-1]=db.mostrarOxigenoTc(i);
                            ConductividadUnd[i-1] = db.mostrarConductividadUnd(i);
                            ConductividadTc[i-1] = db.mostrarConductividadTc(i);
                            Vl[i-1] = db.mostrarVL(i);
                            Ts[i-1] = db.mostrarTs(i);
                            Qls[i-1] = db.mostrarQls(i);
                            VolumenAlicuota[i-1] = db.mostrarVolumenAlicuota(i);
                        }
                        //Toast.makeText(TableOneCaudalaCanActivity.this, ""+numeroMuestras, Toast.LENGTH_SHORT).show();
                    }catch (Exception e){
                        Toast.makeText(TableOneCaudalaCanActivity.this, ""+e, Toast.LENGTH_SHORT).show();
                    }





                    String[]  par1 =  ArrayUtils.addAll(Codigo, numeroMuestras);
                    String[]  par2 =  ArrayUtils.addAll(par1, horaMuestras);
                    String[]  par3 =  ArrayUtils.addAll(par2, Tc);
                    String[]  par4 =  ArrayUtils.addAll(par3, PhPatronUnd);
                    String[]  par5 =  ArrayUtils.addAll(par4, PhPatronTc);
                    String[]  par6 =  ArrayUtils.addAll(par5, PhMuestraUnd);
                    String[]  par7 =  ArrayUtils.addAll(par6, PhMuestraTc);
                    String[]  par8 =  ArrayUtils.addAll(par7, OxigenoMg);
                    String[]  par9 =  ArrayUtils.addAll(par8, OxigenoTc);
                    String[]  par10 =  ArrayUtils.addAll(par9, ConductividadUnd);
                    String[]  par11 =  ArrayUtils.addAll(par10, ConductividadTc);
                    String[]  par12 =  ArrayUtils.addAll(par11, Vl);
                    String[]  par13 =  ArrayUtils.addAll(par12, Ts);
                    String[]  par14 =  ArrayUtils.addAll(par13, Qls);
                    String[]  par15 =  ArrayUtils.addAll(par14, VolumenAlicuota);
                    String[]  par16 =  ArrayUtils.addAll(par15, volumen_total);
                    String[]  par17 =  ArrayUtils.addAll(par16, numero_total);
                    String[]  par18 =  ArrayUtils.addAll(par17, Qp);
                    String[]  par19 =  ArrayUtils.addAll(par18, nQp);
                    String[] infoCreate = ArrayUtils.addAll(par19, Observaciones);
                    fileWriter.writeNext(infoCreate);

                fileWriter.close();

                ProgressDialog progressDialog = new ProgressDialog(TableOneCaudalaCanActivity.this);
                progressDialog.setTitle("Archivo creado, sincronizando con Google Drive.");
                progressDialog.setMessage("Por favor, espera.");
                progressDialog.show();


                try {
                    driveServiceHelper.createFileDrive(nameFileInDrive, filecrate.getPath()).addOnSuccessListener(new OnSuccessListener<String>() {
                        @Override
                        public void onSuccess(String s) {
                            progressDialog.dismiss();
                            Toast.makeText(TableOneCaudalaCanActivity.this, "El archivo ya está en Google Drive con tu cuenta de Google", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(TableOneCaudalaCanActivity.this, "No se pudo subir el archivo a Google Drive", Toast.LENGTH_SHORT).show();
                        }
                    });
                }catch (Exception e){
                    progressDialog.dismiss();
                    Toast.makeText(TableOneCaudalaCanActivity.this, "Ocurrio un problema al subir el archivo a drive", Toast.LENGTH_SHORT).show();
                }





                Toast.makeText(TableOneCaudalaCanActivity.this, "Datos agregados al archivo: " + filecrate.getPath(), Toast.LENGTH_LONG).show();
            }catch (IOException ie){

                Toast.makeText(TableOneCaudalaCanActivity.this, "Ocurrio un problema al crear el archivo."+ ie, Toast.LENGTH_LONG).show();
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
                File filecrate = new File(ruteFolder, "DCaudalVol1"+nameTeam+".csv");
                if(!filecrate.exists()){
                    filecrate.createNewFile();
                    String[] titles = {"No. Muestra", "Hora de toma", "T('C)", "pH Patrón Und. ph", "pH Patrón T('C)",
                            "ph Muestra Und. pH", "pH Muestra t('C)", "Oxígeno mg O2/L", "Oxígeno T(´C)","Conductividad Und pH", "Conductividad T ('C)", "V(L)",
                            "t(s)", "Q(L/s)" , "Volumen ALICUOTA"};
                    CSVWriter fileWriter = new CSVWriter(new OutputStreamWriter(new FileOutputStream(filecrate, true),
                            "ISO-8859-1"), ',',
                            CSVWriter.NO_QUOTE_CHARACTER,
                            CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                            CSVWriter.DEFAULT_LINE_END);

                    //fileWriter.writeNext(titles);
                    fileWriter.close();
                }
            }catch (IOException ie){
                try {
                    Intent intent = new Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                    intent.addCategory("android.intent.category.DEFAULT");
                    intent.setData(Uri.parse(String.format("package:%s", new Object[]{getApplicationContext().getPackageName()})));
                    activityResultLauncher.launch(intent);
                    Toast.makeText(TableOneCaudalaCanActivity.this, "Por favor, acepta los permisos para la aplicación "+ app_name, Toast.LENGTH_LONG).show();
                    finish();
                }catch (Exception e){
                    Intent intent = new Intent();
                    intent.setAction(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                    activityResultLauncher.launch(intent);
                    Toast.makeText(TableOneCaudalaCanActivity.this, "Por favor, acepta los permisos para la aplicación "+ app_name, Toast.LENGTH_LONG).show();
                    finish();
                }
            }


        }else{
            int permissionResult = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if(permissionResult == PackageManager.PERMISSION_DENIED){
                AlertDialog.Builder Bien = new AlertDialog.Builder(TableOneCaudalaCanActivity.this);
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
                File filecrate = new File(ruteFolder, "DCaudalVol1"+nameTeam+".csv");
                if(!filecrate.exists()){
                    try {
                        filecrate.createNewFile();
                        String[] titles = {"No. Muestra", "Hora de toma", "T('C)", "pH Patrón Und. ph", "pH Patrón T('C)",
                                "ph Muestra Und. pH", "pH Muestra t('C)", "Oxígeno mg O2/L", "Oxígeno T(´C)","Conductividad Und pH", "Conductividad T ('C)", "V(L)",
                                "t(s)", "Q(L/s)" , "Volumen ALICUOTA"};
                        CSVWriter fileWriter = new CSVWriter(new OutputStreamWriter(new FileOutputStream(filecrate, true),
                                "ISO-8859-1"), ',',
                                CSVWriter.NO_QUOTE_CHARACTER,
                                CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                                CSVWriter.DEFAULT_LINE_END);

                        //fileWriter.writeNext(titles);
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
                        GoogleAccountCredential credential = GoogleAccountCredential.usingOAuth2(TableOneCaudalaCanActivity.this, Collections.singleton(DriveScopes.DRIVE_FILE));
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