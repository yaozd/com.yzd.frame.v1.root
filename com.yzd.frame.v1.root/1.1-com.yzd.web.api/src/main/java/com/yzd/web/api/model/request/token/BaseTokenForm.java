package com.yzd.web.api.model.request.token;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/***
 *
 *
 * @author yzd
 * @date 2018/9/13 10:09.
 */
@Data
@NoArgsConstructor
public class BaseTokenForm {
    @NotNull
    @Pattern(regexp = "[\\d]{13}")
    private String timestamp;
}
