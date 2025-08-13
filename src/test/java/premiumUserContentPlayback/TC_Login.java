package premiumUserContentPlayback;

import java.lang.reflect.Method;

import org.apache.logging.log4j.Logger;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import sunnxtPages.HomepageSunnxt;
import sunnxtPages.LoginPageSunnxt;
import sunnxtPages.baseFunction;
import sunnxtPages.contentLanguageSelection;
import utility.DataProviderClass;
import utility.ExtentReportManager;
import utility.Log;

public class TC_Login extends baseFunction {

	@BeforeMethod
	public void setupTest(Method method) {
		test = ExtentReportManager.createTest(method.getName());  // Initialize ExtentReport test node

		setupDriver();
		launchSunnxt();
	}

	@Test(dataProvider = "loginData", dataProviderClass = DataProviderClass.class, enabled = false)
	public void loginTestWithMultipleUser(String username, String password) {
		test.info("Login test with Multiple user starts........");
		contentLanguageSelection cls=new contentLanguageSelection(driver);
		implicitWait(10);
		//cls.clickAllowButton();
		implicitWait(5);
		cls.clickLanguage("tamil");
		cls.clickLanguage("telugu");
		cls.clickDoneButton();
		test.info("Language has been selected");
		test.info("Navigating to Home Page");
		HomepageSunnxt hp=new HomepageSunnxt(driver);
		implicitWait(10);
		hp.clickSignIn();
		test.info("Navigating to Login Page");
		implicitWait(10);
		LoginPageSunnxt lp =new LoginPageSunnxt(driver);
		test.info("User Details has been entered");
		lp.userDetail(username, password);
		test.info("User has been logged in successfully");
		hp.clickLogout();
		test.info("User has been logged out successfully");
		test.pass("Login Successful");

	}

	@Test (enabled = true)
	public void loginTest() {
		test.info("Login test starts........");
		contentLanguageSelection cls=new contentLanguageSelection(driver);
		implicitWait(10);
		//cls.clickAllowButton();
		implicitWait(5);
		cls.clickLanguage("tamil");
		cls.clickLanguage("telugu");
		cls.clickDoneButton();
		test.info("Language has been selected");
		test.info("Navigating to Home Page");
		HomepageSunnxt hp=new HomepageSunnxt(driver);
		implicitWait(10);
		hp.clickSignIn();
		test.info("Navigating to Login Page");
		implicitWait(10);
		LoginPageSunnxt lp =new LoginPageSunnxt(driver);
		test.info("User Details has been entered");
		lp.userDetail("9841595069", "123456");
		test.info("User has been logged in successfully");
		hp.clickLogout();
		test.info("User has been logged out successfully");
		test.pass("Login Successful");
	}



}
