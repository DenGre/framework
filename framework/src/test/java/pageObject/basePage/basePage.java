package pageObject.basePage;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

public class basePage {
    protected WebDriver driver;
    public JavascriptExecutor js;
    public WebDriverWait wait;

    public basePage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, 10);
        js = (JavascriptExecutor) driver;
        PageFactory.initElements(driver, this);
    }

    // Clicking on the element
    //sdsd
    public void click(WebElement el) {
        highlightElement(el, "red");
        el.click();

    }

    // Send keys or fill with text the field
    public void fillText(WebElement el, String text) {
        highlightElement(el, "yellow");
        el.clear();
        el.sendKeys(text);
    }

    // sleeps for a specific time
    public void sleep(int i) {
        try {
            TimeUnit.SECONDS.sleep(i);
        } catch (Exception e) {
            System.out.println("Something went wrong");
        }
    }

    // Get text - any text that is in the element will be parsed to a String
    public String getText(WebElement el) {
        return el.getText();
    }

    //Highlight the element with the chosen color
    private void highlightElement(WebElement element, String color) {
        // keep the old style to change it back
        String originalStyle = element.getAttribute("style");
        String newStyle = "background-color:yellow;border: 1px solid " + color + ";" + originalStyle;
        js = (JavascriptExecutor) driver;

        // Change the style
        js.executeScript("var tmpArguments = arguments;setTimeout(function () {tmpArguments[0].setAttribute('style', '"
                + newStyle + "');},0);", element);

        // Change the style back after 400 milliseconds
        js.executeScript("var tmpArguments = arguments;setTimeout(function () {tmpArguments[0].setAttribute('style', '"
                + originalStyle + "');},400);", element);
    }

    //Refreshing the page if needed
    public void refreshPage() {
        driver.navigate().refresh();
    }

    //Drag and drop elements
    public void dragAndDrop(WebElement el, WebElement dropZone) {
        Actions actions = new Actions(driver);
        actions.dragAndDrop(el, dropZone).perform();
    }

    //Moving elements on other elements
    public void moveToElement(WebElement el) {
        Actions actions = new Actions(driver);
        actions.moveToElement(el).perform();
    }

    //Returns true if the element is displayed
    public boolean isElementDisplayed(WebElement el) {
        try {
            return el.isDisplayed();
        } catch (StaleElementReferenceException ex) {
            return false;
        } catch (NoSuchElementException e) {
            return false;
        }
    }
}