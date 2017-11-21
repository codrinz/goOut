package com.example.codrin.showitnow;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

public class ChangeShow extends AppCompatActivity {
    String showName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_show);
        showName = getIntent().getStringExtra("changedShow");

    }

    public void changeDate(View view){
        EditText dayView = findViewById(R.id.newDateInput);
        Shows.getShowByName(showName).changeShowDate(Integer.parseInt(dayView.getText().toString())%7);
        ((BaseAdapter)Shows.listViewStatic.getAdapter()).notifyDataSetChanged();
    }
}
