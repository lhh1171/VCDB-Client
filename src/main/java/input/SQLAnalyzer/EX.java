package input.SQLAnalyzer;

import input.ActionEntity;
import input.RequestEntity;

import java.util.HashMap;

public class EX {
    public static int state = 0; // 记录DFA的状态
    public final static HashMap<String,Integer> PostState=new HashMap<String,Integer>();
    public static void DFA(ActionEntity actionEntity){
        RequestEntity requestEntity;
        if (isPut(actionEntity)) {
            state = 1;
        } else if (isDelete(actionEntity)) {
            state = 2;
        } else if (isPost(actionEntity)) {
            state = 3;
        } else {
            MethodGetErr(actionEntity);
        }
        switch (state){
            case 1:
                if (isCreateDB(actionEntity)) {
                    requestEntity=getCreateDB();
                    createDB(requestEntity);
                    break;
                } else if (isCreateTable(actionEntity)) {
                    requestEntity=getCreateTable();
                    createTable(requestEntity);
                    break;
                } else if (isPutCells(actionEntity)) {
                    requestEntity=getPutCells();
                    putCells(requestEntity);
                    break;
                } else {
                    putErr(actionEntity);
                    break;
                }
            case 2:
                if (isDeleteDB(actionEntity)) {
                    requestEntity=getDeleteDB();
                    deleteDB(requestEntity);
                    break;
                } else if (isDeleteTable(actionEntity)) {
                    requestEntity=getDeleteTable();
                    deleteTable(requestEntity);
                    break;
                }  else {
                    deleteErr(actionEntity);
                    break;
                }
                break;
            case 3:
                handlePost(actionEntity);
                break;
            default:
                System.out.println("未知错误---------------------");
        }
    }

    private static void handlePost(ActionEntity actionEntity) {
        int PState=PostState.get(actionEntity.getUrl());
        RequestEntity requestEntity;
        switch (PState){
            case 1:
                requestEntity=getCreateTable(actionEntity);
                createTable(requestEntity);
                break;
            case 2:
                requestEntity=getAlterTable(actionEntity);
                alterTable(requestEntity);
                break;
            case 3:
                requestEntity=getOpenTransaction(actionEntity);
                openTransaction(requestEntity);
                break;
            case 4:
                requestEntity=getCloseTransaction(actionEntity);
                closeTransaction(requestEntity);
                break;
            case 5:
                requestEntity=getMergeVersion(actionEntity);
                mergeVersion(requestEntity);
                break;
            case 6:
                requestEntity=getUseVersion(actionEntity);
                useVersion(requestEntity);
                break;
            case 7:
                requestEntity=getShowVersion(actionEntity);
                showVersion(requestEntity);
                break;
            case 8:
                requestEntity=getSingleSearch(actionEntity);
                singleSearch(requestEntity);
                break;
            case 9:
                requestEntity=getDeleteCells(actionEntity);
                deleteCells(requestEntity);
                break;
            case 10:
                requestEntity=getUpdateCells(actionEntity);
                updateCells(requestEntity);
                break;
            case 11:
                requestEntity=getMultiSearch(actionEntity);
                multiSearch(requestEntity);
                break;
            case 12:
                requestEntity=getDeleteVersion(actionEntity);
                deleteVersion(requestEntity);
                break;
            default:
                System.out.println("未知错误-----------------");
        }
    }
}
