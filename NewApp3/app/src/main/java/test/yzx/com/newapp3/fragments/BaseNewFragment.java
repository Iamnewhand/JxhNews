package test.yzx.com.newapp3.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yzx.delegate.RecyclerDelegateAdapter;
import com.yzx.delegate.holder.ViewHolder;
import com.yzx.delegate.items.CommonItem;
import com.yzx.delegate.items.FooterItem;

import java.util.List;

import test.yzx.com.newapp3.R;
import test.yzx.com.newapp3.conts.NetDatas;
import test.yzx.com.newapp3.models.NewsModel;

/**
 * Created by yangzhenxiang on 2018/7/15.
 */

public class BaseNewFragment extends Fragment {

    public static BaseNewFragment newInstance(int type) {
        Bundle args = new Bundle();
        args.putInt("type", type);
        BaseNewFragment fragment = new BaseNewFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_base_layout, null);
    }

    RecyclerView recyclerView;
    RecyclerDelegateAdapter delegateAdapter;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        recyclerView = getView().findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),1));
        delegateAdapter = new RecyclerDelegateAdapter(getActivity());
        recyclerView.setAdapter(delegateAdapter);
        intDelegateAdapter();
        initData();
        super.onActivityCreated(savedInstanceState);
    }

    private void initData() {
        List<NewsModel> list;
        String datas = "";
        switch (getArguments().getInt("type")) {
            case 0:
                datas = NetDatas.yule_1;
                break;
            case 1:
                datas = NetDatas.jingji_1;
                break;
            case 2:
                datas = NetDatas.jiaoyu_1;
                break;
            case 3:
                datas = NetDatas.junshi_1;
                break;
            default:
                break;
        }
        list = new Gson().fromJson(datas, new TypeToken<List<NewsModel>>() {
        }.getType());

        commonItem.setData(list);
        delegateAdapter.notifyDataSetChanged();
    }


    private CommonItem<NewsModel> commonItem;

    public void intDelegateAdapter() {

        commonItem = new CommonItem<NewsModel>(R.layout.cell_base_news_layout) {
            @Override
            protected void convert(ViewHolder holder, int position, int positionAtTotal, final NewsModel model) {
                Glide.with(context).load(model.image).into(holder.getImageView(R.id.img_cell));
                holder.setText(R.id.tv_title, model.title)
                        .setText(R.id.tv_time, model.time)
                        .itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(context, "点击查看新闻" + model.id, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        };
        delegateAdapter.registerItem(commonItem).registerItem(new FooterItem(R.layout.cell_news_footer_layout) {
            @Override
            protected void convert(ViewHolder holder) {
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        loadMore();
                    }
                });
            }

            @Override
            public FooterStatusChangedListener setFooterStatusChangedListener() {
                return new FooterStatusChangedListener() {
                    @Override
                    public void loadComplete(ViewHolder holder) {
                        holder.setViewVisible(R.id.progress, View.GONE).getTextView(R.id.tv_tip).setText("加载更多");
                    }

                    @Override
                    public void loading(ViewHolder holder) {
                        holder.setViewVisible(R.id.progress, View.VISIBLE).getTextView(R.id.tv_tip).setText("加载中");
                    }

                    @Override
                    public void loadError(ViewHolder holder) {
                        holder.setViewVisible(R.id.progress, View.GONE).getTextView(R.id.tv_tip).setText("网络异常");
                    }

                    @Override
                    public void noMore(ViewHolder holder) {
                        holder.setViewVisible(R.id.progress, View.GONE).getTextView(R.id.tv_tip).setText("没有更多");
                    }
                };
            }
        });
        delegateAdapter.notifyDataSetChanged();
    }

    public void loadMore() {
        delegateAdapter.setFooterStatusLoading();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1500);
                    final List<NewsModel> list;

                    String datas = "";
                    switch (getArguments().getInt("type")) {
                        case 0:
                            datas = NetDatas.yule_2;
                            break;
                        case 1:
                            datas = NetDatas.jingji_2;
                            break;
                        case 2:
                            datas = NetDatas.jiaoyu_2;
                            break;
                        case 3:
                            datas = NetDatas.junshi_2;
                            break;
                        default:
                            break;
                    }
                    list = new Gson().fromJson(datas, new TypeToken<List<NewsModel>>() {
                    }.getType());

                    recyclerView.post(new Runnable() {
                        @Override
                        public void run() {
                            commonItem.addData(list);
                            delegateAdapter.setFooterStatusLoadMore();
                            delegateAdapter.notifyDataSetChanged();
                        }
                    });


                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

}
