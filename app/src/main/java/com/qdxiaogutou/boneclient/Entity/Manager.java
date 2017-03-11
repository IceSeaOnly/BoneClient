package com.qdxiaogutou.boneclient.Entity;


/**
 * Created by Administrator on 2017/1/7.
 */

public class Manager {
    private int id;
    private String name;
    private String phone;
    private String pass;
    private int salary;
    private Long lastLogin;
    private Long regTime;
    private boolean forbiden;
    private String tmp_token;
    private int tmp_schoolId;
    private String tmp_schoolName;
    private boolean tmp_tag;
    private String alipay;
    private String wxpay;
    private String openId;
    private double dividendRatio;
    private String pdesc;
    private boolean could_delete;
    private String address;
    private boolean cs_notice;

    public Manager(String name, String phone, String pass,String ali,String wx,String openId,double dr,String descStr,String add) {
        this.pdesc = descStr;
        this.dividendRatio = dr;
        this.openId = openId;
        this.alipay = ali;
        this.wxpay = wx;
        this.name = name;
        this.phone = phone;
        this.pass = pass;
        this.salary = 0;
        this.lastLogin = System.currentTimeMillis();
        this.regTime = System.currentTimeMillis();
        this.forbiden = false;
        this.could_delete = true;
        this.address = add;
        this.cs_notice = true;
    }

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

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public Long getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Long lastLogin) {
        this.lastLogin = lastLogin;
    }

    public Long getRegTime() {
        return regTime;
    }

    public void setRegTime(Long regTime) {
        this.regTime = regTime;
    }

    public boolean isForbiden() {
        return forbiden;
    }

    public void setForbiden(boolean forbiden) {
        this.forbiden = forbiden;
    }

    public boolean isTmp_tag() {
        return tmp_tag;
    }

    public void setTmp_tag(boolean tmp_tag) {
        this.tmp_tag = tmp_tag;
    }

    public String getAlipay() {
        return alipay;
    }

    public void setAlipay(String alipay) {
        this.alipay = alipay;
    }

    public String getWxpay() {
        return wxpay;
    }

    public void setWxpay(String wxpay) {
        this.wxpay = wxpay;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public double getDividendRatio() {
        return dividendRatio;
    }

    public void setDividendRatio(double dividendRatio) {
        this.dividendRatio = dividendRatio;
    }

    public String getPdesc() {
        return pdesc;
    }

    public void setPdesc(String pdesc) {
        this.pdesc = pdesc;
    }

    public boolean isCould_delete() {
        return could_delete;
    }

    public void setCould_delete(boolean could_delete) {
        this.could_delete = could_delete;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isCs_notice() {
        return cs_notice;
    }

    public void setCs_notice(boolean cs_notice) {
        this.cs_notice = cs_notice;
    }
}
