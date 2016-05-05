package cheng.com.android.cunghoangdao.services;

import android.content.Context;
import android.os.AsyncTask;

import cheng.com.android.cunghoangdao.interfaces.OnReturnArticleCunghoangdaoByDay;
import cheng.com.android.cunghoangdao.model.Article;

/**
 * Created by Welcome on 4/24/2016.
 */
public class JsoupParseCungHoangDaoByDay extends AsyncTask<String, Void, Article> {
    private OnReturnArticleCunghoangdaoByDay onReturnArticleCunghoangdaoByDay;
    private Context context;
    private String urlArticle;
    public JsoupParseCungHoangDaoByDay(Context context,OnReturnArticleCunghoangdaoByDay onReturnArticleCunghoangdaoByDay,
    String urlArticle) {
        this.context=context;
        this.urlArticle = urlArticle;
        this.onReturnArticleCunghoangdaoByDay = onReturnArticleCunghoangdaoByDay;
    }

    @Override
    protected Article doInBackground(String... params) {
        return Article.paserJsoupCategory(urlArticle,context);
    }

    @Override
    protected void onPostExecute(Article article) {
        super.onPostExecute(article);
        onReturnArticleCunghoangdaoByDay.onReturnArticleCunghoangdaoByDay(article);
    }


}
