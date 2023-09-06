package com.la.veolia;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.la.veolia.db.*;

public class CaptureDatesTableOneCaudalVolActivity extends AppCompatActivity {
    CheckBox duplicateDate;
    Button button_add_capture_dates_one;
    EditText edit_no_muestra_thow_cap,edit_hora_toma_thow_cap,edit_tc_thow_cap,
            edit_patron_ph_thow_cap,edit_patron_tc_thow_cap,edit_muestra_ph_thow_cap,
            edit_muestra_tc_thow_cap,edit_oxigeno_mg_thow_cap,edit_oxigeno_tc_thow_cap,
            edit_conductividad_ph_thow_cap,edit_conductividad_tc_thow_cap,edit_text_lv_thow_cap,
            edit_text_ts_thow_cap;
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capture_dates_table_one_caudal_vol);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        duplicateDate = findViewById(R.id.duplicateDate);
        edit_no_muestra_thow_cap = findViewById(R.id.edit_no_muestra_thow_cap);
        edit_hora_toma_thow_cap = findViewById(R.id.edit_hora_toma_thow_cap);
        edit_tc_thow_cap = findViewById(R.id.edit_tc_thow_cap);
        edit_patron_ph_thow_cap = findViewById(R.id.edit_patron_ph_thow_cap);
        edit_patron_tc_thow_cap = findViewById(R.id.edit_patron_tc_thow_cap);
        edit_muestra_ph_thow_cap = findViewById(R.id.edit_muestra_ph_thow_cap);
        edit_muestra_tc_thow_cap = findViewById(R.id.edit_muestra_tc_thow_cap);
        edit_oxigeno_mg_thow_cap = findViewById(R.id.edit_oxigeno_mg_thow_cap);
        edit_oxigeno_tc_thow_cap = findViewById(R.id.edit_oxigeno_tc_thow_cap);
        edit_conductividad_ph_thow_cap = findViewById(R.id.edit_conductividad_ph_thow_cap);
        edit_conductividad_tc_thow_cap = findViewById(R.id.edit_conductividad_tc_thow_cap);
        edit_text_lv_thow_cap = findViewById(R.id.edit_text_lv_thow_cap);
        edit_text_ts_thow_cap = findViewById(R.id.edit_text_ts_thow_cap);

        button_add_capture_dates_one = findViewById(R.id.button_add_capture_dates_one);
        duplicateDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(duplicateDate.isChecked()){
                    edit_no_muestra_thow_cap.setText("");
                    edit_text_lv_thow_cap.setText("");
                    edit_text_ts_thow_cap.setText("");
                    edit_no_muestra_thow_cap.setEnabled(false);
                    edit_text_lv_thow_cap.setEnabled(false);
                    edit_text_ts_thow_cap.setEnabled(false);
                }else {

                    edit_no_muestra_thow_cap.setEnabled(true);
                    edit_text_lv_thow_cap.setEnabled(true);
                    edit_text_ts_thow_cap.setEnabled(true);
                }

            }
        });
        button_add_capture_dates_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!duplicateDate.isChecked()){
                    if(edit_text_ts_thow_cap.getText().toString().isEmpty() || Double.parseDouble(edit_text_ts_thow_cap.getText().toString()) == 0){
                        Toast.makeText(CaptureDatesTableOneCaudalVolActivity.this, "Completa el campo T(s), con un valor diferente a 0", Toast.LENGTH_LONG).show();
                    }else if( edit_text_lv_thow_cap.getText().toString().isEmpty()) {
                        Toast.makeText(CaptureDatesTableOneCaudalVolActivity.this, "Completa el campo V(L), así sea cero(0)", Toast.LENGTH_LONG).show();
                    }else {


                        double ts = Double.parseDouble(edit_text_ts_thow_cap.getText().toString());
                        double vl = Double.parseDouble(edit_text_lv_thow_cap.getText().toString());
                        double qls = vl/ts;
                        //Toast.makeText(CaptureDatesTableOneCaudalVolActivity.this, " "+qls, Toast.LENGTH_SHORT).show();
                        DbHelper dbHelper = new DbHelper(CaptureDatesTableOneCaudalVolActivity.this);
                        SQLiteDatabase db = dbHelper.getWritableDatabase();
                        if (db != null) {

                            dbCaptureTableOneCaudalVol bdCapture = new dbCaptureTableOneCaudalVol(CaptureDatesTableOneCaudalVolActivity.this);

                            long id = bdCapture.insertarDatos(edit_no_muestra_thow_cap.getText().toString(), edit_hora_toma_thow_cap.getText().toString(),
                                    edit_tc_thow_cap.getText().toString(), edit_patron_ph_thow_cap.getText().toString(), edit_patron_tc_thow_cap.getText().toString(),
                                    edit_muestra_ph_thow_cap.getText().toString(), edit_muestra_tc_thow_cap.getText().toString(), edit_oxigeno_mg_thow_cap.getText().toString(),
                                    edit_oxigeno_tc_thow_cap.getText().toString(), edit_conductividad_ph_thow_cap.getText().toString(), edit_conductividad_tc_thow_cap.getText().toString(),
                                    edit_text_lv_thow_cap.getText().toString(), edit_text_ts_thow_cap.getText().toString(), String.valueOf(qls));

                            if (id > 0) {
                                Toast.makeText(CaptureDatesTableOneCaudalVolActivity.this, "Se guardó el registro", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(CaptureDatesTableOneCaudalVolActivity.this, "No se pudo guardar el registro", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(CaptureDatesTableOneCaudalVolActivity.this, "Nooooooo", Toast.LENGTH_SHORT).show();
                        }
                    }
                }else{
                    double ts = 1.0;
                    double vl = 0.0;
                    double qls = vl/ts;
                    //Toast.makeText(CaptureDatesTableOneCaudalVolActivity.this, " "+qls, Toast.LENGTH_SHORT).show();
                    DbHelper dbHelper = new DbHelper(CaptureDatesTableOneCaudalVolActivity.this);
                    SQLiteDatabase db = dbHelper.getWritableDatabase();
                    if (db != null) {

                        dbCaptureTableOneCaudalVol bdCapture = new dbCaptureTableOneCaudalVol(CaptureDatesTableOneCaudalVolActivity.this);

                        long id = bdCapture.insertarDatos(edit_no_muestra_thow_cap.getText().toString(), edit_hora_toma_thow_cap.getText().toString(),
                                edit_tc_thow_cap.getText().toString(), edit_patron_ph_thow_cap.getText().toString(), edit_patron_tc_thow_cap.getText().toString(),
                                edit_muestra_ph_thow_cap.getText().toString(), edit_muestra_tc_thow_cap.getText().toString(), edit_oxigeno_mg_thow_cap.getText().toString(),
                                edit_oxigeno_tc_thow_cap.getText().toString(), edit_conductividad_ph_thow_cap.getText().toString(), edit_conductividad_tc_thow_cap.getText().toString(),
                                edit_text_lv_thow_cap.getText().toString(), edit_text_ts_thow_cap.getText().toString(), String.valueOf(qls));

                        if (id > 0) {
                            Toast.makeText(CaptureDatesTableOneCaudalVolActivity.this, "Se guardó el registro", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(CaptureDatesTableOneCaudalVolActivity.this, "No se pudo guardar el registro", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(CaptureDatesTableOneCaudalVolActivity.this, "....", Toast.LENGTH_SHORT).show();
                    }
                }

                }

        });
    }
}