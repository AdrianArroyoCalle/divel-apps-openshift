package com.divel.online.apps;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;

public class DivelFeedback extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
	throws IOException{
		//Usage:
		// /feedback?TITLE=<title of review>&BODY=<body of the review>&RATING=<from 1 to 5>
		resp.setContentType("text/plain");
		String useragent=req.getHeader("User-Agent");
		
		if(req.getParameter("TITLE")!=null && req.getParameter("BODY")!=null && req.getParameter("RATING")!=null)
		{
			DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
			Entity feedback=new Entity("DIVEL-FEEDBACK");
			feedback.setProperty("Title", req.getParameter("TITLE"));
			feedback.setProperty("Message", req.getParameter("BODY"));
			feedback.setProperty("Rate",req.getParameter("RATING"));
			feedback.setProperty("Date",new java.util.Date());
			feedback.setProperty("User-Agent",useragent);
			datastore.put(feedback);
			
		}else{
			resp.getWriter().println("Please, that servlet needs parameters\nUsage: /feedback?TITLE=...&BODY=...&RATING=...");
			
		}
		
		
		
		
		
	}

}
