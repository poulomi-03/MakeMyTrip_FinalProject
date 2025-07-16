package base;
 
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import pages.CabBookingPage;
import pages.GiftCardPage;
import pages.HotelBookingPage;
import utils.ActionUtil;
import utils.ConfigReader;
 
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

	@BeforeClass
	public void DriverSetup() {
		String browser = ConfigReader.get("browser");
		driver = WebDriverSetUp.setupDriver(browser);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        js = (JavascriptExecutor) driver;
		act = new Actions(driver);
		
        cabBookingPage = new CabBookingPage(driver);
        giftCardPage = new GiftCardPage(driver);
        hotelObj = new HotelBookingPage(driver);
        action = new ActionUtil(driver);

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
		System.out.println("---------------------------------------------------------------------------");
	}
	
	@AfterMethod
	public void closingTestCase() {
		System.out.println("---------------------------------------------------------------------------");
		driver.get(baseUrl);
	}
	
	@AfterClass
	public void tearDown() {
		driver.quit();
	}
}