package cheng.com.android.cunghoangdao.model;

import cheng.com.android.cunghoangdao.R;

/**
 * Created by Welcome on 1/18/2016.
 */
public class Common {
    public static User user = new User();
    public static int imgAvatar;

    public static int getImgAvatar() {
        return imgAvatar;
    }

    public static void setImgAvatar(int imgAvatar) {
        Common.imgAvatar = imgAvatar;
    }

    public static String checkCungHoangDao(int month, int day) {
        String cunghoangdao = "";
        switch (month) {
            case 1:
                if (day < 20) {
                    cunghoangdao = "Ma Kết";
                    setImgAvatar(R.drawable.chipi);
                } else {
                    cunghoangdao = "Thủy Bình";
                    setImgAvatar(R.drawable.chipi);
                }
                break;
            case 2:
                if(day<19){
                    cunghoangdao = "Thủy Bình";
                    setImgAvatar(R.drawable.chipi); 
                }else {
                    cunghoangdao = "Song Ngư";
                    imgAvatar = R.drawable.chipi;
                }
                break;
            case 3:
                if(day<22){
                    cunghoangdao = "Song Ngư";
                    imgAvatar = R.drawable.chipi;
                }else {
                    cunghoangdao = "Bạch Dương";
                    imgAvatar = R.drawable.chipi;
                }
                break;
            case 4:
                if(day<21){
                    cunghoangdao = "Bạch Dương";
                    imgAvatar = R.drawable.chipi;
                }else {
                    cunghoangdao = "Kim Ngưu";
                    imgAvatar = R.drawable.chipi;
                }
                break;
            case 5:
                if(day<22){
                    cunghoangdao = "Kim Ngưu";
                    imgAvatar = R.drawable.chipi;
                }else {
                    cunghoangdao = "Song Tử";
                    imgAvatar = R.drawable.chipi;
                }
                break;
            case 6:
                if(day<22){
                    cunghoangdao = "Song Tử";
                    imgAvatar = R.drawable.chipi;
                }else {
                    cunghoangdao = "Cự Giải";
                    imgAvatar = R.drawable.chipi;
                }
                break;
            case 7:
                if(day<24){
                    cunghoangdao = "Cự Giải";
                    imgAvatar = R.drawable.chipi;
                }else {
                    cunghoangdao = "Sư Tử";
                    imgAvatar = R.drawable.chipi;
                }
                break;
            case 8:
                if(day<24){
                    cunghoangdao = "Sư Tử";
                    imgAvatar = R.drawable.chipi;
                }else {
                    cunghoangdao = "Xử Nữ";
                    imgAvatar = R.drawable.chipi;
                }
                break;
            case 9:
                if(day<23){
                    cunghoangdao = "Xử Nữ";
                    imgAvatar = R.drawable.chipi;
                }else {
                    cunghoangdao = "Thiên Bình";
                    imgAvatar = R.drawable.chipi;
                }
                break;
            case 10:
                if(day<24){
                    cunghoangdao = "Thiên Bình";
                    imgAvatar = R.drawable.chipi;
                }else {
                    cunghoangdao = "Thiên Yết";
                    imgAvatar = R.drawable.chipi;
                }
                break;
            case 11:
                if(day<25){
                    cunghoangdao = "Thiên Yết";
                    imgAvatar = R.drawable.chipi;
                }else {
                    cunghoangdao = "Nhân Mã";
                    imgAvatar = R.drawable.chipi;
                }
                break;
            case 12:
                if(day<20){
                    cunghoangdao = "Nhân Mã";
                    imgAvatar = R.drawable.chipi;
                }else {
                    cunghoangdao = "Ma Kết";
                    imgAvatar = R.drawable.chipi;
                }
                break;
        }
        return cunghoangdao;
    }
}
