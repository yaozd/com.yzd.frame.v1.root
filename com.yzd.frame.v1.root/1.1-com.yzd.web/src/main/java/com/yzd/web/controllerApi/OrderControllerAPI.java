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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/order")
@Api(description = "订单管理")
public class OrderControllerAPI {
    private static final Logger log = LoggerFactory.getLogger(OrderControllerAPI.class);
    @GetMapping("getOrder/{id}")
    @ApiOperation(value = "查询订单（DONE）")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "订单编号", dataType = ApiDataType.LONG, paramType = ApiParamType.PATH),
    })
    public String getOrder(@PathVariable Long id) {
        log.info("单个参数用  @ApiImplicitParam");
        return "getOrder:"+id;
    }
    @GetMapping("getPrice")
    @ApiOperation(value = "模拟异常情况-查询订单价格（DONE）")
    public String getPrice(){
        mockThrow();
        return "1.1";
    }

    private void mockThrow() {
        int a=1;
        int b=0;
        int c=a/b;
    }
}
