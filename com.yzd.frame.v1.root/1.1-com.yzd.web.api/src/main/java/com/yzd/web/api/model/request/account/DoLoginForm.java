package com.yzd.web.api.model.request.account;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

/***
 *
 *
 * @author yzd
 * @date 2018/9/13 14:21.
 */
@Data
@NoArgsConstructor
public class DoLoginForm {
    @NotBlank
    private String name;
    @NotBlank
    private String passworld;

}
