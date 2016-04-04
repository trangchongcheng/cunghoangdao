package cheng.com.android.cunghoangdao.fragments;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;

import cheng.com.android.cunghoangdao.R;
import cheng.com.android.cunghoangdao.activities.maincreen.ViewPagerAdapter;
import cheng.com.android.cunghoangdao.fragments.congiap.ConGiapContainerFragment;
import cheng.com.android.cunghoangdao.fragments.cunghoangdaotab.CungHoangDaoTabFragment;
import cheng.com.android.cunghoangdao.fragments.hometab.HomeContainerFragment;
import cheng.com.android.cunghoangdao.fragments.tuvi.TuViTabFragment;

/**
 * Created by Welcome on 3/29/2016.
 */
public class ViewPageContainerFragment extends BaseFragment {
    private ViewPager viewPager;
    private TabLayout tabLayout;
    @Override
    public void init() {
        tabLayout =  (TabLayout)getView().findViewById(R.id.tab_layout);
        viewPager =  (ViewPager) getView().findViewById(R.id.view_pager);
    }

    @Override
    public void setEvent() {
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public void setValue() {

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_viewpage;
    }
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getFragmentManager());
        adapter.addFrag(new HomeContainerFragment(), "Home");
        adapter.addFrag(new CungHoangDaoTabFragment(), "Cung Hoàng Đạo");
        adapter.addFrag(new ConGiapContainerFragment(), "12 Con Giáp");
        adapter.addFrag(new TuViTabFragment(), "Tử Vi");
        viewPager.setAdapter(adapter);
    }
}
