package input.store.mem;


import org.junit.Test;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * @ClassName TestKV
 * @Description TODO
 * @Author lqc
 * @Date 2022/8/17 下午4:55
 * @Version 1.0
 */

public class TestKV {
    @Test
    public void testKV(){
        byte[] row="row1".getBytes(StandardCharsets.UTF_8);
        byte[] family="fam1".getBytes(StandardCharsets.UTF_8);
        List<KV.ValueNode> values=new ArrayList<>();
        for (int i = 1; i <= 2; i++) {
            long time= (new Date()).getTime();
            KV.Type type=KV.byteToType((byte) 4);
            byte[] qualifier=("qualifier"+i).getBytes(StandardCharsets.UTF_8);
            byte[] value=("value"+i).getBytes(StandardCharsets.UTF_8);
            values.add(new KV.ValueNode(time,type,qualifier,0,qualifier.length,value,0,value.length));
        }
//        for (KV.ValueNode valueNode:values) {
//            System.out.println(valueNode.getTime());
//            System.out.println(valueNode.getType());
//            System.out.println(valueNode.getQLength());
//            System.out.println(valueNode.getQualifier());
//            System.out.println(valueNode.getVLength());
//            System.out.println(valueNode.getValue());
//        }
        KV kv=new KV(row,0,row.length,family,0,family.length,values);
        System.out.println(kv.getRowKey());
        System.out.println(kv.getFamily());
        List<KV.ValueNode> vv=kv.getValues();
        System.out.println("...........................");
        for (KV.ValueNode valueNode:vv) {
            System.out.println(valueNode.getTime());
            System.out.println(valueNode.getType());
            System.out.println(valueNode.getQLength());
            System.out.println(valueNode.getQualifier());
            System.out.println(valueNode.getVLength());
            System.out.println(valueNode.getValue());
        }
    }
}
