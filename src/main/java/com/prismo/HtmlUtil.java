package com.prismo;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

public class HtmlUtil {
    static String title = "Prismo Vulnerable App";

    public static void printMenu(HttpServletResponse response) {
        try {
            PrintWriter out = response.getWriter();
            StringBuffer menu = new StringBuffer();
            menu.append("&nbsp&nbsp");
            menu.append("<div style=\"background-color:cyan\"><br>");
            menu.append("&nbsp&nbsp");
            menu.append("<b><a href=sql>SQL</a></b>");
            menu.append("&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp");
            menu.append("<b><a href=sqladv>SQL Advanced</a></b>");
            menu.append("&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp");
            menu.append("<b><a href=cmdexec>Web shell</a></b>");
            menu.append("&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp");
            menu.append("<b><a href=oscmd>Os Command</a></b>");
            menu.append("&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp");
            menu.append("<b><a href=ssrf>SSRF</a></b>");
            menu.append("&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp");
            menu.append("<b><a href=deserialize>Deserialization</a></b>");
            menu.append("&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp");
            menu.append("<b><a href=xss>XSS</a></b>");
            menu.append("<br>");
            menu.append("&nbsp&nbsp");
            menu.append("</div>");
            out.println(menu);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void openTable(HttpServletResponse response) {
        try {
            PrintWriter out = response.getWriter();
            out.println("<table>");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void closeTable(HttpServletResponse response) {
        try {
            PrintWriter out = response.getWriter();
            out.println("</table>");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void openRow(HttpServletResponse response) {
        try {
            PrintWriter out = response.getWriter();
            out.println("<tr>");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void closeRow(HttpServletResponse response) {
        try {
            PrintWriter out = response.getWriter();
            out.println("</tr>");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void openCol(HttpServletResponse response) {
        try {
            PrintWriter out = response.getWriter();
            out.println("<td valign=\"top\">");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void closeCol(HttpServletResponse response) {
        try {
            PrintWriter out = response.getWriter();
            out.println("</td>");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void printCurrentTitle(String title, HttpServletResponse response) {
        try {
            PrintWriter out = response.getWriter();
            out.println("<br><br><br> <b>" +title + "<br> </b> <br>");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void startBody(HttpServletResponse response) {
        try {
            PrintWriter out = response.getWriter();
            out.println("<body bgcolor=\"lightgray\" onload=\"loaded();\">");
            out.println("<h1>" + HtmlUtil.title + "</h1>");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void printHtmlHeader(HttpServletResponse response) {
        try {
            response.setContentType("text/html");
            response.setCharacterEncoding("UTF-8");
            PrintWriter out = response.getWriter();
            out.println("<!DOCTYPE html><html>");
            out.println("<head>");
            out.println("<meta charset=\"UTF-8\" />");
            out.println("<title>" + HtmlUtil.title + "</title>");
            out.println("</head>");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
