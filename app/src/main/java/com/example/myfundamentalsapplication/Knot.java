package com.example.myfundamentalsapplication;

import android.graphics.drawable.Drawable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "knot_table")
public class Knot {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private int description;
    private int image;

    public Knot(String name, int description, int image) {
        this.name = name;
        this.description = description;
        this.image = image;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDescription() {
        return description;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public int getImage() {
        return image;
    }
}
