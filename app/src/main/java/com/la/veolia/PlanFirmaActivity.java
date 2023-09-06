package com.la.veolia;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.UUID;

public class PlanFirmaActivity extends AppCompatActivity {
    DriveServiceHelper driveServiceHelper;
    int REQUEST_CODE = 200;
    ActivityResultLauncher<Intent> activityResultLauncher;
    private Lienzo lienzo;
    Button button_save_Plan, button_clear_Plan;
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_firma);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        try{
            lienzo = findViewById(R.id.lienzo);
        }catch (Exception e){
            Toast.makeText(PlanFirmaActivity.this, " "+e, Toast.LENGTH_SHORT).show();
        }

        button_save_Plan = findViewById(R.id.button_save_Plan);
        button_clear_Plan = findViewById(R.id.button_clear_Plan);
        //Con este metodo se guardará la imagen de la firma dibujada por el usuario.
        button_save_Plan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameImage = getIntent().getStringExtra("codigo_plan_muestreo");
                lienzo.setDrawingCacheEnabled(true);

                String filePath = getFilesDir()+"/ArchivosGeneradosVeolia";
                File dir = new File(filePath);
                if(!dir.exists()){
                    dir.mkdirs();
                }
                File file = new File(dir, nameImage+".png");
                try{
                    FileOutputStream fOut = new FileOutputStream(file);

                    boolean imgSave = lienzo.getDrawingCache().compress(Bitmap.CompressFormat.PNG, 85, fOut);
                    fOut.flush();
                    fOut.close();
                    if(imgSave){
                        Toast.makeText(PlanFirmaActivity.this, "Imagen guardada en:  " + filePath, Toast.LENGTH_SHORT).show();
                        ProgressDialog progressDialog = new ProgressDialog(PlanFirmaActivity.this);
                        progressDialog.setTitle("Archivo creado, sincronizando con Google Drive.");
                        progressDialog.setMessage("Por favor, espera.");
                        progressDialog.show();


                        try {
                            driveServiceHelper.createFileDriveFirma(nameImage, file.getPath()).addOnSuccessListener(new OnSuccessListener<String>() {
                                @Override
                                public void onSuccess(String s) {
                                    progressDialog.dismiss();
                                    Toast.makeText(PlanFirmaActivity.this, "El archivo ya está en Google Drive con tu cuenta de Google", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    progressDialog.dismiss();
                                    Toast.makeText(PlanFirmaActivity.this, "No se pudo subir el archivo a Google Drive", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }catch (Exception e){
                            progressDialog.dismiss();
                            Toast.makeText(PlanFirmaActivity.this, "Ocurrio un problema al subir el archivo a drive", Toast.LENGTH_SHORT).show();
                        }

                    }else{
                        Toast.makeText(PlanFirmaActivity.this, "Error al guardar imagen de firma", Toast.LENGTH_SHORT).show();
                    }
                }catch (IOException e){

                }



/*

                String imgSave = MediaStore.Images.Media.insertImage(getContentResolver(), lienzo.getDrawingCache(), nameImage+".png", "drawing");
                if(imgSave!=null){ Toast.makeText(PlanFirmaActivity.this, "Imagen guardada.", Toast.LENGTH_SHORT).show(); }else{
                    Toast.makeText(PlanFirmaActivity.this, "Error al guardad imagen de firma", Toast.LENGTH_SHORT).show(); }
                lienzo.destroyDrawingCache();
                */

            }
        });
        button_clear_Plan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lienzo.NuevoDibujo();
            }
        });


        singin();
        activityResultLauncher=registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if(result.getResultCode()== Activity.RESULT_OK){
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){
                        if (Environment.isExternalStorageManager()){
                            Toast.makeText(PlanFirmaActivity.this, "Permiso concedido, puedes continuar", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(PlanFirmaActivity.this, "Permiso denegado, no puedes continuar.", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                }
            }
        });
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
                        GoogleAccountCredential credential = GoogleAccountCredential.usingOAuth2(PlanFirmaActivity.this, Collections.singleton(DriveScopes.DRIVE_FILE));
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