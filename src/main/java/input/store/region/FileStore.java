package input.store.region;

import input.entity.Cell.ColumnFamilyCell;
import input.store.mem.ColumnFamilyMeta;
import input.store.mem.KV;
import input.store.mem.KeyValueSkipListSet;
import input.util.Bytes;

import java.util.Iterator;
import java.util.concurrent.ConcurrentNavigableMap;

/**
 * @ClassName Region
 * @Description TODO
 * @Author lqc
 * @Date 2022/8/9 下午10:25
 * @Version 1.0
 */
/*Region的最小单位Store,一个store对应一个列族*/
public class FileStore {
    /*Trailer纪录了RegionMetaIndex、Data Index、Meta Index块的起始位置，Data Index和Meta Index索引的数量等。
     * meta 主要存放meta信息，即BloomFilter信息。
     * data 存储多个kv对*/
    /*
     * Trailer (offset of other member)(根据offset,可以拿到整个region的大小)包含RegionMeta (类）
     * MetaIndex int(每一个RegionMeta的物理地址,按照Key的顺序排列)(每个单位的大小固定)
     * DataIndex int(每一个KV的物理地址)
     * CF_Meta      这个列族的元数据，包括列族的限制（ColumnFamilyCell）
     * DataSet   (不分，一直往后累加，不用打乱排序）
     * */
    private byte[] data = null;
    private int length = 0;

    public byte[] getData() {
        return data;
    }

    public int getLength() {
        return length;
    }

    public FileStore() {
    }

//    public FileStore(RegionInfo regionInfo, int dataIndexOffset, int metaIndexOffset, int MetaSetOffset, int DataSetOffset) {
//        this.data = new byte[regionInfo.getRegionInfoLength() + regionInfo.getRegionInfoLengthSize() + 4 + 4 + 4 + 4];
//    }
    public FileStore(RegionInfo regionInfo, KeyValueSkipListSet dataSet, ColumnFamilyMeta columnFamilyMeta) {
        this.data = new byte[4 + regionInfo.getRegionInfoLength() + 4 +4+ dataSet.getByteSize() + 4 + columnFamilyMeta.getLength()];
        int dataSetCount=dataSet.size();
        int pos=0;
        pos= Bytes.putInt(this.data,pos,regionInfo.getRegionInfoLength());
        pos=Bytes.putBytes(this.data,pos,regionInfo.getData(),0,regionInfo.getRegionInfoLength());
        pos= Bytes.putInt(this.data,pos,dataSetCount);
        //获取key和value的set
        for (KV kv : dataSet) {
            pos = Bytes.putInt(this.data, pos, kv.getLength());
            pos = Bytes.putBytes(this.data, pos, kv.getData(), 0, kv.getLength());
        }
        pos= Bytes.putInt(this.data,pos,columnFamilyMeta.getLength());
        pos=Bytes.putBytes(this.data,pos,columnFamilyMeta.getData(),0,columnFamilyMeta.getLength());
        this.length=this.data.length;
    }

}
