package com.e9cloud.mybatis.domain;

/**
 * 用户账户信息
 */
public class Account extends User {

  private static final long serialVersionUID = -5871590343444875970L;

  /** 用户密码 */
  private String pwd;

  /** 私盐 */
  private String salt;

  // 邮箱验证码
  private String vcode;

  public String getVcode() {
    return vcode;
  }

  public void setVcode(String vcode) {
    this.vcode = vcode;
  }

  public String getPwd() {
    return pwd;
  }

  public void setPwd(String pwd) {
    this.pwd = pwd == null ? null : pwd.trim();
  }

  public String getSalt() {
    return salt;
  }

  public void setSalt(String salt) {
    this.salt = salt == null ? null : salt.trim();
  }
}
