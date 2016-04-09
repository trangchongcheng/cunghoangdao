package cheng.com.android.cunghoangdao.ultils.htmltextview;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.text.Html.ImageGetter;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;

public class URLImageParse implements ImageGetter {
    private Context context;
    private TextView container;

    @SuppressWarnings("deprecation")
    // Needed to replace drawable with downloaded one
    private class URLDrawable extends BitmapDrawable {
        protected Drawable drawable;

        @Override
        public void draw(Canvas canvas) {
            // override the draw to refresh function later
            if (drawable != null) {
                drawable.draw(canvas);
            }
        }
    }

    public URLImageParse(TextView t, Context c) {
        context = c;
        container = t;
    }

    public Drawable getDrawable(String source) {
        URLDrawable urlDrawable = new URLDrawable();

        ImageGetterAsyncTask asyncTask = new ImageGetterAsyncTask(urlDrawable);

        asyncTask.execute(source);
        return urlDrawable;
    }

    public class ImageGetterAsyncTask extends AsyncTask<String, Void, Drawable> {
        URLDrawable urlDrawable;

        public ImageGetterAsyncTask(URLDrawable d) {
            urlDrawable = d;
        }

        @Override
        protected Drawable doInBackground(String... params) {
            String source = params[0];
            return fetchDrawable(source);
        }

        @Override
        protected void onPostExecute(Drawable result) {
            // Scales image/container if space is available in container
            int width = (int) (result.getIntrinsicWidth());
            int height = (int) (result.getIntrinsicHeight());
            float scale = context.getResources().getDisplayMetrics().density;
            if (width * scale < container.getWidth()) {
                width = (int) (result.getIntrinsicWidth() * scale);
                height = (int) (result.getIntrinsicHeight() * scale);
            }

            urlDrawable.setBounds(0, 0, 0 + width, 0 + height);

            // Change to downloaded image
            urlDrawable.drawable = result;

            // Invalidate TextView to redraw image
            URLImageParse.this.container.invalidate();



            // Resize TextView height to accommodate for image
            // 4.0+ devices
            URLImageParse.this.container
                    .setHeight((URLImageParse.this.container.getHeight() + height));
            // Needed for devices before 4.0
            URLImageParse.this.container.setEllipsize(null);
        }

        public Drawable fetchDrawable(String urlString) {
            try {
                InputStream is = fetch(urlString);
                Drawable drawable = Drawable.createFromStream(is, "src");

                // Scales image if space is available in container
                int width = (int) (drawable.getIntrinsicWidth());
                int height = (int) (drawable.getIntrinsicHeight());
                float scale = context.getResources().getDisplayMetrics().density;
                if (width * scale < container.getWidth()) {
                    width = (int) (drawable.getIntrinsicWidth() * scale);
                    height = (int) (drawable.getIntrinsicHeight() * scale);
                }

                drawable.setBounds(0, 0, 0 + width, 0 + height);
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
                conn.setReadTimeout(18000); //18s
                conn.setConnectTimeout(18000); //18s
                conn.setUseCaches(false);
                conn.setDoInput(true);
                conn.connect();
                stream = conn.getInputStream();
            } catch (SocketTimeoutException e) {
                e.printStackTrace();
                return null;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            return stream;
        }
    }
}
