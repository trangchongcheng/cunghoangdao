package cheng.com.android.cunghoangdao.activities.intro;

import android.content.Intent;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jpardogo.android.googleprogressbar.library.FoldingCirclesDrawable;

import java.util.Random;

import cheng.com.android.cunghoangdao.R;
import cheng.com.android.cunghoangdao.activities.maincreen.MainScreenActivity;
import cheng.com.android.cunghoangdao.ultils.CustomFont;


public class LaunchScreenActivity extends AppCompatActivity {

<<<<<<< HEAD
    private static final int SPLASH_TIME = 2000;
=======
    private static final int SPLASH_TIME = 3000;
>>>>>>> daf69e5129a81981f8eceb4d59207ebd2a080f56
    private ProgressBar google_progress;
    private TextView tvDecription, tvSologan;
    private RelativeLayout rl;
    Random r = new Random();
    int random;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch_screen);
        rl = (RelativeLayout) findViewById(R.id.rl);
        random = r.nextInt(4);
        int[] color = getProgressDrawableColors();
        rl.setBackgroundColor(color[random]);
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
        colors[1] = ContextCompat.getColor(this, R.color.dark_moderate);
        colors[2] = ContextCompat.getColor(this, R.color.bright_blue);
        colors[3] = ContextCompat.getColor(this,R.color.vivid_yellow);
        return colors;
    }
}
