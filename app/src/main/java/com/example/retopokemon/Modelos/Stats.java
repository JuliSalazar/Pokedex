package com.example.retopokemon.Modelos;

import java.io.Serializable;

public class Stats implements Serializable {
    private int base_stat;

    public int getStat() {
        return base_stat;
    }

    public void setStat(int stat) {
        this.base_stat = stat;
    }
}
