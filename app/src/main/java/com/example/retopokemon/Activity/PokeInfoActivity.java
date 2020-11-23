package com.example.retopokemon.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.retopokemon.Modelos.Pokemon;
import com.example.retopokemon.R;
import com.google.firebase.firestore.FirebaseFirestore;

public class PokeInfoActivity extends AppCompatActivity {

    private Pokemon myPokemon;
    private Button soltarBTN;
    private ImageView pokeIV;
    private TextView pokeNameTV;
    private TextView pokeTypeTV;
    private TextView pokeDefTV;
    private TextView pokeHpTV;
    private TextView pokeAttackTV;
    private TextView pokeSpeedTV;
    private FirebaseFirestore db;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poke_info);

        pokeNameTV = findViewById(R.id.pokeNameInfoTV);
        pokeTypeTV = findViewById(R.id.typeInfoTV);
        pokeIV = findViewById(R.id.pokeIV);
        pokeDefTV = findViewById(R.id.defTV);
        pokeHpTV = findViewById(R.id.hpTV);
        pokeAttackTV = findViewById(R.id.attackTV);
        pokeSpeedTV = findViewById(R.id.speedTV);
        soltarBTN = findViewById(R.id.soltarBTN);

        //Pokemon pedido
        myPokemon = (Pokemon) getIntent().getExtras().getSerializable("pokemon");
        //Types
        String type = "";
        for (int i= 0; i<myPokemon.getTypes().size(); i++){
            String typeName = myPokemon.getTypes().get(i).getTypeName().getName();
            if(i>0){
                type += " - ";
            }
            type += typeName.substring(0, 1).toUpperCase() + typeName.substring(1);


        }
        pokeTypeTV.setText(type);
        userID = getIntent().getExtras().getString("userId");
        Glide.with(this).load(myPokemon.getSprites().getImg()).into(pokeIV);
        pokeNameTV.setText(myPokemon.getName().substring(0, 1).toUpperCase() + myPokemon.getName().substring(1));

        pokeHpTV.setText(""+myPokemon.getStats().get(0).getStat());
        pokeAttackTV.setText(""+myPokemon.getStats().get(1).getStat());
        pokeDefTV.setText(""+myPokemon.getStats().get(2).getStat());
        pokeSpeedTV.setText(""+myPokemon.getStats().get(5).getStat());

        db = FirebaseFirestore.getInstance();

        soltarBTN.setOnClickListener(
                (v)->{
                    db.collection("users").document(userID).
                            collection("pokemones").document(myPokemon.getUuid()).delete();
                    finish();
                }
        );

    }
}