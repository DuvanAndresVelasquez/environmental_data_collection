package com.la.veolia.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.la.veolia.entitys.RegistrosTableOneCaudalCan;
import com.la.veolia.entitys.RegistrosTableOneCaudalVol;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class dbCaptureTableOneCaudalVol extends DbHelper{

    Context context;
    public dbCaptureTableOneCaudalVol(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    public  long insertarDatos(String numero_muestra, String hora_toma, String tc, String ph_patron_und, String ph_patron_tc,
                               String ph_muestra_und, String ph_muestra_tc, String oxigeno_mg, String oxigeno_tc,
                               String conductivida_und, String conductividad_tc, String vl, String ts, String qls){

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
        values.put("vl", vl);
        values.put("ts",ts);
        values.put("qls",qls);
        values.put("volumenalicuota","No calculado");
        id = db.insert(TABLE_TABLE_ONE, null, values);

        return id;

    }




    public  long insertarCalculos(int id, String volumenA){

        boolean correct = false;


        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try{
            db.execSQL("UPDATE " +TABLE_TABLE_ONE + " SET volumenalicuota = '" + volumenA +"' WHERE  id_to =" + id);
            correct = true;
        }catch (Exception e){
            correct = false;
        }

        return id;

    }














    public ArrayList<RegistrosTableOneCaudalVol> mostrarDatos(){
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();


        ArrayList<RegistrosTableOneCaudalVol> listaRegistros = new ArrayList<>();
        RegistrosTableOneCaudalVol registrosTableOneCaudalVol = null;
        Cursor cursor = null;

        cursor = db.rawQuery("SELECT * FROM " + TABLE_TABLE_ONE, null);

        if(cursor.moveToFirst()){
            do{
                registrosTableOneCaudalVol = new RegistrosTableOneCaudalVol();
                registrosTableOneCaudalVol.setId(cursor.getInt(0));
                registrosTableOneCaudalVol.setNumero_muestra(cursor.getString(1));
                registrosTableOneCaudalVol.setHora_toma(cursor.getString(2));
                registrosTableOneCaudalVol.setTc(cursor.getString(3));
                registrosTableOneCaudalVol.setPh_patron_und(cursor.getString(4));
                registrosTableOneCaudalVol.setPh_patron_tc(cursor.getString(5));
                registrosTableOneCaudalVol.setPh_muestra_und(cursor.getString(6));
                registrosTableOneCaudalVol.setPh_muestra_tc(cursor.getString(7));
                registrosTableOneCaudalVol.setOxigeno_mg(cursor.getString(8));
                registrosTableOneCaudalVol.setOxigeno_tc(cursor.getString(9));
                registrosTableOneCaudalVol.setConductividad_und(cursor.getString(10));
                registrosTableOneCaudalVol.setConductividad_tc(cursor.getString(11));
                registrosTableOneCaudalVol.setVl(cursor.getString(12));
                registrosTableOneCaudalVol.setTs(cursor.getString(13));
                registrosTableOneCaudalVol.setQls(cursor.getString(14));
                registrosTableOneCaudalVol.setVolumen_alicuota(cursor.getString(15));
                listaRegistros.add(registrosTableOneCaudalVol);



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

        cursor = db.rawQuery("SELECT * FROM " + TABLE_TABLE_ONE, null);

        if(cursor.moveToFirst()){
            do{

                listaRegistros = new String[]{String.valueOf(cursor.getInt(0)), cursor.getString(1),cursor.getString(2),
                        cursor.getString(3),cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getString(8), cursor.getString(9), cursor.getString(10),
                        cursor.getString(11), cursor.getString(12), cursor.getString(13)};




            }while(cursor.moveToNext());
        }
        cursor.close();
        return listaRegistros;
    }


    public int showSizeDates(){
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int sizeDates = 0;
        Cursor cursor = null;

        cursor = db.rawQuery("SELECT  COUNT(id_to) FROM " + TABLE_TABLE_ONE, null);

        if(cursor.moveToFirst()){
            do{

                sizeDates = cursor.getInt(0);
            }while(cursor.moveToNext());
        }
        cursor.close();
        return sizeDates;
    }
    public String showQls(){
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String Qls = "0.0";
        Cursor cursor = null;
        String idMax = showIdMax();
        String qlsMax = showQlsMax(idMax);
        cursor = db.rawQuery("SELECT SUM(qls) FROM " + TABLE_TABLE_ONE, null);

        if(cursor.moveToFirst()){
            do{
                Qls = cursor.getString(0);
            }while(cursor.moveToNext());
        }
        cursor.close();
        double QlsFinal = Double.parseDouble(Qls)-Double.parseDouble(qlsMax);
        return String.valueOf(QlsFinal);
    }

    public String showIdMax(){
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String Qls = "";
        Cursor cursor = null;

        cursor = db.rawQuery("SELECT MAX(id_to) FROM " + TABLE_TABLE_ONE, null);

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

        cursor = db.rawQuery("SELECT qls FROM " + TABLE_TABLE_ONE + " WHERE id_to = " + id, null);

        if(cursor.moveToFirst()){
            do{
                Qls = cursor.getString(0);
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

        cursor = db.rawQuery("SELECT * FROM " + TABLE_TABLE_ONE + " WHERE id_to=" + i, null);

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






    public String mostrarNumeroMuestra(int i){
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String listaNumerosMuestra = "";
        RegistrosTableOneCaudalCan registrosTableOneCaudalCan = null;
        Cursor cursor = null;

        cursor = db.rawQuery("SELECT * FROM " + TABLE_TABLE_ONE + " WHERE id_to = "+i, null);

        if(cursor.moveToFirst()){
            do{
                listaNumerosMuestra = cursor.getString(1);
            }while(cursor.moveToNext());
        }
        cursor.close();
        return listaNumerosMuestra;
    }



    public String mostrarHoraMuestra(int i){
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String listaHoraMuestra = "";
        RegistrosTableOneCaudalCan registrosTableOneCaudalCan = null;
        Cursor cursor = null;

        cursor = db.rawQuery("SELECT * FROM " + TABLE_TABLE_ONE + " WHERE id_to = "+i , null);

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

        cursor = db.rawQuery("SELECT * FROM " + TABLE_TABLE_ONE + " WHERE id_to = "+i , null);

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

        cursor = db.rawQuery("SELECT * FROM " + TABLE_TABLE_ONE + " WHERE id_to = "+i , null);

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

        cursor = db.rawQuery("SELECT * FROM " + TABLE_TABLE_ONE + " WHERE id_to = "+i , null);

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

        cursor = db.rawQuery("SELECT * FROM " + TABLE_TABLE_ONE + " WHERE id_to = "+i , null);

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

        cursor = db.rawQuery("SELECT * FROM " + TABLE_TABLE_ONE + " WHERE id_to = "+i , null);

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

        cursor = db.rawQuery("SELECT * FROM " + TABLE_TABLE_ONE + " WHERE id_to = "+i , null);

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

        cursor = db.rawQuery("SELECT * FROM " + TABLE_TABLE_ONE + " WHERE id_to = "+i , null);

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

        cursor = db.rawQuery("SELECT * FROM " + TABLE_TABLE_ONE + " WHERE id_to = "+i , null);

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

        cursor = db.rawQuery("SELECT * FROM " + TABLE_TABLE_ONE + " WHERE id_to = "+i , null);

        if(cursor.moveToFirst()){
            do{
                listaHoraMuestra = cursor.getString(11);
            }while(cursor.moveToNext());
        }
        cursor.close();
        return listaHoraMuestra;
    }


    public String mostrarVL(int i){
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String listaHoraMuestra = "";
        RegistrosTableOneCaudalCan registrosTableOneCaudalCan = null;
        Cursor cursor = null;

        cursor = db.rawQuery("SELECT * FROM " + TABLE_TABLE_ONE + " WHERE id_to = "+i , null);

        if(cursor.moveToFirst()){
            do{
                listaHoraMuestra = cursor.getString(12);
            }while(cursor.moveToNext());
        }
        cursor.close();
        return listaHoraMuestra;
    }


    public String mostrarTs(int i){
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String listaHoraMuestra = "";
        RegistrosTableOneCaudalCan registrosTableOneCaudalCan = null;
        Cursor cursor = null;

        cursor = db.rawQuery("SELECT * FROM " + TABLE_TABLE_ONE + " WHERE id_to = "+i , null);

        if(cursor.moveToFirst()){
            do{
                listaHoraMuestra = cursor.getString(13);
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

        cursor = db.rawQuery("SELECT qls FROM " + TABLE_TABLE_ONE + " WHERE id_to = "+i , null);

        if(cursor.moveToFirst()){
            do{
                listaHoraMuestra = cursor.getString(0);
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

        cursor = db.rawQuery("SELECT * FROM " + TABLE_TABLE_ONE + " WHERE id_to = "+i , null);

        if(cursor.moveToFirst()){
            do{
                listaHoraMuestra = cursor.getString(15);
            }while(cursor.moveToNext());
        }
        cursor.close();
        return listaHoraMuestra;
    }










    public  Boolean deleteDates(){

        Boolean result = false;


        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try{
            db.execSQL(" DELETE FROM "+TABLE_TABLE_ONE);
            db.execSQL(" DELETE FROM SQLITE_SEQUENCE WHERE NAME = '"+TABLE_TABLE_ONE +"'");
            result = true;
        }catch (Exception e){
            result = false;
        }finally {
            //db.close();
        }
        return result;
    }



    public  Boolean deleteRegister(int id){

        Boolean result = false;


        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try{
            db.execSQL(" DELETE FROM "+TABLE_TABLE_ONE + " WHERE id_to =" + id);
            db.execSQL(" DELETE FROM SQLITE_SEQUENCE WHERE NAME = '"+TABLE_TABLE_ONE +"'");
            result = true;
        }catch (Exception e){
            result = false;
        }finally {
            //db.close();
        }
        return result;
    }

    public  boolean updateDates(String vl, String ts, int id, String qls, String hm, String tc, String pu, String pt, String  mu, String mt, String om, String ot, String cu, String ct){

        boolean result = false;


        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        try{
            db.execSQL(" UPDATE "+ TABLE_TABLE_ONE + " SET hora_toma = '"+ hm + "',  tc = '"+ tc + "',  ph_patron_und = '"+ pu + "',  ph_patron_tc = '"+ pt + "', ph_muestra_und = '"+ mu + "', ph_muestra_tc = '"+ mt + "', oxigeno_mg = '"+ om + "', oxigeno_tc = '"+ ot + "', conductividad_und = '"+ cu + "', conductividad_tc = '"+ ct + "', vl = '"+ vl + "', ts = '"+ ts + "', qls = '"+qls+"'  WHERE id_to =  "+ id);
            result = true;
        }catch (Exception e){
            result = false;
        }
        return result;
    }

}
