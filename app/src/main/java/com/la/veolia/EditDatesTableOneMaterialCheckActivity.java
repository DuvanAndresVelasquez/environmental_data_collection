package com.la.veolia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.la.veolia.db.dbCaptureTableOneMaterialCheck;

public class EditDatesTableOneMaterialCheckActivity extends AppCompatActivity {
    EditText otra,cant, obse ;
    EditText item,rs, rc,rl;
    TextView mi,mrs,mrc,mrl,tittle;
    Button btn_edit;
    boolean update = false;

    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_dates_table_one_material_check);
        try{
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        tittle = findViewById(R.id.title);
        otra = findViewById(R.id.edit_text_otra_cual__);
        cant = findViewById(R.id.edit_text_cantidad__);
        obse = findViewById(R.id.edit_text_observaciones__);
        rs = findViewById(R.id.edit_text_revision_salida__);
        rc = findViewById(R.id.edit_text_revision_campo__);
        rl = findViewById(R.id.edit_text_revision_llegada__);

        btn_edit = findViewById(R.id.button_edit_material_check);

        String itemm = getIntent().getStringExtra("item");
        String otraa = getIntent().getStringExtra("otra");
        String obss = getIntent().getStringExtra("obs");
        String cantt = getIntent().getStringExtra("cant");
        String rss = getIntent().getStringExtra("rs");
        String rcc = getIntent().getStringExtra("rc");
        String rll = getIntent().getStringExtra("rl");
        int id = getIntent().getIntExtra("ids", 0);

        tittle.setText("Editando: "+itemm);


           otra.setText(otraa);
           obse.setText(obss);
           cant.setText(cantt);
           rs.setText(rss);
           rc.setText(rcc);
           rl.setText(rll);

        dbCaptureTableOneMaterialCheck dbCapture = new dbCaptureTableOneMaterialCheck(EditDatesTableOneMaterialCheckActivity.this);

        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                    String obs = "En el item: " + itemm;
                    String otraEdit = "";
                    String obseEdit = "";
                    String cantEdit = "0";
                    if(otra.getText().toString().isEmpty()){
                        otraEdit = "--";
                    }else{
                        otraEdit = otra.getText().toString();
                    }
                    if(obse.getText().toString().isEmpty()){
                        obseEdit = "--";
                    }else{
                        obseEdit= obse.getText().toString();
                    }
                    if(cant.getText().toString().isEmpty()){
                        cantEdit = "0";
                    }else{
                        cantEdit = cant.getText().toString();
                    }
                    update = dbCapture.updateDates( otraEdit, id, Integer.parseInt(cantEdit), obse.getText().toString(), rs.getText().toString(),
                            rc.getText().toString(), rl.getText().toString());
                    if (update = true) {



                        if (otra.getText().toString().equals(otraa)) {
                            String tsmsg = "";
                        } else {
                            String tsmsg = "- se cambió Otra ¿Cúal? de: " + otraa + " a " + otra.getText().toString() + "";
                            obs = obs + tsmsg;
                        }

                        if (obse.getText().toString().equals(obss)) {
                            String hmmsg = "";
                        } else {
                            String hmmsg = "- se cambiaron las observaciones de: " + obss + " a " + obse.getText().toString() + "";
                            obs = obs + hmmsg;
                        }


                        if (cant.getText().toString().equals(cantt)) {
                            String tcmsg = "";
                        } else {
                            String tcmsg = "- se cambió la cantidad de: " + cantt + " a " + cant.getText().toString() + "";
                            obs = obs + tcmsg;
                        }

                        if (rs.getText().toString().equals(rss)) {
                            String pumsg = "";
                        } else {
                            String pumsg = "- se cambió Revisión Salida de: " + rss + " a " + rs.getText().toString() + "";
                            obs = obs + pumsg;
                        }

                        if (rc.getText().toString().equals(rcc)) {
                            String ptmsg = "";
                        } else {
                            String ptmsg = "- se cambió Revisión campo de: " + rcc + " a " + rc.getText().toString() + "";
                            obs = obs + ptmsg;
                        }


                        if (rl.getText().toString().equals(rll)) {
                            String mumsg = "";
                        } else {
                            String mumsg = "- se cambió Revisión Llegada de: " + rll + " a " + rl.getText().toString() + "";
                            obs = obs + mumsg;
                        }


                        SharedPreferences preferencesObservaciones = getSharedPreferences("Observaciones", Context.MODE_PRIVATE);
                        String Observaciones = obs;

                        final String ObservacionesSaved = preferencesObservaciones.getString("ObservacionOneMaterial", "");

                        if (ObservacionesSaved.equals("")) {
                            SharedPreferences.Editor observacionesName = preferencesObservaciones.edit();
                            observacionesName.putString("ObservacionOneMaterial", Observaciones);
                            observacionesName.commit();
                        } else {
                            String ObsAdd = ObservacionesSaved + ". Tambien, " + Observaciones;
                            SharedPreferences.Editor observacionesName = preferencesObservaciones.edit();
                            observacionesName.putString("ObservacionOneMaterial", ObsAdd);
                            observacionesName.commit();
                        }

                        Toast.makeText(EditDatesTableOneMaterialCheckActivity.this, "Datos actualizados exitosamente.", Toast.LENGTH_LONG).show();
                        finish();
                    } else {
                        Toast.makeText(EditDatesTableOneMaterialCheckActivity.this, "Parece que hubo un probelma, vuelve a intentarlo.", Toast.LENGTH_LONG).show();
                    }

            }
        });
        }catch (Exception e){
            Toast.makeText(EditDatesTableOneMaterialCheckActivity.this, ""+e, Toast.LENGTH_SHORT).show();
        }
    }

}