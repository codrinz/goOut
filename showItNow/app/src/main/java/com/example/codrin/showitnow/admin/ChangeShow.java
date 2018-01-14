package com.example.codrin.showitnow.admin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.example.codrin.showitnow.utils.storage.DatabaseUtils;
import com.example.codrin.showitnow.R;
import com.example.codrin.showitnow.utils.entities.Show;

import java.util.Calendar;

public class ChangeShow extends AppCompatActivity {
    String showName;
    Show show;
    DatePicker mDatePicker;
    EditText newName;
    Button changeButton, deleteButton;
    Boolean changeDate = false, changeName = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_show);
        showName = getIntent().getStringExtra("changedShow");
        show = Shows.getShowByName(showName);
        newName = (EditText) findViewById(R.id.newShowname);
        mDatePicker = (DatePicker) findViewById(R.id.newDate);
        changeButton = (Button) findViewById(R.id.change);
        deleteButton = (Button) findViewById(R.id.delete);

    /*}

    public void changeDate(View view){
    */
        //EditText dayView = findViewById(R.id.newDateInput);
       // Shows.getShowByName(showName).changeShowDate(Integer.parseInt(dayView.getText().toString())%7);
        final DatabaseUtils db = new DatabaseUtils();
        //set calendar to current date
        Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);
       // mDatePicker.updateDate(mYear, mMonth,mDay);
        newName.setText(show.getShowName());//set initial name

        newName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newName.setText("");
                changeName = true;
                System.out.println("[CHANGE]Name changed");
                changeButton.setVisibility(View.VISIBLE);
            }
        });

        mDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeDate = true;
                System.out.println("[CHANGE]Date changed");
                changeButton.setVisibility(View.VISIBLE);
            }
        });

        changeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Show changedShow = new Show(show);
                if(changeName == true) changedShow.setShowName(newName.getText().toString());
                if(changeDate == true){
                    changedShow.setDay(mDatePicker.getDayOfMonth());
                    changedShow.setMonth(mDatePicker.getMonth()+1);
                }
                db.update(getApplicationContext(),show,changedShow);
                ((BaseAdapter)Shows.listViewStatic.getAdapter()).notifyDataSetChanged();
                finish();
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.delete(getApplicationContext(),show);
                finish();
            }
        });

    }

    public void deleteShow(View view){
        Shows.deleteShowByName(showName);
        ((BaseAdapter)Shows.listViewStatic.getAdapter()).notifyDataSetChanged();
    }

    @Override
    public void onBackPressed(){
    finish();
    }
}
