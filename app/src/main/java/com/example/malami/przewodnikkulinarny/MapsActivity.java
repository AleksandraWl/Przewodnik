package com.example.malami.przewodnikkulinarny;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class MapsActivity extends FragmentActivity
        implements GoogleMap.OnMyLocationButtonClickListener,
        GoogleMap.OnMyLocationClickListener,
        OnMapReadyCallback {

    private GoogleMap mMap;
    private static final String FileNameSzerokosc= "szerokosc.txt";
    Double szerokosc;
    private static final String FileNameDlugosc= "dlugosc.txt";
    Double dlugosc;
    private static final String FileNameNazwa="nazwa.txt";
    private static final String FileNameAdres="adres.txt";
    String nazwa, adres;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    public void onMapReady(GoogleMap map) {
        mMap = map;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        FileInputStream FisSzer = null;
        try {
            FisSzer = openFileInput(FileNameSzerokosc);
            InputStreamReader isr = new InputStreamReader(FisSzer);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String text;

            while ((text = br.readLine())!= null){
            sb.append(text).append("\n");
            }
            text=sb.toString();
            szerokosc = Double.parseDouble(text);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        FileInputStream FisDlu = null;
        try {
            FisDlu = openFileInput(FileNameDlugosc);
            InputStreamReader isrDl = new InputStreamReader(FisDlu);
            BufferedReader brDl = new BufferedReader(isrDl);
            StringBuilder sbDL = new StringBuilder();
            String text;

            while ((text = brDl.readLine())!= null){
                sbDL.append(text).append("\n");
            }
            text=sbDL.toString();
            dlugosc = Double.parseDouble(text);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        FileInputStream FisNaz = null;
        try {
            FisNaz = openFileInput(FileNameNazwa);
            InputStreamReader isrNaz = new InputStreamReader(FisNaz);
            BufferedReader brNaz = new BufferedReader(isrNaz);
            StringBuilder sbNaz = new StringBuilder();
            String text;

            while ((text = brNaz.readLine())!= null){
                sbNaz.append(text).append("\n");
            }
            nazwa=sbNaz.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        FileInputStream FisAd = null;
        try {
            FisAd = openFileInput(FileNameAdres);
            InputStreamReader isrAd = new InputStreamReader(FisAd);
            BufferedReader brAd = new BufferedReader(isrAd);
            StringBuilder sbAd = new StringBuilder();
            String text;

            while ((text = brAd.readLine())!= null){
                sbAd.append(text).append("\n");
            }
            adres=sbAd.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        mMap.setMyLocationEnabled(true);
        mMap.setOnMyLocationButtonClickListener(this);
        mMap.setOnMyLocationClickListener(this);
        map.addMarker(new MarkerOptions()
                .position(new LatLng(szerokosc, dlugosc)).title(nazwa).snippet(adres));
        LatLng punkt = new LatLng( szerokosc, dlugosc);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom( punkt,18));
    }



    public void onMyLocationClick(@NonNull Location location) {
        Toast.makeText(this, "Twoja lokalizacja", Toast.LENGTH_LONG).show();
    }


    public boolean onMyLocationButtonClick() {
      //  Toast.makeText(this, "Twoja lokalizacja", Toast.LENGTH_SHORT).show();
        // Return false so that we don't consume the event and the default behavior still occurs
        // (the camera animates to the user's current position).
        return false;
    }
}
