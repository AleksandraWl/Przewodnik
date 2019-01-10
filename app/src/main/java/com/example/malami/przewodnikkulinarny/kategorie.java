package com.example.malami.przewodnikkulinarny;

/**
 * Created by MalaMi on 19.09.2018.
 */

public class kategorie {
    String kategoria;
    String informacja;
    //String lokalizacja;

    kategorie(){

    }

    public kategorie(String kategoria, String informacja) //,String lokalizacja)
    {
        this.kategoria = kategoria;
        this.informacja=informacja;
      //  this.lokalizacja=lokalizacja;
    }

    public String getKategoria() {
        return kategoria;
    }

    public String getInformacja(){
        return informacja;
    }

   /* public String getLokalizacja() {
        return lokalizacja;
    }

    public void setLokalizacja(String lokalizacja) {
        this.lokalizacja = lokalizacja;
    }*/

    public void setInformacja(String informacja) {
        this.informacja = informacja;
    }

    public void setKategoria(String kategoria) {
        this.kategoria = kategoria;
    }


}
