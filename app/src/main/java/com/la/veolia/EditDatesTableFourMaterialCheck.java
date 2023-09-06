package com.la.veolia;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.la.veolia.db.dbCaptureTableFourMaterialCheck;
import com.la.veolia.db.dbCaptureTableThreeMaterialCheck;

public class EditDatesTableFourMaterialCheck extends AppCompatActivity {
    Spinner item, sr,rs, rc,rl;
    TextView mi,mrs,mrc,mrl;
    EditText cantidad;
    Button btn_edit;
    boolean update = false;

    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_dates_table_four_material_check);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        item = findViewById(R.id.edit_text_item_four);
        sr = findViewById(R.id.edit_text_se_requiere_four);
        rs = findViewById(R.id.edit_text_revision_salida_four);
        rc = findViewById(R.id.edit_text_revision_campo_four);
        rl = findViewById(R.id.edit_text_revision_llegada_four);
        cantidad = findViewById(R.id.edit_text_cantidad_table_four);
        btn_edit = findViewById(R.id.button_edit_material_check_four);

        String itemm = getIntent().getStringExtra("item");
        String srr = getIntent().getStringExtra("sr");
        String rss = getIntent().getStringExtra("rs");
        String rcc = getIntent().getStringExtra("rc");
        String rll = getIntent().getStringExtra("rl");
        String cann = getIntent().getStringExtra("can");
        int id = getIntent().getIntExtra("ids", 0);
        item.setSelection(0);
        sr.setSelection(0);
        rs.setSelection(0);
        rc.setSelection(0);
        rl.setSelection(0);
        cantidad.setText(cann);
        dbCaptureTableFourMaterialCheck dbCapture = new dbCaptureTableFourMaterialCheck(EditDatesTableFourMaterialCheck.this);

        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                update = dbCapture.updateDates(item.getSelectedItem().toString(), sr.getSelectedItem().toString(), id,  rs.getSelectedItem().toString(),
                        rc.getSelectedItem().toString(), rl.getSelectedItem().toString(), cantidad.getText().toString());
                if (update = true) {
                    Toast.makeText(EditDatesTableFourMaterialCheck.this, "Datos actualizados", Toast.LENGTH_SHORT).show();
                }
                finish();
            }
        });
    }
}