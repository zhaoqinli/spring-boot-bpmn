package com.itstyle.bpmn.web;

import com.itstyle.bpmn.common.config.AbstractController;
import com.itstyle.bpmn.common.entity.Result;
import com.itstyle.bpmn.entity.SysUser;
import com.itstyle.bpmn.service.IModelerService;
import org.activiti.engine.RepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 发布模块
 */
@RestController
@RequestMapping("models")
public class ModelerController extends AbstractController {

    @Autowired
    RepositoryService repositoryService;
    @Autowired
    private IModelerService modelerService;


    @RequestMapping(value = "/deployList")
    public Result deployList(@RequestBody Map<String, Object> params, HttpServletRequest request){
        return modelerService.deployList(params);
    }


    @ResponseBody
    @RequestMapping(value = "/deploy")
    public Result deploy(@RequestParam(name = "id") Integer id,HttpServletRequest request) throws Exception{
        SysUser user =  this.getUser(request);
        return modelerService.deploy(id,user);
    }


    @ResponseBody
    @RequestMapping(value = "/deleteDeploy")
    public Result deleteDeploy(@RequestParam(name = "id") String id,HttpServletRequest request){
        SysUser user =  this.getUser(request);
        return modelerService.deleteDeploy(id,user);
    }
}
