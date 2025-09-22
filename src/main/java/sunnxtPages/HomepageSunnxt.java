package sunnxtPages;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import utility.Log;
import utility.WaitUtils;

//***** HomepageSunnxt Class *****//
//Purpose: Represents the Home Page (TV Shows, Shorts,Music Videos and Comedy) of the SunNXT app in Selenium Web Automation.
//
//Responsibilities:
//1. Interact with the profile icon to perform SignIn and Logout.
//2. Detect and scroll through all carousel sections dynamically loaded on the homepage.
//3. Select a specific or random carousel for testing.
//4. Scroll and select specific or random content within the carousel.
//5. Handle dynamic page loading, StaleElementReferenceExceptions, and pagination.
//6. Log all significant actions, warnings, and errors using Log4j2 for debugging.
//7. Facilitate content playback testing by navigating to content details.


public class HomepageSunnxt extends WaitUtils {

    //***** Logger to log messages in console/file *****//
    private static final Logger logger = Log.getLogger(HomepageSunnxt.class);

    //***** Constructor: Pass WebDriver instance to parent WaitUtils class *****//
    public HomepageSunnxt(WebDriver driver) {
        super(driver); // WaitUtils provides wait and scroll utility methods
    }

    //***** Web Elements on Homepage *****//
    @FindBy(xpath = "//img[@alt='myaccount']")
    WebElement profileIconButton; // Profile icon at top-right corner

    @FindBy(linkText = "Sign In")
    WebElement signInButton; // "Sign In" button in profile dropdown

    @FindBy(xpath = "//li[text()='Logout']")
    private WebElement logoutButton; // "Logout" option in profile dropdown

    @FindBy(tagName = "h1")
    private List<WebElement> carouselList; // All carousel headers on the homepage

    @FindBy(className = "viewmore_movie_images__2NctY")
    private List<WebElement> content; // Content items inside a carousel

    //***** Click on SignIn button *****//
    public void clickSignIn() {
        int attempts = 0;
        //***** Retry mechanism to handle StaleElementReferenceException *****//
        while (attempts < 2) {
            try {
                waitForElementToBeClickable(10, profileIconButton); // Wait until clickable
                profileIconButton.click(); // Click profile icon
                logger.info("Profile icon clicked"); // Log action
                break; // Exit retry loop if successful
            } catch (StaleElementReferenceException e) {
                logger.warn("Caught StaleElementReferenceException. Retrying..."); // Retry if element is stale
            }
            attempts++;
        }

        waitForElementToBeClickable(5, signInButton); // Wait for SignIn button
        signInButton.click(); // Click SignIn
        logger.info("Signin button clicked"); // Log action
    }

    //***** Click Logout *****//
    public void clickLogout() {
        int attempts = 0;
        while (attempts < 2) { // Retry mechanism
            try {
                waitForElementToBeClickable(10, profileIconButton); // Wait for profile icon
                profileIconButton.click(); // Open dropdown
                logger.info("Profile icon clicked");
                break; // Exit loop if successful
            } catch (StaleElementReferenceException e) {
                logger.warn("Caught StaleElementReferenceException. Retrying...");
            }
            attempts++;
        }
        waitForElementToBeClickable(10, logoutButton); // Wait for Logout button
        logoutButton.click(); // Click logout
        logger.info("Logout button clicked");
    }
    
    public boolean loggedOut() {
    	try {
			clickLogout();
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
    }

    //***** Check content playback for a specific or random carousel and content *****//
    public void contentPlayBackCheck(int carouselIndex, int contentIndex) throws InterruptedException {
        scrollPageWithLoad(); // Scroll page to load all carousels
        logger.info("Extracting all carousel elements...");

        //***** Collect names/text of all carousels *****//
        List<String> carouselCollections = new ArrayList<>();
        for (WebElement carousel : carouselList) {
            carouselCollections.add(carousel.getText()); // Store carousel headers
        }

        //***** Choose random carousel if index not provided *****//
        if (carouselIndex == -1 && !carouselList.isEmpty()) {
            int totalCarousel = carouselList.size();
            carouselIndex = new Random().nextInt(totalCarousel); // Random selection
        }

        //***** Get the text of the targeted carousel *****//
        String specificCarousel = carouselList.get(carouselIndex).getText();

        //***** Only proceed if the targeted carousel exists *****//
        if (carouselCollections.contains(specificCarousel)) {
            WebElement targetCarousel = carouselList.get(carouselIndex);
            scrollIntoView(targetCarousel); // Scroll carousel into view
            targetCarousel.click(); // Click carousel to view content
            logger.info("Targeted carousel: " + specificCarousel);

            int maxDepth = 5; // Maximum pagination depth
            int clickDepth = 0; // Counter for carousel pages

            while (clickDepth < maxDepth) {
                //***** Get all content inside the carousel *****//
                List<WebElement> insideCarousel = driver.findElements(By.className("viewmore_movie_images__2NctY"));

                //***** Scroll to load more content inside the carousel *****//
                for (int i = 0; i < 5; i++) {
                    scrollByPixel(); // Scroll a bit
                    Thread.sleep(5000); // Wait for content to load
                    insideCarousel = driver.findElements(By.className("viewmore_movie_images__2NctY"));
                    if (insideCarousel.size() >= 30) { // Stop if sufficient content loaded
                        break;
                    }
                }

                //***** Pick random content if index not provided *****//
                if (contentIndex == -1 && insideCarousel.size() > 0) {
                    contentIndex = new Random().nextInt(insideCarousel.size());
                }

                //***** Verify content index is within range *****//
                if (contentIndex >= insideCarousel.size()) {
                    logger.warn("No content at given index: " + contentIndex);
                    break; // Exit if invalid
                }

                try {
                    WebElement contentToClick = insideCarousel.get(contentIndex); // Get the targeted content
                    scrollIntoView(contentToClick); // Scroll into view
                    Thread.sleep(3000); // Wait for rendering
                    contentToClick.click(); // Click content to go to detail/playback page
                    Thread.sleep(3000); // Wait for playback page to load

                    //***** Check if more carousel pages exist *****//
                    boolean isCarouselPresent = !driver.findElements(By.className("viewmore_viewall_data__rWzIY")).isEmpty();
                    if (isCarouselPresent) {
                        clickDepth++; // Increase depth if more pages exist
                        contentIndex = -1; // Reset content index for next page
                    } else {
                        break; // Stop if no more pages
                    }
                } catch (NoSuchElementException e) {
                    logger.error("Element not found while clicking content", e); // Log missing element
                } catch (Exception e) {
                    logger.error("Exception occurred during playback check", e); // Log any other exception
                }
            }
        } else {
            logger.warn("Provided Carousel isn't listed: " + specificCarousel); // Warn if carousel does not exist
        }
    }
}
