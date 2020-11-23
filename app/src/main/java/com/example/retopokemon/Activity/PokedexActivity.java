package com.example.retopokemon.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.retopokemon.Modelos.Pokemon;
import com.example.retopokemon.Modelos.User;
import com.example.retopokemon.PokeAdapater;
import com.example.retopokemon.R;
import com.example.retopokemon.Util.Constants;
import com.example.retopokemon.Util.HTTPSWebUtilDomi;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.gson.Gson;

import java.util.UUID;

public class PokedexActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText namePokemon;
    private EditText searchPokeEDT;
    private Button atraparBTN;
    private Button searchBTN;
    private RecyclerView pokemonsViewList;
    LinearLayoutManager layoutManager;
    private PokeAdapater adapater;
    private FirebaseFirestore db;
    private User myUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokedex);

        //Usuario logueado
        myUser = (User) getIntent().getExtras().getSerializable("myUser");

        searchPokeEDT = findViewById(R.id.searchEDT);
        atraparBTN = findViewById(R.id.atraparBTN);
        searchBTN = findViewById(R.id.searchBTN);
        namePokemon = findViewById(R.id.pokeNameEDT);
        pokemonsViewList = findViewById(R.id.pokemonsViewList);
        pokemonsViewList.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        pokemonsViewList.setLayoutManager(layoutManager);
        adapater = new PokeAdapater(myUser.getId());
        pokemonsViewList.setAdapter(adapater);
        atraparBTN.setOnClickListener(this);
        searchBTN.setOnClickListener(this);
        db = FirebaseFirestore.getInstance();
        CollectionReference usersRef = db.collection("users");


      /*  usersRef.get().addOnCompleteListener(
                task->{
                    for (DocumentSnapshot doc: task.getResult()){

                    }
                }
        );*/
    }

    public void updatePokemons(){
        adapater.clearList();
        db.collection("users").document(myUser.getId()).
                collection("pokemones").orderBy("time", Query.Direction.DESCENDING).get().addOnCompleteListener(
                task->{
                    for (DocumentSnapshot doc: task.getResult().getDocuments()){
                        adapater.addPokemon(doc.toObject(Pokemon.class));
                    }
                }
        );
    }

    public void searchPokemons(){
        adapater.clearList();
        String pokeName = searchPokeEDT.getText().toString().trim().toLowerCase();
        if(!pokeName.isEmpty()){
            db.collection("users").document(myUser.getId()).
                    collection("pokemones").whereEqualTo("name", pokeName).get().addOnCompleteListener(
                    task->{
                        for (DocumentSnapshot doc: task.getResult().getDocuments()){
                            adapater.addPokemon(doc.toObject(Pokemon.class));
                        }
                    }
            );
        }else{
            updatePokemons();
        }
    }

    @Override
    protected void onResume() {
        updatePokemons();
        super.onResume();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.atraparBTN:
                String pokemon = namePokemon.getText().toString().trim().toLowerCase();
                if(pokemon.equals("")){
                    Toast.makeText(this, "Ingresa el nombre de un pokemon", Toast.LENGTH_LONG).show();
                }else{
                    new Thread(
                            () -> {
                                HTTPSWebUtilDomi http = new HTTPSWebUtilDomi();
                                String response = http.GETrequest(Constants.POKEURL + pokemon);
                                    Gson gson = new Gson();
                                    Pokemon pokemon1 = gson.fromJson(response, Pokemon.class);
                                    runOnUiThread(
                                            ()->{
                                                if(pokemon1 == null){
                                                    Toast.makeText(this, "No existe el pokemon :(", Toast.LENGTH_LONG).show();
                                                }else {
                                                    Toast.makeText(this, "¡Has atrapado un "+pokemon1.getName()+"!", Toast.LENGTH_LONG).show();
                                                    pokemon1.setTime(System.currentTimeMillis());
                                                    adapater.addPokemonInitialPos(pokemon1);
                                                    String uuid = UUID.randomUUID().toString();
                                                    pokemon1.setUuid(uuid);
                                                    db.collection("users").document(myUser.getId()).
                                                            collection("pokemones").document(uuid).set(pokemon1);
                                                }
                                            }
                                    );
                            }
                    ).start();

                }
                break;
            case R.id.searchBTN:
                searchPokemons();
                break;
        }
    }
}