package com.example.codrin.showitnow.utils.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.Query;

import java.util.List;

/**
 * Created by Codrin on 14/01/2018.
 */

@Entity(tableName = "Users")
public class User {
    @PrimaryKey(autoGenerate = true)
    private int uid;
    @ColumnInfo(name = "email_address")
    private String email;
    @ColumnInfo(name = "password")
    private String password;
    @ColumnInfo(name = "user_type")
    private int user_type;
    //0 will be for admin, 1 for client
}
