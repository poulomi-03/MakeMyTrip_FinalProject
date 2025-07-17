package base;
 
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

import pages.CabBookingPage;
import pages.GiftCardPage;
import pages.HotelBookingPage;
import utils.ActionUtil;
import utils.ConfigReader;
import utils.ExtentManager;
 
public class BaseTest {
	public WebDriver driver;
	public String baseUrl;
	
	public static WebDriverWait wait;
	public static JavascriptExecutor js;
	public static Actions act;
	public static ActionUtil action;
	
	public CabBookingPage cabBookingPage;
	public GiftCardPage giftCardPage;
	public HotelBookingPage hotelObj;
	
    public ExtentReports extent;

	@BeforeClass
	@Parameters({"browser"} )
	public void DriverSetup(@Optional("chrome") String browser) {
		driver = WebDriverSetUp.setupDriver(browser);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
		
        wait = new WebDriverWait(driver, Duration.ofSeconds(2));
        js = (JavascriptExecutor) driver;
		act = new Actions(driver);
		
        action = new ActionUtil(driver);
        extent = ExtentManager.getInstance();

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
}