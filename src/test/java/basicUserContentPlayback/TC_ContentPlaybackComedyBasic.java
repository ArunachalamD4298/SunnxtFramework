package basicUserContentPlayback;

import org.apache.logging.log4j.Logger;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import baseClass.TestBase;
import sunnxtPages.ContentDetailPageSunnxt;
import sunnxtPages.HomepageSunnxt;
import sunnxtPages.LoginPageSunnxt;
import sunnxtPages.PlayerControlsSunnxt;
import sunnxtPages.TopMenuSunnxt;
import sunnxtPages.contentLanguageSelection;
import utility.ExtentReportManager;
import utility.Log;

public class TC_ContentPlaybackComedyBasic extends TestBase {
	
    private static final Logger logger = Log.getLogger(TC_ContentPlaybackComedyBasic.class);

	
    @BeforeMethod
	public void setup() {
		setupDriver();
		launchSunnxt();
        test=ExtentReportManager.createTest("Comedy Section Playback");

	}

	@Test(enabled =true)
	public void contentPlayBackCheckTest() throws InterruptedException {
		logger.info("Starting playback test...");

        contentLanguageSelection cls = new contentLanguageSelection(driver);
        cls.clickLanguage("tamil");
        cls.clickDoneButton();
        test.info("Language has been selected"); 
        test.info("Navigating to Home Page");

        HomepageSunnxt hp = new HomepageSunnxt(driver);
        implicitWait(10);
        hp.clickSignIn();


        LoginPageSunnxt lp = new LoginPageSunnxt(driver);
        lp.userDetail("sunnxtq@gmail.com", "2221086");
        test.info("User Details has been entered");

        waitForLoginSuccess();

        TopMenuSunnxt tm=new TopMenuSunnxt(driver);
        tm.redirectionToComedy();
        test.info("Navigated to Comedy Section");
        hp.contentPlayBackCheck(1, -1);
        test.info("Page starts scroll. Wait for few seconds....");
        test.info("Random content has been selected.");
        
        ContentDetailPageSunnxt cdp = new ContentDetailPageSunnxt(driver);
        cdp.clickPlayButton();
		cdp.getContentName();
        test.info("Content playback will start soon");
     // ✅ use inherited networkUtils from TestBase
        networkUtils.clearCapturedRequests();
        Thread.sleep(5000);  // wait for requests

        if (networkUtils.isAdRequested()) {
            logger.info("✅ Ad request detected: " + networkUtils.getAdRequests());
        } else {
            logger.warn("❌ No ad request detected.");
        }
        
        
        PlayerControlsSunnxt player = new PlayerControlsSunnxt(driver);
        player.validatePlayback();
        test.info("Content is playing and validated");
        test.pass("Playback successful in Music Video Section");
    }
    @AfterTest
	private void tearDown() {
		exitIfPlaybackOrSubscription();
	}

}
