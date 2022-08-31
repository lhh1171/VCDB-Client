package input.store.wal;
import input.store.mem.KV;

import java.util.ArrayList;

//A collection of updates in a transaction
public class WalEdit {
    public ArrayList<KV.ValueNode> actions;
    public WalEdit(){
        actions=new ArrayList<>();
    }
    public void syncLog(){}

}
