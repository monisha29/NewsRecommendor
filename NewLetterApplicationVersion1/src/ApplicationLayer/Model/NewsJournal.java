package ApplicationLayer.Model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ApplicationLayer.Model.Adapters.CorpusAdapter;
import ApplicationLayer.Model.Adapters.NewsAdapter;

public class NewsJournal {

	
	public static  HashMap<String,Integer> vocab;
	public static  HashMap<String,Float> normalizedvocab;	
	public static  int TODAYS_CORPURS_COUNT = 23;
	public static  List<News> listOfNews ;
	public static  ArrayList<HashMap<String, Number>> listOfWeightedNewsProfile ; 
	public  List<String> corpus;
	public NewsJournal() {
		listOfNews = new ArrayList<News>();
		corpus=new ArrayList<String>();
		listOfWeightedNewsProfile = new ArrayList<>();
		vocab=new HashMap<String,Integer>();
		normalizedvocab=new HashMap<String,Float>();
		//dummy code to be removed
		
		
	}
	
	
	public static void getInstance()
	{
		if(listOfWeightedNewsProfile==null)
		{
			loadTodaysJournal();
		}
		
	}
	

	
	public static List<com.example.admin.newsbytes.News> sendList()
	{
		List<com.example.admin.newsbytes.News> ans = new ArrayList<com.example.admin.newsbytes.News>();
		for(News n : listOfNews)
		{
			com.example.admin.newsbytes.News a = new com.example.admin.newsbytes.News();
			a.newsId = 256;
			a.headlines = n.getHeadline();
			a.description = n.getDescription();
			ans.add(a);
			
		}
		
		return ans;
	}
	

	public List<News> getListOfNews() {
		return listOfNews;
	}
	@SuppressWarnings("static-access")
	public void setListOfNews(List<News> listOfNews) {
		this.listOfNews = listOfNews;
	} 
	
	public void refresh()
	{
		//TODO : Deepti : Delete all yesterdays news load Todays news
		
		//Clear table
		NewsAdapter deladapter= new NewsAdapter();
		deladapter.deleteNews();
		//load todays news
		
		
		//TODO : Monisha : Update User Interest Values
	}
	
	public static void loadTodaysJournal() 
	{
		 /* TODO : Deepti get news from api*/
		try{
		String pythonScriptPath = "C:/Python34/newsfetcher1.py";
		String[] cmd = new String[2];
		cmd[0] = "python "; 
		cmd[1] = pythonScriptPath;

		Runtime rt = Runtime.getRuntime();
		try {
			Process pr = rt.exec(cmd);
		
		
		
		 BufferedReader bfr = new BufferedReader(new InputStreamReader(pr.getInputStream()));
		 String line = "";
		 while((line = bfr.readLine()) != null) {
		 System.out.println(line);
		 }
		 
		 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		//creating vocab and term-fraction hashmap
		NewsAdapter adapter= new NewsAdapter();
		List<NewsAdapter> listOfAdapter= new ArrayList<NewsAdapter>();		
		try {
			listOfAdapter=adapter.fetchAllNews();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	

		for(NewsAdapter n :listOfAdapter)
		{  
		   News news = new News();
		   news.makeNewsObject(n);

		   System.out.println(news.getDescription());
		   listOfNews.add(news); 
		  
		}
		ArrayList<ArrayList<String>> listOfAllNewsProfiles= new ArrayList<>();
		for(int i=0;i<listOfNews.size();i++)
		{   			
			listOfAllNewsProfiles.add((ArrayList<String>) listOfNews.get(i).getNewsprofile());
		}
		for(ArrayList<String> p : listOfAllNewsProfiles){
			for(String s:p){
				int count = vocab.containsKey(s) ? vocab.get(s) : 0;
				vocab.put(s, count + 1);
			}
		}		

		for (String k : vocab.keySet()) {
			normalizedvocab.put(k, (float)vocab.get(k)/vocab.size());
		}
		listOfWeightedNewsProfile = adapter.fetchWeightedNewsProfile();
 	
		
		
		//transfer vocab to tempCorpus
		CorpusAdapter c = new CorpusAdapter();
		//System.out.println(vocab.keySet());
		c.loadCorpus((vocab));
//			
			/*TODO : Sonali get news from webScrapper
			 * TODO : Deepti , Sonali : calculate tf-idf and store in database
			 * 
			 * 
		}
			 */
		
		}
		catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
			
	}
	
	
	
	
	
	

	
	public void updateCorpus(){
		CorpusAdapter c = new CorpusAdapter();
		c.transferCorpus();
		
		
	}
}
