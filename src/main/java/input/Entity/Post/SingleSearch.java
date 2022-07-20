package input.Entity.Post;




import input.Entity.Cell.Aggregate;
import input.Entity.Cell.Order;
import input.Entity.Cell.TermCell;

import java.util.List;
/**
 * @author : wyy
 * @Date : 2022.7.11
 */
public class SingleSearch extends RequestEntity {
    private List<String> cf_names;

    private List<TermCell> terms;

    private List<Order> orders;

    private List<Aggregate> aggregate;

    public void setCf_names(List<String> cf_names) {
        this.cf_names = cf_names;
    }

    public void setTerms(List<TermCell> terms) {
        this.terms = terms;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public void setAggregate(List<Aggregate> aggregate) {
        this.aggregate = aggregate;
    }
}
