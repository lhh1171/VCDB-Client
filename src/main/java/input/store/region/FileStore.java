package input.store.region;

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
     * MetaSet
     * DataSet   (不分，一直往后累加，不用打乱排序）
     * */
    private byte[] data = null;

    public FileStore() {
    }

    public FileStore(RegionInfo regionInfo, int dataIndexOffset, int metaIndexOffset, int MetaSetOffset, int DataSetOffset) {
        this.data = new byte[regionInfo.getRegionInfoLength() + regionInfo.getRegionInfoLengthSize() + 4 + 4 + 4 + 4];
    }
}
