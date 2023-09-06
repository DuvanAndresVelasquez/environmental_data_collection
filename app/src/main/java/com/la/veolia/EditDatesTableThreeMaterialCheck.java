package com.la.veolia;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.la.veolia.db.dbCaptureTableThreeMaterialCheck;

public class EditDatesTableThreeMaterialCheck extends AppCompatActivity {

    EditText  sr,rs, rc,rl;
    TextView mi,mrs,mrc,mrl, titlet;
    Button btn_edit;
    boolean update = false;

    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_dates_table_three_material_check);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        titlet = findViewById(R.id.titlet);
        sr = findViewById(R.id.edit_text_se_requiere_three);
        rs = findViewById(R.id.edit_text_revision_salida_three);
        rc = findViewById(R.id.edit_text_revision_campo_three);
        rl = findViewById(R.id.edit_text_revision_llegada_three);

        btn_edit = findViewById(R.id.button_edit_material_check_three);

        String itemm = getIntent().getStringExtra("item");
        String srr = getIntent().getStringExtra("sr");
        String rss = getIntent().getStringExtra("rs");
        String rcc = getIntent().getStringExtra("rc");
        String rll = getIntent().getStringExtra("rl");
        int id = getIntent().getIntExtra("ids", 0);
        titlet.setText("Editando: "+itemm);



        sr.setText(srr);
        rs.setText(rss);
        rc.setText(rcc);
        rl.setText(rll);
        dbCaptureTableThreeMaterialCheck dbCapture = new dbCaptureTableThreeMaterialCheck(EditDatesTableThreeMaterialCheck.this);
        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                update = dbCapture.updateDates( sr.getText().toString(), id,  rs.getText().toString(),
                        rc.getText().toString(), rl.getText().toString());
                if (update = true) {
                    Toast.makeText(EditDatesTableThreeMaterialCheck.this, "Datos actualizados", Toast.LENGTH_SHORT).show();
                }
                finish();
            }
        });

    }
}