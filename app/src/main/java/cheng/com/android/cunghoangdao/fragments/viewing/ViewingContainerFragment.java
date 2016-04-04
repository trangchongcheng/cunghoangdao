package cheng.com.android.cunghoangdao.fragments.viewing;

import cheng.com.android.cunghoangdao.R;
import cheng.com.android.cunghoangdao.fragments.BaseContainerFragment;

/**
 * Created by Welcome on 3/28/2016.
 */
public class ViewingContainerFragment extends BaseContainerFragment {
    @Override
    public void init() {
//        if (getChildFragmentManager().getBackStackEntryCount() == 0) {
//            replaceFragment(new ViewingFragment(), true);
//        }

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
