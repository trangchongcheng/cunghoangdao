package cheng.com.android.cunghoangdao.services;

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.List;

import cheng.com.android.cunghoangdao.model.hometab.NewsFeed;

/**
 * Created by Welcome on 3/24/2016.
 */
public class XMLGetData {
    public  static final String TAG = "XMLGetData";
    private String mUrl;
    private XmlPullParserFactory factory;
    private XmlPullParser parser;
    private InputStream urlStream;
    private List<NewsFeed> arrayNewsFeed;
    private NewsFeed newsFeed;
    private String tagName;

    public static final String ITEM = "item";
    public static final String CHANNEL = "channel";

    public static final String TITLE = "title";
    public static final String LINK = "link";
    public static final String DESCRIPTION = "description";
    public static final String CATEGORY = "category";
    public static final String PUBLISHEDDATE = "pubDate";
    public static final String CONTENT_ENCODED = "content:encoded";

    private String title;
    private String link;
    private String description;
    private String category;
    private String pubDate;
    private String guid;
    private String image;


    public XMLGetData(String mUrl) {
        this.mUrl = mUrl;
    }

    public static InputStream downloadUrl(String urlString) throws IOException {
        URL url = null;
        InputStream stream = null;
        try {
            url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setReadTimeout(18000); //60s
            conn.setConnectTimeout(18000); //30s
            conn.setUseCaches(false);
            conn.setDoInput(true);
            conn.connect();
            stream = conn.getInputStream();
        }catch (SocketTimeoutException e) {
                e.printStackTrace();
            Log.d(TAG, "Time out ");
                return null;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return stream;
    }
}
