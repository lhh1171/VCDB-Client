package input.store.mem;


import java.util.HashMap;
import java.util.Map;

/**
 * @Author lhh1171
 * @Date 2022/4/2 下午3:13
 * @Version 1.8
 */
public class DB_Tools {

    Map<String,Integer> version_flag;
    Map<String,FileInfo> fileInfoList;
    public DB_Tools() {
        this.version_flag = new HashMap<>();
    }
}
