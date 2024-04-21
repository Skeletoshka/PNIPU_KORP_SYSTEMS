package biz.bna.project.repository;

import biz.bna.project.model.Repeat;
import biz.bna.project.utils.DatabaseUtils;

import java.util.Map;

public class RepeatRepository extends TableRepository<Repeat>{

    public RepeatRepository() {
        super(Repeat.class);
    }

    @Override
    public void update(Repeat obj) {
        String sql = String.format("UPDATE %s SET " +
                "appendix_id = ?, " +
                "word_id = ?, " +
                "%1$s_count = ? " +
                "WHERE %1$s_id = ?", "repeat");
        Map<Integer, Object> params = Map.of(
                1, obj.getAppendixId(),
                2, obj.getWordId(),
                3, obj.getRepeatCount(),
                4, obj.getRepeatId()
        );
        databaseUtils.executeSql(sql, params);
    }

    @Override
    public void insert(Repeat obj) {
        String sql = String.format("INSERT INTO %s ( %1$s_id, appendix_id, word_id, %1$s_count) " +
                "VALUES (?, ?, ?, ?) ", "repeat");
        Map<Integer, Object> params = Map.of(
                1, databaseUtils.getSequenceNextValue("repeat_id_gen"),
                2, obj.getAppendixId(),
                3, obj.getWordId(),
                4, obj.getRepeatCount()
        );
        databaseUtils.executeSql(sql, params);
    }
}
