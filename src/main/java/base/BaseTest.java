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
 
import com.aventstack.extentreports.ExtentReports;
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
	public void DriverSetup() {
		String browser = ConfigReader.get("browser");
		driver = WebDriverSetUp.setupDriver(browser);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        js = (JavascriptExecutor) driver;
		act = new Actions(driver);
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
        cabBookingPage = new CabBookingPage(driver);
        giftCardPage = new GiftCardPage(driver);
        hotelObj = new HotelBookingPage(driver);
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
 
    @BeforeSuite
    public void setupExtentReport() {
        extent = ExtentManager.getInstance();
    }
    @AfterSuite
    public void flushExtentReport() {
        extent.flush();
    }
}