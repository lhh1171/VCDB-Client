package input;




import input.ENTITY.JTableCell;
import input.ENTITY.Order;
import input.ENTITY.TermCell;

import java.util.List;
/**
 * @author : wyy
 * @Date : 2022.7.11
 */
public class MultiSearch extends RequestEntity {
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



}
