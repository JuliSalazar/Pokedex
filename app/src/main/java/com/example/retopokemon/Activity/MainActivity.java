package com.example.retopokemon.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.retopokemon.Modelos.User;
import com.example.retopokemon.R;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.UUID;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button ingresarBTN;
    private EditText usernameEDT;
    private FirebaseFirestore db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ingresarBTN = findViewById(R.id.ingresarBTN);
        usernameEDT = findViewById(R.id.usernameEDT);
        ingresarBTN.setOnClickListener(this);
        db = FirebaseFirestore.getInstance();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ingresarBTN:
                String username = usernameEDT.getText().toString();
                if(username.isEmpty()){
                    Toast.makeText(this, "Ingresa un nombre de usuario", Toast.LENGTH_LONG).show();
                }else{

                    User user = new User(UUID.randomUUID().toString(), username);
                    CollectionReference usersRef = db.collection("users");
                    Query query = usersRef.whereEqualTo("username", username);
                    query.get().addOnCompleteListener(
                            task -> {
                                if (task.isSuccessful()){
                                    if (task.getResult().size() > 0){
                                        for (QueryDocumentSnapshot document : task.getResult()){
                                            User dbUser = document.toObject(User.class);
                                            goToPokedexActivity(dbUser);
                                            break;
                                        }
                                        Toast.makeText(this,"Ya existe el usuario", Toast.LENGTH_LONG).show();
                                    }else {
                                        db.collection("users").document(user.getId()).set(user);
                                        goToPokedexActivity(user);
                                    }
                                }
                            }
                    );
                }

                break;
        }
    }

    public void goToPokedexActivity(User user){
        Intent i = new Intent(this, PokedexActivity.class);
        i.putExtra("myUser", user);
        startActivity(i);
    }
}