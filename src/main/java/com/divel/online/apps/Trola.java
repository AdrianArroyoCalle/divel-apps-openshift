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

public class Trola extends HttpServlet{
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
	throws IOException{
		resp.setContentType("application/x-trola-protocol");
		DatastoreService datastore;
		datastore= DatastoreServiceFactory.getDatastoreService();
		
		if(req.getParameter("INITIALIZE")!=null)
		{
			Entity SET=new Entity("TROLA-KEY","INITIALIZE");
			SET.setProperty("ACTIVE", false);
			SET.setProperty("DONATIVE/MONEY",0);
			SET.setProperty("CREATED",new java.util.Date());
			SET.setProperty("PASSWORD","Undefined");
			datastore.put(SET);
			resp.getWriter().println("[INFO]{TrolaKey initialized. This action only creates the Entity structure for create Enitys in control panel}");
			return;
		}
		if(req.getParameter("CREATE")!=null)
		{
			String masterkey=req.getParameter("MASTERKEY");
			if(MasterKey.CheckMasterKey(masterkey)==true)
			{
				Entity SET=new Entity("TROLA-KEY",req.getParameter("EMAIL"));
				SET.setProperty("ACTIVE", true);
				SET.setProperty("DONATIVE/MONEY",req.getParameter("MONEY"));
				SET.setProperty("CREATED",new java.util.Date());
				SET.setProperty("PASSWORD",req.getParameter("PASSWORD"));
				datastore.put(SET);
			}
			
		}
		
		
		Key key=KeyFactory.createKey("TROLA-KEY",req.getParameter("EMAIL"));
		try {
			Entity user=datastore.get(key);
			if(((String)user.getProperty("PASSWORD")).compareTo(req.getParameter("PASSWORD"))!=0)
			{
				resp.getWriter().println("[ERROR]{User hacked}");
			}
			else{
				if((java.lang.Boolean)user.getProperty("ACTIVE")==true)
				{
					resp.getWriter().println("[OK]{Correct user}");
					if(req.getParameter("DOWNLOAD")!=null)
					{
						resp.sendRedirect("trola.jnlp");
					}
					
					
				}
			}
			
		} catch (EntityNotFoundException e) {
			// TODO Auto-generated catch block
			resp.getWriter().println("[ERROR]{The user doesn't exist}");
			
		}
		
	}
}
