package ApplicationLayer.Model.Adapters;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
//import java.util.ArrayList;
import java.util.List;

//import ApplicationLayer.Model.News;

public class LogAdapter extends DBUtility {
	public int count;  
	public int finalCount;
	public double updatedweight;

	
	public LogAdapter()
	{
		connectToDatabase();
	}
	
	
	@SuppressWarnings("static-access")
	public void insertintoDB(int userId,List<Integer> indexofkeywords) 
	{
		try{
		 // connectToDatabase();
		  java.sql.Statement stmt = null;  
		  ResultSet rs = null;
		 
		  
		  
		  for(int i=0;i<indexofkeywords.size();i++)
			{ 
		      Integer keyword=indexofkeywords.get(i);
			 String selectQuery= "select * from LOGS where userid= " +userId +" and corpusid="+keyword+"";
			
			  stmt = con.createStatement( rs.TYPE_SCROLL_SENSITIVE,
			          rs.CONCUR_UPDATABLE); 
			 
			  rs =  stmt.executeQuery(selectQuery);
			  
			  //con.setReadOnly(false);
			  if(rs.next())
			  { 
				  
//			  while(rs.next()){
//			  int id=Integer.parseInt(rs.getObject(1).toString());
//			  System.out.println(id);
//		      if(id==userId)
//		      {if(rs.getObject(2).toString().equals(indexofkeywords.get(i).toString()))
//			   System.out.println(rs.getObject(3).toString());
			  count=Integer.parseInt(rs.getObject(3).toString());
				  //double weight=Double.parseDouble(rs.getObject(4).toString());
				  finalCount=count+1;
				  //updatedweight=weight+(finalCount/2);
				 //System.out.println(updatedweight);
//				  rs.updateInt("count", finalCount);
			 
				  rs.deleteRow();
				  stmt.executeUpdate("INSERT INTO LOGS " + 
			                "VALUES ("+userId+", "+keyword+","+finalCount+")");			  
			  }
			  
			  else  if(!rs.next())
		      {   int count=1;
		          //updatedweight=0.125;
		    	  stmt.executeUpdate("INSERT INTO LOGS " + 
			                "VALUES ("+userId+", "+keyword+","+count+")");
		      }
			  
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
			  }
	
	
	public void insertLog(int userid, int newsid)
	{
	try
	{
		
		
		
		
	}
	catch(Exception e)
	{
		e.printStackTrace();
	}
	
		
	}
	
	
	public void getListOfTopNewsProfiles(int userId) throws SQLException
	   {java.sql.Statement stmt = null;  
		 ResultSet rs = null;  
		 
		
		 String SQL = "select * from LOGS where userid="+userId+" order by weight DESC";
			stmt = con.createStatement();  
	      rs =  stmt.executeQuery(SQL);
		  while(rs.next())
		  {
			  System.out.println(rs.getObject(4));
		  }
//		return l;
		   
	   }
	
	public boolean userExistsWithLogs(int userid)
	{
		boolean status = false;
		java.sql.Statement stmt = null;  
		 ResultSet rs = null;  
		try
		{
			
		String SQL = "select * from Log where userid="+userid ;
		stmt = con.createStatement();  
		rs =  stmt.executeQuery(SQL);
		 if(rs.next())
		    	status = true;
		    
			 
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try {
				
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return status;
	}
		
	
	public HashMap<String,Integer> logsOfAUser(int userid)
	{
		HashMap<String,Integer> usersfeatures = new HashMap<String,Integer>();
		java.sql.Statement stmt = null;  
		 ResultSet rs = null;  
		try
		{
			
		String SQL = "select l.weight,gc.feature,gc.weight from Logs l inner join GlobalCorpus gc on l.corpusid = gc.id where userid="+userid ;
		System.out.println(SQL);
		stmt = con.createStatement();  
		rs =  stmt.executeQuery(SQL);
		 while(rs.next())
		 {
			 String feature = (String) rs.getObject(2);
			 int weight = (int) rs.getObject(1);
			 if(usersfeatures.containsKey(feature))
					usersfeatures.put(feature,usersfeatures.get(feature)+weight);
					else
						usersfeatures.put(feature, weight);
		 }
		    	
		    
		 System.out.println(usersfeatures);
		//return usersfeatures;	 
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			try {
				
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return usersfeatures;
		
	
	}
	
//	public List<Integer> fetchLogKewordsOfAuser(int userid)
//	{
//		List<Integer> idOfUserKeywords = new ArrayList<Integer>();
//		java.sql.Statement stmt = null;  
//		 ResultSet rs = null;  
//		 
//		
//		 String SQL = "select * from LOGS where userid="+userid;
//			stmt = con.createStatement();  
//	      rs =  stmt.executeQuery(SQL);
//		  while(rs.next())
//		  {
//			  System.out.println(rs.getObject(4));
//		  }
//		return l;

	


		    	  
			  
			
		     
			 
		 		
		
				
		
	
	
	

	@Override
	public void parseResultSet() {
		// TODO Auto-generated method stub
		
	}



	     

}
