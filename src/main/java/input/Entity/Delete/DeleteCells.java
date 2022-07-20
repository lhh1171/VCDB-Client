package input.Entity.Delete;


import input.Entity.Cell.TermCell;
import input.Entity.Post.RequestEntity;

import java.util.List;

/**
 * @author : wyy
 * @Date : 2022.7.11
 */
public class DeleteCells extends RequestEntity {
    private List<String> cf_names;

    private List<TermCell> terms;

    public void setCf_names(List<String> cf_names) {
        this.cf_names = cf_names;
    }

    public void setTerms(List<TermCell> terms) {
        this.terms = terms;
    }
}
