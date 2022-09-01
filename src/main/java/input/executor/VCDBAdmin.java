package input.executor;

import input.entity.Delete.DeleteCells;
import input.entity.Delete.DeleteTable;
import input.entity.Post.*;
import input.entity.Put.CreateDB;
import input.entity.Put.CreateTable;
import input.store.mem.KV;
import input.store.mem.MemStore;
import input.store.wal.VCLog;
import input.store.wal.WalEdit;

import java.util.Date;
import java.util.List;

import static input.store.mem.KV.byteToType;

public class VCDBAdmin {
    MemStore memStore;
    public VCDBAdmin(){
        memStore=new MemStore();
    }
    public void createDB(String dBName) {
        KV.ValueNode valueNode=new KV.ValueNode((new Date()).getTime(),byteToType((byte) 0),"".getBytes(),0,"".getBytes().length,"".getBytes(),0,"".getBytes().length);
        WalEdit walEdit = VCLog.entry.get(dBName.getBytes());
        if (walEdit==null){
            VCLog.entry.put(dBName.getBytes(),new WalEdit());
        }
        WalEdit  newWalEdit = VCLog.entry.get(dBName.getBytes());
        newWalEdit.actions.add(valueNode);
        if (memStore.kvset.get("")==null){
            memStore.add(new KV("".getBytes(),0,"".getBytes().length,"".getBytes(),0,"".getBytes().length,null));
        }
        KV kv=memStore.kvset.get("");
        List<KV.ValueNode> values = kv.getValues();
        values.add(valueNode);
        memStore.kvset.remove(kv);
        KV newKv=new KV("".getBytes(),0,"".getBytes().length,"".getBytes(),0,"".getBytes().length,values);
        memStore.add(newKv);
    }

    public void createTable(CreateTable createTable) {

    }

    public void deleteDB(DeleteCells deleteCells) {

    }

    public void deleteTable(DeleteTable deleteTable) {

    }

    public void openTransaction(OpenTransaction openTransaction) {

    }

    public void closeTransaction(CloseTransaction closeTransaction) {

    }

    public void putCells(PutCells putCells) {

    }

    public void alterTable(AlterTable alterTable) {

    }

    public void mergeVersion(MergeVersion mergeVersion) {

    }

    public void useVersion(UseVersion useVersion) {

    }

    public void showVersion(ShowVersion showVersion) {

    }

    public void singleSearch(SingleSearch singleSearch) {

    }

    public void deleteCells(DeleteCells deleteCells) {

    }

    public void updateCells(UpdateCells updateCells) {

    }

    public void multiSearch(MultiSearch multiSearch) {

    }

    public void deleteVersion(DeleteVersion requestEntity) {
    }
}
