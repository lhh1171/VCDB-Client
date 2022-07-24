package input.Server;


import input.Analyzer.EX;
import input.Entity.Post.ActionEntity;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.util.CharsetUtil;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.*;


public class JsonToRestFulEntityHandler extends ChannelInboundHandlerAdapter {
//    @Override
//    public void channelActive(ChannelHandlerContext ctx) throws Exception {
//        String ip=ctx.channel().remoteAddress().toString();
//        String[] a= ip.split(":");
//        System.out.println(a[0]+"-->"+"已连接");
//    }
//    @Override
//    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
//        String ip=ctx.channel().remoteAddress().toString();
//        String[] a= ip.split(":");
//        System.out.println(a[0]+"-->"+"已断开");
//    }
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ActionEntity actionEntity= toRestfulEntity(msg);
        System.out.println("server接收到\n"+actionEntity);
        EX.DFA2(actionEntity);
        ctx.fireChannelActive();
    }
    public ActionEntity toRestfulEntity(Object msg) throws JSONException {
        FullHttpRequest fullHttpRequest =(FullHttpRequest) msg;
        ActionEntity actionEntity = new ActionEntity(fullHttpRequest.getMethod().name(),fullHttpRequest.getUri());
        String content = fullHttpRequest.content().toString(CharsetUtil.UTF_8);
        System.out.println(content);
        return jsonToJavaOfRestfulEntity(content,actionEntity);
    }
    //解析json转换为实体
    public  ActionEntity jsonToJavaOfRestfulEntity(String content,ActionEntity actionEntity) throws JSONException {
        if ("".equals(content)){
            return  actionEntity;
        }
        JSONObject jsonObject = new JSONObject(content.trim());
        Iterator keys = jsonObject.keys();
        while(keys.hasNext()) {
            String key = (String) keys.next();
            Object o = jsonObject.get(key);
            if (o instanceof JSONArray) {
                System.out.println(key+": jsonArray类型");
                addCompoundAttribute(key,(JSONArray) o,actionEntity);
            }else {
                System.out.println(key+": kv类型");
                addRegularAttribute(key,o,actionEntity);
            }
        }
        return actionEntity;
    }

    private void addRegularAttribute(String key,Object o, ActionEntity actionEntity) {
        //看当前key是否存在
        if (actionEntity.containKey(key)){
            System.err.println("key重复");
        }
        if (o instanceof String){
            actionEntity.addRegularAttribute(key,o);
        }else {
            actionEntity.addRegularAttribute(key,String.valueOf(o));
        }

    }

    private void addCompoundAttribute(String key,JSONArray array, ActionEntity actionEntity) throws JSONException {
        //看当前key是否存在
        if (actionEntity.containKey(key)){
            System.err.println("key重复");
        }
        List<String> cfNames=new ArrayList<String>();
        if ("cf_names".equalsIgnoreCase(key)){
            for(int i=0;i<array.length();i++) {
                try{
                    String str = array.getString(i);
                    cfNames.add(str);
                }catch (JSONException ignored){
                    System.err.println("cfNames错误");
                }
            }
            actionEntity.addRegularAttribute(key,cfNames);
        } else {
            List<HashMap<String, String>> hashMapList=new ArrayList<HashMap<String, String>>();
            for(int i=0;i<array.length();i++) {
                String key2;
                try{
                    HashMap<String, String> hashMap=new HashMap<String, String>();
                    JSONObject jo=array.getJSONObject(i);
                    Iterator iterator = jo.keys();
                    while(iterator.hasNext()){
                        key2 = (String) iterator.next();
                        hashMap.put(key2,jo.getString(key2));
                    }
                    hashMapList.add(hashMap);
                }catch (JSONException ignored){
                    System.err.println("json类型错误");
                }
            }
            actionEntity.addCompoundAttribute(key,hashMapList);
        }
    }
}



