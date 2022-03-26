package com.hujiya.test.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.hujiya.common.utils.PageUtils;
import com.hujiya.test.entity.OrderEntity;

import java.util.Map;

/**
 * 
 *
 * @author hjy
 * @email sunlightcs@gmail.com
 * @date 2022-03-18 20:27:28
 */
public interface OrderService extends IService<OrderEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

