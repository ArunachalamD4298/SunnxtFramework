package baseClass;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.apache.logging.log4j.Logger; // << Needed!
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

import io.github.bonigarcia.wdm.WebDriverManager;
import utility.ExtentReportManager;
import utility.Log;
import utility.NetworkUtils;
public class TestBase {

	public WebDriver driver;
	protected static ExtentReports extent;
	protected ExtentTest test;
	public static final Logger logger = Log.getLogger(TestBase.class);
    protected NetworkUtils networkUtils;

	@BeforeSuite
	public void setupReport() {
		extent = ExtentReportManager.getReportInstance();
	}

	@AfterSuite
	public void teardownReport() {
		extent.flush();
		//String reportPath = ExtentReportManager.reportPath;
		//EmailUtils.sendTestReport(reportPath);
	}

	public void waitForLoginSuccess() {
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));
		wait.until(ExpectedConditions.visibilityOfElementLocated(
				By.xpath("//img[@alt='search']")));
		logger.info("User login successful");
	}


	public void setupDriver() {
			ChromeOptions options=new ChromeOptions();
			Map<String, Object> prefs=new HashMap<>();
			prefs.put("profile.default_content_setting_values.notifications", 2);
			options.setExperimentalOption("prefs", prefs);

		    // ✅ Add Eager Page Load Strategy
		    options.setPageLoadStrategy(PageLoadStrategy.EAGER);
			
			WebDriverManager.chromedriver().setup();
			driver=new ChromeDriver(options);
			networkUtils = new NetworkUtils(driver);
			networkUtils.startCapture();   // begin listening to requests
			driver.manage().window().maximize();
			logger.info("Web Driver initiated. Launching Chrome Browser");
	}

	public void launchSunnxt() {
		logger.info("Launching Sun NXT.....");   		
		driver.get("https://www.sunnxt.com/");

	}

	public void quitDriver() {
		implicitWait(10);
		driver.quit();
		logger.info("All browser sessions have been closed.");

	}

	public void closeSession() {
		driver.close();
		logger.info("Current session has been closed.");
	}


	public void implicitWait(int seconds) {
	    driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(seconds));
	    logger.info("Applying implicit wait of {} seconds.", seconds);
	}


	public String randomEmail() {

		Random random =new Random();
		String randomEmailId = "asgkhbkjk"+Integer.toString(random.nextInt(100))+"@hotfail.com";
		logger.debug("Generated random email: {}", randomEmailId);
		return randomEmailId;

	}

	public void notificationBlocker() {
		logger.info("Setting Chrome options to block notifications.");
		ChromeOptions options=new ChromeOptions();
		Map<String, Object> prefs=new HashMap<>();
		prefs.put("profile.default_content_setting_values.notifications", 2);
		options.setExperimentalOption("prefs", prefs);
	}
	
	
	public void exitIfPlaybackOrSubscription() {
	    try {
	        if (driver == null) {
	            return;
	        }

	        // ✅ Check subscription screen
	        boolean subscriptionShown = !driver.findElements(
	            By.xpath("//h2[text()='Subscriptions']")
	        ).isEmpty();

	        if (subscriptionShown) {
	            logger.info("Subscription screen detected. Closing browser.");
	            quitDriver();
	            return;
	        }

	        // ✅ Check video playback via JS
	        JavascriptExecutor js = (JavascriptExecutor) driver;
	        boolean videoPlaying = (Boolean) js.executeScript(
	            "var v = document.querySelector('video');" +
	            "return !!v && !v.paused && v.currentTime > 0 && v.offsetParent !== null;"
	        );

	        if (videoPlaying) {
	            logger.info("Video is playing. Waiting 8 seconds before closing browser...");
	            Thread.sleep(8000);
	            quitDriver();
	            return;
	        }

	        // ✅ Fallback
	        logger.info("Neither subscription screen nor video playback detected. Closing browser.");
	        quitDriver();

	    } catch (Exception e) {
	        logger.error("Error during teardown. Forcing browser quit.", e);
	        quitDriver();
	    }
	}


}
