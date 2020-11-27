package org.zouyi.common;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.List;

import org.zouyi.common.home.HomeFragment;
import org.zouyi.common.my.MyFragment;
import org.zouyi.common.utils.ImmerseStatusBarV6;
import org.zouyi.common.view.NoScrollViewPager;

public class MainActivity extends AppCompatActivity {
    private TabLayout mTablayout;
    private NoScrollViewPager mViewPager;
    private MainPagerAdapter myPagerAdapter;
    /**内容区Fragment*/
    private List<Fragment> fragments;
    /**底部标签名*/
    private String[] mTabNames={"首页","我"};
    /**标签未被选中图标*/
    private int[] iconUnSelected = {R.drawable.home_unselected, R.drawable.my_unselected};
    /**标签被选中图标*/
    private int[] iconSelected = {R.drawable.home_selected, R.drawable.my_selected};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImmerseStatusBarV6.setImmerseStatusBar(this);
        setContentView(R.layout.activity_main);
        initFragments();
        mTablayout = (TabLayout) findViewById(R.id.tablayout);
        mViewPager = (NoScrollViewPager) findViewById(R.id.viewpager);
        mViewPager.setNoScroll(true);//禁止左右滑动
        myPagerAdapter=new MainPagerAdapter(this,getSupportFragmentManager(), fragments,
                mTabNames, iconUnSelected,iconSelected);
        mViewPager.setAdapter(myPagerAdapter);
        mTablayout.setupWithViewPager(mViewPager);//最重要的一句代码，将TabLayout和ViewPager关联起来
        /**
         * 为每一个Tab设置自定义布局
         */
        for (int i = 0; i <mTablayout.getTabCount() ; i++) {
            TabLayout.Tab tab = mTablayout.getTabAt(i);
            tab.setCustomView(myPagerAdapter.getTabView(i));
        }
        mTablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab==null){
                    return;
                }
                //第二个参数，可以在点击tab标签时页面没有滑屏效果，避免页面风格差异带来的视觉闪现体验问题
                mViewPager.setCurrentItem(tab.getPosition(), false);
                myPagerAdapter.setTabSelected(tab.getPosition());
            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                if(tab==null){
                    return;
                }
                myPagerAdapter.setTabUnSelected(tab.getPosition());
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        myPagerAdapter.setTabSelected(0);
    }

    private void initFragments() {
        fragments = new ArrayList<>();
        for (int i = 0; i < mTabNames.length; i++) {
            BaseFragment fragment=null;
            if(i==0){
                fragment = HomeFragment.newInstance(mTabNames[i]);
            }else  if(i==1){
                fragment = MyFragment.newInstance(mTabNames[i]);
            }
            fragments.add(fragment);
        }
    }
}
