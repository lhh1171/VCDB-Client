package input.Analyzer;

import input.entity.Cell.*;
import input.entity.Cell.ColumnFamilyCell;
import input.entity.Delete.DeleteCells;
import input.entity.Delete.DeleteDB;
import input.entity.Delete.DeleteTable;
import input.entity.Post.*;
import input.entity.Put.CreateDB;
import input.entity.Put.CreateTable;
import input.executor.VCDBAdmin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EX {
    public  static VCDBAdmin vcdbAdmin;
    static {
        //        initAdmin();
    }
    public static void DFA2(ActionEntity actionEntity) {
        int MethodState = 0; // method
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
        switch (MethodState) {
            case 1:
                handlePut(actionEntity);
                break;
            case 2:
                handleDelete(actionEntity);
                break;
            case 3:
                handlePost(actionEntity);
                break;
            default:
                System.out.println("未知错误---------------------");
                break;
        }
    }

    private static void MethodGetErr(ActionEntity actionEntity) {
        System.err.println("方法错误");
    }

    private static void handlePut(ActionEntity actionEntity) {
        RequestEntity requestEntity;
        String[] putUrl = actionEntity.getUrl().split("/");
        switch (putUrl.length) {
            case 2:
                requestEntity = getCreateDB(actionEntity);
                System.out.println("createDB----------");
                //Before using the VCdbAdmin object, check whether it is initialized
                vcdbAdmin.createDB((CreateDB) requestEntity);
                break;
            case 3:
                requestEntity = getCreateTable(actionEntity);
                System.out.println("createTable---------");
                vcdbAdmin.createTable((CreateTable) requestEntity);
                break;
            default:
                System.out.println("the URL Segment is error" + "给出提示（把PUT所有的命令返还给他");
                break;
        }
    }

    public static void setBaseAttribute(RequestEntity requestEntity, ActionEntity actionEntity) {
        requestEntity.setUri(actionEntity.getUrl());
        requestEntity.setMethod(actionEntity.getMethod());
    }

    private static RequestEntity getCreateTable(ActionEntity actionEntity) {
        CreateTable createTable = new CreateTable();
        setBaseAttribute(createTable, actionEntity);
        if (actionEntity.getRegularAttribute() != null) {
            System.err.println("出现未知属性，打印key");
        }
        for (HashMap.Entry<String, List<HashMap<String, String>>> entry : actionEntity.getCompoundAttribute().entrySet()) {
            if ("column_family".equalsIgnoreCase(entry.getKey())) {
                createTable.setColumn_family(selectColumn_family(entry.getValue()));
            } else {
                System.err.println("把key打印出来，说明它不属于关键字");
            }
        }
        return createTable;
    }

    private static List<ColumnFamilyCell> selectColumn_family(List<HashMap<String, String>> value) {
        List<ColumnFamilyCell> columnFamilyCells = new ArrayList<ColumnFamilyCell>();
        for (HashMap<String, String> kv : value) {
            ColumnFamilyCell columnFamilyCell = new ColumnFamilyCell();
            for (Map.Entry<String, String> cell : kv.entrySet()) {
                if ("cf_name".equalsIgnoreCase(cell.getKey())) {
                    if (columnFamilyCell.getCf_name() == null) {
                        columnFamilyCell.setCf_name(cell.getValue());
                    } else {
                        System.err.println("报错重复设置cf_name属性");
                    }
                } else if ("type".equalsIgnoreCase(cell.getKey())) {
                    if (columnFamilyCell.getType() == null) {
                        columnFamilyCell.setType(cell.getValue());
                    } else {
                        System.err.println("报错重复设置type属性");
                    }
                } else if ("min".equalsIgnoreCase(cell.getKey())) {
                    if (columnFamilyCell.getMin() == Double.MIN_VALUE) {
                        columnFamilyCell.setMin(Double.parseDouble(cell.getValue()));
                    } else {
                        System.err.println("报错重复设置min属性");
                    }
                } else if ("max".equalsIgnoreCase(cell.getKey())) {
                    if (columnFamilyCell.getMax() == Double.MAX_VALUE) {
                        columnFamilyCell.setMax(Double.parseDouble(cell.getValue()));
                    } else {
                        System.err.println("报错重复设置max属性");
                    }
                } else if ("isNull".equalsIgnoreCase(cell.getKey())) {
                    if (columnFamilyCell.isNull() == null) {
                        columnFamilyCell.setNull(Boolean.parseBoolean(cell.getValue()));
                    } else {
                        System.err.println("报错重复设置isNull属性");
                    }
                } else if ("unique".equalsIgnoreCase(cell.getKey())) {
                    if (columnFamilyCell.isUnique() == null) {
                        columnFamilyCell.setUnique(Boolean.parseBoolean(cell.getValue()));
                    } else {
                        System.err.println("报错重复设置unique属性");
                    }
                } else if ("Version".equalsIgnoreCase(cell.getKey())) {
                    if (columnFamilyCell.getVersion() == Integer.MAX_VALUE) {
                        columnFamilyCell.setVersion(Integer.parseInt(cell.getValue()));
                    } else {
                        System.err.println("报错重复设置c_name属性");
                    }
                } else if ("method".equalsIgnoreCase(cell.getKey())) {
                    if (columnFamilyCell.getType() == null) {
                        columnFamilyCell.setType(cell.getValue());
                    } else {
                        System.err.println("报错重复设置method属性");
                    }
                } else {
                    System.err.println("出现未知属性，打印key");
                }
            }
            //TODO检查termCell必要属性是否为空
            if (isNull(columnFamilyCell.getCf_name())) {
                columnFamilyCells.add(columnFamilyCell);
            } else {
                System.err.println("columnFamilyCell缺少必要属性");
            }
        }
        return columnFamilyCells;
    }

    private static RequestEntity getCreateDB(ActionEntity actionEntity) {
        CreateDB createDB = new CreateDB();
        setBaseAttribute(createDB, actionEntity);
        if (actionEntity.getRegularAttribute() != null) {
            System.err.println("出现未知属性，打印key");
        }
        if (actionEntity.getCompoundAttribute() != null) {
            System.err.println("出现未知属性，打印key");
        }
        return createDB;
    }


    private static void handleDelete(ActionEntity actionEntity) {
        RequestEntity requestEntity;
        String[] deleteUrl = actionEntity.getUrl().split("/");
        switch (deleteUrl.length) {
            case 2:
                requestEntity = getDeleteDB(actionEntity);
                //项任务池里投放任务
//                putCell(Node);
                System.out.println("deleteDB------------");
                vcdbAdmin.deleteDB((DeleteCells) requestEntity);
                break;
            case 3:
                requestEntity = getDeleteTable(actionEntity);
                System.out.println("deleteTable----------------");
                vcdbAdmin.deleteTable((DeleteTable) requestEntity);
                break;
            default:
                System.out.println("the URL Segment is error" + "给出提示（把Delete开头的所有的命令返还给他");
        }
    }

    private static RequestEntity getDeleteTable(ActionEntity actionEntity) {
        DeleteTable deleteTable = new DeleteTable();
        setBaseAttribute(deleteTable, actionEntity);
        if (actionEntity.getRegularAttribute() != null) {
            System.err.println("出现未知属性，打印key");
        }
        if (actionEntity.getCompoundAttribute() != null) {
            System.err.println("出现未知属性，打印key");
        }
        return deleteTable;
    }

    private static RequestEntity getDeleteDB(ActionEntity actionEntity) {
        DeleteDB deleteDB = new DeleteDB();
        setBaseAttribute(deleteDB, actionEntity);
        if (actionEntity.getRegularAttribute() != null) {
            System.err.println("出现未知属性，打印key");
        }
        if (actionEntity.getCompoundAttribute() != null) {
            System.err.println("出现未知属性，打印key");
        }
        return deleteDB;
    }

    private static void handlePost(ActionEntity actionEntity) {
        RequestEntity requestEntity;
        String[] postUrl = actionEntity.getUrl().split("/");
        switch (postUrl.length) {
            case 2:
                if ("_open".equalsIgnoreCase(postUrl[1])) {
                    requestEntity = getOpenTransaction(actionEntity);
                    System.out.println("openTransaction---------------");
                    vcdbAdmin.openTransaction((OpenTransaction) requestEntity);
                } else if ("_close".equalsIgnoreCase(postUrl[1])) {
                    requestEntity = getCloseTransaction(actionEntity);
                    System.out.println("closeTransaction------------");
                    vcdbAdmin.closeTransaction((CloseTransaction) requestEntity);
                } else {
                    System.out.println("the URL Segment is error" + "给出提示（把POST开头的所有的命令返还给他");
                }
                break;
            case 4:
                if ("_insert".equalsIgnoreCase(postUrl[3])) {
                    requestEntity = getPutCells(actionEntity);
                    System.out.println("putCells-------------");
                    vcdbAdmin.putCells((PutCells) requestEntity);
                } else if ("_alter".equalsIgnoreCase(postUrl[3])) {
                    requestEntity = getAlterTable(actionEntity);
                    System.out.println("alterTable-------------");
                    vcdbAdmin.alterTable((AlterTable) requestEntity);
                } else if ("_merge".equalsIgnoreCase(postUrl[3])) {
                    requestEntity = getMergeVersion(actionEntity);
                    System.out.println("mergeVersion---------------");
                    vcdbAdmin.mergeVersion((MergeVersion) requestEntity);
                } else if ("_use".equalsIgnoreCase(postUrl[3])) {
                    requestEntity = getUseVersion(actionEntity);
                    System.out.println("useVersion-----------");
                    vcdbAdmin.useVersion((UseVersion) requestEntity);
                } else if ("_showVersion".equalsIgnoreCase(postUrl[3])) {
                    requestEntity = getShowVersion(actionEntity);
                    System.out.println("showVersion--------------");
                    vcdbAdmin.showVersion((ShowVersion) requestEntity);
                } else if ("_search".equalsIgnoreCase(postUrl[3])) {
                    requestEntity = getSingleSearch(actionEntity);
                    System.out.println("singleSearch-------------");
                    vcdbAdmin.singleSearch((SingleSearch) requestEntity);
                } else if ("_delete".equalsIgnoreCase(postUrl[3])) {
                    requestEntity = getDeleteCells(actionEntity);
                    System.out.println("deleteCells-------------------");
                    vcdbAdmin.deleteCells((DeleteCells) requestEntity);
                } else if ("delete_version".equalsIgnoreCase(postUrl[3])) {
                    requestEntity = getDeleteVersion(actionEntity);
                    System.out.println("deleteVersion----------------");
                    vcdbAdmin.deleteVersion((DeleteVersion) requestEntity);
                } else if ("_update".equalsIgnoreCase(postUrl[3])) {
                    requestEntity = getUpdateCells(actionEntity);
                    System.out.println("updateCells---------------");
                    vcdbAdmin.updateCells((UpdateCells) requestEntity);
                } else if ("_mget".equalsIgnoreCase(postUrl[3])) {
                    requestEntity = getMultiSearch(actionEntity);
                    System.out.println("multiSearch----------------");
                    vcdbAdmin.multiSearch((MultiSearch) requestEntity);
                } else {
                    System.err.println("the URL Segment is error" + "给出提示（把POST开头的所有的命令返还给他");
                }
                break;
            default:
                System.err.println("the URL Segment is error" + "给出提示（把POST开头的所有的命令返还给他");
                break;
        }
    }

    private static RequestEntity getDeleteVersion(ActionEntity actionEntity) {
        DeleteVersion deleteVersion = new DeleteVersion();
        setBaseAttribute(deleteVersion, actionEntity);
        for (Map.Entry<String, Object> cfs : actionEntity.getRegularAttribute().entrySet()) {
            if ("rowKey".equalsIgnoreCase(cfs.getKey())) {
                deleteVersion.setRowKey((String) cfs.getValue());
            } else if ("version".equalsIgnoreCase(cfs.getKey())) {
                deleteVersion.setVersion((Integer.parseInt((String) cfs.getValue())));
            } else {
                System.err.println("出现未知属性，打印key");
            }
        }
        if (actionEntity.getCompoundAttribute() != null) {
            System.err.println("出现未知属性，打印key");
        }
        return deleteVersion;
    }

    public static <T> List<T> castList(Object obj, Class<T> clazz) {
        List<T> result = new ArrayList<T>();
        if (obj instanceof List<?>) {
            for (Object o : (List<?>) obj) {
                result.add(clazz.cast(o));
            }
            return result;
        }
        return null;
    }

    public static boolean isNull(Object... varargs) {
        boolean is = false;
        for (Object arg : varargs) {//当作数组用foreach遍历
            if (arg == null) {
                is = true;
                break;
            }
        }
        return is;
    }

    private static RequestEntity getMultiSearch(ActionEntity actionEntity) {
        MultiSearch multiSearch = new MultiSearch();
        setBaseAttribute(multiSearch, actionEntity);
        if (actionEntity.getRegularAttribute() == null) {
            System.err.println("把actionEntity.getRegularAttribute打印出来并且说明");
        }
        for (Map.Entry<String, Object> cell : actionEntity.getRegularAttribute().entrySet()) {
            if ("cf_names".equalsIgnoreCase(cell.getKey())) {
                List<String> cf_names = castList(cell.getValue(), String.class);
                if (cf_names.size() == 0) {
                    System.err.println("报错提示cf_names为空");
                }
                multiSearch.setCf_names(cf_names);
            } else if ("limit".equalsIgnoreCase(cell.getKey())) {
                multiSearch.setLimit(Integer.parseInt((String) cell.getValue()));
            } else {
                System.err.println("出现未知属性，打印key");
            }
        }
        for (HashMap.Entry<String, List<HashMap<String, String>>> entry : actionEntity.getCompoundAttribute().entrySet()) {
            if ("j_tables".equalsIgnoreCase(entry.getKey())) {
                multiSearch.setJ_tables(selectJTables(entry.getValue()));
            } else if ("terms".equalsIgnoreCase(entry.getKey())) {
                multiSearch.setTerms(selectTerms(entry.getValue()));
            } else if ("orders".equalsIgnoreCase(entry.getKey())) {
                multiSearch.setOrders(selectOrders(entry.getValue()));
            } else {
                System.err.println("把key打印出来，说明它不属于关键字");
            }
        }
        return multiSearch;
    }

    private static List<Order> selectOrders(List<HashMap<String, String>> value) {
        List<Order> orders = new ArrayList<Order>();
        for (HashMap<String, String> kv : value) {
            Order order = new Order();
            for (Map.Entry<String, String> cell : kv.entrySet()) {
                if ("cf_name".equalsIgnoreCase(cell.getKey())) {
                    if (order.getCf_name() == null) {
                        order.setCf_name(cell.getValue());
                    } else {
                        System.err.println("报错重复设置cf_name属性");
                    }
                } else if ("sort".equalsIgnoreCase(cell.getKey())) {
                    if (order.getSort() == null) {
                        order.setSort(cell.getValue());
                    } else {
                        System.err.println("报错重复设置sort属性");
                    }
                } else {
                    System.err.println("出现未知属性，打印key");
                }
            }
            //TODO检查order必要属性是否为空
            if (!isNull(order.getCf_name(), order.getSort())) {
                orders.add(order);
            } else {
                System.err.println("order缺少必要属性");
            }
        }
        return orders;
    }

    private static List<JTableCell> selectJTables(List<HashMap<String, String>> value) {
        List<JTableCell> j_tables = new ArrayList<JTableCell>();
        for (HashMap<String, String> kv : value) {
            JTableCell jTableCell = new JTableCell();
            for (Map.Entry<String, String> cell : kv.entrySet()) {
                if ("tab_name".equalsIgnoreCase(cell.getKey())) {
                    if (jTableCell.getTableName() == null) {
                        jTableCell.setTableName(cell.getValue());
                    } else {
                        System.err.println("报错重复设置tableName属性");
                    }
                } else if ("method".equalsIgnoreCase(cell.getKey())) {
                    if (jTableCell.getMethod() == null) {
                        jTableCell.setMethod(cell.getValue());
                    } else {
                        System.err.println("报错重复设置Method属性");
                    }
                } else {
                    System.err.println("出现未知属性，打印key");
                }
            }
            //TODO检查jTableCell必要属性是否为空
            if (!isNull(jTableCell.getTableName(), jTableCell.getMethod())) {
                j_tables.add(jTableCell);
            } else {
                System.err.println("jTableCell的必要属性为空");
            }
        }
        return j_tables;
    }

    private static RequestEntity getUpdateCells(ActionEntity actionEntity) {
        UpdateCells updateCells = new UpdateCells();
        setBaseAttribute(updateCells, actionEntity);
        if (actionEntity.getRegularAttribute() != null) {
            System.err.println("出现未知属性，打印key");
        }
        for (HashMap.Entry<String, List<HashMap<String, String>>> entry : actionEntity.getCompoundAttribute().entrySet()) {
            if ("terms".equalsIgnoreCase(entry.getKey())) {
                updateCells.setTerms(selectTerms(entry.getValue()));
            } else if ("values".equalsIgnoreCase(entry.getKey())) {
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
            values.add(valueCell);
        }
        return values;
    }

    private static List<TermCell> selectTerms(List<HashMap<String, String>> value) {
        List<TermCell> terms = new ArrayList<TermCell>();
        for (HashMap<String, String> kv : value) {
            TermCell termCell = new TermCell();
            for (Map.Entry<String, String> cell : kv.entrySet()) {
                if ("cf_name".equalsIgnoreCase(cell.getKey())) {
                    if (termCell.getCf_name() == null) {
                        termCell.setCf_name(cell.getValue());
                    } else {
                        System.err.println("报错重复设置cf_name属性");
                    }
                } else if ("c_name".equalsIgnoreCase(cell.getKey())) {
                    if (termCell.getC_name() == null) {
                        termCell.setC_name(cell.getValue());
                    } else {
                        System.err.println("报错重复设置c_name属性");
                    }
                } else if ("size".equalsIgnoreCase(cell.getKey())) {
                    if (termCell.getSize() == 0) {
                        termCell.setSize(Long.parseLong(cell.getValue()));
                    } else {
                        System.err.println("报错重复设置size属性");
                    }
                } else if ("max".equalsIgnoreCase(cell.getKey())) {
                    if (termCell.getMax() == Double.MAX_VALUE) {
                        termCell.setMax(Double.parseDouble(cell.getValue()));
                    } else {
                        System.err.println("报错重复设置max属性");
                    }
                } else if ("equivalence".equalsIgnoreCase(cell.getKey())) {
                    if (termCell.getEquivalence() == null) {
                        termCell.setEquivalence(Integer.parseInt(cell.getValue()));
                    } else {
                        System.err.println("报错重复设置equivalence属性");
                    }
                } else if ("min".equalsIgnoreCase(cell.getKey())) {
                    if (termCell.getMin() == Double.MIN_VALUE) {
                        termCell.setMin(Double.parseDouble(cell.getValue()));
                    } else {
                        System.err.println("报错重复设置min属性");
                    }
                } else if ("like".equalsIgnoreCase(cell.getKey())) {
                    if (termCell.getLike() == null) {
                        termCell.setLike(cell.getValue());
                    } else {
                        System.err.println("报错重复设置c_name属性");
                    }
                } else {
                    System.err.println("出现未知属性，打印key");
                }
            }
            //TODO检查termCell必要属性是否为空
            if (!isNull(termCell.getCf_name())) {
                terms.add(termCell);
            } else {
                System.err.println("term缺少必要属性");
            }
        }
        return terms;
    }

    private static RequestEntity getDeleteCells(ActionEntity actionEntity) {
        DeleteCells deleteCells = new DeleteCells();
        setBaseAttribute(deleteCells, actionEntity);
        for (Map.Entry<String, Object> cfs : actionEntity.getRegularAttribute().entrySet()) {
            if ("cf_names".equalsIgnoreCase(cfs.getKey())) {
                List<String> cf_names = castList(cfs.getValue(), String.class);
                if (cf_names.size() == 0) {
                    System.err.println("报错提示cf_names为空");
                }
                deleteCells.setCf_names(cf_names);
            } else {
                System.err.println("出现未知属性，打印key");
            }
        }
        for (HashMap.Entry<String, List<HashMap<String, String>>> entry : actionEntity.getCompoundAttribute().entrySet()) {
            if ("terms".equalsIgnoreCase(entry.getKey())) {
                deleteCells.setTerms(selectTerms(entry.getValue()));
            } else {
                System.err.println("把key打印出来，说明它不属于关键字");
            }
        }
        return deleteCells;
    }

    private static RequestEntity getSingleSearch(ActionEntity actionEntity) {
        SingleSearch singleSearch = new SingleSearch();
        setBaseAttribute(singleSearch, actionEntity);
        if (actionEntity.getRegularAttribute() == null) {
            System.err.println("把actionEntity.getRegularAttribute打印出来并且说明");
        }
        for (Map.Entry<String, Object> cfs : actionEntity.getRegularAttribute().entrySet()) {
            if ("cf_names".equalsIgnoreCase(cfs.getKey())) {
                List<String> cf_names = castList(cfs.getValue(), String.class);
                if (cf_names.size() == 0) {
                    System.err.println("报错提示cf_names为空");
                }
                singleSearch.setCf_names(cf_names);
            } else if ("limit".equalsIgnoreCase(cfs.getKey())) {
                singleSearch.setLimit(Integer.parseInt((String) cfs.getValue()));
            } else {
                System.err.println("出现未知属性，打印key");
            }
        }
        for (HashMap.Entry<String, List<HashMap<String, String>>> entry : actionEntity.getCompoundAttribute().entrySet()) {
            if ("terms".equalsIgnoreCase(entry.getKey())) {
                singleSearch.setTerms(selectTerms(entry.getValue()));
            } else if ("orders".equalsIgnoreCase(entry.getKey())) {
                singleSearch.setOrders(selectOrders(entry.getValue()));
            } else if ("Aggregate".equalsIgnoreCase(entry.getKey())) {
                singleSearch.setAggregate(selectAggregate(entry.getValue()));
            } else {
                System.err.println("把key打印出来，说明它不属于关键字");
            }
        }
        return singleSearch;
    }

    private static List<Aggregate> selectAggregate(List<HashMap<String, String>> value) {
        List<Aggregate> aggregates = new ArrayList<Aggregate>();
        for (HashMap<String, String> kv : value) {
            Aggregate aggregate = new Aggregate();
            for (Map.Entry<String, String> cell : kv.entrySet()) {
                if ("c_name".equalsIgnoreCase(cell.getKey())) {
                    if (aggregate.getC_name() == null) {
                        aggregate.setC_name(cell.getValue());
                    } else {
                        System.err.println("报错重复设置cf_name属性");
                    }
                } else if ("function".equalsIgnoreCase(cell.getKey())) {
                    if (aggregate.getFunction() == null) {
                        aggregate.setFunction(cell.getValue());
                    } else {
                        System.err.println("报错重复设置c_name属性");
                    }
                } else if ("as".equalsIgnoreCase(cell.getKey())) {
                    if (aggregate.getAs() == null) {
                        aggregate.setAs(cell.getValue());
                    } else {
                        System.err.println("报错重复设置size属性");
                    }
                } else {
                    System.err.println("出现未知属性，打印key");
                }
            }
            //TODO检查termCell必要属性是否为空
            if (!isNull(aggregate.getAs())) {
                aggregates.add(aggregate);
            } else {
                System.err.println("aggregate缺少必要属性");
            }
        }
        return aggregates;
    }

    private static RequestEntity getShowVersion(ActionEntity actionEntity) {
        ShowVersion showVersion = new ShowVersion();
        setBaseAttribute(showVersion, actionEntity);
        for (Map.Entry<String, Object> cfs : actionEntity.getRegularAttribute().entrySet()) {
            if ("rowKey".equalsIgnoreCase(cfs.getKey())) {
                showVersion.setRowKey((String) cfs.getValue());
            } else {
                System.err.println("出现未知属性，打印key");
            }
        }
        if (actionEntity.getCompoundAttribute() != null) {
            System.err.println("出现未知属性，打印key");
        }
        return showVersion;
    }

    private static RequestEntity getUseVersion(ActionEntity actionEntity) {
        UseVersion useVersion = new UseVersion();
        setBaseAttribute(useVersion, actionEntity);
        for (Map.Entry<String, Object> cfs : actionEntity.getRegularAttribute().entrySet()) {
            if ("rowKey".equalsIgnoreCase(cfs.getKey())) {
                useVersion.setRowKey((String) cfs.getValue());
            } else if ("version".equalsIgnoreCase(cfs.getKey())) {
                useVersion.setVersion((Integer.parseInt((String) cfs.getValue())));
            } else {
                System.err.println("出现未知属性，打印key");
            }
        }
        if (actionEntity.getCompoundAttribute() != null) {
            System.err.println("出现未知属性，打印key");
        }
        return useVersion;
    }

    private static RequestEntity getMergeVersion(ActionEntity actionEntity) {
        MergeVersion mergeVersion = new MergeVersion();
        setBaseAttribute(mergeVersion, actionEntity);
        if (actionEntity.getRegularAttribute() != null) {
            System.err.println("出现未知属性，打印key");
        }
        for (HashMap.Entry<String, List<HashMap<String, String>>> entry : actionEntity.getCompoundAttribute().entrySet()) {
            if ("Version_terms".equalsIgnoreCase(entry.getKey())) {
                mergeVersion.setTerms(selectVersionTerms(entry.getValue()));
            } else {
                System.err.println("把key打印出来，说明它不属于关键字");
            }
        }
        return mergeVersion;
    }

    private static List<VersionTerm> selectVersionTerms(List<HashMap<String, String>> value) {
        List<VersionTerm> terms = new ArrayList<VersionTerm>();
        for (HashMap<String, String> kv : value) {
            VersionTerm termCell = new VersionTerm();
            for (Map.Entry<String, String> cell : kv.entrySet()) {
                if ("rowKey".equalsIgnoreCase(cell.getKey())) {
                    if (termCell.getRowKey() == null) {
                        termCell.setRowKey(cell.getValue());
                    } else {
                        System.err.println("报错重复设置rowKey属性");
                    }
                } else if ("version-from".equalsIgnoreCase(cell.getKey())) {
                    if (termCell.getVersionFrom() == -1) {
                        termCell.setVersionFrom(Integer.parseInt(cell.getValue()));
                    } else {
                        System.err.println("报错重复设置version-from属性");
                    }
                } else if ("version-to".equalsIgnoreCase(cell.getKey())) {
                    if (termCell.getVersionTo() == -1) {
                        termCell.setVersionTo(Integer.parseInt(cell.getValue()));
                    } else {
                        System.err.println("报错重复设置version-to属性");
                    }
                } else {
                    System.err.println("出现未知属性，打印key");
                }
            }
            //TODO检查termCell必要属性是否为空
            if (!isNull(termCell.getRowKey(), termCell.getVersionFrom(), termCell.getVersionTo())) {
                terms.add(termCell);
            } else {
                System.err.println("term缺少必要属性");
            }
        }
        return terms;

    }

    private static RequestEntity getAlterTable(ActionEntity actionEntity) {
        AlterTable alterTable = new AlterTable();
        setBaseAttribute(alterTable, actionEntity);
        if (actionEntity.getRegularAttribute() != null) {
            System.err.println("出现未知属性，打印key");
        }
        for (HashMap.Entry<String, List<HashMap<String, String>>> entry : actionEntity.getCompoundAttribute().entrySet()) {
            if ("alter_cells".equalsIgnoreCase(entry.getKey())) {
                alterTable.setAlter_cells(selectAlter(entry.getValue()));
            } else {
                System.err.println("把key打印出来，说明它不属于关键字");
            }
        }
        return alterTable;
    }

    private static List<AlterCell> selectAlter(List<HashMap<String, String>> value) {
        List<AlterCell> alterCells = new ArrayList<AlterCell>();
        for (HashMap<String, String> kv : value) {
            AlterCell alterCell = new AlterCell();
            for (Map.Entry<String, String> cell : kv.entrySet()) {
                if ("cf_name".equalsIgnoreCase(cell.getKey())) {
                    if (alterCell.getCfName() == null) {
                        alterCell.setCfName(cell.getValue());
                    } else {
                        System.err.println("报错重复设置cfName属性");
                    }
                } else if ("old-cf_name".equalsIgnoreCase(cell.getKey())) {
                    if (alterCell.getOld_cfName() == null) {
                        alterCell.setOld_cfName(cell.getValue());
                    } else {
                        System.err.println("报错重复设置old_cfName属性");
                    }
                } else if ("method".equalsIgnoreCase(cell.getKey())) {
                    if (alterCell.getMethod() == null) {
                        alterCell.setMethod(cell.getValue());
                    } else {
                        System.err.println("报错重复设置method属性");
                    }
                } else {
                    System.err.println("出现未知属性，打印key");
                }
            }
            //TODO检查termCell必要属性是否为空
            if ("put".equalsIgnoreCase(alterCell.getMethod())) {
                if (!isNull(alterCell.getCfName()) && isNull(alterCell.getOld_cfName())) {
                    alterCells.add(alterCell);
                } else {
                    System.err.println("aggregate缺少必要属性");
                }
            } else if ("delete".equalsIgnoreCase(alterCell.getMethod())) {
                if (isNull(alterCell.getCfName()) && !isNull(alterCell.getOld_cfName())) {
                    alterCells.add(alterCell);
                } else {
                    System.err.println("aggregate缺少必要属性");
                }
            } else if ("update".equalsIgnoreCase(alterCell.getMethod())) {
                if (!isNull(alterCell.getCfName()) && !isNull(alterCell.getOld_cfName())) {
                    alterCells.add(alterCell);
                } else {
                    System.err.println("aggregate缺少必要属性");
                }
            } else {
                System.err.println("aggregate缺少必要属性");
            }
        }
        return alterCells;
    }

    private static RequestEntity getPutCells(ActionEntity actionEntity) {
        PutCells putCells = new PutCells();
        setBaseAttribute(putCells, actionEntity);
        for (Map.Entry<String, Object> cfs : actionEntity.getRegularAttribute().entrySet()) {
            if ("rowKey".equalsIgnoreCase(cfs.getKey())) {
                putCells.setRowKey((String) cfs.getValue());
            } else {
                System.err.println("出现未知属性，打印key");
            }
        }
        for (HashMap.Entry<String, List<HashMap<String, String>>> entry : actionEntity.getCompoundAttribute().entrySet()) {
            if ("values".equalsIgnoreCase(entry.getKey())) {
                putCells.setValues(selectValues(entry.getValue()));
            } else {
                System.err.println("把key打印出来，说明它不属于关键字");
            }
        }
        return putCells;
    }

    private static RequestEntity getCloseTransaction(ActionEntity actionEntity) {
        CloseTransaction closeTransaction = new CloseTransaction();
        setBaseAttribute(closeTransaction, actionEntity);
        if (actionEntity.getRegularAttribute() != null) {
            System.err.println("出现未知属性，打印key");
        }
        if (actionEntity.getCompoundAttribute() != null) {
            System.err.println("出现未知属性，打印key");
        }
        return closeTransaction;
    }

    private static RequestEntity getOpenTransaction(ActionEntity actionEntity) {
        OpenTransaction openTransaction = new OpenTransaction();
        setBaseAttribute(openTransaction, actionEntity);
        for (Map.Entry<String, Object> cfs : actionEntity.getRegularAttribute().entrySet()) {
            if ("explainValue".equalsIgnoreCase(cfs.getKey())) {
                openTransaction.setExplainValue((String) cfs.getValue());
            } else {
                System.err.println("出现未知属性，打印key");
            }
        }
        if (actionEntity.getCompoundAttribute() != null) {
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
}
