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

import cheng.com.android.cunghoangdao.interfaces.OnReturnContent;
import cheng.com.android.cunghoangdao.interfaces.OnReturnVideoURL;
import cheng.com.android.cunghoangdao.ultils.ConnectionUltils;

/**
 * Created by Welcome on 4/17/2016.
 */
public class JsoupParseLichNgayTot extends AsyncTask<String, String, String> {
    private OnReturnContent onReturnContent;
    private OnReturnVideoURL onReturnVideoURL;
    private Context context;
    private String mLink;
    private String videoUrl = null;
    private String typeVideo;

    public JsoupParseLichNgayTot(Context context, String link, OnReturnContent onReturnContent, String typeVideo) {
        this.context = context;
        this.mLink = link;
        this.onReturnContent = onReturnContent;
        this.typeVideo=typeVideo;
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
            content = document.select("div[class=\"chitiet-singger\"]");
            content.select("script").remove();
            content.select("script").remove();
            content.select("script").remove();
            content.select("script").remove();
            content.select("div[class=\"articlerelatepannel\"]").remove();
            videoUrl = document.select("div[class=\"mediaplayercontent\"]").attr("data-href");
        } catch (HttpStatusException ex) {
            Log.d("Category", "HttpStatusException");
            return "";
        } catch (SocketTimeoutException e) {
            Log.d("Category", "Socket Timeout: ");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content.toString().replace("(Lichngaytot.com)","").replace("Lichngaytot.com","").replace("h3","h5").replace("&nbsp;","");
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if(typeVideo!= null){
            onReturnContent.onReturnContent(s,videoUrl);
        }else {
            onReturnContent.onReturnContent(s);
        }
    }

}
