package input.Entity.Cell;
/**
 * @author : wyy
 * @Date : 2022.7.11
 */
public class VersionTerm {
    String rowKey;
    int versionFrom=-1;
    int VersionTo=-1;

    public void setVersionFrom(int versionFrom) {
        this.versionFrom = versionFrom;
    }

    public void setRowKey(String rowKey) {
        this.rowKey = rowKey;
    }

    public void setVersionTo(int versionTo) {
        VersionTo = versionTo;
    }

    public int getVersionFrom() {
        return versionFrom;
    }
    public String getRowKey() {
        return rowKey;
    }
    public int getVersionTo() {
        return VersionTo;
    }
}
