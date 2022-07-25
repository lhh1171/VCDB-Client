package input.entity.Post;


import input.entity.Cell.AlterCell;

import java.util.List;

/**
 * @author : wyy
 * @Date : 2022.7.11
 */
public class AlterTable extends RequestEntity {
    private List<AlterCell> alter_cells;

    public void setAlter_cells(List<AlterCell> alter_cells) {
        this.alter_cells = alter_cells;
    }
}


