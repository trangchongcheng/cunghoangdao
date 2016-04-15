package cheng.com.android.cunghoangdao.fragments.video;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.malmstein.fenster.controller.FensterPlayerController;
import com.malmstein.fenster.controller.FensterPlayerControllerVisibilityListener;
import com.malmstein.fenster.controller.SimpleMediaFensterPlayerController;
import com.malmstein.fenster.play.FensterVideoStateListener;
import com.malmstein.fenster.view.FensterLoadingView;
import com.malmstein.fenster.view.FensterVideoView;

import cheng.com.android.cunghoangdao.R;

/**
 * Created by Welcome on 4/13/2016.
 */
public class VideoFragment extends Fragment implements FensterVideoStateListener {

    private View contentView;
    private FensterVideoView textureView;
    private FensterPlayerController fensterPlayerController;
    private FensterLoadingView fensterLoadingView;

    public VideoFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        contentView = inflater.inflate(R.layout.fen__fragment_fenster_video, container);
        textureView = (FensterVideoView) contentView.findViewById(R.id.fen__play_video_texture);
        fensterPlayerController = (FensterPlayerController) contentView.findViewById(R.id.fen__play_video_controller);
        fensterLoadingView = (FensterLoadingView) contentView.findViewById(R.id.fen__play_video_loading);
        return contentView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initVideo();
    }

    private void initVideo() {
        textureView.setMediaController(fensterPlayerController);
        textureView.setOnPlayStateListener(this);
    }

    public void playExampleVideo() {
        textureView.setVideo("http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4",
                SimpleMediaFensterPlayerController.DEFAULT_VIDEO_START);
        textureView.start();
    }

    public void setVisibilityListener(FensterPlayerControllerVisibilityListener visibilityListener) {
        fensterPlayerController.setVisibilityListener(visibilityListener);
    }

    public void showFensterController() {
        fensterLoadingView.hide();
        fensterPlayerController.show();
    }

    private void showLoadingView(){
        fensterLoadingView.show();
        fensterPlayerController.hide();
    }

    @Override
    public void onFirstVideoFrameRendered() {
        fensterPlayerController.show();
    }

    @Override
    public void onPlay() {
        showFensterController();
    }

    @Override
    public void onBuffer() {
        showLoadingView();
    }

    @Override
    public boolean onStopWithExternalError(int position) {
        return false;
    }
}
