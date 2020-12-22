package org.zouyi.common.my;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import org.zouyi.common.BaseFragment;
import org.zouyi.common.R;
import org.zouyi.common.view.PullFlashLoadMoreListView;


public class MyFragment extends BaseFragment {
    
    private View view;
    private PullFlashLoadMoreListView plListViewMessage;
    private MyAdapter homeAdapter;
    private List<String> messages;

    public static MyFragment newInstance(String data) {
        Bundle args = new Bundle();
        args.putString("key", data);
        MyFragment fragment = new MyFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater,container,savedInstanceState);
        view =inflater.inflate(R.layout.fragment_my, container, false);
        tvTitle.setText(getArguments().getString("key"));
        refreshMore();
        flContent.addView(view);
        homeAdapter = new MyAdapter(getActivity());
        initData();
        return baseView;
    }

    /**
     * 初始化数据
     */
    private void initData() {
        messages = new ArrayList<String>();
        messages.add("最近在干嘛");
        messages.add("最近在干嘛");
        messages.add("最近在干嘛");
        messages.add("最近在干嘛");
        messages.add("最近在干嘛");
        messages.add("最近在干嘛");
        messages.add("最近在干嘛");
        messages.add("最近在干嘛");
        messages.add("最近在干嘛");
        messages.add("最近在干嘛");
        messages.add("最近在干嘛");
        homeAdapter.loadData(messages);
        plListViewMessage.setAdapter(homeAdapter);
    }
    /**
     * 初始化下拉刷新，上拉更多
     */
    public void refreshMore() {
        plListViewMessage= (PullFlashLoadMoreListView) view.findViewById(R.id.plListViewMessage);
        plListViewMessage.setInterface(new PullFlashLoadMoreListView.IReflashListener(){
            @Override
            public void onReflash() {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        initData();
                        //通知listview 刷新数据完毕；
                        plListViewMessage.reflashComplete();
                    }
                }, 2000);
            }

            @Override
            public void onLoad() {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        messages.add("最近在干嘛");
                        homeAdapter.notifyDataSetChanged();
                        //通知listview加载完毕
                        plListViewMessage.loadComplete();
                    }
                }, 2000);

            }

        });

    }
}
