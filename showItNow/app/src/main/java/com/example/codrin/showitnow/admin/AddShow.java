package com.example.codrin.showitnow.admin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.example.codrin.showitnow.utils.storage.DatabaseUtils;
import com.example.codrin.showitnow.R;
import com.example.codrin.showitnow.utils.entities.Show;

public class AddShow extends AppCompatActivity {
boolean dateCompleted = false, nameCompleted = false, spotsCompleted = false;
int occupiedSpotsI, allSpotsI;

    private void check(){
       Button addButton = (Button) findViewById(R.id.addShowButton);
       if(dateCompleted&&nameCompleted&&spotsCompleted) ;
       addButton.setVisibility(View.VISIBLE);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_show);

        EditText showname = (EditText) findViewById(R.id.showNameAdd);
        EditText allSpots = (EditText) findViewById(R.id.allSpotsAdd);
        NumberPicker occupiedSpots = (NumberPicker) findViewById(R.id.occupiedSpotsAdd);
        DatePicker mDatePicker = (DatePicker) findViewById(R.id.datePickerAdd);
        TextView caption = (TextView) findViewById(R.id.captionReservedAdd);
        Button addButton = (Button) findViewById(R.id.addShowButton);
        mDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateCompleted = true;
                System.out.println("[CHANGE]Date changed");
                check();
            }
        });
        allSpots.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            NumberPicker occupiedSpots = (NumberPicker) findViewById(R.id.occupiedSpotsAdd);
            EditText allSpots = (EditText) findViewById(R.id.allSpotsAdd);
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) allSpots.setText("");
                if (!hasFocus) {
                    if(Integer.parseInt(allSpots.getText().toString())>0)
                        occupiedSpots.setVisibility(View.VISIBLE);
                        occupiedSpots.setMaxValue(Integer.parseInt(allSpots.getText().toString()));
                        spotsCompleted=true;
                }
                check();
            }
        });
        showname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText showname = (EditText) findViewById(R.id.showNameAdd);
                showname.setText("");
                nameCompleted = true;
                System.out.println("[CHANGE]Name changed");
                check();
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText showname = (EditText) findViewById(R.id.showNameAdd);
                EditText allSpots = (EditText) findViewById(R.id.allSpotsAdd);
                NumberPicker occupiedSpots = (NumberPicker) findViewById(R.id.occupiedSpotsAdd);
                DatePicker mDatePicker = (DatePicker) findViewById(R.id.datePickerAdd);
                TextView caption = (TextView) findViewById(R.id.captionReservedAdd);

                occupiedSpotsI = occupiedSpots.getValue();
                allSpotsI = Integer.parseInt(allSpots.getText().toString());

                Show show = new Show(showname.getText().toString(),mDatePicker.getDayOfMonth(),mDatePicker.getMonth()+1,allSpotsI-occupiedSpotsI,allSpotsI);
                DatabaseUtils db = new DatabaseUtils();
                db.insert(getApplicationContext(),show);
                finish();
            }
        });
    }
    @Override
    public void onBackPressed(){
        finish();
    }
}
