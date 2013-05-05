package com.divel.online.apps;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.divel.online.apps.MasterKey;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

public class Redirecter extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
	throws IOException{
		resp.setContentType("text/plain");
		//Usage: /redirecter?ID=<id of redirect> or
		//Usage: /redirecter?ID=<id of redirect>&URL=<url of redirect>&MASTERKEY=<master key>
		if(req.getParameter("MASTERKEY")==null)
		{
		
		
		if(req.getParameter("ID")!=null)
		{
			DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
			
			Key key=KeyFactory.createKey("REDIRECTER",req.getParameter("ID"));
			try {
				Entity REDIRECTER=datastore.get(key);
				String sendurl=(String)REDIRECTER.getProperty("URL");
				resp.sendRedirect(sendurl);
				

					
				
			} catch (EntityNotFoundException e) {
				resp.getWriter().println("Invalid ID");

				
			}
			
			
			
		}else{
		
		resp.getWriter().println("Invalid ID");
		}
		}else
		{
			//Create new ID for redirecter
			DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
			if(MasterKey.CheckMasterKey(req.getParameter("MASTERKEY"))!=true)
				return;
			Key key=KeyFactory.createKey("REDIRECTER",req.getParameter("ID"));
			try {
				Entity REDIRECTER=datastore.get(key);
				REDIRECTER.setProperty("URL", req.getParameter("URL"));
				datastore.put(REDIRECTER);

					
				
			} catch (EntityNotFoundException e) {
				Entity MASTER=new Entity("REDIRECTER",req.getParameter("ID"));
				MASTER.setProperty("URL", req.getParameter("URL"));
				datastore.put(MASTER);
				//resp.getWriter().println("New MasterKey");

				
			}
			
			
			
		}
	}
}
