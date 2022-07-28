package input.store.mem;


public class SkipList {
  //size,nodeCount
  private static final float SKIP_LIST_P = 0.1f;
  private static final int MAX_LEVEL = 16;

  private int levelCount = 1;

  private final SkipNode head = new SkipNode();  // 带头链表

  public SkipNode find(String rowKey) {
    SkipNode p = head;
    for (int i = levelCount - 1; i >= 0; --i) {
      while (p.forwards[i] != null && p.forwards[i].data.rowKey .compareTo(rowKey)<0) {
        p = p.forwards[i];
      }
    }

    if (p.forwards[0] != null && p.forwards[0].data.rowKey == rowKey) {
      return p.forwards[0];
    } else {
      return null;
    }
  }

  public void insert(KeyValue value) {
    int level = randomLevel();
    SkipNode newNode = new SkipNode();
    newNode.data = value;
    newNode.maxLevel = level;
    SkipNode[] update = new SkipNode[level];

    //每一层
    for (int i = 0; i < level; ++i) {
      update[i] = head;
    }

    // record every level largest value which smaller than insert value in update[]
    SkipNode p = head;

    for (int i = level - 1; i >= 0; --i) {
      while (p.forwards[i] != null &&p.forwards[i].data.rowKey .compareTo(value.rowKey)<0) {
        p = p.forwards[i];
      }
      // use update save node in search path
      update[i] = p;
    }

    // in search path node next node become new node forwords(next)
    for (int i = 0; i < level; ++i) {
      newNode.forwards[i] = update[i].forwards[i];
      update[i].forwards[i] = newNode;
    }

    // update node hight
    if (levelCount < level) levelCount = level;
  }

  public void delete(String rowKey) {
    SkipNode[] update = new SkipNode[levelCount];
    SkipNode p = head;
    for (int i = levelCount - 1; i >= 0; --i) {
      while (p.forwards[i] != null && p.forwards[i].data.rowKey.compareTo(rowKey)<0) {
        p = p.forwards[i];
      }
      update[i] = p;
    }

    if (p.forwards[0] != null && p.forwards[0].data.rowKey.equals(rowKey)) {
      for (int i = levelCount - 1; i >= 0; --i) {
        if (update[i].forwards[i] != null && update[i].forwards[i].data.rowKey.equals(rowKey)) {
          update[i].forwards[i] = update[i].forwards[i].forwards[i];
        }
      }
    }

    while (levelCount>1&&head.forwards[levelCount]==null){
      levelCount--;
    }

  }

  private int randomLevel() {
    int level = 1;
    while (Math.random() < SKIP_LIST_P && level < MAX_LEVEL)
      level += 1;
    return level;
  }

  public void printAll() {
    SkipNode p = head;
    while (p.forwards[0] != null) {
      System.out.println(p.forwards[0]);
      p = p.forwards[0];
    }
    System.out.println();
  }

  static class SkipNode {
    KeyValue data = null;
    private final SkipNode[] forwards = new SkipNode[MAX_LEVEL];
    private int maxLevel = 10;

    @Override
    public String toString() {
      return "{ data: " +
              data.rowKey +
              "; levels: " +
              maxLevel +
              " }";
    }
  }
}

