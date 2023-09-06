package com.la.veolia.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NOMBRE_TABLE = "db.tables";

    public static final String TABLE_TABLE_ONE ="table_table_one";
    public static final String TABLE_TABLE_TWO ="table_table_two";
    public static final String TABLE_TABLE_THREE ="table_table_three";
    public static final String TABLE_TABLE_FOUR ="table_table_four";
    public static final String TABLE_TABLE_FIVE ="table_table_five";

    public DbHelper(@Nullable Context context) {
        super(context, DATABASE_NOMBRE_TABLE, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE "+ TABLE_TABLE_ONE + "(" +
                "id_to INTEGER PRIMARY KEY AUTOINCREMENT," +
                "numero_muestra TEXT NOT NULL," +
                "hora_toma TEXT NOT NULL," +
                "tc TEXT NOT NULL," +
                "ph_patron_und TEXT NOT NULL," +
                "ph_patron_tc TEXT NOT NULL," +
                "ph_muestra_und TEXT NOT NULL," +
                "ph_muestra_tc TEXT NOT NULL," +
                "oxigeno_mg TEXT NOT NULL," +
                "oxigeno_tc TEXT NOT NULL," +
                "conductividad_und TEXT NOT NULL," +
                "conductividad_tc TEXT NOT NULL," +
                "vl TEXT NOT NULL," +
                "ts TEXT NOT NULL," +
                "qls TEXT NOT NULL," +
                "volumenalicuota TEXT NOT NULL)");

        db.execSQL("CREATE TABLE "+ TABLE_TABLE_TWO + "(" +
                "id_to INTEGER PRIMARY KEY AUTOINCREMENT," +
                "numero_muestra TEXT NOT NULL," +
                "hora_toma TEXT NOT NULL," +
                "tc TEXT NOT NULL," +
                "ph_patron_und TEXT NOT NULL," +
                "ph_patron_tc TEXT NOT NULL," +
                "ph_muestra_und TEXT NOT NULL," +
                "ph_muestra_tc TEXT NOT NULL," +
                "oxigeno_mg TEXT NOT NULL," +
                "oxigeno_tc TEXT NOT NULL," +
                "conductividad_und TEXT NOT NULL," +
                "conductividad_tc TEXT NOT NULL," +
                "profundidad_agua TEXT NOT NULL," +
                "d_d TEXT NOT NULL," +
                "caudal_total TEXT NOT NULL," +
                "fqt TEXT NOT NULL," +
                "caudal_parcial TEXT NOT NULL," +
                "qls TEXT NOT NULL," +
                "volumen_alicuota TEXT NOT NULL)");


        db.execSQL("CREATE TABLE "+ TABLE_TABLE_THREE + "(" +
                "id_to INTEGER PRIMARY KEY AUTOINCREMENT," +
                "item TEXT NOT NULL," +
                "otra_cual TEXT NOT NULL," +
                "observaciones TEXT NOT NULL," +
                "cantidad TEXT NOT NULL," +
                "revision_salida TEXT NOT NULL," +
                "revision_campo TEXT NOT NULL," +
                "revision_llegada TEXT NOT NULL)");

        db.execSQL("CREATE TABLE "+ TABLE_TABLE_FOUR + "(" +
                "id_to INTEGER PRIMARY KEY AUTOINCREMENT," +
                "item TEXT NOT NULL," +
                "se_requiere TEXT NOT NULL," +
                "revision_salida TEXT NOT NULL," +
                "revision_campo TEXT NOT NULL," +
                "revision_llegada TEXT NOT NULL)");

        db.execSQL("CREATE TABLE "+ TABLE_TABLE_FIVE + "(" +
                "id_to INTEGER PRIMARY KEY AUTOINCREMENT," +
                "item TEXT NOT NULL," +
                "se_requiere TEXT NOT NULL," +
                "cantidad TEXT NOT NULL," +
                "revision_salida TEXT NOT NULL," +
                "revision_campo TEXT NOT NULL," +
                "revision_llegada TEXT NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(" DROP TABLE "+TABLE_TABLE_ONE);
        db.execSQL(" DROP TABLE "+TABLE_TABLE_TWO);
        db.execSQL(" DROP TABLE "+TABLE_TABLE_THREE);
        db.execSQL(" DROP TABLE "+TABLE_TABLE_FOUR);
        db.execSQL(" DROP TABLE "+TABLE_TABLE_FIVE);
        onCreate(db);
    }


    private void createTableOneVol(SQLiteDatabase db){

    }



    private void createTableTwoCan(SQLiteDatabase db){
        db.execSQL("CREATE TABLE "+ TABLE_TABLE_ONE + "(" +
                "id_to INTEGER PRIMARY KEY AUTOINCREMENT," +
                "numero_muestra TEXT NOT NULL," +
                "hora_toma TEXT NOT NULL," +
                "tc DECIMAL NOT NULL," +
                "ph_patron_und DECIMAL NOT NULL," +
                "ph_patron_tc DECIMAL NOT NULL," +
                "ph_muestra_und DECIMAL NOT NULL," +
                "oxigeno_mg DECIMAL NOT NULL," +
                "oxigeno_tc DECIMAL NOT NULL," +
                "conductividad_und DECIMAL NOT NULL," +
                "conductividad_tc DECIMAL NOT NULL," +
                "vl DECIMAL NOT NULL," +
                "ts DECIMAL NOT NULL)");
    }
}
