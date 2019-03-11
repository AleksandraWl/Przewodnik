package com.example.malami.przewodnikkulinarny;

import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.hardware.SensorManager;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.constraint.solver.widgets.Snapshot;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.firebase.ui.auth.AuthUI;
import com.google.firebase.database.ValueEventListener;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import okhttp3.internal.cache.DiskLruCache;


public class WyborJedzenia extends AppCompatActivity {


    Spinner spinner;
    //Button wyloguj;
    DatabaseReference db;
    GoogleMap gmap;
    final ArrayList<String> lista = new ArrayList<>();

    private SharedPreferences preferences;
    EditText NowyAdministrator;
    FirebaseAuth firebaseAuth;
    String genere;
    TextView adres;
    String dlugosc, szerokosc, Nazwa, Adres;

    DatabaseReference mDatabase;
    DatabaseReference lubnaRef;
    Double f_szerokosc;
    Double f_dlugosc;
    private static final int RECORD_REQUEST_CODE = 101;
    TextView d, s;
    private SensorManager manager;
    private static final String TAG = "";

    private static final String FileNameSzerokosc = "szerokosc.txt";
    private static final String FileNameDlugosc = "dlugosc.txt";
    private static final String FileNameNazwa = "nazwa.txt";
    private static final String FileNameAdres = "adres.txt";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wybor_jedzenia);


        mDatabase = FirebaseDatabase.getInstance().getReference();
        lubnaRef = mDatabase.child("Restauracje");
        d = findViewById(R.id.dlugosc);
        s = findViewById(R.id.szerokosc);


        firebaseAuth = FirebaseAuth.getInstance();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        db = FirebaseDatabase.getInstance().getReference("Restauracje");
        adres = findViewById(R.id.Adres);


        spinner = (findViewById(R.id.spinner));
        spinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, fetchData()));
        genere = spinner.getSelectedItem().toString();


        manager = (SensorManager) getSystemService(SENSOR_SERVICE);

        int permissionCheck = ContextCompat.checkSelfPermission(this,//pozwolenie
                Manifest.permission.ACCESS_FINE_LOCATION);
        ;
        int permissionCheck2 = ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION);
        int permissionCheck3 = ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permissionCheck != PackageManager.PERMISSION_GRANTED || permissionCheck2 !=
                PackageManager.PERMISSION_GRANTED || permissionCheck3 !=
                PackageManager.PERMISSION_GRANTED) {
            Log.i(TAG, "Permission to record denied");
            makeRequest();
        }
    }

    protected void makeRequest() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                },
                RECORD_REQUEST_CODE);
    }

    private ArrayList<String> fetchData() {
        lista.clear();
        lista.add("Restauracje");
        db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
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

    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.wyloguj:
                wylogowanie();

        }

        return super.onOptionsItemSelected(item);
    }


    public void zatwierdz(View view) {
        genere = spinner.getSelectedItem().toString();

        lubnaRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //These are all of your children.
                Map<String, Object> lubna = (Map<String, Object>) dataSnapshot.getValue();

                for (String childKey : lubna.keySet()) {
                    if (genere.equals("Restauracje")) {
                        adres.setText("Wybierz restauracje");
                    } else if (childKey.equals(genere)) {
                        //childKey is your "-LQka.. and so on"
                        //Your current object holds all the variables in your picture.
                        Map<String, Object> currentLubnaObject = (Map<String, Object>) lubna.get(childKey);

                        String adresRes = (String) currentLubnaObject.get("adres");
                        String Telefon = (String) currentLubnaObject.get("telefon");
                        String email= (String)currentLubnaObject.get("email");
                        String godziny= (String)currentLubnaObject.get("otwarcie");
                        kontakt(genere, adresRes, Telefon,godziny,email);

                    }
                    //You can access each variable like so: String variableName = (String) currentLubnaObject.get("INSERT_VARIABLE_HERE"); //data, description, taskid, time, title
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    private void showToast(String message) {
        Toast.makeText(getApplicationContext(),
                message,
                Toast.LENGTH_LONG).show();
    }


    private void kontakt(String nazwa,String adres, String telefon, String godziny, String email) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setTitle(nazwa);
        final String[] options = {"Godziny otwarcia: " + godziny, "Adres: "+ adres, "Telefon: "+telefon, "E-mail: "+email
        };
        dialogBuilder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
        dialogBuilder.setCancelable(false);
        dialogBuilder.setPositiveButton("Ok", new Dialog.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int whichButton) {

            }
        });

        dialogBuilder.create();
        dialogBuilder.show();
    }


    public void mapa(View view) {
        genere = spinner.getSelectedItem().toString();
        lubnaRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //These are all of your children.
                Map<String, Object> lubna = (Map<String, Object>) dataSnapshot.getValue();


                for (String childKey : lubna.keySet()) {
                    if (genere.equals("Restauracje")) {
                        adres.setText("Wybierz restauracje");
                    } else if (childKey.equals(genere)) {
                        //childKey is your "-LQka.. and so on"
                        //Your current object holds all the variables in your picture.
                        Map<String, Object> currentLubnaObject = (Map<String, Object>) lubna.get(childKey);

                        szerokosc = (String) currentLubnaObject.get("szerokosc");
                        dlugosc = (String) currentLubnaObject.get("dlugosc");
                        Nazwa = (String) currentLubnaObject.get("nazwa");
                        Adres = (String) currentLubnaObject.get("adres");
                    }
                    //You can access each variable like so: String variableName = (String) currentLubnaObject.get("INSERT_VARIABLE_HERE"); //data, description, taskid, time, title
                }

                if (!genere.equals("Restauracje")) {
                    f_szerokosc = Double.parseDouble(szerokosc);
                    f_dlugosc = Double.parseDouble(dlugosc);
                    s.setText(f_szerokosc + "");
                    d.setText(f_dlugosc + "");

                    String textSzerokosc = s.getText().toString();
                    FileOutputStream fosSzer = null;
                    try {
                        fosSzer = openFileOutput(FileNameSzerokosc, MODE_PRIVATE);
                        fosSzer.write(textSzerokosc.getBytes());
                        Toast.makeText(WyborJedzenia.this, "Save", Toast.LENGTH_SHORT).show();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        if (fosSzer != null) {
                            try {
                                fosSzer.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }


                    String textDlugosc = d.getText().toString();
                    FileOutputStream fosDlu = null;
                    try {
                        fosDlu = openFileOutput(FileNameDlugosc, MODE_PRIVATE);
                        fosDlu.write(textDlugosc.getBytes());
                        Toast.makeText(WyborJedzenia.this, "Save", Toast.LENGTH_SHORT).show();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                    s.setText(Nazwa);
                    d.setText(Adres);

                    String textNazwa = s.getText().toString();
                    FileOutputStream fosNa = null;
                    try {
                        fosNa = openFileOutput(FileNameNazwa, MODE_PRIVATE);
                        fosNa.write(textNazwa.getBytes());
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        if (fosNa != null) {
                            try {
                                fosNa.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    String textAdres = d.getText().toString();
                    FileOutputStream fosAd = null;
                    try {
                        fosAd = openFileOutput(FileNameAdres, MODE_PRIVATE);
                        fosAd.write(textAdres.getBytes());
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        if (fosAd != null) {
                            try {
                                fosAd.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }


                    Intent i = new Intent(WyborJedzenia.this, MapsActivity.class);
                    startActivity(i);
                }
            }

            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

//


    public Double getF_szerokosc() {
        // s.setText("Szerokosc: "+ f_szerokosc);
        return f_szerokosc;
    }

    public Double getF_dlugosc() {
        //d.setText("Dlugosc: "+f_dlugosc);
        return f_dlugosc;
    }


    public void menu(View view) {
        genere = spinner.getSelectedItem().toString();
        lubnaRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //These are all of your children.
                Map<String, Object> lubna = (Map<String, Object>) dataSnapshot.getValue();

                for (String childKey : lubna.keySet()) {
                    if (genere.equals("Restauracje")) {
                        adres.setText("Wybierz restauracje");
                    } else if (childKey.equals(genere))
                    {
                        Map<String, Object> currentLubnaObject = (Map<String, Object>) lubna.get(childKey);

                        szerokosc = (String) currentLubnaObject.get("szerokosc");
                        dlugosc = (String) currentLubnaObject.get("dlugosc");
                        Nazwa = (String) currentLubnaObject.get("nazwa");
                        Adres = (String) currentLubnaObject.get("adres");
                    }
                    //You can access each variable like so: String variableName = (String) currentLubnaObject.get("INSERT_VARIABLE_HERE"); //data, description, taskid, time, title
                }

                s.setText(Nazwa);

                if(!genere.equals("Restauracje")) {
                    String textNazwa = s.getText().toString();
                    FileOutputStream fosNa = null;
                    try {
                        fosNa = openFileOutput(FileNameNazwa, MODE_PRIVATE);
                        fosNa.write(textNazwa.getBytes());
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        if (fosNa != null) {
                            try {
                                fosNa.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }


                    Intent i = new Intent(WyborJedzenia.this, MenuRestauracji.class);
                    startActivity(i);
                }
            }


            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void wylogowanie() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setTitle("Wyjście");
        dialogBuilder.setMessage("Czy napewno?");
        dialogBuilder.setCancelable(false);
        dialogBuilder.setPositiveButton("Tak", new Dialog.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int whichButton) {
                showToast("Wychodzę");
                finish();
            }
        });
        dialogBuilder.setNegativeButton("Nie", new Dialog.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int whichButton) {
                showToast("Anulowano");

            }
        });
        dialogBuilder.create();
        dialogBuilder.show();
    }


}

