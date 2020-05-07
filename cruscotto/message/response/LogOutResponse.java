package it.cyberSec.cruscotto.message.response;

import java.util.Date;

public class LogOutResponse {
	
    private String email;
    private Date login;
    private Date logout;
    
    
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Date getLogin() {
		return login;
	}
	public void setLogin(Date login) {
		this.login = login;
	}
	public Date getLogout() {
		return logout;
	}
	public void setLogout(Date logout) {
		this.logout = logout;
	}
    
    
    

}
