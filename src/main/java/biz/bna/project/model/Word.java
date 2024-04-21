package biz.bna.project.model;

import biz.bna.project.annotations.Column;
import biz.bna.project.annotations.Id;
import biz.bna.project.annotations.Table;

@Table(name = "word")
public class Word {

    @Id
    @Column(name = "word_id")
    private Integer wordId;

    @Column(name = "word_text")
    private String wordText;

    public Word() {
    }

    public Word(Integer wordId, String wordText) {
        this.wordId = wordId;
        this.wordText = wordText;
    }

    public Integer getWordId() {
        return wordId;
    }

    public void setWordId(Integer wordId) {
        this.wordId = wordId;
    }

    public String getWordText() {
        return wordText;
    }

    public void setWordText(String wordText) {
        this.wordText = wordText;
    }
}
