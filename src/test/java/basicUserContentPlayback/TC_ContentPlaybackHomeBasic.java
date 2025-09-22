package basicUserContentPlayback;

import org.apache.logging.log4j.Logger;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import baseClass.TestBase;
import premiumUserContentPlayback.TC_ContentPlaybackLiveTV;
import sunnxtPages.ContentDetailPageSunnxt;
import sunnxtPages.HomepageSunnxt;
import sunnxtPages.LiveTVPageSunnxt;
import sunnxtPages.LoginPageSunnxt;
import sunnxtPages.PlayerControlsSunnxt;
import sunnxtPages.TopMenuSunnxt;
import sunnxtPages.contentLanguageSelection;
import utility.ExtentReportManager;
import utility.Log;

public class TC_ContentPlaybackHomeBasic extends TestBase {
	private static final Logger logger = Log.getLogger(TC_ContentPlaybackHomeBasic.class);

	@BeforeMethod
	public void beforeMethod() {
		setupDriver();
		launchSunnxt();
	}

	@Test (invocationCount = 1)
	private void contentplaybackcheck() throws InterruptedException {
		logger.info("Starting playback testing for Live TV");
		test = ExtentReportManager.createTest("Live TV Playback");
		contentLanguageSelection cls=new contentLanguageSelection(driver);
		//cls.clickAllowButton();
		cls.clickLanguage("tamil");
		cls.clickDoneButton();

		HomepageSunnxt hp =new HomepageSunnxt(driver);
		implicitWait(10);
		hp.clickSignIn();

		LoginPageSunnxt lp = new LoginPageSunnxt(driver);
		lp.userDetail("sunnxtq@gmail.com", "2221086");
		test.info("User Details has been entered");

		waitForLoginSuccess();

		hp.contentPlayBackCheck(1, -1);

		ContentDetailPageSunnxt cdp=new ContentDetailPageSunnxt(driver);
		cdp.clickPlayButton();
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

		test.pass("Playback successful in Live TV");

	}
	@AfterTest
	private void tearDown() {
		exitIfPlaybackOrSubscription();

	}


}
