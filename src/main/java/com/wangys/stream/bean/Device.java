package com.wangys.stream.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Device
 *
 * @author wangys
 * @date 2020-08-24 17:10
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Device {
    private int id;
    private String code;
    private String name;
}
