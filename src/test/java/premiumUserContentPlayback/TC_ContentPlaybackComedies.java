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

public class TC_ContentPlaybackComedies extends TestBase{
    private static final Logger logger = Log.getLogger(TC_ContentPlaybackComedies.class);

	@BeforeMethod
	public void beforemethod() {
		setupDriver();
		launchSunnxt();
	}

	@Test
	public void contentPlaybackCheck() throws InterruptedException {
		logger.info("Starting playback testing for Comedies Section");
		test = ExtentReportManager.createTest("Comedies Section Playback");
		contentLanguageSelection cls=new contentLanguageSelection(driver);

		cls.clickLanguage("tamil");
		cls.clickDoneButton();

		HomepageSunnxt hp=new HomepageSunnxt(driver);
		implicitWait(10);
		hp.clickSignIn();

		LoginPageSunnxt lp=new LoginPageSunnxt(driver);
		lp.userDetail("9841595069", "123456");

		implicitWait(30);

		TopMenuSunnxt tm =new TopMenuSunnxt(driver);
		tm.redirectionToComedy();

		hp.contentPlayBackCheck(1, -1);

		ContentDetailPageSunnxt cdp =new ContentDetailPageSunnxt(driver);
		cdp.clickPlayButton();
		
		PlayerControlsSunnxt player = new PlayerControlsSunnxt(driver);

		player.validatePlayback();
		
		test.pass("Playback successful in Comedies Section");
	}
	@AfterTest
	private void tearDown() {
		PlayerControlsSunnxt player = new PlayerControlsSunnxt(driver);
		if(player.isVideoPlaying()) {
			quitDriver();
		}
	}
}
