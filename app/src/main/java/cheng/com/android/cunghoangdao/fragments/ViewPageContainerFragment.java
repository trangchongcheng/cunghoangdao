package cheng.com.android.cunghoangdao.fragments;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.Log;

import cheng.com.android.cunghoangdao.R;
import cheng.com.android.cunghoangdao.activities.maincreen.ViewPagerAdapter;
import cheng.com.android.cunghoangdao.fragments.congiap.ConGiapContainerFragment;
import cheng.com.android.cunghoangdao.fragments.cunghoangdaotab.CungHoangDaoContainerFragment;
import cheng.com.android.cunghoangdao.fragments.hometab.HomeContainerFragment;
import cheng.com.android.cunghoangdao.fragments.tuvi.TuViContainerFragment;

/**
 * Created by Welcome on 3/29/2016.
 */
public class ViewPageContainerFragment extends BaseFragment {
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private final String TAG = getClass().getSimpleName();
    private  ViewPagerAdapter adapter;
    private boolean[] isTabsSelected = new boolean[5];
    private OnTabChangeListener mOnTabChangeListener;
    private int countSelectedTab = 1;


    @Override
    public void init() {
        tabLayout =  (TabLayout)getView().findViewById(R.id.tab_layout);
        viewPager =  (ViewPager) getView().findViewById(R.id.view_pager);
        adapter = new ViewPagerAdapter(getFragmentManager());
    }

    @Override
    public void setEvent() {
        setupViewPager(viewPager);
      //  viewPager.setOffscreenPageLimit(1);
        isTabsSelected[0] = true;
        tabLayout.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                Log.d(TAG, "onPageSelected: "+position);
                if (position != 0) {
                    if (!isTabsSelected[position]
                            && adapter.getItem(position).getChildFragmentManager().getBackStackEntryCount() == 1
                            && getCurrentFragment(position) instanceof OnTabChangeListener) {
                        mOnTabChangeListener = (OnTabChangeListener) getCurrentFragment(position);
                        mOnTabChangeListener.onTabSelected();
                    }
                } else if (adapter.getItem(0).getChildFragmentManager().getBackStackEntryCount() == 1) {
                    Log.d(TAG, "position = 0");
                }
                if (!isTabsSelected[position]) {
                    isTabsSelected[position] = true;
                    countSelectedTab++;
                    viewPager.setOffscreenPageLimit(countSelectedTab);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }



    @Override
    public void setValue() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_viewpage;
    }
    private void setupViewPager(ViewPager viewPager) {
        adapter.addFrag(new HomeContainerFragment(), "Home");
        adapter.addFrag(new CungHoangDaoContainerFragment(), "Cung Hoàng Đạo");
        adapter.addFrag(new ConGiapContainerFragment(), "12 Con Giáp");
        adapter.addFrag(new TuViContainerFragment(), "Tử Vi");
        viewPager.setAdapter(adapter);
    }
    public Fragment getCurrentFragment(int position) {
        FragmentManager fm = getChildFragmentManager();
        return fm.findFragmentById(R.id.container_framelayout);
    }
    public interface OnTabChangeListener {
        void onTabSelected();
    }
}
