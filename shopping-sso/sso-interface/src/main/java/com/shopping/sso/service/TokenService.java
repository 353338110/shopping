package com.shopping.sso.service;

import com.shopping.common.utils.ShoppingResult;

/**
 * 根据token查询用户信息
 */
public interface TokenService {
    ShoppingResult getUserByToken(String token);

}
