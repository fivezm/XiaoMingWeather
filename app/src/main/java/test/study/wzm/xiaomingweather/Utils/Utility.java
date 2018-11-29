package test.study.wzm.xiaomingweather.Utils;

import android.text.TextUtils;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import test.study.wzm.xiaomingweather.Bean.FocusWord;
import test.study.wzm.xiaomingweather.Bean.WeatherBean;
import test.study.wzm.xiaomingweather.DB.City;
import test.study.wzm.xiaomingweather.DB.County;
import test.study.wzm.xiaomingweather.DB.Province;

/**
 *  全国省份城市县处理工具类
 */
public class Utility {
    /**
     * 解析和处理服务器返回的省级数据 URL = http://guolin.tech/api/china
     * [{"id":1,"name":"北京"},{"id":2,"name":"上海"},{"id":3,"name":"天津"},{"id":4,"name":"重庆"},
     * {"id":5,"name":"香港"},{"id":6,"name":"澳门"},{"id":7,"name":"台湾"},{"id":8,"name":"黑龙江"},
     * {"id":9,"name":"吉林"},{"id":10,"name":"辽宁"},{"id":11,"name":"内蒙古"},{"id":12,"name":"河北"},
     * {"id":13,"name":"河南"},{"id":14,"name":"山西"},{"id":15,"name":"山东"},{"id":16,"name":"江苏"},
     * {"id":17,"name":"浙江"},{"id":18,"name":"福建"},{"id":19,"name":"江西"},{"id":20,"name":"安徽"},
     * {"id":21,"name":"湖北"},{"id":22,"name":"湖南"},{"id":23,"name":"广东"},{"id":24,"name":"广西"},
     * {"id":25,"name":"海南"},{"id":26,"name":"贵州"},{"id":27,"name":"云南"},{"id":28,"name":"四川"},
     * {"id":29,"name":"西藏"},{"id":30,"name":"陕西"},{"id":31,"name":"宁夏"},{"id":32,"name":"甘肃"},
     * {"id":33,"name":"青海"},{"id":34,"name":"新疆"}]
     */
    public static boolean handleProvinceResponse(String response){
        if (!TextUtils.isEmpty(response)){
            try {
                JSONArray allProvinces = new JSONArray(response);
                for(int i =0,len=allProvinces.length();i<len;i++) {
                    JSONObject provinceObject = allProvinces.getJSONObject(i);
                    Province province = new Province();
                    province.setProvinceName(provinceObject.getString("name"));
                    province.setProvinceCode(provinceObject.getInt("id"));
                    province.save();
                }
                return true;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * 解析和处理服务器返回的市级数据 URL = http://guolin.tech/api/china/23
     * [{"id":205,"name":"广州"},{"id":206,"name":"韶关"},{"id":207,"name":"惠州"},{"id":208,"name":"梅州"},
     * {"id":209,"name":"汕头"},{"id":210,"name":"深圳"},{"id":211,"name":"珠海"},{"id":212,"name":"顺德"},
     * {"id":213,"name":"肇庆"},{"id":214,"name":"湛江"},{"id":215,"name":"江门"},{"id":216,"name":"河源"},
     * {"id":217,"name":"清远"},{"id":218,"name":"云浮"},{"id":219,"name":"潮州"},{"id":220,"name":"东莞"},
     * {"id":221,"name":"中山"},{"id":222,"name":"阳江"},{"id":223,"name":"揭阳"},{"id":224,"name":"茂名"},{"id":225,"name":"汕尾"},{"id":350,"name":"佛山"}]
     */
    public static boolean handleCityResponse(String response ,int provinceId){
        if (!TextUtils.isEmpty(response)) {
            try {
                JSONArray allCities = new JSONArray(response);
                for (int i=0,len=allCities.length();i<len;i++) {
                    JSONObject cityObject = allCities.getJSONObject(i);
                    City city = new City();
                    city.setCityName(cityObject.getString("name"));
                    city.setCityCode(cityObject.getInt("id"));
                    city.setProvinceId(provinceId);
                    city.save();
                }
                return true;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * 解析和处理服务器返回的县级数据 URL = http://guolin.tech/api/china/23/215
     * [{"id":1584,"name":"江门","weather_id":"CN101281101"},{"id":1585,"name":"开平","weather_id":"CN101281103"},
     * {"id":1586,"name":"新会","weather_id":"CN101281104"},{"id":1587,"name":"恩平","weather_id":"CN101281105"},
     * {"id":1588,"name":"台山","weather_id":"CN101281106"},{"id":1589,"name":"蓬江","weather_id":"CN101281107"},
     * {"id":1590,"name":"鹤山","weather_id":"CN101281108"},{"id":1591,"name":"江海","weather_id":"CN101281109"}]
     */
    public static boolean handleCountyResponse(String response, int cityId) {
        if (!TextUtils.isEmpty(response)) {
            try {
                JSONArray allCounties = new JSONArray(response);
                for (int i =0,len = allCounties.length();i<len;i++) {
                    JSONObject countyObject = allCounties.getJSONObject(i);
                    County county = new County();
                    county.setCountyName(countyObject.getString("name"));
                    county.setWeatherId(countyObject.getString("weather_id"));
                    county.setCityId(cityId);
                    county.save();
                }
                return true;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * 将返回的json数据解析成WeatherBean实体类,该类装的是天气信息
     */
    public static WeatherBean handleWeatherResponse(String response) {
        return new Gson().fromJson(response, WeatherBean.class);
    }

    /**
     * 将返回的json数据解析成FocusWord实体类,该类装的是名人名言
     * @param response 返回的json数据
     * @return
     */
    public static FocusWord handleWordResponse(String response) {
        return new Gson().fromJson(response, FocusWord.class);
    }

}
