package TestSuite;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;
import io.appium.java_client.service.local.AppiumServiceBuilder;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.validator.routines.InetAddressValidator;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;


@Listeners(JListeners.class)

public class TestConfig{

	static AppiumDriver driver;
	static AppiumDriverLocalService server;
	{
	
	}
	
	public static AppiumDriver<WebElement> getDriver() {
		return driver;
	}
			
	@BeforeTest
	public static void startAppium() throws RuntimeException,IOException
	{
		try
		{
		server = AppiumDriverLocalService.buildService(new AppiumServiceBuilder()
		.usingDriverExecutable(new File("C:\\Program Files\\nodejs\\node.exe"))
		.withAppiumJS(new File("C:\\Program Files\\Appium\\node_modules\\appium\\bin\\appium.js"))
		.withIPAddress("127.0.0.1")
		.usingAnyFreePort());
		server.start();
		System.out.println(server.isRunning());
		if (server == null || !server.isRunning())
		      throw new RuntimeException("An appium server node is not started!");
		//System.out.println(System.getProperty("user.dir"));
		//File classpathRoot = new File(System.getProperty("user.dir"));
		driveAPK.downloadFile();
		System.out.println("APK Downloaded");
		File appDir = new File("C:\\Users\\FC\\workspace\\FropcornTest\\bin\\TestSuite");
		File app = new File(appDir, "fropcorn.apk");
		System.out.println("Found APK");
	    DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
	    desiredCapabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "Appium");
	    desiredCapabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
	    desiredCapabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "a01b27a8");
	    desiredCapabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "5.1.1");
	    desiredCapabilities.setCapability(MobileCapabilityType.APP, app.getAbsolutePath());
	    desiredCapabilities.setCapability(MobileCapabilityType.APP_PACKAGE, "com.fropcorn.www");
	    //desiredCapabilities.setCapability(MobileCapabilityType.APP_ACTIVITY, ".SplashScreen");
	    //desiredCapabilities.setCapability("appiumVersion", "1.3.4");
	    driver = new AppiumDriver(server, desiredCapabilities) {

			@Override
			public WebElement scrollTo(String arg0) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public WebElement scrollToExact(String arg0) {
				// TODO Auto-generated method stub
				return null;
			}
		};
	  		{
		};
		
		driver.manage().timeouts().implicitlyWait(100, TimeUnit.SECONDS);
	}
	catch (SessionNotCreatedException e3)
		{
		e3.printStackTrace();
		}
	}
		
	      
	@AfterTest
	  public void tear()
	  {
	    server.stop();
	    
	  }
	
	@AfterMethod
	@AfterSuite
	public void tearDown(){
 
       Email.Testmail();
 
        }
	
	@Test
	public static void Registration() throws InterruptedException
	{
		System.out.println("TestCase: Registration");
		try
		{
			Thread.sleep(5000);
			WebElement mobile = driver.findElementById("com.fropcorn.www:id/mobile");
			if (mobile.isEnabled())
			{
				driver.hideKeyboard();
				mobile.sendKeys("9550205474");
				driver.findElementById("com.fropcorn.www:id/email").sendKeys("ravi@ravi.com");
				driver.findElementById("com.fropcorn.www:id/submit").click();
				Thread.sleep(5000);
				WebElement go = driver.findElementByXPath("//android.widget.Button[@text='GO']");
				go.click();
				System.out.println("Registration Sucessfull");
				//driver.closeApp();
				
				
			} 	
		}
	    catch(NoSuchElementException e)
	    {
	    	e.printStackTrace();
	    
	    }
		
	}
	@Test(enabled=false)
	public static void testConn() throws InterruptedException {
		driver.launchApp();
    	try
    	{
	    	Thread.sleep(8000);
			System.out.println("Registered Already");
			driver.findElementByXPath("//android.widget.Button[@text='GO']").click();
			System.out.println("Connection Established");
			
    	}
    	catch(NoSuchElementException e1)
    	{
	    	e1.printStackTrace();
	    	Thread.sleep(5000);
	    	driver.findElementByXPath("//android.widget.Button[@text='Retry']").click();
	    	Thread.sleep(5000);
			System.out.println("Connection Not Established");
		
			
    	}
	}
	
	
	
	@Test(enabled = false)	
	public static void getFilters() throws InterruptedException
	{
	try {
	System.out.println("Test Case: Filters");
	Thread.sleep(5000);
	//driver.findElementByXPath("//android.widget.Button[@text='GO']").click();
	driver.findElementByClassName("android.widget.ImageButton").click();
	Thread.sleep(2500);
	driver.findElementByXPath("//android.widget.TextView[@text='Movies']").click();
	Thread.sleep(2500);
	driver.findElementByXPath("//android.widget.TextView[@text='Filters']").click();
	System.out.println("Finding Text:");
	Thread.sleep(2500);
	//List<WebElement> Filters = driver.findElementsByXPath("android.widget.TextView");
	//List<WebElement> Languages = Filters.
	//List<WebElement> languagesList = driver.findElementsById("com.fropcorn.www:id/language_list");
	
	List<WebElement> genreList = driver.findElementById("com.fropcorn.www:id/genre_list").findElements(By.className("android.widget.TextView")); 	
	List<WebElement> languageList = driver.findElementById("com.fropcorn.www:id/language_list").findElements(By.className("android.widget.TextView")); 	
	
	for(int i=0;i<genreList.size();i++)
	{
		for(int j=0;j<languageList.size();j++){
			System.out.println(genreList.get(i).getText()+"   "+languageList.get(j).getText() );
			genreList.get(i).click();
			languageList.get(j).click();
			Thread.sleep(2500);
			driver.findElementByXPath("//android.widget.TextView[@text='New']").click();
			Thread.sleep(2500);
			driver.findElementByXPath("//android.widget.TextView[@text='Filters']").click();
			genreList.get(i).click();
			languageList.get(j).click();
			
		}
		
		
	}
	
	}
	
	catch (NoSuchElementException e2)
	{
		e2.printStackTrace();
	}
	}

public static void main(String[] args) throws InterruptedException
{

Registration();
testConn();
getFilters();
	
}



}

	
