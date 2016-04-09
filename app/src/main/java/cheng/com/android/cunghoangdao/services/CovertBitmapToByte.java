package cheng.com.android.cunghoangdao.services;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Welcome on 4/8/2016.
 */
public class CovertBitmapToByte extends AsyncTask<String, String, Bitmap> {
    public OnReturnBimapFromByte onReturnBimapFromByte;
    private Context context;
    public CovertBitmapToByte(Context context,OnReturnBimapFromByte onReturnBimapFromByte){
        this.context=context;
        this.onReturnBimapFromByte=onReturnBimapFromByte;
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        Bitmap bitmap = null;
        try {
            // Download the image
            URL url = new URL(params[0]);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream is = connection.getInputStream();
            // Decode image to get smaller image to save memory
            final BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = false;
            options.inSampleSize = 4;
            bitmap = BitmapFactory.decodeStream(is, null, options);
            is.close();
        } catch (IOException e) {

        }
        return bitmap;
    }


    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        onReturnBimapFromByte.onReturnBimapFromByte(getBytes(bitmap));
    }

    public static byte[] getBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }
    public interface OnReturnBimapFromByte{
        void onReturnBimapFromByte(byte[] mage);
    }
}


