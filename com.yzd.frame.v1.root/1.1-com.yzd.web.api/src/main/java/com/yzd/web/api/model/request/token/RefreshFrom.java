package com.yzd.web.api.model.request.token;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * Created by zd.yao on 2018/9/12.
 */
@Data
@NoArgsConstructor
public class RefreshFrom {
    @NotBlank
    private String appId;
    @NotBlank
    private String appTypeId;
    @NotBlank
    private String refreshToken;
    @NotNull
    @Pattern(regexp = "[\\d]{13}")
    private String timestamp;
}