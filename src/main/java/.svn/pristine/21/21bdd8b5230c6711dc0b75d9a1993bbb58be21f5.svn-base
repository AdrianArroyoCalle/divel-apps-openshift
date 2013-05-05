package com.divel.online.apps;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.jasper.util.Enumerator;

public class ProcSudoku extends HttpServlet {
	@SuppressWarnings("null")
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException{
		resp.setContentType("text/plain");
		resp.getWriter().println("Procesando...");
		int sudoku[]={9,2,1,/**/3,4,7,/**/8,6,5,
				3,8,6,/**/5,1,2,/**/4,7,9,
				4,7,5,/**/6,8,9,/**/1,2,3,
				/**************************/
				2,1,4,/**/8,3,5,/**/6,9,7,
				5,3,7,/**/1,9,6,/**/2,4,8,
				6,9,8,/**/2,7,4,/**/3,5,1,
				/**************************/
				1,4,2/**/,9,5,3/**/,7,8,6,
				7,5,3/**/,4,6,8/**/,9,1,2,
				8,6,9/**/,7,2,1/**/,5,3,4};
		int sudokuuser;
		boolean mal=false;
		Map mapa=req.getParameterMap();
		Map      map  = req.getParameterMap() ;
		Iterator iter = map.entrySet().iterator() ;
		  while ( iter.hasNext() ) {
		    Map.Entry entry  = (Map.Entry)iter.next();
		    String    name   = (String)entry.getKey();
		    String[]  values = (String[])entry.getValue();
		    int casilla = Integer.parseInt(name);
		   
		    int valor = Integer.parseInt(values[0]);
		    //resp.getWriter().println("Datos a comparar si "+sudoku[casilla-1]+"es igual a "+valor);
		    if(sudoku[casilla-1]!=valor){resp.getWriter().println("Mal");mal=true;}
		  }
		if(mal==false){resp.getWriter().println("Bien, lo has hecho correctamente");}
		 
		
		
	}
		
	}