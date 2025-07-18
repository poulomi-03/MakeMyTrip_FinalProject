package base;
 
import java.io.IOException;
import java.net.URL;
import java.time.Duration;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;

import com.aventstack.extentreports.ExtentReports;

import pages.CabBookingPage;
import pages.GiftCardPage;
import pages.HotelBookingPage;
import utils.ActionUtil;
import utils.ConfigReader;
import utils.ExtentManager;
import utils.JavascriptExecutorUtil;
 
public class BaseTest {
	public WebDriver driver;
	public String baseUrl;
	
	public static WebDriverWait wait;
	public static ActionUtil action;
	public static JavascriptExecutorUtil jsUtil;
	
	public CabBookingPage cabBookingPage;
	public GiftCardPage giftCardPage;
	public HotelBookingPage hotelObj;
    public ExtentReports extent;
    public Logger logger;
	
	
	@BeforeClass
	@Parameters({"os","browser"})
	public void setup(String os,String br) throws IOException {
		
		logger = LogManager.getLogger(this.getClass());
		
		if(ConfigReader.get("execution_env").equalsIgnoreCase("remote")) {
			DesiredCapabilities capabilities = new DesiredCapabilities();
			
			if(os.equalsIgnoreCase("windows")) {
				capabilities.setPlatform(Platform.WIN11);
			}else if(os.equalsIgnoreCase("mac")) {
				capabilities.setPlatform(Platform.MAC);
			}else {
				System.out.println("No matching os");
				return;
			}
			
			switch(br.toLowerCase()) {
			case "chrome": 
				capabilities.setBrowserName("chrome");
				break;
			case "edge":
				capabilities.setBrowserName("MicrosoftEdge");
				break;
			default: System.out.println("Invalid browser name"); return;
			}
			
			driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), capabilities);
		}
		
		if(ConfigReader.get("execution_env").equalsIgnoreCase("local")) {
			driver = WebDriverSetUp.setupDriver(br);
		}
		
		driver.manage().deleteAllCookies();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
		
        wait = new WebDriverWait(driver, Duration.ofSeconds(2));
		
        action = new ActionUtil(driver);
        extent = ExtentManager.getInstance();
        jsUtil = new JavascriptExecutorUtil(driver);

        // Go to MakeMyTrip Home Page
		baseUrl = ConfigReader.get("baseUrl");
		driver.get(baseUrl); // Open MakemyTrip Home page.
        String currentUrl = driver.getCurrentUrl();
        
        // Validate
        if (!currentUrl.equals(baseUrl)) {
            System.out.println("Remarks: Failed to navigate to "+baseUrl+"Current URL is: "+currentUrl);
        }
        else {
            System.out.println("Remarks: Successfully navigated to "+ baseUrl);        	
        }
        driver.findElement(By.xpath("//span[@data-cy='closeModal']")).click(); // Close popup
	}
 
	@BeforeMethod
	public void goToHomePage() {
        cabBookingPage = new CabBookingPage(driver);
        giftCardPage = new GiftCardPage(driver);
        hotelObj = new HotelBookingPage(driver);
	}
	
	@AfterMethod
	public void closingTestCase() {
		driver.get(baseUrl);
	}
	
	@AfterClass
	public void tearDown() {
		driver.quit();
	}
	
    @AfterSuite
    public void flushExtentReport() {
        extent.flush();
    }

	public String randomString(){
		String generatedString=RandomStringUtils.randomAlphabetic(10);
		return generatedString;
	}
}