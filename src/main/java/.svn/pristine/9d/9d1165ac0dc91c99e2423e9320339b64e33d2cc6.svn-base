package com.divel.online.apps;

import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.ServletResponse;
import javax.servlet.http.*;
import javax.servlet.*;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import javax.mail.*;
import javax.mail.internet.*;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;



import java.io.*;

import java.util.Properties;

public class Divel_KeyServlet extends HttpServlet {
	
	public String DivelKey1(String email){
		char[] chars = email.toCharArray();
		StringBuffer strBuffer = new StringBuffer();
		for (int i = 0; i < chars.length; i++) {
		   strBuffer.append(Integer.toHexString((int) chars[i]));
		}
		String key= strBuffer.toString();
		return key;
	}
	public String DivelKey2(){
		String key=java.util.UUID.randomUUID().toString();
		return key;
	}
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
	throws IOException {
		if(req.getParameter("DKEY1")!=null && req.getParameter("EMAIL")!=null){
			//Crear un Divel Key v1
			String email=req.getParameter("EMAIL");
			resp.setContentType("text/plain");
			resp.getWriter().println(DivelKey1(email));
			
			
		}
		if(req.getParameter("DKEY2")!=null){
			//Crear un Divel Key v2
			resp.setContentType("text/plain");
			resp.getWriter().println(DivelKey2());
		}
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		String usuario = user.getEmail();
		String key;
		String validar = req.getParameter("PRODUCTOS_KEY");
		resp.setContentType("text/html");
		resp.getWriter().println("<HTML><HEAD><TITLE>Divel Key - Validation</TITLE></HEAD>");
		resp.getWriter().println("<BODY><DIV align=\"center\"><h1>Loading "+validar+" ...</h1>");
		resp.getWriter().println("<h2>We have sent you a e-mail to "+usuario+" with the key for "+validar+"</h2></div>");
		//resp.getWriter().println("<script language=\"javascript\"> alert( \"Inicio\" );</script>");
		Integer AZPAZETA= validar.compareTo("AZPAZETA"); 
		
		if(AZPAZETA==0){
			//resp.getWriter().println("<script language=\"javascript\"> alert( \"Variable es 0\" );</script>");
			    //for (int x=0;x<usuario.length();x++)
			    //    asciicode+=usuario.charAt(x);
			  char[] chars = usuario.toCharArray();
			  StringBuffer strBuffer = new StringBuffer();
			  for (int i = 0; i < chars.length; i++) {
			    strBuffer.append(Integer.toHexString((int) chars[i]));
			  }
			  key= strBuffer.toString();
			    Properties props = new Properties();
			    resp.getWriter().println("<br>Complete login...<br>");
			    Session session = Session.getDefaultInstance(props);
			    MimeMessage message = new MimeMessage(session);
			    try {
		            Message msg = new MimeMessage(session);
		            msg.setFrom(new InternetAddress("divelkey@divelapps.appspotmail.com", "Divel Key"));
		            msg.addRecipient(Message.RecipientType.TO,
		                             new InternetAddress(usuario, "User"));
		            msg.setSubject("Divel Key");
		            msg.setText("Your key is: "+key+"\n\nThanks for use Divel Key");
		            Transport.send(msg);
		            resp.getWriter().println("<br>Message send<br>");
		        } catch (AddressException e) {
		            // ...
		        } catch (MessagingException e) {
		            // ...
		        }
				resp.getWriter().println("</BODY></HTML>");

		}else{resp.getWriter().println("<script language=\"javascript\"> alert( \"No era Azpazeta, era "+validar+"\" );</script>");}
	}
}
