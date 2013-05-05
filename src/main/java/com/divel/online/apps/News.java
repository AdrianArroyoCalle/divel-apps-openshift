package com.divel.online.apps;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.TimeZone;

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

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;	



public class News {
	DatastoreService datastore;
	boolean googleAppEngine;
	News()
	{
		String appEngineVersion = System.getProperty("com.google.appengine.runtime.version");
		if(appEngineVersion==null)
			googleAppEngine=false;
		else
			googleAppEngine=true;
		
		
		//Constructor
		if(googleAppEngine) //GAE Version
		 datastore= DatastoreServiceFactory.getDatastoreService();
		else{
			//Java SE Version
		}
			
		
	}
	public void NewNew(String title,String author,String body,boolean email)
	{
		//SimpleDateFormat gmtDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		//gmtDateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));

		//Current Date Time in GMT
		 //String date=gmtDateFormat.format(new Date());
		String date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").format(new Date());
		
		
		Entity news=new Entity("NEWS",title);
		news.setProperty("Title", title);
		news.setProperty("Author",author);
		news.setProperty("HTMLText",body);
		news.setProperty("Date",date);
		news.setProperty("UUID", java.util.UUID.randomUUID().toString());
		datastore.put(news);
		
		Entity lastupdate=new Entity("NEWS-LASTUPDATE","ATOM-MAIN");
		lastupdate.setProperty("DATE",date);
		datastore.put(lastupdate);
		if(email)
			SendMail(body,title);
	}
	public void SendMail(String body, String title)
	{
		Query q = new Query("DIVEL-ACCOUNTS");

		// PreparedQuery contains the methods for fetching query results
		// from the datastore
		PreparedQuery pq = datastore.prepare(q);

		for (Entity result : pq.asIterable()) {
		  String email=(String)result.getKey().getName();
		  
		  Properties props = new Properties();
	        Session session = Session.getDefaultInstance(props, null);
	        String html = "<!DOCTYPE html>" +
	        		"<html><head><title>Divel Newsletter</title></head>" +
	        		"<body>" +
	        		"<h1>"+title+"</h1>" +
	        		body+
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
	            msg.setFrom(new InternetAddress("newsletter@divelapps.appspotmail.com", "Divel News"));
	            msg.addRecipient(Message.RecipientType.TO,
	                             new InternetAddress(email, "Mr. User"));
	            msg.setSubject("Divel Newsletter");
	            
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
	}
	public String GetAtomNews()
	{
		String date=null;
		Key keyDate=KeyFactory.createKey("NEWS-LASTUPDATE","ATOM-MAIN");
		try {
			Entity lastupdate=datastore.get(keyDate);
			date=(String)lastupdate.getProperty("DATE");
				
			
		} catch (EntityNotFoundException e) {
			// TODO Auto-generated catch block
			/*Entity GUID=new Entity("GUID",req.getParameter("GUID"));
			GUID.setProperty("Usado", 0);
			GUID.setProperty("Precio", 2.99);
			datastore.put(GUID);*/
			e.printStackTrace();
			date="Unknow";
			
		}
		
		
		String atom;
		atom="<?xml version=\"1.0\" encoding=\"utf-8\"?>"+
		"<feed xmlns=\"http://www.w3.org/2005/Atom\">"+
        "<title>Divel News</title>"+
        "<subtitle>Newsletter about Divel products</subtitle>"+
        "<link href=\"http://divelapps.appspot.com/feed\" rel=\"self\" />"+
        "<id>http://divelapps.appspot.com/</id>"+
        "<updated>"+date+"</updated>";
		
		Query q = new Query("NEWS");

		// PreparedQuery contains the methods for fetching query results
		// from the datastore
		PreparedQuery pq = datastore.prepare(q);

		for (Entity result : pq.asIterable()) {
		  String title = (String) result.getProperty("Title");
		  String author= (String) result.getProperty("Author");
		  String body = (String) result.getProperty("HTMLText");
		  String pubdate = (String) result.getProperty("Date");
		  String id=(String) result.getProperty("UUID");
		  String xml="<entry>"+
          "<title>"+title+"</title>"+
             "<id>"+id+"</id>"+
              "<updated>"+pubdate+"</updated>"+
               "<summary>Read the new newsletter about Divel Products</summary>"+
               "<content type=\"html\">"+
               body+
               "</content>"+
                "<author>"+
                      "<name>"+author+"</name>"+
                      "<email>divel@lists.launchpad.net</email>"+
                "</author>"+
        "</entry>";
		  atom+=xml;
		}
		atom+="</feed>";
		
		return atom;
	}
	
}
