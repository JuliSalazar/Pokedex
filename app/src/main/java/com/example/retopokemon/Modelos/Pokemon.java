package com.example.retopokemon.Modelos;

import java.io.Serializable;
import java.util.List;

public class Pokemon implements Serializable {
    private String id;
    private String uuid;
    private String name;
    private Sprites sprites;
    private List<Stats> stats;
    private List<PokeType> types;
    private long time;

    public Pokemon(String id, String name, Sprites sprites, List<Stats> stats, List<PokeType> types) {
        this.id = id;
        this.name = name;
        this.sprites = sprites;
        this.stats = stats;
        this.types = types;
        this.time = 0;
    }

    public Pokemon() {
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Sprites getSprites() {
        return sprites;
    }

    public List<Stats> getStats() {
        return stats;
    }

    public void setStats(List<Stats> stats) {
        this.stats = stats;
    }

    public List<PokeType> getTypes() {
        return types;
    }

    public void setTypes(List<PokeType> types) {
        this.types = types;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public void setSprites(Sprites sprites) {
        this.sprites = sprites;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
