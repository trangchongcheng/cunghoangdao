package cheng.com.android.cunghoangdao.model;

/**
 * Created by Welcome on 1/18/2016.
 */
public class User {
    private String UserName;
    private int Dao;
    private int Month;
    private int Year;
    public User(){

    }
    public User(String userName, int dao, int month, int year) {
        UserName = userName;
        Dao = dao;
        Month = month;
        Year = year;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public int getDao() {
        return Dao;
    }

    public void setDao(int dao) {
        Dao = dao;
    }

    public int getMonth() {
        return Month;
    }

    public void setMonth(int month) {
        Month = month;
    }

    public int getYear() {
        return Year;
    }

    public void setYear(int year) {
        Year = year;
    }
}
