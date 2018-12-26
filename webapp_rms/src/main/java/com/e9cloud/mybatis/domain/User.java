package com.e9cloud.mybatis.domain;

import com.google.common.collect.Lists;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 人员相关信息
 * 
 * @author wzj
 *
 */
public class User implements Serializable {

	private static final long serialVersionUID = -8013136901960560354L;

	private Integer id;
	private String username;
	private String password;
	private String salt;
	private String nick;
	private String sex;
	private String phone;
	private String email;

	// 用户状态
	private String status;

	private Date createTime;

	// 用户所在部门 Id
	private String departmentId;

	// 用户所在部门
	private Department department;

	//平台权限
	private String sysType;

	//角色名
	private String roleName;

	private List<Role> roles = Lists.newArrayList();

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username == null ? null : username.trim();
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password == null ? null : password.trim();
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt == null ? null : salt.trim();
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick == null ? null : nick.trim();
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex == null ? null : sex.trim();
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email == null ? null : email.trim();
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status == null ? null : status.trim();
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	public String getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(String departmentId) {
		this.departmentId = departmentId;
	}

	public String getSysType() { return sysType; }

	public void setSysType(String sysType) { this.sysType = sysType; }

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleName() {

		return roleName;
	}
}