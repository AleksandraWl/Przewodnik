package com.example.malami.przewodnikkulinarny;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UsunAdministratora extends AppCompatActivity {

    Spinner spinner;
    FirebaseHelper helper;
    DatabaseReference baza;
    String spinnerItemText;
    Button usun;
    EditText email;
    String genere;
    final ArrayList<String> lista = new ArrayList<>();
    TextView tw ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usun_administratora);
        baza = FirebaseDatabase.getInstance().getReference("Administratorzy");
        usun = (Button) findViewById(R.id.usun);
        email = findViewById(R.id.email);
        spinner = (findViewById(R.id.spinner));
        tw = findViewById(R.id.textView5);

        DatabaseReference databaseReference;

        //helper = new FirebaseHelper(db);

        spinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, fetchData()));

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



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
                Intent i = new Intent(UsunAdministratora.this, Logowanie.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
                finish();
        }

        return super.onOptionsItemSelected(item);
    }
    private ArrayList<String> fetchData() {
        //lista.clear();
        lista.add("Administratorzy");
        baza.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    admin Admin = ds.getValue(admin.class);
                    lista.add(Admin.getEmail());
                    //  Toast.makeText(this, ds+"", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return lista;

    }


    public void Usun(View view) {
        // genere = spinner.getSelectedItem().toString();
        final String semail = email.getText().toString().trim();
        tw.setText(semail);
       //baza.child(semail);

    }
}