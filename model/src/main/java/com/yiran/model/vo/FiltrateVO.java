package com.yiran.model.vo;

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
    private Map<String,String> kindMap;
    private Map<String,String> brandMap;
    private Map<String,String> colorMap;
    private Map<String,String> sizeMap;
}
