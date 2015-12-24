package TestSuite;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;

public class driveAPK {
	public static void main(String[] args)
	{
		try {
			//printCurrentDataAndTime();
			downloadFile();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
  public static void printCurrentDataAndTime()
  {
	  DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	  Date date = new Date();	
	  System.out.println(dateFormat.format(date));

  }
	
  public static void downloadFile() throws MalformedURLException, IOException {
	printCurrentDataAndTime();
	FileUtils.copyURLToFile(new URL("https://drive.google.com/uc?export=download&id=0B_eBKEzF7DNtaEQzcUtkcXFfXzg"), new File("C:\\Users\\FC\\workspace\\FropcornTest\\bin\\TestSuite\\fropcorn.apk"));
	System.out.println("Apk Updated");
	//System.out.println(dateFormat.format(date));
  }
}