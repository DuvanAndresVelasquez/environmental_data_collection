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
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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
import com.la.veolia.adapters.ListRegistroAdapterOneCan;
import com.la.veolia.db.dbCaptureTableOneCaudalCan;
import com.la.veolia.db.dbCaptureTableOneCaudalVol;
import com.la.veolia.entitys.RegistrosTableOneCaudalCan;
import com.opencsv.CSVWriter;

import org.apache.commons.lang3.ArrayUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class TableOneCaudalChannelsActivity extends AppCompatActivity {
    DriveServiceHelper driveServiceHelper;
    int REQUEST_CODE = 200;
    ActivityResultLauncher<Intent> activityResultLauncher;
    EditText edit_text_diametro_Caudal_can_tabla1,edit_text_coeficiente_Caudal_can_tabla1,
            edit_text_pendiente_Caudal_can_tabla1,edit_text_volumen_Caudal_can_tabla1,
            edit_text_numero_Caudal_can_tabla1, edit_text_codigo_Caudal_can_tabla1,edit_text_observaciones_Caudal_can_tabla1;
    Button btn_add_data_can, Calcular_tabla1_caudal_can_tabla1, button_save_caudal_can_tabla1,
            btn_delete_data;
    TextView Text_view_area_caudal_can,Text_view_radio_caudal_can,
            Text_view_qp_caudal_can,Text_view_n_qp_caudal_can;
    RecyclerView listaRegistros;
    ArrayList<RegistrosTableOneCaudalCan> listaArrayRegistros;
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_one_caudal_channels);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        btn_delete_data = findViewById(R.id.btn_delete_data);
        edit_text_observaciones_Caudal_can_tabla1 = findViewById(R.id.edit_text_observaciones_Caudal_can_tabla1);
        Calcular_tabla1_caudal_can_tabla1 = findViewById(R.id.Calcular_tabla1_caudal_can_tabla1);
        edit_text_diametro_Caudal_can_tabla1 = findViewById(R.id.edit_text_diametro_Caudal_can_tabla1);
        edit_text_coeficiente_Caudal_can_tabla1 = findViewById(R.id.edit_text_coeficiente_Caudal_can_tabla1);
        edit_text_pendiente_Caudal_can_tabla1 = findViewById(R.id.edit_text_pendiente_Caudal_can_tabla1);
        edit_text_volumen_Caudal_can_tabla1 = findViewById(R.id.edit_text_volumen_Caudal_can_tabla1);
        edit_text_numero_Caudal_can_tabla1 = findViewById(R.id.edit_text_numero_Caudal_can_tabla1);
        edit_text_codigo_Caudal_can_tabla1 = findViewById(R.id.edit_text_codigo_Caudal_can_tabla1);
        Text_view_area_caudal_can = findViewById(R.id.Text_view_area_caudal_can);
        Text_view_radio_caudal_can = findViewById(R.id.Text_view_radio_caudal_can);
        Text_view_qp_caudal_can = findViewById(R.id.Text_view_qp_caudal_can);
        Text_view_n_qp_caudal_can = findViewById(R.id.Text_view_n_qp_caudal_can);
        String codigoo = getIntent().getStringExtra("codigo_caudal_channels");
        edit_text_codigo_Caudal_can_tabla1.setText(codigoo);
        edit_text_codigo_Caudal_can_tabla1.setEnabled(false);
        edit_text_observaciones_Caudal_can_tabla1.setEnabled(false);
        button_save_caudal_can_tabla1 = findViewById(R.id.button_save_caudal_can_tabla1);
        btn_add_data_can = findViewById(R.id.btn_add_data);
        listaRegistros = findViewById(R.id.rv_caudal_one);
        listaRegistros.setLayoutManager(new LinearLayoutManager(this));
        dbCaptureTableOneCaudalCan dbCapture = new dbCaptureTableOneCaudalCan(TableOneCaudalChannelsActivity.this);

        listaArrayRegistros = new ArrayList<>();
        ListRegistroAdapterOneCan adapter = new ListRegistroAdapterOneCan(dbCapture.mostrarDatosTwo());

        listaRegistros.setAdapter(adapter);


        Calcular_tabla1_caudal_can_tabla1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edit_text_diametro_Caudal_can_tabla1.getText().toString().isEmpty() ||
                        edit_text_coeficiente_Caudal_can_tabla1.getText().toString().isEmpty() ||
                edit_text_pendiente_Caudal_can_tabla1.getText().toString().isEmpty() ||
                edit_text_volumen_Caudal_can_tabla1.getText().toString().isEmpty()
                ){
                    Toast.makeText(TableOneCaudalChannelsActivity.this, "Aún hay datos incompletos", Toast.LENGTH_LONG).show();
                }else {

                    dbCaptureTableOneCaudalCan db = new dbCaptureTableOneCaudalCan(TableOneCaudalChannelsActivity.this);
                    int sizeDates = db.showSizeDates();
                    double diametro = Double.parseDouble(edit_text_diametro_Caudal_can_tabla1.getText().toString());


                    double volumentotal = Double.parseDouble(edit_text_volumen_Caudal_can_tabla1.getText().toString());
                    double pendiente_canal = Double.parseDouble(edit_text_pendiente_Caudal_can_tabla1.getText().toString());
                    double coeficiente_manning = Double.parseDouble(edit_text_coeficiente_Caudal_can_tabla1.getText().toString());

                    BigDecimal areaa = new BigDecimal(3.1416 * ((diametro * diametro) / 4)).setScale(5, RoundingMode.HALF_UP);
                    BigDecimal radioo = new BigDecimal(diametro/4).setScale(5, RoundingMode.HALF_UP);
                    double area = 3.1416 * ((diametro * diametro) / 4);
                    double radio = diametro/4;
                    BigDecimal caudal_totall = new BigDecimal((area/coeficiente_manning) * (Math.pow(radio, 0.667)) * (Math.pow(pendiente_canal, 0.5))).setScale(5, RoundingMode.HALF_UP);

                    double caudal_total = (area/coeficiente_manning) * (Math.pow(radio, 0.667)) * (Math.pow(pendiente_canal, 0.5));

                    Text_view_area_caudal_can.setText(" "+ areaa);
                    Text_view_radio_caudal_can.setText(" "+radioo);



                    try{
                        Map<String, String> map = new HashMap<String, String>();
                        map.put("0.01","0.0002");
                        map.put("0.02","0.0007");
                        map.put("0.03","0.0016");
                        map.put("0.04","0.003");
                        map.put("0.05","0.0048");
                        map.put("0.06","0.0071");
                        map.put("0.07","0.00998");
                        map.put("0.08","0.013"
                        );map.put("0.09","0.0167");
                        map.put("0.1","0.0209"
                        );map.put("0.11","0.0255");
                        map.put("0.12","0.0306");
                        map.put("0.13","0.0361");
                        map.put("0.14","0.0421");
                        map.put("0.15","0.0486");
                        map.put("0.16","0.0555");
                        map.put("0.17","0.0629");
                        map.put("0.18","0.0707");
                        map.put("0.19","0.0789");
                        map.put("0.2","0.0876"
                        );map.put("0.21","0.0966");
                        map.put("0.22","0.1061");
                        map.put("0.23","0.116"
                        );map.put("0.24","0.1263");
                        map.put("0.25","0.137"
                        );map.put("0.26","0.148"
                        );map.put("0.27","0.1595");
                        map.put(".28","0.1712");
                        map.put("0.29","0.1834");
                        map.put("0.3","0.1958"
                        );map.put("0.31","0.2086");
                        map.put("0.32","0.2218");
                        map.put("0.33","0.2352");
                        map.put("0.34","0.2489");
                        map.put("0.35","0.2629");
                        map.put("0.36","0.2772");
                        map.put("0.37","0.2918");
                        map.put("0.38","0.3066");
                        map.put("0.39","0.3217");
                        map.put("0.4","0.337");
                        map.put("0.41","0.3525");
                        map.put("0.42","0.3682");
                        map.put("0.43","0.3842");
                        map.put("0.44","0.4003");
                        map.put("0.45","0.4165");
                        map.put("0.46","0.433"
                        );map.put("0.47","0.4495");
                        map.put("0.48","0.4662");
                        map.put("0.49","0.4831");
                        map.put("0.5","0.5"
                                );map.put("0.51","0.517"
                        );map.put("0.52","0.5341");
                        map.put("0.53","0.5513");
                        map.put("0.54","0.5685");
                        map.put("0.55","0.5857");
                        map.put("0.56","0.603"
                        );map.put("0.57","0.6202");
                        map.put("0.58","0.6375");
                        map.put("0.59","0.6547");
                        map.put("0.6","0.6718"
                        );map.put("0.61","0.6889");
                        map.put("0.62","0.706"
                        );map.put("0.63","0.7229");
                        map.put("0.64","0.7397");
                        map.put("0.65","0.7564");
                        map.put("0.66","0.7729");
                        map.put("0.67","0.7893");
                        map.put("0.68","0.8055");
                        map.put("0.69","0.8215");
                        map.put("0.7","0.8372"
                        );map.put("0.71","0.8527");
                        map.put("0.72","0.868"
                        );map.put("0.73","0.8829");
                        map.put("0.74","0.8976");
                        map.put("0.75","0.9119");
                        map.put("0.76","0.9258");
                        map.put("0.77","0.9394");
                        map.put("0.78",".9525");
                        map.put("0.79","0.9652");
                        map.put("0.8","0.9775"
                        );map.put("0.81","0.9892");
                        map.put("0.82","1.0004");
                        map.put("0.83","1.011"
                        );map.put("0.84","1.0211");
                        map.put("0.85","1.0304");
                        map.put("0.86","1.0391");
                        map.put("0.87","1.0471");
                        map.put("0.88","1.0542");
                        map.put("0.89","1.0605");
                        map.put("0.9","1.0658"
                        );map.put("0.91","1.0701");
                        map.put("0.92","1.0733");
                        map.put("0.93","1.0752");
                        map.put("0.94","1.0757");
                        map.put("0.95","1.0745");
                        map.put("0.96","1.0714");
                        map.put("0.97","1.0657");
                        map.put("0.98","1.0567");
                        map.put("0.99","1.042"
                        );map.put("1.0","1");




                        for(int i = 1; i<=(sizeDates-1); i++){
                            double profundidad = Double.parseDouble(db.mostrarProfundidad(i));



                            double dd = profundidad/diametro ;
                            //Toast.makeText(TableOneCaudalChannelsActivity.this, ""+dd, Toast.LENGTH_SHORT).show();
                            boolean isExist = map.containsKey(String.valueOf(dd));
                            if(isExist){
                                //Toast.makeText(TableOneCaudalChannelsActivity.this, "dato encontrado", Toast.LENGTH_LONG).show();

                                double fqtnew = Double.parseDouble(map.get(String.valueOf(dd)));
                               db.updateFqtWithTable(i, String.valueOf(fqtnew));
                            }else{
                                Toast.makeText(TableOneCaudalChannelsActivity.this, "El dato D/d no coincide con los valores registrados previamente en la tabla, esto afectará los resultados negativamente, reviselos, por favor.", Toast.LENGTH_LONG).show();
                            }

                            double Fqt = Double.parseDouble(db.mostrarFqt(i));

                            BigDecimal Dd = new BigDecimal(dd).setScale(5, RoundingMode.HALF_UP);

                            double CaudalParcial = (caudal_total)*Fqt;
                            BigDecimal  Qls = new BigDecimal(CaudalParcial*1000).setScale(5, RoundingMode.HALF_UP);
                            double qls = 0.0;
                            BigDecimal CA = new BigDecimal(CaudalParcial).setScale(5, RoundingMode.HALF_UP);
                            double caudalParcialFinal = CaudalParcial;

                            db.insertarCalculos(i, String.valueOf(Dd),String.valueOf(caudal_totall),String.valueOf(CA), String.valueOf(Qls));
                        }
                        double sumaFinal = 0;
                        for(int ii = 1 ; ii<=(sizeDates-1);ii++){

                            double Qlsinicial = Double.parseDouble(db.mostrarSumQls(ii));

                            sumaFinal += Qlsinicial;
                            BigDecimal  Qls = new BigDecimal(sumaFinal).setScale(5, RoundingMode.HALF_UP);
                            String sumaFinalFinal = String.valueOf(Qls);
                            sumaFinal = Double.parseDouble(sumaFinalFinal);
                            if(ii==25){
                                Toast.makeText(TableOneCaudalChannelsActivity.this, ""+sumaFinal, Toast.LENGTH_LONG).show();
                            }
                        }

                        double Qp = sumaFinal/(sizeDates-1) ;
                        double nQp = Qp*(sizeDates-1);
                        double constante = volumentotal/nQp;
                        Text_view_qp_caudal_can.setText(String.valueOf(Qp));
                        Text_view_n_qp_caudal_can.setText(String.valueOf(nQp));
                        for(int i = 1; i<=(sizeDates-1); i++){
                            double Qlsi = Double.parseDouble(db.mostrarQls(i));
                            BigDecimal volumenAlicuotaa = new BigDecimal(Qlsi*constante).setScale(5, RoundingMode.HALF_UP);
                            double volumenAlicuota = Qlsi*constante;
                            db.insertarVolumenAlicuota(i, String.valueOf(volumenAlicuotaa));
                        }
                        onResume();
                        Toast.makeText(TableOneCaudalChannelsActivity.this, "Valores calculados, ya se puede generar el archivo ", Toast.LENGTH_LONG).show();
                    }catch (Exception e){
                        AlertDialog.Builder Bien = new AlertDialog.Builder(TableOneCaudalChannelsActivity.this);
                        Bien.setMessage("Parece que hay un error, revisar."+e)
                                .setCancelable(false)
                                .setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                                    @RequiresApi(api = Build.VERSION_CODES.M)
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {

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
                        Toast.makeText(TableOneCaudalChannelsActivity.this, " Error: "+e, Toast.LENGTH_SHORT).show();
                    }
                }

                //Toast.makeText(TableOneCaudalChannelsActivity.this, " "+area, Toast.LENGTH_SHORT).show();
            }
        });

        btn_delete_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder Bien = new AlertDialog.Builder(TableOneCaudalChannelsActivity.this);
                Bien.setMessage("Esta acción vaciará los datos registrados. ¿Deseas continuar?")
                        .setCancelable(false)
                        .setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                            @RequiresApi(api = Build.VERSION_CODES.M)
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Boolean delete =  dbCapture.deleteDates();
                                if(delete=true){
                                    Toast.makeText(TableOneCaudalChannelsActivity.this, "Datos eliminados.", Toast.LENGTH_SHORT).show();
                                    SharedPreferences preferencesObservacionesdelete = getSharedPreferences("Observaciones", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor observacionesName = preferencesObservacionesdelete.edit();
                                    observacionesName.putString("ObservacionOneCaudal", "");
                                    observacionesName.commit();
                                    edit_text_observaciones_Caudal_can_tabla1.setText("Sin datos editados");
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
        btn_add_data_can.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TableOneCaudalChannelsActivity.this, CaptureDateTableOneCaudalCanActivity.class);
                startActivity(intent);

            }
        });

        button_save_caudal_can_tabla1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Text_view_area_caudal_can.getText().toString().isEmpty() ||
                Text_view_radio_caudal_can.getText().toString().isEmpty() ||
                edit_text_codigo_Caudal_can_tabla1.getText().toString().isEmpty()){
                    Toast.makeText(TableOneCaudalChannelsActivity.this, " No has cálculado, o falta el código", Toast.LENGTH_SHORT).show();
                }else{
                    AlertDialog.Builder Bien = new AlertDialog.Builder(TableOneCaudalChannelsActivity.this);
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
                                        Toast.makeText(TableOneCaudalChannelsActivity.this, ""+e, Toast.LENGTH_SHORT).show();
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

        singin();
        activityResultLauncher=registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if(result.getResultCode()== Activity.RESULT_OK){
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){
                        if (Environment.isExternalStorageManager()){
                            Toast.makeText(TableOneCaudalChannelsActivity.this, "Permiso concedido, puedes continuar", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(TableOneCaudalChannelsActivity.this, "Permiso denegado, no puedes continuar.", Toast.LENGTH_SHORT).show();
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
        final String ObservacionesSaved = preferencesObservaciones.getString("ObservacionOneCaudal","");
        if(ObservacionesSaved.equals("")){
            edit_text_observaciones_Caudal_can_tabla1.setText("Sin datos editados");
        }else{
            edit_text_observaciones_Caudal_can_tabla1.setText(ObservacionesSaved);
        }
        listaArrayRegistros.clear();
        listaArrayRegistros = new ArrayList<>();

        dbCaptureTableOneCaudalCan dbCapture = new dbCaptureTableOneCaudalCan(TableOneCaudalChannelsActivity.this);
        int sizeData = dbCapture.showSizeDates();
        if(sizeData > 0){
            btn_delete_data.setVisibility(View.VISIBLE);
            edit_text_numero_Caudal_can_tabla1.setText(" "+sizeData);
            Calcular_tabla1_caudal_can_tabla1.setVisibility(View.VISIBLE);
            button_save_caudal_can_tabla1.setVisibility(View.VISIBLE);
        }else{
            Calcular_tabla1_caudal_can_tabla1.setVisibility(View.GONE);
            button_save_caudal_can_tabla1.setVisibility(View.GONE);
        }
        ListRegistroAdapterOneCan adapter = new ListRegistroAdapterOneCan(dbCapture.mostrarDatosTwo());

        listaRegistros.setAdapter(adapter);

        if(listaArrayRegistros.size() >= 49){
            btn_add_data_can.setVisibility(View.GONE);
        }


    }


    public static boolean contains(String[] arreglo, String codigo){
        return Arrays.asList(arreglo).contains(codigo);
    }

    private void verifyCodeInFile(){
        SharedPreferences preferencesId = getSharedPreferences("EquipoVeolia", Context.MODE_PRIVATE);
        final String nameTeam = preferencesId.getString("NombreEquipo","E1");
        File ruteFolder = new File(getFilesDir()+"/ArchivosGeneradosVeolia");
        File file = new File(ruteFolder, "ECaudalCanal1"+nameTeam+".csv");
        boolean isCreate = false;
        if(!ruteFolder.exists()){
            Toast.makeText(TableOneCaudalChannelsActivity.this, "No se encuentra el archivo...", Toast.LENGTH_SHORT).show();
        }else{
            String cadena;
            String[] arreglo = {};

            try{
                FileReader fileReader =  new FileReader(file);
                BufferedReader bufferedReader = new BufferedReader(fileReader);

                while((cadena = bufferedReader.readLine()) != null){
                    arreglo = cadena.split(",");
                }
                boolean value = contains(arreglo, edit_text_codigo_Caudal_can_tabla1.getText().toString());
                if(value){
                    AlertDialog.Builder Bien = new AlertDialog.Builder(TableOneCaudalChannelsActivity.this);
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
                saveFile();
                Toast.makeText(TableOneCaudalChannelsActivity.this, "nv", Toast.LENGTH_SHORT).show();
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
        File filecrate = new File(ruteFolder, "ECaudalCanal1"+nameTeam+".csv");
        String nameFileInDrive = "ECaudalCanal1"+nameTeam+".csv";
        try {

            filecrate.createNewFile();
            String[] Codigo = {edit_text_codigo_Caudal_can_tabla1.getText().toString()};
            String[] Diamtero_Canal = {edit_text_diametro_Caudal_can_tabla1.getText().toString()};
            String[] coeficiente = {edit_text_coeficiente_Caudal_can_tabla1.getText().toString()};
            String[] pendiente = {edit_text_pendiente_Caudal_can_tabla1.getText().toString()};
            String[] areatotal = {Text_view_area_caudal_can.getText().toString()};
            String[] radio = {Text_view_radio_caudal_can.getText().toString()};
            String[] volumen = {edit_text_volumen_Caudal_can_tabla1.getText().toString()};
            String[] numero = {edit_text_numero_Caudal_can_tabla1.getText().toString()};
            String[] qp = {Text_view_qp_caudal_can.getText().toString()};
            String[] nqp = {Text_view_n_qp_caudal_can.getText().toString()};
            String[] obs = {edit_text_observaciones_Caudal_can_tabla1.getText().toString()};

            CSVWriter fileWriter = new CSVWriter(new OutputStreamWriter(new FileOutputStream(filecrate, true),
                    "ISO-8859-1"), ',',
                    CSVWriter.NO_QUOTE_CHARACTER,
                    CSVWriter.DEFAULT_ESCAPE_CHARACTER,
                    CSVWriter.DEFAULT_LINE_END);


            dbCaptureTableOneCaudalCan dbCapture = new dbCaptureTableOneCaudalCan(TableOneCaudalChannelsActivity.this);
            int sizeDatess = dbCapture.showSizeDates();
            String[] nummuestras = {String.valueOf(sizeDatess-1)};
                 dbCaptureTableOneCaudalCan db = new dbCaptureTableOneCaudalCan(TableOneCaudalChannelsActivity.this);
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
                String[] Profundidad = new String[50];
                String[] Dd = new String[50];
                String[] CaudalTotal = new String[50];
                String[] Fqt = new String[50];
                String[] CaudalParcial = new String[50];
                String[] Qls = new String[50];
                String[] VolumenAlicuota = new String[50];

                try{
                    for(int i = 1 ; i<=50; i++){
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
                        Profundidad[i-1] = db.mostrarProfundidad(i);
                        Dd[i-1] = db.mostrarDd(i);
                        CaudalTotal[i-1] = db.mostrarCaudalTotal(i);
                        Fqt[i-1] = db.mostrarFqt(i);
                        CaudalParcial[i-1] = db.mostrarCaudalParcial(i);
                        Qls[i-1] = db.mostrarQls(i);
                        VolumenAlicuota[i-1] = db.mostrarVolumenAlicuota(i);
                    }
                }catch (Exception e){
                    Toast.makeText(TableOneCaudalChannelsActivity.this, " "+e, Toast.LENGTH_SHORT).show();
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
            String[]  par12 =  ArrayUtils.addAll(par11, Profundidad);
            String[]  par13 =  ArrayUtils.addAll(par12, Dd);
            String[]  par14 =  ArrayUtils.addAll(par13, CaudalTotal);
            String[] par15 = ArrayUtils.addAll(par14, Fqt);
            String[] par16 = ArrayUtils.addAll(par15, CaudalParcial);
            String[] par17 = ArrayUtils.addAll(par16, Qls);
            String[] par18 = ArrayUtils.addAll(par17, VolumenAlicuota);
            String[] par19 = ArrayUtils.addAll(par18, Diamtero_Canal);
            String[] par20 = ArrayUtils.addAll(par19, areatotal);
            String[] par21 = ArrayUtils.addAll(par20, radio);
            String[] par22 = ArrayUtils.addAll(par21, coeficiente);
            String[] par23 = ArrayUtils.addAll(par22, pendiente);
            String[] par24 = ArrayUtils.addAll(par23, volumen);
            String[] par25 = ArrayUtils.addAll(par24, nummuestras);
            String[] par26 = ArrayUtils.addAll(par25, qp);
            String[] par27 = ArrayUtils.addAll(par26, nqp);
            String[] infoCreate = ArrayUtils.addAll(par27, obs);
            fileWriter.writeNext(infoCreate);
            fileWriter.close();

            ProgressDialog progressDialog = new ProgressDialog(TableOneCaudalChannelsActivity.this);
            progressDialog.setTitle("Archivo creado, sincronizando con Google Drive.");
            progressDialog.setMessage("Por favor, espera.");
            progressDialog.show();


            try {
                driveServiceHelper.createFileDrive(nameFileInDrive, filecrate.getPath()).addOnSuccessListener(new OnSuccessListener<String>() {
                    @Override
                    public void onSuccess(String s) {
                        progressDialog.dismiss();
                        Toast.makeText(TableOneCaudalChannelsActivity.this, "El archivo ya está en Google Drive con tu cuenta de Google", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(TableOneCaudalChannelsActivity.this, "No se pudo subir el archivo a Google Drive", Toast.LENGTH_SHORT).show();
                    }
                });
            }catch (Exception e){
                progressDialog.dismiss();
                Toast.makeText(TableOneCaudalChannelsActivity.this, "Ocurrio un problema al subir el archivo a drive", Toast.LENGTH_SHORT).show();
            }





            Toast.makeText(TableOneCaudalChannelsActivity.this, "Datos agregados al archivo: " + filecrate.getPath(), Toast.LENGTH_LONG).show();
        }catch (IOException ie){

            Toast.makeText(TableOneCaudalChannelsActivity.this, "Ocurrio un problema al crear el archivo."+ ie, Toast.LENGTH_LONG).show();
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
                File filecrate = new File(ruteFolder, "ECaudalCanal1"+nameTeam+".csv");
                if(!filecrate.exists()){
                    filecrate.createNewFile();
                    String[] titles = {"No. Muestra", "Hora de toma", "T('C)", "pH Patrón Und. ph", "pH Patrón T('C)",
                            "ph Muestra Und. pH", "pH Muestra t('C)", "Oxígeno mg O2/L", "Oxígeno T(´C)", "Conductividad Und pH", "Conductividad T ('C)","Profundidad de agua",
                            "Caudal total (Qt)(m3/s)", "FQt" , "Caudal parcial (Qp)(m3/s)", "Q(L/s)","Volumen ALICUOTA", "Observaciones"};
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
                    Toast.makeText(TableOneCaudalChannelsActivity.this, "Por favor, acepta los permisos para la aplicación "+ app_name, Toast.LENGTH_LONG).show();
                    finish();
                }catch (Exception e){
                    Intent intent = new Intent();
                    intent.setAction(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                    activityResultLauncher.launch(intent);
                    Toast.makeText(TableOneCaudalChannelsActivity.this, "Por favor, acepta los permisos para la aplicación "+ app_name, Toast.LENGTH_LONG).show();
                    finish();
                }
            }


        }else{
            int permissionResult = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if(permissionResult == PackageManager.PERMISSION_DENIED){
                AlertDialog.Builder Bien = new AlertDialog.Builder(TableOneCaudalChannelsActivity.this);
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
                File filecrate = new File(ruteFolder, "ECaudalCanal1"+nameTeam+".csv");
                if(!filecrate.exists()){
                    try {
                        filecrate.createNewFile();
                        String[] titles = {"No. Muestra", "Hora de toma", "T('C)", "pH Patrón Und. ph", "pH Patrón T('C)",
                                "ph Muestra Und. pH", "pH Muestra t('C)", "Oxígeno mg O2/L", "Oxígeno T(´C)", "Conductividad Und pH", "Conductividad T ('C)","Profundidad de agua",
                                "Caudal total (Qt)(m3/s)", "FQt" , "Caudal parcial (Qp)(m3/s)", "Q(L/s)","Volumen ALICUOTA", "Observaciones"};
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
                        GoogleAccountCredential credential = GoogleAccountCredential.usingOAuth2(TableOneCaudalChannelsActivity.this, Collections.singleton(DriveScopes.DRIVE_FILE));
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