package cheng.com.android.cunghoangdao.model;

/**
 * Created by Welcome on 4/25/2016.
 */
public class Tienich {
    private int color;
    private String title;
    private int image;
    private String decription;

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getDecription() {
        return decription;
    }

    public void setDecription(String decription) {
        this.decription = decription;
    }

    public Tienich(String title, int image, String decription, int color) {
        this.title = title;
        this.image = image;
        this.decription = decription;
        this.color=color;
    }
}
