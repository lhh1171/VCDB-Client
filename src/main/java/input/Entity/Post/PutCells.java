package input.Entity.Post;



import input.Entity.Cell.Value;

import java.util.List;

/**
 * @author : wyy
 * @Date : 2022.7.11
 */
public class PutCells extends RequestEntity {
    private String rowKey;

    private List<Value> values;

    public void setRowKey(String rowKey) {
        this.rowKey = rowKey;
    }

    public void setValues(List<Value> values) {
        this.values = values;
    }
}


