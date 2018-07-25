package test.yzx.com.newapp3.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import test.yzx.com.newapp3.R;

/**
 * Created by yangzhenxiang on 2018/7/14.
 */

public class NewFragment extends Fragment {

    public static NewFragment newInstance() {

        Bundle args = new Bundle();

        NewFragment fragment = new NewFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_news_layout, null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        initViews();
        initData();
        super.onViewCreated(view, savedInstanceState);
    }

    private List<String> titles;// 标题
    private List<Fragment> fragments;// 页面
    private MyAdapter adapter;

    private void initData() {
        titles = new ArrayList<>(Arrays.asList("娱乐", "经济", "教育", "军事"));
        fragments = new ArrayList<>();
        fragments.add(BaseNewFragment.newInstance(0));
        fragments.add(BaseNewFragment.newInstance(1));
        fragments.add(BaseNewFragment.newInstance(2));
        fragments.add(BaseNewFragment.newInstance(3));

        adapter = new MyAdapter(getActivity().getSupportFragmentManager());
        mViewPager.setAdapter(adapter);
    }

    private ViewPager mViewPager;
    private PagerTabStrip mPagerTabStrip;

    private void initViews() {
        mViewPager = getView().findViewById(R.id.viewpager);
        mPagerTabStrip = getView().findViewById(R.id.pager_tab_strip);
        mPagerTabStrip.setBackgroundColor(Color.WHITE);// 背景颜色
        mPagerTabStrip.setTextColor(Color.BLACK);// 标题颜色，这里需要带透明度的颜色值
        mPagerTabStrip.setTabIndicatorColor(Color.RED);// 指示器颜色，这里需要带透明度的颜色值
        mPagerTabStrip.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);// 字体大小
    }

    class MyAdapter extends FragmentPagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles.get(position);
        }
    }

}
