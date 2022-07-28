package input.store.wal;

import input.store.mem.KeyValue;

import java.util.ArrayList;

//A collection of updates in a transaction
public class WalEdit {
    ArrayList<KeyValue> keyValues;
}
