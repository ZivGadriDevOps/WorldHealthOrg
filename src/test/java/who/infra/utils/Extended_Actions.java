package who.infra.utils;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class Extended_Actions {

    protected static Logger logger = LogManager.getLogger("Page Objects");

    protected RemoteWebDriver driver;

    protected static String getElementText(WebElement element) {

        String text = null;
        try {
            text = element.getText();
        } catch (WebDriverException wde) {
            wde.printStackTrace();
            Assert.fail("Action failed. Failure reason: " + wde.getMessage());
        }
        return text;
    }

    protected static void waitUntilElementVisible(RemoteWebDriver driver, WebElement element, int timeoutInSec) {
        WebDriverWait wait = new WebDriverWait(driver, timeoutInSec);
        try {
            wait.until(ExpectedConditions.visibilityOf(element));
        } catch (WebDriverException wde) {
            wde.printStackTrace();
            Assert.fail("Action failed. Failure reason: " + wde.getMessage());
        }
    }

    protected static void clickOnElement(WebElement element) {

        try {
            element.click();
            TestHelper.waitSeconds(1);
        } catch (WebDriverException wde) {
            wde.printStackTrace();
            Assert.fail("Action failed. Failure reason: " + wde.getMessage());
        }

    }


}
