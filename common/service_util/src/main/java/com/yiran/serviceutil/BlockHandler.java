package com.yiran.serviceutil;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.yiran.common.result.R;
import com.yiran.common.result.ResultCodeEnum;

/**
 * @author Yang Song
 * @date 2022/10/14 13:54
 * 限流后的兜底数据
 */
public class BlockHandler {
    public static R<String> soHot(String userId,String proId,BlockException exception){
      return R.fail(ResultCodeEnum.SO_HOT);
    }
}
