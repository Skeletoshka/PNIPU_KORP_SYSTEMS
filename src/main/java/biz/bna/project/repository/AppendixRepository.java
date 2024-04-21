package biz.bna.project.repository;

import biz.bna.project.model.Appendix;

import java.util.HashMap;
import java.util.Map;

public class AppendixRepository extends TableRepository<Appendix>{

    public AppendixRepository() {
        super(Appendix.class);
    }


    @Override
    public void update(Appendix obj) {
        String sql = String.format("UPDATE %s SET " +
                "%1$s_name = ?, " +
                "%1$s_path = ? " +
                "WHERE %1$s_id = ?", "appendix");
        Map<Integer, Object> params = Map.of(
                1, obj.getAppendixName(),
                2, obj.getAppendixPath(),
                3, obj.getAppendixId()
        );
        databaseUtils.executeSql(sql, params);
    }

    @Override
    public void insert(Appendix obj) {
        String sql = String.format("INSERT INTO %s (%1$s_id, %1$s_name, %1$s_path) VALUES (?, ?, ?)", "appendix");
        obj.setAppendixId(databaseUtils.getSequenceNextValue("appendix_id_gen"));
        Map<Integer, Object> params = Map.of(
                1, obj.getAppendixId(),
                2, obj.getAppendixName(),
                3, obj.getAppendixPath()
        );
        databaseUtils.executeSql(sql, params);
    }
}
