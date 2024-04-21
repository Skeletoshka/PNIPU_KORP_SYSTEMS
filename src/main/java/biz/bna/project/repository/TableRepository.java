package biz.bna.project.repository;

import biz.bna.project.rowmapper.RowMapForObject;
import biz.bna.project.utils.DatabaseUtils;
import biz.bna.project.utils.OrmUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public abstract class TableRepository <T>{
    private final Class<T> cls;
    protected final DatabaseUtils databaseUtils;

    public TableRepository(Class<T> cls){
        databaseUtils = new DatabaseUtils();
        this.cls = cls;
    }

    public List<T> getAll(){
        String sql = String.format("SELECT * FROM %s", OrmUtils.getTableName(cls));
        RowMapForObject<T> rowMapper = new RowMapForObject<T>(cls);
        return databaseUtils.select(rowMapper, sql);
    }

    public T getOne(int id){
        String sql = String.format("SELECT * FROM %s WHERE %1$s_id = ?", OrmUtils.getTableName(cls));
        RowMapForObject<T> rowMapper = new RowMapForObject<T>(cls);
        List<T> rows = databaseUtils.select(rowMapper, sql, id);
        if(rows.isEmpty()){
            return null;
        }else{
            return rows.get(0);
        }
    }

    public List<T> findWhere(String where, Map<Integer, Object> params){
        String sql = String.format("SELECT * FROM %s WHERE %s", OrmUtils.getTableName(cls), where);
        RowMapForObject<T> rowMapper = new RowMapForObject<T>(cls);
        return databaseUtils.select(rowMapper, sql, params);
    }

    public List<T> findWhere(String where, Object param0Value){
        String sql = String.format("SELECT * FROM %s WHERE %s", OrmUtils.getTableName(cls), where);
        RowMapForObject<T> rowMapper = new RowMapForObject<T>(cls);
        return databaseUtils.select(rowMapper, sql, param0Value);
    }

    public void delete(int id){
        String sql = String.format("DELETE FROM %s WHERE %1$s_id = ?", OrmUtils.getTableName(cls));
        databaseUtils.executeSql(sql, id);
    }

    public void delete(int[] ids){
        for(int id: ids){
            delete(id);
        }
    }

    public abstract void update(T obj);
    public abstract void insert(T obj);

}
