package input.store.region;

import input.store.mem.ColumnFamilyMeta;

/**
 * @ClassName Trailer
 * @Description TODO
 * @Author lqc
 * @Date 2022/9/6 下午5:32
 * @Version 1.0
 */

/*
 * Trailer (offset of other member)(根据offset,可以拿到整个region的大小)包含RegionMeta (类）
 * pageCount
 * CF_Meta      这个列族的元数据，包括列族的限制（ColumnFamilyCell）
 * pageTrailer(KVRange(startKey,endKey))得固定
 * DataSet   (不分，一直往后累加，不用打乱排序）
 * */
public class Trailer {
    int pageCountIndex;
    int columnMetaIndex;
    int regionInfoIndex;
    int pageTrailerIndex;
    int dataSetIndex;
}
