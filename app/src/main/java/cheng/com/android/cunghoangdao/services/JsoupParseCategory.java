package cheng.com.android.cunghoangdao.services;

import android.content.Context;
import android.os.AsyncTask;

import java.util.ArrayList;

import cheng.com.android.cunghoangdao.model.Category;

/**
 * Created by Welcome on 3/31/2016.
 */
public class JsoupParseCategory extends AsyncTask<Void,Void,ArrayList<Category>> {
    private String mUrl;
    private Context context;
    private OnReturnCategoryList onReturnCategoryList;
    private int typeCategory;
    public JsoupParseCategory(Context context, String mUrl, OnReturnCategoryList onReturnCategoryList, int typeCategory) {
        this.context = context;
        this.mUrl = mUrl;
        this.typeCategory=typeCategory;
        this.onReturnCategoryList = onReturnCategoryList;
    }

    @Override
    protected ArrayList<Category> doInBackground(Void... params) {
        return Category.paserJsoupCategory(mUrl);
    }

    @Override
    protected void onPostExecute(ArrayList<Category> arrCategory) {
        super.onPostExecute(arrCategory);
        onReturnCategoryList.onReturnCategoryListFinish(arrCategory,typeCategory);
    }
    public interface OnReturnCategoryList{
        void onReturnCategoryListFinish(ArrayList<Category> arrCategory, int typeCategory);
    }
}
