package input.Entity.Post;

/**
 * @author : wyy
 * @Date : 2022.7.11
 */
public class DeleteVersion extends RequestEntity {
    private String rowKey;
    private int version;

    public void setRowKey(String rowKey) {
        this.rowKey = rowKey;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}
