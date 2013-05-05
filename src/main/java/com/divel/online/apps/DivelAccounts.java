package com.divel.online.apps;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;


public class DivelAccounts extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
	throws IOException{
		resp.setContentType("text/plain");
		UserService userService = UserServiceFactory.getUserService();
		User user = userService.getCurrentUser();
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		//Inicializar servicios
		if(user==null && req.getParameter("EMAIL")==null){
			//Si no hay usuario Google o parámetro EMAIL
			resp.sendRedirect(userService.createLoginURL(req.getRequestURI()));
			
		}else{
			String email;
			if(user==null)
			{
				//El usuario es el parámetro EMAIL
				email=req.getParameter("EMAIL");
				resp.getWriter().println(email);
			}else{
				//El usuario es el EMAIL de Google
				email=user.getEmail();
			}
		if(Check(email)==false){ //¿Existe el usuario?
			//Crear usuario nuevo
			resp.getWriter().println("Creating account in Divel Account system");
			
			Entity div=new Entity("DIVEL-ACCOUNTS",email);
			div.setProperty("VecesUsado", 0); //Numero de usos de DivAccount, proporciona ventajas
			div.setProperty("Azpazeta", 0); //¿Ha registrado Azpazeta?
			div.setProperty("DivCity", 0); //¿Ha registrado DivCity?
			div.setProperty("LicenseDCM", 0); //¿Ha comprado licencia de DivCity Manager?
			div.setProperty("Bloco", 0); //¿Ha registrado Bloco?
			div.setProperty("Cheat", 0); //¿Ha comprado trucos?
			div.setProperty("NumeroBuys",0); //Numero de veces que ha gastado dinero
			div.setProperty("Developer", 0); //¿Es desarrollador?
			div.setProperty("DivSO", 0); //¿Ha registrado Divel SO?
			div.setProperty("Karma", 0); //Numero de karma
			div.setProperty("DivCoins", 0); //Numero de DivCoins
			div.setProperty("DivelKey1",DivelKey(1,email)); //Clave de Divel Key v1
			String divKey2=DivelKey(2,email);
			div.setProperty("DivelKey2",divKey2); //Clave de Divel Key v2 
			div.setProperty("Google+", 0); //URL de Google+
			div.setProperty("DivelNetworkName",email); //Nombre para Divel Network
			datastore.put(div);
			//Enviar email con datos del registro
			SendWelcomeEmail(email, divKey2); //Tambien datos del Divel Key para borrar cuenta
			
		}else{
			//El usuario existe ya en DivAccounts
			try {
				Key keydiv=KeyFactory.createKey("DIVEL-ACCOUNTS",email);
				
				Entity div=datastore.get(keydiv);
			if(req.getParameter("AZP")!=null){
					//Registra Azpazeta
					div.setProperty("VecesUsado", ((Long)div.getProperty("VecesUsado"))+1);
					div.setProperty("Azpazeta",1);
					datastore.put(div);
					resp.getWriter().println("AZP User\n");
					//Enviar email con 
					SendEmail(email, "<img src=\"https://sites.google.com/site/azpazeta/multimedia/azpazeta_svg.svg\"/><br>Welcome to Azpazeta online services. Now you are registered in Azpazeta online services with your DivAccount.");
	
			}
			if(req.getParameter("MCT")!=null){
				//Registra DivCity
				div.setProperty("VecesUsado", ((Long)div.getProperty("VecesUsado"))+1);
				div.setProperty("DivCity",1);
				datastore.put(div);
				resp.getWriter().println("MCT User\n");
				SendEmail(email, "<img src=\"https://sites.google.com/site/divelcity/multimedia/divcity_64.png\"/><br>Welcome to DivCity online services. Now you are registered in DivCity online services with your DivAccount.");
			}
			if(req.getParameter("DivSO")!=null){
				//Registra Divel SO
				div.setProperty("VecesUsado", ((Long)div.getProperty("VecesUsado"))+1);
				div.setProperty("DivSO",1);
				datastore.put(div);
				resp.getWriter().println("DivSO User\n");
				SendEmail(email, "Welcome to Divel SO online services. Now you are registered in Divel SO online services with your DivAccount.");
			}
			if(req.getParameter("Karma")!=null){
				//Aumnetar Karma gracias a buenas acciones
				div.setProperty("VecesUsado", ((Long)div.getProperty("VecesUsado"))+1);
				div.setProperty("Karma",((Long)div.getProperty("Karma"))+100);
				datastore.put(div);
				resp.getWriter().println("More Karma\n");
				SendEmail(email,"Congratulations, now you have more Karma<br>Your karma: "+div.getProperty("Karma"));
			}
			if(req.getParameter("DivCoins")!=null){
				//Aumentar DivCoins gracias a pagos
				div.setProperty("VecesUsado", ((Long)div.getProperty("VecesUsado"))+1);
				div.setProperty("DivCoins",((Long)div.getProperty("DivCoins"))+100);
				datastore.put(div);
				resp.getWriter().println("More DivCoins\n");
				SendEmail(email,"Congratulations, now you have more DivCoins<br>Your DivCoins: "+div.getProperty("DivCoins")+"<br><br>You can spend your DivCoins in:<br>*DivCity Manager<br>*BigBen building for DivCity");
			}
			if(req.getParameter("ChangeKarmaForDivCoins")!=null){
				//Comprobar Karma; 1000 Karma= 100 DivCoins = 1 €
				div.setProperty("VecesUsado",((Long)div.getProperty("VecesUsado"))+1);
				div.setProperty("Karma", ((Long)div.getProperty("Karme"))-1000);
				div.setProperty("DivCoins", ((Long)div.getProperty("DivCoins"))+100);
				SendEmail(email,"Congratulations, now you have more DivCoins<br>Your DivCoins: "+div.getProperty("DivCoins")+"<br><br>You can spend your DivCoins in:<br>*DivCity Manager<br>*BigBen building for DivCity");
			}
			if(req.getParameter("GetBigBen")!=null){
				//Obtener objeto de DivCity, BigBen
				if(((Long)div.getProperty("DivCity"))==1){
				div.setProperty("VecesUsado",((Long)div.getProperty("VecesUsado"))+1);
				div.setProperty("DivCoins",((Long)div.getProperty("DivCoins"))-200);
				resp.getWriter().println("BigBen=1");
				SendEmail(email,"Hi, now you have the Big Ben building avalible<br>Your DivCoins: "+div.getProperty("DivCoins"));
				}
			}
			if(req.getParameter("BLOCO")!=null){
				//Registrar bloco
				div.setProperty("VecesUsado",((Long)div.getProperty("VecesUsado"))+1);
				div.setProperty("Bloco",1);
				datastore.put(div);
				resp.getWriter().println("BLOCO USER");
				SendEmail(email,"<img src=\"https://sites.google.com/site/bloco3d/multimedia/bloco_64.png\">Welcome to Bloco online services. Now you are registered in Bloco online services. You can use Bloco Market.");
				
				
			}
			if(req.getParameter("SENDMAIL")!=null){
				//Reenviar email
				SendWelcomeEmail(email,div.getProperty("DivelKey2").toString());
				
				
			}
			if(req.getParameter("DELETE")!=null){
				//Borrar cuenta
				String dk2=div.getProperty("DivelKey2").toString();
				String dkenviada=req.getParameter("DKEY2");
				if(dkenviada!=null){
					if(dk2.compareTo(dkenviada)==0){
					datastore.delete(keydiv);
					SendEmail(email,"<p>Hi, you are deleted in DivAccounts</p>");
					}
				}
				
				
				
			}
			if(req.getParameter("GETSERV")!=null)
			{
					Long azpazeta=(Long)div.getProperty("Azpazeta");
					Long mct=(Long)div.getProperty("DivCity");
					Long bloco=(Long)div.getProperty("Bloco");
					resp.getWriter().println("AZPAZETA:"+azpazeta+"+DIVCITY:"+mct+"+BLOCO:"+bloco);
			}
			
			
			
			
			
			} catch (EntityNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
			
			
		}
		
		
		
		
		
		}
		
		
		

}
	
	
	private String DivelKey(int version, String email) {
		Divel_KeyServlet keyServlet=new Divel_KeyServlet();
		String key=null;
		if(version==1){
			key=keyServlet.DivelKey1(email);
		}else if(version==2){
			key=keyServlet.DivelKey2();
		}
		return key;
	}


	private void SendEmail(String email, String asunto) {
		// TODO Auto-generated method stub
		Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);
        String html = "<!DOCTYPE html>" +
        		"<html><head><title>Message from DivAccount</title></head>" +
        		"<body>" +
        		"<h1>DivAccount Message</h1>" +
        		"<p>"+asunto+"</p>" +
        		"<br>" +
        		"<p>You can watch your data from here <a href=\"http://divelapps.appspot.com/DivAccountState.jsp?EMAIL="+email+"\">DivAccount State</a></p>" +
        		"<br><p>Thanks for using DivAccount Services</p>" +
        		"</body></html>";
        Multipart mp = new MimeMultipart();
        
        try{
        MimeBodyPart htmlPart = new MimeBodyPart();
        htmlPart.setContent(html, "text/html");
        
			mp.addBodyPart(htmlPart);
        
        } catch (MessagingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}


        try {
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress("divaccount@divelapps.appspotmail.com", "DivAccounts"));
            msg.addRecipient(Message.RecipientType.TO,
                             new InternetAddress(email, "Mr. User"));
            msg.setSubject("Message from DivAccount");
            
            //msg.setText(msgBody);
            msg.setContent(mp);
            Transport.send(msg);
    
        } catch (AddressException e) {
            // ...
        	e.printStackTrace();
        } catch (MessagingException e) {
            // ...
        	e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}


	private void SendWelcomeEmail(String email, String DivelKey2){

		Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);
        String html = "<!DOCTYPE html>" +
        		"<html><head><title>Your DivAccount has been activated</title></head>" +
        		"<body>" +
        		"<h1>DivAccount Message</h1>" +
        		"<p>Congratulations, your DivAccount has been activated</p>" +
        		"<br>" +
        		"<p>Your Divel Key v2 is: " + DivelKey2 +
        		"<br>Save it for important actions like buying or deleting the account</p>" +
        		"<br>" +
        		"<p>You can watch your data from here <a href=\"http://divelapps.appspot.com/DivAccountState.jsp?EMAIL="+email+"\">DivAccount State</a></p>" +
        		"<br><p>Thanks for using DivAccount Services</p>" +
        		"</body></html>";
        byte[] attachmentData;
        Multipart mp = new MimeMultipart();
        
        try{
        MimeBodyPart htmlPart = new MimeBodyPart();
        htmlPart.setContent(html, "text/html");
        
			mp.addBodyPart(htmlPart);
		

        /*MimeBodyPart attachment = new MimeBodyPart();
        attachment.setFileName("manual.pdf");
        attachment.setContent(attachmentData, "application/pdf");
        mp.addBodyPart(attachment);*/
        
        } catch (MessagingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}


        try {
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress("divaccount@divelapps.appspotmail.com", "DivAccounts"));
            msg.addRecipient(Message.RecipientType.TO,
                             new InternetAddress(email, "Mr. User"));
            msg.setSubject("Your DivAccount has been activated");
            
            //msg.setText(msgBody);
            msg.setContent(mp);
            Transport.send(msg);
    
        } catch (AddressException e) {
            // ...
        	e.printStackTrace();
        } catch (MessagingException e) {
            // ...
        	e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	public boolean Check(String email){
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Key keyGUID=KeyFactory.createKey("DIVEL-ACCOUNTS",email);
		try {
			Entity GUID=datastore.get(keyGUID);
			return true;	
			
		} catch (EntityNotFoundException e) {
			// TODO Auto-generated catch block
			/*Entity GUID=new Entity("GUID",req.getParameter("GUID"));
			GUID.setProperty("Usado", 0);
			GUID.setProperty("Precio", 2.99);
			datastore.put(GUID);*/
			e.printStackTrace();
			return false;
		}
		
		
		
	}

	
}
