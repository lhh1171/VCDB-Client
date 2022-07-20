package input.Analyzer;

import input.Entity.Cell.*;
import input.Entity.Delete.ColumnFamilyCell;
import input.Entity.Delete.DeleteCells;
import input.Entity.Delete.DeleteDB;
import input.Entity.Delete.DeleteTable;
import input.Entity.Post.*;
import input.Entity.Put.CreateDB;
import input.Entity.Put.CreateTable;

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
        //用枚举替换
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
//                createDB(requestEntity);
                break;
            case 3:
                requestEntity=getCreateTable(actionEntity);
//                createTable(requestEntity);
                break;
            default:
                System.out.println("the URL Segment is error"+"给出提示（把PUT所有的命令返还给他");
        }
    }

    public static void setBaseAttribute(RequestEntity requestEntity,ActionEntity actionEntity){
        requestEntity.setUri(actionEntity.getUrl());
        requestEntity.setMethod(actionEntity.getMethod());
    }
    private static RequestEntity getCreateTable(ActionEntity actionEntity) {
        CreateTable createTable=new CreateTable();
        setBaseAttribute(createTable,actionEntity);
        if (actionEntity.getRegularAttribute()!=null){
            System.err.println("出现未知属性，打印key");
        }
        for (HashMap.Entry<String, List<HashMap<String, String>>> entry : actionEntity.getCompoundAttribute().entrySet()) {
            if ("column_family".equalsIgnoreCase(entry.getKey())){
                createTable.setColumn_family(selectColumn_family(entry.getValue()));
            } else {
                System.err.println("把key打印出来，说明它不属于关键字");
            }
        }
        return createTable;
    }

    private static List<ColumnFamilyCell> selectColumn_family(List<HashMap<String, String>> value) {
        List<ColumnFamilyCell> columnFamilyCells=new ArrayList<ColumnFamilyCell>();
        for (HashMap<String,String> kv: value){
            ColumnFamilyCell columnFamilyCell=new ColumnFamilyCell();
            for (Map.Entry<String,String> cell:kv.entrySet()){
                if ("cf_name".equalsIgnoreCase(cell.getKey())){
                    if (columnFamilyCell.getCf_name()==null){
                        columnFamilyCell.setCf_name(cell.getValue());
                    } else {
                        System.err.println("报错重复设置cf_name属性");
                    }
                }else if ("type".equalsIgnoreCase(cell.getKey())){
                    if (columnFamilyCell.getType()==null){
                        columnFamilyCell.setType(cell.getValue());
                    } else {
                        System.err.println("报错重复设置type属性");
                    }
                }else if ("min".equalsIgnoreCase(cell.getKey())){
                    if (columnFamilyCell.getMin()==Double.MIN_VALUE){
                        columnFamilyCell.setMin(Double.parseDouble(cell.getValue()));
                    } else {
                        System.err.println("报错重复设置min属性");
                    }
                }else if ("max".equalsIgnoreCase(cell.getKey())){
                    if (columnFamilyCell.getMax()==Double.MAX_VALUE){
                        columnFamilyCell.setMax(Double.parseDouble(cell.getValue()));
                    } else {
                        System.err.println("报错重复设置max属性");
                    }
                }else if ("isNull".equalsIgnoreCase(cell.getKey())){
                    if (!columnFamilyCell.isNull()){
                        columnFamilyCell.setNull(Boolean.parseBoolean(cell.getValue()));
                    } else {
                        System.err.println("报错重复设置isNull属性");
                    }
                }else if ("unique".equalsIgnoreCase(cell.getKey())){
                    if (!columnFamilyCell.isUnique()){
                        columnFamilyCell.setUnique(Boolean.parseBoolean(cell.getValue()));
                    } else {
                        System.err.println("报错重复设置unique属性");
                    }
                }else if ("Version".equalsIgnoreCase(cell.getKey())){
                    if (columnFamilyCell.getVersion()==Integer.MAX_VALUE){
                        columnFamilyCell.setVersion(Integer.parseInt(cell.getValue()));
                    } else {
                        System.err.println("报错重复设置c_name属性");
                    }
                }else if ("method".equalsIgnoreCase(cell.getKey())){
                    if (columnFamilyCell.getType()==null){
                        columnFamilyCell.setType(cell.getValue());
                    } else {
                        System.err.println("报错重复设置method属性");
                    }
                }else {
                    System.err.println("出现未知属性，打印key");
                }
            }
            //TODO检查termCell必要属性是否为空
            if (isNull(columnFamilyCell.getCf_name())){
                columnFamilyCells.add(columnFamilyCell);
            }else {
                System.err.println("columnFamilyCell缺少必要属性");
            }
        }
        return columnFamilyCells;
    }

    private static RequestEntity getCreateDB(ActionEntity actionEntity) {
        CreateDB createDB=new CreateDB();
        setBaseAttribute(createDB,actionEntity);
        if (actionEntity.getRegularAttribute()!=null){
            System.err.println("出现未知属性，打印key");
        }
        if (actionEntity.getCompoundAttribute()!=null){
            System.err.println("出现未知属性，打印key");
        }
        return createDB;
    }


    private static void handleDelete(ActionEntity actionEntity) {
        String[] deleteUrl= actionEntity.getMethod().split("/");
        switch (deleteUrl.length){
            case 2:
                requestEntity=getDeleteDB(actionEntity);
//                putCell(Node);
//                deleteDB(requestEntity);
                break;
            case 3:
                requestEntity=getDeleteTable(actionEntity);
//                deleteTable(requestEntity);
                break;
            default:
                System.out.println("the URL Segment is error"+"给出提示（把Delete开头的所有的命令返还给他");
        }
    }

    private static RequestEntity getDeleteTable(ActionEntity actionEntity) {
        DeleteTable deleteTable=new DeleteTable();
        setBaseAttribute(deleteTable,actionEntity);
        if (actionEntity.getRegularAttribute()!=null){
            System.err.println("出现未知属性，打印key");
        }
        if (actionEntity.getCompoundAttribute()!=null){
            System.err.println("出现未知属性，打印key");
        }
        return deleteTable;
    }

    private static RequestEntity getDeleteDB(ActionEntity actionEntity) {
        DeleteDB deleteDB=new DeleteDB();
        setBaseAttribute(deleteDB,actionEntity);
        if (actionEntity.getRegularAttribute()!=null){
            System.err.println("出现未知属性，打印key");
        }
        if (actionEntity.getCompoundAttribute()!=null){
            System.err.println("出现未知属性，打印key");
        }
        return deleteDB;
    }

    private static void handlePost(ActionEntity actionEntity) {
        String[] postUrl=actionEntity.getMethod().split("/");
        switch (postUrl.length){
            case 2:
                if ("_open".equalsIgnoreCase(postUrl[1])){
                    requestEntity=getOpenTransaction(actionEntity);
//                    openTransaction(requestEntity);
                } else if ("_close".equalsIgnoreCase(postUrl[1])){
                    requestEntity=getCloseTransaction(actionEntity);
//                    closeTransaction(requestEntity);
                } else {
                    System.out.println("the URL Segment is error"+"给出提示（把POST开头的所有的命令返还给他");
                }
                break;
            case 3:
                if ("_insert".equalsIgnoreCase(postUrl[2])){
                    requestEntity=getPutCells(actionEntity);
//                    putCells(requestEntity);
                }else if ("alter".equalsIgnoreCase(postUrl[2])){
                    requestEntity=getAlterTable(actionEntity);
//                    alterTable(requestEntity);
                }else if ("_merge".equalsIgnoreCase(postUrl[2])){
                    requestEntity=getMergeVersion(actionEntity);
//                    mergeVersion(requestEntity);
                }else if ("_use".equalsIgnoreCase(postUrl[2])){
                    requestEntity=getUseVersion(actionEntity);
//                    useVersion(requestEntity);
                }else if ("_showVersion".equalsIgnoreCase(postUrl[2])){
                    requestEntity=getShowVersion(actionEntity);
//                    showVersion(requestEntity);
                }else if ("_search".equalsIgnoreCase(postUrl[2])){
                    requestEntity=getSingleSearch(actionEntity);
//                    singleSearch(requestEntity);
                }else if ("_delete".equalsIgnoreCase(postUrl[2])){
                    requestEntity=getDeleteCells(actionEntity);
//                    deleteCells(requestEntity);
                }else if ("_update".equalsIgnoreCase(postUrl[2])){
                    requestEntity=getUpdateCells(actionEntity);
//                    updateCells(requestEntity);
                }else if ("_mget".equalsIgnoreCase(postUrl[2])){
                    requestEntity=getMultiSearch(actionEntity);
//                    multiSearch(requestEntity);
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
        setBaseAttribute(updateCells,actionEntity);
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
        DeleteCells deleteCells=new DeleteCells();
        setBaseAttribute(deleteCells,actionEntity);
        for (Map.Entry<String,Object> cfs:actionEntity.getRegularAttribute().entrySet()){
            if ("cf_names".equalsIgnoreCase(cfs.getKey())){
                List<String> cf_names=castList(cfs.getValue(),String.class);
                if (cf_names.size()==0){
                    System.err.println("报错提示cf_names为空");
                }
                deleteCells.setCf_names(cf_names);
            }else {
                System.err.println("出现未知属性，打印key");
            }
        }
        for (HashMap.Entry<String, List<HashMap<String, String>>> entry : actionEntity.getCompoundAttribute().entrySet()) {
            if ("terms".equalsIgnoreCase(entry.getKey())){
                deleteCells.setTerms(selectTerms(entry.getValue()));
            } else {
                System.err.println("把key打印出来，说明它不属于关键字");
            }
        }
        return deleteCells;
    }

    private static RequestEntity getSingleSearch(ActionEntity actionEntity) {
        SingleSearch singleSearch=new SingleSearch();
        setBaseAttribute(singleSearch,actionEntity);
        for (Map.Entry<String,Object> cfs:actionEntity.getRegularAttribute().entrySet()){
            if ("cf_names".equalsIgnoreCase(cfs.getKey())){
                List<String> cf_names=castList(cfs.getValue(),String.class);
                if (cf_names.size()==0){
                    System.err.println("报错提示cf_names为空");
                }
                singleSearch.setCf_names(cf_names);
            }else {
                System.err.println("出现未知属性，打印key");
            }
        }
        for (HashMap.Entry<String, List<HashMap<String, String>>> entry : actionEntity.getCompoundAttribute().entrySet()) {
            if ("terms".equalsIgnoreCase(entry.getKey())){
                singleSearch.setTerms(selectTerms(entry.getValue()));
            }else if ("orders".equalsIgnoreCase(entry.getKey())){
                singleSearch.setOrders(selectOrders(entry.getValue()));
            }else if ("aggregate".equalsIgnoreCase(entry.getKey())){
                singleSearch.setAggregate(selectAggregate(entry.getValue()));
            } else {
                System.err.println("把key打印出来，说明它不属于关键字");
            }
        }
        return singleSearch;
    }

    private static List<Aggregate> selectAggregate(List<HashMap<String, String>> value) {
        List<Aggregate> aggregates=new ArrayList<Aggregate>();
        for (HashMap<String,String> kv: value){
            Aggregate aggregate=new Aggregate();
            for (Map.Entry<String,String> cell:kv.entrySet()){
                if ("c_name".equalsIgnoreCase(cell.getKey())){
                    if (aggregate.getC_name()==null){
                        aggregate.setC_name(cell.getValue());
                    } else {
                        System.err.println("报错重复设置cf_name属性");
                    }
                }else if ("function".equalsIgnoreCase(cell.getKey())){
                    if (aggregate.getFunction()==null){
                        aggregate.setFunction(cell.getValue());
                    } else {
                        System.err.println("报错重复设置c_name属性");
                    }
                }else if ("as".equalsIgnoreCase(cell.getKey())){
                    if (aggregate.getAs()==null){
                        aggregate.setAs(cell.getValue());
                    } else {
                        System.err.println("报错重复设置size属性");
                    }
                }else {
                    System.err.println("出现未知属性，打印key");
                }
            }
            //TODO检查termCell必要属性是否为空
            if (isNull(aggregate.getC_name())){
                aggregates.add(aggregate);
            }if (isNull(aggregate.getFunction())){
                aggregates.add(aggregate);
            }if (isNull(aggregate.getAs())){
                aggregates.add(aggregate);
            }else {
                System.err.println("aggregate缺少必要属性");
            }
        }
        return aggregates;
    }

    private static RequestEntity getShowVersion(ActionEntity actionEntity) {
        ShowVersion showVersion=new ShowVersion();
        setBaseAttribute(showVersion,actionEntity);
        for (Map.Entry<String,Object> cfs:actionEntity.getRegularAttribute().entrySet()){
            if ("rowKey".equalsIgnoreCase(cfs.getKey())){
                showVersion.setRowKey((String)cfs.getValue());
            }else {
                System.err.println("出现未知属性，打印key");
            }
        }
        if (actionEntity.getCompoundAttribute()!=null){
            System.err.println("出现未知属性，打印key");
        }
        return showVersion;
    }

    private static RequestEntity getUseVersion(ActionEntity actionEntity) {
        UseVersion useVersion=new UseVersion();
        setBaseAttribute(useVersion,actionEntity);
        for (Map.Entry<String,Object> cfs:actionEntity.getRegularAttribute().entrySet()){
            if ("rowKey".equalsIgnoreCase(cfs.getKey())){
                useVersion.setRowKey((String)cfs.getValue());
            }if ("Version".equalsIgnoreCase(cfs.getKey())){
                useVersion.setVersion((Integer.parseInt((String)cfs.getValue())));
            }else {
                System.err.println("出现未知属性，打印key");
            }
        }
        if (actionEntity.getCompoundAttribute()!=null){
            System.err.println("出现未知属性，打印key");
        }
        return useVersion;
    }

    private static RequestEntity getMergeVersion(ActionEntity actionEntity) {
        MergeVersion mergeVersion=new MergeVersion();
        setBaseAttribute(mergeVersion,actionEntity);
        for (Map.Entry<String,Object> cfs:actionEntity.getRegularAttribute().entrySet()){
            if ("rowKey".equalsIgnoreCase(cfs.getKey())){
                mergeVersion.setRowKey((String)cfs.getValue());
            }else {
                System.err.println("出现未知属性，打印key");
            }
        }
        for (HashMap.Entry<String, List<HashMap<String, String>>> entry : actionEntity.getCompoundAttribute().entrySet()) {
            if ("Terms".equalsIgnoreCase(entry.getKey())){
                mergeVersion.setTerms(selectTerms(entry.getValue()));
            } else {
                System.err.println("把key打印出来，说明它不属于关键字");
            }
        }
        return mergeVersion;
    }

    private static RequestEntity getAlterTable(ActionEntity actionEntity) {
        AlterTable alterTable=new AlterTable();
        setBaseAttribute(alterTable,actionEntity);
        if (actionEntity.getRegularAttribute()!=null){
            System.err.println("出现未知属性，打印key");
        }
        for (HashMap.Entry<String, List<HashMap<String, String>>> entry : actionEntity.getCompoundAttribute().entrySet()) {
            if ("alter_cells".equalsIgnoreCase(entry.getKey())){
                alterTable.setAlter_cells(selectAlter(entry.getValue()));
            } else {
                System.err.println("把key打印出来，说明它不属于关键字");
            }
        }
        return null;
    }

    private static List<AlterCell> selectAlter(List<HashMap<String, String>> value) {
        List<AlterCell> alterCells=new ArrayList<AlterCell>();
        for (HashMap<String,String> kv: value){
            AlterCell alterCell=new AlterCell();
            for (Map.Entry<String,String> cell:kv.entrySet()){
                if ("cfName".equalsIgnoreCase(cell.getKey())){
                    if (alterCell.getCfName()==null){
                        alterCell.setCfName(cell.getValue());
                    } else {
                        System.err.println("报错重复设置cfName属性");
                    }
                }else if ("old_cfName".equalsIgnoreCase(cell.getKey())){
                    if (alterCell.getOld_cfName()==null){
                        alterCell.setOld_cfName(cell.getValue());
                    } else {
                        System.err.println("报错重复设置old_cfName属性");
                    }
                }else if ("method".equalsIgnoreCase(cell.getKey())){
                    if (alterCell.getMethod()==null){
                        alterCell.setMethod(cell.getValue());
                    } else {
                        System.err.println("报错重复设置method属性");
                    }
                }else {
                    System.err.println("出现未知属性，打印key");
                }
            }
            //TODO检查termCell必要属性是否为空
            if ("put".equalsIgnoreCase(alterCell.getMethod())){
                if (isNull(alterCell.getCfName())&&!isNull(alterCell.getOld_cfName())){
                    alterCells.add(alterCell);
                }
            }else if ("delete".equalsIgnoreCase(alterCell.getMethod())){
                if (!isNull(alterCell.getCfName())&&isNull(alterCell.getOld_cfName())){
                    alterCells.add(alterCell);
                }
            }else if ("update".equalsIgnoreCase(alterCell.getMethod())){
                if (isNull(alterCell.getCfName())&&isNull(alterCell.getOld_cfName())){
                    alterCells.add(alterCell);
                }
            }else {
                System.err.println("aggregate缺少必要属性");
            }
        }
        return alterCells;
    }

    private static RequestEntity getPutCells(ActionEntity actionEntity) {
        PutCells putCells=new PutCells();
        setBaseAttribute(putCells,actionEntity);
        for (Map.Entry<String,Object> cfs:actionEntity.getRegularAttribute().entrySet()){
            if ("rowKey".equalsIgnoreCase(cfs.getKey())){
                putCells.setRowKey((String)cfs.getValue());
            }else {
                System.err.println("出现未知属性，打印key");
            }
        }
        for (HashMap.Entry<String, List<HashMap<String, String>>> entry : actionEntity.getCompoundAttribute().entrySet()) {
            if ("values".equalsIgnoreCase(entry.getKey())){
                putCells.setValues(selectValues(entry.getValue()));
            } else {
                System.err.println("把key打印出来，说明它不属于关键字");
            }
        }
        return putCells;
    }

    private static RequestEntity getCloseTransaction(ActionEntity actionEntity) {
        CloseTransaction closeTransaction=new CloseTransaction();
        setBaseAttribute(closeTransaction,actionEntity);
        if (actionEntity.getRegularAttribute()!=null){
            System.err.println("出现未知属性，打印key");
        }
        if (actionEntity.getCompoundAttribute()!=null){
            System.err.println("出现未知属性，打印key");
        }
        return closeTransaction;
    }

    private static RequestEntity getOpenTransaction(ActionEntity actionEntity) {
        OpenTransaction openTransaction=new OpenTransaction();
        setBaseAttribute(openTransaction,actionEntity);
        for (Map.Entry<String,Object> cfs:actionEntity.getRegularAttribute().entrySet()){
            if ("explainValue".equalsIgnoreCase(cfs.getKey())){
                openTransaction.setExplainValue((String)cfs.getValue());
            }else {
                System.err.println("出现未知属性，打印key");
            }
        }
        if (actionEntity.getCompoundAttribute()!=null){
            System.err.println("出现未知属性，打印key");
        }
        return openTransaction;
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

//    public static void main(String[] args) {
//        ActionEntity actionEntity=new ActionEntity();
//        String[] split = actionEntity.getMethod().split("/");
//    }
}
