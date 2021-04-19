package com.example.dmobapp;



import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;



public class myAdapter extends RecyclerView.Adapter<myAdapter.myviewholder>{



    ArrayList<Salles> dataholder;
    private Context context;

    public myAdapter(ArrayList<Salles> dataholder,Context context) {
        this.context = context;
        this.dataholder = dataholder;
    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.salles_item,parent,false);

        return new myviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myviewholder holder, int position) {
        holder.tvNom.setText(dataholder.get(position).getNom());
        holder.tvAdresse.setText(dataholder.get(position).getAdresse());
        holder.tvTel.setText(dataholder.get(position).getTel());
//        holder.tvlongitude.setText(dataholder.get(position).getLongitude());
//        holder.tvLatitude.setText(dataholder.get(position).getLatitude());
        holder.tvTel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String num = "tel:" + dataholder.get(position).getTel();
                System.out.println(num);
                Intent i = new Intent(Intent.ACTION_DIAL);
                i.setData(Uri.parse(num));
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataholder.size();
    }


    static class myviewholder extends RecyclerView.ViewHolder
    {
        TextView tvNom, tvAdresse, tvTel, tvlongitude, tvLatitude;

        public myviewholder(@NonNull View itemView) {
            super(itemView);

            tvNom = itemView.findViewById(R.id.tvNom);
            tvAdresse = itemView.findViewById(R.id.tvAdresse);
            tvTel = itemView.findViewById(R.id.tvTel);
            tvlongitude = itemView.findViewById(R.id.longitudeText);
            tvLatitude = itemView.findViewById(R.id.latitudeText);
        }
    }
}
