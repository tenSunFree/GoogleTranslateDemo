package com.home.googletranslatedemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final int GET_INPUT = 500;
    private int height, resourceId;
    private String inputText;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private Toolbar toolbar;
    private View statusBarView;
    private ViewGroup.LayoutParams statusBarViewLayoutParams;
    private List<SideDrawerBottomItem> sideDrawerBottomItems;
    private SideDrawerBottomListAdapter sideDrawerBottomListAdapter;
    private ListView sideDrawerBottomListView;
    private CardView translationCardView;
    private EditText editText;
    private FrameLayout translationFrameLayout;
    private Intent intent;
    private ActivityOptionsCompat options;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        replaceActionBar();
        initializeSideDrawerBottomItems();
        initializeSideDrawerBottomListView();
        initializeTheInputBoxRelated();
    }

    /**
     * 初始化輸入框的相關控件內容
     */
    private void initializeTheInputBoxRelated() {
        translationCardView = findViewById(R.id.translationCardView);
        editText = findViewById(R.id.editText);
        editText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });
        translationFrameLayout = findViewById(R.id.translationFrameLayout);
        translationFrameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startNewInterfaceWithSharedElements();
            }
        });
    }

    /**
     * 使用共享元素启动新界面
     */
    private void startNewInterfaceWithSharedElements() {
        inputText = editText.getText().toString();
        intent = new Intent(MainActivity.this, TypingInputActivity.class);
        intent.putExtra("inputText", inputText);
        options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                MainActivity.this,
                translationCardView, "translationCardView");
        startActivityForResult(intent, GET_INPUT, options.toBundle());
    }

    /**
     * 初始化SideDrawerBottomListView
     */
    private void initializeSideDrawerBottomListView() {
        sideDrawerBottomListView = findViewById(R.id.sideDrawerBottomListView);
        sideDrawerBottomListAdapter = new SideDrawerBottomListAdapter(this);
        sideDrawerBottomListAdapter.addAll(sideDrawerBottomItems);
        sideDrawerBottomListView.setAdapter(sideDrawerBottomListAdapter);
    }

    /**
     * 建立SideDrawerBottomItems的資料
     */
    private void initializeSideDrawerBottomItems() {
        sideDrawerBottomItems = new ArrayList<>();
        sideDrawerBottomItems.add(new SideDrawerBottomItem(R.drawable.icon_home_screen, "主畫面"));
        sideDrawerBottomItems.add(new SideDrawerBottomItem(R.drawable.icon_collection, "收藏"));
        sideDrawerBottomItems.add(new SideDrawerBottomItem(R.drawable.icon_offline_translation, "離線翻譯"));
        sideDrawerBottomItems.add(new SideDrawerBottomItem(R.drawable.icon_set_up, "設定"));
        sideDrawerBottomItems.add(new SideDrawerBottomItem(R.drawable.icon_description, "說明與意見回饋"));
    }

    /**
     * 更新狀態欄的高度
     */
    @Override
    protected void onResume() {
        super.onResume();
        setStatusBarViewHeight();
    }

    /**
     * 微調狀態欄的高度, 讓它與預設高度一致
     */
    private void setStatusBarViewHeight() {
        statusBarView = findViewById(R.id.statusBarView);
        height = 0;
        resourceId = getApplicationContext().getResources().getIdentifier(
                "status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            height = getApplicationContext().getResources().getDimensionPixelSize(resourceId);
        }
        statusBarViewLayoutParams = statusBarView.getLayoutParams();
        statusBarViewLayoutParams.height = height;
        statusBarViewLayoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        statusBarView.setLayoutParams(statusBarViewLayoutParams);
    }

    /**
     * 取代原本的ActionBar
     */
    private void replaceActionBar() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.menu_main);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar,             // 實作 drawer toggle 並放入 toolbar
                R.string.drawer_open, R.string.drawer_close);
        mDrawerToggle.syncState();
    }

    /**
     * 接收返回的輸入內容
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == GET_INPUT && resultCode == Activity.RESULT_OK) {
            Bundle bundle = data.getExtras();
            editText.setText(bundle.getString("inputText"));
        }
    }
}
