package com.example.retopokemon;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.retopokemon.Modelos.Pokemon;

public class PokemonView extends RecyclerView.ViewHolder implements View.OnClickListener {

    private ConstraintLayout root;
    private ImageView img;
    private TextView name;
    private pokeClicked btn;
    private Pokemon pokemon;

    public PokemonView(ConstraintLayout root) {
        super(root);
        this.root = root;
        name = root.findViewById(R.id.namePokeTV);
        img = root.findViewById(R.id.pokeImg);
        this.root.setOnClickListener(this);
    }

    public ConstraintLayout getRoot() {
        return root;
    }

    public ImageView getImg() {
        return img;
    }

    public TextView getName() {
        return name;
    }

    public void setPokemon(Pokemon pokemon) {
        this.pokemon = pokemon;
    }

    @Override
    public void onClick(View view) {
        if (btn!=null){
            btn.pokeClicked(this.pokemon,this.root);
        }
    }

    public void setBtn(pokeClicked btn) {
        this.btn = btn;
    }

    public interface pokeClicked{
        void pokeClicked(Pokemon pokemon, View v);
    }

}
