package com.shopping.common.utils;

import java.util.ArrayList;
import java.util.List;

public class StringUtils {

    /**
     * 根据前端传过来的字符串如("123465,1234845")
     * 拆分成mybatis需要使用的List<Long>类型
     * @param ids
     * @return
     */
    public static List<Long> getIDsListByStr(String ids){
        List<Long> longs = new ArrayList<>();
        if (org.apache.commons.lang3.StringUtils.isEmpty(ids)) {
            return longs;
        }
        String[] strs = ids.split(",");
        for (String str: strs) {
            longs.add(new Long(str));
        }
        return longs;
    }
}
