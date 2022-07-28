package input.store.file;

import input.store.wal.WalEdit;

import java.util.HashMap;

public class VCLog {
//    HLog Sequece File的Value是HBase的KeyValue对象，
//    即对应HFile中的KeyValue，包括：row、column family、qualifier、timestamp、value，以及“Key Type”（比如PUT或DELETE)。
    //rowKey,walEdit
    HashMap<String,WalEdit> entry;
}
