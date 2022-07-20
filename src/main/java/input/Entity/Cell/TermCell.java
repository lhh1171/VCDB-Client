package input.Entity.Cell;

/**
 * @author : wyy
 * @Date : 2022.7.11
 */
public class TermCell {
    /**
     * cf_name : 列族名称
     * c_name : 列名
     * version-from : 原来版本号
     * version-to : 新版本号
     * max : 最大值
     * size : 大小
     * min : 最小值
     * like : 模糊(%表示多个字符，_表示一个字符)
     * tablename : 表名
     */

    private String cf_name;
    private String c_name;
    private long size=0;
    private double max=Double.MAX_VALUE;
    private boolean equivalence=false;
    private double min=Double.MIN_VALUE;
    private String like;

    public String getCf_name() {
        return cf_name;
    }

    public String getC_name() {
        return c_name;
    }

    public long getSize() {
        return size;
    }

    public double getMax() {
        return max;
    }

    public boolean getEquivalence() {
        return equivalence;
    }

    public double getMin() {
        return min;
    }

    public String getLike() {
        return like;
    }

    public void setCf_name(String cf_name) {
        this.cf_name = cf_name;
    }

    public void setC_name(String c_name) {
        this.c_name = c_name;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public void setMax(double max) {
        this.max = max;
    }

    public void setEquivalence(boolean equivalence) {
        this.equivalence = equivalence;
    }

    public void setMin(double min) {
        this.min = min;
    }

    public void setLike(String like) {
        this.like = like;
    }
}
