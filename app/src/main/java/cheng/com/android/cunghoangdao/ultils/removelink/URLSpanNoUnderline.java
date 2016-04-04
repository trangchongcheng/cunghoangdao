package cheng.com.android.cunghoangdao.ultils.removelink;

import android.text.TextPaint;
import android.text.style.URLSpan;

import cheng.com.android.cunghoangdao.R;

/**
 * Created by Welcome on 3/29/2016.
 */
public class URLSpanNoUnderline extends URLSpan {
    public URLSpanNoUnderline(String p_Url) {
        super(p_Url);
    }

    public void updateDrawState(TextPaint p_DrawState) {
        super.updateDrawState(p_DrawState);
        p_DrawState.setUnderlineText(false);
        p_DrawState.setColor(R.color.colorPrimary);
    }
}
