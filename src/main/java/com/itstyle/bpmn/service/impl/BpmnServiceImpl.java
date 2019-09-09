package com.itstyle.bpmn.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.itstyle.bpmn.common.util.DateUtils;
import com.itstyle.bpmn.repository.BpmnRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itstyle.bpmn.common.dynamicquery.DynamicQuery;
import com.itstyle.bpmn.common.entity.PageBean;
import com.itstyle.bpmn.common.entity.Result;
import com.itstyle.bpmn.common.util.CommonUtils;
import com.itstyle.bpmn.entity.BpmnModel;
import com.itstyle.bpmn.entity.SysUser;
import com.itstyle.bpmn.service.IBpmnService;

@Service("bpmnService")
public class BpmnServiceImpl implements IBpmnService {

	@Autowired
	private DynamicQuery dynamicQuery;
	@Autowired
	private BpmnRepository bpmnRepository;
	
	@Override
	public Result list(Map<String, Object> params, SysUser user) {
		StringBuffer nativeCount = new StringBuffer("SELECT count(*) FROM bpmn_model WHERE user_create=?");
		Long count =  dynamicQuery.nativeQueryCount(nativeCount.toString(),new Object[] {user.getId()});
		List<BpmnModel> bpmnList = new ArrayList<>();
		if(count>0) {
			Integer pageNo = Integer.parseInt(params.get("pageNo").toString());
			Integer pageSize = Integer.parseInt(params.get("pageSize").toString());
			StringBuffer nativeSql = new StringBuffer("SELECT * FROM bpmn_model WHERE user_create=? order by gmt_create");
			Pageable pageable = new PageRequest(pageNo-1,pageSize);
			bpmnList =dynamicQuery.nativeQueryPagingList(BpmnModel.class, pageable, nativeSql.toString(), new Object[] {user.getId()} );
		}
		PageBean<BpmnModel> result = new PageBean<>(bpmnList, count);
		return  CommonUtils.msg(result);
	}

	@Override
	public Result get(Integer id, Integer userId) {
		String nativeSql = "SELECT * FROM bpmn_model WHERE user_create=? AND id=?";
		BpmnModel model = dynamicQuery.nativeQuerySingleResult(BpmnModel.class, nativeSql, new Object[] {userId,id});
		return  CommonUtils.msg(model);
	}

	@Override
	@Transactional
	public Result remove(Integer id, Integer userId) {
		String nativeSql = "DELETE FROM bpmn_model WHERE user_create=? AND id=?";
		int count = dynamicQuery.nativeExecuteUpdate(nativeSql, new Object[] {userId,id});
		return  CommonUtils.msg(count);
	}

	@Override
	public BpmnModel get(Integer id) {
		String nativeSql = "SELECT * FROM bpmn_model WHERE id=?";
		BpmnModel model = dynamicQuery.nativeQuerySingleResult(BpmnModel.class, nativeSql, new Object[] {id});
		return model;
	}

	@Override
	public Result save(BpmnModel bpmn, SysUser user) {
		if(bpmn.getId() == null){
			bpmn.setUserCreate(user.getId());
			bpmn.setUsername(user.getUsername());
			bpmn.setGmtCreate(DateUtils.getTimestamp());
			bpmn.setGmtUpdate(DateUtils.getTimestamp());
			bpmn.setDeployStatus(0);
			bpmn.setModelKey(bpmn.getModelName());
			bpmnRepository.saveAndFlush(bpmn);
		}else {
			BpmnModel bpmnModel = this.get(bpmn.getId());
			bpmnModel.setBpmnXml(bpmn.getBpmnXml());
			bpmnModel.setBpmnImage(bpmn.getBpmnImage());
			bpmnModel.setUserIdUpdate(user.getId());
			bpmnModel.setUsernameUpdate(user.getUsername());
			bpmnModel.setGmtUpdate(DateUtils.getTimestamp());
			bpmnModel.setDeployStatus(0);
			bpmnModel.setModelKey(bpmn.getModelName());
			bpmnRepository.saveAndFlush(bpmnModel);
		}

		return Result.ok(bpmn.getId());
	}

	@Override
	public void deploy(BpmnModel bpmn, SysUser user) {
		bpmn.setUserIdDeploy(user.getId());
		bpmn.setUsernameDeploy(user.getUsername());
		bpmn.setGmtDeploy(DateUtils.getTimestamp());
		bpmn.setDeployStatus(1);
		bpmnRepository.saveAndFlush(bpmn);
	}

	@Override
	public Result cancel(Integer id, SysUser user) {
		BpmnModel bpmnModel = this.get(id);
		bpmnModel.setUserIdUpdate(user.getId());
		bpmnModel.setUsernameUpdate(user.getUsername());
		bpmnModel.setGmtUpdate(DateUtils.getTimestamp());
		bpmnModel.setDeployStatus(7);
		bpmnRepository.saveAndFlush(bpmnModel);
		return CommonUtils.msg(id);
	}
}
