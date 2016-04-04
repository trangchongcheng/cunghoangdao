package cheng.com.android.cunghoangdao.activities.startscreen;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import cheng.com.android.cunghoangdao.R;
import cheng.com.android.cunghoangdao.activities.BaseActivity;
import cheng.com.android.cunghoangdao.activities.maincreen.MainScreenActivity;
import cheng.com.android.cunghoangdao.ultils.SharedPreferencesUser;

/**
 * Created by Welcome on 1/19/2016.
 */
public class StartActivity extends BaseActivity implements LoginFragment.OnLoginFragmentListener{
    @Override
    public void setContentView() {
        setContentView(R.layout.activity_start);
    }

    @Override
    public void init() {
        if(SharedPreferencesUser.getInstance(this).isLogin()){
            SharedPreferencesUser.getInstance(this).getInfo();
            Intent intent = new Intent(getApplicationContext(), MainScreenActivity.class);
            startActivity(intent);
            finish();
        }else {
            replaceFragment(new LoginFragment(),false,false);
        }
    }

    @Override
    public void setValue(Bundle savedInstanceState) {
    }

    @Override
    public void setEvent() {

    }
    public void replaceFragment(Fragment fragment, boolean isAddToBackStack, boolean isHaveAnimation) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (isHaveAnimation) {
            transaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right
                    , R.anim.slide_in_right, R.anim.slide_out_left);
        }

        transaction.replace(R.id.frangment_container, fragment);
        if ( isAddToBackStack ) {
            transaction.addToBackStack(null);
        }
        transaction.commit();
    }

    @Override
    public void onSignUpSuccess() {
        Intent interested = new Intent(getApplicationContext(), MainScreenActivity.class);
        startActivity(interested);
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        finish();
    }
}
