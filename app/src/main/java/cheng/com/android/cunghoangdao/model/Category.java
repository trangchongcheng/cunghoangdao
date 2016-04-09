package cheng.com.android.cunghoangdao.model;

import android.util.Log;

import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;

/**
 * Created by Welcome on 3/31/2016.
 */
public class Category {
    public static boolean isLast = false;
    private final String TAG = getClass().getSimpleName();
    private String mTitle;
    private String mImage;
    private String mLink;
    private String mDescription;
    private String mContent;

    public Category() {
    }

    public Category(String mTitle, String mImage, String mLink, String mDescription) {
        this.mTitle = mTitle;
        this.mImage = mImage;
        this.mLink = mLink;
        this.mDescription = mDescription;
    }
    public Category(String mTitle, String mImage, String mLink, String mDescription,String mContent) {
        this.mTitle = mTitle;
        this.mImage = mImage;
        this.mLink = mLink;
        this.mDescription = mDescription;
        this.mContent = mContent;
    }

    public Category(String mTitle, String mImage, String mLink) {
        this.mTitle = mTitle;
        this.mImage = mImage;
        this.mLink = mLink;
    }

    public String getmLink() {
        return mLink;
    }

    public void setmLink(String mLink) {
        this.mLink = mLink;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getmImage() {
        return mImage;
    }

    public void setmImage(String mImage) {
        this.mImage = mImage;
    }

    public String getmDescription() {
        return mDescription;
    }

    public void setmDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public String getmContent() {
        return mContent;
    }

    public void setmContent(String mContent) {
        this.mContent = mContent;
    }

    public static ArrayList<Category> paserJsoupCategory(String url) {
        ArrayList<Category> arrCategory = new ArrayList<>();
        try {
            // Connect to the web site
            Document document = Jsoup.connect(url)
                    .timeout(18000)
                    .get();
            // Using Elements to get the class data
            Elements title = document.select("div[class=\"meta-image\"] a");
            Elements image = document.select("div[class=\"meta-image\"] a img ");
            for (int i = 0; i < title.size(); i++) {
                arrCategory.add(new Category(title.get(i).attr("title"),
                        image.get(i).attr("src"), title.get(i).attr("href") + ""));

            }
        }catch (HttpStatusException ex){
            isLast = true;
            Log.d("Category", "HttpStatusException");
            return arrCategory;
        }
        catch (SocketTimeoutException e) {
            Log.d("Category", "Socket Timeout: ");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return arrCategory;
    }

}
