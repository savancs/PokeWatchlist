package com.example.pokewatchlist;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.LinkedList;

public class viewModel extends ViewModel {

    private MutableLiveData<LinkedList<Pokemon>> pokeList;
    private MutableLiveData<Pokemon> currentPoke;

    Pokemon pikachu = new Pokemon("25", "Pikachu", "4", "60", "112","mega-punch", "static");
    
    public LiveData<LinkedList<Pokemon>> getPokes() {
        if (pokeList == null) {
            pokeList = new MutableLiveData<LinkedList<Pokemon>>();
            loadPoke();
        }
        return pokeList;
    }

    public void loadPoke(){
        LinkedList<Pokemon> pokeList2 = new LinkedList<>();
        pokeList2.add(pikachu);
        pokeList.setValue(pokeList2);

    }
    public MutableLiveData<Pokemon> getPoke() {
        if (currentPoke == null) {
            currentPoke = new MutableLiveData<Pokemon>();
            currentPoke.setValue(pikachu);
        }
        return currentPoke;
    }
    public void addPoke(Pokemon p){
        LinkedList<Pokemon> pokemon = pokeList.getValue();
        pokemon.add(p);
        pokeList.setValue(pokemon);
    }

    public void setcurrentPoke(Pokemon p) {
        if(currentPoke == null) {
            currentPoke = new MutableLiveData<Pokemon>();
            p = pikachu;
        }
        currentPoke.setValue(p);
    }


}
