package com.yzd.web.model;

import lombok.Data;
import lombok.NoArgsConstructor;

/***
 *
 *
 * @author yzd
 * @date 2018/9/14 14:40.
 */

@Data
@NoArgsConstructor
public class DoLoginForm {
    private String name;
    private String passworld;
}