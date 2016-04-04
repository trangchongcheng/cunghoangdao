package cheng.com.android.cunghoangdao.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseContainerFragment extends Fragment {

    private boolean mIsViewInited;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (!mIsViewInited) {
            init();
            mIsViewInited = true;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(getLayoutId(), container, false);
    }

    public abstract void init();

    public abstract int getLayoutId();

    protected abstract int getContainerId();

    public void replaceFragment(Fragment fragment, boolean addToBackStack) {
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        String tag = getContainerId()+":" + getChildFragmentManager().getBackStackEntryCount();
        Log.d("replaceFragment", "replaceFragment: "+tag);
        if( addToBackStack ) {
            transaction.addToBackStack(tag);
        }
        if ( getChildFragmentManager().getBackStackEntryCount() > 0 ) {
            transaction.hide(getChildFragmentManager().findFragmentById(getContainerId()));
        }
        transaction.add(getContainerId(), fragment, tag);
        transaction.commit();
        getChildFragmentManager().executePendingTransactions();
    }

    public boolean popFragment() {
        boolean isPop = false;

        if ( getChildFragmentManager().getBackStackEntryCount() > 0 ) {
            isPop = true;
            getChildFragmentManager().popBackStack();
            getChildFragmentManager().executePendingTransactions();
        }
        return isPop;
    }

}
