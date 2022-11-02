package com.yiran.controller;

import com.yiran.common.result.R;
import com.yiran.model.entity.MultiMenu;
import com.yiran.service.MultiMenuService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @Author 小番茄
 * @Date 2022/11/1 16:09
 */
@RestController
@RequestMapping("/MultiMenu")
public class MultiMenuController {
    private final MultiMenuService menuService;
    public MultiMenuController(MultiMenuService menuService) {
        this.menuService = menuService;
    }

    /**
     * 根据集合来查询多级菜单
     * @return 多级菜单集合
     */
    @RequestMapping("/list/tree")
    public R list(){
        List<MultiMenu> multiMenus =  menuService.listWithTree();
        return R.ok("multiMenus",multiMenus);
    }
}
