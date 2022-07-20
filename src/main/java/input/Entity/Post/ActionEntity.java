package input.Entity.Post;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActionEntity {
    String Method;
    String Url;
    HashMap<String,Object> RegularAttribute;
    Map <String,List<HashMap<String,String>>> CompoundAttribute;

    public String getMethod() {
        return Method;
    }

    public String getUrl() {
        return Url;
    }

    public HashMap<String, Object> getRegularAttribute() {
        return RegularAttribute;
    }

    public Map<String, List<HashMap<String, String>>> getCompoundAttribute() {
        return CompoundAttribute;
    }
}


