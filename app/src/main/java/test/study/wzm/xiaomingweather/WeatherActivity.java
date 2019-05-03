package test.study.wzm.xiaomingweather;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import test.study.wzm.xiaomingweather.Adapter.FragmentAdapter;
import test.study.wzm.xiaomingweather.Bean.FocusWord;
import test.study.wzm.xiaomingweather.Bean.WeatherBean;
import test.study.wzm.xiaomingweather.Dialogs.ChooseAreaDialog;
import test.study.wzm.xiaomingweather.Fragment.Fragment1;
import test.study.wzm.xiaomingweather.Fragment.LifeStyleFragment;
import test.study.wzm.xiaomingweather.Utils.HttpUtil;
import test.study.wzm.xiaomingweather.Utils.Utility;

public class WeatherActivity extends AppCompatActivity {

    private ProgressDialog progressDialog;
    private TextView titleCity;

    private TextView tv_refresh;

    private TextView degreeText;

    private TextView weatherMiaoShu;

    private TextView bodyTemp;

    private TextView nengjiandu;

    private TextView fengxiang;

    private TextView shidu;

    private TextView qiya;

    public DrawerLayout drawerLayout;

    public ChooseAreaDialog chooseAreaDialog;

    private Button navButton;

    // public SwipeRefreshLayout swipeRefresh;
    private String mWeatherId;

    private ImageView bingPicImg;

    private ImageView weather_icon;

    private ViewPager viewPager;

    private FragmentAdapter myFragmentPagerAdapter;

    private TabLayout tabLayout;

    private TextView refresh_time;

    NavigationView navView;

    private TextView focus_word;
    private TextView focus_name;


    private List<WeatherBean.HeWeather6Bean.DailyForecastBean> forecastList = new ArrayList<>();
    private List<WeatherBean.HeWeather6Bean.LifestyleBean> lifestyleList = new ArrayList<>();
    // private List<WeatherBean.HeWeather6Bean.HourlyBean> hourlyList = new ArrayList<>();
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //隐藏状态栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_weather);

        initTextView();
        initListener();
        initNavView();

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String weatherString = prefs.getString("weather", null);
        if (weatherString != null) {
            //有缓存直接解析天气数据
            WeatherBean weather = Utility.handleWeatherResponse(weatherString);
            mWeatherId = weather.getHeWeather6().get(0).getBasic().getCid();
            forecastList.addAll(weather.getHeWeather6().get(0).getDaily_forecast());

            lifestyleList.addAll(weather.getHeWeather6().get(0).getLifestyle());
            // hourlyList.addAll(weather.getHeWeather6().get(0).getHourly());
            showWeatherInfo(weather);
        } else {
            //无缓存时去服务器查询天气
            mWeatherId = getIntent().getStringExtra("weather_id");
            requestWeather(mWeatherId);
        }

        String bingPic = prefs.getString("bing_pic", null);
        if (bingPic != null) {
            Glide.with(this).load(bingPic).into(bingPicImg);
        } else {
            loadBingPic();
        }
        //添加缓存,focus_word和focus_name空指针异常,不知道为什么
//        String focus_word_json = prefs.getString("focus_word_json", null);
//        if (focus_word_json != null) {
//            FocusWord focusWord = Utility.handleWordResponse(focus_word_json);
//            focus_word.setText(focusWord.getResult().getFamous_saying());
//            focus_name.setText("---  "+focusWord.getResult().getFamous_name());
//        }else {
//            loadFocusWord();
//        }

        initViewPager();
       // loadFocusWord();
    }

    private void initNavView() {
        navView.setCheckedItem(R.id.nav_home);
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_home:
                        drawerLayout.closeDrawers();
                        break;
                    case R.id.nav_city:
                        drawerLayout.closeDrawers();
                        chooseAreaDialog.show();
                        break;
                    case R.id.nav_calendar:
                        Intent intent = new Intent(WeatherActivity.this, CalendarActivity.class);
                        startActivity(intent);
//                        drawerLayout.closeDrawers();
                        break;
                }
                return true;
            }
        });
    }

    private void initViewPager() {
        showProgressDialog();
        //使用适配器将ViewPager与Fragment绑定在一起
        viewPager = findViewById(R.id.viewPager);
        myFragmentPagerAdapter = new FragmentAdapter(getSupportFragmentManager(), forecastList, lifestyleList);
        viewPager.setAdapter(myFragmentPagerAdapter);

        //将TabLayout与ViewPager绑定在一起
        tabLayout = findViewById(R.id.tl_tab);
        tabLayout.setupWithViewPager(viewPager);
        closeProgressDialog();
    }

    /**
     * 加载必应每日一图
     */
    private void loadBingPic() {
        String requestBingPic = "http://guolin.tech/api/bing_pic";

        HttpUtil.sendRequestWithOKhttp(requestBingPic, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String bingPic = response.body().string();
                editor.putString("bing_pic", bingPic);
                editor.apply();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Glide.with(WeatherActivity.this).load(bingPic).into(bingPicImg);
                    }
                });
            }
        });
    }

    /**
     * 加载名人名言
     * 2019.5.3发现名人名言api不能使用了,所以关闭名人名言更新功能
     */
    private void loadFocusWord() {
        String requestUrl = "http://api.avatardata.cn/MingRenMingYan/Random?key=1067a21929ab4a9f8d549801cf58b82e";
        focus_name = findViewById(R.id.focus_name);
        focus_word = findViewById(R.id.focus_word);
        HttpUtil.sendRequestWithOKhttp(requestUrl, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String responseText = null;
                        try {
                            responseText = response.body().string();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        FocusWord focusWord = Utility.handleWordResponse(responseText);
                        focus_word.setText(focusWord.getResult().getFamous_saying());
                        focus_name.setText("---  " + focusWord.getResult().getFamous_name());
                        //添加缓存
                        editor.putString("focus_word_json", responseText);
                        editor.apply();
                    }
                });

            }
        });
    }

    /**
     * 初始化监听器
     */
    public void initListener() {
        navButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
               // loadFocusWord();
            }
        });
//        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                requestWeather(mWeatherId);
//            }
//        });
        tv_refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestWeather(mWeatherId);
            }
        });

        chooseAreaDialog = new ChooseAreaDialog(WeatherActivity.this);

        titleCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseAreaDialog.show();
            }
        });

    }

    /**
     * 初始化TextView
     */
    public void initTextView() {
        titleCity = findViewById(R.id.title_city);
        tv_refresh = findViewById(R.id.title_refresh);
        degreeText = findViewById(R.id.degree_text);
        weatherMiaoShu = findViewById(R.id.weather_miaoshu);
        bodyTemp = findViewById(R.id.body_temp);
        nengjiandu = findViewById(R.id.tv_nengjiandu);
        fengxiang = findViewById(R.id.tv_fengxiang);
        shidu = findViewById(R.id.tv_shidu);
        qiya = findViewById(R.id.tv_qiya);
        drawerLayout = findViewById(R.id.drawer_layout);
        navButton = findViewById(R.id.nav_button);
        weather_icon = findViewById(R.id.weather_icon);
//        swipeRefresh = findViewById(R.id.swipe_refresh);
//        swipeRefresh.setColorSchemeResources(R.color.colorPrimary);
        bingPicImg = findViewById(R.id.bing_pic_img);
        refresh_time = findViewById(R.id.refresh_time);
        navView = findViewById(R.id.nav_view);
//        focus_word = navView.findViewById(R.id.focus_word);
//        focus_name = navView.findViewById(R.id.focus_name);
        editor = PreferenceManager.getDefaultSharedPreferences(WeatherActivity.this).edit();
    }

    /**
     * 根据天气id请求城市天气信息
     */
    public void requestWeather(final String weatherId) {
        showProgressDialog();
        loadBingPic();
        String weatherUrl = "https://free-api.heweather.com/s6/weather?location=" + weatherId + "&key=9fd05e5767e14b71a0b6d05cdd3058fc";
        HttpUtil.sendRequestWithOKhttp(weatherUrl, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(WeatherActivity.this, "获取天气失败", Toast.LENGTH_SHORT).show();
                        closeProgressDialog();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseText = response.body().string();
                final WeatherBean weather = Utility.handleWeatherResponse(responseText);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        System.out.print(weather.getHeWeather6());
                        if (weather != null && "ok".equals(weather.getHeWeather6().get(0).getStatus())) {
                            //将返回的json数据存入SharedPreference中,当做缓存
                            SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(WeatherActivity.this).edit();
                            editor.putString("weather", responseText);
                            editor.apply();
                            //mWeatherId 是城市天气的id,用于传入请求天气方法中的参数
                            mWeatherId = weather.getHeWeather6().get(0).getBasic().getCid();
                            //做这个判断是因为,forecastList是天气预报的数据,要作为参数传入FragmentAdapter中,拿来展示数据用,如果这个列表中已经有数据了,不作清空,那么旧数据就会加上新数据,导致数据变多,而且数据不正确
                            if (forecastList != null) {
                                forecastList.clear();
                            }
                            forecastList.addAll(weather.getHeWeather6().get(0).getDaily_forecast()); // 通过请求天气获得天气预报列表,这个列表作为初始化ViewPage时传入的参数

                            if (lifestyleList != null) {
                                lifestyleList.clear();
                            }
                            lifestyleList.addAll(weather.getHeWeather6().get(0).getLifestyle());

                            //当从选择城市界面中选中县后,更新ViewPager中RecyclerView中天气预报的列表,wegamecai牛逼
                            // 不用重新传入天气预报数据,
                            ((Fragment1) myFragmentPagerAdapter.getItem(0)).update();
                            ((LifeStyleFragment) myFragmentPagerAdapter.getItem(1)).update();

                            //显示布局上半部分天气信息
                            showWeatherInfo(weather);
                        } else {
                            Toast.makeText(WeatherActivity.this, "获取天气失败", Toast.LENGTH_SHORT).show();
                        }
                        //swipeRefresh.setRefreshing(false);
                        closeProgressDialog();
                        //loadFocusWord();
                    }
                });
            }
        });
    }

    /**
     * @param weather 天气实体类
     */
    private void showWeatherInfo(WeatherBean weather) {
        WeatherBean.HeWeather6Bean weather6Bean = weather.getHeWeather6().get(0);
        titleCity.setText(weather6Bean.getBasic().getLocation());
        degreeText.setText(weather6Bean.getNow().getTmp() + "°");
        weatherMiaoShu.setText("   " + weather6Bean.getNow().getCond_txt());
        bodyTemp.setText("体感 " + weather6Bean.getNow().getFl() + " °");
        nengjiandu.setText("能见度\n" + weather6Bean.getNow().getVis() + " km");
        fengxiang.setText(weather6Bean.getNow().getWind_dir() + "\n" + weather6Bean.getNow().getWind_sc() + "级");
        shidu.setText("湿度\n" + weather6Bean.getNow().getHum() + "%");
        qiya.setText("气压\n" + weather6Bean.getNow().getPres() + " hpa");
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        Date date = new Date(System.currentTimeMillis());
        String time = sdf.format(date);
        refresh_time.setText("刷新时间 " + time);
        String icon_code = weather6Bean.getNow().getCond_code();
        try {
            //将assets目录下的png图片转为Bitmap类
            Bitmap bitmap = BitmapFactory.decodeStream(getResources().getAssets().open("w" + icon_code + ".png"));
            weather_icon.setImageBitmap(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 关闭进度条
     */
    private void closeProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }

    /**
     * 显示进度条
     */
    private void showProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(WeatherActivity.this);
            progressDialog.setMessage("正在加载...");
            progressDialog.setCanceledOnTouchOutside(false);
        }
        progressDialog.show();
    }

}
