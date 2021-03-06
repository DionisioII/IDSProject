package server;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import java.io.InputStream;

/**
 * Created by domenico on 04/06/16.
 */
public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {

    public ImageResponse delegate = null;//Call back interface

    public DownloadImageTask(ImageResponse imageResponse)
    {
        delegate = imageResponse;//Assigning call back interfacethrough constructor
    }

    /*
    ImageView bmImage;

    public DownloadImageTask(ImageView bmImage) {
        this.bmImage = bmImage;
    }
    */

    protected Bitmap doInBackground(String... urls)
    {
        String urldisplay = urls[0];
        Bitmap mIcon11 = null;
        try {
            InputStream in = new java.net.URL(urldisplay).openStream();
            mIcon11 = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
            return null;
        }
        return mIcon11;
    }

    protected void onPostExecute(Bitmap result)
    {
        delegate.saveImage(result);
        //bmImage.setImageBitmap(result);
    }
}