package input.ENTITY;
/**
 * @author : wyy
 * @Date : 2022.7.11
 */
public class Value {
    /**
     * cf_name : 列族名称
     * c_name : 列名
     * value : 值
     */
    private String cf_name;
    private String c_name;
    private String value;

    public String getCf_name() {
        return cf_name;
    }

    public String getC_name() {
        return c_name;
    }

    public String getValue() {
        return value;
    }

    public void setCf_name(String cf_name) {
        this.cf_name = cf_name;
    }

    public void setC_name(String c_name) {
        this.c_name = c_name;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
