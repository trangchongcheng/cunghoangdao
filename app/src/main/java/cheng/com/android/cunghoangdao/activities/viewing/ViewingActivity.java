package cheng.com.android.cunghoangdao.activities.viewing;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.Spannable;
import android.text.method.ScrollingMovementMethod;
import android.text.style.URLSpan;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cheng.com.android.cunghoangdao.R;
import cheng.com.android.cunghoangdao.activities.BaseActivity;
import cheng.com.android.cunghoangdao.adapters.cunghoangdaotab.RecyclerCunghoangdaoAdapter;
import cheng.com.android.cunghoangdao.ultils.htmltextview.URLImageParser;
import cheng.com.android.cunghoangdao.ultils.removelink.URLSpanNoUnderline;

/**
 * Created by Welcome on 3/28/2016.
 */
public class ViewingActivity extends BaseActivity {
    private Toolbar mToolbar;
    private CollapsingToolbarLayout mClsToolbar;
    private int mutedColor = R.attr.colorPrimary;
    private TextView tvContent;
    private final String TAG = getClass().getSimpleName();
    String content, title;

    @Override
    public void setContentView() {
        overridePendingTransition(R.anim.activity_open_translate, R.anim.activity_close_scale);
        setContentView(R.layout.activity_viewing);
    }

    @Override
    public void init() {
        Intent intent = getIntent();
        content = intent.getStringExtra(RecyclerCunghoangdaoAdapter.CONTENT);
        title = intent.getStringExtra(RecyclerCunghoangdaoAdapter.TITLE);

        mToolbar = (Toolbar) findViewById(R.id.activity_viewing_toolbar);
        mToolbar.setTitle(title);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tvContent = (TextView) findViewById(R.id.activity_viewing_tvContent);
        mClsToolbar = (CollapsingToolbarLayout) findViewById(R.id.activity_viewing_cls_toolbar);
        URLImageParser p = new URLImageParser(tvContent);
        Spannable htmlSpan = (Spannable) Html.fromHtml(content, p, null);
        tvContent.setText(htmlSpan);
        tvContent.setMovementMethod(new ScrollingMovementMethod());
        Spannable processedText = removeUnderlines(htmlSpan);
        tvContent.setClickable(true);
        tvContent.setText(processedText);
        custfont(this, tvContent);
        // setDynamicColor();
    }

    @Override
    public void setValue(Bundle savedInstanceState) {


    }

    @Override
    public void setEvent() {
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void setDynamicColor() {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),
                R.drawable.background);

        // It will generate colors based on the image in an AsyncTask.
        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
            @SuppressWarnings("ResourceType")
            @Override
            public void onGenerated(Palette palette) {

                mutedColor = palette.getMutedColor(R.color.colorPrimary);
                mClsToolbar.setContentScrimColor(mutedColor);
                mClsToolbar.setStatusBarScrimColor(R.color.blue_semi_transparent);
            }
        });
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

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: ");
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        Log.d(TAG, "onPostResume: ");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
    }

    @Override
    protected void onPause() {
        super.onPause();
        overridePendingTransition(R.anim.activity_open_scale, R.anim.activity_close_translate);
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
                ((TextView) v).setTypeface(Typeface.createFromAsset(
                        context.getAssets(), "fonts/Roboto-Regular.ttf"));
            }
        } catch (Exception e) {
        }
    }

    private void overrideFonts(Context context, View child) {
    }

}
