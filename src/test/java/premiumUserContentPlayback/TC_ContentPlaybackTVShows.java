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

public class TC_ContentPlaybackTVShows extends TestBase{
    private static final Logger logger = Log.getLogger(TC_ContentPlaybackTVShows.class);

	@BeforeMethod
	public void beforeMethod() {
		setupDriver();
		launchSunnxt();
	}

	@Test(enabled =true)
	public void contentPlayBackCheckTest() throws InterruptedException {
		logger.info("Starting playback testing for TV Shows Section");
		test = ExtentReportManager.createTest("TV Shows Section Playback");
		
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
		lp.userDetail("9841595069", "12345");
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
        test.info("Content playback will start soon");
        
        PlayerControlsSunnxt player = new PlayerControlsSunnxt(driver);
        player.validatePlayback();
        test.info("Content is playing and validated");
		
		test.pass("Playback successful in TV Shows Section Section");

		
	}
	 @AfterTest
		private void tearDown() {
			PlayerControlsSunnxt player = new PlayerControlsSunnxt(driver);
			if(player.isVideoPlaying()) {
				quitDriver();
			}
	 }
}
