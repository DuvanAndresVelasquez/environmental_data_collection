package com.la.veolia.adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.la.veolia.EditDatesTableOneCaudalVolActivity;
import com.la.veolia.EditDatesTableOneMaterialCheckActivity;
import com.la.veolia.R;
import com.la.veolia.entitys.RegistroTableMaterialCheck;
import com.la.veolia.entitys.RegistrosTableOneCaudalVol;

import java.util.ArrayList;

public class ListRegistroAdapterMaterialCheck extends RecyclerView.Adapter<ListRegistroAdapterMaterialCheck.RegistroTableCaudalViewHolder> {
    ArrayList<RegistroTableMaterialCheck> ListaRegistroC1;
    public ListRegistroAdapterMaterialCheck(ArrayList<RegistroTableMaterialCheck> ListaRegistroC1){
        this.ListaRegistroC1 = ListaRegistroC1;
    }
    @NonNull
    @Override
    public RegistroTableCaudalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_material_check, null,
                false);
        return new RegistroTableCaudalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RegistroTableCaudalViewHolder holder, int position) {
        holder.c1.setText("Item: "+ ListaRegistroC1.get(position).getItem());
        holder.c2.setText("Otra ¿Cual?: "+ ListaRegistroC1.get(position).getOtra_cual());
        holder.c3.setText("Observaciones: "+ ListaRegistroC1.get(position).getObservaciones());
        holder.c4.setText("Cantidad: "+ListaRegistroC1.get(position).getCantidad());
        holder.c5.setText("Revisión salida: "+ListaRegistroC1.get(position).getRevision_salida());
        holder.c6.setText("Revisión campo: "+ListaRegistroC1.get(position).getRevision_campo());
        holder.c7.setText("Revisión llegada: "+ListaRegistroC1.get(position).getRevision_llegada());

    }

    @Override
    public int getItemCount() {
        return ListaRegistroC1.size();
    }

    public class RegistroTableCaudalViewHolder extends RecyclerView.ViewHolder {

        TextView c1, c2, c3,c4, c5, c6,c7;
        public RegistroTableCaudalViewHolder(@NonNull View itemView) {
            super(itemView);
            c1 = itemView.findViewById(R.id.m1);
            c2 = itemView.findViewById(R.id.m2);
            c3 = itemView.findViewById(R.id.m3);
            c4 = itemView.findViewById(R.id.m4);
            c5 = itemView.findViewById(R.id.m5);
            c6 = itemView.findViewById(R.id.m6);
            c7 = itemView.findViewById(R.id.m7);

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
                                    Intent edit = new Intent(context, EditDatesTableOneMaterialCheckActivity.class);
                                    edit.putExtra("item", ListaRegistroC1.get(getAdapterPosition()).getItem());
                                    edit.putExtra("otra", ListaRegistroC1.get(getAdapterPosition()).getOtra_cual());
                                    edit.putExtra("obs", ListaRegistroC1.get(getAdapterPosition()).getObservaciones());
                                    edit.putExtra("cant", ListaRegistroC1.get(getAdapterPosition()).getCantidad());
                                    edit.putExtra("rs", ListaRegistroC1.get(getAdapterPosition()).getRevision_salida());
                                    edit.putExtra("rc", ListaRegistroC1.get(getAdapterPosition()).getRevision_campo());
                                    edit.putExtra("rl", ListaRegistroC1.get(getAdapterPosition()).getRevision_llegada());
                                    edit.putExtra("ids", ListaRegistroC1.get(getAdapterPosition()).getId());
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
