package cheng.com.android.cunghoangdao.fragments.tienich;

import android.content.Intent;
import android.util.Log;
import android.widget.GridView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

import java.util.ArrayList;

import cheng.com.android.cunghoangdao.R;
import cheng.com.android.cunghoangdao.activities.lichngaytot.AmDuongActivity;
import cheng.com.android.cunghoangdao.activities.lichngaytot.BoiCunghoangdaoActivity;
import cheng.com.android.cunghoangdao.activities.lichngaytot.BoingaysinhActivity;
import cheng.com.android.cunghoangdao.activities.lichngaytot.BoinotruiActivity;
import cheng.com.android.cunghoangdao.activities.lichngaytot.SaohanActivity;
import cheng.com.android.cunghoangdao.activities.lichngaytot.TuoisinhconActivity;
import cheng.com.android.cunghoangdao.activities.lichngaytot.TuvingaysinhActivity;
import cheng.com.android.cunghoangdao.activities.lichngaytot.TuvitheothangActivity;
import cheng.com.android.cunghoangdao.activities.maincreen.MainScreenActivity;
import cheng.com.android.cunghoangdao.adapters.tienichtab.TienichAdapter;
import cheng.com.android.cunghoangdao.fragments.BaseFragment;
import cheng.com.android.cunghoangdao.interfaces.OnReturnPositionTienich;
import cheng.com.android.cunghoangdao.model.Tienich;
import vn.amobi.util.ads.video.AmobiShowVideoAdRequest;
import vn.amobi.util.ads.video.AmobiVideoAd;
import vn.amobi.util.ads.video.AmobiVideoAdListener;

/**
 * Created by Welcome on 3/30/2016.
 */
public class TienichTabFragment extends BaseFragment implements OnReturnPositionTienich,AmobiVideoAdListener {

    private final String TAG = getClass().getSimpleName();
    private GridView gvTienich;
    private ArrayList<Tienich> arrTienich;
    private TienichAdapter tienichAdapter;
    private int iClick = 0;
    public InterstitialAd mInterstitialAd;

    @Override
    public void init() {
        Log.d(TAG, "init: ");
        gvTienich = (GridView) getView().findViewById(R.id.fragment_tienichtab_gvTienich);
        arrTienich = new ArrayList<>();
    }

    @Override
    public void setEvent() {
    }

    @Override
    public void setValue() {
        for (int i = 0; i<getResources().getStringArray(R.array.tienichtitle).length;i++){
            arrTienich.add(new Tienich(getResources().getStringArray(R.array.tienichtitle)[i],
                    getResources().obtainTypedArray(R.array.tienichimage).getResourceId(i, -1),
                    getResources().getStringArray(R.array.tienichdescription)[i],
                    getResources().getIntArray(R.array.tienich_color)[i]));
        }
        tienichAdapter = new TienichAdapter(arrTienich,getActivity(),TienichTabFragment.this);
        gvTienich.setAdapter(tienichAdapter);
        tienichAdapter.notifyDataSetChanged();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_tienichtab;
    }

    @Override
    public void onReturnPositionFinish(int position) {
        Log.d(TAG, "onReturnPositionFinish: "+position);
        Intent intent = null;
        iClick++;
        Log.d(TAG, "onReturnPositionFinish: "+iClick);
        if(iClick==4 ||iClick==10 || iClick==15){
            AmobiVideoAd.getInstance().setVideoAdListener(this);
            AmobiVideoAd.getInstance().prepare(getActivity());
        }
        switch (position){
            case 0:
              intent  = new Intent(getActivity(), BoiCunghoangdaoActivity.class);
                intent.putExtra(MainScreenActivity.TOOLBAR_NAME,getResources().getString(R.string.tienich_boicunghoangdao));
                break;
            case 1:
                intent  = new Intent(getActivity(), BoingaysinhActivity.class);
                intent.putExtra(MainScreenActivity.TOOLBAR_NAME,getResources().getString(R.string.tienich_boingaysinh));
                break;
            case 2:
                intent  = new Intent(getActivity(), BoinotruiActivity.class);
                intent.putExtra(MainScreenActivity.TOOLBAR_NAME,getResources().getString(R.string.tienich_boinotruinam));
                break;
            case 3:
                intent  = new Intent(getActivity(), TuvingaysinhActivity.class);
                intent.putExtra(MainScreenActivity.TOOLBAR_NAME,getResources().getString(R.string.tienich_tuvingaysinh));
                break;
            case 4:
                intent  = new Intent(getActivity(), TuvitheothangActivity.class);
                intent.putExtra(MainScreenActivity.TOOLBAR_NAME,getResources().getString(R.string.tienich_tuvitheothang));
                break;
            case 5:
                intent  = new Intent(getActivity(), SaohanActivity.class);
                intent.putExtra(MainScreenActivity.TOOLBAR_NAME,getResources().getString(R.string.tienich_xemsaohan));
                break;
            case 6:
                intent  = new Intent(getActivity(), TuoisinhconActivity.class);
                intent.putExtra(MainScreenActivity.TOOLBAR_NAME,getResources().getString(R.string.tienich_tuoisinhcon));
                break;
            case 7:
                intent  = new Intent(getActivity(), AmDuongActivity.class);
                intent.putExtra(MainScreenActivity.TOOLBAR_NAME,getResources().getString(R.string.tienich_doilichamduong));
                break;
        }
        startActivity(intent);
    }

    @Override
    public void onAdAvailable() {
        Log.d(TAG, "onAdAvailable: ");
        AmobiVideoAd.getInstance().showAd(new AmobiShowVideoAdRequest());
    }

    @Override
    public void onPrepareError() {
        mInterstitialAd = new InterstitialAd(getActivity());
        mInterstitialAd.setAdUnitId(getString(R.string.banner_video));
        mInterstitialAd.setAdListener(new AdListener() {
            public void onAdLoaded() {
                showInterstitial();
            }
        });
        showInterstitial();
    }

    @Override
    public void onAdStarted() {
        Log.d(TAG, "onAdStarted: ");
    }

    @Override
    public void onAdFinished() {
        Log.d(TAG, "onAdFinished: ");

    }
    public void showInterstitial() {
        if (mInterstitialAd != null && mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else {
            startGame();
        }
    }
    public void startGame() {
        if (!mInterstitialAd.isLoading() && !mInterstitialAd.isLoaded()) {
            AdRequest adRequest = new AdRequest.Builder().build();
            mInterstitialAd.loadAd(adRequest);
        }

    }
}
