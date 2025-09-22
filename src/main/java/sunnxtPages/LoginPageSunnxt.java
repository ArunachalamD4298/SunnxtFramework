package sunnxtPages;



import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import utility.Log;
import utility.WaitUtils;

public class LoginPageSunnxt extends WaitUtils{

    private static final Logger logger = Log.getLogger(LoginPageSunnxt.class);

	
	public LoginPageSunnxt(WebDriver driver) {
		super(driver);
	}

	@FindBy (name = "email")
	WebElement userIdField;

	@FindBy (name = "password")
	WebElement passwordFied;

	@FindBy (xpath = "//button[@class='signin_signin_btn__tHSgj btn btn-primary']")
	WebElement loginButton;

	@FindBy (xpath = "//a[text()='SIGN UP']")
	WebElement SignupButton;

	@FindBy (xpath = "//a[text()='Forgot Password ?']")
	WebElement forgotPasswordButton;

	@FindBy (xpath = "//p[text()='User does not exist. Please sign up.']")
	private WebElement uservalidation;

	@FindBy (xpath ="//p[text()='Please enter valid email or mobile number']")
	private WebElement invalidUserID;

	@FindBy (xpath = "//p[text()='Kindly verify your user id or password and try again.']")
	private WebElement invalidPassword;

	@FindBy (xpath = "//p[text()='Please enter password']")
	private WebElement emptyPasswordField;
	
	@FindBy (xpath = "//h3[text()='Device Limit Reached']")
	private WebElement deviceLimitPopUp ;

	@FindBy (xpath ="//button[text()='Add Device']")
	private WebElement addDeviceBtn;
	
	public void userDetail(String username, String password) {
	    implicitWait(10);
	    userIdField.sendKeys(username);
	    logger.info("Email or Mobile Number has been entered");

	    passwordFied.sendKeys(password);
	    logger.info("Password has been entered");

	    loginButton.click();
	    logger.info("Login button clicked");

	    if (isElementVisible(5, deviceLimitPopUp)) {
	        addDeviceBtn.click();
	        logger.info("Device limit popup handled");
	    }

	/// Check if any known login validation element is present
	    if (isElementVisible(3, invalidUserID) ||
	        isElementVisible(3, uservalidation) ||
	        isElementVisible(3, invalidPassword) ||
	        isElementVisible(3, emptyPasswordField)) {

	        try {
	            if (isElementVisible(2, invalidUserID)) {
	                logger.warn("Invalid email or mobile: " + invalidUserID.getText());
	                waitForElementToBeVisible(2, invalidUserID);
	                driver.quit();
	            } else if (isElementVisible(2, uservalidation)) {
	                logger.warn("User not found: " + uservalidation.getText());
	                waitForElementToBeVisible(2, uservalidation);
	                driver.quit();
	            } else if (isElementVisible(2, invalidPassword)) {
	                logger.warn("Incorrect password: " + invalidPassword.getText());
	                waitForElementToBeVisible(2, invalidPassword);
	                driver.quit();
	            } else if (isElementVisible(2, emptyPasswordField)) {
	                logger.warn("Password field empty: " + emptyPasswordField.getText());
	                waitForElementToBeVisible(2, emptyPasswordField);
	                driver.quit();
	            }
	        } catch (Exception e) {
	            logger.error("Exception during login validation: ", e);
	        }

	    } else {
	        // No validation messages → assume login success
	        logger.info("User login successful with username: " + username);
	    }
	}



	public void signupPageRedirection() {
		String expectedValidation = uservalidation.getText();
		if(expectedValidation.equalsIgnoreCase(expectedValidation)) {
			waitForElementToBeClickable(20, SignupButton);
			SignupButton.click();
			logger.info("Redirected to Sign Up page");
			
		}else {
			logger.info("User already exists");
		}
	}

	public void forgotPasswordPageRedirection() {
		forgotPasswordButton.click();
		logger.info("Redirected to Forget Password page");
	}
	
//	public boolean isInvalidUserIDVisible() {
//		return isElementPresent(invalidUserID);
//	}
//	
//	public String getInvalidUserIDText() {
//		return invalidUserID.getText();
//	}
//	
//	public boolean isUserValidationVisible() {
//		return isElementPresent(uservalidation);
//	}
//	
//	public String getUserValidationVisible() {
//		return uservalidation.getText();
//	}
//	
//	public boolean isInvalidPasswordVisible() {
//		return isElementPresent(invalidPassword);
//	}
//	
//	public String getInvalidPasswordVisible() {
//		return invalidPassword.getText();
//	}
//	public boolean isEmptyPasswordVisible() {
//	    return isElementPresent(emptyPasswordField);
//	}

	public String getEmptyPasswordText() {
	    return emptyPasswordField.getText();
	}
}
