package com.example.malami.przewodnikkulinarny;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.BitSet;

public class MenuRestauracji extends AppCompatActivity {

    private static final String FileNameNazwa = "nazwa.txt";
    String nazwa;
    private DatabaseReference mDatabase;
    FirebaseDatabase database;
    private String n;
    final ArrayList<String> lista = new ArrayList<>();
    private Spinner spinner;
    private String genere;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> list = new ArrayList<>();
    private ListView listView;
    menuR menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_restauracji);


        menu= new menuR();
        listView= (ListView)findViewById(R.id.listView);




        FileInputStream FisNaz = null;
        try {
            FisNaz = openFileInput(FileNameNazwa);
            InputStreamReader isrNaz = new InputStreamReader(FisNaz);
            BufferedReader brNaz = new BufferedReader(isrNaz);
            StringBuilder sbNaz = new StringBuilder();
            String text;

            while ((text = brNaz.readLine()) != null) {
                sbNaz.append(text);
            }
            nazwa = sbNaz.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
    }

        mDatabase = FirebaseDatabase.getInstance().getReference("Menu").child(nazwa);
        //lubnaRef = mDatabase.child("Menu").child(nazwa);
        spinner = (findViewById(R.id.spinner3));
        spinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, fetchData()));
        genere = spinner.getSelectedItem().toString();
        Toast.makeText(this, nazwa+"eeeeeeeee", Toast.LENGTH_SHORT).show();




        list = new ArrayList<>();
        adapter = new ArrayAdapter<String>(this, R.layout.menu, R.id.textView, list);
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    menu = ds.getValue(menuR.class);
                    // n=res.getCena();
                    list.add(menu.getNazwa().toString() + " " + menu.getCena().toString());
                    //  Toast.makeText(this, ds+"", Toast.LENGTH_LONG).show();
                    nazwa = menu.getNazwa();
                    listView.setAdapter(adapter);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(i!=0){
                    Wybierz();
                }
            }
        });


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position!=0){
                    Wybierz();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                // sometimes you need nothing here
            }
        });


    }

    private void Wybierz() {
        
         Toast.makeText(this, "Wybrano : " +  nazwa, Toast.LENGTH_LONG).show();
    }

    private ArrayList<String> fetchData() {
        lista.clear();
        lista.add("Restauracje");
       //final int i=0;

        mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    menuR res = ds.getValue(menuR.class);
                   // n=res.getCena();
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
}