package HSBC.basePage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;

import java.time.Duration;

public class BasePage {

    public static final int TIMEOUT_IN_SECONDS = 30;
    public static final int POOLING_TIME_IN_MILLIS = 100;

    public WebDriver driver;

    public BasePage() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("start-maximized");
        this.driver = new ChromeDriver(chromeOptions);
        PageFactory.initElements(driver, this);
    }

    public <T> T waitUntilConditionMeet(ExpectedCondition<T> condition) {
        return new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(TIMEOUT_IN_SECONDS))
                .pollingEvery(Duration.ofMillis(POOLING_TIME_IN_MILLIS))
                .ignoring(NoSuchElementException.class)
                .until(condition);
    }

    public void waitUntilVisibilityOfElement(WebElement element) {
        waitUntilConditionMeet(ExpectedConditions.visibilityOf(element));
    }

    public void waitUntilElementIsClickable(WebElement element) {
        waitUntilConditionMeet(ExpectedConditions.elementToBeClickable(element));
    }

    public void waitUntilElementIsSelected(WebElement element) {
        waitUntilConditionMeet(ExpectedConditions.elementToBeSelected(element));
    }

    public boolean elementContainsAttributeValue(WebElement element, String attribute, String value) {
        String attributeValues = element.getAttribute(attribute);
        if (attributeValues.contains(value)) {
            return true;
        }
        return false;
    }
}
