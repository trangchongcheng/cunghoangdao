package cheng.com.android.cunghoangdao.model.hometab;

import android.content.Context;
import android.support.annotation.NonNull;

import com.alorma.timeline.TimelineView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import cheng.com.android.cunghoangdao.services.XMLGetData;
import cheng.com.android.cunghoangdao.ultils.ConnectionUltils;

/**
 * Created by Welcome on 3/24/2016.
 */
public class NewsFeed {
    private String mTitle;
    private String mLink;
    private String mPubdate;
    private String mCategory;
    private String mDescription;
    private String mContent;
    private String mImageUrl;

    private int type;
    private int alignment;

    private static int mNumberLastPost;
    private XmlPullParserFactory factory;
    private XmlPullParser parser;
    private String tagName;


    public NewsFeed() {
    }

    public NewsFeed(String mTitle, String mLink, String mPubdate, String mCategory,
                    String mDescription, String mContent, String mImageUrl) {
        this.mTitle = mTitle;
        this.mLink = mLink;
        this.mPubdate = mPubdate;
        this.mCategory = mCategory;
        this.mDescription = mDescription;
        this.mContent = mContent;
        this.mImageUrl = mImageUrl;
    }

    public NewsFeed(@NonNull String mTitle, String mLink, String mPubdate, String mCategory, String mDescription,
                    String mContent, String mImageUrl, int type) {
        this.mTitle = mTitle;
        this.mLink = mLink;
        this.mPubdate = mPubdate;
        this.mCategory = mCategory;
        this.mDescription = mDescription;
        this.mContent = mContent;
        this.mImageUrl = mImageUrl;
        this.type = type;
        //this(mTitle, mLink, mPubdate, mCategory, mDescription, mContent, mImageUrl, type, TimelineView.ALIGNMENT_DEFAULT);

    }

    public NewsFeed(@NonNull String mTitle, String mLink, String mPubdate, String mCategory, String mDescription,
                    String mContent, String mImageUrl, int type, int alignment) {
        this.mTitle = mTitle;
        this.mLink = mLink;
        this.mPubdate = mPubdate;
        this.mCategory = mCategory;
        this.mDescription = mDescription;
        this.mContent = mContent;
        this.mImageUrl = mImageUrl;
        this.type = type;
        this.alignment = alignment;

    }

    public NewsFeed(String mTitle, String mDescription, String mLink, String mImageUrl) {
        this.mTitle = mTitle;
        this.mLink = mLink;
        this.mDescription = mDescription;
        this.mImageUrl = mImageUrl;
    }

    public NewsFeed(String mTitle, String mLink, String mPubdate, String mCategory, String mDescription, String mContent) {
        this.mTitle = mTitle;
        this.mLink = mLink;
        this.mPubdate = mPubdate;
        this.mCategory = mCategory;
        this.mDescription = mDescription;
        this.mContent = mContent;
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

    public String getmPubdate() {
        return mPubdate;
    }

    public void setmPubdate(String mPubdate) {
        this.mPubdate = mPubdate;
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

    public String getmContent() {
        return mContent;
    }

    public void setmContent(String mContent) {
        this.mContent = mContent;
    }

    public String getmImageUrl() {
        return mImageUrl;
    }

    public void setmImageUrl(String mImageUrl) {
        this.mImageUrl = mImageUrl;
    }


    public
    @TimelineView.TimelineType
    int getType() {
        return type;
    }

    public void setType(@TimelineView.TimelineType int type) {
        this.type = type;
    }

    public
    @TimelineView.TimelineAlignment
    int getAlignment() {
        return alignment;
    }

    public void setAlignment(@TimelineView.TimelineAlignment int alignment) {
        this.alignment = alignment;
    }


    public static List<NewsFeed> parse(String url,Context context) {
        mNumberLastPost = 0;
        InputStream inputStream = null;
        List<NewsFeed> rssFeedList = null;
        XmlPullParserFactory factory;
        XmlPullParser parser;
        String tagName;
        String mTitle = null, mLink = null, mDescription = null,
                mPubdate = null, mCategory = null, mContent = null, mImageUrl = null;
        if(ConnectionUltils.isConnected(context)==false){
            return null;
        }
        try {
            int count = 0;
            factory = XmlPullParserFactory.newInstance();
            parser = factory.newPullParser();
            inputStream = XMLGetData.downloadUrl(url);
            parser.setInput(inputStream, null);
            int eventType = parser.getEventType();
            boolean done = false;
            NewsFeed newsFeed = new NewsFeed();
            rssFeedList = new ArrayList<NewsFeed>();
            while (eventType != XmlPullParser.END_DOCUMENT && !done) {
                tagName = parser.getName();

                switch (eventType) {
                    case XmlPullParser.START_DOCUMENT:
                        break;
                    case XmlPullParser.START_TAG:
                        if (tagName.equals("item")) {
                            newsFeed = new NewsFeed();
                        }
                        if (tagName.equals("title")) {
                            mTitle = parser.nextText().toString();
                        }
                        if (tagName.equals("link")) {
                            mLink = parser.nextText().toString();
                        }
                        if (tagName.equals("pubDate")) {
                            mPubdate = parser.nextText().toString();
                        }
                        if (tagName.equals("category")) {
                            mCategory = parser.nextText().toString();
                        }
                        if (tagName.equals("description")) {
                            mDescription = parser.nextText().toString();
                        }
                        if (tagName.equals("content:encoded")) {
                            mContent = parser.nextText().toString();
                            mImageUrl = getImageFromContent(mContent);
                        }
                        break;
                    case XmlPullParser.END_TAG:
                        if (tagName.equals("channel")) {
                            done = true;
                        } else if (tagName.equals("item")) {
                            if (mNumberLastPost < 4) {
                                if (mNumberLastPost == 0) {
                                    newsFeed = new NewsFeed(mTitle, mLink, mPubdate, mCategory,
                                            mDescription, mContent, mImageUrl, TimelineView.TYPE_START);
                                } else if (mNumberLastPost == 3) {
                                    newsFeed = new NewsFeed(mTitle, mLink, mPubdate, mCategory,
                                            mDescription, mContent, mImageUrl, TimelineView.TYPE_END);
                                } else {
                                    newsFeed = new NewsFeed(mTitle, mLink, mPubdate, mCategory,
                                            mDescription, mContent, mImageUrl, TimelineView.TYPE_DEFAULT);
                                }

                            } else {
                                newsFeed = new NewsFeed(mTitle, mLink, mPubdate, mCategory,
                                        mDescription, mContent, mImageUrl, TimelineView.TYPE_START);
                            }
                            rssFeedList.add(newsFeed);
                            ++mNumberLastPost;
                        }
                        break;
                }
                eventType = parser.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return rssFeedList;

    }
    public static String getImageFromContent(String content) {
        try {
            int start = content.indexOf("src=\"", 0) + 5;
            int end = content.indexOf(("alt=\""), 0) - 2;
            String imageUrl = content.substring(start, end);
            if (imageUrl != null) {
                return imageUrl;
            }
        } catch (StringIndexOutOfBoundsException ex) {
            return "";
        }

        return "";
    }

}
