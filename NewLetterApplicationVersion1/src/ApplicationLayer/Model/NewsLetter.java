package ApplicationLayer.Model;



import java.sql.SQLException;
import java.util.*;

import ApplicationLayer.HelperFunctions;
import ApplicationLayer.Model.Adapters.LogAdapter;
import ApplicationLayer.Model.Adapters.NewsAdapter;


public class NewsLetter{
	
	private  List<News> listOfNews ;

	public List<Integer> getListOfRecommendedIndexes() {
		return listOfRecommendedIndexes;
	}

	public void setListOfRecommendedIndexes(List<Integer> listOfRecommendedIndexes) {
		this.listOfRecommendedIndexes = listOfRecommendedIndexes;
	}



	private List<Integer> listOfRecommendedIndexes;
	
//	private String typeOfNewsLetter;
	
	public List<News> getListOfNews() {
		return listOfNews;
	}

	public void setListOfNews(List<News> listOfNews) {
		this.listOfNews = listOfNews;
	}

//	public String getTypeOfNewsLetter() {
//		return typeOfNewsLetter;
//	}
//
//	public void setTypeOfNewsLetter(String typeOfNewsLetter) {
//		this.typeOfNewsLetter = typeOfNewsLetter;
//	}

	public NewsLetter() {
		listOfNews = new ArrayList<News>();
	}
	
	
	
	public NewsLetter generateNewsLetter(int userid,Map<String,Number> userprofile) throws SQLException
	{
//		
//		HashMap<String,List<News>> listOfNewsLetters = new HashMap<String,List<News>>();
//		
//		LogAdapter la = new LogAdapter();
//		listOfNewsLetters.put("general",listOfNews);
//		if(la.userExistsWithLogs(userid))
//		{
//			//send recommended + general news
//			List<News> recommendations = new ArrayList<News>();
//		
//			listOfNewsLetters.put("recommendations",recommendations);
//			
//			
//		}
	
		//java.util.List<ArrayList<String>> wnp= new ArrayList<>();
//			 wnp=na.fetchNewsProfile();
			
	        
			
			ApplicationLayer.HelperFunctions helperFunctions = new HelperFunctions();
			List<Number>l=helperFunctions.calculateCosineSimilarity( userprofile,  NewsJournal.listOfWeightedNewsProfile);
			System.out.println("list is " + NewsJournal.listOfWeightedNewsProfile.size());
			List<Integer> index= new ArrayList<>();
			for( int i=0;i<l.size();i++)
			{
				double n= (double)l.get(i);
				double thresh = 0.0;
				if(n>0.0)
				{
					System.out.println(i);
				    index.add(i);
				}
			
			}
			this.listOfRecommendedIndexes=index;
			this.listOfNews=NewsJournal.listOfNews;
			return this;
		
	
		
		//return listOfNewsLetters;
	
		
	}
	
	
	
	
	
	
}
