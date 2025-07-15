package base;
 
import java.time.Duration;
 
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;
 
import pages.CabBookingPage;
 
public class BaseTest {
	public static WebDriver driver;
	public static WebDriverWait wait;
	public static JavascriptExecutor js;
	public static Actions act;
	public static CabBookingPage cabBookingPage;
	@BeforeClass
	public void DriverSetup() {
		driver = WebDriverSetUp.setupDriver("chrome");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        js = (JavascriptExecutor) driver;
		act = new Actions(driver);
        cabBookingPage = new CabBookingPage(driver);
        // Go to MakeMyTrip Home Page
		String baseUrl = "https://www.makemytrip.com/";
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
 
	@AfterMethod
	public void goToHomePage() {
		System.out.println("---------------------------------------------------------------------------");
		driver.findElement(By.xpath("//a[contains(@class,'mmtLogo') or contains(@class,'chMmtLogo')]")).click();
	}
//	@AfterMethod
//	public void closingTestCase() {
//		System.out.println("---------------------------------------------------------------------------");
//	}
	@AfterClass
	public void tearDown() {
		driver.quit();
	}
}