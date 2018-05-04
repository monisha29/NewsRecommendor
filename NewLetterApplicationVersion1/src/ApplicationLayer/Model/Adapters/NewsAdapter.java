package ApplicationLayer.Model.Adapters;



import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ApplicationLayer.Model.NewsJournal;



public class NewsAdapter extends DBUtility{
	public int newsId;
    public String headline;
    public String description;    
	public String newsUrl;
    public String keywords;
	public String imageUrl;
	
	
	
	//fetch all news
    public List<NewsAdapter> fetchAllNews() throws SQLException
    {    
    	List<NewsAdapter> adapter = new ArrayList<NewsAdapter>();         
        java.sql.Statement stmt = null;  
		ResultSet rs = null;  
        String SQL = "select * from  News";
        connectToDatabase(); 
		stmt = con.createStatement();  
        rs =  stmt.executeQuery(SQL);
       // int count=0;
        while (rs.next()) 
        {  
        NewsAdapter news= new NewsAdapter(); 
      //  news.newsId=(int)rs.getObject(0);
//        System.out.println(news.newsId);
        news.headline=(String) rs.getObject(2);         
        news.description=(String) rs.getObject(3);         
        news.imageUrl=(String) rs.getObject(4);
        news.keywords=(String) rs.getObject(6);         
        adapter.add(news);
     //   count++;	
        }
        return adapter;
    }
    
    //fetch news on id
    public List<String> fetchNewsProfile(int newsid) 
	{  
    	  List<String> l = null;
    try	{
   
	 java.sql.Statement stmt = null;  
	 ResultSet rs = null;
	// int x=newsid;
	 String selectQuery= "select * from News where id= " +newsid +" ";
	 stmt = con.createStatement();  
     rs =  stmt.executeQuery(selectQuery);
   //System.out.println(rs.getString("keywords"));
     while(rs.next())
     {   l=new ArrayList<String>();
    	 l.add(rs.getObject(6).toString());
       System.out.println(l);
     }
	}
	
    catch(Exception e)
    {
    	e.printStackTrace();

	 
		
	}
	return l ;
	}
    
    
    public List<Map<String, Number>> fetchNewsProfile() throws SQLException
    {    
    	  connectToDatabase(); 
    	List<NewsAdapter> adapter = new ArrayList<NewsAdapter>(); 
    	List<Map<String, Number>> listOfWeightedUserProfile = new ArrayList<Map<String, Number>>();
        java.sql.Statement stmt = null;  
		ResultSet rs = null;  
        String SQL = "select newsprofile from  News";
		stmt = con.createStatement();  
        rs =  stmt.executeQuery(SQL);
        String result = "";
        while(rs.next()) 
        {  
           result = (String) rs.getObject(1);  
           Map<String, Number> weightedUserProfile = new HashMap<String, Number>();
           for(String index : result.split(","))
        	   weightedUserProfile.put(index, NewsJournal.normalizedvocab.get(index));
           listOfWeightedUserProfile.add(weightedUserProfile);
        }

        
        //System.out.println(listOfWeightedUserProfile);
        
        
        return listOfWeightedUserProfile;
    }
    
    

    
    //delete al news
    public void deleteNews(){
    	connectToDatabase();
		java.sql.Statement stmt = null; 
		try{
			stmt = con.createStatement();
			String query = "DELETE FROM News";
			int deletedRows=stmt.executeUpdate(query);
			if(deletedRows>0){
			     System.out.println("Deleted All Rows In The Table Successfully...");
			}else{
                System.out.println("Table already empty."); 
			}											
		} catch(Exception e){
			System.out.println("Deleted All Rows In  Table Error. ");		
			e.printStackTrace();
		}
    }
/*
    public List<NewsAdapter> getNewsProfile() throws SQLException{
    	
    	List<NewsAdapter> adapter = new ArrayList<NewsAdapter>();       
        java.sql.Statement stmt = null;  
		ResultSet rs = null;  
        String SQL = "select profile from  News";
        connectToDatabase(); 
		stmt = con.createStatement();  
        rs =  stmt.executeQuery(SQL);
        while (rs.next()) 
        {  
        NewsAdapter news= new NewsAdapter();       	
        news.keywords=(String) rs.getObject(4);        
        adapter.add(news);	
       }
       return adapter;
    }
*/    
    
	public ArrayList<HashMap<String, Number>> fetchWeightedNewsProfile() throws SQLException
    {    
    	  connectToDatabase(); 
    	List<NewsAdapter> adapter = new ArrayList<NewsAdapter>(); 
    	ArrayList<HashMap<String, Number>> listOfWeightedNewsProfile = new ArrayList<HashMap<String, Number>>();
        java.sql.Statement stmt = null;  
		ResultSet rs = null;  
        String SQL = "select newsprofile from  News";
		stmt = con.createStatement();  
        rs =  stmt.executeQuery(SQL);
        String result = "";
        while(rs.next()) 
        {  
           result = (String) rs.getObject(1);  
           HashMap<String, Number> weightedNewsProfile = new HashMap<String, Number>();
           for(String index : result.split(","))
        	   weightedNewsProfile.put(index, NewsJournal.normalizedvocab.get(index));
           listOfWeightedNewsProfile.add(weightedNewsProfile);
        }

        
        //System.out.println(listOfWeightedUserProfile);
        
        
        return listOfWeightedNewsProfile;
    }
	@Override
	public void parseResultSet() {
		// TODO Auto-generated method stub
		
	}
}
