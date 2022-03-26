package com.hujiya.test.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hujiya.common.utils.PageUtils;
import com.hujiya.common.utils.Query;

import com.hujiya.test.dao.UserDao;
import com.hujiya.test.entity.UserEntity;
import com.hujiya.test.service.UserService;


@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserDao, UserEntity> implements UserService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
            IPage<UserEntity> page = this.page(
                    new Query<UserEntity>().getPage(params),
                    new QueryWrapper<UserEntity>()
            );

            return new PageUtils(page);
    }

}