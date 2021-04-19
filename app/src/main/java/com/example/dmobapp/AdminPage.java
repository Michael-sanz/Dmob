package com.example.dmobapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.common.internal.Constants;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class AdminPage extends AppCompatActivity {

    RecyclerView recyclerView;
    adaperAdmin adapter;
    FloatingActionButton fbAdd, fbOut;
    private ArrayList<Salles> listeSalles;
    private DatabaseReference myref;
    private final FirebaseDatabase database = FirebaseDatabase.getInstance();


    public AdminPage() {
        // Required empty public constructor
//        this.dataHolderAdmin = dataHolderAdmin;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_page);
        recyclerView = findViewById(R.id.recyclerAmin);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        listeSalles = new ArrayList<>();
        myref = FirebaseDatabase.getInstance().getReference();
        Query query = myref.child("Salles");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    Salles salles = new Salles();
                    salles.setId(snapshot1.getKey());
                    salles.setNom(snapshot1.child("Nom").getValue().toString());
                    salles.setAdresse(snapshot1.child("Adresse").getValue().toString());
                    salles.setTel(snapshot1.child("Tel").getValue().toString());
                    String Lat = snapshot1.child("Latitude").getValue().toString();
                    Double Latitude = Double.parseDouble(Lat);
                    String Long = snapshot1.child("Longitude").getValue().toString();
                    Double Longitude = Double.parseDouble(Long);
                    salles.setLongitude(Latitude);
                    salles.setLatitude(Longitude);

                    listeSalles.add(salles);
                }
                for (int i = 0;i<listeSalles.size();i++){
                    System.out.println(listeSalles.get(i).getLongitude());
                }
                System.out.println(listeSalles);
                adapter = new adaperAdmin(listeSalles,AdminPage.this);
                recyclerView.setAdapter(adapter);
                setClick();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

            private void setClick() {
                adapter.setOnCallBack(new adaperAdmin.OnCallBack() {
                    @Override
                    public void onButtonDeleteClick(Salles salles) {
                        deleteSalle(salles);
                    }

                    @Override
                    public void onButtonEditClick(Salles salles) {
                        updatDialog(salles);
                    }


                });
            }
            private void updatDialog(Salles salles) {
                Dialog dialog = new Dialog(AdminPage.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.modifier_salles);
//                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.setCancelable(true);
                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(Objects.requireNonNull(dialog.getWindow().getAttributes()));
                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
                dialog.getWindow().setAttributes(lp);

                Button btnAnnuler = dialog.findViewById(R.id.modiferAnnuler);
                btnAnnuler.setOnClickListener(v -> { dialog.dismiss();});

                EditText edNom = dialog.findViewById(R.id.modifierNom);
                edNom.setText(salles.getNom());

                EditText edAdresse = dialog.findViewById(R.id.modifierAdresse);
                edAdresse.setText(salles.getAdresse());


                EditText edTel = dialog.findViewById(R.id.modifierTel);
                edTel.setText(salles.getTel());


                Button btnModifier = dialog.findViewById(R.id.modifierValider);
                btnModifier.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        database.getReference("Salles").child(salles.getId()).child("Nom").setValue(edNom.getText().toString());
                        database.getReference("Salles").child(salles.getId()).child("Adresse").setValue(edAdresse.getText().toString());
                        database.getReference("Salles").child(salles.getId()).child("Tel").setValue(edTel.getText().toString());
                        Toast.makeText(getApplicationContext(),"Salle modifiée !",Toast.LENGTH_SHORT).show();
//                        database.getReference("Salles").child(salles.getId()).child("Longitude").setValue(edLongitude.getText());
                        Intent i = new Intent(getApplicationContext(), AdminPage.class);
                        startActivity(i);
                    }
                });
                dialog.show();
            }

            private void deleteSalle(Salles salles){
                AlertDialog.Builder builder = new AlertDialog.Builder(AdminPage.this);
                builder.setMessage("Êtes vous sûr de vouloir supprimer cette salle ? ");
                builder.setCancelable(true);
                builder.setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        database.getReference("Salles").child(salles.getId()).removeValue();
                        Toast.makeText(getApplicationContext(),"Salle supprimer !",Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(getApplicationContext(), AdminPage.class);
                        startActivity(i);
                        dialog.cancel();
                    }
                });
                builder.setNegativeButton("Non", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();

            }

        });

        fbAdd = findViewById(R.id.floatingAddButton);
        fbAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), ajouterSalle.class);
                startActivity(i);
            }
        });

        fbOut = findViewById(R.id.btnOut);
        fbOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }
        });
    }
}