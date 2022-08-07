package input.store.mem;

import org.apache.hadoop.hbase.HConstants;
import org.apache.hadoop.hbase.KeyValue;

import java.util.List;

public class KV {
    private byte [] data = null;  // an immutable byte array that contains the KV
    private int offset = 0;  // offset into bytes buffer KV starts at
    private int length = 0;  // length of the KV starting from offset.
    public  KV(final byte [] row, final int rOffset, final int rLength,
               final byte [] family, final int fOffset, final int fLength,
               final byte [] qualifier, final int qOffset, final int qLength,
               final long timestamp, final Type type,
               final List<ValueNode> values){
        this.data = createByteArray(row, rOffset, rLength,
                family, fOffset, fLength, qualifier, qOffset, qLength,
                timestamp, type, values);
        this.length = data.length;
        this.offset = 0;

    }

    //row is rowKey
    //很多不必要的属性length可为0
    private byte[] createByteArray(byte[] row, int rOffset, int rLength,
                                   byte[] family, int fOffset, int fLength,
                                   byte[] qualifier, int qOffset, int qLength,
                                   long timestamp, Type type,
                                   final List<ValueNode> values) {
        return null;
    }

    public KV(){

    }
    public static enum Type{

    }
    public String getRowKey(){
        return null;
    }

    public String getQualifier(){
        return null;
    }
    public String getTime(){
        return null;
    }
    public byte getType(){
        return 0;
    }
    public String getValue(){
        return null;
    }
    private static void checkParameters(final byte [] row, final int rlength,
                                        final byte [] family, int flength, int qlength, int vlength)
            throws IllegalArgumentException {
        if (rlength > Short.MAX_VALUE) {
            throw new IllegalArgumentException("Row > " + Short.MAX_VALUE);
        }
        if (row == null) {
            throw new IllegalArgumentException("Row is null");
        }
        // Family length
        flength = family == null ? 0 : flength;
        if (flength > Byte.MAX_VALUE) {
            throw new IllegalArgumentException("Family > " + Byte.MAX_VALUE);
        }
        // Qualifier length
        if (qlength > Integer.MAX_VALUE - rlength - flength) {
            throw new IllegalArgumentException("Qualifier > " + Integer.MAX_VALUE);
        }
        // Key length
        long longKeyLength = getKeyDataStructureSize(rlength, flength, qlength);
        if (longKeyLength > Integer.MAX_VALUE) {
            throw new IllegalArgumentException("keylength " + longKeyLength + " > " +
                    Integer.MAX_VALUE);
        }
        // Value length
        if (vlength > HConstants.MAXIMUM_VALUE_LENGTH) { // FindBugs INT_VACUOUS_COMPARISON
            throw new IllegalArgumentException("Value length " + vlength + " > " +
                    HConstants.MAXIMUM_VALUE_LENGTH);
        }
    }
    public static class ValueNode{
        private byte [] bytes = null;  // an immutable byte array that contains the KV
        //timestamp,actionType,value
        public ValueNode(final long timestamp, final KeyValue.Type type,
                         final byte [] value, final int vOffset, final int vLength){
            this.bytes=createByteArray(timestamp, type, value, vOffset, vLength);
        }

        private byte[] createByteArray(long timestamp, KeyValue.Type type,
                                       byte[] value, int vOffset, int vLength) {
            return null;
        }
    }
    public static long getKeyDataStructureSize(int rlength, int flength, int qlength) {
        return KeyValue.KEY_INFRASTRUCTURE_SIZE + rlength + flength + qlength;
    }
    public static class KVComparator implements RawComparator<ValueNode> {
        @Override
        public int compare(byte[] b1, int s1, int l1, byte[] b2, int s2, int l2) {
            return 0;
        }

        @Override
        public int compare(ValueNode o1, ValueNode o2) {
            return 0;
        }
    }

}
