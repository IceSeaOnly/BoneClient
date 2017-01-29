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

    public IndexItemEntity(int id, String imageUrl, String name, String function, String functionUrl) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.name = name;
        this.function = function;
        this.functionUrl = functionUrl;
    }

    public IndexItemEntity() {
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
}
