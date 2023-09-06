package com.la.veolia.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.la.veolia.entitys.RegistroTableFourMaterialCheck;
import com.la.veolia.entitys.RegistroTableThreeMaterialCheck;
import com.la.veolia.entitys.RegistrosTableOneCaudalCan;

import java.util.ArrayList;

public class dbCaptureTableFourMaterialCheck extends DbHelper{
    Context context;
    public dbCaptureTableFourMaterialCheck(@Nullable Context context) {

        super(context);
        this.context = context;
    }

    public  long insertarDatos(String item, String se_requiere, String cantidad, String revision_salida,
                               String revision_campo, String revision_llegada){

        long id = 0;


        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("item", item);
        values.put("se_requiere", se_requiere);
        values.put("cantidad", cantidad);
        values.put("revision_salida", revision_salida);
        values.put("revision_campo", revision_campo);
        values.put("revision_llegada", revision_llegada);
        id = db.insert(TABLE_TABLE_FIVE, null, values);

        return id;

    }



    public  Boolean deleteRegister(int id){

        Boolean result = false;


        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try{
            db.execSQL(" DELETE FROM "+TABLE_TABLE_FIVE + " WHERE id_to =" + id);
            db.execSQL(" DELETE FROM SQLITE_SEQUENCE WHERE NAME = '"+TABLE_TABLE_FOUR +"'");
            result = true;
        }catch (Exception e){
            result = false;
        }finally {
            //db.close();
        }
        return result;
    }



    public String mostrarItem(int i){
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String listaNumerosMuestra = "";
        RegistroTableFourMaterialCheck registrosTableOneCaudalCan = null;
        Cursor cursor = null;

        cursor = db.rawQuery("SELECT * FROM " + TABLE_TABLE_FIVE + " WHERE id_to = "+i, null);

            if(cursor.moveToFirst()){
                do{
                    listaNumerosMuestra = cursor.getString(1);
                }while(cursor.moveToNext());
        }
        cursor.close();
        return listaNumerosMuestra;
    }


    public String mostrarSeRequiere(int i){
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String listaNumerosMuestra = "";
        RegistroTableFourMaterialCheck registrosTableOneCaudalCan = null;
        Cursor cursor = null;

        cursor = db.rawQuery("SELECT * FROM " + TABLE_TABLE_FIVE + " WHERE id_to = "+i, null);

        if(cursor.moveToFirst()){
            do{
                listaNumerosMuestra = cursor.getString(2);
            }while(cursor.moveToNext());
        }
        cursor.close();
        return listaNumerosMuestra;
    }


    public String mostrarCantidad(int i){
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String listaNumerosMuestra = "";
        RegistroTableFourMaterialCheck registrosTableOneCaudalCan = null;
        Cursor cursor = null;

        cursor = db.rawQuery("SELECT * FROM " + TABLE_TABLE_FIVE + " WHERE id_to = "+i, null);

        if(cursor.moveToFirst()){
            do{
                listaNumerosMuestra = cursor.getString(3);
            }while(cursor.moveToNext());
        }
        cursor.close();
        return listaNumerosMuestra;
    }




    public String mostrarRevisionSalida(int i){
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String listaNumerosMuestra = "";
        RegistroTableFourMaterialCheck registrosTableOneCaudalCan = null;
        Cursor cursor = null;

        cursor = db.rawQuery("SELECT * FROM " + TABLE_TABLE_FIVE + " WHERE id_to = "+i, null);

        if(cursor.moveToFirst()){
            do{
                listaNumerosMuestra = cursor.getString(4);
            }while(cursor.moveToNext());
        }
        cursor.close();
        return listaNumerosMuestra;
    }



    public String mostrarRevisionCampo(int i){
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String listaNumerosMuestra = "";
        RegistroTableFourMaterialCheck registrosTableOneCaudalCan = null;
        Cursor cursor = null;

        cursor = db.rawQuery("SELECT * FROM " + TABLE_TABLE_FIVE + " WHERE id_to = "+i, null);

        if(cursor.moveToFirst()){
            do{
                listaNumerosMuestra = cursor.getString(5);
            }while(cursor.moveToNext());
        }
        cursor.close();
        return listaNumerosMuestra;
    }


    public String mostrarRevisionLlegada(int i){
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String listaNumerosMuestra = "";
        RegistroTableFourMaterialCheck registrosTableOneCaudalCan = null;
        Cursor cursor = null;

        cursor = db.rawQuery("SELECT * FROM " + TABLE_TABLE_FIVE + " WHERE id_to = "+i, null);

        if(cursor.moveToFirst()){
            do{
                listaNumerosMuestra = cursor.getString(6);
            }while(cursor.moveToNext());
        }
        cursor.close();
        return listaNumerosMuestra;
    }


    public int showSizeDates(){
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int sizeDates = 0;
        Cursor cursor = null;

        cursor = db.rawQuery("SELECT  COUNT(id_to) FROM " + TABLE_TABLE_FIVE, null);

        if(cursor.moveToFirst()){
            do{

                sizeDates = cursor.getInt(0);
            }while(cursor.moveToNext());
        }
        cursor.close();
        return sizeDates;
    }


    public  Boolean deleteDates(){

        Boolean result = false;


        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try{
            db.execSQL(" DELETE FROM "+TABLE_TABLE_FIVE);
            db.execSQL(" DELETE FROM SQLITE_SEQUENCE WHERE NAME = '"+TABLE_TABLE_FIVE +"'");
            result = true;
        }catch (Exception e){
            result = false;
        }finally {
            //db.close();
        }
        return result;
    }


    public ArrayList<RegistroTableFourMaterialCheck> mostrarDatos(){
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();


        ArrayList<RegistroTableFourMaterialCheck> listaRegistros = new ArrayList<>();
        RegistroTableFourMaterialCheck registroTableMaterialCheck = null;
        Cursor cursor = null;

        cursor = db.rawQuery("SELECT * FROM " + TABLE_TABLE_FIVE, null);

        if(cursor.moveToFirst()){
            do{
                registroTableMaterialCheck = new RegistroTableFourMaterialCheck();
                registroTableMaterialCheck.setId(cursor.getInt(0));
                registroTableMaterialCheck.setItem(cursor.getString(1));
                registroTableMaterialCheck.setSe_requiere(cursor.getString(2));
                registroTableMaterialCheck.setCantidad(cursor.getString(3));
                registroTableMaterialCheck.setRevision_salida(cursor.getString(4));
                registroTableMaterialCheck.setRevision_campo(cursor.getString(5));
                registroTableMaterialCheck.setRevision_llegada(cursor.getString(6));
                listaRegistros.add(registroTableMaterialCheck);
            }while(cursor.moveToNext());
        }
        cursor.close();
        return listaRegistros;
    }


    public  boolean updateDates(String item, String se_requiere, int id,  String rs, String rc, String rl, String cantidad){

        boolean result = false;


        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try{
            db.execSQL(" UPDATE "+ TABLE_TABLE_FIVE + " SET item = '"+ item + "',  se_requiere = '"+ se_requiere + "', cantidad = '"+ cantidad + "',revision_salida = '"+ rs + "', revision_campo = '"+ rc + "', revision_llegada = '"+ rl + "'  WHERE id_to =  "+ id);
            result = true;
        }catch (Exception e){
            result = false;
        }
        return result;
    }
}
