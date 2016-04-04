package cheng.com.android.cunghoangdao.ultils.loadmore;

import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ProgressBar;

import java.util.ArrayList;

import cheng.com.android.cunghoangdao.model.Category;

/**
 * Created by Welcome on 4/1/2016.
 */
public abstract class LoadmoreCategory implements AbsListView.OnScrollListener {
    private int visibleThreshold;
    private int previousTotal;
    private boolean loading;
    ArrayList<Category> arrayCategory;
    ProgressBar progressLoadMore;

    public LoadmoreCategory(ArrayList<Category> array, ProgressBar progressLoadMore) {
        this.arrayCategory = array;
        this.progressLoadMore = progressLoadMore;
        this.visibleThreshold = 4;
        this.previousTotal = 0;
        this.loading = true;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    public abstract void LoadMore();

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (loading) {
            if (totalItemCount > previousTotal) {
                loading = false;
                previousTotal = totalItemCount;
                Log.d("tang", previousTotal + "");
            }
        }
        if (!loading && (totalItemCount) <= (firstVisibleItem + visibleItemCount)) {
            if (arrayCategory.size() == 100) {
                loading = false;
            } else {
                progressLoadMore.setVisibility(View.VISIBLE);
                LoadMore();
                loading = true;
            }
        }

    }
}