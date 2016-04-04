package cheng.com.android.cunghoangdao.services;

import android.content.Context;
import android.os.AsyncTask;

import java.util.ArrayList;

import cheng.com.android.cunghoangdao.model.Category;

/**
 * Created by Welcome on 3/31/2016.
 */
public class JsoupParseAsyntask extends AsyncTask<Void,Void,ArrayList<Category>> {
    private String mUrl;
    private Context context;
    private OnReturnCategoryList onReturnCategoryList;
    public JsoupParseAsyntask(Context context,String mUrl,OnReturnCategoryList onReturnCategoryList) {
        this.context = context;
        this.mUrl = mUrl;
        this.onReturnCategoryList = onReturnCategoryList;
    }

    @Override
    protected ArrayList<Category> doInBackground(Void... params) {
        return Category.paserJsoupCategory(mUrl);
    }

    @Override
    protected void onPostExecute(ArrayList<Category> arrCategory) {
        super.onPostExecute(arrCategory);
        onReturnCategoryList.onReturnCategoryListFinish(arrCategory);
    }
    public interface OnReturnCategoryList{
        void onReturnCategoryListFinish(ArrayList<Category> arrCategory);
    }
}
