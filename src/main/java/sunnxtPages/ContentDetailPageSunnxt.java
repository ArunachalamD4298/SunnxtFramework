package sunnxtPages;

import java.util.List;
import java.util.NoSuchElementException;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import utility.Log;
import utility.WaitUtils;

public class ContentDetailPageSunnxt extends WaitUtils {
    private static final Logger logger = Log.getLogger(ContentDetailPageSunnxt.class);


	public ContentDetailPageSunnxt(WebDriver driver) {
		super(driver);
	}

	@FindBy (id = "paly_button")
	private WebElement playButton;

	@FindBy (xpath = "(//li[text()='Watchlist'])[2]")
	private WebElement WatchlistButton;

	@FindBy (xpath = "//div[text()='Play']")
	private WebElement playBtnLiveTV;

	@FindBy (xpath = "//div[text()='Explore EPG']")
	private List<WebElement> exploreEpg;
	
	@FindBy (xpath = "//span[@class='detail_contentTitle__S00Up']")
	private WebElement contentName;

	public void clickPlayButton() {
		logger.info("Trying to click the play button...");

		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if(isElementDisplayed(playBtnLiveTV)) {
			playBtnLiveTV.click();
			logger.info("Live TV play button found, clicking...");

		}else if(isElementDisplayed(playButton)) {
			playButton.click();
			logger.info("Regular play button found, clicking...");

		}
		else {
            logger.warn("No play button found on the detail page.");
        }
	}
	
	public WebElement getContentName() {
		try {
			String contentName = driver.findElement(By.xpath("//span[@class='detail_contentTitle__S00Up']")).getText();
			logger.info("Targeted Content - {} ", contentName);
		} catch (NoSuchElementException e) {
			logger.error("Failed to get content title", e);
		}

		return contentName;
	}

}
