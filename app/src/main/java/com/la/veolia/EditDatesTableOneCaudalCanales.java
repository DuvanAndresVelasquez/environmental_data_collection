package com.la.veolia;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.la.veolia.db.dbCaptureTableOneCaudalCan;
import com.la.veolia.db.dbCaptureTableOneCaudalVol;

public class EditDatesTableOneCaudalCanales extends AppCompatActivity {
    EditText  hm, tc, pu,pt,mu,mt,om,ot,cu,ct,pa,fqt;
    Button btn_edit;
    boolean update = false;
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return false;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_dates_table_one_caudal_canales);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        pa = findViewById(R.id.edit_text_profundidad_agua);
        fqt = findViewById(R.id.edit_text_fqt);
        hm = findViewById(R.id.edit_text_hora_toma__);
        tc = findViewById(R.id.edit_text_tc__);
        pu = findViewById(R.id.edit_text_ph_patron_und_);
        pt = findViewById(R.id.edit_text_ph_patron_tc_);
        mu = findViewById(R.id.edit_text_ph_muestra_und_);
        mt = findViewById(R.id.edit_text_ph_muestra_tc_);
        om = findViewById(R.id.edit_text_oxígeno_mg__);
        ot = findViewById(R.id.edit_text_oxígeno_tc__);
        cu = findViewById(R.id.edit_text_conduc_und_);
        ct = findViewById(R.id.edit_text_conduc_tc_);
        btn_edit = findViewById(R.id.button_edit_caudal_cana);

        String paa = getIntent().getStringExtra("pa");
        String fqtt = getIntent().getStringExtra("fqt");
        String hmm = getIntent().getStringExtra("ht");
        String puu = getIntent().getStringExtra("pu");
        String ptt = getIntent().getStringExtra("pt");
        String muu = getIntent().getStringExtra("mu");
        String mtt = getIntent().getStringExtra("mt");
        String omm = getIntent().getStringExtra("om");
        String ott = getIntent().getStringExtra("ot");
        String cuu = getIntent().getStringExtra("cu");
        String ctt = getIntent().getStringExtra("ct");
        String tcc = getIntent().getStringExtra("tc");
        String numm = getIntent().getStringExtra("nummuestra");
        int id = getIntent().getIntExtra("ids", 0);

        pa.setText(paa);
        fqt.setText(fqtt);
        hm.setText(hmm);
        pu.setText(puu);
        pt.setText(ptt);
        mu.setText(muu);
        mt.setText(mtt);
        om.setText(omm);
        ot.setText(ott);
        cu.setText(cuu);
        ct.setText(ctt);
        tc.setText(tcc);
        dbCaptureTableOneCaudalCan dbCapture = new dbCaptureTableOneCaudalCan(EditDatesTableOneCaudalCanales.this);

        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    try{

                            String qlss = "No calculado";
                            String obs = "En la muestra número: "+ numm  ;

                        String hmEdit="";
                        String tcEdit="";
                        String puEdit ="";
                        String ptEdit="";
                        String muEdit="";
                        String mtEdit="";
                        String omEdit="";
                        String otEdit="";
                        String cuEdit="";
                        String ctEdit="";
                        if(hm.getText().toString().isEmpty()){
                            hmEdit="--";
                        }else{
                            hmEdit=hm.getText().toString();
                        }

                        if(tc.getText().toString().isEmpty()){
                            tcEdit="--";
                        }else{
                            tcEdit=tc.getText().toString();
                        }
                        if(pu.getText().toString().isEmpty()){
                            puEdit="--";
                        }else{
                            puEdit=pu.getText().toString();
                        }

                        if(pt.getText().toString().isEmpty()){
                            ptEdit="--";
                        }else{
                            ptEdit=pt.getText().toString();
                        }

                        if(mu.getText().toString().isEmpty()){
                            muEdit="--";
                        }else{
                            muEdit=mu.getText().toString();
                        }

                        if(mt.getText().toString().isEmpty()){
                            mtEdit="--";
                        }else{
                            mtEdit=mt.getText().toString();
                        }

                        if(om.getText().toString().isEmpty()){
                            omEdit="--";
                        }else{
                            omEdit=om.getText().toString();
                        }

                        if(ot.getText().toString().isEmpty()){
                            otEdit="--";
                        }else{
                            otEdit=ot.getText().toString();
                        }

                        if(cu.getText().toString().isEmpty()){
                            cuEdit="--";
                        }else{
                            cuEdit=cu.getText().toString();
                        }

                        if(ct.getText().toString().isEmpty()){
                            ctEdit="--";
                        }else{
                            ctEdit=ct.getText().toString();
                        }
                            update =  dbCapture.updateDates(pa.getText().toString(), fqt.getText().toString(), id, qlss,  hmEdit,tcEdit,
                                    puEdit, ptEdit, muEdit, mtEdit,
                                    omEdit, otEdit, cuEdit, ctEdit);
                            if(update=true){


                                if(pa.getText().toString().equals(paa)){
                                    String vlmsg = "";
                                }else{
                                    String vlmsg = "- se cambió Profundidad_agua de: "+ paa +" a " + pa.getText().toString() + "";
                                    obs = obs + vlmsg;
                                }
                                if(fqt.getText().toString().equals(fqtt)){
                                    String tsmsg = "";
                                }else{
                                    String tsmsg = "- se cambió Fqt de: "+ fqtt +" a " + fqt.getText().toString() + "";
                                    obs = obs + tsmsg;
                                }

                                if(hm.getText().toString().equals(hmm)){
                                    String hmmsg = "";
                                }else{
                                    String hmmsg = "- se cambió la hora de la muestra de: "+ hmm +" a " + hm.getText().toString() + "";
                                    obs = obs + hmmsg;
                                }


                                if(tc.getText().toString().equals(tcc)){
                                    String tcmsg = "";
                                }else{
                                    String tcmsg = "- se cambió T(C) de: "+ tcc +" a " + tc.getText().toString() + "";
                                    obs = obs + tcmsg;
                                }

                                if(pu.getText().toString().equals(puu)){
                                    String pumsg = "";
                                }else{
                                    String pumsg = "- se cambió ph Patron und ph de: "+ puu +" a " + pu.getText().toString() + "";
                                    obs = obs + pumsg;
                                }

                                if(pt.getText().toString().equals(ptt)){
                                    String ptmsg = "";
                                }else{
                                    String ptmsg = "- se cambió ph Patron T(C) ph de: "+ ptt +" a " + pt.getText().toString() + "";
                                    obs = obs + ptmsg;
                                }



                                if(mu.getText().toString().equals(muu)){
                                    String mumsg = "";
                                }else{
                                    String mumsg = "- se cambió ph Muestra und ph de: "+ muu +" a " + mu.getText().toString() + "";
                                    obs = obs + mumsg;
                                }


                                if(mt.getText().toString().equals(mtt)){
                                    String mtmsg = "";
                                }else{
                                    String mtmsg = "- se cambió ph Muestra T(C) ph de: "+ mtt +" a " + mt.getText().toString() + "";
                                    obs = obs + mtmsg;
                                }


                                if(om.getText().toString().equals(omm)){
                                    String ommsg = "";
                                }else{
                                    String ommsg = "- se cambió Oxígeno mg O2/L de: "+ omm +" a " + om.getText().toString() + "";
                                    obs = obs + ommsg;
                                }


                                if(cu.getText().toString().equals(cuu)){
                                    String cumsg = "";
                                }else{
                                    String cumsg = "- se cambió Conductividad Und pH de: "+ cuu +" a " + cu.getText().toString() + "";
                                    obs = obs + cumsg;
                                }

                                if(ct.getText().toString().equals(ctt)){
                                    String ctmsg = "";
                                }else{
                                    String ctmsg = "- se cambió Conductividad T('C) de: "+ ctt +" a " + ct.getText().toString() + "";
                                    obs = obs + ctmsg;
                                }

                                if(ct.getText().toString().equals(ctt)){
                                    String ctmsg = "";
                                }else{
                                    String ctmsg = "- se cambió Conductividad T('C) de: "+ ctt +" a " + ct.getText().toString() + "";
                                    obs = obs + ctmsg;
                                }

                                SharedPreferences preferencesObservaciones = getSharedPreferences("Observaciones", Context.MODE_PRIVATE);
                                String Observaciones = obs;

                                final String ObservacionesSaved = preferencesObservaciones.getString("ObservacionOneCaudal","");

                                if(ObservacionesSaved.equals("")){
                                    SharedPreferences.Editor observacionesName = preferencesObservaciones.edit();
                                    observacionesName.putString("ObservacionOneCaudal", Observaciones);
                                    observacionesName.commit();
                                }else{
                                    String ObsAdd = ObservacionesSaved+ ". Tambien, " + Observaciones;
                                    SharedPreferences.Editor observacionesName = preferencesObservaciones.edit();
                                    observacionesName.putString("ObservacionOneCaudal", ObsAdd);
                                    observacionesName.commit();
                                }

                                Toast.makeText(EditDatesTableOneCaudalCanales.this, "Datos actualizados exitosamente.", Toast.LENGTH_LONG).show();
                                finish();
                            }else{
                                Toast.makeText(EditDatesTableOneCaudalCanales.this, "Parece que hubo un probelma, vuelve a intentarlo.", Toast.LENGTH_LONG).show();
                            }


                    }catch (Exception e){
                        Toast.makeText(EditDatesTableOneCaudalCanales.this, ""+e, Toast.LENGTH_LONG).show();
                    }


            }
        });
    }
}