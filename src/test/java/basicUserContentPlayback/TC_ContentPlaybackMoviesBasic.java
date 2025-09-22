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

public class TC_ContentPlaybackMoviesBasic extends TestBase{

	private static final Logger logger = Log.getLogger(TC_ContentPlaybackMoviesBasic.class);

	@BeforeMethod
	private void setup() {
		setupDriver();
		launchSunnxt();
	}

	@Test
	private void playBack() throws InterruptedException {
		logger.info("Starting playback testing for Movies Section");
		test = ExtentReportManager.createTest("Movies Section Playback - Basic User");

		contentLanguageSelection cls=new contentLanguageSelection(driver);
		//cls.clickAllowButton();
		cls.clickLanguage("tamil");
		cls.clickDoneButton();
		test.info("Language has been selected"); 
		test.info("Navigating to Home Page");

		HomepageSunnxt hp =new HomepageSunnxt(driver);
		implicitWait(10);
		hp.clickSignIn();
		test.info("Navigating to Login Page");

		LoginPageSunnxt lp=new LoginPageSunnxt(driver);
		lp.userDetail("sunnxtq@gmail.com", "2221086");
		test.info("User Details has been entered");

		TopMenuSunnxt tm =new TopMenuSunnxt(driver);
		tm.redirectionToMovies();
		implicitWait(30);
		test.info("Navigating to Movies section");
		hp.contentPlayBackCheck(-1, -1);
		test.info("Navigating to random movie");

		ContentDetailPageSunnxt cdp =new ContentDetailPageSunnxt(driver);
		cdp.getContentName();
		cdp.clickPlayButton();
		test.info("Clicked the play button");

		PlayerControlsSunnxt player = new PlayerControlsSunnxt(driver);

		player.validatePlayback();
		test.info("Video player validation completed");

		test.pass("Successfully redirected to subscription page");

	}
	@AfterTest
	private void tearDown() {
		PlayerControlsSunnxt player = new PlayerControlsSunnxt(driver);
		if(player.isVideoPlaying()) {
			quitDriver();
		}else if(player.isSubscriptionShown()) {
			quitDriver();
		}
	}
}
