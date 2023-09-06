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
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Switch;
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
import com.google.api.client.json.*;

import java.util.Collections;


public class MainActivity extends AppCompatActivity {

    DriveServiceHelper driveServiceHelper;
    Button btnIndex;
    Spinner spinner_equipo;
    TextView msgTeam;
    ProgressBar progressBar_main;
    int REQUEST_CODE = 200;
    ActivityResultLauncher<Intent> activityResultLauncher;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        btnIndex = findViewById(R.id.btnIndex);
        msgTeam = findViewById(R.id.msgTeam);
        spinner_equipo = findViewById(R.id.spinner_equipo);
        progressBar_main = findViewById(R.id.progressBar_main);
        singin();
        showSelectTeam();
        btnIndex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startRegisters();
            }
        });
        activityResultLauncher=registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if(result.getResultCode()== Activity.RESULT_OK){
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){
                        if (Environment.isExternalStorageManager()){
                            Toast.makeText(MainActivity.this, "Permiso concedido, puedes continuar", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(MainActivity.this, "Permiso denegado, no puedes continuar.", Toast.LENGTH_SHORT).show();
                            btnIndex.setVisibility(View.GONE);
                        }
                    }
                }
            }
        });
        manageStoragePerimissionA11();
    }
    private void permissionStorage(){
        int permissionResult = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if(permissionResult == PackageManager.PERMISSION_DENIED){
            AlertDialog.Builder Bien = new AlertDialog.Builder(MainActivity.this);
            Bien.setMessage("A continuación se solicíta el pérmiso para creár los archivos en la carpeta raíz de este dispositivo, se sugíere concederlos para garántizar el búen fúncionamiento de la aplicación.")
                    .setCancelable(false)
                    .setPositiveButton("Continúar con el pérmiso.", new DialogInterface.OnClickListener() {
                        @RequiresApi(api = Build.VERSION_CODES.M)
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE);
                        }
                    });
            AlertDialog Titulo = Bien.create();
            Titulo.setTitle("Concede el permiso!");
            Titulo.show();
        }else{

        }
    }
    private void manageStoragePerimissionA11(){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.R){
            
        }else{
            permissionStorage();
        }
    }



    private void startRegisters(){
        SharedPreferences preferencesId = getSharedPreferences("EquipoVeolia", Context.MODE_PRIVATE);
        final String nameTeam = preferencesId.getString("NombreEquipo","Not Result");

        if(nameTeam.equals("Not Result")) {
            String equipoSeleccionado = spinner_equipo.getSelectedItem().toString();
            if (equipoSeleccionado.equals("")) {
                Toast.makeText(MainActivity.this, "Debes seleccionar un equipo", Toast.LENGTH_SHORT).show();
            } else {
                SharedPreferences preferencesTema = getSharedPreferences("EquipoVeolia", Context.MODE_PRIVATE);
                String nombreEquipo = "";
                switch (equipoSeleccionado) {
                    case "Equipo etiqueta":
                        nombreEquipo = "EquipoEtiqueta";
                        break;
                    case "Equipo 1":
                        nombreEquipo = "E1";
                        break;
                    case "Equipo 2":
                        nombreEquipo = "E2";
                        break;
                    case "Equipo 3":
                        nombreEquipo = "E3";
                        break;
                    case "Equipo 4":
                        nombreEquipo = "E4";
                        break;
                    case "Equipo 5":
                        nombreEquipo = "E5";
                        break;
                    case "Equipo 6":
                        nombreEquipo = "E6";
                        break;
                    case "Equipo 7":
                        nombreEquipo = "E7";
                        break;
                    case "Equipo 8":
                        nombreEquipo = "E8";
                        break;
                    case "Equipo 9":
                        nombreEquipo = "E9";
                        break;
                    case "Equipo 10":
                        nombreEquipo = "E10";
                        break;
                }
                SharedPreferences.Editor editorTema = preferencesTema.edit();
                editorTema.putString("NombreEquipo", nombreEquipo);
                editorTema.commit();
                Intent intent = new Intent(MainActivity.this, FormListActivity.class);
                startActivity(intent);
                finish();
            }
        }else{
            Intent intent = new Intent(MainActivity.this, FormListActivity.class);
            startActivity(intent);
            finish();
        }
    }


    private void showSelectTeam(){
        SharedPreferences preferencesId = getSharedPreferences("EquipoVeolia", Context.MODE_PRIVATE);
        final String nameTeam = preferencesId.getString("NombreEquipo","Not Result");

        if(nameTeam.equals("Not Result")) {
            spinner_equipo.setVisibility(View.VISIBLE);
            msgTeam.setVisibility(View.VISIBLE);
        }else{
            spinner_equipo.setVisibility(View.GONE);
            msgTeam.setVisibility(View.GONE);
        }
    }

    private void singin(){
        btnIndex.setVisibility(View.GONE);
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
                        GoogleAccountCredential credential = GoogleAccountCredential.usingOAuth2(MainActivity.this, Collections.singleton(DriveScopes.DRIVE_FILE));
                        credential.setSelectedAccount(googleSignInAccount.getAccount());
                        Toast.makeText(MainActivity.this, "Se inicio sesión satisfactoriamente", Toast.LENGTH_SHORT).show();
                        Drive googleDriveService = new Drive.Builder(
                                AndroidHttp.newCompatibleTransport(),
                                new GsonFactory(),
                                credential)
                                .setApplicationName("Drive upload Veolia documents.")
                                .build();
                        driveServiceHelper = new DriveServiceHelper(googleDriveService);
                        progressBar_main.setVisibility(View.GONE);
                        btnIndex.setVisibility(View.VISIBLE);

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressBar_main.setVisibility(View.GONE);
                        btnIndex.setVisibility(View.VISIBLE);
                        Toast.makeText(MainActivity.this, "No se pudo iniciar sesión, los datos no se guardarán en Drive.", Toast.LENGTH_LONG).show();
                    }
                });
    }
}