package com.xc.demo;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.xc.util.DensityUtil;
import com.xc.xcpulltoloadmorelistview.R;
import com.xc.xcpulltoloadmorelistview.XCPullToLoadMoreListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView mListView;
    MyAdapter mAdapter;
    XCPullToLoadMoreListView mPTLListView;
    List<String> mList = new ArrayList<>(0);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

    }

    private void init() {
        for(int i = 191;i <=200; i ++){
            mList.add("Item "+ i);
        }
        mPTLListView = (XCPullToLoadMoreListView) findViewById(R.id.list);

        mPTLListView.setOnRefreshListener(new XCPullToLoadMoreListView.OnRefreshListener() {

            @Override
            public void onPullDownLoadMore() {
                Log.v("czm", "onRefreshing");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        List<String> list = new ArrayList<String>();
                        int i = 200 - mList.size() - 10 + 1;
                        int count = 0;
                        while (count < 10) {
                            list.add("Item " + i);
                            i++;
                            count++;
                        }
                        mList.addAll(0, list);
                        mAdapter.notifyDataSetChanged();
                        mPTLListView.onRefreshComplete();
                    }
                }, 1000);
            }
        });

        mListView = mPTLListView.getListView();
        mAdapter = new MyAdapter();
        mListView.setAdapter(mAdapter);
    }


}
