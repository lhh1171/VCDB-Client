package input.Entity.Post;

import input.Entity.Cell.TermCell;

import java.util.List;

/**
 * @author : wyy
 * @Date : 2022.7.11
 */
public class MergeVersion extends RequestEntity {
    private String rowKey;
    private List<TermCell> terms;

    public void setRowKey(String rowKey) {
        this.rowKey = rowKey;
    }

    public void setTerms(List<TermCell> terms) {
        this.terms = terms;
    }
}
