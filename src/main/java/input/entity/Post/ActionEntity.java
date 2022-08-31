package input.entity.Post;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ActionEntity {
    String Method;
    String Url;
    HashMap<String,Object> RegularAttribute;
    Map <String,List<HashMap<String, String>>> CompoundAttribute;

    public ActionEntity() {
    }

    public ActionEntity(String method, String url) {
        Method = method;
        Url = url;
    }
    public boolean containKey(String key){
        if (RegularAttribute!=null&&CompoundAttribute!=null){
            return (RegularAttribute.containsKey(key)||CompoundAttribute.containsKey(key));
        }else {
            return false;
        }
    }
    public void setMethod(String method) {
        Method = method;
    }

    public void setUrl(String url) {
        Url = url;
    }
    public void addRegularAttribute(String key,Object obj){
        if (RegularAttribute==null){
            HashMap<String,Object> map=new HashMap<String, Object>();
            map.put(key,obj);
            setRegularAttribute(map);
        }else {
            this.RegularAttribute.put(key,obj);
        }
    }
    public void addCompoundAttribute(String key,List<HashMap<String, String>> list){
        if (CompoundAttribute==null){
            Map <String,List<HashMap<String, String>>> map=new HashMap<String, List<HashMap<String, String>>>();
            map.put(key,list);
            setCompoundAttribute(map);
        }else {
            this.CompoundAttribute.put(key,list);
        }
    }
    public void setRegularAttribute(HashMap<String, Object> regularAttribute) {
        RegularAttribute = regularAttribute;
    }

    public void setCompoundAttribute(Map<String, List<HashMap<String, String>>> compoundAttribute) {
        CompoundAttribute = compoundAttribute;
    }

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

    @Override
    public String toString() {
        return "ActionEntity{" +
                "Method='" + Method + '\'' +
                ", Url='" + Url + '\'' +
                ", RegularAttribute=" + RegularAttribute +
                ", CompoundAttribute=" + CompoundAttribute +
                '}';
    }
}


