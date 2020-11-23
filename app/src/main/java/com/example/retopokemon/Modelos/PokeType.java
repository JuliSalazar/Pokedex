package com.example.retopokemon.Modelos;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PokeType implements Serializable {
    private TypeName type;

    public PokeType() {
    }

    public PokeType(TypeName typeName) {
        this.type = typeName;
    }

    public TypeName getTypeName() {
        return type;
    }

    public void setTypeName(TypeName typeName) {
        this.type = typeName;
    }
}
