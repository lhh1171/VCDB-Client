package input.Entity.Post;

import input.Entity.Cell.TermCell;
import input.Entity.Cell.VersionTerm;

import java.util.List;

/**
 * @author : wyy
 * @Date : 2022.7.11
 */
public class MergeVersion extends RequestEntity {
    private String rowKey;
    private List<VersionTerm> terms;

    public void setTerms(List<VersionTerm> terms) {
        this.terms = terms;
    }

    public void setRowKey(String rowKey) {
        this.rowKey = rowKey;
    }

}
