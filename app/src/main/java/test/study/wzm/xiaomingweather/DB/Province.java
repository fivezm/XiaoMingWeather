package test.study.wzm.xiaomingweather.DB;

import org.litepal.crud.DataSupport;

/**
 * 省份
 */
public class Province extends DataSupport {
    // id 每个实体类都应该有的字段
    private int id;
    // provinceName记录省的名字 ,用于列表显示的名字
    private String provinceName;
    // provinceCode记录省的代号,用来查询省内的城市
    private int provinceCode;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public int getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(int provinceCode) {
        this.provinceCode = provinceCode;
    }
}
