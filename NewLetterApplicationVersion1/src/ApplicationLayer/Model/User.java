package ApplicationLayer.Model;


import java.util.ArrayList;
//import java.sql.SQLException;
//import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ApplicationLayer.Model.Adapters.CorpusAdapter;
import ApplicationLayer.Model.Adapters.LogAdapter;
//import ApplicationLayer.Model.Adapters.LogAdapter;
import ApplicationLayer.Model.Adapters.UserAdapter;

public class User {

	
	private String username; 
	private String password;
	private int id;
	private String type;
	private String email;



	public Map<String,Number> userprofile;
	public UserAdapter userDbo;
	public NewsLetter newsletter ; 
    public UserAdapter ua;
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	
	public User() {
		userDbo = new UserAdapter();
		newsletter = new NewsLetter();
		userprofile = new HashMap<String,Number>();
		ua = new UserAdapter();
		
		
		
		
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public Map<String, Number> getUserprofile() {
		return userprofile;
	}
	public void setUserprofile(Map<String, Number> userprofile) {
		this.userprofile = userprofile;
	}
	public UserAdapter getUserDbo() {
		return userDbo;
	}
	public void setUserDbo(UserAdapter userDbo) {
		this.userDbo = userDbo;
	}
	public NewsLetter getNewsletter() {
		return newsletter;
	}
	public void setNewsletter(NewsLetter newsletter) {
		this.newsletter = newsletter;
	}
	public void generateUserProfiles()
	{
		//Connect to user interests 
		
		
		
	}

	
	
	public void login()
	{
		try {
//		//TODO : Monisha 
		userDbo.fetchUser(username, password);

		if(userDbo!=null)
		{
			this.id = userDbo.id;
			this.setUsername(userDbo.username);
			this.setPassword(password);
			if(this.id == 0)
			{
				System.out.println("User does not exist !! Please Register");
			}
			else
			{
			createUserProfile();
			NewsLetter n=new NewsLetter();
			
			
			this.newsletter = n.generateNewsLetter(this.id,this.userprofile);
		
			System.out.println("user present with profile: " + this.getUserprofile());
			}
		}
		else
		{
			System.out.println("User not found");
	}
			
//			 username=this.getUsername();
//		     password=this.getPassword();
//		     userDbo.fetchUser(username, password);
//		     
//		       
//		       NewsLetter n1= new NewsLetter(); 
//		      n=n1.generateNewsLetter(id);
//		      return n;
//			
//			
//         NewsLetter nl = new NewsLetter();
//         System.out.println("news is available  " + NewsJournal.listOfNews.size());
//         nl.setListOfNews(NewsJournal.listOfNews);
//         this.newsletter = nl;
//
//		//this.newsletters = 	nl.generateNewsLetter(this.id);
//		
		
	 
		
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	
	
	
	public void signup(String username,String password,String emailAddress)
	{
		try{
		int id = ua.addUser(username,password,emailAddress);
		this.id = id;
		System.out.println("user has been set to " + this.id);
		NewsLetter newsletter= new NewsLetter();
		this.newsletter=newsletter.generateNewsLetter(this.id,this.userprofile);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		
		
	}
	
	
	public void createUserProfile()
	{
		//TODO : Monisha
		HashMap<String,Integer> todays_vocabulary = NewsJournal.vocab;
		LogAdapter logAdpater = new LogAdapter();
		HashMap<String,Integer> log = new HashMap<String,Integer>();
		log = logAdpater.logsOfAUser(this.id);
		System.out.println("users log : " + log);
		System.out.println("todays log : " + todays_vocabulary);
		int corpuscount = NewsJournal.TODAYS_CORPURS_COUNT;
		for(String feature : log.keySet())
		{
			int value = log.get(feature);
			if(todays_vocabulary.containsKey(feature))
			{
				int in_vocab = todays_vocabulary.get(feature);
				int users_past = log.get(feature);
				float val = ((float)Math.min(in_vocab, users_past))/((float)corpuscount);
				
				this.userprofile.put(feature,val);
			}
			
			
		}
		
		
		
	}

	
	
	
	
	
	
	
	
	
	

}
