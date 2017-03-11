package com.qdxiaogutou.boneclient.Entity;

/**
 * Created by Administrator on 2016/12/2.
 */
public class IndexItemEntity {
    private int id;
    private String imageUrl;
    private String name;
    private String function;
    private String functionUrl;
    private boolean tmp_status; // 用来表示页面的临时变量
    private boolean super_only;//仅超管可用

    public IndexItemEntity(int id, String imageUrl, String name, String function, String functionUrl) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.name = name;
        this.function = function;
        this.functionUrl = functionUrl;
    }

    public IndexItemEntity() {
    }

    public boolean isSuper_only() {
        return super_only;
    }

    public void setSuper_only(boolean super_only) {
        this.super_only = super_only;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    public String getFunctionUrl() {
        return functionUrl;
    }

    public void setFunctionUrl(String functionUrl) {
        this.functionUrl = functionUrl;
    }

    public boolean isTmp_status() {
        return tmp_status;
    }

    public void setTmp_status(boolean tmp_status) {
        this.tmp_status = tmp_status;
    }
}
