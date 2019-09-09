package com.itstyle.bpmn.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itstyle.bpmn.common.entity.DeploymentResponse;
import com.itstyle.bpmn.common.entity.PageBean;
import com.itstyle.bpmn.common.entity.Result;
import com.itstyle.bpmn.common.util.CommonUtils;
import com.itstyle.bpmn.entity.SysUser;
import com.itstyle.bpmn.service.IBpmnService;
import com.itstyle.bpmn.service.IModelerService;
import org.activiti.bpmn.converter.BpmnXMLConverter;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.editor.language.json.converter.BpmnJsonConverter;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.impl.persistence.entity.ModelEntity;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentQuery;
import org.activiti.engine.repository.Model;
import org.activiti.engine.repository.ModelQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service("modelerService")
public class IModelerServiceImpl implements IModelerService {

    @Autowired
    RepositoryService repositoryService;
    @Autowired
    IBpmnService bpmnService;

   /* @Override
    public Result flowList(Map<String, Object> params) {
        ModelQuery query = repositoryService.createModelQuery();
        long count = query.count();
        List<Model> flowList = new ArrayList<>();
        if(count > 0){
            Integer pageNo = Integer.parseInt(params.get("pageNo").toString());
            Integer pageSize = Integer.parseInt(params.get("pageSize").toString());
            flowList = query.listPage(pageNo, pageSize);
        }
        PageBean<Model> result = new PageBean<>(flowList, count);
        return  CommonUtils.msg(result);
    }*/

    @Override
    public Result deployList(Map<String, Object> params) {

        DeploymentQuery query = repositoryService.createDeploymentQuery();
        long count = query.count();
        List<DeploymentResponse> deployList = new ArrayList<>();
        if(count > 0){
            Integer pageNo = Integer.parseInt(params.get("pageNo").toString());
            Integer pageSize = Integer.parseInt(params.get("pageSize").toString());
            int start =  (pageNo-1) * pageSize;
            List<Deployment> list = query.listPage(start, pageSize);
            for (Deployment deployment : list){
                deployList.add(new DeploymentResponse(deployment));
            }
        }
        PageBean<DeploymentResponse> result = new PageBean<>(deployList, count);
        return  CommonUtils.msg(result);
    }

    @Override
    public Result deploy(Integer id, SysUser user) throws Exception{
        com.itstyle.bpmn.entity.BpmnModel bpmnModel = bpmnService.get(id);
        if (null == bpmnModel){
            return CommonUtils.errorMsg("模型数据为空，请先设计流程并成功保存，再进行发布。");
        }
        String bpmnXml = bpmnModel.getBpmnXml();
        String modelName = bpmnModel.getModelName();
        if (null == bpmnXml){
            return CommonUtils.errorMsg("模型数据为空，请先设计流程并成功保存，再进行发布。");
        }
      /*  byte[] bytes = bpmnXml.getBytes();
        //获取模型
        *//*Model modelData = repositoryService.getModel(id);
        byte[] bytes = repositoryService.getModelEditorSource(modelData.getId());*//*
        if (null == bytes){
            return CommonUtils.errorMsg("模型数据为空，请先设计流程并成功保存，再进行发布。");
        }
        JsonNode modelNode = new ObjectMapper().readTree(bytes);
        BpmnModel model = new BpmnJsonConverter().convertToBpmnModel(modelNode);
        if (model.getProcesses().size() == 0){
            return CommonUtils.errorMsg("数据模型不符合要求，请至少设计一条主线程流。");
        }
        byte[] bpmnBytes = new BpmnXMLConverter().convertToXML(model);*/

        //发布流程
        String processName = modelName + ".bpmn20.xml";
        Deployment deployment = repositoryService.createDeployment()
                .name(modelName)
                //.addString(processName, new String(bpmnBytes, "UTF-8"))
                .addString(processName, bpmnXml)
                .deploy();
        /*Model modelData = new ModelEntity();
        modelData.setDeploymentId(deployment.getId());
        repositoryService.saveModel(modelData);*/
        bpmnService.deploy(bpmnModel,user);
        return CommonUtils.okMsg("发布成功");
    }

    @Override
    public Result deleteDeploy(String id, SysUser user) {
        repositoryService.deleteDeployment(id);
        return CommonUtils.okMsg("删除成功");
    }
}
