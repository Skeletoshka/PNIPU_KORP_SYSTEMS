package biz.bna.project.repository;

import biz.bna.project.model.Word;

import java.util.Map;

public class WordRepository extends TableRepository<Word>{
    public WordRepository() {
        super(Word.class);
    }

    @Override
    public void update(Word obj) {
        String sql = String.format("UPDATE %s SET " +
                "%1$s_text = ? " +
                "WHERE %1$s_id = ?", "word");
        Map<Integer, Object> params = Map.of(
                1, obj.getWordText(),
                2, obj.getWordId()
        );
        databaseUtils.executeSql(sql, params);
    }

    @Override
    public void insert(Word obj) {
        String sql = String.format("INSERT INTO %s (%1$s_id, %1$s_text) VALUES (?, ?)", "word");
        obj.setWordId(databaseUtils.getSequenceNextValue("word_id_gen"));
        Map<Integer, Object> params = Map.of(
                1, obj.getWordId(),
                2, obj.getWordText()
        );
        databaseUtils.executeSql(sql, params);
    }
}
