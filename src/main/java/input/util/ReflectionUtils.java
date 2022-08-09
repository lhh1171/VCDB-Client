package input.util;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ReflectionUtils {
  private static final Map<Class<?>, Constructor<?>> CONSTRUCTOR_CACHE =
          new ConcurrentHashMap<Class<?>, Constructor<?>>();
  private static final Class<?>[] EMPTY_ARRAY = new Class[]{};
  /**
   * Allocate a buffer for each thread that tries to clone objects.
   */
  private static ThreadLocal<CopyInCopyOutBuffer> cloneBuffers
          = new ThreadLocal<CopyInCopyOutBuffer>() {
    @Override
    protected synchronized CopyInCopyOutBuffer initialValue() {
      return new CopyInCopyOutBuffer();
    }
  };
  private static class CopyInCopyOutBuffer {
    DataOutputBuffer outBuffer = new DataOutputBuffer();
    DataInputBuffer inBuffer = new DataInputBuffer();
    /**
     * Move the data from the output buffer to the input buffer.
     */
    void moveData() {
      inBuffer.reset(outBuffer.getData(), outBuffer.getLength());
    }
  }
  public static <T> T newInstance(Class<T> theClass) {
    T result;
    try {
      Constructor<T> meth = (Constructor<T>) CONSTRUCTOR_CACHE.get(theClass);
      if (meth == null) {
        meth = theClass.getDeclaredConstructor(EMPTY_ARRAY);
        meth.setAccessible(true);
        CONSTRUCTOR_CACHE.put(theClass, meth);
      }
      result = meth.newInstance();
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
    return result;
  }

  @Deprecated
  public static void cloneWritableInto(Writable dst,
                                       Writable src) throws IOException {
    CopyInCopyOutBuffer buffer = cloneBuffers.get();
    buffer.outBuffer.reset();
    src.write(buffer.outBuffer);
    buffer.moveData();
    dst.readFields(buffer.inBuffer);
  }

}
