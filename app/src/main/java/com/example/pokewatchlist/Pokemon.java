package com.example.pokewatchlist;

public class Pokemon {
    String name;
    String natnum;
    String height;
    String weight;
    String xp;
    String move;
    String ability;

    public Pokemon(String natnum, String name, String height, String weight, String xp, String move, String ability){
        this.natnum = natnum;
        this.name = name;
        this.height = height;
        this.weight= weight;
        this.xp = xp;
        this.move = move;
        this.ability= ability;
    }
    public String getNum(){
        return natnum;
    }
    public String getName(){
        return name;
    }
    public String getHeight(){
        return height;
    }
    public String getWeight(){
        return weight;
    }
    public String getXp(){
        return xp;
    }
    public String getMove(){
        return move;
    }
    public String getAbility(){
        return ability;
    }
}