package com.example.dmobapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class adaperAdmin extends RecyclerView.Adapter<adaperAdmin.myviewHolder> {

    ArrayList<Salles> listSalles;
    Context context;
    FirebaseDatabase db = FirebaseDatabase.getInstance();
    ImageView imgEdit, imgLeave;
    View v;
    private DatabaseReference myref;

    private OnCallBack onCallBack;

    public adaperAdmin(ArrayList<Salles> listSalles, Context context) {
        this.listSalles = listSalles;
        this.context = context;
    }

    public void setOnCallBack(OnCallBack onCallBack) {
        this.onCallBack = onCallBack;
    }

    @NonNull
    @Override
    public myviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerow,parent,false);
        return new myviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull myviewHolder holder, int position) {
        holder.nom.setText(listSalles.get(position).getNom());
        holder.adresse.setText(listSalles.get(position).getAdresse());
        holder.tel.setText(listSalles.get(position).getTel());
        holder.imgLeave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCallBack.onButtonDeleteClick(listSalles.get(position));
            }
        });
        holder.imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCallBack.onButtonEditClick(listSalles.get(position));
            }
        });

    }

    @Override
    public int getItemCount() {
        return listSalles.size();
    }

    class myviewHolder extends RecyclerView.ViewHolder{
        TextView nom,adresse,tel,longitude,latitude;
        ImageView imgLeave, imgEdit;
        public myviewHolder(@NonNull View itemView) {
            super(itemView);
            nom = (TextView) itemView.findViewById(R.id.nomText);
            adresse = (TextView) itemView.findViewById(R.id.adresseText);
            tel = (TextView) itemView.findViewById(R.id.telText);
            longitude = (TextView) itemView.findViewById(R.id.longitudeText);
            latitude = (TextView) itemView.findViewById(R.id.latitudeText);
            /// Sur les image modifier et supprimer

            imgEdit = (ImageView) itemView.findViewById(R.id.imgModifier);

            imgLeave = (ImageView) itemView.findViewById(R.id.imgSupprimer);

            imgEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    System.out.println("dsffdsdsafasdffsdafdsa");
                }
            });


        }

    }

    public interface OnCallBack{
        void onButtonDeleteClick(Salles salles);
        void onButtonEditClick(Salles salles);
    }

}