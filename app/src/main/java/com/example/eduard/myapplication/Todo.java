package com.example.eduard.myapplication;

import java.util.Date;

/**
 * Created by Eduard on 12.05.2017.
 */

public class Todo {
    private String name;
    private String description;
    private boolean favourite;
    private String expire;

    public Todo(String name, String description, boolean favourite, String expire) {
        this.name = name;
        this.description = description;
        this.favourite = favourite;
        this.expire = expire;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isFavourite() {
        return favourite;
    }

    public void setFavourite(boolean favourite) {
        this.favourite = favourite;
    }

    public String getExpire() {
        return expire;
    }

    public void setExpire(String expire) {
        this.expire = expire;
    }

    @Override
    public String toString() {
        return "Todo{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", favourite=" + favourite +
                ", expire=" + expire +
                '}';
    }
}
