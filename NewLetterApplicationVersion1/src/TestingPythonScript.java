import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class TestingPythonScript {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		String pythonScriptPath = "C:/Python34/newsfetcher.py";
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
		 
	}

}
