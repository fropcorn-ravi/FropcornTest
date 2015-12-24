package TestSuite;

import java.awt.Color;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.lowagie.text.BadElementException;
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Image;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

/**
 * JyperionListener
 * 
 * @author janaudy at jyperion dot org
 */
public class JListeners implements ITestListener {
	/**
	 * Document
	 */
	
	//private String file = System.getProperty("user.dir")+"\\"+"screenshot"+(new Random().nextInt())+".png";
	private Document document = null;
	private static String destFile;
	private static String destDir;
	private static String Temp;
	/**
	 * PdfPTables
	 */
	PdfPTable successTable = null, failTable = null;
	
	/**
	 * throwableMap
	 */
	private HashMap<Integer, Throwable> throwableMap = null;
	
	/**
	 * nbExceptions
	 */
	private int nbExceptions = 0;
	
	/**
	 * JyperionListener
	 */
	public JListeners() {
		log("JyperionListener()");
		
		this.document = new Document();
		this.throwableMap = new HashMap<Integer, Throwable>();
	}
	
	/**
	 * @see com.beust.testng.ITestListener#onTestSuccess(com.beust.testng.ITestResult)
	 */
	public void onTestSuccess(ITestResult result)  {
		log("onTestSuccess("+result+")");
		
		try 
		{
			getScreenshot(result, "pass");
		} catch (Exception e) {
			e.printStackTrace();
		}
		String Pass1 = System.getProperty("user.dir")+"\\"+Temp;
		System.out.println(Pass1);
		if (successTable == null) {
			this.successTable = new PdfPTable(new float[]{.3f, .3f, .1f, .5f});
			Paragraph p = new Paragraph("PASSED TESTS", new Font(Font.TIMES_ROMAN, Font.DEFAULTSIZE, Font.BOLD));
			this.successTable.setTotalWidth(50f);
			p.setAlignment(Element.ALIGN_CENTER);
			PdfPCell cell = new PdfPCell(p);
			cell.setColspan(4);
			cell.setBackgroundColor(Color.GREEN);
			this.successTable.addCell(cell);
			
			cell = new PdfPCell(new Paragraph("Class"));
			cell.setBackgroundColor(Color.LIGHT_GRAY);
			this.successTable.addCell(cell);
			cell = new PdfPCell(new Paragraph("Method"));
			cell.setBackgroundColor(Color.LIGHT_GRAY);
			this.successTable.addCell(cell);
			cell = new PdfPCell(new Paragraph("Time (ms)"));
			cell.setBackgroundColor(Color.LIGHT_GRAY);
			this.successTable.addCell(cell);
//			cell = new PdfPCell(new Paragraph("Exception"));
//			cell.setBackgroundColor(Color.LIGHT_GRAY);
//			this.successTable.addCell(cell);
			cell = new PdfPCell(new Paragraph("Screenshot"));
			cell.setBackgroundColor(Color.LIGHT_GRAY);
			this.successTable.addCell(cell);
		}
		
		PdfPCell cell = new PdfPCell(new Paragraph(result.getTestClass().toString()));
		this.successTable.addCell(cell);
		cell = new PdfPCell(new Paragraph(result.getMethod().toString()));
		this.successTable.addCell(cell);
		cell = new PdfPCell(new Paragraph("" + (result.getEndMillis()-result.getStartMillis())));
		this.successTable.addCell(cell);
		Image image=null;
		Throwable throwable = result.getThrowable();
		if (throwable != null) {
			this.throwableMap.put(new Integer(throwable.hashCode()), throwable);
			this.nbExceptions++;
			
			Paragraph excep = new Paragraph(
                    new Chunk(throwable.toString(),
                    new Font(Font.HELVETICA, Font.DEFAULTSIZE, Font.UNDERLINE)).setLocalGoto("" + throwable.hashCode()));
            cell = new PdfPCell(excep);
            this.successTable.addCell(cell);
        } else {
        	try {
    			image = Image.getInstance(Pass1);
    			image.scalePercent(10f);
    			//image.alignment();
    		} catch (BadElementException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		} catch (MalformedURLException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		} catch (IOException e) {
    			// TODO Auto-generated catch block
    			e.printStackTrace();
    		}
        	cell=new PdfPCell();
        	cell.addElement(image);
            this.successTable.addCell(cell);
           
        }		
			
		}
		
	

	/**
	 * @see com.beust.testng.ITestListener#onTestFailure(com.beust.testng.ITestResult)
	 */
	public void onTestFailure(ITestResult result) {
		log("onTestFailure("+result+")");
		
		try 
		{
			getScreenshot(result, "fail");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		String Fail1 = System.getProperty("user.dir")+"\\"+Temp;
		System.out.println(Fail1);
		if (this.failTable == null) {
			this.failTable = new PdfPTable(new float[]{.3f, .3f, .1f, .3f, .5f});
			this.failTable.setTotalWidth(50f);
			Paragraph p = new Paragraph("FAILED TESTS", new Font(Font.TIMES_ROMAN, Font.DEFAULTSIZE, Font.BOLD));
			p.setAlignment(Element.ALIGN_CENTER);
			PdfPCell cell = new PdfPCell(p);
			cell.setColspan(5);
			cell.setBackgroundColor(Color.RED);
			this.failTable.addCell(cell);
			
			cell = new PdfPCell(new Paragraph("Class"));
			cell.setBackgroundColor(Color.LIGHT_GRAY);
			this.failTable.addCell(cell);
			cell = new PdfPCell(new Paragraph("Method"));
			cell.setBackgroundColor(Color.LIGHT_GRAY);
			this.failTable.addCell(cell);
			cell = new PdfPCell(new Paragraph("Time (ms)"));
			cell.setBackgroundColor(Color.LIGHT_GRAY);
			this.failTable.addCell(cell);
			cell = new PdfPCell(new Paragraph("Exception"));
			cell.setBackgroundColor(Color.LIGHT_GRAY);
			this.failTable.addCell(cell);
			cell = new PdfPCell(new Paragraph("Screenshot"));
			cell.setBackgroundColor(Color.LIGHT_GRAY);
			this.failTable.addCell(cell);
		}
		
		PdfPCell cell = new PdfPCell(new Paragraph(result.getTestClass().toString()));
		this.failTable.addCell(cell);
		cell = new PdfPCell(new Paragraph(result.getMethod().toString()));
		this.failTable.addCell(cell);
		cell = new PdfPCell(new Paragraph("" + (result.getEndMillis()-result.getStartMillis())));
		this.failTable.addCell(cell);
		String exception = result.getThrowable() == null ? "No exeception" : result.getThrowable().toString();
		cell = new PdfPCell(new Paragraph(exception));
		this.failTable.addCell(cell);
		Throwable throwable = result.getThrowable();
		Image image = null;
		if (throwable != null) {
			this.throwableMap.put(new Integer(throwable.hashCode()), throwable);
			this.nbExceptions++;
			try {
				image = Image.getInstance(Fail1);
				image.scalePercent(10f);
			} catch (BadElementException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			cell.addElement(image);
			this.failTable.addCell(cell);
		} else {
			this.failTable.addCell(new PdfPCell(new Paragraph("")));
		}
	}

	/**
	 * @see com.beust.testng.ITestListener#onTestSkipped(com.beust.testng.ITestResult)
	 */
	public void onTestSkipped(ITestResult result) {
		log("onTestSkipped("+result+")");
	}

	/**
	 * @see com.beust.testng.ITestListener#onStart(com.beust.testng.ITestContext)
	 */
	public void onStart(ITestContext context) {
		log("onStart("+context+")");
		try {
			PdfWriter.getInstance(this.document, new FileOutputStream("FropcornTest.pdf"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.document.open();
		
		Paragraph p = new Paragraph(context.getName() + " TESTNG RESULTS",
				FontFactory.getFont(FontFactory.HELVETICA, 20, Font.BOLD, new Color(0, 0, 255)));
		
		try {
			this.document.add(p);
			this.document.add(new Paragraph(new Date().toString()));
		} catch (DocumentException e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * @see com.beust.testng.ITestListener#onFinish(com.beust.testng.ITestContext)
	 */
	public void onFinish(ITestContext context) {
		log("onFinish("+context+")");
		
		try {
			if (this.failTable != null) {
				log("Added fail table");
				this.failTable.setSpacingBefore(15f);
				this.document.add(this.failTable);
				this.failTable.setSpacingAfter(15f);
			}
			
			if (this.successTable != null) {
				log("Added success table");
				this.successTable.setSpacingBefore(15f);
				this.document.add(this.successTable);
				this.successTable.setSpacingBefore(15f);
			}
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		
		Paragraph p = new Paragraph("EXCEPTIONS SUMMARY",
				FontFactory.getFont(FontFactory.HELVETICA, 16, Font.BOLD, new Color(255, 0, 0)));
		try {
			this.document.add(p);
		} catch (DocumentException e1) {
			e1.printStackTrace();
		}
		
		Set<Integer> keys = this.throwableMap.keySet();
		
		assert keys.size() == this.nbExceptions;
		
		for(Integer key : keys) {
			Throwable throwable = this.throwableMap.get(key);
			
			Chunk chunk = new Chunk(throwable.toString(),
					FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLD, new Color(255, 0, 0)));
			chunk.setLocalDestination("" + key);
			Paragraph throwTitlePara = new Paragraph(chunk);
			try {
				this.document.add(throwTitlePara);
			} catch (DocumentException e3) {
				e3.printStackTrace();
			}
			
			StackTraceElement[] elems = throwable.getStackTrace();
			for(StackTraceElement ste : elems) {
				Paragraph throwParagraph = new Paragraph(ste.toString());
				try {
					this.document.add(throwParagraph);
				} catch (DocumentException e2) {
					e2.printStackTrace();
				}
			}
		}
		
		this.document.close();
	}
	
	/**
	 * log
	 * @param o
	 */
	public static void log(Object o) {
		//System.out.println("[JyperionListener] " + o);
	}

	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTestStart(ITestResult arg0) {
		// TODO Auto-generated method stub
		
	}
	
	public static void getScreenshot(ITestResult result, String status) throws IOException
	{
		
		destDir = "";
		String passfailMethod = result.getMethod().getRealClass().getSimpleName() + "." + result.getMethod().getMethodName();
		// To capture screenshot
		File scrFile = ((TakesScreenshot) TestConfig.driver).getScreenshotAs(OutputType.FILE);
		DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy__hh_mm_ss"); 
		// If status = fail then set folder name "screenshots/Failures" 
		if (status.equalsIgnoreCase("fail"))
		{
			destDir = "screenshots\\Failures";
		}
		
		// If status = pass then set folder name "screenshots/Success"
		
		else if (status.equalsIgnoreCase("pass"))
		{
			destDir = "screenshots\\Success";
		}
		
		// To create folder to store screenshots
		new File(destDir).mkdirs();
		
		// Set file name with combination of test class name + date time. 
		
		destFile = passfailMethod + "-" + dateFormat.format(new Date()) + ".png";
		
		try {
			Temp = destDir + "\\" + destFile;
			// Store file at destination folder location 
			FileUtils.copyFile(scrFile, new File(Temp)); 
			} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		
	}
	

}
