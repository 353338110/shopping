package com.shopping.common.utils;

public enum StatusEnum {
    SUCCESS(200,"OK"),
    ERROR(1,"ERROR"),
    MYSQL_ERROR(2001,"数据库操作失败"),
    DELETE_NODE_ERROR(2002,"该节点不是叶子节点，无法删除"),//业务逻辑失误
    REGISTER_TYPE_ERROR(2003,"注册选择的方式错误"),//注册type不是1，2，3
    REGISTER_ERROR_INCOMPLETE(2004,"用户数据不完整，注册失败"),//用户数据不完整，注册失败
    REGISTER_ERROR_USERNAME(2005,"此用户名已经被占用"),
    REGISTER_ERROR_PHONE(2006,"手机号已经被占用"),
    LOGIN_ERROR(2007,"用户名或密码错误"),
    LOGIN_EXPIRE(2008,"用户登录已经过期"),
    SOLR_ERROR(2009,"导入solr失败"),
    NEED_LOGIN(10,"NEED_LOGIN"),
    ILLEGAL_ARGUMENT(2,"ILLEGAL_ARGUMENT");

    private final int code;
    private final String desc;
    StatusEnum(int code,String desc){
        this.code = code;
        this.desc = desc;
    }

    public int  getCode(){
        return code;
    }

    public String getDesc(){
        return desc;
    }

}
