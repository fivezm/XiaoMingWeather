package test.study.wzm.xiaomingweather.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.util.List;

import test.study.wzm.xiaomingweather.Bean.WeatherBean;
import test.study.wzm.xiaomingweather.R;

// RecyclerView 的 天气预报适配器
public class ForecastAdapter extends RecyclerView.Adapter<ForecastAdapter.MyViewHolder> {
    //上下文拿来获取assets目录下的资源,用于ImageView设置本地图片
    private Context context;

    //天气预报列表
    private List<WeatherBean.HeWeather6Bean.DailyForecastBean> forecastList;

    public ForecastAdapter(List<WeatherBean.HeWeather6Bean.DailyForecastBean> forecastList,Context context) {
        this.forecastList = forecastList;
        this.context = context;
    }

    static class MyViewHolder extends RecyclerView.ViewHolder
    {
        private TextView date;
        private TextView sunrise;
        private TextView sunset;
        private TextView tmp_max;
        private TextView tmp_min;
        private ImageView itemImage;
        private TextView wind_speed;
        private TextView press;
        private TextView cond;
        private TextView wind_dir;

        public MyViewHolder(View view)
        {
            super(view);
            date = view.findViewById(R.id.date);
            sunrise = view.findViewById(R.id.sunrise);
            sunset = view.findViewById(R.id.sunset);
            tmp_max = view.findViewById(R.id.tmp_max);
            tmp_min = view.findViewById(R.id.tmp_min);
            itemImage = view.findViewById(R.id.item_image);
            wind_speed = view.findViewById(R.id.wind_speed);
            press = view.findViewById(R.id.press);
            cond = view.findViewById(R.id.cond);
            itemImage = view.findViewById(R.id.item_image);
            wind_dir = view.findViewById(R.id.wind_dir);
            Log.d("启动了MyViewHolder", "ForecastAdapter: dfafdadfasfdadf");
        }
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //加载列表中的 item
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.daily_forecast_item,parent,false);
        //实例化viewholder
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    /**
     * 用于加载数据
     * @param holder
     * @param position 根据RecyclerView列表中选中的位置,可以拿到第几位置的数据
     */
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        WeatherBean.HeWeather6Bean.DailyForecastBean forecastBean = forecastList.get(position);
        holder.press.setText("大气压强 : "+forecastBean.getPres()+" hpa");
        holder.wind_speed.setText("风速 : "+forecastBean.getWind_spd()+" km/h");
        holder.tmp_min.setText("最低温度 :  "+forecastBean.getTmp_min()+" ℃");
        holder.tmp_max.setText("最高温度 :  "+forecastBean.getTmp_max()+" ℃");
        holder.sunset.setText("日落 :   "+forecastBean.getSs());
        holder.sunrise.setText("日出 :  "+forecastBean.getSr());
        holder.wind_dir.setText("风向 : "+forecastBean.getWind_dir());

        holder.date.setText(forecastBean.getDate());
        holder.cond.setText(forecastBean.getCond_txt_d());
        try {
            Bitmap bitmap = BitmapFactory.decodeStream(context.getAssets().open("w"+forecastBean.getCond_code_d()+".png"));
            holder.itemImage.setImageBitmap(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    @Override
    public int getItemCount() {
        return forecastList.size();          //返回个每页的数量
    }
}
