package com.divel.online.apps;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class NewsAtom extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
	throws IOException{
		resp.setContentType("application/atom+xml");
		//Usage: /feed?NEW=0&TITLE=title&AUTHOR=author&BODY=body
		if(req.getParameter("NEW")!=null)
		{
			
			String title=req.getParameter("TITLE");
			String author=req.getParameter("AUTHOR");
			String body=req.getParameter("BODY");
			boolean email=(req.getParameter("SENDMAIL")!=null) ? true : false;
			News news=new News();
			news.NewNew(title, author, body,email);
		}
		News news=new News();
		resp.getWriter().println(news.GetAtomNews());
	}
}
