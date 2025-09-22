package sunnxtPages;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import utility.WaitUtils;

//*****// LiveTVPageSunnxt handles interactions with the Live TV section of the Sunnxt platform
//*****// Extends WaitUtils for utility methods like scrolling, waits, etc.

public class LiveTVPageSunnxt extends WaitUtils {

    //*****// Logger instance for logging info, warnings, and errors
    private static final Logger logger = LogManager.getLogger(LiveTVPageSunnxt.class);

    //*****// Constructor to initialize driver
    public LiveTVPageSunnxt(WebDriver driver) {
        super(driver);
    }

    //*****// WebElements representing Live TV tabs (languages)
    @FindBy(xpath = "//div[@class='livetv_tabs_button__t7aG5']/button")
    private List<WebElement> liveTVTabs;

    //*****// WebElements representing the list of channels
    @FindBy(id = "liveTVChannelsList")
    private List<WebElement> channelLists;

    //*****// Single channel list container for scrolling
    @FindBy(id = "liveTVChannelsList")
    private WebElement channelList;

    //*****// WebElements representing channel names
    @FindBy(className = "livetv_livetv_channels_titles__qLE0j")
    private List<WebElement> channelName;

    //*****// Main method to select a language tab and play a channel
    public void contentPlay(int LanguageIndex, int channelIndex) {

        //*****// Collect all available languages into a list
        List<String> languageCollections = new ArrayList<String>();
        for (WebElement lang : liveTVTabs) {
            languageCollections.add(lang.getText());
        }

        //*****// If LanguageIndex = -1, pick a random language
        if (LanguageIndex == -1 && !liveTVTabs.isEmpty()) {
            int livetv = liveTVTabs.size();
            LanguageIndex = new Random().nextInt(livetv);
        }

        //*****// Get the specific language based on index
        String specificLanguage = liveTVTabs.get(LanguageIndex).getText();

        //*****// Click the targeted language tab if it exists
        if (languageCollections.contains(specificLanguage)) {
            WebElement targetlanguage = liveTVTabs.get(LanguageIndex);
            targetlanguage.click();
            System.out.println("Targeted carousel name: " + liveTVTabs.get(LanguageIndex).getText());
            logger.info("Targeted carousel name: {}", liveTVTabs.get(LanguageIndex).getText());
        } else {
            //*****// Log warning if the language is not present
            logger.warn("Provided Language isn't listed here: {}", liveTVTabs.get(LanguageIndex).getText());
        }

        //*****// Scroll the channel container down by 300 pixels using JS
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollTop+=300;", channelList);

        //*****// Collect all channel names into a list
        List<String> channelCollections = new ArrayList<String>();
        for (WebElement channel : channelName) {
            channelCollections.add(channel.getText());
        }

        //*****// If channelIndex = -1, pick a random channel
        if (channelIndex == -1 && channelName.size() > 0) {
            channelIndex = new Random().nextInt(channelName.size());
        }

        //*****// Check if provided channel index is valid
        if (channelIndex >= channelName.size()) {
            logger.error("No content at given index: {}", channelIndex);
        }

        //*****// Get the specific channel based on index
        String specificChannel = channelName.get(channelIndex).getText();

        //*****// Click the targeted channel if it exists
        if (channelCollections.contains(specificChannel)) {
            WebElement targetChannel = channelName.get(channelIndex);
            //scrollIntoView(targetChannel);  // optional scroll
            targetChannel.click();
            logger.info("Targeted channel name: {}", specificChannel);
        } else {
            //*****// Log warning if channel is not listed
            logger.warn("Provided Channel isn't listed here: {}", specificChannel);
        }

    }
}
