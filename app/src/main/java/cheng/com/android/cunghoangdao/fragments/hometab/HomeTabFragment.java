package cheng.com.android.cunghoangdao.fragments.hometab;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.alorma.timeline.RoundTimelineView;
import com.bumptech.glide.Glide;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;

import java.util.ArrayList;

import cheng.com.android.cunghoangdao.R;
import cheng.com.android.cunghoangdao.activities.category.CategoryActivity;
import cheng.com.android.cunghoangdao.activities.viewing.ViewingActivity;
import cheng.com.android.cunghoangdao.adapters.cunghoangdaotab.RecyclerCunghoangdaoAdapter;
import cheng.com.android.cunghoangdao.adapters.hometab.LatestPostsAdapter;
import cheng.com.android.cunghoangdao.adapters.hometab.RecyclerNewsFeedAdapter;
import cheng.com.android.cunghoangdao.adapters.hometab.RecyclerViewIconCungHoangDaoAdapter;
import cheng.com.android.cunghoangdao.common.UrlGetXml;
import cheng.com.android.cunghoangdao.fragments.BaseFragment;
import cheng.com.android.cunghoangdao.model.Cunghoangdao;
import cheng.com.android.cunghoangdao.model.hometab.NewsFeed;
import cheng.com.android.cunghoangdao.services.BaseAsyntask;

/**
 * Created by Welcome on 1/19/2016.
 */
public class HomeTabFragment extends BaseFragment implements
        BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener, BaseAsyntask.OnReturnNewsFeedList
        , LatestPostsAdapter.OnItemClickListener, RecyclerNewsFeedAdapter.OnClickItemNewsFeed,
        RecyclerViewIconCungHoangDaoAdapter.OnItemClickIconCungHoangDao,
        View.OnClickListener {
    private final String TAG = getClass().getSimpleName();
    public final static String URL_CATEGORY = "url_category";
    public final static String TITLE_CATEGORY = "title_category";
    private RecyclerView mRecyclerViewSlider, rvNewsFeed;
    private ProgressBar progressBar, progressBarNews;
    RecyclerViewIconCungHoangDaoAdapter custom_addapter;
    ArrayList<Cunghoangdao> arrayCunghoangdao;
    ListView lvNews;
    private LatestPostsAdapter mTimeLineAdapter;
    private SliderLayout mDemoSlider;
    private RoundTimelineView tlTi, tlSuu, tlDan, tlMeo, tlThin, tlTy, tlNgo, tlMui, tlThan, tlDau, tlTuat, tlHoi;
    private Button btnConnect;
    private LinearLayout ll;
    private RelativeLayout rl;
    private int state = 0;

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }


    @Override
    public void init() {
        Log.d(TAG, "init +");
        lvNews = (ListView) getView().findViewById(R.id.content_main_lvNews);
        mRecyclerViewSlider = (RecyclerView) getView().findViewById(R.id.cunghoangdao_fragment_recyclerSlide);
        rvNewsFeed = (RecyclerView) getView().findViewById(R.id.fragment_hometab_rvNewFeed);
        mRecyclerViewSlider.setHasFixedSize(true);
        rvNewsFeed.setHasFixedSize(true);
        mRecyclerViewSlider.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        rvNewsFeed.setLayoutManager(new LinearLayoutManager(getContext()));
        progressBar = (ProgressBar) getView().findViewById(R.id.fragment_hometab_progressBar);
        progressBarNews = (ProgressBar) getView().findViewById(R.id.fragment_hometab_progressBarNews);
        btnConnect = (Button) getView().findViewById(R.id.fragment_hometab_btnConnect);
        ll = (LinearLayout) getView().findViewById(R.id.fragment_hometab_ll);
        rl = (RelativeLayout) getView().findViewById(R.id.fragment_hometab_rl);
        initNews();
        new BaseAsyntask(getContext(), UrlGetXml.HOME_TAB, this).execute();
        initSlideCungHoangDao();

    }

    @Override
    public void setEvent() {
        tlTi.setOnClickListener(this);
        tlSuu.setOnClickListener(this);
        tlDan.setOnClickListener(this);
        tlThin.setOnClickListener(this);
        tlMeo.setOnClickListener(this);
        tlTy.setOnClickListener(this);
        tlNgo.setOnClickListener(this);
        tlMui.setOnClickListener(this);
        tlThan.setOnClickListener(this);
        tlDau.setOnClickListener(this);
        tlTuat.setOnClickListener(this);
        tlHoi.setOnClickListener(this);
        btnConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new BaseAsyntask(getContext(), UrlGetXml.HOME_TAB, HomeTabFragment.this).execute();
                progressBar.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void onStop() {
//        mDemoSlider.stopAutoCycle();
        super.onStop();
    }

    private void initSlideCungHoangDao() {
        arrayCunghoangdao = new ArrayList<>();
        Cunghoangdao cunghoangdao = null;
        for (int i = 0; i < getResources().getStringArray(R.array.cunghoangdaoname).length; i++) {
            cunghoangdao = new Cunghoangdao(getResources().getStringArray(R.array.cunghoangdaoname)[i],
                    getResources().obtainTypedArray(R.array.cunghoangdaoimage).getResourceId(i, -1),
                    getResources().getStringArray(R.array.cunghoangdaodate)[i]);
            arrayCunghoangdao.add(cunghoangdao);
        }
        custom_addapter = new RecyclerViewIconCungHoangDaoAdapter(getContext(), arrayCunghoangdao, this);
        mRecyclerViewSlider.setAdapter(custom_addapter);
        custom_addapter.notifyDataSetChanged();
    }

    @Override
    public void setValue() {
    }


    public void initNews() {
        tlTi = (RoundTimelineView) getView().findViewById(R.id.fragment_hometab_tlTi);
        tlSuu = (RoundTimelineView) getView().findViewById(R.id.fragment_hometab_tlSuu);
        tlDan = (RoundTimelineView) getView().findViewById(R.id.fragment_hometab_tlDan);
        tlMeo = (RoundTimelineView) getView().findViewById(R.id.fragment_hometab_tlMeo);
        tlThin = (RoundTimelineView) getView().findViewById(R.id.fragment_hometab_tlThin);
        tlTy = (RoundTimelineView) getView().findViewById(R.id.fragment_hometab_tlTy);
        tlNgo = (RoundTimelineView) getView().findViewById(R.id.fragment_hometab_tlNgo);
        tlMui = (RoundTimelineView) getView().findViewById(R.id.fragment_hometab_tlMui);
        tlThan = (RoundTimelineView) getView().findViewById(R.id.fragment_hometab_tlThan);
        tlDau = (RoundTimelineView) getView().findViewById(R.id.fragment_hometab_tlDau);
        tlTuat = (RoundTimelineView) getView().findViewById(R.id.fragment_hometab_tlTuat);
        tlHoi = (RoundTimelineView) getView().findViewById(R.id.fragment_hometab_tlHoi);

        Glide.with(this).load(R.drawable.ic_12_con_giap_ti).into(tlTi);

        Glide.with(this).load(R.drawable.ic_12_con_giap_suu).into(tlSuu);

        Glide.with(this).load(R.drawable.ic_12_con_giap_dan).into(tlDan);

        Glide.with(this).load(R.drawable.ic_12_con_giap_meo).into(tlMeo);

        Glide.with(this).load(R.drawable.ic_12_con_giap_thin).into(tlThin);

        Glide.with(this).load(R.drawable.ic_12_con_giap_ty).into(tlTy);

        Glide.with(this).load(R.drawable.ic_12_con_giap_ngo).into(tlNgo);

        Glide.with(this).load(R.drawable.ic_12_con_giap_mui).into(tlMui);

        Glide.with(this).load(R.drawable.ic_12_con_giap_than).into(tlThan);

        Glide.with(this).load(R.drawable.ic_12_con_giap_dau).into(tlDau);

        Glide.with(this).load(R.drawable.ic_12_con_giap_tuat).into(tlTuat);

        Glide.with(this).load(R.drawable.ic_12_con_giap_hoi).into(tlHoi);

    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_hometab;
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onSliderClick(BaseSliderView slider) {
        boolean ischeck = true;

    }

    @Override
    public void OnNewsFeedListSend(ArrayList<NewsFeed> arr) {
        ArrayList<NewsFeed> arrayLastPost = new ArrayList<>();
        ArrayList<NewsFeed> arrayNewsFeed = new ArrayList<>();
        if (arr != null) {
            ll.setVisibility(View.INVISIBLE);
            rl.setVisibility(View.VISIBLE);
            for (int i = 0; i < 4; i++) {
                arrayLastPost.add(arr.get(i));
            }
            for (int i = 4; i < arr.size(); i++) {
                arrayNewsFeed.add(arr.get(i));
            }
            mTimeLineAdapter = new LatestPostsAdapter(getContext(), arrayLastPost, this);

            lvNews.setAdapter(mTimeLineAdapter);
            progressBar.setVisibility(View.INVISIBLE);

            RecyclerNewsFeedAdapter adapter = new RecyclerNewsFeedAdapter(getContext(), arrayNewsFeed, this);
            rvNewsFeed.setAdapter(adapter);
            progressBarNews.setVisibility(View.INVISIBLE);
        } else {
            progressBar.setVisibility(View.INVISIBLE);
            ll.setVisibility(View.VISIBLE);
            rl.setVisibility(View.INVISIBLE);

        }
    }


    @Override
    public void onItemClick(View view, int position, String content, String title, String linkImage) {
        putIntent(content, title, linkImage);
    }

    public void replaceFragment(Fragment fragment, boolean isAddToBackStack, boolean isHaveAnimation) {
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        if (isHaveAnimation) {
            transaction.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_right
                    , R.anim.slide_in_right, R.anim.slide_out_left);
        }

        transaction.replace(R.id.activity_maincree_frame_container, fragment);
        if (isAddToBackStack) {
            transaction.addToBackStack(null);
        }
        transaction.commit();
    }


    @Override
    public void onClickItemNewsFeed(View view, int position, String content, String title, String linkImage) {
        putIntent(content, title, linkImage);
    }

    public void putIntent(String content, String title, String linkImage) {
        Intent intent = new Intent(context, ViewingActivity.class);
        intent.putExtra(RecyclerCunghoangdaoAdapter.CONTENT, content);
        intent.putExtra(RecyclerCunghoangdaoAdapter.LINK_IMAGE, linkImage);
        intent.putExtra(RecyclerCunghoangdaoAdapter.TITLE, title);
        intent.putExtra(RecyclerCunghoangdaoAdapter.CATEGORY, "Tin Mới");
        startActivity(intent);
    }

    Intent intent;

    @Override
    public void onClick(View v) {
        intent = new Intent(context, CategoryActivity.class);
        switch (v.getId()) {
            case R.id.fragment_hometab_tlTi:
                intent.putExtra(URL_CATEGORY, UrlGetXml.TUOI_TI);
                intent.putExtra(TITLE_CATEGORY, getResources().getString(R.string.tuoi_ti));
                break;
            case R.id.fragment_hometab_tlSuu:
                intent.putExtra(URL_CATEGORY, UrlGetXml.TUOI_SUU);
                intent.putExtra(TITLE_CATEGORY, getResources().getString(R.string.tuoi_suu));
                break;
            case R.id.fragment_hometab_tlDan:
                intent.putExtra(URL_CATEGORY, UrlGetXml.TUOI_DAN);
                intent.putExtra(TITLE_CATEGORY, getResources().getString(R.string.tuoi_dan));
                break;
            case R.id.fragment_hometab_tlMeo:
                intent.putExtra(URL_CATEGORY, UrlGetXml.TUOI_MAO);
                intent.putExtra(TITLE_CATEGORY, getResources().getString(R.string.tuoi_mao));
                break;
            case R.id.fragment_hometab_tlThin:
                intent.putExtra(URL_CATEGORY, UrlGetXml.TUOI_THIN);
                intent.putExtra(TITLE_CATEGORY, getResources().getString(R.string.tuoi_thin));
                break;
            case R.id.fragment_hometab_tlTy:
                intent.putExtra(URL_CATEGORY, UrlGetXml.TUOI_TY);
                intent.putExtra(TITLE_CATEGORY, getResources().getString(R.string.tuoi_ty));
                break;
            case R.id.fragment_hometab_tlNgo:
                intent.putExtra(URL_CATEGORY, UrlGetXml.TUOI_NGO);
                intent.putExtra(TITLE_CATEGORY, getResources().getString(R.string.tuoi_ngo));
                break;
            case R.id.fragment_hometab_tlMui:
                intent.putExtra(URL_CATEGORY, UrlGetXml.TUOI_MUI);
                intent.putExtra(TITLE_CATEGORY, getResources().getString(R.string.tuoi_mui));
                break;
            case R.id.fragment_hometab_tlThan:
                intent.putExtra(URL_CATEGORY, UrlGetXml.TUOI_THAN);
                intent.putExtra(TITLE_CATEGORY, getResources().getString(R.string.tuoi_dau));
                break;
            case R.id.fragment_hometab_tlDau:
                intent.putExtra(URL_CATEGORY, UrlGetXml.TUOI_DAU);
                intent.putExtra(TITLE_CATEGORY, getResources().getString(R.string.tuoi_dau));
                break;
            case R.id.fragment_hometab_tlTuat:
                intent.putExtra(URL_CATEGORY, UrlGetXml.TUOI_TUAT);
                intent.putExtra(TITLE_CATEGORY, getResources().getString(R.string.tuoi_tuat));
                break;
            case R.id.fragment_hometab_tlHoi:
                intent.putExtra(URL_CATEGORY, UrlGetXml.TUOI_HOI);
                intent.putExtra(TITLE_CATEGORY, getResources().getString(R.string.tuoi_hoi));
                break;
            default:
                break;
        }
        startActivity(intent);
    }

    @Override
    public void onItemClickIconCungHoangDao(View v, int position, String category) {
        intent = new Intent(context, CategoryActivity.class);
        switch (position) {
            case 0:
                intent.putExtra(URL_CATEGORY, UrlGetXml.CUNG_BACH_DUONG);
                intent.putExtra(TITLE_CATEGORY, category);
                break;
            case 1:
                intent.putExtra(URL_CATEGORY, UrlGetXml.CUNG_KIM_NGUU);
                intent.putExtra(TITLE_CATEGORY, category);
                break;
            case 2:
                intent.putExtra(URL_CATEGORY, UrlGetXml.CUNG_SONG_TU);
                intent.putExtra(TITLE_CATEGORY, category);
                break;
            case 3:
                intent.putExtra(URL_CATEGORY, UrlGetXml.CUNG_CU_GIAI);
                intent.putExtra(TITLE_CATEGORY, category);
                break;
            case 4:
                intent.putExtra(URL_CATEGORY, UrlGetXml.CUNG_SU_TU);
                intent.putExtra(TITLE_CATEGORY, category);
                break;
            case 5:
                intent.putExtra(URL_CATEGORY, UrlGetXml.CUNG_XU_NU);
                intent.putExtra(TITLE_CATEGORY, category);
                break;
            case 6:
                intent.putExtra(URL_CATEGORY, UrlGetXml.CUNG_THIEN_BINH);
                intent.putExtra(TITLE_CATEGORY, category);
                break;
            case 7:
                intent.putExtra(URL_CATEGORY, UrlGetXml.CUNG_BO_CAP);
                intent.putExtra(TITLE_CATEGORY, category);
                break;
            case 8:
                intent.putExtra(URL_CATEGORY, UrlGetXml.CUNG_NHAN_MA);
                intent.putExtra(TITLE_CATEGORY, category);
                break;
            case 9:
                intent.putExtra(URL_CATEGORY, UrlGetXml.CUNG_MA_KET);
                intent.putExtra(TITLE_CATEGORY, category);
                break;
            case 10:
                intent.putExtra(URL_CATEGORY, UrlGetXml.CUNG_BAO_BINH);
                intent.putExtra(TITLE_CATEGORY, category);
                break;
            case 11:
                intent.putExtra(URL_CATEGORY, UrlGetXml.CUNG_SONG_NGU);
                intent.putExtra(TITLE_CATEGORY, category);
                break;
            default:
                break;
        }
        startActivity(intent);
    }


}
