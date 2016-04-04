package cheng.com.android.cunghoangdao.fragments.hometab;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;

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
import cheng.com.android.cunghoangdao.adapters.hometab.RecyclerViewAdapter;
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
        View.OnClickListener {
    private final String TAG = getClass().getSimpleName();
    public final static String URL_CATEGORY = "url_category";
    private RecyclerView mRecyclerViewSlider, rvNewsFeed;
    private ProgressBar progressBar, progressBarNews;
    RecyclerViewAdapter custom_addapter;
    ArrayList<Cunghoangdao> arrayCunghoangdao;
    ListView lvNews;
    private LatestPostsAdapter mTimeLineAdapter;
    private SliderLayout mDemoSlider;
    private RoundTimelineView tlTi, tlSuu, tlDan, tlMeo, tlThin, tlTy, tlNgo, tlMui, tlThan, tlDau, tlTuat, tlHoi;

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }


    @Override
    public void init() {
        Log.d(TAG, "init ");
        lvNews = (ListView) getView().findViewById(R.id.content_main_lvNews);
        //  mDemoSlider = (SliderLayout) getView().findViewById(R.id.frangment_main_slider);
        mRecyclerViewSlider = (RecyclerView) getView().findViewById(R.id.cunghoangdao_fragment_recyclerSlide);
        rvNewsFeed = (RecyclerView) getView().findViewById(R.id.fragment_hometab_rvNewFeed);
        mRecyclerViewSlider.setHasFixedSize(true);
        rvNewsFeed.setHasFixedSize(true);
        mRecyclerViewSlider.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        rvNewsFeed.setLayoutManager(new LinearLayoutManager(getContext()));
        progressBar = (ProgressBar) getView().findViewById(R.id.fragment_hometab_progressBar);
        progressBarNews = (ProgressBar) getView().findViewById(R.id.fragment_hometab_progressBarNews);
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
    }

    @Override
    public void onStop() {
//        mDemoSlider.stopAutoCycle();
        super.onStop();
    }

    private void initSlideCungHoangDao() {
        arrayCunghoangdao = new ArrayList<>();
        Cunghoangdao cunghoangdao = null;
//        Cunghoangdao chdBachDuong = new Cunghoangdao(getResources().getString(R.string.chd_bachduong), R.drawable.chd_bachduong);
//        Cunghoangdao chdKimNguu = new Cunghoangdao(getResources().getString(R.string.chd_kimnguu), R.drawable.chd_kimnguu);
//        Cunghoangdao chdSongTu = new Cunghoangdao(getResources().getString(R.string.chd_songngu), R.drawable.chd_songngu);
//        Cunghoangdao chdCuGiai = new Cunghoangdao(getResources().getString(R.string.chd_cugiai), R.drawable.chd_cugiai);
//        Cunghoangdao chdSuTu = new Cunghoangdao(getResources().getString(R.string.chd_sutu), R.drawable.chd_sutu);
//        Cunghoangdao chdXuNu = new Cunghoangdao(getResources().getString(R.string.chd_xunu), R.drawable.chd_xunu);
//        Cunghoangdao chdThienBinh = new Cunghoangdao(getResources().getString(R.string.chd_thienbinh), R.drawable.chd_thienbinh);
//        Cunghoangdao chdBoCap = new Cunghoangdao(getResources().getString(R.string.chd_bocap), R.drawable.chd_bocap);
//        Cunghoangdao chdNhanMa = new Cunghoangdao(getResources().getString(R.string.chd_nhanma), R.drawable.chd_nhanma);
//        Cunghoangdao chdMaKet = new Cunghoangdao(getResources().getString(R.string.chd_maket), R.drawable.chd_maket);
//        Cunghoangdao chdBaoBinh = new Cunghoangdao(getResources().getString(R.string.chd_baobinh), R.drawable.chd_baobinh);
//        Cunghoangdao chdSongNgu = new Cunghoangdao(getResources().getString(R.string.chd_songngu), R.drawable.chd_songngu);

        for (int i = 0; i < getResources().getStringArray(R.array.cunghoangdaoname).length; i++) {
            cunghoangdao = new Cunghoangdao(getResources().getStringArray(R.array.cunghoangdaoname)[i],
                    getResources().obtainTypedArray(R.array.cunghoangdaoimage).getResourceId(i, -1));
            arrayCunghoangdao.add(cunghoangdao);
            Log.d(TAG, "initSlideCungHoangDao: "+getResources().getIntArray(R.array.cunghoangdaoimage)[i]);
        }
        custom_addapter = new RecyclerViewAdapter(getContext(), arrayCunghoangdao);
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
        for (int i = 0; i < 4; i++) {
            arrayLastPost.add(arr.get(i));
        }
        ArrayList<NewsFeed> arrayNewsFeed = new ArrayList<>();
        for (int i = 4; i < arr.size(); i++) {
            arrayNewsFeed.add(arr.get(i));
        }
        mTimeLineAdapter = new LatestPostsAdapter(getContext(), arrayLastPost, this);

        lvNews.setAdapter(mTimeLineAdapter);
        progressBar.setVisibility(View.INVISIBLE);

        RecyclerNewsFeedAdapter adapter = new RecyclerNewsFeedAdapter(getContext(), arrayNewsFeed, this);
        rvNewsFeed.setAdapter(adapter);
        progressBarNews.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onItemClick(View view, int position, String content, String title) {
        putIntent(content, title);
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
    public void onClickItemNewsFeed(View view, int position, String content, String title) {
        putIntent(content, title);
    }

    public void putIntent(String content, String title) {
        Intent intent = new Intent(context, ViewingActivity.class);
        intent.putExtra(RecyclerCunghoangdaoAdapter.CONTENT, content);
        intent.putExtra(RecyclerCunghoangdaoAdapter.TITLE, title);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(context, CategoryActivity.class);
        switch (v.getId()) {
            case R.id.fragment_hometab_tlTi:
                intent.putExtra(URL_CATEGORY, UrlGetXml.TUOI_TI);
                break;
            case R.id.fragment_hometab_tlSuu:
                intent.putExtra(URL_CATEGORY, UrlGetXml.TUOI_SUU);
                break;
            case R.id.fragment_hometab_tlDan:
                intent.putExtra(URL_CATEGORY, UrlGetXml.TUOI_DAN);
                break;
            case R.id.fragment_hometab_tlMeo:
                intent.putExtra(URL_CATEGORY, UrlGetXml.TUOI_MAO);
                break;
            case R.id.fragment_hometab_tlThin:
                intent.putExtra(URL_CATEGORY, UrlGetXml.TUOI_THIN);
                break;
            case R.id.fragment_hometab_tlTy:
                intent.putExtra(URL_CATEGORY, UrlGetXml.TUOI_TY);
                break;
            case R.id.fragment_hometab_tlNgo:
                intent.putExtra(URL_CATEGORY, UrlGetXml.TUOI_NGO);
                break;
            case R.id.fragment_hometab_tlMui:
                intent.putExtra(URL_CATEGORY, UrlGetXml.TUOI_MUI);
                break;
            case R.id.fragment_hometab_tlThan:
                intent.putExtra(URL_CATEGORY, UrlGetXml.TUOI_THAN);
                break;
            case R.id.fragment_hometab_tlDau:
                intent.putExtra(URL_CATEGORY, UrlGetXml.TUOI_DAN);
                break;
            case R.id.fragment_hometab_tlTuat:
                intent.putExtra(URL_CATEGORY, UrlGetXml.TUOI_TUAT);
                break;
            case R.id.fragment_hometab_tlHoi:
                intent.putExtra(URL_CATEGORY, UrlGetXml.TUOI_HOI);
                break;
            default:
                break;
        }
        startActivity(intent);
    }
}
