package cheng.com.android.cunghoangdao.activities.intro;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jpardogo.android.googleprogressbar.library.FoldingCirclesDrawable;

import cheng.com.android.cunghoangdao.R;
import cheng.com.android.cunghoangdao.activities.maincreen.MainScreenActivity;
import cheng.com.android.cunghoangdao.ultils.CustomFont;


public class LaunchScreenActivity extends AppCompatActivity {

    private static final int SPLASH_TIME = 10000;
    private ProgressBar google_progress;
    private TextView tvDecription, tvSologan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        setContentView(R.layout.activity_launch_screen);
        tvDecription = (TextView) findViewById(R.id.tvDecription);
        tvSologan = (TextView) findViewById(R.id.tvSologan);
        CustomFont.custfont(getApplicationContext(), tvDecription,"fonts/Nabila.ttf");
        CustomFont.custfont(getApplicationContext(), tvSologan,"fonts/Gotham-Thin.ttf");
        google_progress = (ProgressBar) findViewById(R.id.google_progress);
        new BackgroundTask().execute();
    }


    private class BackgroundTask extends AsyncTask {
        Intent intent;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            intent = new Intent(LaunchScreenActivity.this, MainScreenActivity.class);
        }

        @Override
        protected Object doInBackground(Object[] params) {

            try {
                Thread.sleep(SPLASH_TIME);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Rect bounds = google_progress.getIndeterminateDrawable().getBounds();
        google_progress.setIndeterminateDrawable(getProgressDrawable());
        google_progress.getIndeterminateDrawable().setBounds(bounds);
    }
    private Drawable getProgressDrawable() {
        Drawable progressDrawable = null;

                progressDrawable = new FoldingCirclesDrawable.Builder(this)
                        .colors(getProgressDrawableColors())
                        .build();


        return progressDrawable;
    }
    private int[] getProgressDrawableColors() {
        int[] colors = new int[4];
        colors[0] = ContextCompat.getColor(this, R.color.colorPrimary);
        colors[1] = ContextCompat.getColor(this, R.color.pink_pressed);
        colors[2] = ContextCompat.getColor(this, R.color.bright_blue);
        colors[3] = ContextCompat.getColor(this,R.color.navigationBarColor);
        return colors;
    }
}
