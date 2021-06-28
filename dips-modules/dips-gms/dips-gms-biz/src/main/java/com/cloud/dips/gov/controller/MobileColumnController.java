package com.cloud.dips.gov.controller;

import com.cloud.dips.common.core.util.R;
import com.cloud.dips.common.log.annotation.SysLog;
import com.cloud.dips.gov.service.MobileColumnService;
import com.cloud.dips.gov.service.impl.MobileColumnServiceImpl;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 移动栏目管理
 * @author yinzan
 * @ClassName: MobileColumnController
 * @ProjectName dips
 * @Description: TODO
 * @date 2019/3/11下午3:20
 */
@RestController()
@RequestMapping("/mobile")
@AllArgsConstructor
public class MobileColumnController {

    private  final  MobileColumnService mobileColumnService;

    @PostMapping("/policy")
    @SysLog("移动政策栏目")
    @ApiOperation("移动政策栏目")
    public R<Boolean> mobilepolicy(@RequestParam String ids, @RequestParam String source, @RequestParam String target){
       return new R<>(mobileColumnService.movepolicy(ids, source, target));
    }


}
