package test.study.wzm.xiaomingweather.Adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import test.study.wzm.xiaomingweather.Bean.WeatherBean;
import test.study.wzm.xiaomingweather.Fragment.Fragment1;
import test.study.wzm.xiaomingweather.Fragment.HourlyWeatherFragment;
import test.study.wzm.xiaomingweather.Fragment.LifeStyleFragment;

public class FragmentAdapter extends FragmentPagerAdapter {
    // Tab 标签
    private String[] mTitle = {"天气预报", "生活建议"};

    //初始化碎片,用于复用
    private Fragment1 fragment1;
    //private HourlyWeatherFragment hourlyFrag;
    private LifeStyleFragment lifeStyleFragment;

    // 天气预报列表
    private List<WeatherBean.HeWeather6Bean.DailyForecastBean> forecastList = new ArrayList<>();
    // 每小时天气列表
    private List<WeatherBean.HeWeather6Bean.HourlyBean> hourlyList = new ArrayList<>();
    //生活建议列表
    private List<WeatherBean.HeWeather6Bean.LifestyleBean> lifestyleList = new ArrayList<>();

    // 构造函数中传入了天气预报数据 ,用作Fragment1的构造参数
    public FragmentAdapter(FragmentManager fm,List<WeatherBean.HeWeather6Bean.DailyForecastBean> forecastList,List<WeatherBean.HeWeather6Bean.LifestyleBean> lifeList) {
        super(fm);
        // 此行判断是为了 判断 天气预报列表中是否已经有了数据,如果有了数据则要清空,再添加数据进来
        if (this.forecastList!=null){
            this.forecastList.clear();
        }
//        if (this.hourlyList != null) {
//            this.hourlyList.clear();
//        }
        if (this.lifestyleList != null) {
            this.lifestyleList.clear();
        }
        this.forecastList = forecastList;
        this.lifestyleList = lifeList;
       // this.hourlyList = hourlyList;
        fragment1 = new Fragment1(forecastList);
        //hourlyFrag = new HourlyWeatherFragment(hourlyList);
        lifeStyleFragment = new LifeStyleFragment(lifestyleList);
    }


    //通过滑动ViewPage 的位置,返回 第position 位 的碎片,position从 0 角标开始
    @Override
    public Fragment getItem(int position) {
        if (position == 1) {
            return lifeStyleFragment;
        }
        return  fragment1;
    }

    // tabLayout 中 tab 的数量
    @Override
    public int getCount() {
        return mTitle.length;
    }

    //ViewPager与TabLayout绑定后，这里获取到PageTitle就是Tab的Text文本
    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mTitle[position];
    }
}
