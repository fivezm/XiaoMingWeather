package test.study.wzm.xiaomingweather.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import test.study.wzm.xiaomingweather.Bean.WeatherBean;
import test.study.wzm.xiaomingweather.R;

// RecyclerView 的 每小时天气 适配器
public class HourlyRecyViewAdapter extends RecyclerView.Adapter<HourlyRecyViewAdapter.ViewHolder> {

    //上下文拿来获取assets目录下的资源,用于ImageView设置本地图片
    private Context context;

    //每小时天气列表
    private List<WeatherBean.HeWeather6Bean.HourlyBean> hourlyList;

    public HourlyRecyViewAdapter(Context context, List<WeatherBean.HeWeather6Bean.HourlyBean> hourlyList) {
        this.context = context;
        this.hourlyList = hourlyList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //加载列表中的 item
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.hourly_weather_item, parent, false);
        //实例化viewholder
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        WeatherBean.HeWeather6Bean.HourlyBean hourlyBean = hourlyList.get(position);
        holder.cond_txt.setText(hourlyBean.getCond_txt());
        holder.hourly_time.setText(hourlyBean.getTime());
        holder.cloud.setText("云量 : "+hourlyBean.getCloud());
        holder.hum.setText("相对湿度 : "+hourlyBean.getHum());
        holder.pres.setText("大气压强 : "+hourlyBean.getPres());
        holder.wind_spd.setText("风速 : "+hourlyBean.getWind_spd());
        holder.tmp.setText(hourlyBean.getTmp());
        try {
            InputStream in = context.getAssets().open("w" + hourlyBean.getCond_code() + ".png");
            Bitmap bitmap = BitmapFactory.decodeStream(in);
            holder.itemImage.setImageBitmap(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return hourlyList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView hourly_time;
        private TextView cond_txt;
        private TextView wind_spd;
        private TextView hum;
        private TextView pres;
        private ImageView itemImage;
        private TextView cloud;
        private TextView tmp;

        public ViewHolder(View view) {
            super(view);
            hourly_time = view.findViewById(R.id.hourly_time);
            cond_txt = view.findViewById(R.id.cond_txt);
            wind_spd = view.findViewById(R.id.wind_spd);
            hum = view.findViewById(R.id.hum);
            pres = view.findViewById(R.id.pres);
            cloud = view.findViewById(R.id.cloud);
            tmp = view.findViewById(R.id.tmp);
            itemImage = view.findViewById(R.id.item_image);
        }
    }
}
