package com.example.retopokemon.Modelos;

import java.io.Serializable;

public class TypeName implements Serializable {
    private String name;

    public TypeName() {
    }

    public TypeName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
