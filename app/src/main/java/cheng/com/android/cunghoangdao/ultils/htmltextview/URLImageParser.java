package cheng.com.android.cunghoangdao.ultils.htmltextview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.text.Html.ImageGetter;
import android.util.Base64;
import android.util.Log;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;

import cheng.com.android.cunghoangdao.ultils.ConnectionUltils;

public class URLImageParser implements ImageGetter {
    Context context;
    TextView container;

    public URLImageParser(TextView container, Context context) {
        this.context = context;
        this.container = container;
    }

    public Drawable getDrawable(String source) {
        if (source.matches("data:image.*base64.*")) {
            String base_64_source = source.replaceAll("data:image.*base64", "");
            byte[] data = Base64.decode(base_64_source, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
            Drawable image = new BitmapDrawable(context.getResources(), bitmap);
            image.setBounds(0, 0, 0 + image.getIntrinsicWidth(), 0 + image.getIntrinsicHeight());
            Log.d("URLImageParser", "image: " + image);
            return image;
        } else {
            URLDrawable urlDrawable = new URLDrawable();
            ImageGetterAsyncTask asyncTask = new ImageGetterAsyncTask(urlDrawable);
            asyncTask.execute(source);
            Log.d("URLImageParser", "urlDrawable: " + urlDrawable);
            return urlDrawable; //return reference to URLDrawable where We will change with actual image from the src tag
        }
    }

    public class ImageGetterAsyncTask extends AsyncTask<String, Void, Drawable> {
        URLDrawable urlDrawable;

        public ImageGetterAsyncTask(URLDrawable d) {
            this.urlDrawable = d;
        }

        @Override
        protected Drawable doInBackground(String... params) {
            String source = params[0];
            return fetchDrawable(source);
        }

        @Override
        protected void onPostExecute(Drawable result) {
            int width = 0, height = 0;
            if (result == null) {
                urlDrawable.drawable = null;
            } else {
                width = (int) (result.getIntrinsicWidth());
                height = (int) (result.getIntrinsicHeight());
                float scale = context.getResources().getDisplayMetrics().density;
                if (width * scale < container.getWidth()) {
                    width = (int) (result.getIntrinsicWidth() * scale);
                    height = (int) (result.getIntrinsicHeight() * scale);
                }

                urlDrawable.setBounds(0, 0, 0 + width, 0 + height);
                urlDrawable.drawable = result;
            }
            // Change to downloaded image


            // Invalidate TextView to redraw image
            URLImageParser.this.container.invalidate();


            // Resize TextView height to accommodate for image
            // 4.0+ devices
            URLImageParser.this.container
                    .setHeight((URLImageParser.this.container.getHeight() + height));
            URLImageParser.this.container.setWidth((URLImageParser.this.container.getHeight() + width));
            // Needed for devices before 4.0
            URLImageParser.this.container.setEllipsize(null);

        }

        public Drawable fetchDrawable(String urlString) {
            try {
                if (!ConnectionUltils.isConnected(context)) {
                    return null;
                }
                InputStream is = fetch(urlString);
                if (is == null) {
                    return null;
                }
                Drawable drawable = Drawable.createFromStream(is, "src");
                // Scales image if space is available in container
                int width = (int) (drawable.getIntrinsicWidth());
                int height = (int) (drawable.getIntrinsicHeight());
                float scale = context.getResources().getDisplayMetrics().density;
                if (width * scale < container.getWidth()) {
                    width = (int) (drawable.getIntrinsicWidth() * scale);
                    height = (int) (drawable.getIntrinsicHeight() * scale);
                }

                drawable.setBounds(0, 0, width, height);
                Log.d("URLImageParser", "fetchDrawable: " + drawable);
                return drawable;
            } catch (Exception e) {
                return null;
            }
        }

        private InputStream fetch(String urlString) throws MalformedURLException, IOException {
            URL url = null;
            InputStream stream = null;
            try {
                url = new URL(urlString);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setReadTimeout(30000); //18s
                conn.setConnectTimeout(30000); //18s
                conn.setUseCaches(false);
                conn.setDoInput(true);
                conn.connect();
                stream = conn.getInputStream();
            } catch (SocketTimeoutException e) {
                e.printStackTrace();
                return null;
            } catch (MalformedURLException e) {
                e.printStackTrace();
                return null;
            }
            return stream;
        }
    }
}