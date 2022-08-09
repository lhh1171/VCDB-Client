package input.store.wal;

import input.store.mem.KV;

import java.util.ArrayList;

//A collection of updates in a transaction
public class WalEdit {
    ArrayList<KV> keyValues;
}
