package cheng.com.android.cunghoangdao.services;


import android.content.Context;
import android.util.Log;

import org.apache.commons.lang.StringEscapeUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.ArrayList;

import cheng.com.android.cunghoangdao.model.Category;
import cheng.com.android.cunghoangdao.ultils.ConnectionUltils;

public class ApiServiceLichNgayTot {

    public enum ApiRequestType {
        TYPE_POST,
        TYPE_PUT,
        TYPE_DELETE,
        TYPE_GET
    }


    private static int temp = 1;

    public static ArrayList<Category> makeHttpRequest(Context context, String targetUrl,
                                                      ApiRequestType typeRequest, String params,
                                                      int typeGet, int typeCategory, String categoryName) {
        ArrayList<Category> arrCategory = new ArrayList<>();
        URL url;
        HttpURLConnection httpURLConnection = null;
        String content = null;
        if (!ConnectionUltils.isConnected(context)) {
            return null;
        }
        StringBuilder response = null;
        try {
            url = new URL(targetUrl);
            httpURLConnection = (HttpURLConnection) url.openConnection();

            switch (typeRequest) {
                case TYPE_POST:
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    break;
                case TYPE_GET:
                    httpURLConnection.setRequestMethod("GET");
                    httpURLConnection.setDoOutput(false);
                    break;
                case TYPE_PUT:
                    httpURLConnection.setRequestMethod("PUT");
                    httpURLConnection.setDoOutput(true);
                    break;
                default:
                    httpURLConnection.setRequestMethod("DELETE");
                    httpURLConnection.setDoOutput(false);
                    break;
            }
            httpURLConnection.setRequestProperty("Content-type", "application/json;charset=utf-8");
            httpURLConnection.setReadTimeout(15000); //15s
            httpURLConnection.setConnectTimeout(15000); //15s
            httpURLConnection.setUseCaches(false);
            httpURLConnection.setDoInput(true);
            httpURLConnection.connect();

            if ((params != null) && (!params.equals(""))) {
                DataOutputStream os = new DataOutputStream(httpURLConnection.getOutputStream());
                os.writeBytes(params);
                os.flush();
                os.close();
            }
            InputStream is = httpURLConnection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, "UTF-8"), 8);
            response = new StringBuilder();
            while ((content = rd.readLine()) != null) {
                response.append(content);
            }
            rd.close();
            if (typeGet == 0) {
                String contentUnicode = StringEscapeUtils.unescapeJava(response.toString());
                arrCategory.add(new Category(contentUnicode.replace("\"", "").replace("\n", "")
                        .replace("[adv]","").replace("tuoi dan ok.jpg","tuoi%20dan%20ok.jpg")));
            } else if (typeGet == 2) {
                arrCategory.add(new Category(response.toString()));
            } else {
                Elements title = null;
                Document document = null;
                String contentUnicode = StringEscapeUtils.unescapeJava(response.toString());
                document = Jsoup.parse(contentUnicode);
                Elements link = document.select("div[class=\"thunal220x140\"] a");
                Elements image = document.select("div[class=\"thunal220x140\"] a img");
                if(typeGet==3){
                    title= document.select("h3[class=\"h3-seo\"]");
                }else {
                    title = document.select("span[class=\"h3-seo\"]");
                }
                Elements decription = document.select("p[style=\"color:#777; font-size:13px;\"]");
                for (int i = 0; i < link.size(); i++) {
                    arrCategory.add(new Category(title.get(i).text(), image.get(i).attr("src"),
                            link.get(i).attr("href"), decription.get(i).text().replace("(Lichngaytot.com)", ""), categoryName));
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        } catch (SocketTimeoutException e) {
            Log.d("SocketTimeoutException", "SocketTimeoutException: ");
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
        }
        Log.d("Cheng", "makeHttpRequest: "+arrCategory.size());
        return arrCategory;
    }
}
