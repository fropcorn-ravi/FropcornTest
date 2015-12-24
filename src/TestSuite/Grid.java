package TestSuite;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Grid {
	public static void main(String[] args)
	{
	try {
		Hub();
	}
	catch (IOException e) 
	{
		e.printStackTrace();
	}
	catch (InterruptedException e)
	{
		e.printStackTrace();
	}
			
	}
	
	public static void Hub() throws IOException, InterruptedException
	{
		//String path = "cmd /c start C:\\Users\FC\\workspace\\FropcornTest\\bin\\TestSuite\\Hub.bat "
		//Process p = Runtime.getRuntime().exec(new String[]{"cmd.exe", "/c", "C:\\Users\\FC\\workspace\\FropcornTest\\bin\\TestSuite\\Hub.bat"});
//		p.waitFor();
//		BufferedReader reader = new BufferedReader(new InputStreamReader(
//			    p.getInputStream()));
//			    String line = reader.readLine();
//			    while (line != null) {
//			        System.out.println(line);
//			        line = reader.readLine();
		
		String cmd = "C:\\Users\\FC\\workspace\\FropcornTest\\bin\\TestSuite\\Hub.bat";
		Runtime r = Runtime.getRuntime();
		Process pr = r.exec(cmd);

		BufferedReader stdInput = new BufferedReader(
		    new InputStreamReader( pr.getInputStream() ));

		String s ;
		while ((s = stdInput.readLine()) != null) {
		    System.out.println(s);
		
	}

}
	
}
