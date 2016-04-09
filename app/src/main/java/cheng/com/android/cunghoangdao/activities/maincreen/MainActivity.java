package cheng.com.android.cunghoangdao.activities.maincreen;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;
import com.getbase.floatingactionbutton.FloatingActionsMenu;

import cheng.com.android.cunghoangdao.R;
import cheng.com.android.cunghoangdao.activities.BaseActivity;
import cheng.com.android.cunghoangdao.fragments.hometab.HomeContainerFragment;
import cheng.com.android.cunghoangdao.model.Common;
import de.hdodenhof.circleimageview.CircleImageView;

import static cheng.com.android.cunghoangdao.R.string.openDrawer;

/**
 * Created by Welcome on 1/31/2016.
 */
public class MainActivity extends BaseActivity {
    private Toolbar mToolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private CircleImageView profile_image;
    private TextView tvName;
    private View headerView;
    private FloatingActionsMenu floatButton;
    private FloatingActionButton floatButton_item_edit,floatButton_item_detail;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private CollapsingToolbarLayout mCollapsingToolbarLayout;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_main);
    }

    @Override
    public void init() {

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        navigationView = (NavigationView) findViewById(R.id.navigationview);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        headerView = navigationView.getHeaderView(0);
        tvName = (TextView) headerView.findViewById(R.id.tvName);
        profile_image = (CircleImageView)headerView.findViewById(R.id.profile_image);

        viewPager =  (ViewPager) findViewById(R.id.view_pager);

        tabLayout =  (TabLayout) findViewById(R.id.tab_layout);

        mCollapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar_layout);

        floatButton = (FloatingActionsMenu) headerView.findViewById(R.id.float_button);
        floatButton_item_edit = new FloatingActionButton(this);
        floatButton_item_edit.setTitle("Thay đổi thông tin");
        floatButton_item_edit.setSize(1);
        floatButton.addButton(floatButton_item_edit);

        floatButton_item_detail = new FloatingActionButton(this);
        floatButton_item_detail.setTitle("Chi tiết về bạn");
        floatButton_item_detail.setSize(1);
        floatButton.addButton(floatButton_item_detail);
    }

    @Override
    public void setValue(Bundle savedInstanceState) {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
        mCollapsingToolbarLayout.setTitle("Cung Hoàng Đạo");
        tvName.setText("Hi " + Common.user.getUserName() + " !");
        profile_image.setImageResource(Common.getImgAvatar());
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                if (menuItem.isChecked()) menuItem.setChecked(false);
                else menuItem.setChecked(true);

                //Closing drawer on item click
                drawerLayout.closeDrawers();

                //Check to see which item was being clicked and perform appropriate action
                switch (menuItem.getItemId()) {


                    //Replacing the main content with ContentFragment Which is our Inbox View;
                    case R.id.inbox:
                        Toast.makeText(getApplicationContext(), "Inbox Selected", Toast.LENGTH_SHORT).show();
                        return true;

                    // For rest of the options we just show a toast on click

                    case R.id.starred:
                        Toast.makeText(getApplicationContext(), "Stared Selected", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.sent_mail:
                        Toast.makeText(getApplicationContext(), "Send Selected", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.drafts:
                        Toast.makeText(getApplicationContext(), "Drafts Selected", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.allmail:
                        Toast.makeText(getApplicationContext(), "All Mail Selected", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.trash:
                        Toast.makeText(getApplicationContext(), "Trash Selected", Toast.LENGTH_SHORT).show();
                        return true;
                    case R.id.spam:
                        Toast.makeText(getApplicationContext(), "Spam Selected", Toast.LENGTH_SHORT).show();
                        return true;
                    default:
                        Toast.makeText(getApplicationContext(), "Somethings Wrong", Toast.LENGTH_SHORT).show();
                        return true;


                }
            }
        });

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,mToolbar,
                openDrawer,R.string.closeDrawer){
            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank

                super.onDrawerOpened(drawerView);
            }
        };
        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }

    @Override
    public void setEvent() {

    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new HomeContainerFragment(), "Home");
        adapter.addFrag(new HomeContainerFragment(), "Cung Hoàng Đạo");
        adapter.addFrag(new HomeContainerFragment(), "12 Con Giáp");
        adapter.addFrag(new HomeContainerFragment(), "Tử Vi");
        viewPager.setAdapter(adapter);
    }



}
