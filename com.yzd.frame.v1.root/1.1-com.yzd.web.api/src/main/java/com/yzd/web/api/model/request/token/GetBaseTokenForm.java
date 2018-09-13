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
    @NotNull
    private String appId;
    @NotNull
    private String appTypeId;
}