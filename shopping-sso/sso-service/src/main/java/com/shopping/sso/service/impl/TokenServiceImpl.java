package com.shopping.sso.service.impl;

import com.shopping.common.jedis.JedisClient;
import com.shopping.common.utils.JsonUtils;
import com.shopping.common.utils.ShoppingResult;
import com.shopping.common.utils.StatusEnum;
import com.shopping.pojo.TbUser;
import com.shopping.sso.service.TokenService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TokenServiceImpl implements TokenService {


    @Autowired
    private JedisClient jedisClient;
    @Value("${SESSION_EXPIRE}")
    private Integer SESSION_EXPIRE;

    @Override
    public ShoppingResult getUserByToken(String token) {
        //根据token到redis中取用户信息
        String json = jedisClient.get("SESSION:" + token);
        //取不到用户信息，登录已经过期，返回登录过期
        if (StringUtils.isBlank(json)) {
            return ShoppingResult.build(StatusEnum.LOGIN_EXPIRE.getCode(),
                    StatusEnum.LOGIN_EXPIRE.getDesc());
        }
        //取到用户信息更新token的过期时间
        jedisClient.expire("SESSION:" + token, SESSION_EXPIRE);
        //返回结果，E3Result其中包含TbUser对象
        TbUser user = JsonUtils.jsonToPojo(json, TbUser.class);
        return ShoppingResult.ok(user);
    }
}
