package input.Entity.Cell;

/**
 * @author : wyy
 * @Date : 2022.7.11
 */

public class Aggregate {
    /**
     * c_name : 列名
     * function : avg/min/max/sum(聚合)
     * as : 别名
     */
    private String c_name;
    private String function;
    private String as;

    public String getC_name() {
        return c_name;
    }

    public String getFunction() {
        return function;
    }

    public String getAs() {
        return as;
    }

    public void setC_name(String c_name) {
        this.c_name = c_name;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    public void setAs(String as) {
        this.as = as;
    }
}
