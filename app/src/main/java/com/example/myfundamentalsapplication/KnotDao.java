package com.example.myfundamentalsapplication;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface KnotDao {
    @Insert
    void insert(Knot knot);

    @Update
    void update(Knot knot);

    @Delete
    void delete(Knot knot);

    @Query("DELETE FROM knot_table")
    void deleteAllKnots();

    @Query("SELECT * FROM knot_table ORDER BY name")
    LiveData<List<Knot>> getAllKnots();
}
