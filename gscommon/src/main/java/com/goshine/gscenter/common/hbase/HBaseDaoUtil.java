package com.goshine.gscenter.common.hbase;

import com.goshine.gscenter.common.hbase.annotations.HbaseColumn;
import com.goshine.gscenter.common.hbase.annotations.HbaseTable;
import com.goshine.gscenter.gskit.collection.ListUtil;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.hbase.*;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.client.coprocessor.AggregationClient;
import org.apache.hadoop.hbase.client.coprocessor.LongColumnInterpreter;
import org.apache.hadoop.hbase.filter.*;
import org.apache.hadoop.hbase.util.Bytes;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import scala.Int;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;

@Component("hBaseDaoUtil")
public class HBaseDaoUtil {
    protected final org.slf4j.Logger logger = LoggerFactory.getLogger(this.getClass());

    // 关闭连接
    public static void close() {
        if (HconnectionFactory.connection != null) {
            try {
                HconnectionFactory.connection.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @Descripton: 创建表
     * @param tableName
     * @param familyColumn
     */
    public void createTable(String tableName, Set<String> familyColumn) {
        TableName tn = TableName.valueOf(tableName);
        try (Admin admin = HconnectionFactory.connection.getAdmin();) {
            HTableDescriptor htd = new HTableDescriptor(tn);
            for (String fc : familyColumn) {
                HColumnDescriptor hcd = new HColumnDescriptor(fc);
                htd.addFamily(hcd);
            }
            admin.createTable(htd);
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("创建"+tableName+"表失败！", e);
        }
    }

    /**
     * @Descripton: 删除表
     * @param tableName
     */
    public void dropTable(String tableName) {
        TableName tn = TableName.valueOf(tableName);
        try (Admin admin = HconnectionFactory.connection.getAdmin();){
            admin.disableTable(tn);
            admin.deleteTable(tn);
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("删除"+tableName+"表失败！");
        }
    }

    /**
     * @Descripton: 根据条件过滤查询
     * @param obj
     * @param param
     */
    public <T> List<T> queryScan(T obj, Map<String, String> param)throws Exception{
        List<T> objs = new ArrayList<T>();
        String tableName = getORMTable(obj);
        if (StringUtils.isBlank(tableName)) {
            return null;
        }
        try (Table table = HconnectionFactory.connection.getTable(TableName.valueOf(tableName)); Admin admin = HconnectionFactory.connection.getAdmin();){
            if(!admin.isTableAvailable(TableName.valueOf(tableName))){
                return objs;
            }
            Scan scan = new Scan();
            for (Map.Entry<String, String> entry : param.entrySet()){
                Class<?> clazz = obj.getClass();
                Field[] fields = clazz.getDeclaredFields();
                for (Field field : fields) {
                    if (!field.isAnnotationPresent(HbaseColumn.class)) {
                        continue;
                    }
                    field.setAccessible(true);
                    HbaseColumn orm = field.getAnnotation(HbaseColumn.class);
                    String family = orm.family();
                    String qualifier = orm.qualifier();
                    if(qualifier.equals(entry.getKey())){
                        Filter filter = new SingleColumnValueFilter(Bytes.toBytes(family), Bytes.toBytes(entry.getKey()), CompareFilter.CompareOp.EQUAL, Bytes.toBytes(entry.getValue()));
                        scan.setFilter(filter);
                    }
                }
            }
            ResultScanner scanner = table.getScanner(scan);
            for (Result result : scanner) {
                T beanClone = (T) BeanUtils.cloneBean(HBaseBeanUtil.resultToBean(result, obj));
                objs.add(beanClone);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("查询失败！");
            throw new Exception(e);
        }
        return objs;
    }

    /**
     * @Descripton: 根据rowkey查询
     * @param obj
     * @param rowkeys
     */
    public <T> List<T> get(T obj, String ... rowkeys) {
        List<T> objs = new ArrayList<T>();
        String tableName = getORMTable(obj);
        if (StringUtils.isBlank(tableName)) {
            return objs;
        }
        try (Table table = HconnectionFactory.connection.getTable(TableName.valueOf(tableName));Admin admin = HconnectionFactory.connection.getAdmin();){
            if(!admin.isTableAvailable(TableName.valueOf(tableName))){
                return objs;
            }
            List<Result> results = getResults(tableName, rowkeys);
            if (results.isEmpty()) {
                return objs;
            }
            for (int i = 0; i < results.size(); i++) {
                T bean = null;
                Result result = results.get(i);
                if (result == null || result.isEmpty()) {
                    continue;
                }
                try {
                    bean = HBaseBeanUtil.resultToBean(result, obj);
                    objs.add(bean);
                } catch (Exception e) {
                    e.printStackTrace();
                    logger.error("查询异常！", e);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return objs;
    }


    /**
     * @Descripton: 保存实体对象
     * @param objs
     */
    public <T> boolean save(T ... objs) {
        List<Put> puts = new ArrayList<Put>();
        String tableName = "";
        try (Admin admin = HconnectionFactory.connection.getAdmin();){
            for (Object obj : objs) {
                if (obj == null) {
                    continue;
                }
                tableName = getORMTable(obj);
                // 表不存在，先获取family创建表
                if(!admin.isTableAvailable(TableName.valueOf(tableName))){
                    // 获取family, 创建表
                    Class<?> clazz = obj.getClass();
                    Field[] fields = clazz.getDeclaredFields();
                    Set<String> set = new HashSet<>(10);
                    for(int i=0;i<fields.length;i++){
                        if (!fields[i].isAnnotationPresent(HbaseColumn.class)) {
                            continue;
                        }
                        fields[i].setAccessible(true);
                        HbaseColumn orm = fields[i].getAnnotation(HbaseColumn.class);
                        String family = orm.family();
                        if ("rowkey".equalsIgnoreCase(family)) {
                            continue;
                        }
                        set.add(family);
                    }
                    // 创建表
                    createTable(tableName, set);
                }
                Put put = HBaseBeanUtil.beanToPut(obj);
                puts.add(put);
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error("保存Hbase异常！");
        }
        return savePut(puts, tableName);
    }

    /**
     * @Descripton: 保存实体对象
     * @param objList
     */
    public <T> boolean batchSave(List<T> objList) {
        List<Put> puts = new ArrayList<Put>();
        String tableName = "";
        try (Admin admin = HconnectionFactory.connection.getAdmin();){
            for (Object obj : objList) {
                if (obj == null) {
                    continue;
                }
                tableName = getORMTable(obj);
                // 表不存在，先获取family创建表
                if(!admin.isTableAvailable(TableName.valueOf(tableName))){
                    // 获取family, 创建表
                    Class<?> clazz = obj.getClass();
                    Field[] fields = clazz.getDeclaredFields();
                    Set<String> set = new HashSet<>(10);
                    for(int i=0;i<fields.length;i++){
                        if (!fields[i].isAnnotationPresent(HbaseColumn.class)) {
                            continue;
                        }
                        fields[i].setAccessible(true);
                        HbaseColumn orm = fields[i].getAnnotation(HbaseColumn.class);
                        String family = orm.family();
                        if ("rowkey".equalsIgnoreCase(family)) {
                            continue;
                        }
                        set.add(family);
                    }
                    // 创建表
                    createTable(tableName, set);
                }
                Put put = HBaseBeanUtil.beanToPut(obj);
                puts.add(put);
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error("保存Hbase异常！");
        }
        return savePut(puts, tableName);
    }

    /**
     * @Descripton: 根据tableName保存
     * @param tableName
     * @param objs
     */
    public <T> void save(String tableName, T ... objs){
        List<Put> puts = new ArrayList<Put>();
        for (Object obj : objs) {
            if (obj == null) {
                continue;
            }
            try {
                Put put = HBaseBeanUtil.beanToPut(obj);
                puts.add(put);
            } catch (Exception e) {
                logger.warn("", e);
            }
        }
        savePut(puts, tableName);
    }

    /**
     * @Descripton: 删除
     * @param obj
     * @param rowkeys
     */
    public <T> void delete(T obj, String... rowkeys) {
        String tableName = "";
        tableName = getORMTable(obj);
        if (StringUtils.isBlank(tableName)) {
            return;
        }
        List<Delete> deletes = new ArrayList<Delete>();
        for (String rowkey : rowkeys) {
            if (StringUtils.isBlank(rowkey)) {
                continue;
            }
            deletes.add(new Delete(Bytes.toBytes(rowkey)));
        }
        delete(deletes, tableName);
    }


    /**
     * @Descripton: 批量删除
     * @param deletes
     * @param tableName
     */
    private void delete(List<Delete> deletes, String tableName) {
        try (Table table = HconnectionFactory.connection.getTable(TableName.valueOf(tableName));) {
            if (StringUtils.isBlank(tableName)) {
                logger.info("tableName为空！");
                return;
            }
            table.delete(deletes);
        } catch (IOException e) {
            e.printStackTrace();
            logger.error("删除失败！",e);
        }
    }

    /**
     * @Descripton: 根据tableName获取列簇名称
     * @param tableName
     */
    public List<String> familys(String tableName) {
        try (Table table = HconnectionFactory.connection.getTable(TableName.valueOf(tableName));){
            List<String> columns = new ArrayList<String>();
            if (table==null) {
                return columns;
            }
            HTableDescriptor tableDescriptor = table.getTableDescriptor();
            HColumnDescriptor[] columnDescriptors = tableDescriptor.getColumnFamilies();
            for (HColumnDescriptor columnDescriptor :columnDescriptors) {
                String columnName = columnDescriptor.getNameAsString();
                columns.add(columnName);
            }
            return columns;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("查询列簇名称失败！" ,e);
        }
        return new ArrayList<String>();
    }

    // 保存方法
    private boolean savePut(List<Put> puts, String tableName){
        if (StringUtils.isBlank(tableName)) {
            return false;
        }
        try (Table table = HconnectionFactory.connection.getTable(TableName.valueOf(tableName));Admin admin = HconnectionFactory.connection.getAdmin();){
            table.put(puts);
            return true;
        }catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 获取tableName
    private String getORMTable(Object obj) {
        HbaseTable table = obj.getClass().getAnnotation(HbaseTable.class);
        return table.tableName();
    }

    // 获取查询结果
    private List<Result> getResults(String tableName, String... rowkeys) {
        List<Result> resultList = new ArrayList<Result>();
        List<Get> gets = new ArrayList<Get>();
        for (String rowkey : rowkeys) {
            if (StringUtils.isBlank(rowkey)) {
                continue;
            }
            Get get = new Get(Bytes.toBytes(rowkey));
            gets.add(get);
        }
        try (Table table = HconnectionFactory.connection.getTable(TableName.valueOf(tableName));) {
            Result[] results = table.get(gets);
            Collections.addAll(resultList, results);
            return resultList;
        } catch (Exception e) {
            e.printStackTrace();
            return resultList;
        }
    }

    public <T> HBasePageBean<T> queryPageScanGreater(HBasePageBean<T> thBasePageBean,T obj, Map<String, String> param)throws Exception{

//        if(thBasePageBean.getList()==null||thBasePageBean.getList().size()==0){
//            //获取总记录数
//            thBasePageBean.setTotalCount(116L);
//        }

        List<T> objs = new ArrayList<T>();
        String tableName = getORMTable(obj);
        if (StringUtils.isBlank(tableName)) {
            return null;
        }
        try (Table table = HconnectionFactory.connection.getTable(TableName.valueOf(tableName));Admin admin = HconnectionFactory.connection.getAdmin();){
            if(!admin.isTableAvailable(TableName.valueOf(tableName))){
                thBasePageBean.setList(objs);
                return thBasePageBean;
            }
            Scan scan = new Scan();

            FilterList filterList = new FilterList(
                    FilterList.Operator.MUST_PASS_ALL);
            Filter pageFilter = new PageFilter(thBasePageBean.getPageSize()+1);
            if(!StringUtils.isEmpty(thBasePageBean.getNextPageRow())) {
                Filter rowFilter1 = new RowFilter(CompareFilter.CompareOp.GREATER_OR_EQUAL,
                        new BinaryComparator(Bytes.toBytes(thBasePageBean.getNextPageRow())));
                filterList.addFilter(rowFilter1);
            }

            filterList.addFilter(pageFilter);

            scan.setFilter(filterList);

            if(param!=null)
                for (Map.Entry<String, String> entry : param.entrySet()){
                    Class<?> clazz = obj.getClass();
                    Field[] fields = clazz.getDeclaredFields();
                    for (Field field : fields) {
                        if (!field.isAnnotationPresent(HbaseColumn.class)) {
                            continue;
                        }
                        field.setAccessible(true);
                        HbaseColumn orm = field.getAnnotation(HbaseColumn.class);
                        String family = orm.family();
                        String qualifier = orm.qualifier();
                        if(qualifier.equals(entry.getKey())){
                            Filter filter = new SingleColumnValueFilter(Bytes.toBytes(family), Bytes.toBytes(entry.getKey()), CompareFilter.CompareOp.GREATER_OR_EQUAL, Bytes.toBytes(entry.getValue()));
                            scan.setFilter(filter);
                        }
                    }
                }

            if(ListUtil.isEmpty(thBasePageBean.getList()))
                thBasePageBean = getPages(thBasePageBean,obj,param,tableName,table);

            ResultScanner scanner = table.getScanner(scan);
            int i=0;
            String firstRow = null;
            for (Result result : scanner) {
                i++;
                if(thBasePageBean.getCurrPage()==1&&i==1){
                    firstRow = Bytes.toString(result.getRow());
                }
                if(i==(thBasePageBean.getPageSize()+1)){
                    thBasePageBean.setPrePageRow(thBasePageBean.getCurPageRow());
                    thBasePageBean.setCurPageRow(thBasePageBean.getNextPageRow());
                    thBasePageBean.setNextPageRow(Bytes.toString(result.getRow()));
                }else{
                    T beanClone = (T)BeanUtils.cloneBean(HBaseBeanUtil.resultToBean(result, obj));
                    objs.add(beanClone);
                }
            }
            if(firstRow!=null){
                thBasePageBean.setPrePageRow(firstRow);
                thBasePageBean.setCurPageRow(firstRow);
            }

        } catch (Exception e) {
            e.printStackTrace();
            logger.error("查询失败！");
            throw new Exception(e);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            logger.error("查询失败！");
            throwable.printStackTrace();
        }

        thBasePageBean.setList(objs);

        return thBasePageBean;
    }

    private <T> HBasePageBean<T> getPages(HBasePageBean<T> thBasePageBean,T obj, Map<String, String> param,String tableName,Table table){
        if(ListUtil.isEmpty(thBasePageBean.getList())){
            enableAggregation(tableName);

            Scan scan = new Scan();
            FilterList filterList = new FilterList(
                    FilterList.Operator.MUST_PASS_ALL);
            if(!StringUtils.isEmpty(thBasePageBean.getCurPageRow())) {
                Filter rowFilter1 = new RowFilter(CompareFilter.CompareOp.GREATER_OR_EQUAL,
                        new BinaryComparator(Bytes.toBytes(thBasePageBean.getCurPageRow())));
                filterList.addFilter(rowFilter1);
            }

            scan.setFilter(filterList);

            AggregationClient aggregation = new AggregationClient(HconnectionFactory.connection.getConfiguration());
            Long count = null;
            try {
                count = aggregation.rowCount(table,
                        new LongColumnInterpreter(), scan);
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
            thBasePageBean.setTotalCount(count);
            thBasePageBean.setTotalPage((count % thBasePageBean.getPageSize() == 0) ? count/thBasePageBean.getPageSize(): (count/thBasePageBean.getPageSize()) + 1);
        }
        return  thBasePageBean;
    }

    private void enableAggregation(String tableName) {
        String coprocessorName = "org.apache.hadoop.hbase.coprocessor.AggregateImplementation";
        try {
            HBaseAdmin admin = new HBaseAdmin(HconnectionFactory.connection.getConfiguration());
            HTableDescriptor htd = admin.getTableDescriptor(Bytes.toBytes(tableName));
            List<String> coprocessors = htd.getCoprocessors();
            if (coprocessors != null && coprocessors.size() > 0) {
                return;
            } else {
                admin.disableTable(tableName);
                htd.addCoprocessor(coprocessorName);
                admin.modifyTable(tableName, htd);
                admin.enableTable(tableName);
            }
        } catch (TableNotFoundException e) {
            logger.error(e.getMessage());
        } catch (MasterNotRunningException e) {
            logger.error(e.getMessage());
        } catch (ZooKeeperConnectionException e) {
            logger.error(e.getMessage());
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }



    /**
     * @Descripton: 根据条件过滤查询（大于等于）
     * @param obj
     * @param param
     */
    public <T> List<T> queryScanGreater(T obj, Map<String, String> param)throws Exception{
        List<T> objs = new ArrayList<T>();
        String tableName = getORMTable(obj);
        if (StringUtils.isBlank(tableName)) {
            return null;
        }
        try (Table table = HconnectionFactory.connection.getTable(TableName.valueOf(tableName));Admin admin = HconnectionFactory.connection.getAdmin();){
            if(!admin.isTableAvailable(TableName.valueOf(tableName))){
                return objs;
            }
            Scan scan = new Scan();
            if(param!=null)
            for (Map.Entry<String, String> entry : param.entrySet()){
                Class<?> clazz = obj.getClass();
                Field[] fields = clazz.getDeclaredFields();
                for (Field field : fields) {
                    if (!field.isAnnotationPresent(HbaseColumn.class)) {
                        continue;
                    }
                    field.setAccessible(true);
                    HbaseColumn orm = field.getAnnotation(HbaseColumn.class);
                    String family = orm.family();
                    String qualifier = orm.qualifier();
                    if(qualifier.equals(entry.getKey())){
                        Filter filter = new SingleColumnValueFilter(Bytes.toBytes(family), Bytes.toBytes(entry.getKey()), CompareFilter.CompareOp.GREATER_OR_EQUAL, Bytes.toBytes(entry.getValue()));
                        scan.setFilter(filter);
                    }
                }
            }
            ResultScanner scanner = table.getScanner(scan);
            for (Result result : scanner) {
                T beanClone = (T)BeanUtils.cloneBean(HBaseBeanUtil.resultToBean(result, obj));
                objs.add(beanClone);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("查询失败！");
            throw new Exception(e);
        }
        return objs;
    }
}

