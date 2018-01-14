package com.example.codrin.showitnow;

import android.content.Intent;
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

public class clientShows extends AppCompatActivity {
    public static List<Show> showsArray = localStorage.showsArray;
    public static ListView listViewStatic;
    private DatabaseUtils db = new DatabaseUtils();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_shows);
        clientShows.listViewStatic =(ListView) findViewById(R.id.showsListClient);
        db.getAllClient(getApplicationContext());
        ArrayAdapter<Show> showsAdapter = new ArrayAdapter<Show>(this,
                android.R.layout.simple_list_item_1, clientShows.showsArray);
        ListView listView = (ListView) findViewById(R.id.showsListClient);
        listView.setAdapter(showsAdapter);
        listViewStatic.setAdapter(showsAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View arg1, int position, long arg3)
            {
                // Toast.makeText(SuggestionActivity.this, "" + position, Toast.LENGTH_SHORT).show();
                Show entry = (Show) parent.getItemAtPosition(position);
                openShowDetails(entry.getShowName());
            }
        });
        while(clientShows.showsArray.isEmpty()){
            try {
                wait(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("[WARNING]Can't find data to display for the client");
            clientShows.showsArray=localStorage.showsArray;
            ((BaseAdapter)clientShows.listViewStatic.getAdapter()).notifyDataSetChanged();
        }
        ((BaseAdapter)clientShows.listViewStatic.getAdapter()).notifyDataSetChanged();
    }
    protected void openShowDetails(String showName){
        Intent intent = new Intent (clientShows.this, showsDetails.class);
        intent.putExtra("selectedShow", showName);
        startActivity(intent);
    }
    @Override
    public void onBackPressed(){
        finish();
    }
}
