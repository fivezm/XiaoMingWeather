package test.study.wzm.xiaomingweather.DB;

import org.litepal.crud.DataSupport;
/**
 * 城市
 */
public class City extends DataSupport {

    private int id;
    // cityName 用于记录城市的名字 用于列表显示的名字
    private String cityName;
    // cityCode 用于记录市的代号,用来查询城市内的县
    private int cityCode;
    // provinceId 用于记录当前城市所属于的省的代号
    private int provinceId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public int getCityCode() {
        return cityCode;
    }

    public void setCityCode(int cityCode) {
        this.cityCode = cityCode;
    }

    public int getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(int provinceId) {
        this.provinceId = provinceId;
    }
}
