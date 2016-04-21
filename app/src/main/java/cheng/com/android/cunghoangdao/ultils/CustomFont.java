package cheng.com.android.cunghoangdao.ultils;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Welcome on 4/18/2016.
 */
public class CustomFont {

    public static void custfont(final Context context, View v, String patch) {
        try {
            if (v instanceof ViewGroup) {
                ViewGroup vg = (ViewGroup) v;

                for (int i = 0; i < vg.getChildCount(); i++) {
                    View child = vg.getChildAt(i);
                    overrideFonts(context, child);
                }
            } else if (v instanceof TextView) {
                ((TextView) v).setTypeface(Typeface.createFromAsset(
                        context.getAssets(), patch));
            }
        } catch (Exception e) {
        }
    }

    private static void overrideFonts(Context context, View child) {
    }
}
