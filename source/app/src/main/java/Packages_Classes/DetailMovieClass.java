package Packages_Classes;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by jocelyn.giraud on 25/08/2016.
 */
public class DetailMovieClass implements Parcelable {

    String m_original_title;
    String m_movie_poster;
    String m_overview;
    Float m_vote_average;
    String m_release_date;
    int m_Id;

    public String getoriginal_title()
    {
        return m_original_title;
    }

    public String getMoviePoster()
    {
        return m_movie_poster;
    }

    public int getId()
    {
        return m_Id;
    }

    public String getReleaseDate()
    {
        return  m_release_date;
    }

    public String getOverview()
    {
        return m_overview;
    }

    public Float getVoteAverage()
    {
        return m_vote_average;
    }

    public DetailMovieClass(String original_title, String movie_poster, String overview,
                            Float vote_average, String release_date, int Id)
    {
        this.m_original_title = original_title;
        this.m_movie_poster = movie_poster;
        this.m_overview = overview;
        this.m_vote_average = vote_average;
        this.m_release_date = release_date;
        this.m_Id = Id;
    }

    protected DetailMovieClass(Parcel in) {
        m_original_title = in.readString();
        m_movie_poster = in.readString();
        m_overview = in.readString();
        m_vote_average = in.readFloat();
        m_release_date = in.readString();
        m_Id = in.readInt();
    }

    public static final Creator<DetailMovieClass> CREATOR = new Creator<DetailMovieClass>() {
        @Override
        public DetailMovieClass createFromParcel(Parcel in) {
            return new DetailMovieClass(in);
        }

        @Override
        public DetailMovieClass[] newArray(int size) {
            return new DetailMovieClass[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(m_original_title);
        dest.writeString(m_movie_poster);
        dest.writeString(m_overview);
        dest.writeFloat(m_vote_average);
        dest.writeString(m_release_date);
        dest.writeInt(m_Id);
    }
}
