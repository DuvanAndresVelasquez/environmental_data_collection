package com.la.veolia.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.la.veolia.EditDatesTableOneCaudalVolActivity;
import com.la.veolia.R;
import com.la.veolia.TableOneCaudalaCanActivity;
import com.la.veolia.db.dbCaptureTableOneCaudalVol;
import com.la.veolia.entitys.RegistrosTableOneCaudalVol;

import java.util.ArrayList;

public class ListRegistroAdapter extends RecyclerView.Adapter<ListRegistroAdapter.RegistroTableCaudalViewHolder>{
    ArrayList<RegistrosTableOneCaudalVol> ListaRegistroC1;
    private View.OnClickListener listener;
    public  ListRegistroAdapter(ArrayList<RegistrosTableOneCaudalVol> ListaRegistroC1){
        this.ListaRegistroC1 = ListaRegistroC1;
    }
    @NonNull
    @Override
    public RegistroTableCaudalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_caudal_one, null,
                false);
        return new RegistroTableCaudalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RegistroTableCaudalViewHolder holder, int position) {
        holder.c1.setText("No. Muestra: "+ ListaRegistroC1.get(position).getNumero_muestra());
        holder.c2.setText("Hora toma: "+ ListaRegistroC1.get(position).getHora_toma());
        holder.c3.setText("T('C): "+ ListaRegistroC1.get(position).getTc());
        holder.c4.setText("pH Patrón und ph: "+ListaRegistroC1.get(position).getPh_patron_und());
        holder.c5.setText("pH Patrón T('C): "+ListaRegistroC1.get(position).getPh_patron_tc());
        holder.c6.setText("pH Muestra und ph: "+ListaRegistroC1.get(position).getPh_muestra_und());
        holder.c7.setText("pH Muestra T('C): "+ListaRegistroC1.get(position).getPh_muestra_tc());
        holder.c8.setText("Oxígeno mg 02/L: "+ListaRegistroC1.get(position).getOxigeno_mg());
        holder.c9.setText("Oxígeno T('C): "+ListaRegistroC1.get(position).getOxigeno_tc());
        holder.c10.setText("Conductividad Und pH: "+ListaRegistroC1.get(position).getConductividad_und());
        holder.c11.setText("Conductividad T('C): "+ListaRegistroC1.get(position).getConductividad_tc());
        holder.c12.setText("V (L): "+ListaRegistroC1.get(position).getVl());
        holder.c13.setText("t (s): "+ListaRegistroC1.get(position).getTs());
        holder.c14.setText("Q (L/s): "+ListaRegistroC1.get(position).getQls());
        holder.c15.setText("Volúmen alicuota: "+ListaRegistroC1.get(position).getVolumen_alicuota());
        holder.c16.setVisibility(View.GONE);
        holder.c17.setVisibility(View.GONE);
        holder.c18.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {

        return ListaRegistroC1.size();

    }


    public class RegistroTableCaudalViewHolder extends RecyclerView.ViewHolder {

        TextView c1, c2, c3,c4, c5, c6,c7,c8,c9,c10, c11,c12,c13,c14,c15,c16,c17,c18;
        public RegistroTableCaudalViewHolder(@NonNull View itemView) {
            super(itemView);
            c1 = itemView.findViewById(R.id.c1);
            c2 = itemView.findViewById(R.id.c2);
            c3 = itemView.findViewById(R.id.c3);
            c4 = itemView.findViewById(R.id.c4);
            c5 = itemView.findViewById(R.id.c5);
            c6 = itemView.findViewById(R.id.c6);
            c7 = itemView.findViewById(R.id.c7);
            c8 = itemView.findViewById(R.id.c8);
            c9 = itemView.findViewById(R.id.c9);
            c10 = itemView.findViewById(R.id.c10);
            c11= itemView.findViewById(R.id.c11);
            c12 = itemView.findViewById(R.id.c12);
            c13 = itemView.findViewById(R.id.c13);
            c14 = itemView.findViewById(R.id.c14);
            c15 = itemView.findViewById(R.id.c15);
            c16 = itemView.findViewById(R.id.c16);
            c17 = itemView.findViewById(R.id.c17);
            c18 = itemView.findViewById(R.id.c18);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();


                    AlertDialog.Builder Bien = new AlertDialog.Builder(context);
                    Bien.setMessage("Edita los datos de este regístro.")
                            .setCancelable(true)
                            .setPositiveButton("Editar", new DialogInterface.OnClickListener() {
                                @RequiresApi(api = Build.VERSION_CODES.M)
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Intent edit = new Intent(context, EditDatesTableOneCaudalVolActivity.class);
                                    edit.putExtra("ht", ListaRegistroC1.get(getAdapterPosition()).getHora_toma());
                                    edit.putExtra("tc", ListaRegistroC1.get(getAdapterPosition()).getTc());
                                    edit.putExtra("pu", ListaRegistroC1.get(getAdapterPosition()).getPh_patron_und());
                                    edit.putExtra("pt", ListaRegistroC1.get(getAdapterPosition()).getPh_patron_tc());
                                    edit.putExtra("mu", ListaRegistroC1.get(getAdapterPosition()).getPh_muestra_und());
                                    edit.putExtra("mt", ListaRegistroC1.get(getAdapterPosition()).getPh_muestra_tc());
                                    edit.putExtra("om", ListaRegistroC1.get(getAdapterPosition()).getOxigeno_mg());
                                    edit.putExtra("ot", ListaRegistroC1.get(getAdapterPosition()).getOxigeno_tc());
                                    edit.putExtra("cu", ListaRegistroC1.get(getAdapterPosition()).getConductividad_und());
                                    edit.putExtra("ct", ListaRegistroC1.get(getAdapterPosition()).getConductividad_tc());
                                    edit.putExtra("vl", ListaRegistroC1.get(getAdapterPosition()).getVl());
                                    edit.putExtra("ts", ListaRegistroC1.get(getAdapterPosition()).getTs());
                                    edit.putExtra("ids", ListaRegistroC1.get(getAdapterPosition()).getId());
                                    edit.putExtra("nummuestra", ListaRegistroC1.get(getAdapterPosition()).getNumero_muestra());
                                    context.startActivity(edit);
                                }
                            });
                    AlertDialog Titulo = Bien.create();
                    Titulo.setTitle("Opciones!");
                    Titulo.show();





                }
            });
        }
    }
}
