package com.qdxiaogutou.boneclient.Entity;


/**
 * Created by Administrator on 2016/11/22.
 */

public class School {

    private int id;
    private String schoolName;
    private String tag;/**SDUST*/

    public School(String schoolName, String tag) {
        this.schoolName = schoolName;
        this.tag = tag;
    }

    public School() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
