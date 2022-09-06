package input.store.wal;

import java.util.HashMap;

public class VCLog {
    //即对应HFile中的KeyValue，包括：row、column family、qualifier、timestamp、value，以及“Key Type”（比如PUT或DELETE)。
    //rowKey,walEdit一对一的关系，hashmap应该换成一个index dataSet
   public static HashMap<byte[],WalEdit> entry;
   public VCLog(){
       entry=new HashMap<>();
   }

}
