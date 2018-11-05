package com.shopping.common.utils;

public enum StatusEnum {
    SUCCESS(200,"OK"),
    ERROR(1,"ERROR"),
    MYSQL_ERROR(2001,"数据库操作失败"),
    DELETE_NODE_ERROR(2002,"该节点不是叶子节点，无法删除"),//业务逻辑失误
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
