package TransportLayer;

//import java.io.DataInputStream;
//import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

//import com.example.admin.testing.Tmessage;

import TransportLayer.com.example.admin.newsbytes.Message;
//import TransportLayer.com.example.admin.newsbytes.Messenger;

import com.example.admin.newsbytes.Messenger;
import com.example.admin.newsbytes.News;
import com.example.admin.newsbytes.Newsletter;
import com.example.admin.newsbytes.TMessage;
import com.example.admin.newsbytes.UserLog;

import ApplicationLayer.Controller;
import ApplicationLayer.Model.NewsJournal;
import SessionLayer.TrackableMessenger;


public class TransportHandle extends Thread{

	TrackableMessenger messenger ;
	private static ServerSocket ss;
	private static Socket s ; 
	private static ObjectInputStream ois ;
	@SuppressWarnings("unused")
	private static ObjectOutputStream dos ;
	
	static
	{
		try {
			ss = new ServerSocket(5011);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public TransportHandle(Socket socket) throws IOException
	{
        // obtaining input and out streams
		s = socket;		
		ois = new ObjectInputStream(s.getInputStream());
        dos = new ObjectOutputStream(s.getOutputStream());

        messenger = new TrackableMessenger();
        
        // dos.write(data);
        communicate();
	}
	
	public static void startServer()
	{
		System.out.println("Server Started");
		
		while (true) 
		{
		 
		    try
		    {
		    Socket socket = null;
		    socket = ss.accept();
		    System.out.println("A new client is connected : " + socket);
		    Thread t = new TransportHandle(socket);
		    t.start();
			s.close();
		    }
		    catch (Exception e){
		        try {
					s.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		        e.printStackTrace();
		    }
		}
	}
	
	
	public void incomingTest() throws IOException
	  { 
		try{
		 com.example.admin.newsbytes.Messenger message = new com.example.admin.newsbytes.Messenger();
		  message =(com.example.admin.newsbytes.Messenger) ois.readObject();
		  System.out.println(message.type);
		  
		  if(message.type.equalsIgnoreCase("User Information"))
		  {
			  //verify user and return corresponding newsletter
			  
			  List<News> listOfNews = NewsJournal.sendList();
			  System.out.println(listOfNews.get(0).description);
			  com.example.admin.newsbytes.Newsletter nl= new com.example.admin.newsbytes.Newsletter();
              for(News n : listOfNews)
              	nl.news.add(n);
              nl.userid = 100;
              nl.listofrecommendedindexes.add(3);
              nl.listofrecommendedindexes.add(4);
              System.out.println("sent");
              Messenger msg = new Messenger();
              msg.object = (Newsletter)nl;
              msg.header="1233";
              dos.writeObject(msg);
              
		  }
		  else if(message.type.equalsIgnoreCase("Log Information"))
		  {
			UserLog log = (UserLog)message.object;
			System.out.println(log.newsid);
			System.out.println(log.userid);
			  //
		  }
		 
          
          
      
	  
		 }catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
     }
	
	
	
	public void communicate() throws IOException
	  { 
		try{
		 com.example.admin.newsbytes.Messenger message = new com.example.admin.newsbytes.Messenger();
		  message =(com.example.admin.newsbytes.Messenger) ois.readObject();
		  com.example.admin.newsbytes.Messenger response =   messenger.handleUserRequest(message);
          dos.writeObject(response);

		 }catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
   }
	
	
	
	
	public void incoming() throws IOException
	  { 
		  TMessage m = new TMessage();
		// ois = new ObjectInputStream(s.getInputStream());
		 try {
        while(s.isConnected()){
      	  System.out.println("here");
            Object aux;
			
				aux = ois.readObject();
				System.out.println(aux);
			
            if(aux instanceof TMessage){
                m = (TMessage)aux;
                //System.out.print("Data "+m.TYPE+m.ID+m.DATA);
                System.out.println("username  " + m.username);
                System.out.println("passowrd "+ m.password);
            
                //TMessage objm = new TMessage();
                List<News> listOfNews = NewsJournal.sendList();
                Newsletter nl= new Newsletter();
                for(News n : listOfNews)
                	nl.news.add(n);
                
                
//                News one = new News();
////                one.headlines="deepti konduri";
////                one.description = "from gitam vizag";
//                News two = new News();
//                two.headlines="sonali sharma";
//                two.description = "future actress from kachnar city outer jec";
//                nl.news.add(one);
//                nl.news.add(two);
//				  objm.newsprofile=[n1,n2];
//				  System.out.println(objm.newsletter);
//                System.out.print(objm.TYPE+" "+objm.ID+" "+objm.username+objm.password);
				  System.out.println();
      		  //ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
      		  dos.writeObject(nl);
               
                //s.close();
                
            }
            }
        
	  
		 }catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
       }

	
	
	
	
//	public static void main(String[] args) {
//		// TODO Auto-generated method stub
//		try {
//			while(true){
//			//System.out.println("enter");
//			 ss = new ServerSocket(5011);
//		      s = ss.accept();
//              
//              System.out.println("A new client is connected : " + s);
//     
//             // dos.write(data);
//              byte[] data2 = new byte[1024];
//      	       dis.read(data2);
//      	     Message2 m = new Message2();
//      	     m = deserialize(data2);
//               
//              System.out.println("Assigning new thread for this client");
//              Message2 message = new Message2();
//              message.body = "NewsApp";
//              byte[] data = serialize(message);
//              dos.write(data);
////			System.out.println("request recieve");
//			
//			
//			//System.out.println("working");
//
//			//Scanner scan = new Scanner(System.in);
//			
//			// out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(s.getOutputStream())));
//			//out.println(str);
//
//		//	p.close();
//			s.close();
//			ss.close();
//			}
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}

	
	
	@SuppressWarnings("unused")
	private static Message2 deserialize(byte[] data)
	{
		//System.out.println("here :" + data);
		Message2 msg = new Message2();
		String covert = new String(data);
		System.out.println(covert);
		msg.body = new String(covert);
		return msg;
	}
	
	 public static byte[] serialize(Message2 msg)
	    {
	        String change = msg.body.toString();
	        System.out.println(" Server sends  " + change);
	        return change.getBytes();
	    }
}
 class Message2 {
String body;
}
