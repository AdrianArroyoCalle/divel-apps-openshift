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

public class MasterKey extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
	throws IOException{
		resp.setContentType("text/plain");
		
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		
		Key keyused=KeyFactory.createKey("MASTER-KEY","SET?");
		try {
			Entity SET=datastore.get(keyused);
			resp.getWriter().println("MasterKey already set");
			return;
				
			
		} catch (EntityNotFoundException e) {
			// TODO Auto-generated catch block
			Entity SET=new Entity("MASTER-KEY","SET?");
			SET.setProperty("SET", true);
			datastore.put(SET);
			resp.getWriter().println("MasterKey not set");
			//e.printStackTrace();
			
		}
		
		Key key=KeyFactory.createKey("MASTER-KEY",req.getParameter("KEY"));
		try {
			Entity MASTER=datastore.get(key);
			resp.getWriter().println("MasterKey used - Strange error");
				
			
		} catch (EntityNotFoundException e) {
			// TODO Auto-generated catch block
			Entity MASTER=new Entity("MASTER-KEY",req.getParameter("KEY"));
			datastore.put(MASTER);
			resp.getWriter().println("New MasterKey");
			//e.printStackTrace();
			
		}
		
	
	
	}
	public static boolean CheckMasterKey(String keys){
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		
		Key key=KeyFactory.createKey("MASTER-KEY",keys);
		try {
			Entity MASTER=datastore.get(key);
			//resp.getWriter().println("MasterKey correct!");
			return true;
				
			
		} catch (EntityNotFoundException e) {
			// TODO Auto-generated catch block
			//Entity MASTER=new Entity("MASTER-KEY",keys);
			//datastore.put(MASTER);
			//resp.getWriter().println("New MasterKey");
			//e.printStackTrace();
			return false;
			
		}
		
	}
}
