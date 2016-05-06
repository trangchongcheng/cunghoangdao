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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import cheng.com.android.cunghoangdao.R;
import cheng.com.android.cunghoangdao.activities.lichngaytot.ClipPhongThuyActivity;
import cheng.com.android.cunghoangdao.activities.lichngaytot.DanhmucActivity;
import cheng.com.android.cunghoangdao.activities.maincreen.MainScreenActivity;
import cheng.com.android.cunghoangdao.activities.viewing.ViewingActivity;
import cheng.com.android.cunghoangdao.adapters.cunghoangdaotab.RecyclerCunghoangdaoAdapter;
import cheng.com.android.cunghoangdao.adapters.hometab.CustomAdapter;
import cheng.com.android.cunghoangdao.adapters.hometab.RecyclerViewIconCungHoangDaoAdapter;
import cheng.com.android.cunghoangdao.common.UrlGetXml;
import cheng.com.android.cunghoangdao.fragments.BaseFragment;
import cheng.com.android.cunghoangdao.interfaces.OnItemClickRecyclerView;
import cheng.com.android.cunghoangdao.interfaces.OnReturnArticleCunghoangdaoByDay;
import cheng.com.android.cunghoangdao.model.Article;
import cheng.com.android.cunghoangdao.model.Category;
import cheng.com.android.cunghoangdao.model.Cunghoangdao;
import cheng.com.android.cunghoangdao.model.Header;
import cheng.com.android.cunghoangdao.services.ApiServiceLichNgayTot;
import cheng.com.android.cunghoangdao.services.JsoupParseCungHoangDaoByDay;
import cheng.com.android.cunghoangdao.services.LichngaytotAsyntask;

/**
 * Created by Welcome on 1/19/2016.
 */
public class HomeTabFragment extends BaseFragment implements
        OnReturnArticleCunghoangdaoByDay,
        OnItemClickRecyclerView,
        LichngaytotAsyntask.OnReturnJsonObject,
        RecyclerViewIconCungHoangDaoAdapter.OnItemClickIconCungHoangDao{
    private final String TAG = getClass().getSimpleName();
    public final static String URL_CATEGORY = "url_category";
    public final static String TITLE_CATEGORY = "title_category";
    private RecyclerView mRecyclerViewSlider, rvNewsFeed, rvCongiap;
    private ProgressBar progressBar, progressBarNews;
    RecyclerViewIconCungHoangDaoAdapter custom_addapter;
    ArrayList<Cunghoangdao> arrayCunghoangdao;
    private Button btnConnect;
    private LinearLayout ll, llcunghoangdao;
    private RelativeLayout rl;
    private int state = 0;
    private int page = 0;
    private TextView tvTitle, tvDescription;
    private CustomAdapter categoryAdapter;
    private ImageView imgThumbnail;
    private ArrayList<Category> arrArticle;
    private Header header;
    private Random rand ;
    private AdView adView;


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void init() {
        Log.d(TAG, "init +");
        AdView mAdView = (AdView) getView().findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        rand = new Random();
        rvNewsFeed = (RecyclerView) getView().findViewById(R.id.fragment_hometab_rvNewFeed);
        rvNewsFeed.setHasFixedSize(true);
        rvNewsFeed.setNestedScrollingEnabled(false);
        rvNewsFeed.setLayoutManager(new LinearLayoutManager(getContext()));
        rvCongiap = (RecyclerView) getView().findViewById(R.id.fragment_hometab_rvCongiap);
        rvCongiap.setHasFixedSize(true);
        rvCongiap.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        progressBarNews = (ProgressBar) getView().findViewById(R.id.fragment_hometab_progressBarNews);
        btnConnect = (Button) getView().findViewById(R.id.fragment_hometab_btnConnect);
        tvTitle = (TextView) getView().findViewById(R.id.fragment_hometab_tvTitle);
        tvDescription = (TextView) getView().findViewById(R.id.fragment_hometab_tvDecription);
        imgThumbnail = (ImageView) getView().findViewById(R.id.fragment_hometab_imgThumbnail);
        ll = (LinearLayout) getView().findViewById(R.id.fragment_hometab_ll);
        rl = (RelativeLayout) getView().findViewById(R.id.fragment_hometab_rl);
        llcunghoangdao = (LinearLayout) getView().findViewById(R.id.fragment_hometab_llCunghoangdao);
        arrArticle = new ArrayList<>();
        //Get Article Cunghoangdao by Day
        new JsoupParseCungHoangDaoByDay(getActivity(), this, UrlGetXml.CUNG_HOANG_DAO_BY_DAY).execute();
        //get Article NewFeeds
        progressBarNews.setVisibility(View.VISIBLE);
        new LichngaytotAsyntask(getActivity(), UrlGetXml.PHONG_THUY + page,
                ApiServiceLichNgayTot.ApiRequestType.TYPE_GET, 1, 0,
                getResources().getString(R.string.category_phongthuy), this).execute();
        new LichngaytotAsyntask(getActivity(), UrlGetXml.LICH_TU_VI + page,
                ApiServiceLichNgayTot.ApiRequestType.TYPE_GET, 1, 0,
                getResources().getString(R.string.category_tuvi), this).execute();
        new LichngaytotAsyntask(getActivity(), UrlGetXml.LICH_CUNG_HOANG_DAO + page,
                ApiServiceLichNgayTot.ApiRequestType.TYPE_GET, 1, 0,
                     getResources().getString(R.string.category_cunghoangdao), this).execute();
        initSlideCungHoangDao();

    }

    @Override
    public void setEvent() {
        btnConnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBarNews.setVisibility(View.VISIBLE);
                new JsoupParseCungHoangDaoByDay(getActivity(), HomeTabFragment.this, UrlGetXml.CUNG_HOANG_DAO_BY_DAY).execute();
                new LichngaytotAsyntask(getActivity(), UrlGetXml.PHONG_THUY + page,
                        ApiServiceLichNgayTot.ApiRequestType.TYPE_GET, 1, 0,
                        getResources().getString(R.string.category_phongthuy), HomeTabFragment.this).execute();
                new LichngaytotAsyntask(getActivity(), UrlGetXml.LICH_TU_VI + page,
                        ApiServiceLichNgayTot.ApiRequestType.TYPE_GET, 1, 0,
                        getResources().getString(R.string.category_tuvi), HomeTabFragment.this).execute();
                new LichngaytotAsyntask(getActivity(), UrlGetXml.LICH_CUNG_HOANG_DAO + page,
                        ApiServiceLichNgayTot.ApiRequestType.TYPE_GET, 1, 0,
                        getResources().getString(R.string.category_cunghoangdao), HomeTabFragment.this).execute();

            }
        });

    }

    @Override
    public void onStop() {
        super.onStop();
    }

    private void initSlideCungHoangDao() {
        arrayCunghoangdao = new ArrayList<>();
        Cunghoangdao cunghoangdao = null;
        for (int i = 0; i < getResources().getStringArray(R.array.congiapname).length; i++) {
            cunghoangdao = new Cunghoangdao(getResources().getStringArray(R.array.congiapname)[i],
                    getResources().obtainTypedArray(R.array.congiapimage).getResourceId(i, -1));
            arrayCunghoangdao.add(cunghoangdao);
        }
        custom_addapter = new RecyclerViewIconCungHoangDaoAdapter(getContext(), arrayCunghoangdao, this);
        rvCongiap.setAdapter(custom_addapter);
        custom_addapter.notifyDataSetChanged();
    }

    @Override
    public void setValue() {

    }

    //Return news Cung hoang dao by day
    @Override
    public void onReturnArticleCunghoangdaoByDay(final Article article) {
        if (article != null) {
            header = new Header(getString(R.string.category_cunghoangdao), article.getmContent(), article.getmTitle(),
                    article.getmDescription(), getResources().obtainTypedArray(R.array.cunghoangdaoimage).getResourceId(rand.nextInt(11), -1));
        }
    }

    public void getArray(ArrayList<Category> arrContent) {
        for (int i = 0; i < arrContent.size(); i++) {
            if (!arrContent.get(i).getmTitle().substring(0, 5).equals("Tử vi"))
                arrArticle.add(arrContent.get(i));
        }

    }

    private int iTimes = 0;

    @Override
    public void onReturnJsonObject(ArrayList<Category> arrContent, int type, String categoryName) {
        if (arrContent != null) {
            getArray(arrContent);
            iTimes++;
            Log.d(TAG, "onReturnJsonObject: " + iTimes);
            progressBarNews.setVisibility(View.INVISIBLE);
            ll.setVisibility(View.GONE);
            rl.setVisibility(View.VISIBLE);
        } else {
            progressBarNews.setVisibility(View.VISIBLE);
            ll.setVisibility(View.VISIBLE);
            rl.setVisibility(View.INVISIBLE);
        }
        if (iTimes == 3) {
            if (arrArticle != null) {
                Collections.shuffle(arrArticle);
                categoryAdapter = new CustomAdapter(context, header, arrArticle, 0, "null", this);
                rvNewsFeed.setAdapter(categoryAdapter);
            }
        }

    }

    @Override
    public void onItemClickListener(View v, int position, String title,
                                    String linkAricle, String linkImage, int category, String categoryName) {
        putIntent(title, linkAricle, linkImage, category, categoryName);


    }

    @Override
    public void onDaybydayitemClickLintener(View v, int position, String category, String title, String content) {
        Intent intent = new Intent(getActivity(), ViewingActivity.class);
        intent.putExtra(RecyclerCunghoangdaoAdapter.TITLE, title);
        intent.putExtra(RecyclerCunghoangdaoAdapter.CATEGORY, category);
        intent.putExtra(RecyclerCunghoangdaoAdapter.CONTENT, content);
        startActivity(intent);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_hometab;
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


    public void putIntent(String title, String linkArticle, String linkImage, int type, String categoryName) {
        Intent intent = null;
        if (type == 0) {
            intent = new Intent(getActivity(), ViewingActivity.class);
        } else if (type == 1) {
            intent = new Intent(getActivity(), ClipPhongThuyActivity.class);
            intent.putExtra(RecyclerCunghoangdaoAdapter.TYPE_VIDEO, "type_video");
        }
        assert intent != null;
        intent.putExtra(RecyclerCunghoangdaoAdapter.LINK, linkArticle);
        intent.putExtra(RecyclerCunghoangdaoAdapter.TITLE, title);
        intent.putExtra(RecyclerCunghoangdaoAdapter.CATEGORY, categoryName);
        intent.putExtra(RecyclerCunghoangdaoAdapter.LINK_IMAGE, linkImage);
        intent.putExtra(RecyclerCunghoangdaoAdapter.TYPE_LICH_NGAY_TOT, "type_lichngaytot");
        startActivity(intent);
    }

    @Override
    public void onItemClickIconCungHoangDao(View v, int position, String category) {
        Intent intent = new Intent(getActivity(), DanhmucActivity.class);
        switch (position){
            case 0:
                intent.putExtra(MainScreenActivity.TYPE_CATEGORY,getString(R.string.type_tuoi_ti));
                intent.putExtra(MainScreenActivity.TOOLBAR_NAME,category);
                break;
            case 1:
                intent.putExtra(MainScreenActivity.TYPE_CATEGORY,getString(R.string.type_tuoi_suu));
                intent.putExtra(MainScreenActivity.TOOLBAR_NAME,category);
                break;
            case 2:
                intent.putExtra(MainScreenActivity.TYPE_CATEGORY,getString(R.string.type_tuoi_dan));
                intent.putExtra(MainScreenActivity.TOOLBAR_NAME,category);
                break;
            case 3:
                intent.putExtra(MainScreenActivity.TYPE_CATEGORY,getString(R.string.type_tuoi_mao));
                intent.putExtra(MainScreenActivity.TOOLBAR_NAME,category);
                break;
            case 4:
                intent.putExtra(MainScreenActivity.TYPE_CATEGORY,getString(R.string.type_tuoi_thin));
                intent.putExtra(MainScreenActivity.TOOLBAR_NAME,category);
            case 5:
                intent = new Intent(getActivity(), DanhmucActivity.class);
                intent.putExtra(MainScreenActivity.TYPE_CATEGORY,getString(R.string.type_tuoi_ty));
                intent.putExtra(MainScreenActivity.TOOLBAR_NAME,category);
                break;
            case 6:
                intent.putExtra(MainScreenActivity.TYPE_CATEGORY,getString(R.string.type_tuoi_ngo));
                intent.putExtra(MainScreenActivity.TOOLBAR_NAME,category);
                break;
            case 7:
                intent.putExtra(MainScreenActivity.TYPE_CATEGORY,getString(R.string.type_tuoi_mui));
                intent.putExtra(MainScreenActivity.TOOLBAR_NAME,category);
                break;
            case 8:
                intent.putExtra(MainScreenActivity.TYPE_CATEGORY,getString(R.string.type_tuoi_than));
                intent.putExtra(MainScreenActivity.TOOLBAR_NAME,category);
                break;
            case 9:
                intent.putExtra(MainScreenActivity.TYPE_CATEGORY,getString(R.string.type_tuoi_dau));
                intent.putExtra(MainScreenActivity.TOOLBAR_NAME,category);
                break;
            case 10:
                intent.putExtra(MainScreenActivity.TYPE_CATEGORY,getString(R.string.type_tuoi_tuat));
                intent.putExtra(MainScreenActivity.TOOLBAR_NAME,category);
                break;
            case 11:
                intent.putExtra(MainScreenActivity.TYPE_CATEGORY,getString(R.string.type_tuoi_hoi));
                intent.putExtra(MainScreenActivity.TOOLBAR_NAME,category);
                break;
            default:
                break;
        }
        startActivity(intent);
    }
}
