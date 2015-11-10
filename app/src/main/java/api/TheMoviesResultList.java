package api;

import android.content.ContentResolver;
import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.BaseColumns;

import java.io.Serializable;
import java.util.Date;

import lib.MoviesContract;

public class TheMoviesResultList implements Serializable, Parcelable {
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


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte(adult ? (byte) 1 : (byte) 0);
        dest.writeString(this.backdrop_path);
        dest.writeLong(this.id);
        dest.writeString(this.original_language);
        dest.writeString(this.original_title);
        dest.writeString(this.overview);
        dest.writeLong(release_date != null ? release_date.getTime() : -1);
        dest.writeString(this.poster_path);
        dest.writeString(this.poster_path_hires);
        dest.writeDouble(this.popularity);
        dest.writeString(this.title);
        dest.writeByte(video ? (byte) 1 : (byte) 0);
        dest.writeDouble(this.vote_average);
        dest.writeInt(this.vote_count);
    }

    public TheMoviesResultList() {
    }

    private TheMoviesResultList(Parcel in) {
        this.adult = in.readByte() != 0;
        this.backdrop_path = in.readString();
        this.id = in.readLong();
        this.original_language = in.readString();
        this.original_title = in.readString();
        this.overview = in.readString();
        long tmpRelease_date = in.readLong();
        this.release_date = tmpRelease_date == -1 ? null : new Date(tmpRelease_date);
        this.poster_path = in.readString();
        this.poster_path_hires = in.readString();
        this.popularity = in.readDouble();
        this.title = in.readString();
        this.video = in.readByte() != 0;
        this.vote_average = in.readDouble();
        this.vote_count = in.readInt();
    }

    public static final Parcelable.Creator<TheMoviesResultList> CREATOR = new Parcelable.Creator<TheMoviesResultList>() {
        public TheMoviesResultList createFromParcel(Parcel source) {
            return new TheMoviesResultList(source);
        }

        public TheMoviesResultList[] newArray(int size) {
            return new TheMoviesResultList[size];
        }
    };
}
