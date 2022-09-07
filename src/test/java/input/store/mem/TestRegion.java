package input.store.mem;

import input.store.region.FileStore;
import input.store.region.FileStoreMeta;
import input.util.Bytes;
import org.junit.Test;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @ClassName input.store.mem.TestRegion
 * @Description TODO
 * @Author lqc
 * @Date 2022/9/1 下午2:51
 * @Version 1.0
 */

public class TestRegion {
    @Test
    public void  testRegion(){
        ColumnFamilyMeta cfMeta=new ColumnFamilyMeta(true,false,1,100,ColumnFamilyMeta.byteToCFType((byte) 44),"fff1".getBytes(),0,"fff1".getBytes().length);
        System.out.println(cfMeta.isUnique());
        System.out.println(cfMeta.isNull());
        System.out.println(cfMeta.getMin());
        System.out.println(cfMeta.getMax());
        System.out.println(cfMeta.getType());
        System.out.println(cfMeta.getCFLength());
        System.out.println(cfMeta.getCf_name());
        System.out.println("===================================");

        FileStoreMeta fileStoreMeta =new FileStoreMeta((new Date()).getTime(),false,"/vcdb/re1","r1".getBytes(),"r2".getBytes(),"t1");
        System.out.println(fileStoreMeta.getTimeStamp());
        System.out.println(fileStoreMeta.isSplit());
        System.out.println(fileStoreMeta.getEncodedName());
        System.out.println(Bytes.toString(fileStoreMeta.getStartKey()) );
        System.out.println(Bytes.toString(fileStoreMeta.getEndKey()) );
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
        KV kv=new KV(row,0,row.length,family,0,family.length,values);
        KeyValueSkipListSet kvs = new KeyValueSkipListSet(new KV.KVComparator());
        kvs.add(kv);

        System.out.println("===================================");
        FileStore fileStore=new FileStore(fileStoreMeta,kvs,cfMeta);
        FileStoreMeta fileStoreMeta1 = fileStore.getRegionInfo();
        System.out.println(fileStoreMeta1.getTimeStamp());
        System.out.println(fileStoreMeta1.isSplit());
        System.out.println(fileStoreMeta1.getEncodedName());
        System.out.println(Bytes.toString(fileStoreMeta1.getStartKey()) );
        System.out.println(Bytes.toString(fileStoreMeta1.getEndKey()) );
        System.out.println("===================================");
        KeyValueSkipListSet dataSet = fileStore.getDataSet();
        for (KV kv1:dataSet){
            System.out.println(kv1.getRowKey());
            System.out.println(kv1.getFamily());
            List<KV.ValueNode> vv=kv1.getValues();
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
        System.out.println("===================================");
        ColumnFamilyMeta meta = fileStore.getMeta();
        System.out.println(meta.isUnique());
        System.out.println(meta.isNull());
        System.out.println(meta.getMin());
        System.out.println(meta.getMax());
        System.out.println(meta.getType());
        System.out.println(meta.getCFLength());
        System.out.println(meta.getCf_name());
        System.out.println("===================================");
    }
}
