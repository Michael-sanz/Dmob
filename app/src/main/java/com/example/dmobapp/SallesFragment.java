package com.example.dmobapp;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class SallesFragment extends Fragment {
    RecyclerView recyclerView;

    ArrayList<Salles> dataHolder;

    private DatabaseReference myref;

    FloatingActionButton btnAdd;

    FirebaseAuth firebaseAuth;



    public SallesFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("WrongConstant")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View rootView = inflater.inflate(R.layout.fragment_salles,container,false);
        recyclerView = rootView.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        dataHolder = new ArrayList<>();
        GetDataFromFirebase();
        return rootView;
    }

    private void GetDataFromFirebase() {
        myref = FirebaseDatabase.getInstance().getReference();
        Query query = myref.child("Salles");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    Salles salles = new Salles();

                    salles.setNom(snapshot1.child("Nom").getValue().toString());

                    salles.setAdresse(snapshot1.child("Adresse").getValue().toString());

                    salles.setTel(snapshot1.child("Tel").getValue().toString());

//                    salles.setLongitude(snapshot1.child("Longitude").getValue().toString());
//
//                    salles.setLatitude(snapshot1.child("Latitude").getValue().toString());

                    dataHolder.add(salles);
                }
                recyclerView.setAdapter(new myAdapter(dataHolder,getContext()));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}