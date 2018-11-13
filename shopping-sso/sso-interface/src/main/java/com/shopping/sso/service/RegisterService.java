package com.shopping.sso.service;

import com.shopping.common.utils.ShoppingResult;
import com.shopping.pojo.TbUser;

public interface RegisterService {

    ShoppingResult checkData(String param,int type);

    ShoppingResult register(TbUser user);
}
