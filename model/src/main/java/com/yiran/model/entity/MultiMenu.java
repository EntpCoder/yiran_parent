package com.yiran.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 
 * </p>
 *
 * @author Yang Song
 * @since 2022-10-03
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("multi_menu")
public class MultiMenu implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 多级菜单id
     */
    @TableId
    private String menuId;

    /**
     * 菜单标题
     */
    private String title;

    /**
     * 父级菜单id
     */
    private String parentId;
    /**
     * 数据库中没有，属于二级菜单
     */
    @TableField(exist = false)
    private List<MultiMenu> children;
}
