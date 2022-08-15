package input.store.region;

//存储a region的具体信息(元数据)
public class RegionInfo {
    private int regionInfoLengthSize = 0;
    private int regionInfoLength = 0;
    //Region 创建的时间戳
    long timeStamp;
    private boolean split = false;
    //该region对应的文件名
    private String encodedName = null;
    private byte[] endKey;
    private byte[] startKey;
    private String tableName = null;

    public RegionInfo(long timeStamp, boolean split, String encodedName, byte[] endKey, byte[] startKey, String tableName) {
        this.encodedName = encodedName;
        this.endKey = endKey;
        this.startKey = startKey;
        this.timeStamp = timeStamp;
        this.split = split;
        this.tableName = tableName;
        this.regionInfoLength = 8 + 1 + encodedName.getBytes().length + endKey.length + startKey.length + tableName.getBytes().length;
        //encodedNameLengthSize+endKeyLengthSize+startKeyLengthSize+tableNameLengthSize
        this.regionInfoLengthSize = 8 + 8 + 8 + 8;
    }

    public int getRegionInfoLengthSize() {
        return regionInfoLengthSize;
    }

    public int getRegionInfoLength() {
        return regionInfoLength;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public boolean isSplit() {
        return split;
    }

    public String getEncodedName() {
        return encodedName;
    }

    public byte[] getEndKey() {
        return endKey;
    }

    public byte[] getStartKey() {
        return startKey;
    }

    public String getTableName() {
        return tableName;
    }

    public void setRegionInfoLengthSize(int regionInfoLengthSize) {
        this.regionInfoLengthSize = regionInfoLengthSize;
    }

    public void setRegionInfoLength(int regionInfoLength) {
        this.regionInfoLength = regionInfoLength;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public void setSplit(boolean split) {
        this.split = split;
    }

    public void setEncodedName(String encodedName) {
        this.encodedName = encodedName;
    }

    public void setEndKey(byte[] endKey) {
        this.endKey = endKey;
    }

    public void setStartKey(byte[] startKey) {
        this.startKey = startKey;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }
}
