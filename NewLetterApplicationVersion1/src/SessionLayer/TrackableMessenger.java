package SessionLayer;

import java.util.ArrayList;
import java.util.List;

import com.example.admin.newsbytes.UserLog;

import ApplicationLayer.Model.Log;
import ApplicationLayer.Model.NewsLetter;
import ApplicationLayer.Model.User;
import ApplicationLayer.Model.News;;
public class TrackableMessenger {
	
	
	
	
	public com.example.admin.newsbytes.Messenger handleUserRequest(com.example.admin.newsbytes.Messenger message)
	{
		com.example.admin.newsbytes.Messenger response = new com.example.admin.newsbytes.Messenger();
		
		String typeOfRequest = message.type;
		
		if(typeOfRequest.equalsIgnoreCase("login"))
		{
			
			com.example.admin.newsbytes.User user = (com.example.admin.newsbytes.User)message.object;
			User userEntity = new User();
			userEntity.setUsername(user.userid);
			userEntity.setPassword(user.password);
			userEntity.login();
			System.out.println("user has loggen in " + userEntity.getUsername());
			System.out.println("user has a news letter : " + userEntity.newsletter.getListOfNews().size());
			com.example.admin.newsbytes.Newsletter newsletter = new com.example.admin.newsbytes.Newsletter();
			newsletter.userid = userEntity.getId();
			List<com.example.admin.newsbytes.News> dummuList  = new ArrayList<com.example.admin.newsbytes.News>();
			for(News n : userEntity.newsletter.getListOfNews() )
			{
				//System.out.println("Parsing");
				com.example.admin.newsbytes.News news = new  com.example.admin.newsbytes.News();
				news.headlines = n.getHeadline();
				news.description = n.getDescription();
				dummuList.add(news);
			}
			newsletter.news = dummuList;
			newsletter.listofrecommendedindexes = userEntity.newsletter.getListOfRecommendedIndexes();
			
			response.object = (com.example.admin.newsbytes.Newsletter)newsletter;
			
			return response;
			
			
			
			
			
			
			
		}
		else if(typeOfRequest.equalsIgnoreCase("signup"))
		{
			com.example.admin.newsbytes.User user = (com.example.admin.newsbytes.User)message.object;
			User userEntity = new User();
			 userEntity.signup(user.userid,user.password, user.useremail);
			 com.example.admin.newsbytes.Newsletter newsletter = new com.example.admin.newsbytes.Newsletter();
			newsletter.userid = userEntity.getId();
				List<com.example.admin.newsbytes.News> dummuList  = new ArrayList<com.example.admin.newsbytes.News>();
				for(News n : userEntity.newsletter.getListOfNews() )
				{
					//System.out.println("Parsing");
					com.example.admin.newsbytes.News news = new  com.example.admin.newsbytes.News();
					news.headlines = n.getHeadline();
					news.description = n.getDescription();
					dummuList.add(news);
				}
				newsletter.news = dummuList;
				newsletter.listofrecommendedindexes = userEntity.newsletter.getListOfRecommendedIndexes();
				
				response.object = (com.example.admin.newsbytes.Newsletter)newsletter;
				
				return response;
			
		}
		else if(typeOfRequest.equalsIgnoreCase("log"))
		{
			System.out.println("here");
			com.example.admin.newsbytes.UserLog log = new com.example.admin.newsbytes.UserLog();
			log = (UserLog)message.object;
			Log logEntity = new Log();
			logEntity.setUserId(log.userid);
			logEntity.setNewsId(log.newsid);
			System.out.println("userid : " + log.userid + " liked  news id :" + log.newsid );
			String status = logEntity.storeLog();
			System.out.println(status);
			response.object = (String)status;
			
		}
		else
		{
			System.out.println("invalid request");
		}
		
		
		
	
		
		return response;
		
	}
	
	
//	
//	public NewsLetter helpUserLogin()
//	{
//		
//	}
	
	
	public com.example.admin.newsbytes.Newsletter signUpUser()
	{
		com.example.admin.newsbytes.Newsletter newsletter = new com.example.admin.newsbytes.Newsletter();
		
		
		
		
		return newsletter;
		
	}
	

}
