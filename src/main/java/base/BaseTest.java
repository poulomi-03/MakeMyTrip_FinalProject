package base;
 
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestContext;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import pages.CabBookingPage;
import pages.GiftCardPage;
import pages.HotelBookingPage;
import utils.ActionUtil;
import utils.ConfigReader;
import utils.JavascriptExecutorUtil;

@Listeners(utils.ExtentReportManager.class)
public class BaseTest {
	public static WebDriver driver;
	public String baseUrl;
	
//	public static WebDriverWait wait;
	public static ActionUtil action;
	public static JavascriptExecutorUtil jsUtil;
	
	public CabBookingPage cabBookingPage;
	public GiftCardPage giftCardPage;
	public HotelBookingPage hotelObj;
    public Logger logger;
    
    public int impWait = 5;
    public int expWait = 3;
	
	@BeforeClass(groups= {"Smoke", "Regression"})
	@Parameters({"os","browser"})
	public void setup(@Optional("windows") String os, @Optional("chrome") String br) throws IOException {
		
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
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(impWait));
//        wait = new WebDriverWait(driver, Duration.ofSeconds(expWait));
		
        action = new ActionUtil(driver);
        jsUtil = new JavascriptExecutorUtil(driver);

        // Go to MakeMyTrip Home Page
		baseUrl = ConfigReader.get("baseUrl");
		driver.get(baseUrl); // Open MakemyTrip Home page.
        String currentUrl = driver.getCurrentUrl();
        
        // Validate
        if (!currentUrl.equals(baseUrl)) {
            System.out.println("Remarks: Failed to navigate to "+baseUrl+"Current URL is: "+currentUrl);
            return;
        }
        driver.findElement(By.xpath("//span[@data-cy='closeModal']")).click(); // Close popup
	}
 
	@BeforeMethod(groups= {"Smoke", "Regression"})
	public void goToHomePage() {
        cabBookingPage = new CabBookingPage(driver);
        giftCardPage = new GiftCardPage(driver);
        hotelObj = new HotelBookingPage(driver);
	}
	
	@AfterMethod(groups= {"Smoke", "Regression"})
	public void closingTestCase(ITestContext context) {
        context.removeAttribute("message");
        context.removeAttribute("screenshot");
		driver.get(baseUrl);
	}
	
	@AfterClass(groups= {"Smoke", "Regression"})
	public void tearDown() {
		driver.quit();
	}
	

	public String randomString(){
		String generatedString=RandomStringUtils.randomAlphabetic(10);
		return generatedString;
	}
	
	public static String captureScreen(String folder, String tname) throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());

        TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
        File sourceFile = takesScreenshot.getScreenshotAs(OutputType.FILE);

        String directoryPath = System.getProperty("user.dir") + "\\screenshots\\"+folder+"\\";
        File directory = new File(directoryPath);

        // Create directory if it doesn't exist
        if (!directory.exists()) {
            directory.mkdirs();
        }

        String targetFilePath = directoryPath + tname + "_" + timeStamp + ".png";
        File targetFile = new File(targetFilePath);

        sourceFile.renameTo(targetFile);

        return targetFilePath;
	}
}