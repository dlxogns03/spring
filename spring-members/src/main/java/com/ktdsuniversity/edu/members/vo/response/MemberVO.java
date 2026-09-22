package com.ktdsuniversity.edu.members.vo.response;

public class MemberVO {
	
	private String email;
	private String name;
	private String nickName;
	private String password;
	private String registDate;
	private String modifyDate;
	private String latestLoginSuccessDate;
	private String latestLoginFailDate;
	private String latestLogoutDate;
	private int loginFailCount;
	private String loginBlockYn;
	private String loginBlockDate;
	private String loginYn;
	private String salt;
	private String delYn;
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRegistDate() {
		return registDate;
	}
	public void setRegistDate(String registDate) {
		this.registDate = registDate;
	}
	public String getModifyDate() {
		return modifyDate;
	}
	public void setModifyDate(String modifyDate) {
		this.modifyDate = modifyDate;
	}
	public String getLatestLoginSuccessDate() {
		return latestLoginSuccessDate;
	}
	public void setLatestLoginSuccessDate(String latestLoginSuccessDate) {
		this.latestLoginSuccessDate = latestLoginSuccessDate;
	}
	public String getLatestLoginFailDate() {
		return latestLoginFailDate;
	}
	public void setLatestLoginFailDate(String latestLoginFailDate) {
		this.latestLoginFailDate = latestLoginFailDate;
	}
	public String getLatestLogoutDate() {
		return latestLogoutDate;
	}
	public void setLatestLogoutDate(String latestLogoutDate) {
		this.latestLogoutDate = latestLogoutDate;
	}
	public int getLoginFailCount() {
		return loginFailCount;
	}
	public void setLoginFailCount(int loginFailCount) {
		this.loginFailCount = loginFailCount;
	}
	public String getLoginBlockYn() {
		return loginBlockYn;
	}
	public void setLoginBlockYn(String loginBlockYn) {
		this.loginBlockYn = loginBlockYn;
	}
	public String getLoginBlockDate() {
		return loginBlockDate;
	}
	public void setLoginBlockDate(String loginBlockDate) {
		this.loginBlockDate = loginBlockDate;
	}
	public String getLoginYn() {
		return loginYn;
	}
	public void setLoginYn(String loginYn) {
		this.loginYn = loginYn;
	}
	public String getSalt() {
		return salt;
	}
	public void setSalt(String salt) {
		this.salt = salt;
	}
	public String getDelYn() {
		return delYn;
	}
	public void setDelYn(String delYn) {
		this.delYn = delYn;
	}
	@Override
	public String toString() {
		return "MemberVO [email=" + email + ", name=" + name + ", nickName=" + nickName + ", password=" + password
				+ ", registDate=" + registDate + ", modifyDate=" + modifyDate + ", latestLoginSuccessDate="
				+ latestLoginSuccessDate + ", latestLoginFailDate=" + latestLoginFailDate + ", latestLogoutDate="
				+ latestLogoutDate + ", loginFailCount=" + loginFailCount + ", loginBlockYn=" + loginBlockYn
				+ ", loginBlockDate=" + loginBlockDate + ", loginYn=" + loginYn + ", salt=" + salt + ", delYn=" + delYn
				+ "]";
	}
	
	
	

}
