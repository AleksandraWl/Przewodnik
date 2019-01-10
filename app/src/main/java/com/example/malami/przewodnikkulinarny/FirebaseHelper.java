package com.example.malami.przewodnikkulinarny;

/**
 * Created by MalaMi on 20.09.2018.
 */

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class FirebaseHelper {

    DatabaseReference db;
    Boolean saved=null;
    kategorie spacecraft = null;

    public FirebaseHelper(DatabaseReference db) {
        this.db = db;
    }

    /*//SAVE
    public  Boolean save(kategorie spacecraft)
    {
        if(spacecraft==null)
        {
            saved=false;
        }else
        {
            try
            {
                db.child("Kategoria").push().setValue(spacecraft);
                saved=true;
            }catch (DatabaseException e)
            {
                e.printStackTrace();
                saved=false;
            }
        }

        return saved;
    }*/


    public ArrayList<String> ListaKategorie()
    {
        final ArrayList<String> lista=new ArrayList<>();
        lista.clear();
        lista.add("Kategoria");


        db.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                fetchData(dataSnapshot,lista);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                fetchData(dataSnapshot,lista);

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return lista;
    }


    public ArrayList<String> ListaAdministratorow()
    {
        final ArrayList<String> lista=new ArrayList<>();
        lista.clear();
        lista.add("Administratorzy");


        db.addChildEventListener(new ChildEventListener() {
            @Override

            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Administratorzy(dataSnapshot,lista);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Administratorzy(dataSnapshot,lista);

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return lista;
    }

    private void fetchData(DataSnapshot snapshot,ArrayList<String> lista)
    {

        lista.clear();
        lista.add("Kategoria");
        for (DataSnapshot ds:snapshot.getChildren())
        {
            String kategoria=ds.getValue(kategorie.class).getKategoria();
            lista.add(kategoria);
        }
    }

    private void Administratorzy(DataSnapshot snapshot,ArrayList<String> lista)
    {
        lista.clear();
        lista.add("Administratorzy");
        for (DataSnapshot ds:snapshot.getChildren())
        {
            String administrator=ds.getValue(admin.class).getEmail();
            lista.add(administrator);
        }
    }

}