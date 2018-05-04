package ApplicationLayer.Model;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ApplicationLayer.HelperFunctions;
import ApplicationLayer.Model.Adapters.NewsAdapter;
import ApplicationLayer.Model.Adapters.UserAdapter;
import TransportLayer.TransportHandle;

//import ApplicationLayer.Model.Adapters.LogAdapter;

//import ApplicationLayer.Model.Adapters.LogAdapter;

public class Tester {

	public static void main(String[] args) throws SQLException {
//		
//		try
//		{
//		NewsJournal nj = new NewsJournal();
//	
//			nj.loadNewsLetter();
//			NewsAdapter a = new NewsAdapter();
//			System.out.println("listOfWeightedUserProfile:");
//			System.out.println(a.fetchNewsProfile());
//			List<Map<String, Number>> fnp= a.fetchNewsProfile();
//			System.out.println(fnp.size());
//			
//			//User u = new User();
//			//u.register("abc", "abc", "abc@gmail.com");
//			//	NewsLetter a = new NewsLetter();
//			//	a.loadNewsLetter();
//			User u = new User();
//			u.login("monisha29","monisha2992");
//			u.createUserProfile();
//			System.out.println(u.userprofile);
//			Map<String,Number> up = new HashMap<String, Number>();
//			up=u.userprofile;
//			HelperFunctions hf = new HelperFunctions();		
//			ArrayList<Number>  result = hf.calculateCosineSimilarity(up,fnp);
//			int counter =0;
//			for( int i =0;i<result.size();i++){
//				if (result.get(i).doubleValue() > 0){
//					System.out.println("index of news:"+i);
//					counter++;
//				}
//			}
//			System.out.println(counter);
//		}
//		catch(Exception e)
//		{
//			e.printStackTrace();
//		}
		
	//	TransportHandle.startServer();
//		//test for login
//		System.out.println("userProfile");
//		User u = new User();
//		u.login("monisha29","monisha2992");
//		u.createUserProfile();
//		System.out.println(u.userprofile);
////		//unit test
//		//LogAdapter la = new LogAdapter();
////		System.out.println(la.userExistsWithLogs(2));
//		//la.logsOfAUser(1);
		
		
//		User newUser = new User();
//		newUser.setPassword("monisha2992");
//		newUser.setUsername("monishaksaldld");
//		newUser.setEmail("monisha");
//		
		UserAdapter ua = new UserAdapter();
		ua.addUser("dpt", "dptsdpt", "avkldfjd");
	//	NewsAdapter na = new NewsAdapter();
	//	na.fetchAllNews();
		
		

	}

}
