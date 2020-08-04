package com.prismo;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.nio.CharBuffer;
import java.sql.*;
//import java.util.Base64;
import java.util.HashMap;

import oracle.jdbc.OracleDriver;

// TODO rename to SQLServlet
@WebServlet(name = "xss")
public class Xss extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      
        PrintWriter out = null;
        try {
            out = response.getWriter();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
	    HtmlUtil.printHtmlHeader(response);
        HtmlUtil.startBody(response);
        HtmlUtil.printMenu(response);
        HtmlUtil.openTable(response);
        HtmlUtil.openRow(response);
        HtmlUtil.openCol(response);
        HtmlUtil.printCurrentTitle("XSS", response);
        
        String Fname = request.getParameter("fname");
        String form = "<form action=\"xss\" method=\"post\">" +
				"Enter Name : <input type=\"text\" id=\"fname\" name=\"fname\"><br><br>"+
				"<input type=\"submit\" value=\"Submit\">" + "</form>";
        out.println(form);
        out.printf("<br><br>Name : %s\n", Fname);
        out.println("</body>");
        out.println("</html>");
      
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
        PrintWriter out = null;
        try {
            out = response.getWriter();
        } catch (Exception e) {
            e.printStackTrace();
        }
        HtmlUtil.printHtmlHeader(response);
        HtmlUtil.startBody(response);
        HtmlUtil.printMenu(response);
        HtmlUtil.openTable(response);
        HtmlUtil.openRow(response);
        HtmlUtil.openCol(response);
        HtmlUtil.printCurrentTitle("XSS", response);
	    
        String form = "<form action=\"xss\" method=\"post\">" +
        				"Enter Name : <input type=\"text\" id=\"fname\" name=\"fname\"><br><br>"+
        				"<input type=\"submit\" value=\"Submit\">" + "</form>";
        out.println(form);
        out.println("</body>");
        out.println("</html>");
        
    }

}
