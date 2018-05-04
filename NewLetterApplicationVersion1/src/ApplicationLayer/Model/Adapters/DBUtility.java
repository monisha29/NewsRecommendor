package ApplicationLayer.Model.Adapters;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;


//Implementation of Pure Fabrication
public abstract class DBUtility {

	
	private String connectionUrl = "jdbc:sqlserver://M-1736:1433;" +  
	         "databaseName=NewsLetterApplication;integratedSecurity=true";
    Connection con = null;  
   

	public String getConnectionUrl() {
		return connectionUrl;
	}

	public void setConnectionUrl(String connectionUrl) {
		this.connectionUrl = connectionUrl;
	} 
	
	
	public void connectToDatabase()
	{
		  try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		
	        this.con = DriverManager.getConnection(connectionUrl);  
	        System.out.println("Connection object formed");
	         
		  } catch (ClassNotFoundException e) {
	
				e.printStackTrace();
			} catch (SQLException e) {

			e.printStackTrace();
		}   
		  
		  
		  }

	
	public ResultSet executeSelectQuery(String selectQuery) throws Exception{
		 java.sql.Statement stmt = null;  
		 ResultSet rs = null;  
		
		if(con!=null)
		{
			try
			{
			
//				boolean reachable = con.isValid(1);// 10 sec
		         stmt = con.createStatement();  
		         rs =  stmt.executeQuery(selectQuery);  
		      }
		      catch(Exception e) {  
			         e.printStackTrace();  
			      } 
		}
		else
		{
			throw new Exception("Create COnnection Object");
		}
		return rs;
		
		
	}
		  
	
	
	abstract void parseResultSet();
	
	
	
	
	
	
}
