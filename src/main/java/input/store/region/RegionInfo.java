package input.store.region;

import input.util.Bytes;

//存储a region的具体信息(元数据)
public class RegionInfo {
    private int regionInfoLength = 0;
    //Region 创建的时间戳
    long timeStamp;
    private boolean split = false;
    //该region对应的文件名
    private String encodedName = null;
    private byte[] endKey;
    private byte[] startKey;
    private String tableName = null;
    private final byte[] data;

    public byte[] getData() {
        return data;
    }

    public RegionInfo(long timeStamp, boolean split, String encodedName, byte[] endKey, byte[] startKey, String tableName) {
        this.encodedName = encodedName;
        this.endKey = endKey;
        this.startKey = startKey;
        this.timeStamp = timeStamp;
        this.split = split;
        this.tableName = tableName;
        this.regionInfoLength = 8 + 1 + encodedName.getBytes().length + endKey.length + startKey.length + tableName.getBytes().length;
        byte spl=0;
        if (split){
            spl=1;
        }
        this.data=createByteArray(timeStamp,spl,encodedName,endKey,startKey,tableName);
        this.regionInfoLength=this.data.length;
    }

    private byte[] createByteArray(long timeStamp, byte spl,
                                   String encodedName, byte[] endKey,
                                   byte[] startKey, String tableName) {
        int pos=0;
        byte[] bytes = new byte[8+1+4+encodedName.getBytes().length+4+endKey.length+4+startKey.length+4+tableName.getBytes().length];
        pos=Bytes.putLong(bytes,pos,timeStamp);
        pos= Bytes.putByte(bytes,pos,spl);
        pos=Bytes.putInt(bytes,pos,encodedName.getBytes().length);
        pos=Bytes.putBytes(bytes,pos,encodedName.getBytes(),0,encodedName.getBytes().length);
        pos=Bytes.putInt(bytes,pos,endKey.length);
        pos=Bytes.putBytes(bytes,pos,endKey,0,endKey.length);
        pos=Bytes.putInt(bytes,pos,startKey.length);
        pos=Bytes.putBytes(bytes,pos,startKey,0,startKey.length);
        pos=Bytes.putInt(bytes,pos,tableName.getBytes().length);
        pos=Bytes.putBytes(bytes,pos,tableName.getBytes(),0,tableName.getBytes().length);
        return bytes;
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
