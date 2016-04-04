package cheng.com.android.cunghoangdao.ultils.htmltextview;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.text.Html;
import android.util.Log;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;

/**
 * Created by Welcome on 3/28/2016.
 */
public class URLImageParser implements Html.ImageGetter {
    TextView c;
    URLDrawable urlDrawable;
    TextView container;

    public URLImageParser(TextView c) {
        this.container = c;
    }

    public Drawable getDrawable(String source) {
        urlDrawable = new URLDrawable();
        ImageGetterAsyncTask asyncTask = new ImageGetterAsyncTask(urlDrawable);
        asyncTask.execute(source);
        return urlDrawable;
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
           // float multiplier = (float) 200 / (float) result.getIntrinsicWidth();
           // int width = (int) (result.getIntrinsicWidth() * multiplier);
           // int height = (int) (result.getIntrinsicHeight() * multiplier);
          //  Log.d("TAG", width+"-"+height);
            Log.d("height + width", URLImageParser.this.container.getHeight() + "-" + result.getIntrinsicHeight());
            urlDrawable.setBounds(80, 500, 500, 600);
            urlDrawable.drawable = result;
            URLImageParser.this.container.invalidate();
            URLImageParser.this.container.setHeight((URLImageParser.this.container.getHeight() + result.getIntrinsicHeight()));
        }

        public Drawable fetchDrawable(String urlString) {
            try {
                InputStream is = fetch(urlString);
                Drawable drawable = Drawable.createFromStream(is, "src");
                drawable.setBounds(50, -50, 850 + drawable.getIntrinsicWidth(),500 + drawable.getIntrinsicHeight());
              //  drawable.setBounds(0,0,0,0);
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

