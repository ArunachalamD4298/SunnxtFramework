package premiumUserContentPlayback;

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

public class TC_ContentPlaybackShorts extends TestBase{
    private static final Logger logger = Log.getLogger(TC_ContentPlaybackShorts.class);

    @BeforeMethod
	public void setup() {
		setupDriver();
		launchSunnxt();
        test=ExtentReportManager.createTest("Shorts Section Playback");

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
        lp.userDetail("9841595069", "12345");
        test.info("User Details has been entered");

        waitForLoginSuccess();

        TopMenuSunnxt tm=new TopMenuSunnxt(driver);
        tm.redirectionToShorts();
        test.info("Navigated to Shorts Section");
        hp.contentPlayBackCheck(1, -1);
        test.info("Page starts to scroll. Wait for few seconds....");
        test.info("Random content has been selected.");
        
        ContentDetailPageSunnxt cdp = new ContentDetailPageSunnxt(driver);
        cdp.clickPlayButton();
		cdp.getContentName();

        test.info("Content playback will start soon");
        
        PlayerControlsSunnxt player = new PlayerControlsSunnxt(driver);
        player.validatePlayback();
        test.info("Content is playing and validated");
        test.pass("Playback successful in Shorts Section");
    }
    @AfterTest
	private void tearDown() {
		PlayerControlsSunnxt player = new PlayerControlsSunnxt(driver);
		if(player.isVideoPlaying()) {
			quitDriver();
		}
	}

}
