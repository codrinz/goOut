package com.example.codrin.showitnow.utils.storage;

import android.content.Context;
import android.os.AsyncTask;

import com.example.codrin.showitnow.utils.entities.Show;

/**
 * Created by Codrin on 13/01/2018.
 */

public class DatabaseUtils {

public void getAll(final Context context){
    new AsyncTask<Void,Void,Void>(){
        @Override
        protected Void doInBackground(Void...voids) {
            localStorage.showsArray = AppDatabase.getAppDatabase(context).showDAO().getAll();
            return null;
        }
    }.execute();
}
public void insert(final Context context, final Show show){
    if(localStorage.getShowByName(show.getShowName())!=null) {
        System.out.println("[ERROR] tried to add a show already existing");
        return;
    }
    localStorage.showsArray.add(show);
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
    localStorage.showsArray.remove(show);
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
    localStorage.showsArray.remove(oldShow);
    localStorage.showsArray.add(newShow);
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

    public void getAllClient(final Context applicationContext) {
        new AsyncTask<Void,Void,Void>(){
            @Override
            protected Void doInBackground(Void...voids) {
                localStorage.showsArray = AppDatabase.getAppDatabase(applicationContext).showDAO().getAll();
                return null;
            }
        }.execute();
    }
}
