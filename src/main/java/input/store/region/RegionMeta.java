package input.store.region;

import input.store.mem.KeyValueSkipListSet;

import java.util.HashMap;

/**
 * @ClassName RegionMeta
 * @Description TODO
 * @Author lqc
 * @Date 2022/8/13 下午1:43
 * @Version 1.0
 */
/*
* PD提取的类，保存的是整个Region Server的元数据*/
    //单独存起来
public class RegionMeta {

    /*fileCount,pageCount*/
    byte[] MetaByte;
    /*key(db+table+cf)---encodeNames(fileCount{(nameLength,name).....})*/
    /*encodeName---keyRange(startKey,endKey)*/
    String metaName;//文件名字
    HashMap<byte[],byte[]> fileMap=new HashMap<>();
    HashMap<byte[],byte[]> rangMap=new HashMap<>();
}
