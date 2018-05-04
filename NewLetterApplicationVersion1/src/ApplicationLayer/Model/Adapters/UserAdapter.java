package ApplicationLayer.Model.Adapters;

import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import ApplicationLayer.Model.User;



public class UserAdapter extends DBUtility{

	public String username; 
	public String password;
	public int id;
	
	void parseResultSet() {
        
		
		connectToDatabase();
		try {
			ResultSet rs = executeSelectQuery("");
	         while (rs.next()) { 
	     		  System.out.println(rs.getObject(4));
	     		 
		       } 
			
		} catch (Exception e) {
	
			e.printStackTrace();
		}
		
		
	}
	
	
	public void fetchUser(String username, String password)
	{
		 connectToDatabase();
		 java.sql.Statement stmt = null;  
	      ResultSet rs = null;  
	      try {  

		     	    String SQL = "select * from [User] where username = '" + username+"' and [password] = '" + password + "'";  
		     	    System.out.println(SQL);
			         stmt = con.createStatement();  
			         rs =  stmt.executeQuery(SQL);  
			         while (rs.next()) { 
			     		  this.id =  (Integer) rs.getObject(1);
			     		  this.password = password;
			     		  this.username = username;
				       } 
			      }
			      catch (Exception e) {  
				         e.printStackTrace();  
				      } 
	      
	      
	}
	
	public int addUser(String username,String password,String email){
		connectToDatabase();
	    //String sql = "insert into User(id,username,password,email) values(?,?,?)";
	    String sql = "insert into [User](username,password,email)  "
	    		+ ""
	    		+ "values('"+username+"','"+password+"','"+email+ "') ";
//	    System.out.println(sql);
//        PreparedStatement ps;
	   BigDecimal ans = null;
	    try {
	    	Statement stmt = con.createStatement();         // Create a Statement object

	    	stmt.executeUpdate(sql,Statement.RETURN_GENERATED_KEYS);   // Indicate you want automatically 
	    	                                      // generated keys
	    	ResultSet rs = stmt.getGeneratedKeys();     
	    	while(rs.next()){
            System.out.println(rs.getBigDecimal(1));
            ans = rs.getBigDecimal(1);
	    	
	    	}
//	    	ps = con.prepareStatement(sql);	}
//	    	//ps.setString(1, user.getUsername());
//	    	//ps.setString(2, user.getPassword());
//	    	//ps.setString(3, user.getEmail());
//	    	ResultSet set = ps.executeUpdate();    
//	    	System.out.println(set);
			}
        catch (SQLException e) {
        	e.printStackTrace();
			}
	    return Integer.parseInt(ans.toString());

	}
	

}
