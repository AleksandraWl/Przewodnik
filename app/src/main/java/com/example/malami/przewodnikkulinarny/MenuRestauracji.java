package com.example.malami.przewodnikkulinarny;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MenuRestauracji extends AppCompatActivity {

    private static final String FileNameNazwa = "nazwa.txt";
    String nazwa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_restauracji);

        FileInputStream FisNaz = null;
        try {
            FisNaz = openFileInput(FileNameNazwa);
            InputStreamReader isrNaz = new InputStreamReader(FisNaz);
            BufferedReader brNaz = new BufferedReader(isrNaz);
            StringBuilder sbNaz = new StringBuilder();
            String text;

            while ((text = brNaz.readLine()) != null) {
                sbNaz.append(text).append("\n");
            }
            nazwa = sbNaz.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}