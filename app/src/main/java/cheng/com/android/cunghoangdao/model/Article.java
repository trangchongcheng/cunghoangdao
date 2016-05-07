package cheng.com.android.cunghoangdao.model;

import android.content.Context;
import android.util.Log;

import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.SocketTimeoutException;

import cheng.com.android.cunghoangdao.R;
import cheng.com.android.cunghoangdao.ultils.ConnectionUltils;

/**
 * Created by Welcome on 4/7/2016.
 */
public class Article {
    private int id;
    private String mTitle;
    private String mLink;
    private String mCategory;
    private String mDescription;
    private byte[] mImage;
    private String mContent;
    private int hasRead;
    public static Article arrArticle;

    public Article(int id, String mTitle, String mLink, String mCategory,
                   String mDescription, byte[] mImage, String mContent) {
        this.id = id;
        this.mTitle = mTitle;
        this.mLink = mLink;
        this.mCategory = mCategory;
        this.mDescription = mDescription;
        this.mImage = mImage;
        this.mContent = mContent;
    }
    public Article(int id, String mTitle, String mCategory,
                   byte[] mImage, String mContent) {
        this.id = id;
        this.mTitle = mTitle;
        this.mCategory = mCategory;
        this.mImage = mImage;
        this.mContent = mContent;
    }

    public Article(int id, String mTitle, String mDescription,
                   String mContent, int hasRead) {
        this.id = id;
        this.mTitle = mTitle;
        this.mDescription = mDescription;
        this.mContent = mContent;
        this.hasRead = hasRead;
    }

    public Article( String mTitle, String mDescription,
                    String mContent) {
        this.mTitle = mTitle;
        this.mDescription = mDescription;
        this.mContent = mContent;
    }

    public Article( String mTitle, String mCategory,
                   byte[] mImage, String mContent) {
        this.mTitle = mTitle;
        this.mCategory = mCategory;
        this.mImage = mImage;
        this.mContent = mContent;
    }

    public Article() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getmLink() {
        return mLink;
    }

    public void setmLink(String mLink) {
        this.mLink = mLink;
    }

    public String getmCategory() {
        return mCategory;
    }

    public void setmCategory(String mCategory) {
        this.mCategory = mCategory;
    }

    public String getmDescription() {
        return mDescription;
    }

    public void setmDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public byte[] getmImage() {
        return mImage;
    }

    public void setmImage(byte[] mImage) {
        this.mImage = mImage;
    }

    public String getmContent() {
        return mContent;
    }

    public void setmContent(String mContent) {
        this.mContent = mContent;
    }


    public static Article paserJsoupCategory(String url, Context context) {
        if(!ConnectionUltils.isConnected(context)){
            return null;
        }
        try {

            // Connect to the web site
            Document document = Jsoup.connect(url)
                    .timeout(18000)
                    .get();
            String title = context.getString(R.string.tu_vi_hom_nay);
            Elements content = document.select("div[class=\"chitiet-singger\"]");
            content.select("div[class=\"list_anchor_neo\"]").remove();
            content.select("script").remove();
            content.select("script").remove();
            content.select("script").remove();
            content.select("script").remove();
            arrArticle = new Article(title,content.text().substring(0,150),content.toString()
                    .replace("<img src=\"","<img src=\"http://lichngaytot.com"));

        }catch (HttpStatusException ex){
            Log.d("Category", "HttpStatusException");
            return null;
        }
        catch (SocketTimeoutException e) {
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return arrArticle;
    }
}
