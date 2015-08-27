package com.itopplus.tonmanport.popularmovies;
import android.util.Log;

import java.lang.reflect.Field;
import java.util.Objects;

import lib.IHTTPRequestParam;
/**
 * Created by Tonman on 26/8/2558.
 */
public class theMovieDB implements IHTTPRequestParam {

    // Share resource (Parameter)
    public String TOKEN;


    @Override
    public String getParam() {
        for(Field field : this.getClass().getDeclaredFields()) {
            if(field.getType()== String.class){
                String value = (String)field.get(this);
                Log.d("TTTTTTTTT", value);
            }
        }
        return "?TOKEN="+TOKEN;
    }
}