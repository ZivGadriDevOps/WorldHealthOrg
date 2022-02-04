package who.infra.testlisteners;

//NEW ADDITION
import io.qameta.allure.Attachment;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.remote.RemoteWebDriver;
//NEW ADDITION
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;
import who.infra.core.TestBase;

public class TestRetry implements IRetryAnalyzer {

    private static int maxTry = TestBase.TEST_MAX_RETRIES;
    private int count = 0;

    @Attachment
    public byte[] takeScreenShot(RemoteWebDriver driver) {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }

    @Override
    public boolean retry(ITestResult iTestResult) {
        if (!iTestResult.isSuccess()) {                      //Check if test not succeed
            if (count < maxTry) {                            //Check if maxTry count is reached
                count++;                                     //Increase the maxTry count by 1
                iTestResult.setStatus(ITestResult.FAILURE);  //Mark test as failed
                takeScreenShotBeforeRetry(iTestResult);      //Takes screenshot to attach to Allure report
                return true;                                 //Tells TestNG to re-run the test
            } else {
                iTestResult.setStatus(ITestResult.FAILURE);  //If maxCount reached,test marked as failed
            }
        } else {
            iTestResult.setStatus(ITestResult.SUCCESS);      //If test passes, TestNG marks it as passed
        }
        return false;
    }

    private void takeScreenShotBeforeRetry(ITestResult result) {
        Object currentClass = result.getInstance();
        RemoteWebDriver driver;
        TestBase tb = ((TestBase) currentClass);
        if (tb.getDriver() != null){
            try {
                driver = tb.getDriver();
                takeScreenShot(driver);
            } catch (Exception ex){
                ex.printStackTrace();
            }
        }
    }

}
