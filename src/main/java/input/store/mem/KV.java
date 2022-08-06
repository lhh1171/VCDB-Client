package input.store.mem;

import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.Tag;

import java.util.List;

public class KV {
    private byte [] data = null;  // an immutable byte array that contains the KV
    private int offset = 0;  // offset into bytes buffer KV starts at
    private int length = 0;  // length of the KV starting from offset.
    public  KV(final byte [] row, final int roffset, final int rlength,
               final byte [] family, final int foffset, final int flength,
               final byte [] qualifier, final int qoffset, final int qlength,
               final long timestamp, final KeyValue.Type type,
               final byte [] value, final int voffset, final int vlength,
               final List<Tag> tags){
        this.data = createByteArray(row, roffset, rlength,
                family, foffset, flength, qualifier, qoffset, qlength,
                timestamp, type, value, voffset, vlength, tags);
        this.length = data.length;
        this.offset = 0;

    }

    //很多不必要的属性length可为0
    private byte[] createByteArray(byte[] row, int roffset, int rlength,
                                   byte[] family, int foffset, int flength,
                                   byte[] qualifier, int qoffset, int qlength,
                                   long timestamp, KeyValue.Type type, byte[] value,
                                   int voffset, int vlength, List<Tag> tags) {
        return null;
    }

    public KV(){

    }
}
