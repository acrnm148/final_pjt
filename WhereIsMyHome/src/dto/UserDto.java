package dto;

import java.util.Date;

public class UserDto {
	int user_seq;
	String user_name;
	String user_password;
	String user_email;
	String user_profile_image_url;
	Date user_register_date;
	String user_clsf;
	
	public String getUser_clsf() {
		return user_clsf;
	}
	public void setUser_clsf(String user_clsf) {
		this.user_clsf = user_clsf;
	}
	public int getUser_seq() {
		return user_seq;
	}
	public void setUser_seq(int user_seq) {
		this.user_seq = user_seq;
	}
	public String getUser_name() {
		return user_name;
	}
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	public String getUser_password() {
		return user_password;
	}
	public void setUser_password(String user_password) {
		this.user_password = user_password;
	}
	public String getUser_email() {
		return user_email;
	}
	public void setUser_email(String user_email) {
		this.user_email = user_email;
	}
	public String getUser_profile_image_url() {
		return user_profile_image_url;
	}
	public void setUser_profile_image_url(String user_profile_image_url) {
		if( user_profile_image_url == null || "null".equals(user_profile_image_url) || "".equals(user_profile_image_url)) {
            this.user_profile_image_url = "/img/user/noProfile.png";
        }else {
            this.user_profile_image_url = user_profile_image_url;
        }
	}
	public Date getUser_register_date() {
		return user_register_date;
	}
	public void setUser_register_date(Date user_register_date) {
		this.user_register_date = user_register_date;
	}
	
	
	
	
	
	
	
	
}
