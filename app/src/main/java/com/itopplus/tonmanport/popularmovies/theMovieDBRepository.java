package com.itopplus.tonmanport.popularmovies;

import java.util.ArrayList;

import lib.Utility;

/**
 * Created by Tonman on 26/8/2558.
 */
public class theMovieDBRepository {
        private final String BASE_URL = "";

        //get Popular movies;
        public ArrayList<theMovieDBItems> getMovie(theMovieDB apiParams){
            /// TODO : I must connect to API (TOKEN is require) 26/08/2015 5:00am
            String result = Utility.HTTPGet(BASE_URL, apiParams);
            if(result!=null){
                return null;
            }else {
                return null;
            }
        }
}
