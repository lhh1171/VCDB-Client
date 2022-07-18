package input.SQLAnalyzer;

import input.ActionEntity;
import input.MultiSearch;
import input.RequestEntity;

public class EX {
    public static int MethodState = 0; // method
    public static int PutState=0;
    public static int DeleteState=0;
    public static int PostState=0;
    public static RequestEntity requestEntity;
//    public static void DFA(ActionEntity actionEntity){
//        if (isPut(actionEntity)) {
//            MethodState = 1;
//        } else if (isDelete(actionEntity)) {
//            MethodState = 2;
//        } else if (isPost(actionEntity)) {
//            MethodState = 3;
//        } else {
//            MethodGetErr(actionEntity);
//        }
//        switch (MethodState){
//            case 1:
//                if (isCreateDB(actionEntity)) {
//                    PutState=1;
//                    requestEntity=getCreateDB();
//                    createDB(requestEntity);
//                    break;
//                } else if (isCreateTable(actionEntity)) {
//                    PutState=2;
//                    requestEntity=getCreateTable();
//                    createTable(requestEntity);
//                    break;
//                } else if (isPutCells(actionEntity)) {
//                    PutState=3;
//                    requestEntity=getPutCells();
//                    putCells(requestEntity);
//                    break;
//                } else {
//                    putErr(actionEntity);
//                    break;
//                }
//            case 2:
//                if (isDeleteDB(actionEntity)) {
//                    DeleteState=1;
//                    requestEntity=getDeleteDB();
//                    deleteDB(requestEntity);
//                    break;
//                } else if (isDeleteTable(actionEntity)) {
//                    DeleteState=2;
//                    requestEntity=getDeleteTable();
//                    deleteTable(requestEntity);
//                    break;
//                } else {
//                    deleteErr(actionEntity);
//                    break;
//                }
//                break;
//            case 3:
//                if(isAlterTable()){
//                    requestEntity=getAlterTable(actionEntity);
//                    alterTable(requestEntity);
//                    break;
//                }else if(isOpenTransaction()){
//                    requestEntity=getOpenTransaction(actionEntity);
//                    openTransaction(requestEntity);
//                    break;
//                }else if(isCloseTransaction()){
//                    requestEntity=getCloseTransaction(actionEntity);
//                    closeTransaction(requestEntity);
//                }else if(isMergeVersion()){
//                    requestEntity=getMergeVersion(actionEntity);
//                    mergeVersion(requestEntity);
//                    break;
//                }else if(isUseVersion()){
//                    requestEntity=getUseVersion(actionEntity);
//                    useVersion(requestEntity);
//                    break;
//                }else if(isShowVersion()){
//                    requestEntity=getShowVersion(actionEntity);
//                    showVersion(requestEntity);
//                    break;
//                }else if(isSingleSearch()){
//                    requestEntity=getSingleSearch(actionEntity);
//                    singleSearch(requestEntity);
//                    break;
//                }else if(isDeleteCells()){
//                    requestEntity=getDeleteCells(actionEntity);
//                    deleteCells(requestEntity);
//                    break;
//                }else if(isUpdateCells()){
//                    requestEntity=getUpdateCells(actionEntity);
//                    updateCells(requestEntity);
//                    break;
//                }else if(isMultiSearch()){
//                    requestEntity=getMultiSearch(actionEntity);
//                    multiSearch(requestEntity);
//                    break;
//                }else if(isDeleteVersion()){
//                    requestEntity=getDeleteVersion(actionEntity);
//                    deleteVersion(requestEntity);
//                    break;
//                }
//                break;
//            default:
//                System.out.println("未知错误---------------------");
//        }
//    }
    public static void DFA2(ActionEntity actionEntity){
        if (isPut(actionEntity)) {
            MethodState = 1;
        } else if (isDelete(actionEntity)) {
            MethodState = 2;
        } else if (isPost(actionEntity)) {
            MethodState = 3;
        } else {
            MethodGetErr(actionEntity);
        }
        switch (MethodState){
            case 1:
                handlePut(actionEntity);
            case 2:
                handleDelete(actionEntity);
            case 3:
                handlePost(actionEntity);
            default:
                System.out.println("未知错误---------------------");
        }
    }

    private static void MethodGetErr(ActionEntity actionEntity) {
    }

    private static void handlePut(ActionEntity actionEntity) {
        String[] putUrl= actionEntity.getMethod().split("/");
        switch (putUrl.length){
            case 2:
                requestEntity=getCreateDB(actionEntity);
                createDB(requestEntity);
                break;
            case 3:
                requestEntity=getCreateTable(actionEntity);
                createTable(requestEntity);
                break;
            default:
                System.out.println("the URL Segment is error"+"给出提示（把PUT所有的命令返还给他");
        }
    }

    private static RequestEntity getCreateTable(ActionEntity actionEntity) {
    }

    private static RequestEntity getCreateDB(ActionEntity actionEntity) {
    }


    private static void handleDelete(ActionEntity actionEntity) {
        String[] deleteUrl= actionEntity.getMethod().split("/");
        switch (deleteUrl.length){
            case 2:
                requestEntity=getDeleteDB(actionEntity);
                deleteDB(requestEntity);
                break;
            case 3:
                requestEntity=getDeleteTable(actionEntity);
                deleteTable(requestEntity);
                break;
            default:
                System.out.println("the URL Segment is error"+"给出提示（把Delete开头的所有的命令返还给他");
        }
    }

    private static RequestEntity getDeleteTable(ActionEntity actionEntity) {
    }

    private static RequestEntity getDeleteDB(ActionEntity actionEntity) {
    }

    private static void handlePost(ActionEntity actionEntity) {
        String[] postUrl=actionEntity.getMethod().split("/");
        switch (postUrl.length){
            case 2:
                if ("_open".equalsIgnoreCase(postUrl[1])){
                    requestEntity=getOpenTransaction(actionEntity);
                    openTransaction(requestEntity);
                }else if ("_close".equalsIgnoreCase(postUrl[1])){
                    requestEntity=getCloseTransaction(actionEntity);
                    closeTransaction(requestEntity);
                }else {
                    System.out.println("the URL Segment is error"+"给出提示（把POST开头的所有的命令返还给他");
                }
                break;
            case 3:
                if ("_insert".equalsIgnoreCase(postUrl[2])){
                    requestEntity=getPutCells();
                    putCells(requestEntity);
                }else if ("alter".equalsIgnoreCase(postUrl[2])){
                    requestEntity=getAlterTable(actionEntity);
                    alterTable(requestEntity);
                }else if ("_merge".equalsIgnoreCase(postUrl[2])){
                    requestEntity=getMergeVersion(actionEntity);
                    mergeVersion(requestEntity);
                }else if ("_use".equalsIgnoreCase(postUrl[2])){
                    requestEntity=getUseVersion(actionEntity);
                    useVersion(requestEntity);
                }else if ("_showVersion".equalsIgnoreCase(postUrl[2])){
                    requestEntity=getShowVersion(actionEntity);
                    showVersion(requestEntity);
                }else if ("_search".equalsIgnoreCase(postUrl[2])){
                    requestEntity=getSingleSearch(actionEntity);
                    singleSearch(requestEntity);
                }else if ("_delete".equalsIgnoreCase(postUrl[2])){
                    requestEntity=getDeleteCells(actionEntity);
                    deleteCells(requestEntity);
                }else if ("_update".equalsIgnoreCase(postUrl[2])){
                    requestEntity=getUpdateCells(actionEntity);
                    updateCells(requestEntity);
                }else if ("_mget".equalsIgnoreCase(postUrl[2])){
                    requestEntity=getMultiSearch(actionEntity);
                    multiSearch(requestEntity);
                } else {
                    System.out.println("the URL Segment is error"+"给出提示（把POST开头的所有的命令返还给他");
                }
//                switch (postUrl[2]){
//                    case "_insert":
//                    case "alter":
//                    case "_merge":
//                    case "_use":
//                    case "_showVersion":
//                    case "_search":
//                    case "_delete":
//                    case "_update":
//                    case "_mget":
//                    default:
//                        System.out.println("the URL Segment is error"+"给出提示（把POST开头的所有的命令返还给他");
//                }
            default:
                System.out.println("the URL Segment is error"+"给出提示（把POST开头的所有的命令返还给他");

        }
    }

    private static RequestEntity getMultiSearch(ActionEntity actionEntity) {
        MultiSearch multiSearch=new MultiSearch();
    }

    private static RequestEntity getUpdateCells(ActionEntity actionEntity) {
    }

    private static RequestEntity getDeleteCells(ActionEntity actionEntity) {
    }

    private static RequestEntity getSingleSearch(ActionEntity actionEntity) {
    }

    private static RequestEntity getShowVersion(ActionEntity actionEntity) {
    }

    private static RequestEntity getUseVersion(ActionEntity actionEntity) {
    }

    private static RequestEntity getMergeVersion(ActionEntity actionEntity) {
    }

    private static RequestEntity getAlterTable(ActionEntity actionEntity) {
    }

    private static RequestEntity getPutCells() {
    }

    private static RequestEntity getCloseTransaction(ActionEntity actionEntity) {
    }

    private static RequestEntity getOpenTransaction(ActionEntity actionEntity) {
    }

    private static boolean isPost(ActionEntity actionEntity) {
        return "POST".equalsIgnoreCase(actionEntity.getMethod());
    }

    private static boolean isDelete(ActionEntity actionEntity) {
        return "DELETE".equalsIgnoreCase(actionEntity.getMethod());
    }

    private static boolean isPut(ActionEntity actionEntity) {
        return "PUT".equalsIgnoreCase(actionEntity.getMethod());
    }

    public static void main(String[] args) {
        ActionEntity actionEntity=new ActionEntity();
        String[] split = actionEntity.getMethod().split("/");
    }
}
