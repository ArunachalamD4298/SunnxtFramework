package userOnboarding;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import baseClass.TestBase;
import sunnxtPages.HomepageSunnxt;
import sunnxtPages.LoginPageSunnxt;
import sunnxtPages.SignupPageSunnxt;
import sunnxtPages.contentLanguageSelection;

public class TC_Signup extends TestBase {

	@BeforeMethod
	public void beforeMethod() {
		setupDriver();
		launchSunnxt();
	}

	@Test (enabled = true, invocationCount = 1)
	public void createaccount() throws InterruptedException {
		contentLanguageSelection cls=new contentLanguageSelection(driver);
		implicitWait(10);
		//cls.clickAllowButton();
		//implicitWait(5);
		cls.clickLanguage("tamil");
		cls.clickLanguage("telugu");
		cls.clickDoneButton();
		HomepageSunnxt hp=new HomepageSunnxt(driver);
		implicitWait(10);
		hp.clickSignIn();
		implicitWait(10);
		LoginPageSunnxt lp =new LoginPageSunnxt(driver);
		String user= randomEmail();
		lp.userDetail(user, "asdfgh@@");
		lp.signupPageRedirection();
		Thread.sleep(5000);
		SignupPageSunnxt sup=new SignupPageSunnxt(driver);
		sup.enterLoginId(user);
		sup.enterPassword("asdfgh@@");
		sup.enterUserName("test");
		sup.setAge(25);
		sup.genderSelection("male");
		sup.countriesSelection("India");
		implicitWait(15);
		sup.statesSelection("Tamil Nadu");
		sup.clickTermsAndCondition();
		sup.createAccount();
	}
	@AfterMethod
	private void tearDown() {
		SignupPageSunnxt sup=new SignupPageSunnxt(driver);

		if(sup.isAccountCreated()) {
			quitDriver();
		}
	}
}
