package com.yzd.web.util.feignExt.request;

import lombok.Data;
import lombok.NoArgsConstructor;

/***
 *
 *
 * @author yzd
 * @date 2018/9/14 13:50.
 */
@Data
@NoArgsConstructor
public class DoLoginFeginForm {
    private String name;
    private String passworld;
}