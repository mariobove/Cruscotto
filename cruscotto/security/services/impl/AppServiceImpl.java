package it.cyberSec.cruscotto.security.services.impl;

import java.io.IOException;
import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.opencsv.CSVWriter;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

import it.cyberSec.cruscotto.message.response.ListaPresenze;
import it.cyberSec.cruscotto.model.ConfGen;
import it.cyberSec.cruscotto.model.User;
import it.cyberSec.cruscotto.repository.AppRepository;
import it.cyberSec.cruscotto.repository.UserRepository;
import it.cyberSec.cruscotto.security.services.AppService;
import it.cyberSec.cruscotto.security.services.UserService;

@Service("appServiceImpl")
public class AppServiceImpl implements AppService{
	
	@Autowired
	AppRepository appRepository;
	@Autowired
	UserRepository userRepository;
	@Autowired
	UserService userService;

	@Override
	public String sendmail(String mailUser,String ruolo) throws AddressException, MessagingException, IOException{
		
		   
		   Properties props = new Properties();
		   for(ConfGen mail:appRepository.findAll()) {
			   
		   props.put("mail.smtp.auth",(mail.getChiave().equalsIgnoreCase("mail.smtp.auth"))?mail.getValore():"");
		   props.put("mail.smtp.starttls.enable", (mail.getChiave().equalsIgnoreCase("mail.smtp.starttls.enable"))?mail.getValore():"");
		   props.put("mail.smtp.host", (mail.getChiave().equalsIgnoreCase("mail.smtp.host"))?mail.getValore():"");
		   props.put("mail.smtp.port", (mail.getChiave().equalsIgnoreCase("mail.smtp.port"))?mail.getValore():"");
		   props.put("mail", (mail.getChiave().equalsIgnoreCase("mail"))?mail.getValore():"");
		   props.put("password", (mail.getChiave().equalsIgnoreCase("password"))?mail.getValore():"");
		   
		   }
		   
		  
		   
		   Session session = Session.getInstance(props, new javax.mail.Authenticator() {
		      protected PasswordAuthentication getPasswordAuthentication() {
		         return new PasswordAuthentication( props.getProperty("mail"),  props.getProperty("password"));
		      }
		   });
		   Message msg = new MimeMessage(session);
		   msg.setFrom(new InternetAddress(props.getProperty("mail"), false));

		   msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mailUser));
		   msg.setSubject("CyberAcademy email");
		   msg.setContent("CyberAcademy email Utente Registrato", "text/html");
		   msg.setSentDate(new Date());

		   MimeBodyPart messageBodyPart = new MimeBodyPart();
		   messageBodyPart.setContent("CyberAcademy Utente Registrato con ruolo "+ruolo, "text/html");

		   Multipart multipart = new MimeMultipart();
		   multipart.addBodyPart(messageBodyPart);
		  // MimeBodyPart attachPart = new MimeBodyPart();

		  // attachPart.attachFile("/var/tmp/image19.png");
		//   multipart.addBodyPart(attachPart);
		   
		   msg.setContent(multipart);
		   Transport.send(msg);   
		   
		   return "Email sent successfully";
	}

	/*
	@Override
	public void exportPresenzeUtente(Long idUtente, HttpServletRequest request, HttpServletResponse response) throws IOException {
		
		User user = userRepository.findById(idUtente).orElseThrow(
				() -> new UsernameNotFoundException("User Not Found with -> idUser : " + idUtente ));
		
		
        //set file name and content type
        String filename = "ListaPresenze.csv";

        response.setContentType("text/csv");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + filename + "\"");

        //create a csv writer
        StatefulBeanToCsv<ListaPresenze> writer = new StatefulBeanToCsvBuilder<ListaPresenze>(response.getWriter())
                .withQuotechar(CSVWriter.NO_QUOTE_CHARACTER)
                .withSeparator(CSVWriter.DEFAULT_SEPARATOR)
                .withOrderedResults(false)
                .build();

        //write all users to csv file
        try {
			writer.write(userService.listaLoginUser(user.getUsername(), user.getPassword()));
		} catch (CsvDataTypeMismatchException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CsvRequiredFieldEmptyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
    }
		
	
*/
	


}
