package sunnxtPages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import utility.WaitUtils;

public class TopMenuSunnxt extends WaitUtils {

	public TopMenuSunnxt(WebDriver driver) {
		super(driver);
	}
	
	private static final Logger logger=LoggerFactory.getLogger(TopMenuSunnxt.class);

	@FindBy (xpath = "(//li[text()='Home'])[1]")
	private WebElement home;

	@FindBy (xpath = "(//li[text()='Movies'])[1]")
	private WebElement movie;

	@FindBy (xpath = "(//li[text()='Live TV'])[1]")
	private WebElement liveTv;

	@FindBy (xpath = "(//li[text()='TV Shows'])[1]")
	private WebElement tvShows;

	@FindBy (xpath = "(//li[text()='Music Videos'])[1]")
	private WebElement musicVideo;

	@FindBy (xpath = "(//li[text()='Comedy'])[1]")
	private WebElement comedy;

	@FindBy (xpath = "(//li[text()='Shorts'])[1]")
	private WebElement shorts;

	@FindBy (xpath ="//button[text()='Languages']")
	private WebElement language;
	
	
	public void redirectionToHomePage() {
		waitForElementToBeClickable(10, home);
		home.click();
		logger.info("Redirected to Home Page");
	}

	public void redirectionToMovies() {
		waitForElementToBeClickable(10, movie);
		movie.click();
		logger.info("Redirected to Movies Page");

	}

	public void redirectionToLiveTv() {
		waitForElementToBeClickable(10, liveTv);
		liveTv.click();
		logger.info("Redirected to Live Tv Page");

	}

	public void redirectionToTvShows() {
		waitForElementToBeClickable(10, tvShows);
		tvShows.click();
		logger.info("Redirected to Tv Shows Page");

	}

	public void redirectionMusicVideos() {
		waitForElementToBeClickable(10, musicVideo);
		musicVideo.click();
		logger.info("Redirected to Music Videos Page");

	}

	public void redirectionToComedy() {
		waitForElementToBeClickable(10, comedy);
		comedy.click();
		logger.info("Redirected to Comedy Page");

	}

	public void redirectionToShorts() {
		waitForElementToBeClickable(10, shorts);
		shorts.click();
		logger.info("Redirected to Shorts Page");

	}

}
