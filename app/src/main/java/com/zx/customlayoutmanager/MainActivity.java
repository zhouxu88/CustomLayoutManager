package com.zx.customlayoutmanager;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.zx.customlayoutmanager.helper.CenterScrollListener;
import com.zx.customlayoutmanager.layoutmanager.CircleLayoutManager;
import com.zx.customlayoutmanager.layoutmanager.CircleZoomLayoutManager;
import com.zx.customlayoutmanager.layoutmanager.GalleryLayoutManager;
import com.zx.customlayoutmanager.layoutmanager.ScrollZoomLayoutManager;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener {
    private final static int CIRCLE = 0;
    private final static int SCROLL_ZOOM = 1;
    private final static int CIRCLE_ZOOM = 2;
    private final static int GALLERY = 3;

    private int mode = -1;
    private RecyclerView recyclerView;
    private CircleLayoutManager circleLayoutManager;
    private CircleZoomLayoutManager circleZoomLayoutManager;
    private ScrollZoomLayoutManager scrollZoomLayoutManager;
    private GalleryLayoutManager galleryLayoutManager;
    private List<Integer> mList; //数据源
    private RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();
        configureRecyclerView();
        radioGroup = (RadioGroup) findViewById(R.id.manager_rg);
        radioGroup.setOnCheckedChangeListener(this);
        //determineLayoutManager();
        //recyclerView.smoothScrollToPosition(5);
    }

    //初始化数据源
    private void initData() {
        mList = new ArrayList<>();
        mList.add(R.mipmap.img1);
        mList.add(R.mipmap.img2);
        mList.add(R.mipmap.img3);
        mList.add(R.mipmap.img4);
        mList.add(R.mipmap.img5);
        mList.add(R.mipmap.img6);
        mList.add(R.mipmap.img3);
        mList.add(R.mipmap.img3);
        mList.add(R.mipmap.img6);
        mList.add(R.mipmap.img6);
        mList.add(R.mipmap.img3);
        mList.add(R.mipmap.img6);
    }

    private void configureRecyclerView() {
        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        circleLayoutManager = new CircleLayoutManager(this);
        circleZoomLayoutManager = new CircleZoomLayoutManager(this);
        scrollZoomLayoutManager = new ScrollZoomLayoutManager(this, Dp2px(10));
        galleryLayoutManager = new GalleryLayoutManager(this, Dp2px(10));

        determineLayoutManager(0);
        recyclerView.addOnScrollListener(new CenterScrollListener());
        recyclerView.setAdapter(new MyAdapter(this, mList));
    }

    private void determineLayoutManager(int mode) {
        switch (mode) {
            case CIRCLE:
                changeAndToast(circleLayoutManager, "圆弧模式");
                break;
            case SCROLL_ZOOM:
                changeAndToast(scrollZoomLayoutManager, "缩放模式");
                break;
            case CIRCLE_ZOOM:
                changeAndToast(circleZoomLayoutManager, "圆弧+缩放模式");
                break;
            case GALLERY:
                changeAndToast(galleryLayoutManager, "画廊模式");
                break;
        }
    }

    //切换LayoutManager的模式
    private void changeAndToast(RecyclerView.LayoutManager layoutManager, String toast) {
        recyclerView.setLayoutManager(layoutManager);
        Toast.makeText(this, toast, Toast.LENGTH_SHORT).show();
    }

    //dp转化为px
    private int Dp2px(float dp) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        int mode = 0;
        switch (checkedId) {
            case R.id.circle_rb:
                mode = 0;
                break;
            case R.id.scroll_rb:
                mode = 1;
                break;
            case R.id.circle_scroll_rb:
                mode = 2;
                break;
            case R.id.gallery_rb:
                mode = 3;
                break;
        }
        determineLayoutManager(mode);
    }
}
