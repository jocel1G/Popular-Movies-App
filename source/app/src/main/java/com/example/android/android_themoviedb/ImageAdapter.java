package com.example.android.android_themoviedb;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

// 24/08/2016
import Packages_Classes.DetailMovieClass;


/**
 * Created by Jocelyn on 10/07/2016.
 */

public class ImageAdapter extends BaseAdapter {
    public ImageAdapter() {
        super();
    }

    private Context mContext;

    public ImageAdapter(Context c) {
        mContext = c;
    }
/*
    // 23/08/2016
    public ImageAdapter(Context c, String[] mIma)
    {
        mContext = c;
        mImages = mIma;
    }
*/
    /**
     * How many items are in the data set represented by this Adapter.
     *
     * @return Count of items.
     */
    @Override
    public int getCount() {
        if (mImages != null)
            return mImages.length;
        else
            return  0;
    }

    /**
     * Get the data item associated with the specified position in the data set.
     *
     * @param position Position of the item whose data we want within the adapter's
     *                 data set.
     * @return The data at the specified position.
     */
    @Override
    public Object getItem(int position) {
        //return null;
        return mImages[position];
    }

    /**
     * Get the row id associated with the specified position in the list.
     *
     * @param position The position of the item within the adapter's data set whose row id we want.
     * @return The id of the item at the specified position.
     */
    @Override
    public long getItemId(int position) {
        return 0;
    }

    /**
     * Get a View that displays the data at the specified position in the data set. You can either
     * create a View manually or inflate it from an XML layout file. When the View is inflated, the
     * parent View (GridView, ListView...) will apply default layout parameters unless you use
     * {@link LayoutInflater#inflate(int, ViewGroup, boolean)}
     * to specify a root view and to prevent attachment to the root.
     *
     * @param position    The position of the item within the adapter's data set of the item whose view
     *                    we want.
     * @param convertView The old view to reuse, if possible. Note: You should check that this view
     *                    is non-null and of an appropriate type before using. If it is not possible to convert
     *                    this view to display the correct data, this method can create a new view.
     *                    Heterogeneous lists can specify their number of view types, so that this View is
     *                    always of the right type (see {@link #getViewTypeCount()} and
     *                    {@link #getItemViewType(int)}).
     * @param parent      The parent that this view will eventually be attached to
     * @return A View corresponding to the data at the specified position.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            //imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
            //imageView.setLayoutParams(new GridView.LayoutParams(200, 200));
            imageView.setLayoutParams(new GridView.LayoutParams(200, 250));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = (ImageView) convertView;
        }

        // 24/08/2016 : memorization of the ID
        imageView.setTag(mImages[position].getId());

         final String strLoad = "http://image.tmdb.org/t/p/w185/";
        Picasso.with(mContext).load(strLoad + mImages[position].getMoviePoster()).into(imageView);
        return imageView;

    }
/*
    // references to our images
    private Integer[] mThumbIds = {
            R.drawable.cupcake, R.drawable.donut,
            R.drawable.eclair
    };
*/
    // references to our images
    ////private Integer[] mThumbIds;
    //private String[] mImages;
    private DetailMovieClass[] mImages;

    public void replaceImages(DetailMovieClass[] pImages)
    {
        mImages = pImages;
        notifyDataSetChanged();
    }

}
