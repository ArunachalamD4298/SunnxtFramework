package sunnxtPages;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.Select;

import utility.WaitUtils;

public class SignupPageSunnxt extends WaitUtils{

    private static final Logger logger = LogManager.getLogger(SignupPageSunnxt.class);

	
	public SignupPageSunnxt(WebDriver driver) {
		super(driver);
	}

	@FindBy (name = "email")
	private WebElement emailField;

	@FindBy (name = "password")
	private WebElement passwordField;

	@FindBy (name = "name")
	private WebElement nameField;

	@FindBy (name = "age")
	private WebElement ageField;

	@FindBy (xpath = "//span[text()='male']")
	private WebElement male;

	@FindBy (xpath = "//span[text()='female']")
	private WebElement female;

	@FindBy (name = "country")
	private WebElement country;

	@FindBy (name = "state")
	private WebElement state;

	@FindBy (id = "terms")
	private WebElement termsAndcondition;

	@FindBy (xpath = "//button[text()='Sign Up ']")
	private WebElement signupButton;
	
	

	public void enterLoginId(String loginid) {
waitForElementToBeClickable(5, emailField);
		emailField.sendKeys(loginid);
		logger.info("User Id - {}", loginid);
	
	}

	public void enterPassword(String newpassword) {

		passwordField.sendKeys(newpassword);
	}

	public void enterUserName (String name) {
		nameField.sendKeys(name);
		logger.info("Profile Name - {}", name);

	}
	

	public void setAge(int age) {
		Select sel=new Select(ageField);

		String ageRange="";

		if(age>=18 && age<=25) {
			ageRange ="18-25 Years";
		}
		else if (age>=26 && age<=30) {
			ageRange ="26-30 Years";
		}
		else if (age>=31 && age<=35) {
			ageRange="31-35 Years";
		}
		else if (age>=36 && age<=40) {
			ageRange="36-40 Years";
		}
		else if (age>=41 && age<=50) {
			ageRange="41-50 Years";
		}
		else if (age>=51 && age<=60) {
			ageRange="51-60 Years";
		}
		else if (age>=61 && age<=70) {
			ageRange="61-70 Years";
		}
		else if (age>=71 && age<=80) {
			ageRange="71-80 Years";
		}
		else if (age>=81 && age<=90) {
			ageRange="81-90 Years";
		}
		else {
			throw new IllegalStateException("Age isn't in the range");
		}
		sel.selectByVisibleText(ageRange);
		logger.info("Selected age {}", ageRange);

	}
	public void genderSelection(String gender) {
		if(gender.equalsIgnoreCase("male")) {
			waitForElementToBeClickable(3, male);
			male.click();
			logger.info("Gender selection - Male");
		}else if(gender.equalsIgnoreCase("female")) {
			waitForElementToBeClickable(3, female);
			female.click();
			logger.info("Gender selection - Female");

		}else {
			throw new IllegalStateException("Given gender isn't listed ");

		}
	}


	public void countriesSelection(String CountryName) {
		waitForDropdownOptions(country, 1, 10);
		
		Select selectCountry =new Select(country);
		List<WebElement> options = selectCountry.getOptions();
		List <String> countryTexts=new ArrayList<String>();
		for(WebElement option: options) {
			countryTexts.add(option.getText().trim());
		}

		if(countryTexts.contains(CountryName)) {
			selectCountry.selectByVisibleText(CountryName);
			logger.info(CountryName + " is selected");
		}else {
			logger.error("Country '" + CountryName + "' not found in dropdown.");
		}
		
	}

	public void statesSelection(String StateName) {
		waitForDropdownOptions(state, 1, 30);

		Select selectState =new Select(state);
		List<WebElement> options = selectState.getOptions();
		List <String> stateText=new ArrayList<String>();
		for(WebElement option: options) {
			stateText.add(option.getText().trim());
		}

		if(stateText.contains(StateName)) {

			waitForTextToBePresent(20, state, StateName);
			selectState.selectByVisibleText(StateName);
			System.out.println(StateName +" is selected");
		}
		else {
			logger.error("State '" + StateName + "' not found in dropdown.");
		}

	}


	public void clickTermsAndCondition() {
		termsAndcondition.click();
	}

	public void createAccount() {
		signupButton.click();
		logger.info("User has been created");
	}
	
	public boolean isAccountCreated() {
		try {
			createAccount();
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
}
