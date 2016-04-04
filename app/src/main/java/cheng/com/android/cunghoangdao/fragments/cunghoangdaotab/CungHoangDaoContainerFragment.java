package cheng.com.android.cunghoangdao.fragments.cunghoangdaotab;

import cheng.com.android.cunghoangdao.R;
import cheng.com.android.cunghoangdao.fragments.BaseContainerFragment;

/**
 * Created by Welcome on 2/8/2016.
 */
public class CungHoangDaoContainerFragment extends BaseContainerFragment {
    @Override
    public void init() {
        if (getChildFragmentManager().getBackStackEntryCount() == 0) {
            replaceFragment(new CungHoangDaoTabFragment(), true);
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
