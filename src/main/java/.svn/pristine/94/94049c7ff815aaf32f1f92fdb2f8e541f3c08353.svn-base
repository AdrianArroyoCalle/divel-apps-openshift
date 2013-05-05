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

@SuppressWarnings("serial")
public class Licencias extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
	throws IOException{
		resp.setContentType("text/plain");
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		/*Entity GUID =new Entity("GUID",req.getParameter("GUID"));
		GUID.setProperty("Precio", 2.99);*/
		if(req.getParameter("GUID")!=null){
		//resp.getWriter().println(req.getParameter("GUID"));
		Key keyGUID=KeyFactory.createKey("GUID",(String) req.getParameter("GUID"));
		try {
			Entity GUID=datastore.get(keyGUID);
			Long usado=(Long) GUID.getProperty("Usado");
			if(usado<50){
				usado++;
				GUID.setProperty("Usado",usado);
				datastore.put(GUID);
				resp.getWriter().println("GUID valido\nUsado: "+usado);
				
				
			}else{
				resp.getWriter().println("GUID Invalido\nUsado: "+usado);
				
				
			}
		} catch (EntityNotFoundException e) {
			// TODO Auto-generated catch block
			/*Entity GUID=new Entity("GUID",req.getParameter("GUID"));
			GUID.setProperty("Usado", 0);
			GUID.setProperty("Precio", 2.99);
			datastore.put(GUID);*/
			e.printStackTrace();
			resp.getWriter().println("GUID Invalido\nNo usado");
		}
		
		}else{
			resp.getWriter().println("Por favor, usa la siguiente estructura: divelapps.appspot.com/licencias?GUID=tuguid");
			
		}
		
		
	
	
	}
}
