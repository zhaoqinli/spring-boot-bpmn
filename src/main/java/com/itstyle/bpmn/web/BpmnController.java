package com.itstyle.bpmn.web;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.itstyle.bpmn.common.config.AbstractController;
import com.itstyle.bpmn.common.entity.Result;
import com.itstyle.bpmn.entity.BpmnModel;
import com.itstyle.bpmn.entity.SysUser;
import com.itstyle.bpmn.service.IBpmnService;

/**
 *  工作流
 */
@RestController
@RequestMapping("/bpmn")
public class BpmnController extends AbstractController {
	
	@Autowired
	private IBpmnService bpmnService;
	
	@RequestMapping(value="list")
	public Result list(@RequestBody Map<String, Object> params,HttpServletRequest request) {
		SysUser user =  this.getUser(request);
		return bpmnService.list(params, user);
    }
	
	@RequestMapping(value="save")
	public Result save(BpmnModel bpmn,HttpServletRequest request) {
		SysUser user =  this.getUser(request);
		return bpmnService.save(bpmn,user);
    }
	
	@RequestMapping(value="get")
	public Result get(Integer id,HttpServletRequest request){
		SysUser user =  this.getUser(request);
		return  bpmnService.get(id, user.getId());
    }
	@RequestMapping(value="remove")
	public Result remove(Integer id,HttpServletRequest request){
		SysUser user =  this.getUser(request);
		return  bpmnService.remove(id, user.getId());
    }

	@RequestMapping(value="cancel")
	public Result cancel(Integer id,HttpServletRequest request){
		SysUser user =  this.getUser(request);
		return  bpmnService.cancel(id, user);
	}
	
	@RequestMapping(value="download")
	public void download(HttpServletResponse response, HttpServletRequest request){
		InputStream is = null;
		try {
			String id = request.getParameter("id");
			BpmnModel bpmn = bpmnService.get(Integer.parseInt(id));
			// 1.设置文件ContentType类型，这样设置，会自动判断下载文件类型
			response.setContentType("multipart/form-data");
			// 2.设置文件头：最后一个参数是设置下载文件名
			response.setHeader("Content-Disposition", "attachment;fileName=" +bpmn.getModelName()+".bpmn");
			is = new ByteArrayInputStream(bpmn.getBpmnXml().getBytes());
			ServletOutputStream output = response.getOutputStream();
			IOUtils.copy(is, output);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (is != null) {
					is.close();
				}
				response.getOutputStream().flush();
				response.getOutputStream().close();
			} catch (IOException e) {
				logger.error("关闭文件IOException!", e);
			}
		}
    }
}
