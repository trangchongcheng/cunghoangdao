package cheng.com.android.cunghoangdao.model;

/**
 * Created by Welcome on 4/19/2016.
 */
public class LanguageItem {
    private String name;
    private String locale;
    private boolean isSelected;

    public LanguageItem(String name, String locale, boolean isSelected) {
        this.isSelected = isSelected;
        this.locale = locale;
        this.name = name;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setIsSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }
}
