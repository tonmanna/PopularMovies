package api;

import java.io.Serializable;
import java.util.Date;

public class TheMoviesModelResultList implements Serializable {
    public boolean adult;
    public String backdrop_path;
    public long id;
    public String original_language;
    public String original_title;
    public String overview;
    public Date release_date;
    public String poster_path;
    public String poster_path_hires;
    public double popularity;
    public String title;
    public boolean video;
    public double vote_average;
    public int vote_count;
}
