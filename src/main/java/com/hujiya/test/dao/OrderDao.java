package com.hujiya.test.dao;

import com.hujiya.test.entity.OrderEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 
 * 
 * @author hjy
 * @email sunlightcs@gmail.com
 * @date 2022-03-18 20:27:28
 */
@Mapper
public interface OrderDao extends BaseMapper<OrderEntity> {
	
}
