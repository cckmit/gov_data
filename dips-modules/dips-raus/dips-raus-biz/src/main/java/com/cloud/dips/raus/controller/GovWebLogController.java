package com.cloud.dips.raus.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.cloud.dips.common.core.util.Query;
import com.cloud.dips.raus.api.entity.GovWebLog;
import com.cloud.dips.raus.service.GovWebLogService;
import com.cloud.dips.common.core.util.R;

/**
 * 日志表
 *
 * @author johan
 * @date 2018-12-04 16:48:44
 */
@RestController
@RequestMapping("/weblog")
public class GovWebLogController {
    @Autowired
    private GovWebLogService govWebLogService;


    /**
        *  列表
    * @param params
    * @return
    */
    @GetMapping("/page")
    public Page userRecordList(@RequestParam(required = false) Map<String, Object> params) {
    	
      return  govWebLogService.selectPage(new Query<>(params), new EntityWrapper<GovWebLog>());
    }


    /**
          * 信息
     * @param id
     * @return R
     */
    @GetMapping("/{id}")
    public R info(@PathVariable("id") Long id){
			GovWebLog govWebLog = govWebLogService.selectById(id);
			return new R<>(govWebLog);
    }

    /**
          * 保存
     * @param govWebLog
     * @return R
     */
    @PostMapping
    public R save(GovWebLog govWebLog){
    	
			return new R<>(govWebLogService.insert(govWebLog));
    }

    /**
          * 修改
     * @param govWebLog
     * @return R
     */
    @PutMapping
    public R update(@RequestBody GovWebLog govWebLog){
			govWebLogService.updateById(govWebLog);
      return new R<>(Boolean.TRUE);
    }

    /**
          * 删除
     * @param id
     * @return R
     */
    @DeleteMapping("/{id}")
    public R delete(@PathVariable Long id){
      return new R<>(govWebLogService.deleteById(id));
    }


    @RequestMapping("/getip")
    public R gteip(HttpServletRequest request){
		JSONObject allInformation = govWebLogService.getAllInformation(request);
		return new  R<>(allInformation);
	}

}
