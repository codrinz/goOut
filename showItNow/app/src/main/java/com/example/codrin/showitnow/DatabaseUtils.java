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
    if(Shows.getShowByName(show.getShowName())!=null) {
        System.out.println("[ERROR] tried to add a show already existing");
        return;
    }
    Shows.showsArray.add(show);
    new AsyncTask<Void,Void,Void>(){
        @Override
        protected Void doInBackground(Void...voids) {
            if(AppDatabase.getAppDatabase(context).showDAO().getShow(show.getShowName())==null);
            AppDatabase.getAppDatabase(context).showDAO().insert(show);
            getAll(context);
            return null;
        }
    }.execute();
}
public void delete(final Context context, final Show show){
    Shows.showsArray.remove(show);
    new AsyncTask<Void,Void,Void>(){
        @Override
        protected Void doInBackground(Void...voids) {
            AppDatabase.getAppDatabase(context).showDAO().delete(show);
            getAll(context);
            return null;
        }
    }.execute();
}
public void update(final Context context,final Show oldShow, final Show newShow){
    Shows.showsArray.remove(oldShow);
    Shows.showsArray.add(newShow);
    new AsyncTask<Void,Void,Void>(){
        @Override
        protected Void doInBackground(Void...voids) {
            //AppDatabase.getAppDatabase(context).showDAO().update(newShow);
            AppDatabase.getAppDatabase(context).showDAO().delete(oldShow);
            AppDatabase.getAppDatabase(context).showDAO().insert(newShow);
            getAll(context);
            return null;
        }
    }.execute();
}
}