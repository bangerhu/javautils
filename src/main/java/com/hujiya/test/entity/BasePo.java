package com.hujiya.test.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

/**
 * @author nicolasli
 * @version 1.0
 * @className BasePo
 * @description TODO
 * @date 2020/3/28 下午3:54
 **/

@Data
public class BasePo implements Serializable {

    public final static String DEFAULT_USERNAME = "system";
    @TableId()
    private String id;

}
