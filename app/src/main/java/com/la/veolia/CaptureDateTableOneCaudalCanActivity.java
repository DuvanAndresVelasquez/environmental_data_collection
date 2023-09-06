package com.la.veolia;

import androidx.appcompat.app.AppCompatActivity;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.la.veolia.db.DbHelper;
import com.la.veolia.db.dbCaptureTableOneCaudalCan;
import com.la.veolia.db.dbCaptureTableOneCaudalVol;

public class CaptureDateTableOneCaudalCanActivity extends AppCompatActivity {
    CheckBox duplicateDate;
    Button button_add_capture_dates_one;
    EditText edit_no_muestra_thow_cap,edit_hora_toma_thow_cap,edit_tc_thow_cap,
            edit_patron_ph_thow_cap,edit_patron_tc_thow_cap,edit_muestra_ph_thow_cap,
            edit_muestra_tc_thow_cap,edit_oxigeno_mg_thow_cap,edit_oxigeno_tc_thow_cap,
            edit_conductividad_ph_thow_cap,edit_conductividad_tc_thow_cap,edit_text_profundidad_agua_thow_cap,
            edit_text_fqt_thow_cap;

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capture_date_table_one_caudal_can);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        duplicateDate = findViewById(R.id.duplicate);
        button_add_capture_dates_one = findViewById(R.id.btn_add_caudal_one);
        edit_no_muestra_thow_cap = findViewById(R.id.edit_text_no_muestra);
        edit_hora_toma_thow_cap = findViewById(R.id.edit_text_hora_toma);
        edit_tc_thow_cap = findViewById(R.id.edit_text_tc);
        edit_patron_ph_thow_cap = findViewById(R.id.edit_text_ph_pa_und);
        edit_patron_tc_thow_cap = findViewById(R.id.edit_text_ph_pa_tc);
        edit_muestra_ph_thow_cap = findViewById(R.id.edit_text_ph_mu_und);
        edit_muestra_tc_thow_cap = findViewById(R.id.edit_text_ph_mu_tc);
        edit_oxigeno_mg_thow_cap = findViewById(R.id.edit_text_oxi_mg);
        edit_oxigeno_tc_thow_cap = findViewById(R.id.edit_text_oxi_tc);
        edit_conductividad_ph_thow_cap = findViewById(R.id.edit_text_condutividad_und);
        edit_conductividad_tc_thow_cap = findViewById(R.id.edit_text_condutividad_tc);
        edit_text_profundidad_agua_thow_cap = findViewById(R.id.edit_text_profundidad);
        edit_text_fqt_thow_cap = findViewById(R.id.edit_text_fq);
        edit_text_fqt_thow_cap.setEnabled(false);

duplicateDate.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {

        if(duplicateDate.isChecked()){
            edit_no_muestra_thow_cap.setText("");
            edit_text_profundidad_agua_thow_cap.setText("");
            edit_text_fqt_thow_cap.setText("");
            edit_no_muestra_thow_cap.setEnabled(false);
            edit_text_profundidad_agua_thow_cap.setEnabled(false);
            edit_text_fqt_thow_cap.setEnabled(false);
        }else{

            edit_no_muestra_thow_cap.setEnabled(true);
            edit_text_profundidad_agua_thow_cap.setEnabled(true);
            edit_text_fqt_thow_cap.setEnabled(true);
        }

    }
});
        button_add_capture_dates_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DbHelper dbHelper = new DbHelper(CaptureDateTableOneCaudalCanActivity.this);
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                if(db != null){
                    dbCaptureTableOneCaudalCan bdCapture = new dbCaptureTableOneCaudalCan(CaptureDateTableOneCaudalCanActivity.this);
                    long id = bdCapture.insertarDatos(edit_no_muestra_thow_cap.getText().toString(), edit_hora_toma_thow_cap.getText().toString(),
                            edit_tc_thow_cap.getText().toString(), edit_patron_ph_thow_cap.getText().toString(), edit_patron_tc_thow_cap.getText().toString(),
                            edit_muestra_ph_thow_cap.getText().toString(), edit_muestra_tc_thow_cap.getText().toString(), edit_oxigeno_mg_thow_cap.getText().toString(),
                            edit_oxigeno_tc_thow_cap.getText().toString(),edit_conductividad_ph_thow_cap.getText().toString(),edit_conductividad_tc_thow_cap.getText().toString(),
                            edit_text_profundidad_agua_thow_cap.getText().toString(), "1");

                    if(id>0){
                        Toast.makeText(CaptureDateTableOneCaudalCanActivity.this, "Se guardó el registro con id:"+ id, Toast.LENGTH_LONG).show();
                        finish();
                    }else{
                        Toast.makeText(CaptureDateTableOneCaudalCanActivity.this, "No se pudo guardar el registro", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(CaptureDateTableOneCaudalCanActivity.this, "Nooooooo", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}