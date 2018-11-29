package test.study.wzm.xiaomingweather.DB;

import org.litepal.crud.DataSupport;
/**
 * 县
 */
public class County extends DataSupport {

    private int id;
    // countyName记录县的名字 用于列表显示的名字
    private String countyName;
    // weatherId记录县所对应的天气id
    private String weatherId;
    // cityId用于记录县所对应的市的代码
    private int cityId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCountyName() {
        return countyName;
    }

    public void setCountyName(String countyName) {
        this.countyName = countyName;
    }

    public String getWeatherId() {
        return weatherId;
    }

    public void setWeatherId(String weatherId) {
        this.weatherId = weatherId;
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }
}
