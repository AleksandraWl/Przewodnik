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
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DodawanieAdministratora extends AppCompatActivity {

    EditText NowyAdministrator;
    DatabaseReference administratorzy;
    String email;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dodawanie_administratora);
        NowyAdministrator=(findViewById(R.id.NowyAdministrator));
        administratorzy = FirebaseDatabase.getInstance().getReference("Administratorzy");

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
                Intent i = new Intent(DodawanieAdministratora.this, Logowanie.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
                finish();

        }

        return super.onOptionsItemSelected(item);
    }


    public void dodajAdministratora(View view) {
        email = NowyAdministrator.getText().toString().trim();
        if(!TextUtils.isEmpty(email))
        {
            TworzenieAdmina(email);
        }
        else             Toast.makeText(this, "Podaj e-mail", Toast.LENGTH_SHORT).show();

        Intent i  = new Intent (DodawanieAdministratora.this, administrator.class);
        startActivity(i);
    }


    public void TworzenieAdmina(final String emailAdmin)
    {
        final int[] zmienna = {0};
        databaseReference= FirebaseDatabase.getInstance().getReference("Administratorzy");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot s : dataSnapshot.getChildren()){
                    admin Admin = s.getValue(admin.class);
                    if (Admin.getEmail().equals(emailAdmin)) {
                        zmienna[0]++;
                        Toast.makeText(DodawanieAdministratora.this, "Podany e-mail istnieje w bazie administratorów", Toast.LENGTH_SHORT).show();


                    }
                   /* else
                    {
                    admin admin = new admin(emailAdmin);
                    String id= administratorzy.push().getKey();
                    administratorzy.child(id).setValue(admin);
                    Toast.makeText(DodawanieAdministratora.this, "Dodano nowego administratora", Toast.LENGTH_SHORT).show();
                    }*/

                }
              //  Toast.makeText(DodawanieAdministratora.this, "" + zmienna[0], Toast.LENGTH_SHORT).show();;
            if (zmienna[0]==0)
            {

                admin admin = new admin(emailAdmin);
                String id= administratorzy.push().getKey();
                administratorzy.child(id).setValue(admin);
                Toast.makeText(DodawanieAdministratora.this, "Dodano nowego administratora", Toast.LENGTH_SHORT).show();
            }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });


    }
}
