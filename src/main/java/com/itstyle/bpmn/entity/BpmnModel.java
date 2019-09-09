package com.itstyle.bpmn.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "bpmn_model")
@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler"})
public class BpmnModel {
	
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;

	@Column(name="model_key")
	private String modelKey;

	@Column(name="model_name")
	private String modelName;

	@Lob
	@Column(name="bpmn_xml",columnDefinition="TEXT")
	private String bpmnXml;

	@Lob
	@Column(name="bpmn_imgae",columnDefinition="TEXT")
	private String bpmnImage;

	@Column(name="deploy_status")
	private Integer deployStatus;

	@Column(name="deploy_id")
	private String deployId;

	@Column(name="gmt_create")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Timestamp gmtCreate;

	@Column(name="gmt_update")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Timestamp gmtUpdate;

	@Column(name="gmt_deploy")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Timestamp gmtDeploy;

	@Column(name="user_create")
	private Integer userCreate;

	@Column(name="username")
	private String username;

	@Column(name="user_id_update")
	private Integer userIdUpdate;

	@Column(name="username_update")
	private String usernameUpdate;

	@Column(name="user_id_deploy")
	private Integer userIdDeploy;

	@Column(name="username_deploy")
	private String usernameDeploy;


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getModelKey() {
		return modelKey;
	}

	public void setModelKey(String modelKey) {
		this.modelKey = modelKey;
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public String getBpmnXml() {
		return bpmnXml;
	}

	public void setBpmnXml(String bpmnXml) {
		this.bpmnXml = bpmnXml;
	}

	public String getBpmnImage() {
		return bpmnImage;
	}

	public void setBpmnImage(String bpmnImage) {
		this.bpmnImage = bpmnImage;
	}

	public Integer getDeployStatus() {
		return deployStatus;
	}

	public void setDeployStatus(Integer deployStatus) {
		this.deployStatus = deployStatus;
	}

	public String getDeployId() {
		return deployId;
	}

	public void setDeployId(String deployId) {
		this.deployId = deployId;
	}

	public Timestamp getGmtCreate() {
		return gmtCreate;
	}

	public void setGmtCreate(Timestamp gmtCreate) {
		this.gmtCreate = gmtCreate;
	}

	public Timestamp getGmtUpdate() {
		return gmtUpdate;
	}

	public void setGmtUpdate(Timestamp gmtUpdate) {
		this.gmtUpdate = gmtUpdate;
	}

	public Timestamp getGmtDeploy() {
		return gmtDeploy;
	}

	public void setGmtDeploy(Timestamp gmtDeploy) {
		this.gmtDeploy = gmtDeploy;
	}

	public Integer getUserCreate() {
		return userCreate;
	}

	public void setUserCreate(Integer userCreate) {
		this.userCreate = userCreate;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Integer getUserIdUpdate() {
		return userIdUpdate;
	}

	public void setUserIdUpdate(Integer userIdUpdate) {
		this.userIdUpdate = userIdUpdate;
	}

	public String getUsernameUpdate() {
		return usernameUpdate;
	}

	public void setUsernameUpdate(String usernameUpdate) {
		this.usernameUpdate = usernameUpdate;
	}

	public Integer getUserIdDeploy() {
		return userIdDeploy;
	}

	public void setUserIdDeploy(Integer userIdDeploy) {
		this.userIdDeploy = userIdDeploy;
	}

	public String getUsernameDeploy() {
		return usernameDeploy;
	}

	public void setUsernameDeploy(String usernameDeploy) {
		this.usernameDeploy = usernameDeploy;
	}
}
