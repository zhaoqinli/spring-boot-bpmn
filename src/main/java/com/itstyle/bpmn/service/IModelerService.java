package com.itstyle.bpmn.service;

import com.itstyle.bpmn.common.entity.Result;
import com.itstyle.bpmn.entity.SysUser;

import java.util.Map;

public interface IModelerService {

    //Result flowList(Map<String, Object> params);

    Result deployList(Map<String, Object> params);

    Result deploy(Integer id, SysUser user)throws Exception;

    Result deleteDeploy(String id, SysUser user);

}
