package input.store.region;

import input.store.mem.ColumnFamilyMeta;
import input.store.mem.KV;
import input.store.mem.KeyValueSkipListSet;
import input.util.Bytes;

import java.util.ArrayList;
import java.util.List;


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
     * pageCount
     * CF_Meta      这个列族的元数据，包括列族的限制（ColumnFamilyCell）
     * regionInfo
     * pageTrailer(KVRange(startKey,endKey))得固定
     * DataSet   (不分，一直往后累加，不用打乱排序）
     * */
    //pageCountMax=2^31/2^11=2^20
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

    public int getPageTrailerLength(List<KVRange> pageTrailer) {
        int count=0;
        for (KVRange kvRange:pageTrailer){
            count+=4+kvRange.getLength();
        }
        return count;
    }

    public byte[] getHeadPage(){
        Trailer trailer = getTrailer();
        int dataSetIndex = trailer.getDataSetIndex();
        return Bytes.subByte(this.data,0,dataSetIndex);
    }

    public FileStore( ColumnFamilyMeta columnFamilyMeta, FileStoreMeta fileStoreMeta,
                      List<KVRange> pageTrailer, KeyValueSkipListSet dataSet) {
        //fileStoreMeta也会随时更新
        this.data = new byte[4*1024*16];
        int pos = 16;
        pos = Bytes.putInt(this.data, pos, columnFamilyMeta.getLength());
        pos = Bytes.putBytes(this.data, pos, columnFamilyMeta.getData(), 0, columnFamilyMeta.getLength());
        pos = Bytes.putInt(this.data, pos, fileStoreMeta.getLength());
        pos = Bytes.putBytes(this.data, pos, fileStoreMeta.getData(), 0, fileStoreMeta.getLength());
        pos = Bytes.putInt(this.data, pos, pageTrailer.size());
        for (KVRange kvRange : pageTrailer) {
            pos = Bytes.putInt(this.data, pos, kvRange.getLength());
            pos = Bytes.putBytes(this.data, pos, kvRange.getData(), 0, kvRange.getLength());
        }
        pos=(pos%(1024*4)+1)*(1024*4);
        //分页管理可以改变trailer的参数
        Trailer trailer=new Trailer(
                4*4,
                4*4+4+columnFamilyMeta.getLength(),
                4*4+4+columnFamilyMeta.getLength()+4+fileStoreMeta.getLength(),
                pos);
        Bytes.putInt(this.data, 0, trailer.getColumnMetaIndex());
        Bytes.putInt(this.data, 4, trailer.getRegionInfoIndex());
        Bytes.putInt(this.data, 8, trailer.getPageTrailerIndex());
        Bytes.putInt(this.data, 12, trailer.getDataSetIndex());
        int dataSetCount = dataSet.size();
        pos = Bytes.putInt(this.data, pos, dataSetCount);
        //获取key和value的set
        for (KV kv : dataSet) {
            pos = Bytes.putInt(this.data, pos, kv.getLength());
            pos = Bytes.putBytes(this.data, pos, kv.getData(), 0, kv.getLength());
        }
        this.length = this.data.length;
    }

//        public FileStore(RegionInfo regionInfo, int dataIndexOffset, int metaIndexOffset, int MetaSetOffset, int DataSetOffset) {
//        this.data = new byte[regionInfo.getRegionInfoLength() + regionInfo.getRegionInfoLengthSize() + 4 + 4 + 4 + 4];
//    }
    public boolean isSplitHeadPage(int length) {
        int headEnd=getRegionInfoIndex()+ getPageTrailerLength(getPageTrailer());
        return (length + (headEnd) % ((1024 * 4)))/(1024*4)!=0;
    }

    public Trailer getTrailer() {
        return new Trailer(Bytes.toInt(this.data, 0, 4),
                Bytes.toInt(this.data, 4, 4),
                Bytes.toInt(this.data, 8, 4),
                Bytes.toInt(this.data, 12, 4));
    }

    public int getColumnFamilyMetaLength() {
        return Bytes.toInt(this.data, 16, 4);
    }

    public byte[] getColumnFamilyMeta() {
        return Bytes.subByte(this.data, 20, getColumnFamilyMetaLength());
    }

    public int getFileStoreMetaLength() {
        return Bytes.toInt(this.data, 20 + getColumnFamilyMetaLength(), 4);
    }

    public byte[] getFileStoreMeta() {
        return Bytes.subByte(this.data, 24 + getColumnFamilyMetaLength(), getColumnFamilyMetaLength());
    }

    public List<KVRange> getPageTrailer() {
        Trailer trailer = getTrailer();
        int pos = trailer.getPageTrailerIndex();
        List<KVRange> pageTrailer = new ArrayList<>();
        int kvRangeCount = Bytes.toInt(this.data, pos, 4);
        pos += 4;
        for (int i = 0; i < kvRangeCount; i++) {
            int rangeLength = Bytes.toInt(this.data, pos, 4);
            pos += 4;
            pageTrailer.add(new KVRange(Bytes.subByte(this.data, pos, rangeLength)));
            pos += rangeLength;
        }
        return pageTrailer;
    }

    public int getColumnMetaIndex(){
        return Bytes.toInt(this.data,0,4);
    }
    public int getRegionInfoIndex(){
        return Bytes.toInt(this.data,4,4);
    }
    public int getTrailerIndex(){
        return Bytes.toInt(this.data,8,4);
    }
    public int getDataSetIndex(){
        return Bytes.toInt(this.data,12,4);
    }

    /*----------------------------------------------------*/

    public void setColumnMetaIndex(int offset){
        Bytes.putInt(this.data, 0, offset);
    }

    public void setRegionInfoIndex(int offset){
        Bytes.putInt(this.data, 4, offset);
    }

    public void setTrailerIndex(int offset){
        Bytes.putInt(this.data, 8, offset);
    }

    public void setDataSetIndex(int offset){
        Bytes.putInt(this.data, 12, offset);
    }

    public void updateColumnFamilyMeta(ColumnFamilyMeta columnFamilyMeta){
        int columnFamilyMetaIndex=getColumnMetaIndex();
        byte[] oldHeadPage=getHeadPage();

    }
    public void updateFileStoreMeta(FileStoreMeta fileStoreMeta){
        int fileStoreMetaIndex=getRegionInfoIndex();

    }
    public void updatePageTrailer(KVRange kvRange){
        int pageTrailer=getTrailerIndex();

    }
    //update/add
    public void setData(KV kv){
        int dataSetIndex=getDataSetIndex();

    }
    public KeyValueSkipListSet getDataSet() {
        Trailer trailer = getTrailer();
        int pos = trailer.getDataSetIndex();
        KeyValueSkipListSet kvs = new KeyValueSkipListSet(new KV.KVComparator());
        int kvCount = Bytes.toInt(this.data, pos, 4);
        pos += 4;
        for (int i = 0; i < kvCount; i++) {
            int kvLength = Bytes.toInt(this.data, pos, 4);
            pos += 4;
            kvs.add(new KV(Bytes.subByte(this.data, pos, kvLength)));
            pos += kvLength;
        }
        return kvs;
    }

}
