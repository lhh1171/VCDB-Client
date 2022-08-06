package input.store.mem;

public class KeyValue {
    String rowKey;
    ValueNode valueListHead;

    public KeyValue(String rowKey, ValueNode valueListHead) {
        this.rowKey = rowKey;
        this.valueListHead = valueListHead;
    }

    public void insert(String cname, String value, long valueLength,short opsType) {
        ValueNode temp=valueListHead;
        while (true){
            temp=temp.next;
            if (temp==null){
                temp=new ValueNode(cname, value, valueLength,opsType);
                break;
            }
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
