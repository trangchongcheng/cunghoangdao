package cheng.com.android.cunghoangdao.abtracts;

import android.support.v4.app.Fragment;
import android.util.Log;

/**
 * Created by Welcome on 4/6/2016.
 */
public abstract class BaseLazyFragment extends Fragment {
    private final String TAG = getClass().getSimpleName();
    protected boolean isVisible;


    /**
     * to judge if is visible to user
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if(getUserVisibleHint()) {
            this.isVisible = true;
            onVisible();
        } else {
            this.isVisible = false;
            onInvisible();
        }

    }


    /**
     * when the  fragment is not visible to user
     */
    protected void onInvisible() {
        Log.d(TAG, "onInvisible: ");
    }

    /**
     * when the fragment is visible to user
     *
     * lazyload
     */
    protected void onVisible() {
        Log.d(TAG, "onVisible: ");
        lazyLoad();
    }

    protected abstract void lazyLoad();

}

