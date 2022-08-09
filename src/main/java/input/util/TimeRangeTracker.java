package input.util;

import input.store.mem.KV;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;


/**
 * Stores the minimum and maximum timestamp values (both are inclusive).
 * Can be used to find if any given time range overlaps with its time range
 * MemStores use this class to track its minimum and maximum timestamps.
 * When writing StoreFiles, this information is stored in meta blocks and used
 * at read time to match against the required TimeRange.
 */
public class TimeRangeTracker implements Writable {

  long minimumTimestamp = -1;
  long maximumTimestamp = -1;

  /**
   * Default constructor.
   * Initializes TimeRange to be null
   */
  public TimeRangeTracker() {

  }

  /**
   * Copy Constructor
   * @param trt source TimeRangeTracker
   */
  public TimeRangeTracker(final TimeRangeTracker trt) {
    this.minimumTimestamp = trt.getMinimumTimestamp();
    this.maximumTimestamp = trt.getMaximumTimestamp();
  }

  public TimeRangeTracker(long minimumTimestamp, long maximumTimestamp) {
    this.minimumTimestamp = minimumTimestamp;
    this.maximumTimestamp = maximumTimestamp;
  }

//  /**
//   * Update the current TimestampRange to include the timestamp from KeyValue
//   * If the Key is of type DeleteColumn or DeleteFamily, it includes the
//   * entire time range from 0 to timestamp of the key.
//   * @param kv the KeyValue to include
//   */
//  public void includeTimestamp(final KV kv) {
//    includeTimestamp(kv.getTimestamp());
//    if (kv.isDeleteColumnOrFamily()) {
//      includeTimestamp(0);
//    }
//  }
//
//  /**
//   * Update the current TimestampRange to include the timestamp from Key.
//   * If the Key is of type DeleteColumn or DeleteFamily, it includes the
//   * entire time range from 0 to timestamp of the key.
//   * @param key
//   */
//  public void includeTimestamp(final byte[] key) {
//    includeTimestamp(Bytes.toLong(key,key.length-KeyValue.TIMESTAMP_TYPE_SIZE));
//    int type = key[key.length - 1];
//    if (type == Type.DeleteColumn.getCode() ||
//        type == Type.DeleteFamily.getCode()) {
//      includeTimestamp(0);
//    }
//  }

  /**
   * If required, update the current TimestampRange to include timestamp
   * @param timestamp the timestamp value to include
   */
  private synchronized void includeTimestamp(final long timestamp) {
    if (maximumTimestamp == -1) {
      minimumTimestamp = timestamp;
      maximumTimestamp = timestamp;
    }
    else if (minimumTimestamp > timestamp) {
      minimumTimestamp = timestamp;
    }
    else if (maximumTimestamp < timestamp) {
      maximumTimestamp = timestamp;
    }
    return;
  }

  /**
   * Check if the range has any overlap with TimeRange
   * @param tr TimeRange
   * @return True if there is overlap, false otherwise
   */
  public synchronized boolean includesTimeRange(final TimeRange tr) {
    return (this.minimumTimestamp < tr.getMax() &&
        this.maximumTimestamp >= tr.getMin());
  }

  /**
   * @return the minimumTimestamp
   */
  public synchronized long getMinimumTimestamp() {
    return minimumTimestamp;
  }

  /**
   * @return the maximumTimestamp
   */
  public synchronized long getMaximumTimestamp() {
    return maximumTimestamp;
  }

  public synchronized void write(final DataOutput out) throws IOException {
    out.writeLong(minimumTimestamp);
    out.writeLong(maximumTimestamp);
  }

  public synchronized void readFields(final DataInput in) throws IOException {
    this.minimumTimestamp = in.readLong();
    this.maximumTimestamp = in.readLong();
  }

  @Override
  public synchronized String toString() {
    return "[" + minimumTimestamp + "," + maximumTimestamp + "]";
  }
}
