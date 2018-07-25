package test.yzx.com.newapp3.fragments;


import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import test.yzx.com.newapp3.R;

/**
 * Created by yangzhenxiang on 2018/7/14.
 */

public class PersonFragment extends Fragment {

    public static PersonFragment newInstance() {
        
        Bundle args = new Bundle();
        
        PersonFragment fragment = new PersonFragment();
        fragment.setArguments(args);
        return fragment;
    }
    
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_person_layout,null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Glide.with(getActivity()).load(R.drawable.person_bg).into((ImageView) getView().findViewById(R.id.img_person_bg));
        getView().findViewById(R.id.rl_my).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "个人信息", Toast.LENGTH_SHORT).show();
            }
        });
        getView().findViewById(R.id.rl_collection).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "新闻收藏", Toast.LENGTH_SHORT).show();
            }
        });
        getView().findViewById(R.id.rl_setting).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "设置", Toast.LENGTH_SHORT).show();
            }
        });
        getView().findViewById(R.id.rl_feed_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "用户反馈", Toast.LENGTH_SHORT).show();
            }
        });
        getView().findViewById(R.id.rl_about).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), getVersion(), Toast.LENGTH_SHORT).show();
            }
        });
        getView().findViewById(R.id.btn_login_out).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "退出登录", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public String getVersion() {
        try {
            PackageManager manager = getActivity().getPackageManager();
            PackageInfo info = manager.getPackageInfo(getActivity().getPackageName(), 0);
            String version = info.versionName;
            return "当前版本" + version;
        } catch (Exception e) {
            return "当前版本1.2";
        }
    }



}
