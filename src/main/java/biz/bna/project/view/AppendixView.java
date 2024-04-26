package biz.bna.project.view;

import biz.bna.project.annotations.Column;

public class AppendixView {

    @Column(name = "appendix_id")
    private Integer appendixId;

    @Column(name = "appendix_name")
    private String appendixName;

    @Column(name = "appendix_path")
    private String appendixPath;

    @Column(name = "relevance")
    private Integer relevance;

    public AppendixView() {
    }

    public AppendixView(Integer appendixId,
                        String appendixName,
                        String appendixPath,
                        Integer relevance) {
        this.appendixId = appendixId;
        this.appendixName = appendixName;
        this.appendixPath = appendixPath;
        this.relevance = relevance;
    }

    public Integer getAppendixId() {
        return appendixId;
    }

    public void setAppendixId(Integer appendixId) {
        this.appendixId = appendixId;
    }

    public String getAppendixName() {
        return appendixName;
    }

    public void setAppendixName(String appendixName) {
        this.appendixName = appendixName;
    }

    public String getAppendixPath() {
        return appendixPath;
    }

    public void setAppendixPath(String appendixPath) {
        this.appendixPath = appendixPath;
    }

    public Integer getRelevance() {
        return relevance;
    }

    public void setRelevance(Integer relevance) {
        this.relevance = relevance;
    }
}
