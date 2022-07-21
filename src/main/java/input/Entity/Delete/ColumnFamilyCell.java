package input.Entity.Delete;


/**
 * @author : wyy
 * @Date : 2022.7.11
 */
public class ColumnFamilyCell{
    private String cf_name;
    private String type;
    private double min;
    private double max;
    private boolean unique;
    private boolean isNull;
    private int Version=Integer.MAX_VALUE;
    private String method;

    public String getCf_name() {
        return cf_name;
    }

    public String getType() {
        return type;
    }

    public double getMin() {
        return min;
    }

    public double getMax() {
        return max;
    }

    public boolean isUnique() {
        return unique;
    }

    public boolean isNull() {
        return isNull;
    }

    public int getVersion() {
        return Version;
    }

    public String getMethod() {
        return method;
    }

    public void setCf_name(String cf_name) {
        this.cf_name = cf_name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setMin(double min) {
        this.min = min;
    }

    public void setMax(double max) {
        this.max = max;
    }

    public void setUnique(boolean unique) {
        this.unique = unique;
    }

    public void setNull(boolean aNull) {
        isNull = aNull;
    }

    public void setVersion(int version) {
        Version = version;
    }

    public void setMethod(String method) {
        this.method = method;
    }
}