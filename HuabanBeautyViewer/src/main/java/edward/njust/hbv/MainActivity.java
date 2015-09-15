package edward.njust.hbv;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.Menu;
import android.view.View;

import com.baoyz.widget.PullRefreshLayout;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;
import edward.njust.hbv.Adapters.RecyclerAdapter;
import jp.wasabeef.recyclerview.animators.adapters.AlphaInAnimationAdapter;

public class MainActivity extends Activity {

    public static Callback callback1, callback2;
    private PullRefreshLayout prl;
    private RecyclerView rv;
    private List<ImageInfo> data;
    private RecyclerAdapter adapter;
    private OkHttpClient client;

    private View mDecorView;

    @Override
    protected void onResume() {
        super.onResume();
        hideSystemUI();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_recycler);
        mDecorView = getWindow().getDecorView();

        EventBus.getDefault().register(this);
        data = new ArrayList<ImageInfo>();
        client = new OkHttpClient();
        callback1 = new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {
                data.addAll(HuabanImages.generateImageInfos(response.body().string()));
                EventBus.getDefault().post(new ImageEvent(0, data.size(), 50));
                HuabanImages.isLoading = false;
            }
        };
        callback2 = new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {

            }

            @Override
            public void onResponse(Response response) throws IOException {
                data.clear();
                data.addAll(HuabanImages.generateImageInfos(response.body().string()));
                EventBus.getDefault().post(new ImageEvent(1, 0, data.size()));
                HuabanImages.isLoading = false;
            }
        };
        prl = (PullRefreshLayout) findViewById(R.id.prl);
        prl.setOnRefreshListener(new PullRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                HuabanImages.loadNewestImages(50, MainActivity.callback2);
            }
        });
        rv = (RecyclerView) findViewById(R.id.rv);
        rv.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
        adapter = new RecyclerAdapter(data, this);
        rv.setAdapter(adapter);
        HuabanImages.loadNewestImages(50, callback1);
        prl.setRefreshing(true);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public void onEventMainThread(ImageEvent event) {
        switch (event.getSourceKind()) {
            case 0:
                adapter.notifyItemRangeInserted(event.getPositionStart(), event.getItemCount());
                break;
            case 1:
//                adapter.notifyItemRangeChanged(0, 50);
                adapter.notifyDataSetChanged();
                break;
        }
        prl.setRefreshing(false);
    }

    private void hideSystemUI() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
            mDecorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                            | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }

    private void showSystemUI() {
        mDecorView.setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }
}
