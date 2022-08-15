package input.store.mem;

import input.util.Bytes;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class KV {
    private byte[] data = null;  // an immutable byte array that contains the KV
    private int length = 0;  // length of the KV starting from offset.

    public byte[] getData() {
//        String s=new String("123456");
//        byte[] bytes = s.getBytes(StandardCharsets.UTF_8);

        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public String getRowKey() {
        int keyLength=Bytes.toInt(this.data,0,4);
        int rLength=Bytes.toInt(this.data,8,4);
        String row=Bytes.toString(this.data,16,rLength);
        int fLength=Bytes.toInt(this.data,16+rLength,4);
        String family=Bytes.toString(this.data,20+rLength,fLength);
        long valueLength=Bytes.toLong(this.data,20+rLength+fLength,8);
        List<ValueNode> valueNodes = byteToValues(this.data, 28 + rLength + fLength, valueLength);
        return new String(this.data, StandardCharsets.UTF_8);
    }

    private List<ValueNode> byteToValues(byte[] data, int offset, long length) {
        List<ValueNode> valueNodes=new ArrayList<ValueNode>();
        int pos=0;
        int valueCount=Bytes.toInt(this.data,pos,4);
        for (int i = 0; i < valueCount; i++) {
            long timestamp=Bytes.toLong(this.data,pos,8);
            pos=pos+8;
            byte type=this.data[pos];
            pos++;
            int qLength=Bytes.toInt(this.data,pos,4);
            pos=pos+4;
            String qualifier=Bytes.toString(this.data,pos,qLength);
            pos=pos+qLength;
            int vLength=Bytes.toInt(this.data,pos,4);
            pos=pos+4;
            String value=Bytes.toString(this.data,pos,vLength);
            pos=pos+vLength;
        }
//        valueNodes.add();
        return valueNodes;
    }

    public String getQualifier() {
        return null;
    }

    public String getTime() {
        return null;
    }

    public byte getType() {
        return 0;
    }

    public String getValue() {
        return null;
    }

    public KV(final byte[] row, final int rOffset, final int rLength,
              final byte[] family, final int fOffset, final int fLength,
              final List<ValueNode> values) {
        this.data = createByteArray(row, rOffset, rLength,
                family, fOffset, fLength, values);
        this.length = this.data.length;
    }

    public KV() {

    }

    //row is rowKey
    //很多不必要的属性length可为0
    private byte[] createByteArray(byte[] row, int rOffset, int rLength,
                                   byte[] family, int fOffset, int fLength,
                                   final List<ValueNode> values) {
        checkParameters(row, rLength, family, fLength);
        // Calculate length of tags area
        int valuesLength = 0;
        int valueCount=0;
        if (values != null && !values.isEmpty()) {
            for (ValueNode v : values) {
                //valueLengthSize
                valuesLength += v.getLength();
                valueCount++;
            }
        }
        int keyLength = (int) getKeyDataStructureSize(rLength, fLength);
        byte[] bytes = new byte[(int) getKeyValueDataStructureSize(rLength, fLength, valuesLength)+4];
        // Write key, value and key row length.
        int pos = 0;
        pos = Bytes.putInt(bytes, pos, keyLength);
        pos = Bytes.putInt(bytes, pos, rLength);
        pos = Bytes.putBytes(bytes, pos, row, rOffset, rLength);
        pos = Bytes.putInt(bytes, pos,fLength);
        if (fLength != 0) {
            pos = Bytes.putBytes(bytes, pos, family, fOffset, fLength);
        }
        // Add the tags after the value part
        if (valuesLength > 0) {
            pos = Bytes.putInt(bytes, pos, valueCount);
            for (ValueNode valueNode : values) {
                pos = Bytes.putBytes(bytes, pos, valueNode.getBuffer(), valueNode.getOffset(), valueNode.getLength());
            }
        }
        return bytes;
    }

    private long getKeyDataStructureSize(int rLength, int fLength) {
        //ROWKey_LENGTH_SIZE + FAMILY_LENGTH_SIZE
        return 4 + 4 + rLength + fLength;
    }

    private long getKeyValueDataStructureSize(int rLength, int fLength, int valuesLength) {
        return getKeyDataStructureSize(rLength, fLength) + valuesLength;
    }

    public static enum Type {
        CreateDB((byte) 0),
        createTable((byte) 4),
        DeleteDB((byte) 8),
        DeleteTable((byte) 10),
        OpenTransaction((byte) 12),
        CloseTransaction((byte) 14),
        PutCells((byte) 16),
        AlterTable((byte) 18),
        MergeVersion((byte) 20),
        UseVersion((byte) 22),
        ShowVersion((byte) 24),
        SingleSearch((byte) 26),
        DeleteCells((byte) 28),
        UpdateCells((byte) 30),
        MultiSearch((byte) 32),
        DeleteVersion((byte) 34);


        private final byte code;

        Type(final byte c) {
            this.code = c;
        }

        public byte getCode() {
            return this.code;
        }

    }

    private static void checkParameters(final byte[] row, final int rlength,
                                        final byte[] family, int flength)
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
    }

    public static class ValueNode {
        //length=lengthSize+length
        private int length = 0;
        private int offset = 0;
        private final byte[] bytes;  // an immutable byte array that contains the KV

        //qualifier,timestamp,actionType,value
        public ValueNode(final long timestamp, final Type type,
                         final byte[] qualifier, final int qOffset, final int qLength,
                         final byte[] value, final int vOffset, final int vLength) {
            this.bytes = createByteArray(timestamp, type, qualifier, qOffset, qLength, value, vOffset, vLength);
        }

        private byte[] createByteArray(long timestamp, Type type,
                                       byte[] qualifier, int qOffset, int qLength,
                                       byte[] value, int vOffset, int vLength) {
            //timestampSize+typeSize+qLengthSize+vLengthSize+qLength+vLength
            this.length = 8 + 1 + 2 + 2 + qLength + vLength;
            byte[] bytes = new byte[this.length];
            int pos = 0;
            pos = Bytes.putLong(bytes, pos, timestamp);
            pos = Bytes.putByte(bytes, pos, type.getCode());
            pos = Bytes.putInt(bytes, pos, qLength);
            pos = Bytes.putBytes(bytes, pos, qualifier, qOffset, qLength);
            pos = Bytes.putInt(bytes, pos, vLength);
            if (vLength != 0) {
                pos = Bytes.putBytes(bytes, pos, value, vOffset, vLength);
            }
            this.offset = pos;
            return bytes;
        }

        int getLength() {
            return this.length;
        }

        public byte[] getBuffer() {
            return this.bytes;
        }

        int getOffset() {
            return this.offset;
        }
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

//    public static void main(String[] args) {
//        System.out.println(Type.Delete.code);
//    }
}
