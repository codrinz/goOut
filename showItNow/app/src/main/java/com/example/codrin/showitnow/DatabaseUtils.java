package com.example.codrin.showitnow;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.BaseAdapter;

/**
 * Created by Codrin on 13/01/2018.
 */

public class DatabaseUtils {

public void getAll(final Context context){
    new AsyncTask<Void,Void,Void>(){
        @Override
        protected Void doInBackground(Void...voids) {
            Shows.showsArray = AppDatabase.getAppDatabase(context).showDAO().getAll();
            return null;
        }
    }.execute();
}
public void insert(final Context context, final Show show){
    new AsyncTask<Void,Void,Void>(){
        @Override
        protected Void doInBackground(Void...voids) {
            AppDatabase.getAppDatabase(context).showDAO().insert(show);
            getAll(context);
            return null;
        }
    }.execute();
}
public void delete(final Context context, final Show show){
    new AsyncTask<Void,Void,Void>(){
        @Override
        protected Void doInBackground(Void...voids) {
            AppDatabase.getAppDatabase(context).showDAO().delete(show);
            getAll(context);
            return null;
        }
    }.execute();
}
public void update(final Context context, final Show show){
    new AsyncTask<Void,Void,Void>(){
        @Override
        protected Void doInBackground(Void...voids) {
            AppDatabase.getAppDatabase(context).showDAO().update(show);
            getAll(context);
            return null;
        }
    }.execute();
}
}
