package com.example.malami.przewodnikkulinarny;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.firebase.ui.auth.AuthUI;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;


public class WyborJedzeniaAdmin extends AppCompatActivity{

    FirebaseAuth firebaseAuth;
    DatabaseReference db;
    Spinner spinner;
    FirebaseHelper helper;
    Button wyloguj;

    final ArrayList<String> lista=new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wybor_jedzenia_admin);

        firebaseAuth = FirebaseAuth.getInstance();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        wyloguj=findViewById(R.id.wyloguj);

        spinner = (findViewById(R.id.spinner));
        db = FirebaseDatabase.getInstance().getReference("Restauracje");
        helper = new FirebaseHelper(db);

        spinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, fetchData()));

    }

    private ArrayList<String> fetchData()
    {
        lista.clear();
        lista.add("Restauracje");
        db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds:dataSnapshot.getChildren()) {
                    restauracje res = ds.getValue(restauracje.class);

                    lista.add(res.getNazwa());
                    //  Toast.makeText(this, ds+"", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return lista;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.wyloguj:
                Intent i = new Intent(WyborJedzeniaAdmin.this, Logowanie.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
                finish();
        }

        return super.onOptionsItemSelected(item);
    }


    public void zatwierdz(View view) {
        Intent i = new Intent (WyborJedzeniaAdmin.this, MapsActivity.class);
        startActivity(i);
    }

    public void administrator(View view) {
        Intent i = new Intent (WyborJedzeniaAdmin.this, administrator.class);
        startActivity(i);
    }


}

