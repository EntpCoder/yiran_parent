package com.yiran.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Yang Song
 * @date 2022/10/2 23:01
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    private String orderId;
    private String subject;
}
