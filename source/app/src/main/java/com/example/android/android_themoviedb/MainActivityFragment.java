package com.example.android.android_themoviedb;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

// 24/08/2016
import Packages_Classes.DetailMovieClass;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    ImageAdapter mTheMovieDBAdapter;

    public MainActivityFragment() {
    }
/*
    // references to our images
    private Integer[] mThumbIds = {
            R.drawable.cupcake, R.drawable.donut,
            R.drawable.eclair
    };
*/

    //private String[] mImages;
    //private PosterPathAndID[] mImages_PathAndId;
    private DetailMovieClass[] mDetailMovieClass;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Add this line in order for this fragment to handle menu events.
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.v("LOG_TAG", "début onCreateView: ");
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        GridView gridView = (GridView)rootView.findViewById(R.id.gridviewMain);

        mTheMovieDBAdapter = new ImageAdapter(this.getActivity());
        //gridView.setAdapter(new ImageAdapter(this.getActivity(), mImages));
        gridView.setAdapter(mTheMovieDBAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //int Id = ((PosterPathAndID)mTheMovieDBAdapter.getItem(position)).Id;
                // Oui, mais quelle est l'utilité avec Parcelable ?
                //////int Id = ((DetailMovieClass)mTheMovieDBAdapter.getItem(position)).getId();
                //Toast.makeText(getContext() , Integer.toString(Id), Toast.LENGTH_SHORT).show();

                /*
                Intent detailIntent = new Intent(view.getContext(), MovieDetailsActivity.class);
                detailIntent.putExtra("MovieID", Id);
                startActivity(detailIntent);
                */

                //DetailMovieClass detMovieClass = new DetailMovieClass("Toutou", "Tata", "Titi", 5.3f, "2001-06-05", Id);
                DetailMovieClass detMovieClass = (DetailMovieClass)mTheMovieDBAdapter.getItem(position);
                Intent detailIntent = new Intent(view.getContext(), MovieDetailsActivity.class);
                detailIntent.putExtra("DetailMovieClassp", detMovieClass);
                startActivity(detailIntent);
            }
        });

        return  rootView;
    }

    // 27/08/2016
    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null &&
                cm.getActiveNetworkInfo().isConnectedOrConnecting();
    }

    private void updateFilms() {

        FetchMoviesTask movieTask = new FetchMoviesTask();

        // 27/08/2016
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this.getContext());
        String strPop_TR = sharedPref.getString(getString(R.string.pref_pop_tr_key), "popular");
        //Toast.makeText(getContext(), strPop_TR, Toast.LENGTH_SHORT).show();
        movieTask.execute(strPop_TR);

    }


    // 23/08/2016 : onStart
    @Override
    public void onStart() {
        super.onStart();
        // Chargement des films à partir de themoviedb
        //mImages = new String[10];
        // 27/08/2016
        if (isOnline()) {
            updateFilms();
        }
        else
        {
            Toast.makeText(getContext(), "No network connection", Toast.LENGTH_SHORT).show();

        }
    }

    // 23/08/2016 : AsyncTask for getting the films with themoviedb
    private class FetchMoviesTask extends AsyncTask<String, Void, DetailMovieClass[]> {
        private final String LOG_TAG = FetchMoviesTask.class.getSimpleName();

        /**
         * Take the String representing the complete forecast in JSON Format and
         * pull out the data we need to construct the Strings needed for the wireframes.
         *
         * Fortunately parsing is easy:  constructor takes the JSON string and converts it
         * into an Object hierarchy for us.
         */
        /*
        private String[] getMovieDataFromJson(String movieJsonStr)
                throws JSONException {
        */
        private DetailMovieClass[] getMovieDataFromJson(String movieJsonStr)
                throws JSONException {

            // These are the names of the JSON objects that need to be extracted.
            final String OWM_RESULT = "results";


            JSONObject forecastJson = new JSONObject(movieJsonStr);
            JSONArray resultArray = forecastJson.getJSONArray(OWM_RESULT);


            //String[] resultStrs = new String[resultArray.length()];
            //PosterPathAndID[] resultStrs = new PosterPathAndID[resultArray.length()];
            DetailMovieClass[] resultStrs = new DetailMovieClass[resultArray.length()];
            for(int i = 0; i < resultArray.length(); i++) {
                // For now, using the format "Day, description, hi/low"
                String day;
                String description;
                String highAndLow;

                // Get the JSON object representing the movie
                JSONObject movieObject = resultArray.getJSONObject(i);
                //resultStrs[i] = new PosterPathAndID();
                String original_title = movieObject.optString("original_title");
                String movie_poster = movieObject.optString("poster_path");
                String overview = movieObject.optString("overview");
                Float vote_average = Float.parseFloat(movieObject.optString("vote_average"));
                String release_date = movieObject.optString("release_date");
                int Id = movieObject.optInt("id");
                resultStrs[i] = new DetailMovieClass(original_title, movie_poster, overview, vote_average, release_date, Id);
                //resultStrs[i] = movieObject.optString("poster_path");
                //resultStrs[i].PosterPath = movieObject.optString("poster_path");
                //resultStrs[i].Id = movieObject.optInt("id");

            }
            /*
            for (String s : resultStrs) {
                Log.v(LOG_TAG, "Forecast entry: " + s);
            }
            */
            for (DetailMovieClass s : resultStrs) {
                Log.v(LOG_TAG, "Forecast entry: " + s.getMoviePoster());
            }
            return resultStrs;

        }



        @Override
        protected DetailMovieClass[] doInBackground(String... params) {
            //return new String[0];

            // These two need to be declared outside the try/catch
            // so that they can be closed in the finally block.
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            // Will contain the raw JSON response as a string.
            String themovieDBJsonStr = null;

            int numMovies = 6;

            // 27/08/2016
            String strPop_TR = params[0];

            try {
                /*
                final String THEDBMOVIES_BASE_URL =
                        "http://api.themoviedb.org/3/movie/popular?";
                */
                final String THEDBMOVIES_BASE_URL =
                        "http://api.themoviedb.org/3/movie/" + strPop_TR + "?";
                final String api_key_PARAM = "api_key";

                Uri builtUri = Uri.parse(THEDBMOVIES_BASE_URL).buildUpon()
                        .appendQueryParameter(api_key_PARAM, BuildConfig.API_KEY)
                        .build();

                URL url = new URL(builtUri.toString());

                Log.v(LOG_TAG, "Built URI " + builtUri.toString());

                // Create the request to OpenWeatherMap, and open the connection
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();


                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));


                String line;
                while ((line = reader.readLine()) != null) {
                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                    // But it does make debugging a *lot* easier if you print out the completed
                    // buffer for debugging.
                    buffer.append(line + "\n");
                }


                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    return null;
                }
                themovieDBJsonStr = buffer.toString();

                Log.v(LOG_TAG, "The MovieDB string: " + themovieDBJsonStr);

            } catch (IOException e) {
                Log.e(LOG_TAG, "Error ", e);
                // If the code didn't successfully get the weather data, there's no point in attemping
                // to parse it.
                return null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e(LOG_TAG, "Error closing stream", e);
                    }
                }
            }

            try {
                return getMovieDataFromJson(themovieDBJsonStr);
            } catch (JSONException e) {
                Log.e(LOG_TAG, e.getMessage(), e);
                e.printStackTrace();
            }

            return null;

        }

        @Override
        protected void onPostExecute(DetailMovieClass[] result) {
            if(result != null)
            {
                /*
                mTheMovieDBAdapter.clear();
                for (String dayForecastStr : result)
                    mTheMovieDBAdapter.add(dayForecastStr);
                // New data is back from the server.  Hooray!
                */
                /*
                mImages = result;
                mTheMovieDBAdapter.replaceImages(mImages);
                */
                mDetailMovieClass = result;
                mTheMovieDBAdapter.replaceImages(mDetailMovieClass);
            }
        }
    }
}
