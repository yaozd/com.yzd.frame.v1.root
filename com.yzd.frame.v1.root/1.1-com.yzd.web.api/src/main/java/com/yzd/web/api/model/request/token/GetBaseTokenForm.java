package com.yzd.web.api.model.request.token;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * Created by zd.yao on 2018/9/12.
 */
@Data
@NoArgsConstructor
public class GetBaseTokenForm extends BaseTokenForm{
    /***
     * AppId最好是唯一的值UUID或者是设备号
     */
    @NotNull
    private String appId;
    /***
     * AppTypeId类型=web(1),android(2),ios(3)
     */
    @NotNull
    private String appTypeId;
}
