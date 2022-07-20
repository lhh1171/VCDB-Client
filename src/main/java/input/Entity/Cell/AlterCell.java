package input.Entity.Cell;
/**
 * @author : wyy
 * @Date : 2022.7.11
 */
public class AlterCell {

    private String cfName;

    private String old_cfName;

    private String method;

    public String getCfName() {
        return cfName;
    }

    public String getOld_cfName() {
        return old_cfName;
    }

    public String getMethod() {
        return method;
    }

    public void setCfName(String cfName) {
        this.cfName = cfName;
    }

    public void setOld_cfName(String old_cfName) {
        this.old_cfName = old_cfName;
    }

    public void setMethod(String method) {
        this.method = method;
    }
}
