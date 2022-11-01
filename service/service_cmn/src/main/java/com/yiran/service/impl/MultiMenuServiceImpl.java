package com.yiran.service.impl;

import com.yiran.mapper.MultiMenuMapper;
import com.yiran.model.entity.MultiMenu;
import com.yiran.service.MultiMenuService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
    public List<MultiMenu> listWithTree() {
        //1、查出所有分类
        List<MultiMenu> multiMenuList = menuMapper.selectList(null);
        //2、组装成父子分类
        return multiMenuList.stream()
                .filter(multiMenu ->
                        multiMenu.getParentId() != null)
                .peek(menu -> menu.setChildren(getChildrens(menu,multiMenuList)))
                .collect(Collectors.toList());
    }
    /**
     * 递归查找所有菜单的子菜单
     */
    private List<MultiMenu> getChildrens(MultiMenu root,List<MultiMenu> all){
        return all.stream().filter(multiMenu ->{
             return multiMenu.getParentId().equals(root.getMenuId()) ;
         }).collect(Collectors.toList());
    }
}
