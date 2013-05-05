package com.divel.online.apps;

import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.ServletResponse;
import javax.servlet.http.*;
import javax.servlet.*;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import java.util.Date;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;



@SuppressWarnings("serial")
public class Divel_AppsServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
        UserService userService = UserServiceFactory.getUserService();
        User user = userService.getCurrentUser();

        int estado=0;
        if (user != null) {
            resp.setContentType("text/html");
            resp.getWriter().println("<HTML><HEAD><TITLE>Index page</TITLE></HEAD>");
            resp.getWriter().println("<p>Welcome, " + user.getNickname()+"</p>");
            estado=1;
            DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
            Entity employee = new Entity("Users");
            employee.setProperty("E-mail", user);
            employee.setProperty("Name", userService.getCurrentUser().getNickname());

            Date hireDate = new Date();
            employee.setProperty("Date", hireDate);

            datastore.put(employee);

        } else {
            resp.sendRedirect(userService.createLoginURL(req.getRequestURI()));
        }
        
		if(estado==1){
		resp.setContentType("text/html");
		//ServletOutputStream salida = resp.getOutputStream();
		resp.getWriter().println("<BODY>");
		resp.getWriter().println("<IMG SRC=\"DivelApps.png\"><BR><BR>");
		resp.getWriter().println("<p>Welcome to Divel Apps</p>");
		String userApps = req.getParameter("USER");
		String infoAppsx = req.getParameter("APPS.x");
		String infoAppsy = req.getParameter("APPS.y");
		resp.getWriter().println ("<p>Your nickname is : "+userApps+ "</p>"); 
		String thisURL = req.getRequestURI();
		resp.getWriter().println ("<p>Avalible services:</p><BR><BR>");
		resp.getWriter().println ("<a href=\"DivelKey.htm\">Divel Key</a>");
		resp.getWriter().println("<a href=\"DivelCalculate.htm\">Divel Calculate</a>");
		resp.getWriter().println("<p><a href=\""+userService.createLogoutURL("http://1.divelapps.appspot.com") + "\">Logout</a></p>");
		resp.getWriter().println("</BODY></HTML>");
		if(infoAppsx=="201" && infoAppsy=="106")
		{
			resp.getWriter().println("javascript:alert(\"Para más información visita http://sites.google.com/site/divelmedia.\")");
		}
		}else{
			resp.setContentType("text/html");
			resp.getWriter().println("<HTML><HEAD><TITLE>Acceso de usuarios</TITLE></HEAD><BODY><p>No ha sido posible</p></BODY></HTML>");
		}
	}
}
