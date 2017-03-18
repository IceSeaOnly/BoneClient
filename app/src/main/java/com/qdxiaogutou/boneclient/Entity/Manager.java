package com.qdxiaogutou.boneclient.Entity;


/**
 * Created by Administrator on 2017/1/7.
 */

public class Manager {
    private int id;
    private String name;
    private String phone;
    private String tmp_token;
    private int tmp_schoolId;
    private String tmp_schoolName;
    private boolean tmp_tag;


    public Manager() {
    }

    public String getTmp_schoolName() {
        return tmp_schoolName;
    }

    public void setTmp_schoolName(String tmp_schoolName) {
        this.tmp_schoolName = tmp_schoolName;
    }

    public int getTmp_schoolId() {
        return tmp_schoolId;
    }

    public void setTmp_schoolId(int tmp_schoolId) {
        this.tmp_schoolId = tmp_schoolId;
    }

    public String getTmp_token() {
        return tmp_token;
    }

    public void setTmp_token(String tmp_token) {
        this.tmp_token = tmp_token;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isTmp_tag() {
        return tmp_tag;
    }

    public void setTmp_tag(boolean tmp_tag) {
        this.tmp_tag = tmp_tag;
    }
}
