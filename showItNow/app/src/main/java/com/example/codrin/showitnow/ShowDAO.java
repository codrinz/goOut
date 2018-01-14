package com.example.codrin.showitnow;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

/**
 * Created by Codrin on 13/01/2018.
 */

@Dao
public interface ShowDAO {
    @Query("SELECT * FROM shows")
    List<Show> getAll();
    @Insert
    void insert(Show show);
    @Delete
    void delete(Show show);
    @Update
    void update(Show show);
    @Query("SELECT * FROM shows WHERE show_name LIKE :name LIMIT 1")
    Show getShow(String name);
}
