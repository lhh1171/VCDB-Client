package input.SQLAnalyzer;

import input.ActionEntity;
import input.ENTITY.JTableCell;
import input.ENTITY.Order;
import input.ENTITY.TermCell;
import input.ENTITY.Value;
import input.MultiSearch;
import input.RequestEntity;
import input.UpdateCells;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        return null;
    }

    private static RequestEntity getDeleteDB(ActionEntity actionEntity) {
        return null;
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
                    System.err.println("the URL Segment is error"+"给出提示（把POST开头的所有的命令返还给他");
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
                System.err.println("the URL Segment is error"+"给出提示（把POST开头的所有的命令返还给他");

        }
    }
    public static <T> List<T> castList(Object obj, Class<T> clazz)
    {
        List<T> result = new ArrayList<T>();
        if(obj instanceof List<?>)
        {
            for (Object o : (List<?>) obj)
            {
                result.add(clazz.cast(o));
            }
            return result;
        }
        return null;
    }
    public static boolean isNull(String...varargs){
        boolean is=true;
        for(String arg : varargs) {//当作数组用foreach遍历
            if (arg == null) {
                is = false;
                break;
            }
        }
        return is;
    }
    private static RequestEntity getMultiSearch(ActionEntity actionEntity) {
        MultiSearch multiSearch=new MultiSearch();
        multiSearch.setUri(actionEntity.getUrl());
        multiSearch.setMethod("POST");
        if (actionEntity.getRegularAttribute().isEmpty()){
            System.err.println("把actionEntity.getRegularAttribute打印出来并且说明");
        }
        for (Map.Entry<String,Object> cfs:actionEntity.getRegularAttribute().entrySet()){
            if ("cf_names".equalsIgnoreCase(cfs.getKey())){
               List<String> cf_names=castList(cfs.getValue(),String.class);
               if (cf_names.size()==0){
                   System.err.println("报错提示cf_names为空");
               }
               multiSearch.setCf_names(cf_names);
            }else {
                System.err.println("出现未知属性，打印key");
            }
        }
        for (HashMap.Entry<String, List<HashMap<String, String>>> entry : actionEntity.getCompoundAttribute().entrySet()) {
            if ("j_tables".equalsIgnoreCase(entry.getKey())){
                multiSearch.setJ_tables(selectJTables(entry.getValue()));
            }else if ("terms".equalsIgnoreCase(entry.getKey())){
                multiSearch.setTerms(selectTerms(entry.getValue()));
            }else if ("orders".equalsIgnoreCase(entry.getKey())){
                multiSearch.setOrders(selectOrders(entry.getValue()));
            } else {
                System.err.println("把key打印出来，说明它不属于关键字");
            }
        }
        return multiSearch;
    }

    private static List<Order> selectOrders(List<HashMap<String, String>> value) {
        List<Order> orders=new ArrayList<Order>();
        for (HashMap<String,String> kv: value){
            Order order=new Order();
            for (Map.Entry<String,String> cell:kv.entrySet()){
                if ("cf_name".equalsIgnoreCase(cell.getKey())){
                    if (order.getCf_name()==null){
                        order.setCf_name(cell.getValue());
                    }else {
                        System.err.println("报错重复设置cf_name属性");
                    }
                }else if ("sort".equalsIgnoreCase(cell.getKey())){
                    if (order.getSort()==null){
                        order.setSort(cell.getValue());
                    }else {
                        System.err.println("报错重复设置sort属性");
                    }
                }else {
                    System.err.println("出现未知属性，打印key");
                }
            }
            //TODO检查order必要属性是否为空
            if (isNull(order.getCf_name(),order.getSort())){
                orders.add(order);
            }else {
                System.err.println("order缺少必要属性");
            }
        }
        return orders;
    }

    private static List<JTableCell> selectJTables(List<HashMap<String, String>> value) {
        List<JTableCell> j_tables=new ArrayList<JTableCell>();
        for (HashMap<String,String> kv: value){
            JTableCell jTableCell=new JTableCell();
            for (Map.Entry<String,String> cell:kv.entrySet()){
                if ("tableName".equalsIgnoreCase(cell.getKey())){
                    if (jTableCell.getTableName()==null){
                        jTableCell.setTableName(cell.getValue());
                    }else {
                        System.err.println("报错重复设置tableName属性");
                    }
                }else if ("method".equalsIgnoreCase(cell.getKey())){
                    if (jTableCell.getMethod()==null){
                        jTableCell.setMethod(cell.getValue());
                    }else {
                        System.err.println("报错重复设置Method属性");
                    }
                }else {
                    System.err.println("出现未知属性，打印key");
                }
            }
            //TODO检查jTableCell必要属性是否为空
            if (isNull(jTableCell.getTableName(),jTableCell.getMethod())){
                j_tables.add(jTableCell);
            }else {
                System.err.println("jTableCell的必要属性为空");
            }
        }
        return j_tables;
    }

    private static RequestEntity getUpdateCells(ActionEntity actionEntity) {
        UpdateCells updateCells=new UpdateCells();
        updateCells.setUri(actionEntity.getUrl());
        updateCells.setMethod("POST");
        if (actionEntity.getRegularAttribute()!=null){
            System.err.println("出现未知属性，打印key");
        }
        for (HashMap.Entry<String, List<HashMap<String, String>>> entry : actionEntity.getCompoundAttribute().entrySet()) {
           if ("terms".equalsIgnoreCase(entry.getKey())){
                updateCells.setTerms(selectTerms(entry.getValue()));
            }else if ("values".equalsIgnoreCase(entry.getKey())){
                updateCells.setValues(selectValues(entry.getValue()));
            } else {
                System.err.println("把key打印出来，说明它不属于关键字");
            }
        }
        return updateCells;
    }

    private static List<Value> selectValues(List<HashMap<String, String>> value) {
        List<Value> values = new ArrayList<Value>();
        for (HashMap<String, String> kv : value) {
            Value valueCell = new Value();
            for (Map.Entry<String, String> cell : kv.entrySet()) {
                if ("cf_name".equalsIgnoreCase(cell.getKey())) {
                    if (valueCell.getCf_name() == null) {
                        valueCell.setCf_name(cell.getValue());
                    } else {
                        System.err.println("报错重复设置cf_name属性");
                    }
                } else if ("c_name".equalsIgnoreCase(cell.getKey())) {
                    if (valueCell.getC_name() == null) {
                        valueCell.setC_name(cell.getValue());
                    } else {
                        System.err.println("报错重复设置c_name属性");
                    }
                } else if ("value".equalsIgnoreCase(cell.getKey())) {
                    if (valueCell.getValue() == null) {
                        valueCell.setValue(cell.getValue());
                    } else {
                        System.err.println("报错重复设置value属性");
                    }
                } else {
                    System.err.println("出现未知属性，打印key");
                }
            }
            return values;
        }
        return values;
    }

    private static List<TermCell> selectTerms(List<HashMap<String, String>> value) {
        List<TermCell> terms=new ArrayList<TermCell>();
        for (HashMap<String,String> kv: value){
            TermCell termCell=new TermCell();
            for (Map.Entry<String,String> cell:kv.entrySet()){
                if ("cf_name".equalsIgnoreCase(cell.getKey())){
                    if (termCell.getCf_name()==null){
                        termCell.setCf_name(cell.getValue());
                    } else {
                        System.err.println("报错重复设置cf_name属性");
                    }
                }else if ("c_name".equalsIgnoreCase(cell.getKey())){
                    if (termCell.getC_name()==null){
                        termCell.setC_name(cell.getValue());
                    } else {
                        System.err.println("报错重复设置c_name属性");
                    }
                }else if ("size".equalsIgnoreCase(cell.getKey())){
                    if (termCell.getSize()==0){
                        termCell.setSize(Long.parseLong(cell.getValue()));
                    } else {
                        System.err.println("报错重复设置size属性");
                    }
                }else if ("max".equalsIgnoreCase(cell.getKey())){
                    if (termCell.getMax()==Double.MAX_VALUE){
                        termCell.setMax(Double.parseDouble(cell.getValue()));
                    } else {
                        System.err.println("报错重复设置max属性");
                    }
                }else if ("equivalence".equalsIgnoreCase(cell.getKey())){
                    if (!termCell.getEquivalence()){
                        termCell.setEquivalence(Boolean.parseBoolean(cell.getValue()));
                    } else {
                        System.err.println("报错重复设置min属性");
                    }
                }else if ("min".equalsIgnoreCase(cell.getKey())){
                    if (termCell.getMin()==Double.MAX_VALUE){
                        termCell.setMin(Double.parseDouble(cell.getValue()));
                    } else {
                        System.err.println("报错重复设置min属性");
                    }
                }else if ("like".equalsIgnoreCase(cell.getKey())){
                    if (termCell.getLike()==null){
                        termCell.setLike(cell.getValue());
                    } else {
                        System.err.println("报错重复设置c_name属性");
                    }
                }else {
                    System.err.println("出现未知属性，打印key");
                }
            }
            //TODO检查termCell必要属性是否为空
            if (isNull(termCell.getCf_name())){
                terms.add(termCell);
            }else {
                System.err.println("term缺少必要属性");
            }
        }
        return terms;
    }

    private static RequestEntity getDeleteCells(ActionEntity actionEntity) {
        return null;
    }

    private static RequestEntity getSingleSearch(ActionEntity actionEntity) {
        return null;
    }

    private static RequestEntity getShowVersion(ActionEntity actionEntity) {
        return null;
    }

    private static RequestEntity getUseVersion(ActionEntity actionEntity) {
        return null;
    }

    private static RequestEntity getMergeVersion(ActionEntity actionEntity) {
        return null;
    }

    private static RequestEntity getAlterTable(ActionEntity actionEntity) {
        return null;
    }

    private static RequestEntity getPutCells() {
        return null;
    }

    private static RequestEntity getCloseTransaction(ActionEntity actionEntity) {
        return null;
    }

    private static RequestEntity getOpenTransaction(ActionEntity actionEntity) {
        return null;
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
