package com.example.codrin.showitnow.utils.entities;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

/**
 * Created by Codrin on 14/01/2018.
 */
@Dao
public interface UserDAO
{
    @Query("SELECT * FROM users")
    List<User> getAll();
    @Insert
    void insert(User user);
    @Delete
    void delete(User user);
    @Update
    void update(User user);

    @Query("SELECT * FROM users WHERE email_address LIKE :email LIMIT 1")
    Show getUserByMail(String email);
}
