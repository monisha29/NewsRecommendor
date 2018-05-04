package ApplicationLayer;

import ApplicationLayer.Model.NewsJournal;
import TransportLayer.TransportHandle;


public class Controller {

	
	//load journal
	public static void main(String[] args)
	{
		
	
	NewsJournal nj = new NewsJournal();
	
	//nj.updateCorpus();
	
	nj.refresh();
	
	//reduce weight of all keywords from global corpus
	
    nj.loadTodaysJournal();
    
    //update corpus
    
//    
//    System.out.println("News Loaded Today  : " + nj.listOfNews.size());
    
    TransportHandle.startServer();
	
	}
	//start server
	
	
	
	
}
