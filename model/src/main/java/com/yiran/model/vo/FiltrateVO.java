package com.yiran.model.vo;

import com.yiran.model.entity.Brand;
import com.yiran.model.entity.Color;
import com.yiran.model.entity.MultiMenu;
import com.yiran.model.entity.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * @author by LvJunLong
 * @date 2022/10/26
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FiltrateVO {
    private List<MultiMenu> kindList;
    private List<Brand> brandList;
    private List<Color> colorList;
    private List<Size> sizeList;
}
