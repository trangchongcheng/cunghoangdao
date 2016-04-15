package cheng.com.android.cunghoangdao.activities.viewing;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.Spannable;
import android.text.method.ScrollingMovementMethod;
import android.text.style.URLSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.facebook.FacebookSdk;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import cheng.com.android.cunghoangdao.R;
import cheng.com.android.cunghoangdao.activities.BaseActivity;
import cheng.com.android.cunghoangdao.adapters.cunghoangdaotab.RecyclerCunghoangdaoAdapter;
import cheng.com.android.cunghoangdao.model.Article;
import cheng.com.android.cunghoangdao.provider.DataHandlerSaveContent;
import cheng.com.android.cunghoangdao.services.CovertBitmapToByte;
import cheng.com.android.cunghoangdao.services.JsoupParseContent;
import cheng.com.android.cunghoangdao.ultils.ConnectionUltils;
import cheng.com.android.cunghoangdao.ultils.htmltextview.URLImageParser;
import cheng.com.android.cunghoangdao.ultils.removelink.URLSpanNoUnderline;

/**
 * Created by Welcome on 3/28/2016.
 */
public class ViewingActivity extends BaseActivity implements JsoupParseContent.OnReturnContent,
        CovertBitmapToByte.OnReturnBimapFromByte {
    private Toolbar mToolbar;
    private CollapsingToolbarLayout mClsToolbar;
    private int mutedColor = R.attr.colorPrimary;
    public TextView tvContent;
    private final String TAG = getClass().getSimpleName();
    String content, title, linkArticle, linkImage, category, contentShare, typeIntent;
    private Intent intent;
    private ProgressBar progressBar;
    private FloatingActionButton flbtnSave, flbtnShare;
    private DataHandlerSaveContent db;
    private Spannable htmlSpan;
    private String contentTemp;
    private LinearLayout ll;
    private Button btnConnect;
    private NestedScrollView nestscv;
    private FloatingActionMenu flbtnMenu;
    private Spannable processedText;
    private Handler handler;

    @Override
    public void setContentView() {
        overridePendingTransition(R.anim.activity_open_translate, R.anim.activity_close_scale);
        setContentView(R.layout.activity_viewing);
    }

    @Override
    public void init() {
        intent = getIntent();
        typeIntent = intent.getStringExtra(RecyclerCunghoangdaoAdapter.TYPE_OFFLINE);
        linkArticle = intent.getStringExtra(RecyclerCunghoangdaoAdapter.LINK);
        title = intent.getStringExtra(RecyclerCunghoangdaoAdapter.TITLE);
        linkImage = intent.getStringExtra(RecyclerCunghoangdaoAdapter.LINK_IMAGE);
        category = intent.getStringExtra(RecyclerCunghoangdaoAdapter.CATEGORY);


        mToolbar = (Toolbar) findViewById(R.id.activity_viewing_toolbar);
        mToolbar.setTitle(title);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        progressBar = (ProgressBar) findViewById(R.id.activity_viewing_progress);
        tvContent = (TextView) findViewById(R.id.activity_viewing_tvContent);
        mClsToolbar = (CollapsingToolbarLayout) findViewById(R.id.activity_viewing_cls_toolbar);
        flbtnSave = (FloatingActionButton) findViewById(R.id.activity_viewing_flbtnSave);
        flbtnShare = (FloatingActionButton) findViewById(R.id.activity_viewing_flbtnShare);
        progressBar.setVisibility(View.VISIBLE);
        ll = (LinearLayout) findViewById(R.id.activity_viewing_ll);
        btnConnect = (Button) findViewById(R.id.activity_viewing_btnConnect);
        nestscv = (NestedScrollView) findViewById(R.id.activity_viewing_nestscv);
        flbtnMenu = (FloatingActionMenu) findViewById(R.id.activity_viewing_flbtn_menu);

        handler = new Handler();
        db = new DataHandlerSaveContent(this);
        setDynamicColor();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                flbtnMenu.showMenu(true);
            }
        }, 300);
    }

    @Override
    public void setValue(Bundle savedInstanceState) {
        setContent();
    }

    public void setContent() {
        if (linkArticle != null) {
            new JsoupParseContent(this, linkArticle, this).execute();
        } else {
            content = intent.getStringExtra(RecyclerCunghoangdaoAdapter.CONTENT);
            setContent(content);
        }

    }

    @Override
    public void setEvent() {
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        flbtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new CovertBitmapToByte(getApplicationContext(), ViewingActivity.this).execute(linkImage);
                flbtnMenu.hideMenu(true);
            }
        });
        flbtnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setupFacebookShareIntent();
                flbtnMenu.hideMenu(true);
            }
        });
        btnConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContent();
                if (ConnectionUltils.isConnected(getApplicationContext())) {
                    ll.setVisibility(View.GONE);

                }
            }
        });
        nestscv.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY > oldScrollY) {
                    flbtnMenu.hideMenu(true);
                } else if (scrollY < oldScrollY) {
                    flbtnMenu.showMenu(true);
                }
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

    @Override
    public void onReturnContent(String contentParsered) {
        setContent(contentParsered);
    }

    public void setContent(final String content) {
        if (ConnectionUltils.isConnected(this)) {
            if (typeIntent != null) {
                    tvContent.setText(content);
                    ll.setVisibility(View.GONE);
                    progressBar.setVisibility(View.INVISIBLE);
                } else {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        URLImageParser p = new URLImageParser(tvContent, getApplicationContext());
                        htmlSpan = (Spannable) Html.fromHtml(content, p, null);
                        tvContent.setText(htmlSpan);
                        tvContent.setMovementMethod(new ScrollingMovementMethod());
                        processedText = removeUnderlines(htmlSpan);
                        tvContent.setText(processedText);
                        contentShare = htmlSpan.toString().substring(0, 150);
                        custfont(getApplicationContext(), tvContent);
                        progressBar.setVisibility(View.INVISIBLE);
                        contentTemp = content;
                        }
                },1000);

                }

            } else {
                if (typeIntent != null) {
                    tvContent.setText(Html.fromHtml(content));
                    ll.setVisibility(View.GONE);
                    progressBar.setVisibility(View.INVISIBLE);
                } else {
                    ll.setVisibility(View.VISIBLE);
                }
            }

        }

    

        @Override
        public void onReturnBimapFromByte ( byte[] image){
            if (content != null) {
                db.addArticle(new Article(title, category, image, processedText.toString()));
            } else {
                db.addArticle(new Article(title, category, image, contentTemp));
            }

        }

    private void shareTextUrl() {
        Intent share = new Intent(android.content.Intent.ACTION_SEND);
        share.setType("text/plain");
        share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        share.putExtra(Intent.EXTRA_SUBJECT, title);
        share.putExtra(Intent.EXTRA_TEXT, Html.fromHtml(content.substring(1, 20)));

        startActivity(Intent.createChooser(share, "Chia sẽ bài viết này"));
    }

    public void setupFacebookShareIntent() {
        ShareDialog shareDialog;
        FacebookSdk.sdkInitialize(getApplicationContext());
        shareDialog = new ShareDialog(this);

        ShareLinkContent linkContent = new ShareLinkContent.Builder()
                .setContentUrl(Uri.parse("play.google.com"))
                .setContentTitle(title)
                .setContentDescription(contentShare)
                .setImageUrl(Uri.parse(linkImage))
                .build();

        shareDialog.show(linkContent);
    }


}
