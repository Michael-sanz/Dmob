package com.example.dmobapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.concurrent.Executor;

public class LogInFragment extends Fragment {

    View view;

    Button buttonConnexion;

    EditText etEmail, etPassword;

    FirebaseAuth fAuth;


    public LogInFragment() {
        // Required empty public constructor
    }


    public void connectUser() {
        try {
            if (!etEmail.getText().toString().isEmpty() && !etPassword.getText().toString().isEmpty()) {

                fAuth.signInWithEmailAndPassword(etEmail.getText().toString().trim(), etPassword.getText().toString().trim())
                        .addOnCompleteListener((Activity) getContext(), new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
//                                    View view = inflater.inflate(R.layout.fragment_salles,container,false);
                                startActivity(new Intent(getActivity().getApplicationContext(), AdminPage.class));
                                getActivity().finish();
//                                    Intent i = new Intent(getActivity(), SallesFragment.class);
//
////                                    inflater.inflate(R.layout.fragment_log_in, container, false);
//                                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_layout,
//                                            selectedFragment).commit();
                            }
                        });
            } else {
                Toast.makeText(getContext(), "Remplissez les champs !", Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


    private void attachXml() {
        try {
            etEmail = view.findViewById(R.id.editTextEmail);
            etPassword = view.findViewById(R.id.editTextPwd);
            buttonConnexion = view.findViewById(R.id.btnLogin);
//            DB
            fAuth = FirebaseAuth.getInstance();

            buttonConnexion.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    connectUser();

                }
            });
        } catch (Exception e) {
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_log_in, container, false);
        attachXml();
        return view;
    }
}