package input.store.region;

//存储a region的具体信息(元数据)
public class RegionInfo {
    //该region对应的文件名
    private String encodedName = null;
    private byte [] endKey;
    private byte [] startKey;
    //Region 创建的时间戳
    long timeStamp;
    private boolean split = false;
    private String tableName = null;
}
