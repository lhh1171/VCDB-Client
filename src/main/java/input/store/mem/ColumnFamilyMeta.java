package input.store.mem;

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
    byte[] data;
    public ColumnFamilyMeta(boolean unique,boolean isNull,long min,
                            long max,int version,byte method,byte type,
                            byte[] family, int fOffset, int fLength){

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
        //二进制
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
}
