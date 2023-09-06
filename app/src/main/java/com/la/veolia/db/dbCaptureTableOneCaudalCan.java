package com.la.veolia.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.la.veolia.CaptureDatesTableOneCaudalVolActivity;
import com.la.veolia.entitys.RegistrosTableOneCaudalCan;
import com.la.veolia.entitys.RegistrosTableOneCaudalVol;

import java.util.ArrayList;

public class dbCaptureTableOneCaudalCan extends DbHelper{
    Context context;
    public dbCaptureTableOneCaudalCan(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    public long insertarDatos(String numero_muestra, String hora_toma, String tc, String ph_patron_und, String ph_patron_tc,
                              String ph_muestra_und, String ph_muestra_tc, String oxigeno_mg, String oxigeno_tc,
                              String conductivida_und, String conductividad_tc, String profundidad_agua, String fqt){
        long id = 0;


        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("numero_muestra", numero_muestra);
        values.put("hora_toma", hora_toma);
        values.put("tc", tc);
        values.put("ph_patron_und",  ph_patron_und);
        values.put("ph_patron_tc", ph_patron_tc);
        values.put("ph_muestra_und", ph_muestra_und);
        values.put("ph_muestra_tc", ph_muestra_tc);
        values.put("oxigeno_mg", oxigeno_mg);
        values.put("oxigeno_tc", oxigeno_tc);
        values.put("conductividad_und", conductivida_und);
        values.put("conductividad_tc", conductividad_tc);
        values.put("profundidad_agua", profundidad_agua);
        values.put("d_d", "No calculado");
        values.put("caudal_total", "No calculado");
        values.put("fqt",fqt);
        values.put("caudal_parcial", "No calculado");
        values.put("qls", "No calculado");
        values.put("volumen_alicuota", "No calculado");

        id = db.insert(TABLE_TABLE_TWO, null, values);

        return id;
    }


public ArrayList<RegistrosTableOneCaudalCan> mostrarDatosTwo(){
        DbHelper dbHelper = new DbHelper(context);
    SQLiteDatabase db = dbHelper.getWritableDatabase();
    ArrayList<RegistrosTableOneCaudalCan> listaRegistros = new ArrayList<>();
    RegistrosTableOneCaudalCan registrosTableOneCaudalCan = null;
    Cursor cursor = null;

    cursor = db.rawQuery("SELECT * FROM " + TABLE_TABLE_TWO, null);

    if(cursor.moveToFirst()){
        do{
            registrosTableOneCaudalCan = new RegistrosTableOneCaudalCan();
            registrosTableOneCaudalCan.setId(cursor.getInt(0));
            registrosTableOneCaudalCan.setNumero_muestra(cursor.getString(1));
            registrosTableOneCaudalCan.setHora_toma(cursor.getString(2));
            registrosTableOneCaudalCan.setTc(cursor.getString(3));
            registrosTableOneCaudalCan.setPh_patron_und(cursor.getString(4));
            registrosTableOneCaudalCan.setPh_patron_tc(cursor.getString(5));
            registrosTableOneCaudalCan.setPh_muestra_und(cursor.getString(6));
            registrosTableOneCaudalCan.setPh_muestra_tc(cursor.getString(7));
            registrosTableOneCaudalCan.setOxigeno_mg(cursor.getString(8));
            registrosTableOneCaudalCan.setOxigeno_tc(cursor.getString(9));
            registrosTableOneCaudalCan.setConductividad_und(cursor.getString(10));
            registrosTableOneCaudalCan.setConductividad_tc(cursor.getString(11));
            registrosTableOneCaudalCan.setProdundidad_agua(cursor.getString(12));
            registrosTableOneCaudalCan.setDd(cursor.getString(13));
            registrosTableOneCaudalCan.setCaudal_total(cursor.getString(14));
            registrosTableOneCaudalCan.setFqt(cursor.getString(15));
            registrosTableOneCaudalCan.setCaudal_parcial(cursor.getString(16));
            registrosTableOneCaudalCan.setQls(cursor.getString(17));
            registrosTableOneCaudalCan.setVolumen_alicuota(cursor.getString(18));
            listaRegistros.add(registrosTableOneCaudalCan);



        }while(cursor.moveToNext());
    }
    cursor.close();
    return listaRegistros;
}

public String[] mostrarDatosFinales(){
    DbHelper dbHelper = new DbHelper(context);
    SQLiteDatabase db = dbHelper.getWritableDatabase();
    String[] listaRegistros = {};
    RegistrosTableOneCaudalCan registrosTableOneCaudalCan = null;
    Cursor cursor = null;

    cursor = db.rawQuery("SELECT * FROM " + TABLE_TABLE_TWO, null);

    if(cursor.moveToFirst()){
        do{

            listaRegistros = new String[]{String.valueOf(cursor.getInt(0)), cursor.getString(1),cursor.getString(2),
                    cursor.getString(3),cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getString(8), cursor.getString(9), cursor.getString(10),
                    cursor.getString(11), cursor.getString(12), cursor.getString(13),"\n"};




        }while(cursor.moveToNext());
    }
    cursor.close();
    return listaRegistros;
}



    public  long insertarCalculos(int id, String Dd, String CaudalTotal, String CaudalParcial, String Qls){

        boolean correct = false;


        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try{
            db.execSQL("UPDATE " +TABLE_TABLE_TWO + " SET d_d = '" + Dd +"', caudal_total = '"+ CaudalTotal +"', caudal_parcial = '"+ CaudalParcial +"', qls = '"+ Qls +"' WHERE  id_to =" + id);
            correct = true;
        }catch (Exception e){
            correct = false;
        }

        return id;

    }


    public  long insertarVolumenAlicuota(int id,String VolumenAlicuota){

        boolean correct = false;


        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try{
            db.execSQL("UPDATE " +TABLE_TABLE_TWO + " SET volumen_alicuota = '" + VolumenAlicuota +"' WHERE  id_to =" + id);
            correct = true;
        }catch (Exception e){
            correct = false;
        }

        return id;

    }

    public  long updateFqtWithTable(int id,String Fqt){

        boolean correct = false;


        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try{
            db.execSQL("UPDATE " +TABLE_TABLE_TWO + " SET fqt = '" + Fqt +"' WHERE  id_to =" + id);
            correct = true;
        }catch (Exception e){
            correct = false;
        }

        return id;

    }



    public int showSizeDates(){
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int sizeDates = 0;
        Cursor cursor = null;

        cursor = db.rawQuery("SELECT  COUNT(id_to) FROM " + TABLE_TABLE_TWO, null);

        if(cursor.moveToFirst()){
            do{

                sizeDates = cursor.getInt(0);
            }while(cursor.moveToNext());
        }
        cursor.close();
        return sizeDates;
    }
    public int showQls(){
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int Qls = 0;
        Cursor cursor = null;

        cursor = db.rawQuery("SELECT  SUM(vl/ts) FROM " + TABLE_TABLE_TWO, null);

        if(cursor.moveToFirst()){
            do{

                Qls = cursor.getInt(0);
            }while(cursor.moveToNext());
        }
        cursor.close();
        return Qls;
    }


    public String[] mostrarDatosFinalesS(int i){
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String[] listaRegistros = {};
        RegistrosTableOneCaudalCan registrosTableOneCaudalCan = null;
        Cursor cursor = null;

        cursor = db.rawQuery("SELECT * FROM " + TABLE_TABLE_TWO + " WHERE id_to=" + i, null);

        if(cursor.moveToFirst()){
            do{

                listaRegistros = new String[]{cursor.getString(1),cursor.getString(2),
                        cursor.getString(3),cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getString(8), cursor.getString(9), cursor.getString(10),
                        cursor.getString(11), cursor.getString(12), cursor.getString(13)};




            }while(cursor.moveToNext());
        }
        cursor.close();
        return listaRegistros;
    }

    public  Boolean deleteDates(){

        Boolean result = false;


        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try{
            db.execSQL(" DELETE FROM "+TABLE_TABLE_TWO);
            db.execSQL(" DELETE FROM SQLITE_SEQUENCE WHERE NAME = '"+TABLE_TABLE_TWO +"'");
            result = true;
        }catch (Exception e){
            result = false;
        }finally {
            //db.close();
        }
        return result;
    }




    public String mostrarNumeroMuestra(int i){
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String listaHoraMuestra = "";
        RegistrosTableOneCaudalCan registrosTableOneCaudalCan = null;
        Cursor cursor = null;

        cursor = db.rawQuery("SELECT * FROM " + TABLE_TABLE_TWO + " WHERE id_to = "+i , null);

        if(cursor.moveToFirst()){
            do{
                listaHoraMuestra = cursor.getString(1);
            }while(cursor.moveToNext());
        }
        cursor.close();
        return listaHoraMuestra;
    }

    public String mostrarHoraMuestra(int i){
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String listaHoraMuestra = "";
        RegistrosTableOneCaudalCan registrosTableOneCaudalCan = null;
        Cursor cursor = null;

        cursor = db.rawQuery("SELECT * FROM " + TABLE_TABLE_TWO + " WHERE id_to = "+i , null);

        if(cursor.moveToFirst()){
            do{
                listaHoraMuestra = cursor.getString(2);
            }while(cursor.moveToNext());
        }
        cursor.close();
        return listaHoraMuestra;
    }
    public String mostrarTc(int i){
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String listaHoraMuestra = "";
        RegistrosTableOneCaudalCan registrosTableOneCaudalCan = null;
        Cursor cursor = null;

        cursor = db.rawQuery("SELECT * FROM " + TABLE_TABLE_TWO + " WHERE id_to = "+i , null);

        if(cursor.moveToFirst()){
            do{
                listaHoraMuestra = cursor.getString(3);
            }while(cursor.moveToNext());
        }
        cursor.close();
        return listaHoraMuestra;
    }

    public String mostrarPhPatronUnd(int i){
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String listaHoraMuestra = "";
        RegistrosTableOneCaudalCan registrosTableOneCaudalCan = null;
        Cursor cursor = null;

        cursor = db.rawQuery("SELECT * FROM " + TABLE_TABLE_TWO + " WHERE id_to = "+i , null);

        if(cursor.moveToFirst()){
            do{
                listaHoraMuestra = cursor.getString(4);
            }while(cursor.moveToNext());
        }
        cursor.close();
        return listaHoraMuestra;
    }


    public String mostrarPhPatronTc(int i){
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String listaHoraMuestra = "";
        RegistrosTableOneCaudalCan registrosTableOneCaudalCan = null;
        Cursor cursor = null;

        cursor = db.rawQuery("SELECT * FROM " + TABLE_TABLE_TWO + " WHERE id_to = "+i , null);

        if(cursor.moveToFirst()){
            do{
                listaHoraMuestra = cursor.getString(5);
            }while(cursor.moveToNext());
        }
        cursor.close();
        return listaHoraMuestra;
    }
    public String mostrarPhMuestraUnd(int i){
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String listaHoraMuestra = "";
        RegistrosTableOneCaudalCan registrosTableOneCaudalCan = null;
        Cursor cursor = null;

        cursor = db.rawQuery("SELECT * FROM " + TABLE_TABLE_TWO + " WHERE id_to = "+i , null);

        if(cursor.moveToFirst()){
            do{
                listaHoraMuestra = cursor.getString(6);
            }while(cursor.moveToNext());
        }
        cursor.close();
        return listaHoraMuestra;
    }
    public String mostrarPhMuestraTc(int i){
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String listaHoraMuestra = "";
        RegistrosTableOneCaudalCan registrosTableOneCaudalCan = null;
        Cursor cursor = null;

        cursor = db.rawQuery("SELECT * FROM " + TABLE_TABLE_TWO + " WHERE id_to = "+i , null);

        if(cursor.moveToFirst()){
            do{
                listaHoraMuestra = cursor.getString(7);
            }while(cursor.moveToNext());
        }
        cursor.close();
        return listaHoraMuestra;
    }
    public String mostrarOxigenoMg(int i){
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String listaHoraMuestra = "";
        RegistrosTableOneCaudalCan registrosTableOneCaudalCan = null;
        Cursor cursor = null;

        cursor = db.rawQuery("SELECT * FROM " + TABLE_TABLE_TWO + " WHERE id_to = "+i , null);

        if(cursor.moveToFirst()){
            do{
                listaHoraMuestra = cursor.getString(8);
            }while(cursor.moveToNext());
        }
        cursor.close();
        return listaHoraMuestra;
    }
    public String mostrarOxigenoTc(int i){
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String listaHoraMuestra = "";
        RegistrosTableOneCaudalCan registrosTableOneCaudalCan = null;
        Cursor cursor = null;

        cursor = db.rawQuery("SELECT * FROM " + TABLE_TABLE_TWO + " WHERE id_to = "+i , null);

        if(cursor.moveToFirst()){
            do{
                listaHoraMuestra = cursor.getString(9);
            }while(cursor.moveToNext());
        }
        cursor.close();
        return listaHoraMuestra;
    }
    public String mostrarConductividadUnd(int i){
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String listaHoraMuestra = "";
        RegistrosTableOneCaudalCan registrosTableOneCaudalCan = null;
        Cursor cursor = null;

        cursor = db.rawQuery("SELECT * FROM " + TABLE_TABLE_TWO + " WHERE id_to = "+i , null);

        if(cursor.moveToFirst()){
            do{
                listaHoraMuestra = cursor.getString(10);
            }while(cursor.moveToNext());
        }
        cursor.close();
        return listaHoraMuestra;
    }
    public String mostrarConductividadTc(int i){
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String listaHoraMuestra = "";
        RegistrosTableOneCaudalCan registrosTableOneCaudalCan = null;
        Cursor cursor = null;

        cursor = db.rawQuery("SELECT * FROM " + TABLE_TABLE_TWO + " WHERE id_to = "+i , null);

        if(cursor.moveToFirst()){
            do{
                listaHoraMuestra = cursor.getString(11);
            }while(cursor.moveToNext());
        }
        cursor.close();
        return listaHoraMuestra;
    }
    public String mostrarProfundidad(int i){
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String listaHoraMuestra = "";
        RegistrosTableOneCaudalCan registrosTableOneCaudalCan = null;
        Cursor cursor = null;

        cursor = db.rawQuery("SELECT * FROM " + TABLE_TABLE_TWO + " WHERE id_to = "+i , null);

        if(cursor.moveToFirst()){
            do{
                listaHoraMuestra = cursor.getString(12);
            }while(cursor.moveToNext());
        }
        cursor.close();
        return listaHoraMuestra;
    }
    public String mostrarDd(int i){
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String listaHoraMuestra = "";
        RegistrosTableOneCaudalCan registrosTableOneCaudalCan = null;
        Cursor cursor = null;

        cursor = db.rawQuery("SELECT * FROM " + TABLE_TABLE_TWO + " WHERE id_to = "+i , null);

        if(cursor.moveToFirst()){
            do{
                listaHoraMuestra = cursor.getString(13);
            }while(cursor.moveToNext());
        }
        cursor.close();
        return listaHoraMuestra;
    }

    public String mostrarCaudalTotal(int i){
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String listaHoraMuestra = "";
        RegistrosTableOneCaudalCan registrosTableOneCaudalCan = null;
        Cursor cursor = null;

        cursor = db.rawQuery("SELECT * FROM " + TABLE_TABLE_TWO + " WHERE id_to = "+i , null);

        if(cursor.moveToFirst()){
            do{
                listaHoraMuestra = cursor.getString(14);
            }while(cursor.moveToNext());
        }
        cursor.close();
        return listaHoraMuestra;
    }
    public String mostrarFqt(int i){
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String listaHoraMuestra = "";
        RegistrosTableOneCaudalCan registrosTableOneCaudalCan = null;
        Cursor cursor = null;

        cursor = db.rawQuery("SELECT * FROM " + TABLE_TABLE_TWO + " WHERE id_to = "+i , null);

        if(cursor.moveToFirst()){
            do{
                listaHoraMuestra = cursor.getString(15);
            }while(cursor.moveToNext());
        }
        cursor.close();
        return listaHoraMuestra;
    }
    public String mostrarCaudalParcial(int i){
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String listaHoraMuestra = "";
        RegistrosTableOneCaudalCan registrosTableOneCaudalCan = null;
        Cursor cursor = null;

        cursor = db.rawQuery("SELECT * FROM " + TABLE_TABLE_TWO + " WHERE id_to = "+i , null);

        if(cursor.moveToFirst()){
            do{
                listaHoraMuestra = cursor.getString(16);
            }while(cursor.moveToNext());
        }
        cursor.close();
        return listaHoraMuestra;
    }

    public String mostrarQls(int i){
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String listaHoraMuestra = "";
        RegistrosTableOneCaudalCan registrosTableOneCaudalCan = null;
        Cursor cursor = null;

        cursor = db.rawQuery("SELECT * FROM " + TABLE_TABLE_TWO + " WHERE id_to = "+i , null);

        if(cursor.moveToFirst()){
            do{
                listaHoraMuestra = cursor.getString(17);
            }while(cursor.moveToNext());
        }
        cursor.close();
        return listaHoraMuestra;
    }

    public String mostrarVolumenAlicuota(int i){
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String listaHoraMuestra = "";
        RegistrosTableOneCaudalCan registrosTableOneCaudalCan = null;
        Cursor cursor = null;

        cursor = db.rawQuery("SELECT * FROM " + TABLE_TABLE_TWO + " WHERE id_to = "+i , null);

        if(cursor.moveToFirst()){
            do{
                listaHoraMuestra = cursor.getString(18);
            }while(cursor.moveToNext());
        }
        cursor.close();
        return listaHoraMuestra;
    }



    public String mostrarSumQls(int id){
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String listaHoraMuestra = "";
        RegistrosTableOneCaudalCan registrosTableOneCaudalCan = null;
        Cursor cursor = null;
        String idMax = showIdMax();
        String qlsMax = showQlsMax(idMax);
        cursor = db.rawQuery("SELECT qls FROM " + TABLE_TABLE_TWO+ " WHERE id_to = "+id , null);

        if(cursor.moveToFirst()){
            do{
                listaHoraMuestra = cursor.getString(0);
            }while(cursor.moveToNext());
        }
        cursor.close();
        return String.valueOf(listaHoraMuestra);
    }


    public String showIdMax(){
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String Qls = "";
        Cursor cursor = null;

        cursor = db.rawQuery("SELECT MAX(id_to) FROM " + TABLE_TABLE_TWO, null);

        if(cursor.moveToFirst()){
            do{
                Qls = cursor.getString(0);
            }while(cursor.moveToNext());
        }
        cursor.close();
        return Qls;
    }

    public String showQlsMax(String id){
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String Qls = "";
        Cursor cursor = null;

        cursor = db.rawQuery("SELECT qls FROM " + TABLE_TABLE_TWO + " WHERE id_to = " + id, null);

        if(cursor.moveToFirst()){
            do{
                Qls = cursor.getString(0);
            }while(cursor.moveToNext());
        }
        cursor.close();
        return Qls;
    }

    public  boolean updateDates(String pa, String fqt, int id, String qls, String hm, String tc, String pu, String pt, String  mu, String mt, String om, String ot, String cu, String ct){

        boolean result = false;


        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try{
            db.execSQL(" UPDATE "+ TABLE_TABLE_TWO + " SET hora_toma = '"+ hm + "',  tc = '"+ tc + "',  ph_patron_und = '"+ pu + "',  ph_patron_tc = '"+ pt + "', ph_muestra_und = '"+ mu + "', ph_muestra_tc = '"+ mt + "', oxigeno_mg = '"+ om + "', oxigeno_tc = '"+ ot + "', conductividad_und = '"+ cu + "', conductividad_tc = '"+ ct + "', profundidad_agua = '"+ pa + "', fqt = '"+ fqt + "', qls = '"+qls+"'  WHERE id_to =  "+ id);
            result = true;
        }catch (Exception e){
            result = false;
        }
        return result;
    }
}


