package input.Entity.Post;


import input.Entity.Cell.TermCell;
import input.Entity.Cell.Value;

import java.util.List;

/**
 * @author : wyy
 * @Date : 2022.7.11
 */
public class UpdateCells extends RequestEntity {
    private List<TermCell> terms;

    private List<Value> values;

    public void setTerms(List<TermCell> terms) {
        this.terms = terms;
    }

    public void setValues(List<Value> values) {
        this.values = values;
    }

}
