package input.store.mem;

import java.util.concurrent.atomic.AtomicLong;

//对应的是一个表的一个版本的一个列族的数据
public class MemStore {

    // Used to track when to flush
    long timeOfOldestEdit = Long.MAX_VALUE;
    //lock for memStore version
    AtomicLong size;
    public KeyValueSkipListSet kvSet;
    // Snapshot of memStore.  Made for flusher.
    volatile KeyValueSkipListSet snapshot;
    public MemStore(){
        size=new AtomicLong(0);
        kvSet =new KeyValueSkipListSet(new KV.KVComparator());
    }

    public long add(final KV kv) {
        KV toAdd = maybeCloneWithAllocator(kv);
        return internalAdd(toAdd);
    }

    private KV maybeCloneWithAllocator(KV kv) {
        return null;
    }

    long timeOfOldestEdit() {
        return timeOfOldestEdit;
    }

    private boolean addToKVSet(KV e) {
        boolean b = this.kvSet.add(e);
        setOldestEditTimeToNow();
        return b;
    }

    private boolean removeFromKVSet(KV e) {
        boolean b = this.kvSet.remove(e);
        setOldestEditTimeToNow();
        return b;
    }

    void setOldestEditTimeToNow() {
        if (timeOfOldestEdit == Long.MAX_VALUE) {
            timeOfOldestEdit = System.currentTimeMillis();
        }
    }

    /**
     * Internal version of add() that doesn't clone KVs with the
     * allocator, and doesn't take the lock.
     * <p>
     * Callers should ensure they already have the read lock taken
     */
    private long internalAdd(final KV toAdd) {
        long s = heapSizeChange(toAdd, addToKVSet(toAdd));
        this.size.addAndGet(s);
        return s;
    }

    /*
     * Calculate how the MemStore size has changed.  Includes overhead of the
     * backing Map.
     * @param kv
     * @param notPresent True if the kv was NOT present in the set.
     * @return Size
     */
    private long heapSizeChange(final KV kv, final boolean notPresent) {
        return notPresent ? align(kv) : 0;
    }

    //将kv对齐,8的整数
    private long align(KV kv) {
        return 8;
    }
}
