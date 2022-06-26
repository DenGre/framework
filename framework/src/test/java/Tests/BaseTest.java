package Tests;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;

import java.io.File;
import java.io.IOException;

public class BaseTest {
    /* Using variables to short the timework */
    WebDriver driver;
    String email = "dhryhoryev@gmail.com";
    String password = "123456789Aa";
    String website = "ssds";

    /* Setting up the chrome driver via WebDriveManage, so it will always be updated and not stored locally */
    @BeforeClass
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get(website);

    }
    /* Refreshing the page when needed */

    public void refreshPage() {
        driver.navigate().refresh();
    }

    /* After launching the class it should quit after that (closing the web browser) */
    @AfterClass
    public void tearDown() {
        driver.quit();
    }

    /*
     * This method will run after each test,
     * it will take screenshot only for tests that failed
     */
    @AfterMethod
    public void failedTest(
            ITestResult result) {
        //check if the test failed
        if (result.getStatus() == ITestResult.FAILURE) {
            TakesScreenshot ts = (TakesScreenshot) driver;
            File srcFile = ts.getScreenshotAs(OutputType.FILE);
            try {
                FileUtils.copyFile(srcFile, new File("./ScreenShots/" + result.getName() + ".jpg"));
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            //result.getname() method will give you current test case name.
            //./ScreenShots/ tell you that, in your current directory, create folder ScreenShots. dot represents current directory
        }
    }
}
