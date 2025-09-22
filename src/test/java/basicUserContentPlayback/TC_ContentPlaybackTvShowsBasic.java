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

public class TC_ContentPlaybackTvShowsBasic extends TestBase {

	private static final Logger logger= Log.getLogger(TC_ContentPlaybackTvShowsBasic.class);

	@BeforeMethod
	public void setup() {
		setupDriver();
		launchSunnxt();
	}

	@Test(enabled =true)
	public void contentPlayBackCheckTest() throws InterruptedException {
		logger.info("Starting playback testing for TV Shows Section- Basic User");
		test = ExtentReportManager.createTest("TV Shows Section Playback - Basic User");

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
		tm.redirectionToTvShows();
		implicitWait(30);
		test.info("Navigating to TV Shows Page");

		hp.contentPlayBackCheck(-1, -1);
		test.info("Page starts scroll. Wait for few seconds....");
		test.info("Random content has been selected.");

		ContentDetailPageSunnxt cdp = new ContentDetailPageSunnxt(driver);
		cdp.getContentName();
        cdp.clickPlayButton();
        
        // ✅ use inherited networkUtils from TestBase
        networkUtils.clearCapturedRequests();
        Thread.sleep(5000);  // wait for requests

        if (networkUtils.isAdRequested()) {
            logger.info("✅ Ad request detected: " + networkUtils.getAdRequests());
        } else {
            logger.warn("❌ No ad request detected.");
        }
        
        
		test.info("Content playback will start soon");
		
		PlayerControlsSunnxt player = new PlayerControlsSunnxt(driver);

		player.validatePlayback();

		test.info("Content is playing and validated");
		


		test.pass("Playback successful in TV Shows Section Section");

	}


	@AfterTest
	private void tearDown() {
		exitIfPlaybackOrSubscription();
	}
}
