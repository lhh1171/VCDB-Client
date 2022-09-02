package input.store.mem;

import input.util.Bytes;

/**
 * @ClassName ColumnFamilyMeta
 * @Description TODO
 * @Author lqc
 * @Date 2022/8/20 下午9:19
 * @Version 1.0
 */
/*
    long min=Long.MIN_VALUE;
    long max=Long.MAX_VALUE;
    boolean unique;
    boolean isNull;
    int Version=Integer.MAX_VALUE;
    byte method;
    byte type;
    String cf_name;
*/
public class ColumnFamilyMeta {
    private byte[] data=null;
    private int length = 0;

    public static ColumnFamilyMeta getInstance(byte[] data) {
        return new ColumnFamilyMeta(data);
    }

    public byte[] getData() {
        return data;
    }
    public int getLength() {
        return length;
    }
    public ColumnFamilyMeta(boolean unique,boolean isNull,long min,
                            long max,CFType type,
                            byte[] family, int fOffset, int fLength){
        byte uni=0;
        byte isNil=0;
        if (unique){
            uni=1;
        }
        if (isNull){
            isNil=1;
        }
        this.data = createByteArray(uni, isNil, min,
                max,  type, family,fOffset,fLength);
        this.length = this.data.length;
    }

    public ColumnFamilyMeta(byte[] data){
        this.data=data;
        this.length=data.length;
    }

    private byte[] createByteArray(byte unique, byte isNull, long min,
                                   long max, CFType type,
                                   byte[] family, int fOffset, int fLength) {
        byte[] bytes = new byte[1+1+8+8+4+1+4+fLength];
        int pos=0;
        pos= Bytes.putByte(bytes,pos,unique);
        pos=Bytes.putByte(bytes,pos,isNull);
        pos=Bytes.putLong(bytes,pos,min);
        pos=Bytes.putLong(bytes,pos,max);
        pos=Bytes.putByte(bytes,pos,type.getCode());
        pos=Bytes.putInt(bytes,pos,fLength);
        if (fLength != 0) {
            pos = Bytes.putBytes(bytes, pos, family, fOffset, fLength);
        }
        return bytes;
    }
    public boolean isUnique() {
        return data[0] != 0;
    }
    public boolean isNull() {
        return data[1]!=0;
    }
    public long getMin() {
        return Bytes.toLong(this.data,2,8);
    }

    public long getMax() {
        return Bytes.toLong(this.data,10,8);
    }

    public CFType getType() {
        return byteToCFType(data[18]);
    }
    public int getCFLength() {
        return Bytes.toInt(this.data,19,4);
    }
    public String getCf_name() {
        return Bytes.toString(this.data,23,getCFLength());
    }

    public static enum CFType {
        TINYINT((byte) 42),
        SMALLINT((byte) 44),
        INTEGER((byte) 46),
        BIGINT((byte) 48),
        FLOAT((byte) 50),
        TIMESTAMP((byte) 52),
        CHAR((byte) 54),
        VARCHAR((byte) 56),
        //二进制文件
        LONGBLOB((byte) 58),
        ;
        private final byte code;
        CFType(final byte c) {
            this.code = c;
        }
        public byte getCode() {
            return this.code;
        }
    }
    public static CFType byteToCFType(byte b){
        switch (b){
            case 42:
                return CFType.TINYINT;
            case 44:
                return CFType.SMALLINT;
            case 46:
                return CFType.INTEGER;
            case 48:
                return CFType.BIGINT;
            case 50:
                return CFType.FLOAT;
            case 52:
                return CFType.TIMESTAMP;
            case 54:
                return CFType.CHAR;
            case 56:
                return CFType.VARCHAR;
            case 58:
                return CFType.LONGBLOB;
            default:
                return null;
        }
    }
}
