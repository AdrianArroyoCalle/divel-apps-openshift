package com.divel.online.apps;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

public class DivelUpdater extends HttpServlet{
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
	throws IOException{
		resp.setContentType("application/x-divel-updater"); //extension .upd
		//Usage:
		// /updater?PRODUCT=AZPAZETA&VERSION=2 If there are a new version, print the URL to download
		// For new products use these API:
		// /updater?PRODUCT=AZPAZETA&VERSION=2&MASTERKEY=...&URL=...
		if(req.getParameter("MASTERKEY")==null)
		{
			//Normal mode
			DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
			Key key=KeyFactory.createKey("DIVEL-UPDATER",req.getParameter("PRODUCT"));
			try {
				Entity product=datastore.get(key);
				String versionproduct=(String)product.getProperty("VERSION");
				if(versionproduct.compareTo(req.getParameter("VERSION"))==0)
				{
					resp.getWriter().println("[UPDATED]{Not action needed}");
				}else{
					resp.getWriter().println("[URL]{"+(String)product.getProperty("URL")+"}");
				}
					
				
			} catch (EntityNotFoundException e) {
				// TODO Auto-generated catch block
				resp.getWriter().println("[ERROR]{The product doesn't exist}");
				
			}
			
			
		}else{
			//Managing mode
			resp.setContentType("text/plain");
			resp.getWriter().println("Managing mode");
			if(MasterKey.CheckMasterKey(req.getParameter("MASTERKEY"))==true)
			{
				DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
				Entity product=new Entity("DIVEL-UPDATER",req.getParameter("PRODUCT"));
				product.setProperty("VERSION",req.getParameter("VERSION"));
				product.setProperty("URL",req.getParameter("URL"));
				datastore.put(product);
				resp.getWriter().println("Product updated!");
			}
			
			
			
		}
		
		
		
		
		
		
		
		
		
		
		
		
	}
}
