package input.store.region;

/**
 * @ClassName Region
 * @Description TODO
 * @Author lqc
 * @Date 2022/8/9 下午10:25
 * @Version 1.0
 */

public class VCRegion {
    /*
    * Trailer (offset of other member)
    * FileInfo
    * MetaIndex (每一个RegionMeta的物理地址)
    * DataIndex (每一个KV的物理地址)
    * MetaSet
    * DataSet*/
    private byte[] data = null;
}
