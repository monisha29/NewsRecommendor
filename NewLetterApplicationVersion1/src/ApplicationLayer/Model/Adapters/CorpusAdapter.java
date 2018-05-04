package ApplicationLayer.Model.Adapters;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CorpusAdapter extends DBUtility{
	
    public int id; 
	public String tagname;
	
	
	public CorpusAdapter() {
		
        connectToDatabase();		

	}

	public void transferCorpus(){
	    String SQL = "INSERT INTO GlobalCorpus(feature,weight) SELECT * FROM TempCorpus";
    	PreparedStatement ps;
	    try {
			ps = con.prepareStatement(SQL);	
			ps.executeUpdate();
		}
        catch (SQLException e) {
			e.printStackTrace();
		}

	 }
		
	 public void loadTempCorpus(Set<String> set)   {
		 
		 //delete tempCorpus before loading 
			java.sql.Statement stmt = null; 
			try{
				stmt = con.createStatement();
				String query = "DELETE FROM TempCorpus";
				int deletedRows=stmt.executeUpdate(query);
				if(deletedRows>0){
				     System.out.println("Deleted All Rows In The Table TempCorpus...");
				}else{
	                System.out.println("TempCorpus Table already empty"); 
				}											
			} catch(Exception e){
				System.out.println("Deleted all rows error");		
				e.printStackTrace();
			}
		 
		 //load vocab into temoCorpus
		    for(String s:set){   	
		    	String sql = "insert into TempCorpus(tagname) values(?)";
		    	PreparedStatement ps;
				try {	
					ps = con.prepareStatement(sql);	
					ps.setString(1, s);
					ps.executeUpdate();
					}
		    	catch (SQLException e) {
					e.printStackTrace();
					}
	 }
		
	}

	@Override
	public void parseResultSet() {
		// TODO Auto-generated method stub
		
	}

	public int getRowCount(){
      
        java.sql.Statement stmt = null;  
		ResultSet rs = null;  
        String SQL = "select * from  GlobalCorpus";
        int count=0;
		try {
			stmt = con.createStatement();		  
			rs =  stmt.executeQuery(SQL);
			while (rs.next()) 
			{  
				count++;	
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return count;
	}
	
	
	public int findKeywordId(String keyword)
	{
		
		int idOfKeyword =0;
        java.sql.Statement stmt = null;  
		ResultSet rs = null;  
        String SQL = "SELECT  [id] FROM [GlobalCorpus] where feature = '" +  keyword+"'";
        System.out.println();
        int count=0;
		try {
			stmt = con.createStatement();		  
			rs =  stmt.executeQuery(SQL);
			while (rs.next()) 
			{  
				idOfKeyword = (Integer)rs.getObject(1);
			}
			if(rs!=null)
			{
				rs.close();
			}
			if(stmt!=null){
				
			
			stmt.close();
			}
			con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
        return idOfKeyword;
	}

	
	public void loadCorpus(HashMap<String,Integer> vocab_count)   {
		 

	    for(Map.Entry<String,Integer> entry : vocab_count.entrySet()){  
	    	
	    	String feature=entry.getKey();
	    	int weight=	entry.getValue();	
			try {
            Statement sqlStatement = con.createStatement();
	    	String booleanString = "SELECT weight FROM GlobalCorpus Where feature = '" + feature + "'";
	    	String queryString = "";
			ResultSet rs = null;
            sqlStatement.execute(booleanString);
            ResultSet resultSet = sqlStatement.getResultSet(); //result set for records
            boolean recordFound = resultSet.next();
     
            	if (recordFound) {
            		
            		//queryString = "SELECT weight FROM GlobalCorpus Where feature = '" + feature + "'";
                    //rs =  sqlStatement.executeQuery(booleanString);
                    int count = (int) resultSet.getObject(1);
            		count=count+weight;
            		String sql = "Update GlobalCorpus set weight = "+count+" where feature ='"+feature+"'";
            		PreparedStatement ps;	
            		ps = con.prepareStatement(sql);	
            		//ps.setString(1, feature);
            		//ps.setString(2, String.valueOf(count));										
            		ps.executeUpdate();
									
					}
            	else{
            		String sql = "insert into GlobalCorpus(feature,weight) values(?,?)";
            		PreparedStatement ps;

            		ps = con.prepareStatement(sql);	
            		ps.setString(1, feature);
            		ps.setString(2, String.valueOf(weight));										
            		ps.executeUpdate();
            		
            	}
			}
	    	catch (SQLException e) {
				e.printStackTrace();
				}

            }	    	
	    	

}

}
