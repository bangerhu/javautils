package com.hujiya.test.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * 
 * @author hjy
 * @email sunlightcs@gmail.com
 * @date 2022-03-18 20:27:28
 */
@Data
@TableName("user")
public class UserEntity extends BasePo implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	private String name;

}
