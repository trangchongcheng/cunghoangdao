package cheng.com.android.cunghoangdao.model;

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
}
