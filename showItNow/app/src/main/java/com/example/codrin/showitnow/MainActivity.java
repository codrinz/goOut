package com.example.codrin.showitnow;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.codrin.showitnow.utils.localStorage;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        localStorage localStorage = new localStorage();
        DatabaseUtils db = new DatabaseUtils();
        db.getAll(getApplicationContext());
    }

    public void openContact(View view) {
        Intent intent = new Intent (this, Contact_organizer.class);
        startActivity(intent);
    }

    public void openShows(View view){
        Intent intent = new Intent (this, Shows.class);
        startActivity(intent);
    }

    public void openAddShow(View view){
        Intent intent = new Intent (this, AddShow.class);
        startActivity(intent);
    }

    public void openShowList(View view){
        Intent intent = new Intent (this, clientShows.class);
        startActivity(intent);
    }
}
