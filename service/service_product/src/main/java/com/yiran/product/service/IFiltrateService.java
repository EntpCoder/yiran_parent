package com.yiran.product.service;

import com.yiran.model.vo.FiltrateVO;

/**
 * @author by LvJunLong
 * @date 2022/10/26
 */
public interface IFiltrateService {
    /**
     * 根据品牌id查询筛选栏
     * @param brandId 品牌id
     * @return 筛选栏
     */
    FiltrateVO getFiltrateByBrandId(String brandId);
}
