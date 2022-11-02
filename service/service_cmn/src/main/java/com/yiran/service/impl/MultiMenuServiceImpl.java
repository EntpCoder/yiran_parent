package com.yiran.service.impl;

import com.yiran.mapper.MultiMenuMapper;
import com.yiran.model.entity.MultiMenu;
import com.yiran.service.MultiMenuService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


/**
 * @Author 小番茄
 * @Date 2022/11/1 16:08
 */
@Service
public class MultiMenuServiceImpl implements MultiMenuService {
    final MultiMenuMapper menuMapper;

    public MultiMenuServiceImpl(MultiMenuMapper menuMapper) {
        this.menuMapper = menuMapper;
    }

    @Override
    @Cacheable(value = "menu")
    public List<MultiMenu> listWithTree() {
        //1、查出所有分类
        List<MultiMenu> multiMenuList = menuMapper.selectList(null);
        //2、组装成父子分类
        List<MultiMenu> level1Menus = new ArrayList<>();
        for (MultiMenu menu: multiMenuList) {
            if ("0".equals(menu.getParentId())){
                level1Menus.add(menu);
            }
        }

        for (MultiMenu level1Menu : level1Menus) {
            level1Menu.setChildren(getChildrens(level1Menu,multiMenuList));
        }
        return level1Menus;
    }
    /**
     * 递归查找所有菜单的子菜单
     */
    private List<MultiMenu> getChildrens(MultiMenu root,List<MultiMenu> all){
        List<MultiMenu> children = new ArrayList<>();
        for (MultiMenu m: all) {
            if (m.getParentId().equals(root.getMenuId())){
                m.setChildren(getChildrens(m,all));
                children.add(m);
            }
        }
        return children;
    }
}
