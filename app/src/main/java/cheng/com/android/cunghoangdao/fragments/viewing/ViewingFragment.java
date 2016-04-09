package cheng.com.android.cunghoangdao.fragments.viewing;

import android.content.Context;
import android.graphics.Typeface;
import android.text.Html;
import android.text.Spannable;
import android.text.method.ScrollingMovementMethod;
import android.text.style.URLSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cheng.com.android.cunghoangdao.R;
import cheng.com.android.cunghoangdao.fragments.BaseFragment;
import cheng.com.android.cunghoangdao.ultils.htmltextview.URLImageParser1;
import cheng.com.android.cunghoangdao.ultils.removelink.URLSpanNoUnderline;

/**
 * Created by Welcome on 3/28/2016.
 */
public class ViewingFragment extends BaseFragment {
    private String mContent;
    private TextView tvContent;
    private String content;

    public ViewingFragment(String content) {
        this.content = content;
    }

    @Override
    public void init() {
        tvContent = (TextView) getView().findViewById(R.id.fragment_viewing_tvContent);
        //  Bundle bundle = this.getArguments();
        //  mContent = bundle.getString("key", "");
    }

    @Override
    public void setEvent() {

    }

    @Override
    public void setValue() {
        URLImageParser1 p = new URLImageParser1(tvContent);
        Spannable htmlSpan = (Spannable) Html.fromHtml(content, p, null);
        tvContent.setText(htmlSpan);
        tvContent.setMovementMethod(new ScrollingMovementMethod());
       // tvContent.setMovementMethod(LinkMovementMethod.getInstance());
        Spannable processedText = removeUnderlines(htmlSpan);
        tvContent.setClickable(true);
        tvContent.setText(processedText);
        custfont(getActivity(), tvContent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_viewing;
    }

    private void custfont(final Context context, View v) {
        try {
            if (v instanceof ViewGroup) {
                ViewGroup vg = (ViewGroup) v;

                for (int i = 0; i < vg.getChildCount(); i++) {
                    View child = vg.getChildAt(i);
                    overrideFonts(context, child);
                }
            } else if (v instanceof TextView) {
                ((TextView) v).setTypeface(Typeface.createFromAsset(context.getAssets(), "fonts/SP3Hero.ttf"));
            }
        } catch (Exception e) {
        }
    }

    private void overrideFonts(Context context, View child) {
    }

    public static Spannable removeUnderlines(Spannable p_Text) {
        URLSpan[] spans = p_Text.getSpans(0, p_Text.length(), URLSpan.class);
        for (URLSpan span : spans) {
            int start = p_Text.getSpanStart(span);
            int end = p_Text.getSpanEnd(span);
            p_Text.removeSpan(span);
            span = new URLSpanNoUnderline(span.getURL());
            p_Text.setSpan(span, start, end, 0);
        }
        return p_Text;
    }


}
