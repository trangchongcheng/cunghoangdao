package cheng.com.android.cunghoangdao.ultils;

import android.view.View;
import android.widget.ScrollView;

/**
 * Created by Iris Louis on 29/09/2015.
 */
public class ScrollUtil {
    public static void scrollUtil(final ScrollView scrollView){
        scrollView.post(new Runnable() {
            public void run() {
                scrollView.fullScroll(View.FOCUS_UP);
            }
        });
    }
}
