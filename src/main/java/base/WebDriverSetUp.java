package base;
 
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
 
public class WebDriverSetUp {
public static WebDriver driver;
	public static WebDriver setupDriver(String browser) {
		System.out.println("STEP 1: Attempting to open the "+browser+" browser.");
    	try {
            if (browser.equalsIgnoreCase("chrome")) {
                driver = new ChromeDriver();
            } else if (browser.equalsIgnoreCase("edge")) {
                driver = new EdgeDriver();
            } else if (browser.equalsIgnoreCase("firefox")) {
	            driver = new FirefoxDriver();
	        }
    		System.out.println("Remarks: "+browser+" browser opened successfully");
    	}
    	catch(Exception e){
    		System.out.println("Remarks: Faied to open "+browser+" browser.\n"+e);
    	}
		return driver;
	}
}