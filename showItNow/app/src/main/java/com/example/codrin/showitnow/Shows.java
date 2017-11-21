package com.example.codrin.showitnow;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class Shows extends AppCompatActivity {
    public static ArrayList<Show> showsArray = new ArrayList<>();
    public static ListView listViewStatic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shows2);
        Shows.listViewStatic = findViewById(R.id.showsList);
        showsArray.add(new Show("show1",7));

        ArrayAdapter<Show> showsAdapter = new ArrayAdapter<Show>(this,
                android.R.layout.simple_list_item_1, showsArray);
        ListView listView = (ListView) findViewById(R.id.showsList);
        listView.setAdapter(showsAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View arg1, int position, long arg3)
            {
               // Toast.makeText(SuggestionActivity.this, "" + position, Toast.LENGTH_SHORT).show();
                Show entry = (Show) parent.getItemAtPosition(position);
                openChangeShow(entry.getShowName());
            }
        });
    }

    public static Show getShowByName(String name){
        for (Show show:showsArray)if(show.getShowName().compareTo(name)==0)return show;
        return null;
    }

    public void openChangeShow(String showName){
        Intent intent = new Intent (Shows.this, ChangeShow.class);
        intent.putExtra("changedShow", showName);
        startActivity(intent);

    }

}
