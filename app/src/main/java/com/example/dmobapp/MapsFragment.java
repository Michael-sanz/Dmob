package com.example.dmobapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.icu.text.TimeZoneFormat;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.Inflater;

public class MapsFragment extends Fragment implements OnMapReadyCallback{
    GoogleMap gMap;
    ArrayList<LatLng> listePosition = new ArrayList<>();
    ArrayList<String> nomSalle = new ArrayList<>();

    HashMap<String,LatLng> listeNomPos = new HashMap<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_maps,container,false);
        SupportMapFragment supportMapFragment = (SupportMapFragment)getChildFragmentManager().findFragmentById(R.id.map);
        supportMapFragment.getMapAsync(this::onMapReady);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.gMap = googleMap;
        DatabaseReference maref;
        maref = FirebaseDatabase.getInstance().getReference().child("Salles");
        maref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1 : snapshot.getChildren()){
                    String nom = snapshot1.child("Nom").getValue().toString();
                    double longitude = snapshot1.child("Longitude").getValue(Double.class);
                    double latitude = snapshot1.child("Latitude").getValue(Double.class);
                    LatLng ll = new LatLng(longitude,latitude);
//                    listeNomPos.put(nom,ll);
                    nomSalle.add(nom);
                    listePosition.add(ll);
                }

//                for (Map.Entry mapentry : listeNomPos.entrySet()){
//                    System.out.println(mapentry.getKey());
//                    System.out.println(mapentry.getValue());
//                    String cle = mapentry.getKey().toString();
//                    String valeur = mapentry.getValue().toString();
//
////                    LatLng values = (LatLng) mapentry.getValue();
////                    gMap.addMarker(new MarkerOptions().position((LatLng) mapentry.getValue()));
//
//                }
                System.out.println(nomSalle);
                System.out.println(listePosition);
                for (int i=0 ; i<listePosition.size();i++){
                    for (int b=0; b<nomSalle.size();b++){
                        gMap.addMarker(new MarkerOptions().position(listePosition.get(i)).title(nomSalle.get(i)));
                        gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(listePosition.get(i),11.0f));
                        b++;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}