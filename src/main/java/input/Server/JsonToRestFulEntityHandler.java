package input.Server;


import input.Entity.Post.ActionEntity;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.util.CharsetUtil;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;


public class JsonToRestFulEntityHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        String ip=ctx.channel().remoteAddress().toString();
        String[] a= ip.split(":");
        System.out.println(a[0]+"-->"+"已连接");
    }
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        String ip=ctx.channel().remoteAddress().toString();
        String[] a= ip.split(":");
        System.out.println(a[0]+"-->"+"已断开");
    }
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ActionEntity actionEntity= toRestfulEntity(msg);
        ctx.fireChannelActive();
    }
    public ActionEntity toRestfulEntity(Object msg) throws JSONException {
        FullHttpRequest fullHttpRequest =(FullHttpRequest) msg;
        String content = fullHttpRequest.content().toString(CharsetUtil.UTF_8);
        System.out.println(content);
        return jsonToJavaOfRestfulEntity(content);
    }
    //解析json转换为实体
    public  ActionEntity jsonToJavaOfRestfulEntity(String content) throws JSONException {
        ActionEntity actionEntity = new ActionEntity();
        JSONObject jsonObject = new JSONObject(content.trim());
        Iterator<String> keys = jsonObject.keys();
        while(keys.hasNext()) {
            String key = keys.next();
            if (jsonObject.get(key) instanceof JSONObject) {
                // do something with jsonObject here
            }
        }

        return actionEntity;
    }
}



