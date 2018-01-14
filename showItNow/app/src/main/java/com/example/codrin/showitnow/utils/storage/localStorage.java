package com.example.codrin.showitnow.utils.storage;

import com.example.codrin.showitnow.utils.entities.Show;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Codrin on 14/01/2018.
 */

public class localStorage {
    public static List<Show> showsArray = new ArrayList<>();

    public static Show getShowByName(String name){
        for (Show show:showsArray)if(show.getShowName().compareTo(name)==0)return show;
        return null;
    }

    public localStorage() {
    }
}
