package input.ENTITY;
/**
 * @author : wyy
 * @Date : 2022.7.11
 */
public class JTableCell {
    /**
     * tabname : 表名
     * method : inner/left/right/full
     */

    private String tableName;
    private String method;

    public String getTableName() {
        return tableName;
    }

    public String getMethod() {
        return method;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public void setMethod(String method) {
        this.method = method;
    }
}
