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
import androidx.core.content.FileProvider;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;

import android.content.pm.PackageManager;

import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;

import org.apache.http.protocol.HTTP;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FormListActivity extends AppCompatActivity {
    DriveServiceHelper driveServiceHelper;
    TextView msgTeam;
    Button form_one, button_form2, form_two, form_three, form_five,
    form_four, form_six, send_files, send_principal_files;
    int REQUEST_CODE = 200;
    ActivityResultLauncher<Intent> activityResultLauncher;
    private GoogleSignInClient mGoogleSignInClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_list);
        //getSupportActionBar().hide();
        msgTeam = findViewById(R.id.msgGroup);
        form_one = findViewById(R.id.form_one);
        button_form2 = findViewById(R.id.button_form2);
        form_two = findViewById(R.id.form_two);
        form_three = findViewById(R.id.form_three);
        form_four = findViewById(R.id.form_four);
        form_five = findViewById(R.id.form_five);
        form_six = findViewById(R.id.form_six);
        send_files = findViewById(R.id.send_files);
        send_principal_files = findViewById(R.id.send_files_principal);
        SharedPreferences preferencesId = getSharedPreferences("EquipoVeolia", Context.MODE_PRIVATE);
        final String nameTeam = preferencesId.getString("NombreEquipo","Not Result");
        msgTeam.setText("Equipo actual: "+nameTeam);
        if(nameTeam.equals("EquipoEtiqueta")){
            form_one.setVisibility(View.GONE);
            form_two.setVisibility(View.GONE);
            form_three.setVisibility(View.GONE);
            form_four.setVisibility(View.GONE);
            form_five.setVisibility(View.GONE);
            form_six.setVisibility(View.GONE);
        }
        send_files.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadAllFiles();
            }
        });
        singin();
        send_principal_files.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(FormListActivity.this, R.style.BottomSheetDialogTheme);
                    View bottomSheetView = LayoutInflater.from(FormListActivity.this)
                            .inflate(
                                    R.layout.bottom_sheet_firma,
                                    (LinearLayout) bottomSheetDialog.findViewById(R.id.bottomSheetContainerFirma)
                            );

                    EditText codeFirma = bottomSheetView.findViewById(R.id.edit_text_codigo_send_firma);
                    Button action_send = bottomSheetView.findViewById(R.id.action_send);

                    action_send.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            try {
                                action_send.setEnabled(false);
                                String filePath = getFilesDir()+"/ArchivosGeneradosVeolia";
                                File dir = new File(filePath);
                                File file = new File(dir, codeFirma.getText().toString()+".png");
                                driveServiceHelper.createFileDriveFirma(codeFirma.getText().toString(), file.getPath()).addOnSuccessListener(new OnSuccessListener<String>() {
                                    @Override
                                    public void onSuccess(String s) {

                                        AlertDialog.Builder Bien = new AlertDialog.Builder(FormListActivity.this);
                                        Bien.setMessage("Se subió el archivo a Google Drive exitosamente")
                                                .setCancelable(false)
                                                .setPositiveButton("Entendido", new DialogInterface.OnClickListener() {
                                                    @RequiresApi(api = Build.VERSION_CODES.M)
                                                    @Override
                                                    public void onClick(DialogInterface dialogInterface, int i) {
                                                        bottomSheetDialog.dismiss();
                                                    }
                                                });
                                        AlertDialog Titulo = Bien.create();
                                        Titulo.setTitle("Bien!");
                                        Titulo.show();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {

                                        AlertDialog.Builder Bien = new AlertDialog.Builder(FormListActivity.this);
                                        Bien.setMessage("No se ha podido subir el archivo con este código, verificalo y vuelve a intentar.")
                                                .setCancelable(false)
                                                .setPositiveButton("Entendido", new DialogInterface.OnClickListener() {
                                                    @RequiresApi(api = Build.VERSION_CODES.M)
                                                    @Override
                                                    public void onClick(DialogInterface dialogInterface, int i) {
                                                        action_send.setEnabled(true);
                                                    }
                                                });
                                        AlertDialog Titulo = Bien.create();
                                        Titulo.setTitle("Ups!");
                                        Titulo.show();
                                    }
                                });
                            }catch (Exception e){
                                action_send.setEnabled(true);
                                Toast.makeText(FormListActivity.this, "Ocurrio un problema al subir el archivo a drive", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                    bottomSheetDialog.setContentView(bottomSheetView);
                    bottomSheetDialog.show();
                } catch (Exception e) {
                    AlertDialog.Builder Bien = new AlertDialog.Builder(FormListActivity.this);
                    Bien.setMessage(""+e)
                            .setCancelable(false)
                            .setPositiveButton("Entendido", new DialogInterface.OnClickListener() {
                                @RequiresApi(api = Build.VERSION_CODES.M)
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            });
                    AlertDialog Titulo = Bien.create();
                    Titulo.setTitle("Error");
                    Titulo.show();
                      }
            }
        });
        activityResultLauncher=registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if(result.getResultCode()== Activity.RESULT_OK){
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){
                        if (Environment.isExternalStorageManager()){
                            Toast.makeText(FormListActivity.this, "Permiso concedido, puedes continuar", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(FormListActivity.this, "Permiso denegado, no puedes continuar.", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                }
            }
        });
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        //Acciones de los botones en los cuales se repite la acción de abrir las actividades.
        form_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ChainCustodyActivity = new Intent(FormListActivity.this, com.la.veolia.ChainCustodyActivity.class);
                startActivity(ChainCustodyActivity);
            }
        });
        button_form2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent SampleLabelActivity = new Intent(FormListActivity.this, com.la.veolia.SampleLabelActivity.class);
                startActivity(SampleLabelActivity);
            }
        });
        form_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent SamplePlanActivity = new Intent(FormListActivity.this, com.la.veolia.SamplePlanActivity.class);
                startActivity(SamplePlanActivity);
            }
        });
        form_three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent CaudalVolActivity = new Intent(FormListActivity.this, com.la.veolia.CaudalVolActivity.class);
                startActivity(CaudalVolActivity);
            }
        });
        form_four.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent CaudalChannelsActivity = new Intent(FormListActivity.this, com.la.veolia.CaudalChannelsActivity.class);
                startActivity(CaudalChannelsActivity);
            }
        });
        form_five.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent MaterialCheckActivity = new Intent(FormListActivity.this, com.la.veolia.MaterialCheckActivity.class);
                startActivity(MaterialCheckActivity);
            }
        });
        form_six.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent FormatVisitActivity = new Intent(FormListActivity.this, com.la.veolia.FormatVisitActivity.class);
                startActivity(FormatVisitActivity);
            }
        });
        verifyPassExist();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case(R.id.action_salir):
                AlertDialog.Builder Bien = new AlertDialog.Builder(FormListActivity.this);
                Bien.setMessage("¿Quieres cerrar sesión?")
                        .setCancelable(false)
                        .setPositiveButton("Cerrar Sesión", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                mGoogleSignInClient.signOut();
                                Intent salir = new Intent(FormListActivity.this, MainActivity.class);
                                startActivity(salir);
                                finish();
                            }
                        }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                AlertDialog Titulo = Bien.create();
                Titulo.setTitle("Cerrar sesión!");
                Titulo.show();
                break;
            case(R.id.action_team):
                Intent intent = new Intent(FormListActivity.this, DeleteTeamActivity.class);
                startActivity(intent);
                //finish();
                break;
            case(R.id.action_password):
                AlertDialog.Builder Pass = new AlertDialog.Builder(FormListActivity.this);
                Pass.setMessage("A continuación se cambiará la contraseña de equipos (Recuerda, debes tener la contraseña actuál para poder realizar el cambio).")
                        .setCancelable(false)
                        .setPositiveButton("Continuar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(FormListActivity.this, PasswordActivity.class);
                                startActivity(intent);
                                //finish();
                            }
                        }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                AlertDialog PassTituloo = Pass.create();
                PassTituloo.setTitle("Cambiar contraseña!");
                PassTituloo.show();
                break;
        }

        return true;
    }
    private void verifyPassExist(){
        SharedPreferences preferencesId = getSharedPreferences("PasswordTeam", Context.MODE_PRIVATE);
        final String passTeam = preferencesId.getString("Pass","Not Result");
        if(passTeam.equals("Not Result")){
            Intent intent = new Intent(FormListActivity.this, PasswordActivity.class);
            startActivity(intent);
            //finish();
        }
    }
    private void verifyGroup(){
        SharedPreferences preferencesId = getSharedPreferences("EquipoVeolia", Context.MODE_PRIVATE);
        final String nameTeam = preferencesId.getString("NombreEquipo","Not Result");
        if(nameTeam.equals("Not Result")){
            Intent intent = new Intent(FormListActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
    @Override
    protected void onResume() {
        verifyPassExist();
        verifyGroup();
        super.onResume();
    }
    private void uploadAllFiles(){
        ProgressDialog progressDialog = new ProgressDialog(FormListActivity.this);
        progressDialog.setTitle("Sincronizando con Google Drive.");
        progressDialog.setMessage("Por favor, espera.");
        progressDialog.setCancelable(false);
        progressDialog.show();
        File ruteFolder = new File(getFilesDir()+"/ArchivosGeneradosVeolia");
        if (!ruteFolder.exists()) {
            ruteFolder.mkdirs();
        }
        SharedPreferences preferencesId = getSharedPreferences("EquipoVeolia", Context.MODE_PRIVATE);
        final String nameTeam = preferencesId.getString("NombreEquipo","Not Result");
        String etiquetaMuestreoName = "AEtiqueta"+nameTeam+".csv";
        File etiquetaMuestreofilecrate = new File(ruteFolder, "AEtiqueta"+nameTeam+".csv");




        String cadenaCustodiaName = "BCadena"+nameTeam+".csv";
        File cadenaCustodiafilecrate = new File(ruteFolder, "BCadena"+nameTeam+".csv");
        String cadenaCustodia2Name = "BCadena2"+nameTeam+".csv";
        File cadenaCustodia2filecrate = new File(ruteFolder, "BCadena2"+nameTeam+".csv");
        String cadenaCustodia1Name = "BCadena1"+nameTeam+".csv";
        File cadenaCustodia1filecrate = new File(ruteFolder, "BCadena1"+nameTeam+".csv");
        String planMuestreoName = "CPlan"+nameTeam+".csv";
        File planMuestreofilecrate = new File(ruteFolder, "CPlan"+nameTeam+".csv");



        String caudalVolName = "DCaudalVol"+nameTeam+".csv";
        File caudalVolfilecrate = new File(ruteFolder, "DCaudalVol"+nameTeam+".csv");
        String caudalVol1Name = "DCaudalVol1"+nameTeam+".csv";
        File caudalVol1filecrate = new File(ruteFolder, "DCaudalVol1"+nameTeam+".csv");
        String caudalVol2Name = "DCaudalVol2"+nameTeam+".csv";
        File caudalVol2filecrate = new File(ruteFolder, "DCaudalVol2"+nameTeam+".csv");
        String caudalVol3Name = "DCaudalVol3"+nameTeam+".csv";
        File caudalVol3filecrate = new File(ruteFolder, "DCaudalVol3"+nameTeam+".csv");



        String caudalCanName = "ECaudalCanal"+nameTeam+".csv";
        File caudalCanfilecrate = new File(ruteFolder, "ECaudalCanal"+nameTeam+".csv");
        String caudalCan1Name = "ECaudalCanal1"+nameTeam+".csv";
        File caudalCan1filecrate = new File(ruteFolder, "ECaudalCanal1"+nameTeam+".csv");
        String caudalCan2Name = "ECaudalCanal2"+nameTeam+".csv";
        File caudalCan2filecrate = new File(ruteFolder, "ECaudalCanal2"+nameTeam+".csv");
        String caudalCan3Name = "ECaudalCanal3"+nameTeam+".csv";
        File caudalCan3filecrate = new File(ruteFolder, "ECaudalCanal3"+nameTeam+".csv");




        String materialCheckName = "FChequeo"+nameTeam+".csv";
        File materialCheckfilecrate = new File(ruteFolder, "FChequeo"+nameTeam+".csv");
        String materialCheck1Name = "FChequeo1"+nameTeam+".csv";
        File materialCheck1filecrate = new File(ruteFolder, "FChequeo1"+nameTeam+".csv");
        String materialCheck2Name = "FChequeo2"+nameTeam+".csv";
        File materialCheck2filecrate = new File(ruteFolder, "FChequeo2"+nameTeam+".csv");
        String materialCheck3Name = "FChequeo3"+nameTeam+".csv";
        File materialCheck3filecrate = new File(ruteFolder, "FChequeo3"+nameTeam+".csv");
        String materialCheck4Name = "FChequeo4"+nameTeam+".csv";
        File materialCheck4filecrate = new File(ruteFolder, "FChequeo4"+nameTeam+".csv");
        String materialCheck5Name = "FChequeo5"+nameTeam+".csv";
        File materialCheck5filecrate = new File(ruteFolder, "FChequeo5"+nameTeam+".csv");





        String formatVisitName = "GVisitas"+nameTeam+".csv";
        File formatVisitfilecrate = new File(ruteFolder, "GVisitas"+nameTeam+".csv");
        String formatVisit1Name = "GVisitas1"+nameTeam+".csv";
        File formatVisit1filecrate = new File(ruteFolder, "GVisitas1"+nameTeam+".csv");
        String formatVisit2Name = "GVisitas2"+nameTeam+".csv";
        File formatVisit2filecrate = new File(ruteFolder, "GVisitas2"+nameTeam+".csv");
        String formatVisit3Name = "GVisitas3"+nameTeam+".csv";
        File formatVisit3filecrate = new File(ruteFolder, "GVisitas3"+nameTeam+".csv");
        String formatVisit4Name = "GVisitas4"+nameTeam+".csv";
        File formatVisit4filecrate = new File(ruteFolder, "GVisitas4"+nameTeam+".csv");



        try {
            driveServiceHelper.createFileDrive(etiquetaMuestreoName, etiquetaMuestreofilecrate.getPath()).addOnSuccessListener(new OnSuccessListener<String>() {
                @Override
                public void onSuccess(String s) {

                    //Toast.makeText(FormListActivity.this, "El archivo ya está en Google Drive con tu cuenta de Google", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                    //Toast.makeText(FormListActivity.this, "No se pudo subir el archivo a Google Drive", Toast.LENGTH_SHORT).show();
                }
            });



        }catch (Exception e){
            Toast.makeText(FormListActivity.this, "Ocurrio un problema al subir el archivo a drive", Toast.LENGTH_SHORT).show();
        }






        try {
            driveServiceHelper.createFileDrive(cadenaCustodiaName, cadenaCustodiafilecrate.getPath()).addOnSuccessListener(new OnSuccessListener<String>() {
                @Override
                public void onSuccess(String s) {

                    //Toast.makeText(FormListActivity.this, "El archivo ya está en Google Drive con tu cuenta de Google", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                    //Toast.makeText(FormListActivity.this, "No se pudo subir el archivo a Google Drive", Toast.LENGTH_SHORT).show();
                }
            });



        }catch (Exception e){
            Toast.makeText(FormListActivity.this, "Ocurrio un problema al subir el archivo a drive", Toast.LENGTH_SHORT).show();
        }
        try {
            driveServiceHelper.createFileDrive(cadenaCustodia1Name, cadenaCustodia1filecrate.getPath()).addOnSuccessListener(new OnSuccessListener<String>() {
                @Override
                public void onSuccess(String s) {

                    //Toast.makeText(FormListActivity.this, "El archivo ya está en Google Drive con tu cuenta de Google", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                    //Toast.makeText(FormListActivity.this, "No se pudo subir el archivo a Google Drive", Toast.LENGTH_SHORT).show();
                }
            });



        }catch (Exception e){
            Toast.makeText(FormListActivity.this, "Ocurrio un problema al subir el archivo a drive", Toast.LENGTH_SHORT).show();
        }
        try {
            driveServiceHelper.createFileDrive(cadenaCustodia2Name, cadenaCustodia2filecrate.getPath()).addOnSuccessListener(new OnSuccessListener<String>() {
                @Override
                public void onSuccess(String s) {

                    //Toast.makeText(FormListActivity.this, "El archivo ya está en Google Drive con tu cuenta de Google", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                    //Toast.makeText(FormListActivity.this, "No se pudo subir el archivo a Google Drive", Toast.LENGTH_SHORT).show();
                }
            });



        }catch (Exception e){
            Toast.makeText(FormListActivity.this, "Ocurrio un problema al subir el archivo a drive", Toast.LENGTH_SHORT).show();
        }





        try {
            driveServiceHelper.createFileDrive(planMuestreoName, planMuestreofilecrate.getPath()).addOnSuccessListener(new OnSuccessListener<String>() {
                @Override
                public void onSuccess(String s) {

                    //Toast.makeText(FormListActivity.this, "El archivo ya está en Google Drive con tu cuenta de Google", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                    //Toast.makeText(FormListActivity.this, "No se pudo subir el archivo a Google Drive", Toast.LENGTH_SHORT).show();
                }
            });



        }catch (Exception e){
            Toast.makeText(FormListActivity.this, "Ocurrio un problema al subir el archivo a drive", Toast.LENGTH_SHORT).show();
        }





        try {
            driveServiceHelper.createFileDrive(caudalVolName, caudalVolfilecrate.getPath()).addOnSuccessListener(new OnSuccessListener<String>() {
                @Override
                public void onSuccess(String s) {

                    //Toast.makeText(FormListActivity.this, "El archivo ya está en Google Drive con tu cuenta de Google", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                    //Toast.makeText(FormListActivity.this, "No se pudo subir el archivo a Google Drive", Toast.LENGTH_SHORT).show();
                }
            });
        }catch (Exception e){
            Toast.makeText(FormListActivity.this, "Ocurrio un problema al subir el archivo a drive", Toast.LENGTH_SHORT).show();
        }


        try {
            driveServiceHelper.createFileDrive(caudalVol1Name, caudalVol1filecrate.getPath()).addOnSuccessListener(new OnSuccessListener<String>() {
                @Override
                public void onSuccess(String s) {

                    //Toast.makeText(FormListActivity.this, "El archivo ya está en Google Drive con tu cuenta de Google", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                    //Toast.makeText(FormListActivity.this, "No se pudo subir el archivo a Google Drive", Toast.LENGTH_SHORT).show();
                }
            });
        }catch (Exception e){
            Toast.makeText(FormListActivity.this, "Ocurrio un problema al subir el archivo a drive", Toast.LENGTH_SHORT).show();
        }


        try {
            driveServiceHelper.createFileDrive(caudalVol2Name, caudalVol2filecrate.getPath()).addOnSuccessListener(new OnSuccessListener<String>() {
                @Override
                public void onSuccess(String s) {

                    //Toast.makeText(FormListActivity.this, "El archivo ya está en Google Drive con tu cuenta de Google", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                    //Toast.makeText(FormListActivity.this, "No se pudo subir el archivo a Google Drive", Toast.LENGTH_SHORT).show();
                }
            });
        }catch (Exception e){
            Toast.makeText(FormListActivity.this, "Ocurrio un problema al subir el archivo a drive", Toast.LENGTH_SHORT).show();
        }


        try {
            driveServiceHelper.createFileDrive(caudalVol3Name, caudalVol3filecrate.getPath()).addOnSuccessListener(new OnSuccessListener<String>() {
                @Override
                public void onSuccess(String s) {

                    //Toast.makeText(FormListActivity.this, "El archivo ya está en Google Drive con tu cuenta de Google", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                    //Toast.makeText(FormListActivity.this, "No se pudo subir el archivo a Google Drive", Toast.LENGTH_SHORT).show();
                }
            });
        }catch (Exception e){
            Toast.makeText(FormListActivity.this, "Ocurrio un problema al subir el archivo a drive", Toast.LENGTH_SHORT).show();
        }





        try {
            driveServiceHelper.createFileDrive(caudalCanName, caudalCanfilecrate.getPath()).addOnSuccessListener(new OnSuccessListener<String>() {
                @Override
                public void onSuccess(String s) {

                    //Toast.makeText(FormListActivity.this, "El archivo ya está en Google Drive con tu cuenta de Google", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                    //Toast.makeText(FormListActivity.this, "No se pudo subir el archivo a Google Drive", Toast.LENGTH_SHORT).show();
                }
            });



        }catch (Exception e){
            Toast.makeText(FormListActivity.this, "Ocurrio un problema al subir el archivo a drive", Toast.LENGTH_SHORT).show();
        }


        try {
            driveServiceHelper.createFileDrive(caudalCan1Name, caudalCan1filecrate.getPath()).addOnSuccessListener(new OnSuccessListener<String>() {
                @Override
                public void onSuccess(String s) {

                    //Toast.makeText(FormListActivity.this, "El archivo ya está en Google Drive con tu cuenta de Google", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                    //Toast.makeText(FormListActivity.this, "No se pudo subir el archivo a Google Drive", Toast.LENGTH_SHORT).show();
                }
            });



        }catch (Exception e){
            Toast.makeText(FormListActivity.this, "Ocurrio un problema al subir el archivo a drive", Toast.LENGTH_SHORT).show();
        }



        try {
            driveServiceHelper.createFileDrive(caudalCan2Name, caudalCan2filecrate.getPath()).addOnSuccessListener(new OnSuccessListener<String>() {
                @Override
                public void onSuccess(String s) {

                    //Toast.makeText(FormListActivity.this, "El archivo ya está en Google Drive con tu cuenta de Google", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                    //Toast.makeText(FormListActivity.this, "No se pudo subir el archivo a Google Drive", Toast.LENGTH_SHORT).show();
                }
            });



        }catch (Exception e){
            Toast.makeText(FormListActivity.this, "Ocurrio un problema al subir el archivo a drive", Toast.LENGTH_SHORT).show();
        }



        try {
            driveServiceHelper.createFileDrive(caudalCan3Name, caudalCan3filecrate.getPath()).addOnSuccessListener(new OnSuccessListener<String>() {
                @Override
                public void onSuccess(String s) {

                    //Toast.makeText(FormListActivity.this, "El archivo ya está en Google Drive con tu cuenta de Google", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                    //Toast.makeText(FormListActivity.this, "No se pudo subir el archivo a Google Drive", Toast.LENGTH_SHORT).show();
                }
            });



        }catch (Exception e){
            Toast.makeText(FormListActivity.this, "Ocurrio un problema al subir el archivo a drive", Toast.LENGTH_SHORT).show();
        }






        try {
            driveServiceHelper.createFileDrive(materialCheckName, materialCheckfilecrate.getPath()).addOnSuccessListener(new OnSuccessListener<String>() {
                @Override
                public void onSuccess(String s) {

                   // Toast.makeText(FormListActivity.this, "El archivo ya está en Google Drive con tu cuenta de Google", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                    //Toast.makeText(FormListActivity.this, "No se pudo subir el archivo a Google Drive", Toast.LENGTH_SHORT).show();
                }
            });



        }catch (Exception e){
            Toast.makeText(FormListActivity.this, "Ocurrio un problema al subir el archivo a drive", Toast.LENGTH_SHORT).show();
        }



        try {
            driveServiceHelper.createFileDrive(materialCheck1Name, materialCheck1filecrate.getPath()).addOnSuccessListener(new OnSuccessListener<String>() {
                @Override
                public void onSuccess(String s) {

                    // Toast.makeText(FormListActivity.this, "El archivo ya está en Google Drive con tu cuenta de Google", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                    //Toast.makeText(FormListActivity.this, "No se pudo subir el archivo a Google Drive", Toast.LENGTH_SHORT).show();
                }
            });



        }catch (Exception e){
            Toast.makeText(FormListActivity.this, "Ocurrio un problema al subir el archivo a drive", Toast.LENGTH_SHORT).show();
        }




        try {
            driveServiceHelper.createFileDrive(materialCheck2Name, materialCheck2filecrate.getPath()).addOnSuccessListener(new OnSuccessListener<String>() {
                @Override
                public void onSuccess(String s) {

                    // Toast.makeText(FormListActivity.this, "El archivo ya está en Google Drive con tu cuenta de Google", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                    //Toast.makeText(FormListActivity.this, "No se pudo subir el archivo a Google Drive", Toast.LENGTH_SHORT).show();
                }
            });



        }catch (Exception e){
            Toast.makeText(FormListActivity.this, "Ocurrio un problema al subir el archivo a drive", Toast.LENGTH_SHORT).show();
        }





        try {
            driveServiceHelper.createFileDrive(materialCheck3Name, materialCheck3filecrate.getPath()).addOnSuccessListener(new OnSuccessListener<String>() {
                @Override
                public void onSuccess(String s) {

                    // Toast.makeText(FormListActivity.this, "El archivo ya está en Google Drive con tu cuenta de Google", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                    //Toast.makeText(FormListActivity.this, "No se pudo subir el archivo a Google Drive", Toast.LENGTH_SHORT).show();
                }
            });



        }catch (Exception e){
            Toast.makeText(FormListActivity.this, "Ocurrio un problema al subir el archivo a drive", Toast.LENGTH_SHORT).show();
        }




        try {
            driveServiceHelper.createFileDrive(materialCheck4Name, materialCheck4filecrate.getPath()).addOnSuccessListener(new OnSuccessListener<String>() {
                @Override
                public void onSuccess(String s) {

                    // Toast.makeText(FormListActivity.this, "El archivo ya está en Google Drive con tu cuenta de Google", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                    //Toast.makeText(FormListActivity.this, "No se pudo subir el archivo a Google Drive", Toast.LENGTH_SHORT).show();
                }
            });



        }catch (Exception e){
            Toast.makeText(FormListActivity.this, "Ocurrio un problema al subir el archivo a drive", Toast.LENGTH_SHORT).show();
        }




        try {
            driveServiceHelper.createFileDrive(materialCheck5Name, materialCheck5filecrate.getPath()).addOnSuccessListener(new OnSuccessListener<String>() {
                @Override
                public void onSuccess(String s) {

                    // Toast.makeText(FormListActivity.this, "El archivo ya está en Google Drive con tu cuenta de Google", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                    //Toast.makeText(FormListActivity.this, "No se pudo subir el archivo a Google Drive", Toast.LENGTH_SHORT).show();
                }
            });



        }catch (Exception e){
            Toast.makeText(FormListActivity.this, "Ocurrio un problema al subir el archivo a drive", Toast.LENGTH_SHORT).show();
        }






        try {
            driveServiceHelper.createFileDrive(formatVisitName, formatVisitfilecrate.getPath()).addOnSuccessListener(new OnSuccessListener<String>() {
                @Override
                public void onSuccess(String s) {
                    progressDialog.dismiss();
                    //Toast.makeText(FormListActivity.this, "Sincronización finalizada", Toast.LENGTH_LONG).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                    //Toast.makeText(FormListActivity.this, "Sincronización finalizada", Toast.LENGTH_SHORT).show();
                }
            });



        }catch (Exception e){
            progressDialog.dismiss();
            //Toast.makeText(FormListActivity.this, "Ocurrio un problema al subir el archivo a drive", Toast.LENGTH_SHORT).show();
        }



        try {
            driveServiceHelper.createFileDrive(formatVisit1Name, formatVisit1filecrate.getPath()).addOnSuccessListener(new OnSuccessListener<String>() {
                @Override
                public void onSuccess(String s) {
                    progressDialog.dismiss();
                    //Toast.makeText(FormListActivity.this, "Sincronización finalizada", Toast.LENGTH_LONG).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                    //Toast.makeText(FormListActivity.this, "Sincronización finalizada", Toast.LENGTH_SHORT).show();
                }
            });



        }catch (Exception e){
            progressDialog.dismiss();
            //Toast.makeText(FormListActivity.this, "Ocurrio un problema al subir el archivo a drive", Toast.LENGTH_SHORT).show();
        }




        try {
            driveServiceHelper.createFileDrive(formatVisit2Name, formatVisit2filecrate.getPath()).addOnSuccessListener(new OnSuccessListener<String>() {
                @Override
                public void onSuccess(String s) {
                    progressDialog.dismiss();
                    //Toast.makeText(FormListActivity.this, "Sincronización finalizada", Toast.LENGTH_LONG).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                    //Toast.makeText(FormListActivity.this, "Sincronización finalizada", Toast.LENGTH_SHORT).show();
                }
            });



        }catch (Exception e){
            progressDialog.dismiss();
            //Toast.makeText(FormListActivity.this, "Ocurrio un problema al subir el archivo a drive", Toast.LENGTH_SHORT).show();
        }




        try {
            driveServiceHelper.createFileDrive(formatVisit3Name, formatVisit3filecrate.getPath()).addOnSuccessListener(new OnSuccessListener<String>() {
                @Override
                public void onSuccess(String s) {
                    progressDialog.dismiss();
                    //Toast.makeText(FormListActivity.this, "Sincronización finalizada", Toast.LENGTH_LONG).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                    //Toast.makeText(FormListActivity.this, "Sincronización finalizada", Toast.LENGTH_SHORT).show();
                }
            });



        }catch (Exception e){
            progressDialog.dismiss();
            //Toast.makeText(FormListActivity.this, "Ocurrio un problema al subir el archivo a drive", Toast.LENGTH_SHORT).show();
        }




        try {
            driveServiceHelper.createFileDrive(formatVisit4Name, formatVisit4filecrate.getPath()).addOnSuccessListener(new OnSuccessListener<String>() {
                @Override
                public void onSuccess(String s) {
                    progressDialog.dismiss();
                    //Toast.makeText(FormListActivity.this, "Sincronización finalizada", Toast.LENGTH_LONG).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                    //Toast.makeText(FormListActivity.this, "Sincronización finalizada", Toast.LENGTH_SHORT).show();
                }
            });



        }catch (Exception e){
            progressDialog.dismiss();
            //Toast.makeText(FormListActivity.this, "Ocurrio un problema al subir el archivo a drive", Toast.LENGTH_SHORT).show();
        }


        Toast.makeText(FormListActivity.this, "Sincronización finalizada", Toast.LENGTH_SHORT).show();

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
                        GoogleAccountCredential credential = GoogleAccountCredential.usingOAuth2(FormListActivity.this, Collections.singleton(DriveScopes.DRIVE_FILE));
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