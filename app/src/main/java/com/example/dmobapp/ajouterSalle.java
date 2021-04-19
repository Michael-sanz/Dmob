package com.example.dmobapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class ajouterSalle extends AppCompatActivity {

    EditText nom, adresse,tel,lat,longitude;
    Button retour,ajouterSalle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajouter_salle);

        nom = (EditText)findViewById(R.id.ajouterNom);
        adresse = (EditText)findViewById(R.id.ajouterAdresse);
        tel = (EditText)findViewById(R.id.ajouterTel);
        lat = (EditText)findViewById(R.id.ajouterLatitude);
        longitude = (EditText)findViewById(R.id.ajouterLatitude);

        retour = (Button)findViewById(R.id.ajouterAnnuler);
        ajouterSalle = (Button)findViewById(R.id.ajouterValider);

        retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),AdminPage.class));
                finish();
            }
        });
        
        ajouterSalle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processinsert();
            }
        });
    }

    private void processinsert() {
        String longAvantMap = longitude.getText().toString();
        String latAvantMap = lat.getText().toString();
        Double Longitude = Double.parseDouble(longAvantMap);
        Double Latitude = Double.parseDouble(latAvantMap);
        Map<String,Object> map = new HashMap<>();
        map.put("Nom",nom.getText().toString());
        map.put("Adresse",adresse.getText().toString());
        map.put("Tel",tel.getText().toString());
        map.put("Latitude", Latitude);
        map.put("Longitude", Longitude);
        if (!nom.getText().toString().isEmpty() & !adresse.getText().toString().isEmpty() &!tel.getText().toString().isEmpty() &!lat.getText().toString().isEmpty() &!longitude.getText().toString().isEmpty())
        {
            FirebaseDatabase.getInstance().getReference().child("Salles").push()
                    .setValue(map)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            nom.setText("");
                            adresse.setText("");
                            tel.setText("");
                            longitude.setText("");
                            lat.setText("");
                            Intent i = new Intent(getApplicationContext(), AdminPage.class);
                            startActivity(i);
                            Toast.makeText(getApplicationContext(),"La salle à bien été ajoutée",Toast.LENGTH_SHORT).show();

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                    Toast.makeText(getApplicationContext(),"Ressayer !!! La salle n'a pas été ajoutée",Toast.LENGTH_SHORT).show();
                }
            });
        }
        else {
            Toast.makeText(getApplicationContext(),"Tous les champs sont obligatoires", Toast.LENGTH_SHORT).show();
        }
    }
}