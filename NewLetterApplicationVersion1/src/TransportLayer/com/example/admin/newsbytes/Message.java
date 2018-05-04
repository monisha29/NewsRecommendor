package TransportLayer.com.example.admin.newsbytes;
import java.io.Serializable;
import java.util.HashMap;

	public class Message implements Serializable {
	private static final long serialVersionUID = 20150901L;
	public HashMap<String,Object> header;
	public Object body;
	
	
	
	}