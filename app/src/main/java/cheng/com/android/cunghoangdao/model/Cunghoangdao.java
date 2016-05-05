package cheng.com.android.cunghoangdao.model;

/**
 * Created by Welcome on 1/24/2016.
 */
public class Cunghoangdao {
    private String name;
    private int image;
    private String date;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public Cunghoangdao(String name, int image) {
        this.name = name;
        this.image = image;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
