package premiumUserContentPlayback;

import org.apache.logging.log4j.Logger;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import baseClass.TestBase;
import sunnxtPages.ContentDetailPageSunnxt;
import sunnxtPages.HomepageSunnxt;
import sunnxtPages.LiveTVPageSunnxt;
import sunnxtPages.LoginPageSunnxt;
import sunnxtPages.PlayerControlsSunnxt;
import sunnxtPages.TopMenuSunnxt;
import sunnxtPages.contentLanguageSelection;
import utility.ExtentReportManager;
import utility.Log;

public class TC_ContentPlaybackLiveTV extends TestBase{
    private static final Logger logger = Log.getLogger(TC_ContentPlaybackLiveTV.class);

	@BeforeMethod
	public void beforeMethod() {
		setupDriver();
		launchSunnxt();
	}

	@Test (invocationCount = 1)
	private void contentplaybackcheck() throws InterruptedException {
		logger.info("Starting playback testing for Live TV");
		test = ExtentReportManager.createTest("Live TV Playback - Basic User");
		contentLanguageSelection cls=new contentLanguageSelection(driver);
		//cls.clickAllowButton();
		cls.clickLanguage("tamil");
		cls.clickDoneButton();
		test.info("Language has been selected"); 
        test.info("Navigating to Home Page");
        
		HomepageSunnxt hp =new HomepageSunnxt(driver);
		implicitWait(10);
		hp.clickSignIn();

		LoginPageSunnxt lp=new LoginPageSunnxt(driver);
		lp.userDetail("9841595069", "12345");
		test.info("User Details has been entered");
		
		TopMenuSunnxt tm= new TopMenuSunnxt(driver);
		tm.redirectionToLiveTv();
		test.info("Navigated to Live TV Section");
		implicitWait(30);
		LiveTVPageSunnxt ltv=new LiveTVPageSunnxt(driver);
		ltv.contentPlay(-1, -1);
        test.info("Page starts scroll. Wait for few seconds....");
        test.info("Random content has been selected.");
        
		ContentDetailPageSunnxt cdp=new ContentDetailPageSunnxt(driver);
		cdp.clickPlayButton();
		cdp.getContentName();

        test.info("Content playback will start soon");
		PlayerControlsSunnxt player = new PlayerControlsSunnxt(driver);

		player.validatePlayback();
		test.info("Content is playing and validated");
		test.pass("Playback successful in Live TV");

	}
	@AfterTest
	private void tearDown() {
		PlayerControlsSunnxt player = new PlayerControlsSunnxt(driver);
		if(player.isVideoPlaying()) {
			quitDriver();
		}else if(player.isSubscriptionShown()) {
			logger.info("Check the account's subscription");
			quitDriver();
		}
	}

}
