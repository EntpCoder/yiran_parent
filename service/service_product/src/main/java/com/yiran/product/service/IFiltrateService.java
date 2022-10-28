package com.yiran.product.service;

import com.yiran.model.vo.FiltrateVO;

/**
 * @author by LvJunLong
 * @date 2022/10/26
 */
public interface IFiltrateService {
    /**
     * 查询筛选栏栏
     * @param brandId 品牌id
     * @param kindId 品类id
     * @return 筛选栏
     */
    FiltrateVO getFiltrate(String brandId,String kindId);

}
