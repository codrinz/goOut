package com.example.codrin.showitnow;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.example.codrin.showitnow.utils.localStorage;

import java.util.ArrayList;
import java.util.List;

public class Shows extends AppCompatActivity {
    public static List<Show> showsArray = localStorage.showsArray;
    public static ListView listViewStatic;
    private DatabaseUtils db = new DatabaseUtils();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shows2);
        Shows.listViewStatic = findViewById(R.id.showsList);
        db.getAll(getApplicationContext());

        if(Shows.showsArray.isEmpty()){
            db.insert(getApplicationContext(),new Show("test1",2,7,50,100));
        }
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
                finish();
            }
        });
        while(showsArray.isEmpty()){};
        ((BaseAdapter)Shows.listViewStatic.getAdapter()).notifyDataSetChanged();
    }

    public static Show getShowByName(String name){
        for (Show show:showsArray)if(show.getShowName().compareTo(name)==0)return show;
        return null;
    }

    public static boolean deleteShowByName(String name){
        for (Show show:showsArray)if(show.getShowName().compareTo(name)==0)return showsArray.remove(show);
        return false;
    }

    public void openChangeShow(String showName){
        Intent intent = new Intent (Shows.this, ChangeShow.class);
        intent.putExtra("changedShow", showName);
        startActivity(intent);

    }
    @Override
    public void onBackPressed(){
        finish();
    }
}
