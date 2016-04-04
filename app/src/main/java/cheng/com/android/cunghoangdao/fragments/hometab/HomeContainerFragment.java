package cheng.com.android.cunghoangdao.fragments.hometab;

import cheng.com.android.cunghoangdao.R;
import cheng.com.android.cunghoangdao.fragments.BaseContainerFragment;

/**
 * Created by Welcome on 2/8/2016.
 */
public class HomeContainerFragment extends BaseContainerFragment {
    @Override
    public void init() {
        if (getChildFragmentManager().getBackStackEntryCount() == 0) {
            replaceFragment(new HomeTabFragment(), true);
        }
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_container;
    }

    @Override
    protected int getContainerId() {
        return R.id.container_framelayout;
    }
}
