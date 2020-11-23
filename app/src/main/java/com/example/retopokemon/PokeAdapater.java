package com.example.retopokemon;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.retopokemon.Activity.PokeInfoActivity;
import com.example.retopokemon.Activity.PokedexActivity;
import com.example.retopokemon.Modelos.Pokemon;

import java.util.ArrayList;

public class PokeAdapater extends RecyclerView.Adapter<PokemonView> implements PokemonView.pokeClicked{

    private ArrayList<Pokemon> pokemons;
    private String userId;

    public PokeAdapater(String userId){
        pokemons = new ArrayList<>();
        this.userId = userId;
    }
    public void addPokemon(Pokemon pokemon){
        pokemons.add(pokemon);
        this.notifyDataSetChanged();
    }

    public void addPokemonInitialPos(Pokemon pokemon){
        pokemons.add(0,pokemon);
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PokemonView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View row = inflater.inflate(R.layout.pokecol,parent,false);
        ConstraintLayout rowroot = (ConstraintLayout) row;
        PokemonView pokemonView = new PokemonView(rowroot);

        return pokemonView;
    }

    @Override
    public void onBindViewHolder(@NonNull PokemonView holder, int position) {
        Pokemon poke = pokemons.get(position);
        holder.getName().setText(poke.getName().substring(0, 1).toUpperCase() + poke.getName().substring(1));
        Glide.with(holder.getRoot()).load(poke.getSprites().getImg()).into(holder.getImg());
        holder.setPokemon(poke);
        holder.setBtn(this);
    }

    @Override
    public int getItemCount() {
        return pokemons.size();
    }


    public void clearList() {
        pokemons.clear();
        this.notifyDataSetChanged();
    }

    @Override
    public void pokeClicked(Pokemon pokemon, View v) {
        Intent i = new Intent(v.getContext(), PokeInfoActivity.class);
        i.putExtra("pokemon", pokemon);
        i.putExtra("userId", userId);
        v.getContext().startActivity(i);
    }
}
