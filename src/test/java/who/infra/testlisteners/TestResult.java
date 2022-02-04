package who.infra.testlisteners;

import io.qameta.allure.Attachment;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class TestResult implements ITestListener{

    protected static Logger logger = LogManager.getLogger("Listener");

    @Attachment
    public byte[] takeScreenShot(RemoteWebDriver driver) {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }

    @Override
    public void onTestStart(ITestResult result) {

        logger.info("Starting test - '" + result.getName() + "'.");

    }

    @Override
    public void onTestSuccess(ITestResult result) {

        logger.info("Test '" + result.getName() + "' passed successfully.");

    }

    @Override
    public void onTestFailure(ITestResult result) {

        logger.info("Test '" + result.getName() + "' failed to pass on attempt number.\nFailure reason: \n" + result.getThrowable());

    }

    @Override
    public void onTestSkipped(ITestResult result) {

    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {

    }

    @Override
    public void onStart(ITestContext context) {

    }

    @Override
    public void onFinish(ITestContext context) {

    }


}
