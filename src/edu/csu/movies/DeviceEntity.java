package edu.csu.movies;

public class DeviceEntity {
	
	private String device_id;
	private String device_name;///////////////////
	private String device_type;//�豸����////////////////0：平板；1：手机
	private String device_mac;//MAC��///////////ַ
	private String device_token;//�豸��ʶ��
	private String device_online;//0:�����///////1
	private String device_login_time;////////
	private String device_logout_time;
	public String getDevice_id() {
		return device_id;
	}
	public void setDevice_id(String device_id) {
		this.device_id = device_id;
	}
	public String getDevice_name() {
		return device_name;
	}
	public void setDevice_name(String device_name) {
		this.device_name = device_name;
	}
	public String getDevice_type() {
		return device_type;
	}
	public void setDevice_type(String device_type) {
		this.device_type = device_type;
	}
	public String getDevice_mac() {
		return device_mac;
	}
	public void setDevice_mac(String device_mac) {
		this.device_mac = device_mac;
	}
	public String getDevice_token() {
		return device_token;
	}
	public void setDevice_token(String device_token) {
		this.device_token = device_token;
	}
	public String getDevice_online() {
		return device_online;
	}
	public void setDevice_online(String device_online) {
		this.device_online = device_online;
	}
	public String getDevice_login_time() {
		return device_login_time;
	}
	public void setDevice_login_time(String device_login_time) {
		this.device_login_time = device_login_time;
	}
	public String getDevice_logout_time() {
		return device_logout_time;
	}
	public void setDevice_logout_time(String device_logout_time) {
		this.device_logout_time = device_logout_time;
	}
	
	
}
