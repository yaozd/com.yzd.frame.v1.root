package com.yzd.web.controllerApi;

import com.yzd.web.model.User;
import com.yzd.web.util.swaggerExt.ApiDataType;
import com.yzd.web.util.swaggerExt.ApiParamType;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
//不要使用api中的tags的标签，因为使用了相同的标签就会把内容进行合并
//@Api(tags = "UserController", description = "用户管理", value = "用户管理",hidden = true)
@Api(description = "用户管理", value = "用户管理",hidden = true)
public class UserControllerAPI {

    private static final Logger log = LoggerFactory.getLogger(UserControllerAPI.class);

    @GetMapping
    @ApiOperation(value = "条件查询（DONE）")
    //hidden = true 隐藏当前方法
    //@ApiOperation(value = "条件查询（DONE）",hidden = true)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "username", value = "用户名", dataType = ApiDataType.STRING, paramType = ApiParamType.QUERY),
            @ApiImplicitParam(name = "password", value = "密码", dataType = ApiDataType.STRING, paramType = ApiParamType.QUERY),
    })
    public User query(String username, String password) {
        log.info("多个参数用  @ApiImplicitParams");
        return new User(1L, username, password);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "主键查询（DONE）")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户编号", dataType = ApiDataType.LONG, paramType = ApiParamType.PATH),
    })
    public User get(@PathVariable Long id) {
        log.info("单个参数用  @ApiImplicitParam");
        return new User(id, "u1", "p1");
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "删除用户（DONE）")
    @ApiImplicitParam(name = "id", value = "用户编号", dataType = ApiDataType.LONG, paramType = ApiParamType.PATH,defaultValue = "1")
    public void delete(@PathVariable Long id) {
        log.info("单个参数用 ApiImplicitParam");
    }

    @PostMapping
    @ApiOperation(value = "添加用户（DONE）")
    public User post(@RequestBody User user) {
        log.info("如果是 POST PUT 这种带 @RequestBody 的可以不用写 @ApiImplicitParam");
        return user;
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "修改用户（DONE）")
    public void put(@PathVariable Long id, @RequestBody User user) {
        log.info("如果你不想写 @ApiImplicitParam 那么 swagger 也会使用默认的参数名作为描述信息 ");
    }


}
