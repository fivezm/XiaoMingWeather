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

import test.study.wzm.xiaomingweather.Adapter.HourlyRecyViewAdapter;
import test.study.wzm.xiaomingweather.Bean.WeatherBean;
import test.study.wzm.xiaomingweather.R;

@SuppressLint("ValidFragment")
public class HourlyWeatherFragment extends Fragment {
    private RecyclerView recyclerView;

    private HourlyRecyViewAdapter adapter;

    private List<WeatherBean.HeWeather6Bean.HourlyBean> hourlyList;

    private View view;

    @SuppressLint("ValidFragment")
    public HourlyWeatherFragment(List<WeatherBean.HeWeather6Bean.HourlyBean> hourlyList) {
        this.hourlyList = hourlyList;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.hourly_weather, container, false);
            recyclerView = view.findViewById(R.id.hourly_recycler_view);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
            recyclerView.setLayoutManager(linearLayoutManager);
            adapter = new HourlyRecyViewAdapter(getActivity().getApplicationContext(), hourlyList);
            recyclerView.setAdapter(adapter);
        }
        return view;
    }
    // 用于通知 recyclerView中的数据变化 ,更新数据
    public void update(){
        adapter.notifyDataSetChanged();
    }
}
