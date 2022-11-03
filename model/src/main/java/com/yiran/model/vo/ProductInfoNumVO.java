package com.yiran.model.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @author 庆瑞瑞
 * @date 2022/11/3 9:11
 */
@Data
public class ProductInfoNumVO {
    /**
     * 商品信息id
     */
    private String proAttributeInfoId;
    /**
     * 库存数量
     */
    private Integer nums;
}
