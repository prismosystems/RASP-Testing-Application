package com.prismo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.InvalidParameterException;
import java.sql.*;
import java.util.HashMap;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.io.BufferedReader;
import java.io.FileReader;
import oracle.jdbc.*;

// TODO rename to SQLServlet
@WebServlet(name = "SqlAdv")
public class SqlAdv extends HttpServlet {
    static String connectionUrl = "";
    static String dbUser = "";
    static String dbPassword = "";
    static String dbType = "";
    static String DB_TYPE_ORACLE = "Oracle";
    static String DB_TYPE_POSTGRES = "Postgres";
    
    @Override
    public void init() throws ServletException {
        super.init();
        connectionUrl =System.getProperty("prismo_connection_url", "jdbc:postgresql://localhost:5432/sqlinject?sslmode=disable");
        dbUser =System.getProperty("prismo_db_user", "postgres");
        dbPassword =System.getProperty("prismo_db_password", "Psmo0601");
        dbType =System.getProperty("prismo_db_type", DB_TYPE_POSTGRES);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
        HtmlUtil.printCurrentTitle("SQL", response);

        String form = "<form action=\"sqladv\" method=\"post\">" +
                "First name: <input type=\"text\" name=\"first\"> <br><br>" +
                "Last name: <input type=\"text\" name=\"name\"><br><br>" +
                "Password: <input type=\"text\" name=\"password\"><br><br><br>" +
                "Query Statement: <input type=\"text\" name=\"SQLStatement\">  <br><br>" +
                "<input type=\"radio\" name=\"sqltype\" value=\"PrepareStQueryRadio\">: PrearedStatement Query<br>" +
                "<input type=\"radio\" name=\"sqltype\" value=\"StoredProcQueryRadio\">: Stored Procedure Query<br>" +
                "<br><br>" +
                "<input type=\"submit\" value=\"Submit\">" + "</form>";
        out.println(form);

        HtmlUtil.closeCol(response);
        HtmlUtil.openCol(response);
        HtmlUtil.closeCol(response);
        HtmlUtil.closeRow(response);
        HtmlUtil.closeTable(response);
        out.println("</body>");
        out.println("</html>");
    }

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
        HtmlUtil.printCurrentTitle("SQL", response);

        String form = "<form action=\"sqladv\" method=\"post\">" +
                "First name: <input type=\"text\" name=\"first\"> <br><br>" +
                "Last name: <input type=\"text\" name=\"name\"><br><br>" +
                "Password: <input type=\"text\" name=\"password\"><br><br><br>" +
                "Query Statement: <input type=\"text\" name=\"SQLStatement\">  <br><br>" +
                "<input type=\"radio\" name=\"sqltype\" value=\"PrepareStQueryRadio\">: PrearedStatement Query<br>" +
                "<input type=\"radio\" name=\"sqltype\" value=\"StoredProcQueryRadio\">: Stored Procedure Query<br>" +
                "<br><br>" +
                "<input type=\"submit\" value=\"Submit\">" + "</form>";
        out.println(form);
        
        String first = request.getParameter("first");
        String last = request.getParameter("name");
        String pass = request.getParameter("password");
        String SQLStatement = request.getParameter("SQLStatement");
        //String procedure_name = request.getParameter("procedure_name");

        HashMap<String, Integer> sqltypeMap = new HashMap<String, Integer>() {{
            put("PrepareStQueryRadio", 0);
            put("StoredProcQueryRadio", 1);
        	}};

        String sqltypeStr = request.getParameter("sqltype");
        int sqltype = sqltypeMap.get(sqltypeStr);
        String retVal = "Failed!";

        switch (sqltype) {
            case 0: //storedproc
                if (ExecutePreparedStatement(SQLStatement, first,last,pass)) {
                    retVal = "Succeeded";
                }
                break;
            case 1: // executeUpdateSQLColNames
                if (ExecuteStoredProcedure(SQLStatement, first,last,pass)) {
                    retVal = "Succeeded";
                }
                break;
            default:
                System.out.println("SQL Type not found");
        }
        
        HtmlUtil.closeCol(response);
        HtmlUtil.openCol(response);
        out.println("<h2> SQL execution " + retVal + "</h2>");
        HtmlUtil.closeCol(response);
        HtmlUtil.closeRow(response);
        HtmlUtil.closeTable(response);
        out.println("</body>");
        out.println("</html>");

    }
    
    private Connection connectoracledb() {
        Connection conn = null;
        try {
        	
            // Create database connection
		      String dbURL = "jdbc:oracle:thin:@<IP>:<PORT>:XE";
		      String user = "sys as sysdba";
		      String password = "Psmo0601";
		      DriverManager.registerDriver(new OracleDriver());
			  conn = DriverManager.getConnection(dbURL, user, password);
			  System.out.println("Postgres DB Connection established");
        } catch (Exception e) {
            System.err.println("ERROR: failed to connect postgres SQL.");
            e.printStackTrace();
            return null;
        }
        return conn;
    }
    
    private Connection connectpsql() {
        Connection conn = null;
        try {
            // Create database connection
		      String dbURL = "jdbc:postgresql://<IP>:<PORT>/sqlinject?sslmode=disable";
		      String user = "postgres";
		      String password = "Psqlpsmo@1";
			  conn = DriverManager.getConnection(dbURL, user, password);
			  System.out.println("Postgres DB Connection established");
        } catch (Exception e) {
            System.err.println("ERROR: failed to connect postgres SQL.");
            e.printStackTrace();
            return null;
        }
        return conn;
    }
    
    private Connection connect() {
        Connection conn = null;
        try {
            // Create database connection
            if (dbType.equalsIgnoreCase(DB_TYPE_ORACLE)) {
                DriverManager.registerDriver(new OracleDriver());
            }
            System.out.println("Connecting to : " + connectionUrl);
            conn = DriverManager.getConnection(connectionUrl, dbUser, dbPassword);
			  System.out.println("DB Connection established");
        } catch (Exception e) {
            System.err.println("ERROR: failed to connect DB");
            e.printStackTrace();
            return null;
        }
        return conn;
    }
    
    public boolean ExecuteStoredProcedure(String SQLStatment, String fname, String last, String pass) {
    	if (null == SQLStatment)
    	{
    		System.out.println("SQL statement not found");
    		return false;
    	}
    	
    	if (null == fname)
    	{
    		fname = "User";
    	}
    	if (null == last)
    	{
    		last = "Name";
    	}
    	if (null == pass)
    	{
    		pass = "testpass";
    	}
    	
    	char someChar = '?';
    	int count = 0;
    	 boolean outparamisthere = false;
    	for (int i1 = 0; i1 < SQLStatment.length(); i1++) {
    	    if (SQLStatment.charAt(i1) == someChar) {
    	        count++;
    	    }
    	}
    	
        //Connection conn = connect();
    	Connection conn = connect();
    	try {
    		Thread.sleep(2000);}catch(Exception ex) {ex.getStackTrace();}
        if (conn == null)
            return false;
        Statement stmt = null;
        try {
            stmt = conn.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        int output = 0;
        try {
            // close the statement as its not a callable statement
            stmt.close();
            CallableStatement c = null;
            System.out.println("Input stored procedure string : " + SQLStatment);
            String procString = "{" + SQLStatment + "}";
            System.out.println("Formatted stored procedure string : " + procString);
            c = conn.prepareCall(procString);
            int i = 0;
            for (i=0; i < count; i++) {
            	if (i == 0)
                {
                	c.setString(1, fname);
                }
            	else if (i==1) {
            		c.setString(2, last);
            	}
            	else if (i==2) {
            		c.setString(3, pass);
            	}
            	else {
            		c.setString(i+1, "testvalue");
            	}
            }
            //c.registerOutParameter(4, Types.INTEGER);
            System.out.println("Stored procedure being called");
            boolean hasResults = c.execute();
            // Loop through the data and print all artist names
            output = c.getInt(3);
            System.out.println("Customer Count: " + c.getInt(3));
            // Clean up
            c.close();
        } catch (Exception e) {
            System.out.println("Exception !");
            System.err.println(e.getMessage());
        } finally {
            try {
                // Close connection
                conn.close();
            } catch (SQLException e) {
                System.err.println(e.getMessage());
                System.out.println("Exception 2");
            }
        }
        return output > 0;
    }

    public boolean ExecutePreparedStatement(String SQLStatment, String fname, String last, String pass) {
    	if (null == SQLStatment)
    	{
    		System.out.println("SQL statement not found");
    		return false;
    	}
    	
    	if (null == fname)
    	{
    		fname = "User";
    	}
    	if (null == last)
    	{
    		last = "Name";
    	}
    	if (null == pass)
    	{
    		pass = "testpass";
    	}
    	 	
    	Connection conn = connect();
        if (conn == null)
            return false;
        boolean hasResults = false;
        
        String query = SQLStatment.replaceFirst("?", fname);
    	query = query.replaceFirst("?", last);
    	query = query.replaceFirst("?", pass);
    	
        PreparedStatement stmt = null;
        try {
            
            System.out.println("PreparedStatement QUERY :" + query);
            stmt = conn.prepareStatement(query);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        
        try {
        	hasResults = stmt.execute(); 
        } catch (Exception e) {
            System.out.println("Exception !");
            System.err.println(e.getMessage());
        } finally {
            try {
                // Close connection
                stmt.close();
                conn.close();
            } catch (SQLException e) {
                System.err.println(e.getMessage());
                System.out.println("Exception 2");
            }
        }
        
    	return hasResults;
    }
}
