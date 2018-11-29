package test.study.wzm.xiaomingweather.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import test.study.wzm.xiaomingweather.Bean.WeatherBean;
import test.study.wzm.xiaomingweather.R;

public class LifeStyleRecyViewAdapter extends RecyclerView.Adapter<LifeStyleRecyViewAdapter.ViewHolder> {

    private List<WeatherBean.HeWeather6Bean.LifestyleBean> lifestyle;

    public LifeStyleRecyViewAdapter(List<WeatherBean.HeWeather6Bean.LifestyleBean> lifestyle) {
        this.lifestyle = lifestyle;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //加载列表中的 item
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.life_style_item,parent,false);
        //实例化viewholder
        LifeStyleRecyViewAdapter.ViewHolder holder = new LifeStyleRecyViewAdapter.ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        WeatherBean.HeWeather6Bean.LifestyleBean lifestyleBean = lifestyle.get(position);
        holder.type.setText(transToChar(lifestyleBean.getType()));
        holder.brf.setText(lifestyleBean.getBrf());
        holder.txt.setText(lifestyleBean.getTxt());
    }

    @Override
    public int getItemCount() {
        return lifestyle.size();
    }

    public String transToChar(String type) {
        switch (type) {
            case "comf":
                return "舒适度指数";
            case "cw":
                return "洗车指数";
            case "drsg":
                return "穿衣指数";
            case "flu":
                return "感冒指数";
            case "sport":
                return "运动指数";
            case "trav":
                return "旅游指数";
            case "uv":
                return "紫外线指数";
            case "air":
                return "空气污染扩散条件指数";
            case "ac":
                return "空调开启指数";
            case "gl":
                return "太阳镜指数";
            case "mu":
                return "化妆指数";
            case "airc":
                return "晾晒指数";
            case "ptfc":
                return "交通指数";
            case "fisin":
                return "钓鱼指数";
            case "spi":
                return "防晒指数";
                default:
                    return null;
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView type ;
        private TextView brf;
        private TextView txt;

        public ViewHolder(View view) {
            super(view);
            type = view.findViewById(R.id.type);
            brf = view.findViewById(R.id.brf);
            txt = view.findViewById(R.id.txt);
        }
    }
}
