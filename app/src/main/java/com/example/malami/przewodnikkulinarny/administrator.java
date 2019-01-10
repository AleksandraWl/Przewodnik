package com.example.malami.przewodnikkulinarny;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

public class administrator extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_administrator);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.wyloguj:
                //  AuthUI.getInstance().signOut(this);
                Intent i = new Intent(administrator.this, Logowanie.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
                finish();

        }

        return super.onOptionsItemSelected(item);
    }

    public void dodajAdministratora(View view) {
        Intent i = new Intent(administrator.this, DodawanieAdministratora.class);
        startActivity(i);
    }

    public void usunAdministratora(View view) {
        Intent i = new Intent (administrator.this, UsunAdministratora.class);
        startActivity(i);
    }


    public void dodawanieKategorii(View view) {
            Intent i = new Intent(administrator.this, DodawanieKategorii.class);
            startActivity(i);
        }


    public void DodajRestauracje(View view) {
        Intent i = new Intent (administrator.this, DodawanieRestauracji.class);
        startActivity(i);
    }
}



