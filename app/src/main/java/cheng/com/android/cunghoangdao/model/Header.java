package cheng.com.android.cunghoangdao.model;

/**
 * Created by Welcome on 5/3/2016.
 */
public class Header {
    //    More fields can be defined here after your need
    private  int thumbnail;
    private String category;
    private String content;
    private String title;
    private String description;

    public Header() {
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Header(String category, String content, String title, String description,int thumbnail) {
        this.content=content;
        this.category=category;
        this.title = title;
        this.description = description;
        this.thumbnail=thumbnail;
    }

    public int getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(int thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

