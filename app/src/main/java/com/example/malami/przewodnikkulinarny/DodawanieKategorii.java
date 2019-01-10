package com.example.malami.przewodnikkulinarny;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DodawanieKategorii extends AppCompatActivity {


    Spinner spinner;
    EditText NowaKategoria;
    EditText Informacje;
   // EditText Lokalizacja;
    DatabaseReference kategorie;
    String kategoria;
    String informacje;
    //String lokalizacja;
    DatabaseReference db;
    final ArrayList<String> lista=new ArrayList<>();
    String genere;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dodawanie_kategorii);

        db = FirebaseDatabase.getInstance().getReference("Restauracje");

        spinner=(findViewById(R.id.spinner2));
        NowaKategoria=(findViewById(R.id.NowaKategoria));
        Informacje = (findViewById(R.id.Informacje));
       // Lokalizacja= (findViewById(R.id.Adres));
        kategorie = FirebaseDatabase.getInstance().getReference("Kategorie");

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        spinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, fetchData()));


    }

    private ArrayList<String> fetchData()
    {
        lista.clear();
        lista.add("Wybierz restauracje");
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
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.wyloguj:
                Intent i = new Intent(DodawanieKategorii.this, Logowanie.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
                finish();
        }

        return super.onOptionsItemSelected(item);
    }


    public void DodajKategorie(View view) {
        genere = spinner.getSelectedItem().toString();


       /* kategoria = NowaKategoria.getText().toString().trim();
        informacje= Informacje.getText().toString().trim();
      //  lokalizacja= Lokalizacja.getText().toString().trim();


        if(!TextUtils.isEmpty(kategoria))
        {
            kategorie kat = new kategorie(kategoria, informacje);
            String id= kategorie.push().getKey();
            kategorie.child(id).setValue(kat);

            Toast.makeText(this, "Ok, dodano", Toast.LENGTH_SHORT).show();
        }
        else {Toast.makeText(this, "Nie dodano", Toast.LENGTH_SHORT).show();}
*/



    }
}
