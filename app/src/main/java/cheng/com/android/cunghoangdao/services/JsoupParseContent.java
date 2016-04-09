package cheng.com.android.cunghoangdao.services;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.SocketTimeoutException;

import cheng.com.android.cunghoangdao.ultils.ConnectionUltils;

/**
 * Created by Welcome on 4/7/2016.
 */
public class JsoupParseContent extends AsyncTask<String, String, String> {
    private OnReturnContent onReturnContent;
    private Context context;
    private String mLink;

    public JsoupParseContent(Context context, String link, OnReturnContent onReturnContent) {
        this.context = context;
        this.mLink = link;
        this.onReturnContent = onReturnContent;
    }

    @Override
    protected String doInBackground(String... params) {
        Elements content = null;
        String data = null;
        if(!ConnectionUltils.isConnected(context)){
           return null;
        }
        try {
            // Connect to the web site
            Document document = Jsoup.connect(mLink)
                    .timeout(18000)
                    .get();
            // Using Elements to get the class data
            content = document.select("div[class=\"entry-content\"],div[class=\"wp-caption aligncenter\"] img[src]");
            content.select("script").remove();
            content.select("script").remove();
            content.select("script").remove();
            content.select("script").remove();

          //  content.toString().replaceAll("(adsbygoogle = window.adsbygoogle || []).push({});","");

        } catch (HttpStatusException ex) {
            Log.d("Category", "HttpStatusException");
            return "";
        } catch (SocketTimeoutException e) {
            Log.d("Category", "Socket Timeout: ");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content.toString();
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        onReturnContent.onReturnContent(s);
    }

    public interface OnReturnContent {
        void onReturnContent(String content);
    }
}
