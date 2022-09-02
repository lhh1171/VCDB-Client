package input.store.region;

import input.util.Bytes;

//存储a region的具体信息(元数据)
public class RegionInfo {

//    //Region 创建的时间戳
//    long timeStamp;
//    private boolean split = false;
//    //该region对应的文件名
//    private String encodedName = null;
//    private byte[] endKey;
//    private byte[] startKey;
//    private String tableName = null;
    private final byte[] data;
    private int regionInfoLength = 0;

    public RegionInfo(byte[] data){
        this.data=data;
        this.regionInfoLength=data.length;
    }
    public RegionInfo(long timeStamp, boolean split, String encodedName, byte[] endKey, byte[] startKey, String tableName) {
        this.regionInfoLength = 8 + 1 + 4 + encodedName.getBytes().length + 4 + endKey.length + 4 + startKey.length + 4 + tableName.getBytes().length;
        byte spl=0;
        if (split){
            spl=1;
        }
        this.data=createByteArray(timeStamp,spl,encodedName,endKey,startKey,tableName);
        this.regionInfoLength=this.data.length;
    }
    public byte[] getData() {
        return data;
    }
    public int getRegionInfoLength() {
        return regionInfoLength;
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


    public long getTimeStamp() {
        return Bytes.toLong(this.data,0,8);
    }

    public boolean isSplit() {
        return this.data[9]!=0;
    }
    public int getNameLength(){
        return Bytes.toInt(this.data,9,4);
    }

    public String getEncodedName() {
        return Bytes.toString(this.data,13,getNameLength());
    }
    public int getEndKeyLength() {
        return Bytes.toInt(this.data,13+getNameLength(),4);
    }
    public byte[] getEndKey() {
        return Bytes.subByte(this.data,17+getNameLength(),getEndKeyLength());
    }
    public int getStartKeyLength(){
        return Bytes.toInt(this.data,17+getNameLength()+getEndKeyLength(),4);
    }
    public byte[] getStartKey() {
        return Bytes.subByte(this.data,21+getNameLength()+getEndKeyLength(),getStartKeyLength());
    }
    public int getTableNameLength(){
        return Bytes.toInt(this.data,21+getNameLength()+getEndKeyLength()+getStartKeyLength(),4);
    }
    public String getTableName() {
        return Bytes.toString(this.data,25+getNameLength()+getEndKeyLength()+getStartKeyLength(),getTableNameLength());
    }

}
