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
import sunnxtPages.contentLanguageSelection;
import utility.ExtentReportManager;
import utility.Log;

public class TC_ContentPlaybackHome extends TestBase {

    private static final Logger logger = Log.getLogger(TC_ContentPlaybackHome.class);

    @BeforeMethod
    public void beforeMethod() {
        test=ExtentReportManager.createTest("Home Section Playback");
    	setupDriver();
        launchSunnxt();
        
    }

    @Test(enabled = true, invocationCount = 1)
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

        hp.contentPlayBackCheck(7, 1);
        test.info("Page starts scroll. Wait for few seconds....");
        test.info("Random content has been selected.");
        
        ContentDetailPageSunnxt cdp = new ContentDetailPageSunnxt(driver);
        cdp.clickPlayButton();
		cdp.getContentName();

        test.info("Content playback will start soon");
        
        PlayerControlsSunnxt player = new PlayerControlsSunnxt(driver);
        
        test.info("Content is playing and validated");
        player.moveFocusToPlayer();
        player.fullScreen();
        //player.pauseTheContent();
//        player.setVolume(0.5);
//        player.seekContent(600);
        //player.playTheContent();
        player.enableOrDisableCaptions("disable");
        player.validatePlayback();
        test.pass("Playback successful in Home Section");
    }
    @Test(enabled =false)
    private void playerControlCheck() throws InterruptedException {
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

        hp.contentPlayBackCheck(-1, 8);
        test.info("Page starts scroll. Wait for few seconds....");
        test.info("Random content has been selected.");
        
        ContentDetailPageSunnxt cdp = new ContentDetailPageSunnxt(driver);
        cdp.clickPlayButton();
		cdp.getContentName();

        test.info("Content playback will start soon");
        
        PlayerControlsSunnxt player = new PlayerControlsSunnxt(driver);
        player.moveFocusToPlayer();
        player.fullScreen();
        player.pauseTheContent();
        player.setVolume(0.5);
        player.seekContent(60);
        player.qualityChanges("360");
        player.enableOrDisableCaptions("disable");

	}
    
    @AfterTest
	private void tearDown() {
		PlayerControlsSunnxt player = new PlayerControlsSunnxt(driver);
		if(player.isVideoPlaying()) {
			quitDriver();
		}
	}
}
