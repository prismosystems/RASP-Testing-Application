package com.prismo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.URL;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

@javax.servlet.annotation.WebServlet(name = "AppServlet", urlPatterns = "/AppServlet")
public class AppServlet extends javax.servlet.http.HttpServlet {
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        PrintWriter out = null;
        try {
            out = response.getWriter();
        } catch (Exception e) {
            e.printStackTrace();
        }
        HtmlUtil.printHtmlHeader(response);
        HtmlUtil.startBody(response);
        HtmlUtil.printMenu(response);
        HtmlUtil.printCurrentTitle("SSRF", response);

        String form = "<form action=\"ssrf\">" +
                "URL: <input type=\"text\" name=\"ssrf\" id=\"ssrf\"><br><br>" +
                "Https URL: <input type=\"text\" name=\"httpsssrf\" id=\"httpsssrf\"><br><br>" +
                "<input type=\"submit\" value=\"Submit\">" + "</form>";
        out.println(form);

        String ssrfUrl = request.getParameter("ssrf");
        String httpsssrfUrl = request.getParameter("httpsssrf");
                
       if(ssrfUrl !=null && ssrfUrl.length() > 0) {
            UseUrlOpenConnection(request, response, ssrfUrl);
        } else if (httpsssrfUrl != null && 0 == httpsssrfUrl.toUpperCase().indexOf("HTTPS://")) {
        	UseUrlOpenConnectionhttps(request, response, httpsssrfUrl);    	
        }
        
        System.out.println("Executed URLOpen");

    }

    public void UseUrlOpenConnection(javax.servlet.http.HttpServletRequest request,
                                     javax.servlet.http.HttpServletResponse response, String ssrfURL) throws javax.servlet.ServletException, IOException {
        try {
            response.getWriter().println("Inside Url.openStream");
            String url  = "https://www.oracle.com/";
            if (ssrfURL != null && ssrfURL.length() > 0) {
                url = ssrfURL;
            }
            
            URL oracle = new URL(url);
            BufferedReader in = new BufferedReader(new InputStreamReader(oracle.openStream()));

            String inputLine;
            while ((inputLine = in.readLine()) != null){
                System.out.println(inputLine);
                response.getWriter().print(inputLine);
                }
            in.close();
        } catch (Exception e) {
            response.getWriter().println("Exception!!");
            response.getWriter().print(e.getMessage());
        }
    }

    public void UseUrlOpenConnectionhttps(javax.servlet.http.HttpServletRequest request,
            javax.servlet.http.HttpServletResponse response, String ssrfURL) throws javax.servlet.ServletException, IOException {
        
        String UrlToOpen = ssrfURL.replaceFirst("HTTPS://", "");
        UrlToOpen = UrlToOpen.replaceFirst("https://", "");
        
        try {
	        	System.out.printf("Opening SSL socket for host : %s\n", UrlToOpen);
	            SSLSocketFactory factory =
	                    (SSLSocketFactory)SSLSocketFactory.getDefault();
	            SSLSocket socket =
	                    (SSLSocket)factory.createSocket(UrlToOpen, 443);
	
	            socket.startHandshake();
	
	            PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())));
	
	            out.println("GET / HTTP/1.0");
	            out.println();
	            out.flush();
	
	            if (out.checkError())
	                System.out.println("SSLSocketClient:  java.io.PrintWriter error");
	
	            BufferedReader in = new BufferedReader(
	                    new InputStreamReader(
	                            socket.getInputStream()));
	
	            String inputLine;
	            while ((inputLine = in.readLine()) != null) {
	                System.out.println(inputLine);
	                response.getWriter().print(inputLine);
	            	}
	            in.close();
	            out.close();
	            socket.close();

        	} catch (Exception e) {
        		e.printStackTrace();
        	}
    	}
}




