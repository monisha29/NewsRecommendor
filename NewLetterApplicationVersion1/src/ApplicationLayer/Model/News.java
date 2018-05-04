package ApplicationLayer.Model;

import java.util.ArrayList;
import java.util.List;
import ApplicationLayer.Model.Adapters.NewsAdapter;

public class News {



	private int newsId;
	private String headline ; 
	private String description;
	private String imageUrl;
	private String newsUrl;
	private List<String>newsprofile;
	
	public News(){
		newsprofile=new ArrayList<String>();
	}
	public int getNewsId() {
		return newsId;
	}
	public void setNewsId(int newsId) {
		this.newsId = newsId;
	}
	public String getHeadline() {
		return headline;
	}
	public void setHeadline(String headline) {
		this.headline = headline;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public String getNewsUrl() {
		return newsUrl;
	}
	public void setNewsUrl(String newsUrl) {
		this.newsUrl = newsUrl;
	}
	public List<String> getNewsprofile() {
		return newsprofile;
	}
	public void setNewsprofile(List<String> newsprofile) {
		this.newsprofile = newsprofile;
	}
	
	public void makeNewsObject(NewsAdapter adapter)
	{  headline=adapter.headline;
//	   System.out.println(headline); 
	   description=adapter.description;
//	   System.out.println(description);
	   imageUrl=adapter.imageUrl;
//	   System.out.println(imageUrl);
	   newsUrl=adapter.newsUrl;
//	   System.out.println(newsUrl);


	   String[] keys = adapter.keywords.split(",");
	   
	   
	   
	   for(String s:keys)
	   { 
		  newsprofile.add(s);
	   }
	   setNewsprofile(newsprofile);
	    
	}
	 
	
	public void createNewsProfile()
	{
//		//TODO : Deepti
//		
//		NewsAdapter adp = new NewsAdapter();
//		try {
//			Map<String, Number> val = adp.fetchNewsProfile(this.id);
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
	}
		
		
	}
