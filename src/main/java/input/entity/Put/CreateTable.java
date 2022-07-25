package input.entity.Put;




import input.entity.Cell.ColumnFamilyCell;
import input.entity.Post.RequestEntity;

import java.util.List;

/**
 * @author : wyy
 * @Date : 2022.7.11
 */
public class CreateTable extends RequestEntity {

    private List<ColumnFamilyCell> column_family;

    public void setColumn_family(List<ColumnFamilyCell> column_family) {
        this.column_family = column_family;
    }
}