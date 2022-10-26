package com.yiran.model.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author by LvJunLong
 * @date 2022/10/26
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FiltrateVO {
    private List<String> kindList;
    private List<String> brandList;
    private List<String> colorList;
    private List<String> sizeList;
}
