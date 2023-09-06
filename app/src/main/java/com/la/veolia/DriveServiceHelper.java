package com.la.veolia;


import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.api.client.http.FileContent;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;


import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class DriveServiceHelper {

    private final Executor mExecutor = Executors.newSingleThreadExecutor();
    private Drive mDriveService;

    public DriveServiceHelper(Drive mDrivService){
        this.mDriveService = mDrivService;
    }

    public Task<String> createFileDrive(String name, String filer){
        return  Tasks.call(mExecutor, () ->{
            File fileMetaData = new File();
            fileMetaData.setName(name);
            java.io.File  file = new java.io.File(filer);
            FileContent mediaContent = new FileContent("text/csv", file);

            File myFile = null;
            try{
               myFile = mDriveService.files().create(fileMetaData, mediaContent).execute();
            }catch (Exception e){
                e.printStackTrace();
            }
            if(myFile == null){
                throw  new IOException("No se encuentra el archivo que se desea enviar.");
            }
            return myFile.getId();



        });
    }


    public Task<String> createFileDriveFirma(String name, String filer){
        return  Tasks.call(mExecutor, () ->{
            File fileMetaData = new File();
            fileMetaData.setName(name+".png");
            java.io.File  file = new java.io.File(filer);
            FileContent mediaContent = new FileContent("image/png", file);

            File myFile = null;
            try{
                myFile = mDriveService.files().create(fileMetaData, mediaContent).execute();
            }catch (Exception e){
                e.printStackTrace();
            }
            if(myFile == null){
                throw  new IOException("No se encuentra el archivo que se desea enviar.");
            }
            return myFile.getId();



        });
    }

}
