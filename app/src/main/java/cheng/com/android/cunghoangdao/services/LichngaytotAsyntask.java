package cheng.com.android.cunghoangdao.services;

import android.content.Context;
import android.os.AsyncTask;

import java.util.ArrayList;

import cheng.com.android.cunghoangdao.model.Category;

/**
 * Created by Welcome on 4/12/2016.
 */
public class LichngaytotAsyntask extends AsyncTask<String, Void, ArrayList<Category>> {
    private Context context;
    private String mUrl;
    private ApiServiceLichNgayTot.ApiRequestType typeRequest;
    private String params;
    private int typeGet;
    private OnReturnJsonObject onReturnJsonObject;

    public LichngaytotAsyntask(Context context, String mUrl, ApiServiceLichNgayTot.ApiRequestType typeRequest,
                               String params,OnReturnJsonObject onReturnJsonObject) {
        this.context = context;
        this.mUrl = mUrl;
        this.typeRequest = typeRequest;
        this.params = params;
        this.onReturnJsonObject=onReturnJsonObject;
    }
    public LichngaytotAsyntask(Context context, String mUrl,ApiServiceLichNgayTot.ApiRequestType typeRequest,int typeGet,
                              OnReturnJsonObject onReturnJsonObject) {
        this.context = context;
        this.mUrl = mUrl;
        this.typeRequest = typeRequest;
        this.typeGet=typeGet;
        this.params = params;
        this.onReturnJsonObject=onReturnJsonObject;
    }

    @Override
    protected ArrayList<Category> doInBackground(String... param) {
        ArrayList<Category> arrContent = ApiServiceLichNgayTot.makeHttpRequest(context,mUrl,typeRequest,params,typeGet);
        return arrContent;
    }

    @Override
    protected void onPostExecute( ArrayList<Category> arrContent) {
        super.onPostExecute(arrContent);
        onReturnJsonObject.onReturnJsonObject(arrContent);
    }
    public interface OnReturnJsonObject{
        void onReturnJsonObject(ArrayList<Category> arrContent);
    }
}
