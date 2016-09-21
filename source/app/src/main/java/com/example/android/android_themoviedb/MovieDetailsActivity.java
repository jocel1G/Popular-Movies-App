package com.example.android.android_themoviedb;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import Packages_Classes.DetailMovieClass;

public class MovieDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        if (savedInstanceState ==  null)
        {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new MovieDetailsActivityFragment())
                    .commit();
        }

        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);

        try {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        catch (Exception e)
        {
            AlertDialog.Builder messageBox = new AlertDialog.Builder(this);
            messageBox.setTitle("method");
            messageBox.setMessage(e.getMessage());
            messageBox.setCancelable(false);
            messageBox.setNeutralButton("OK", null);
            messageBox.show();
        }
    }

    // 26/08/2016


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public static class MovieDetailsActivityFragment extends Fragment
    {
        private static final String LOG_TAG = MovieDetailsActivityFragment.class.getSimpleName();

        public MovieDetailsActivityFragment()
        {
            setHasOptionsMenu(true);
        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            //return super.onCreateView(inflater, container, savedInstanceState);

            View rootView = inflater.inflate(R.layout.fragment_movie_details, container, false);

            // 21/05/2016
            /*
            Intent intent = getActivity().getIntent();
            int IdMovie = intent.getIntExtra("MovieID", 0);
            Toast.makeText(getContext(), Integer.toString(IdMovie), Toast.LENGTH_SHORT).show();
            */
            Intent intent = getActivity().getIntent();
            DetailMovieClass detMovieClass = intent.getExtras().getParcelable("DetailMovieClassp");
            /*
            mForecastStr = intent.getStringExtra(Intent.EXTRA_TEXT);
            TextView txtView = (TextView)rootView.findViewById(R.id.detail_text);
            txtView.setText(mForecastStr);
            */
            TextView txtView = (TextView)rootView.findViewById(R.id.original_title);
            txtView.setText(detMovieClass.getoriginal_title());

            ImageView imageView = (ImageView)rootView.findViewById(R.id.movie_poster);

            final String strLoad = "http://image.tmdb.org/t/p/w185/";
            Picasso.with(getActivity()).load(strLoad + detMovieClass.getMoviePoster()).into(imageView);

            // Release date
            TextView txtViewRD = (TextView)rootView.findViewById(R.id.release_date);
            String strDateFromClass = detMovieClass.getReleaseDate();
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

            // Vote average
            TextView txtVoteAverage = (TextView)rootView.findViewById((R.id.vote_average));
            Float fltVoteAverage = detMovieClass.getVoteAverage();
            txtVoteAverage.setText(fltVoteAverage.toString());

            try {
                Date dtmDate = dateFormat.parse(strDateFromClass);
                DateFormat dateF2 = new SimpleDateFormat("MMMM yyyy");
                String strDate2 = dateF2.format(dtmDate);
                // 01/09/2016 : Première lettre du mois en majuscules
                strDate2 = strDate2.substring(0,1).toUpperCase() + strDate2.substring(1);

                txtViewRD.setText(strDate2);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            // Overview
            TextView txtViewOverview = (TextView)rootView.findViewById(R.id.overview);
            txtViewOverview.setText(detMovieClass.getOverview());

            return  rootView;
        }
    }
}
