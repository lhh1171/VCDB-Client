package input.entity.Post;




import input.entity.Cell.JTableCell;
import input.entity.Cell.Order;
import input.entity.Cell.TermCell;

import java.util.List;
/**
 * @author : wyy
 * @Date : 2022.7.11
 */
public class MultiSearch extends RequestEntity {
    private int limit;
    private List<JTableCell> j_tables;

    private List<String> cf_names;

    private List<TermCell> terms;

    private List<Order> orders;
    public void setJ_tables(List<JTableCell> j_tables) {
        this.j_tables = j_tables;
    }

    public void setCf_names(List<String> cf_names) {
        this.cf_names = cf_names;
    }

    public void setTerms(List<TermCell> terms) {
        this.terms = terms;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }


    public void setLimit(int limit) {
        this.limit = limit;
    }
}
