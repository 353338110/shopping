package com.shopping.common.pojo;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.List;

@Data
public class EasyUIDataGridResult implements Serializable {
    private long total;
    private List rows;
}
