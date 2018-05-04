package ApplicationLayer.Model;

import java.util.ArrayList;
import java.util.HashMap;
//import java.util.HashMap;
import java.util.List;
import java.util.Set;

import ApplicationLayer.Model.Adapters.CorpusAdapter;
import ApplicationLayer.Model.Adapters.LogAdapter;
import ApplicationLayer.Model.Adapters.NewsAdapter;

public class Log
{ 
	private int userId;
	private int newsId;
    private String type;
    
    public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getNewsId() {
		return newsId;
	}
	public void setNewsId(int newsId) {
		this.newsId = newsId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public List<Integer> indexOfKeywords= new ArrayList<>();
  //  NewsLetter letter= new NewsLetter();
   // public void calculateWeightOfANewsProfile() throws Exception
//    {
//    	//static variable
//    	//global corpus 
//      LogAdapter adapter = new LogAdapter();
//     List<String> newsProfile= new ArrayList<String>();
//    // newsProfile=adapter.fetchNewsProfile(1);
//   
//     
//     
//     indexOfKeywords=letter.checkInCorpus(newsProfile);
//   
//     adapter.insertintoDB(1,indexOfKeywords);
//    
//    
//    
//      
//    	
//    }
	
	
	public String storeLog()
	{
		String response = "";
		try
		{
		//NewsJournal nl =new  NewsJournal(); 	
		LogAdapter log = new LogAdapter();
		System.out.println("here news id to be logged " + this.newsId);
		List<Integer> listOfIndexes = searchForNewsKeywords(this.newsId);
		System.out.println("indexes : " + listOfIndexes);
		log.insertintoDB(this.userId, listOfIndexes);
		
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		
		
		return response;
		
		
	}
	
	
	public List<Integer> searchForNewsKeywords(int newsid)
	{
		
		List<Integer> indexesInGlobalCorpus = new ArrayList<Integer>();
		try{
		NewsAdapter adapter= new NewsAdapter();
		NewsJournal.listOfWeightedNewsProfile = adapter.fetchWeightedNewsProfile();
		System.out.println("printSize"+ NewsJournal.listOfWeightedNewsProfile.size());
		int internalIndex = 0 ;
		for(HashMap<String, Number> sample : NewsJournal.listOfWeightedNewsProfile)
		{
			
			System.out.println(internalIndex);
		if(internalIndex==newsid)
		{
			
			HashMap<String, Number> weighted = sample;
			Set<String> keywords =weighted.keySet();
			System.out.println("Keywords for this news :  " + keywords);
			for(String keyword : keywords)
				{
					System.out.println(keyword);
					CorpusAdapter ca = new CorpusAdapter();
					int id = ca.findKeywordId(keyword);
					if(id == 0 )
					{
						 System.out.println("not found in corpus");
						
					}
						
					else
						{
						System.out.println("news keyword " + keyword + " and its index " + id);
						indexesInGlobalCorpus.add(id);
						}
				}
			
			
			
		}
		else
		{
			System.out.println("Cannot have this id");
		}
		
		internalIndex++;
		
		
	
		}
		
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return indexesInGlobalCorpus;
	}
 

}
