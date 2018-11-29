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

import java.util.ArrayList;
import java.util.List;

import test.study.wzm.xiaomingweather.Adapter.ForecastAdapter;
import test.study.wzm.xiaomingweather.Bean.WeatherBean;
import test.study.wzm.xiaomingweather.R;

/**
 *  Fragment1 用于 展示 天气预报
 */
@SuppressLint("ValidFragment")
public class Fragment1 extends Fragment {
    //碎片中加载一个RecyclerView 所以要定义一个成员变量
    private RecyclerView forecast_list;

    // recyclerView 的适配器 ,用于展示 天气预报
    private ForecastAdapter adapter;

    // 天气预报列表
    private List<WeatherBean.HeWeather6Bean.DailyForecastBean> forecastList ;

    // 最外层视图Viwe ,即是 recyclerView
    View view;

    // 碎片一创建的时候就传入 数据
    @SuppressLint("ValidFragment")
    public Fragment1(List<WeatherBean.HeWeather6Bean.DailyForecastBean> forecastList) {
        this.forecastList = forecastList;
    }

    //无参构造器
    public Fragment1() {
        super();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.daily_forecast, container, false);
            forecast_list = view.findViewById(R.id.forecast_list);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
            forecast_list.setLayoutManager(linearLayoutManager);
            adapter = new ForecastAdapter(forecastList, getActivity().getApplicationContext());
            forecast_list.setAdapter(adapter);
        }
        return view;
    }
    // 用于通知 recyclerView中的数据变化 ,更新数据
    public void update(){
        adapter.notifyDataSetChanged();
    }
}
