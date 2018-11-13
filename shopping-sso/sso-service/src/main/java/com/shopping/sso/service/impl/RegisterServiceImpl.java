package com.shopping.sso.service.impl;

import com.shopping.common.utils.ShoppingResult;
import com.shopping.common.utils.StatusEnum;
import com.shopping.mapper.TbUserMapper;
import com.shopping.pojo.TbUser;
import com.shopping.pojo.TbUserExample;
import com.shopping.sso.service.RegisterService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.List;
@Service
public class RegisterServiceImpl implements RegisterService {

    @Autowired
    private TbUserMapper userMapper;


    @Override
    public ShoppingResult checkData(String param, int type) {
        TbUserExample example = new TbUserExample();
        TbUserExample.Criteria criteria = example.createCriteria();
        //1用户名 2手机号 3邮箱
        if (type==1){
            criteria.andUsernameEqualTo(param);
        }else if (type==2){
            criteria.andPhoneEqualTo(param);
        }else if (type==3){
            criteria.andEmailEqualTo(param);
        }else {
            return ShoppingResult.build(StatusEnum.REGISTER_TYPE_ERROR.getCode()
                    ,StatusEnum.REGISTER_TYPE_ERROR.getDesc());
        }
        List<TbUser> tbUsers = userMapper.selectByExample(example);
        if (null!=tbUsers && tbUsers.size()>0){
            return ShoppingResult.ok(false);
        }
        return ShoppingResult.ok(true);
    }

    @Override
    public ShoppingResult register(TbUser user) {
        //数据有效性校验
        if (StringUtils.isBlank(user.getUsername()) || StringUtils.isBlank(user.getPassword())
                || StringUtils.isBlank(user.getPhone())) {
            return ShoppingResult.build(StatusEnum.REGISTER_ERROR_INCOMPLETE.getCode()
                    , StatusEnum.REGISTER_ERROR_INCOMPLETE.getDesc());
        }
        //1：用户名 2：手机号 3：邮箱
        ShoppingResult result = checkData(user.getUsername(), 1);
        if (!(boolean) result.getData()) {
            return ShoppingResult.build(StatusEnum.REGISTER_ERROR_USERNAME.getCode()
                    , StatusEnum.REGISTER_ERROR_USERNAME.getDesc());
        }
        result = checkData(user.getPhone(), 2);
        if (!(boolean)result.getData()) {
            return ShoppingResult.build(StatusEnum.REGISTER_ERROR_PHONE.getCode()
                    , StatusEnum.REGISTER_ERROR_PHONE.getDesc());
        }
        //补全pojo的属性
        user.setCreated(new Date());
        user.setUpdated(new Date());
        //对密码进行md5加密
        String md5Pass = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
        user.setPassword(md5Pass);
        //把用户数据插入到数据库中
        userMapper.insert(user);
        //返回添加成功
        return ShoppingResult.ok();
    }
}
