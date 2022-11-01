package com.yiran.service;

import com.yiran.model.entity.MultiMenu;

import java.util.List;

/**
 * @Author 小番茄
 * @Date 2022/11/1 16:08
 */
public interface MultiMenuService {
    /**
     * 根据集合来查询多级菜单
     * @return 返回多级菜单
     */
    List<MultiMenu> listWithTree();
}
