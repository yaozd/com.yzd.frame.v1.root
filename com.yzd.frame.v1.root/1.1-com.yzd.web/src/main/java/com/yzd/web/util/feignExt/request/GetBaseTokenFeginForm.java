package com.yzd.web.util.feignExt.request;

import lombok.Data;
import lombok.NoArgsConstructor;

/***
 *
 *
 * @author yzd
 * @date 2018/9/14 13:52.
 */

@Data
@NoArgsConstructor
public class GetBaseTokenFeginForm{
    private String appId;
    private String appTypeId;
    private String timestamp;
}