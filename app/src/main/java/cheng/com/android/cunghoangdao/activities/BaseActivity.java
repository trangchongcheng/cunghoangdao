package cheng.com.android.cunghoangdao.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Welcome on 1/16/2016.
 */
public abstract class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView();
        init();
        setValue(savedInstanceState);
        setEvent();
    }
    public abstract void setContentView();
    public abstract void init();
    public abstract void setValue(Bundle savedInstanceState);
    public abstract void setEvent();

}
