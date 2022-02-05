package who.infra.core;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.ITestNGMethod;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import who.infra.core.driverfactory.DriverInit;
import who.infra.testlisteners.TestResult;
import who.infra.testlisteners.TestRetry;
import who.infra.core.driverfactory.*;

import java.io.IOException;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

@Listeners({TestResult.class})
public abstract class TestBase {

    protected static Logger logger = LogManager.getLogger("tests");

    public static int PAGE_LOAD_TIMEOUT = 60;
    public static int IMPLICIT_WAIT_TIMEOUT = 30;
    public static boolean RUN_CHROME_HEADLESS = false;

    //init driver factory
    public enum DriverType {WEB_CHROME, WEB_IE11, WEB_FIREFOX}
    public static final String WEB_PLATFORM = "chrome";
    public static boolean ENABLE_TEST_RETRY = false;
    public static final int TEST_MAX_RETRIES = 2;
    protected DriverInit driverInit = null;

    protected RemoteWebDriver driver;
    protected abstract DriverType getDriverType();

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    public @interface TestID {
        String id() default "0";
    }


    @BeforeClass(alwaysRun = true)
    public synchronized void beforeClassBase(ITestContext context) {

        logger.info("BeforeClass: setting test retry mode...");

        if (ENABLE_TEST_RETRY) {
            for (ITestNGMethod method : context.getAllTestMethods()) {
                method.setRetryAnalyzerClass(TestRetry.class);
            }
        }
    }

    @BeforeMethod(alwaysRun = true)
    public synchronized void beforeMethodBase() {

        DesiredCapabilities caps = new DesiredCapabilities();

        try {
            logger.info("BeforeMethod: starting MCC driver...");
            driverInit = getDriverInit(getDriverType(), caps);
            driverInit.createDriver();
            driver = driverInit.getDriver();
            setWebDriverCommonManage(driver);
        } catch (WebDriverException | IOException wde) {
            Assert.fail("Failed to create driver.\n" + "Exception: " + wde.getMessage());
        }
    }

    @AfterMethod(alwaysRun = true)
    public synchronized void afterMethodBase() {
        if (getDriver() != null) {
            logger.info("AfterMethod: quit driver, driver type: " + getDriverType().toString());
            getDriver().quit();
            driverInit.removeDriver();
        } else {
            logger.error("AfterMethod: driver is null, driver type: " + getDriverType().toString());
        }
    }

    public RemoteWebDriver getDriver () {
        return this.driver;
    }

    protected DriverInit getDriverInit(DriverType driverType, DesiredCapabilities capabilities) throws IOException {

        DriverInit driverInit;

        switch (driverType){
            case WEB_CHROME : driverInit = new CommonWebChromeDriver(capabilities); break;
            case WEB_FIREFOX: driverInit = new CommonWebFirefoxDriver(capabilities); break;
            case WEB_IE11 : driverInit = new CommonWebIE11Driver(capabilities); break;
            default: throw new RuntimeException("Unknown driver type...");
        }

        return driverInit;
    }

    protected synchronized DriverType getWebDriverType() {
        DriverType driverType;
        switch (WEB_PLATFORM){
            case "chrome" : driverType = DriverType.WEB_CHROME; break;
            case "edge" : driverType = DriverType.WEB_IE11; break;
            case "firefox" : driverType = DriverType.WEB_FIREFOX; break;
            default: throw new RuntimeException("Unknown mcc web driver type...");
        }
        return driverType;
    }

    protected synchronized void setWebDriverCommonManage(RemoteWebDriver driver) {
        driver.manage().window().maximize();
        logger.info("Browser window size: " + driver.manage().window().getSize().toString());
        driver.manage().timeouts().pageLoadTimeout(PAGE_LOAD_TIMEOUT, TimeUnit.SECONDS);
        driver.manage().timeouts().implicitlyWait(IMPLICIT_WAIT_TIMEOUT, TimeUnit.SECONDS);
    }

}
