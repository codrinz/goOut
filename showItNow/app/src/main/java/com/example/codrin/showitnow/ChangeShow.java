package com.example.codrin.showitnow;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

public class ChangeShow extends AppCompatActivity {
    String showName;
    Show show;
    DatePicker mDatePicker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_show);
        showName = getIntent().getStringExtra("changedShow");
        show = Shows.getShowByName(showName);
        mDatePicker = (DatePicker) findViewById(R.id.newDate);
        mDatePicker.updateDate(2017, 7, 20);
    }

    public void changeDate(View view){
        //EditText dayView = findViewById(R.id.newDateInput);
       // Shows.getShowByName(showName).changeShowDate(Integer.parseInt(dayView.getText().toString())%7);
        DatabaseUtils db = new DatabaseUtils();
        db.update(getApplicationContext(),show);
        ((BaseAdapter)Shows.listViewStatic.getAdapter()).notifyDataSetChanged();
    }

    public void deleteShow(View view){
        Shows.deleteShowByName(showName);
        ((BaseAdapter)Shows.listViewStatic.getAdapter()).notifyDataSetChanged();
    }
}
