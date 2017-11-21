package com.example.codrin.showitnow;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class Contact_organizer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_organizer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSendClick(view);
            }
        });
    }


    void onSendClick(View v) {
        TextView nameView = findViewById(R.id.editText);
        TextView addressView = findViewById(R.id.editText2);
        TextView messageView = findViewById(R.id.editText3);

        String name = nameView.getText().toString();
        String address = addressView.getText().toString();
        String message = messageView.getText().toString();

        Intent intent = new Intent(Intent.ACTION_SEND);


        intent.setType("message/rfc822");
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"codrin.st26@gmail.com"});
        intent.putExtra(Intent.EXTRA_SUBJECT, "Mail from "+name+" address: "+address);
        intent.putExtra(Intent.EXTRA_TEXT, message);

        startActivity(Intent.createChooser(intent, "Send Email"));
    }
}
