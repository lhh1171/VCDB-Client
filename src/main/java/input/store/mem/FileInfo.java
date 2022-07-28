package input.store.mem;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;


//对应的是一个表的一个版本的基本元数据
public class FileInfo {
    //当ddl的时候，这些东西就会创建
    String DB_name;
    String Tab_name;
    int version_index;
    Long init_time;
    List<TFilter> tFilter;


    public static class TFilter {
        //    列族名称
        String cf_name;
        //    列族类型
        short type;
        //    最小长度,默认为0= Long.MIN_VALUE
        long min ;
        //    最大长度，默认为最大值= Long.MAX_VALUE
        long max ;
        //   是否唯一,默认false
        boolean isUnique ;

        public TFilter(String cf_name, short type, long min, long max, boolean isUnique) {
            this.cf_name = cf_name;
            this.type = type;
            this.min = min;
            this.max = max;
            this.isUnique = isUnique;
        }
    }
}
