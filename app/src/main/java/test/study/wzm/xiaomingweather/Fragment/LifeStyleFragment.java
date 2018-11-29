package test.study.wzm.xiaomingweather.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import test.study.wzm.xiaomingweather.Adapter.LifeStyleRecyViewAdapter;
import test.study.wzm.xiaomingweather.Bean.WeatherBean;
import test.study.wzm.xiaomingweather.R;

@SuppressLint("ValidFragment")
public class LifeStyleFragment extends Fragment {

    private View view;
    private List<WeatherBean.HeWeather6Bean.LifestyleBean> lifestyleList;

    //碎片中加载一个RecyclerView 所以要定义一个成员变量
    private RecyclerView lifeStyle_list;

    private LifeStyleRecyViewAdapter adapter;

   // private LinearLayout layout;

    @SuppressLint("ValidFragment")
    public LifeStyleFragment(List<WeatherBean.HeWeather6Bean.LifestyleBean> lifestyle) {
        this.lifestyleList = lifestyle;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.life_style, container, false);
            lifeStyle_list = view.findViewById(R.id.lifestyle_recycler_view);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
            lifeStyle_list.setLayoutManager(linearLayoutManager);
            adapter = new LifeStyleRecyViewAdapter(lifestyleList);
            lifeStyle_list.setAdapter(adapter);
            //layout = view.findViewById(R.id.life_style_layout);
        }

        return view;
    }
    // 用于通知 recyclerView中的数据变化 ,更新数据
    public void update(){
        adapter.notifyDataSetChanged();
    }

//    public void showLifeStyle(List<WeatherBean.HeWeather6Bean.LifestyleBean> lifestyle) {
//        layout.removeAllViews();
//        for (WeatherBean.HeWeather6Bean.LifestyleBean beans : lifestyle) {
//            View v = LayoutInflater.from(getActivity()).inflate(R.layout.life_style_item,layout, false);
//            TextView type = v.findViewById(R.id.type);
//            TextView brf = v.findViewById(R.id.brf);
//            TextView txt = v.findViewById(R.id.txt);
//
//            type.setText(beans.getType());
//            brf.setText(beans.getBrf());
//            txt.setText(beans.getTxt());
//            layout.addView(v);
//        }
//    }
}
