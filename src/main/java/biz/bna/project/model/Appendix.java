package biz.bna.project.model;

import biz.bna.project.annotations.Column;
import biz.bna.project.annotations.Id;
import biz.bna.project.annotations.Table;

@Table(name = "appendix")
public class Appendix {

    @Id
    @Column(name = "appendix_id")
    private Integer appendixId;

    @Column(name = "appendix_name")
    private String appendixName;

    @Column(name = "appendix_path")
    private String appendixPath;

    public Appendix() {
    }

    public Appendix(Integer appendixId, String appendixName, String appendixPath) {
        this.appendixId = appendixId;
        this.appendixName = appendixName;
        this.appendixPath = appendixPath;
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
}
