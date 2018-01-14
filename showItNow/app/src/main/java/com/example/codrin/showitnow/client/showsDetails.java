package com.example.codrin.showitnow.client;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.TextView;

import com.example.codrin.showitnow.R;
import com.example.codrin.showitnow.utils.entities.Show;
import com.example.codrin.showitnow.utils.storage.localStorage;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class showsDetails extends AppCompatActivity {
    String showName;
    Show show;
    TextView showNameView;
    TextView availableSpots;
    CalendarView mCalendarView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shows_details);
        showName = getIntent().getStringExtra("selectedShow");
        show = localStorage.getShowByName(showName);
        showNameView = (TextView) findViewById(R.id.showName);
        availableSpots = (TextView) findViewById(R.id.showFreeSeats);
        mCalendarView = (CalendarView) findViewById(R.id.calendarView);
        showNameView.setText(showName);
        availableSpots.setText("Spots available: "+show.getFreeSpots());
        String selectedDate = show.getDay()+"/"+show.getMonth()+"/"+"2018";
        try {
            mCalendarView.setDate(new SimpleDateFormat("dd/MM/yyyy").parse(selectedDate).getTime(), true, true);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        showNameView.setVisibility(View.VISIBLE);
        availableSpots.setVisibility(View.VISIBLE);

        //*************************************PIECHART
        PieChart pieChart = findViewById(R.id.user_piechart);

        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(5, 10, 5, 5);

        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(Color.WHITE);

        pieChart.setTransparentCircleColor(Color.WHITE);
        pieChart.setTransparentCircleAlpha(110);

        pieChart.setHoleRadius(45f);
        pieChart.setTransparentCircleRadius(61f);

        pieChart.setDrawCenterText(true);

        pieChart.setRotationAngle(0);
        pieChart.setRotationEnabled(false);
        pieChart.setHighlightPerTapEnabled(true);

        // add values into pie chart data set
        ArrayList<PieEntry> yvalues = new ArrayList<>();
        Integer v1 = show.getFreeSpots();
        Integer v2 = show.getAllSpots();

        yvalues.add(new PieEntry((float)(v1)/(float)(v2), 0));
        yvalues.add(new PieEntry((float)(v2-v1)/(float)(v2), 1));

        PieDataSet dataSet = new PieDataSet(yvalues, "");

        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(ColorTemplate.rgb("009900")); // green for available spots
        colors.add(ColorTemplate.rgb("990000")); // red for all the occupied spots
        dataSet.setColors(colors);

        // make pie data
        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());

        // piechart legend
        Legend l = pieChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);

        // entry label styling
        pieChart.setEntryLabelColor(Color.WHITE);
        pieChart.setEntryLabelTextSize(12f);

        pieChart.setData(data);
        //*************************************PIECHART
    }
    @Override
    public void onBackPressed(){
        finish();
    }
}
