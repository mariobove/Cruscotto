package it.cyberSec.cruscotto.security.services;

import java.io.IOException;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



public interface AppService {
	
	public String sendmail(String mailUser,String ruolo) throws AddressException, MessagingException, IOException;
	
//	public void exportPresenzeUtente(Long idUtente, HttpServletRequest  request, HttpServletResponse  response) throws IOException;

}
