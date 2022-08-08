package input.store.mem;

import input.util.Bytes;

import java.util.List;

public class KV {
    private byte[] data = null;  // an immutable byte array that contains the KV
    private int offset = 0;  // offset into bytes buffer KV starts at
    private int length = 0;  // length of the KV starting from offset.

    public KV(final byte[] row, final int rOffset, final int rLength,
              final byte[] family, final int fOffset, final int fLength,
              final byte[] qualifier, final int qOffset, final int qLength,
              final List<ValueNode> values) {
        this.data = createByteArray(row, rOffset, rLength,
                family, fOffset, fLength, values);
        this.length = data.length;
        this.offset = 0;
    }

    //row is rowKey
    //很多不必要的属性length可为0
    private byte[] createByteArray(byte[] row, int rOffset, int rLength,
                                   byte[] family, int fOffset, int fLength,
                                   final List<ValueNode> values) {
        checkParameters(row, rLength, family, fLength);
        // Calculate length of tags area
        int valuesLength = 0;
        if (values != null && !values.isEmpty()) {
            for (ValueNode v : values) {
                //valueLengthSize+length
                valuesLength += v.getLength()+8;
            }
        }
        int keyLength = (int) getKeyDataStructureSize(rLength, fLength);
        byte[] bytes = new byte[(int) getKeyValueDataStructureSize(rLength, fLength, valuesLength)];
        // Write key, value and key row length.
        int pos = 0;
        pos = Bytes.putInt(bytes, pos, keyLength);
        pos = Bytes.putShort(bytes, pos, (short)(rLength & 0x0000ffff));
        pos = Bytes.putBytes(bytes, pos, row, rOffset, rLength);
        pos = Bytes.putByte(bytes, pos, (byte) (fLength & 0x0000ff));
        if (fLength != 0) {
            pos = Bytes.putBytes(bytes, pos, family, fOffset, fLength);
        }
        // Add the tags after the value part
        if (valuesLength > 0) {
            pos = Bytes.putLong(bytes, pos, valuesLength);
            for (ValueNode valueNode : values) {
                pos = Bytes.putBytes(bytes, pos, valueNode.getBuffer(), valueNode.getOffset(), valueNode.getLength());
            }
        }
        return bytes;
    }

    private long getKeyDataStructureSize(int rLength, int fLength) {
        //ROWKey_LENGTH_SIZE + FAMILY_LENGTH_SIZE
        return 2 + 1 + rLength + fLength;
    }

    private long getKeyValueDataStructureSize(int rLength, int fLength, int valuesLength) {
        return getKeyDataStructureSize(rLength, fLength) + valuesLength;
    }

    public KV() {

    }


    public static enum Type {
        Minimum((byte) 0),
        Put((byte) 4),

        Delete((byte) 8),
        DeleteFamilyVersion((byte) 10),
        DeleteColumn((byte) 12),
        DeleteFamily((byte) 14),

        // Maximum is used when searching; you look from maximum on down.
        Maximum((byte) 255);

        private final byte code;

        Type(final byte c) {
            this.code = c;
        }


        public byte getCode() {
            return this.code;
        }

        /**
         * Cannot rely on enum ordinals . They change if item is removed or moved.
         * Do our own codes.
         *
         * @param b
         * @return Type associated with passed code.
         */
        public static Type codeToType(final byte b) {
            for (Type t : Type.values()) {
                if (t.getCode() == b) {
                    return t;
                }
            }
            throw new RuntimeException("Unknown code " + b);
        }
    }

    public String getRowKey() {
        return null;
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
        private byte[] bytes = null;  // an immutable byte array that contains the KV

        //qualifier,timestamp,actionType,value
        public ValueNode(final long timestamp, final Type type,
                         final byte[] qualifier, final int qOffset, final int qLength,
                         final byte[] value, final int vOffset, final int vLength) {
            this.bytes = createByteArray(timestamp, type, qualifier, qOffset, qLength, value, vOffset, vLength);
        }

        private byte[] createByteArray(long timestamp, Type type,
                                       byte[] qualifier, int qOffset, int qLength,
                                       byte[] value, int vOffset, int vLength) {

            return null;
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

}
