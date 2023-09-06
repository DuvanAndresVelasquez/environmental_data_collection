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

import com.la.veolia.EditDatesTableOneMaterialCheckActivity;
import com.la.veolia.EditDatesTableThreeMaterialCheck;
import com.la.veolia.R;
import com.la.veolia.entitys.RegistroTableThreeMaterialCheck;

import java.util.ArrayList;

public class ListRegistroAdapterThreeMaterialCheck extends RecyclerView.Adapter<ListRegistroAdapterThreeMaterialCheck.RegistroTableThreeMaterialCheckViewHolder> {
    ArrayList<RegistroTableThreeMaterialCheck> listThreeMaterialCheck;
    public ListRegistroAdapterThreeMaterialCheck(ArrayList<RegistroTableThreeMaterialCheck> listThreeMaterialCheck) {
        this.listThreeMaterialCheck = listThreeMaterialCheck;
    }



    @NonNull
    @Override
    public RegistroTableThreeMaterialCheckViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_material_check, null,
                false);
        return new ListRegistroAdapterThreeMaterialCheck.RegistroTableThreeMaterialCheckViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RegistroTableThreeMaterialCheckViewHolder holder, int position) {

        holder.c1.setText("Item: "+ listThreeMaterialCheck.get(position).getItem());
        holder.c2.setText("Se requiere: "+ listThreeMaterialCheck.get(position).getSe_requiere());
        holder.c5.setText("Revisión salida: "+listThreeMaterialCheck.get(position).getRevision_salida());
        holder.c6.setText("Revisión campo: "+listThreeMaterialCheck.get(position).getRevision_campo());
        holder.c7.setText("Revisión llegada: "+listThreeMaterialCheck.get(position).getRevision_llegada());
        holder.c3.setVisibility(View.GONE);
        holder.c4.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return listThreeMaterialCheck.size();
    }

    public class RegistroTableThreeMaterialCheckViewHolder extends RecyclerView.ViewHolder {
        TextView c1, c2, c3, c4, c5, c6, c7;

        public RegistroTableThreeMaterialCheckViewHolder(@NonNull View itemView) {
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
                                    Intent edit = new Intent(context, EditDatesTableThreeMaterialCheck.class);
                                    edit.putExtra("item", listThreeMaterialCheck.get(getAdapterPosition()).getItem());
                                    edit.putExtra("sr", listThreeMaterialCheck.get(getAdapterPosition()).getSe_requiere());
                                    edit.putExtra("rs", listThreeMaterialCheck.get(getAdapterPosition()).getRevision_salida());
                                    edit.putExtra("rc", listThreeMaterialCheck.get(getAdapterPosition()).getRevision_campo());
                                    edit.putExtra("rl", listThreeMaterialCheck.get(getAdapterPosition()).getRevision_llegada());
                                    edit.putExtra("ids", listThreeMaterialCheck.get(getAdapterPosition()).getId());
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
