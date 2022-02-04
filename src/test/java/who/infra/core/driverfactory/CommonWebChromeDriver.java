package who.infra.core.driverfactory;

import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import who.infra.core.TestBase;
import who.infra.utils.TestHelper;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class CommonWebChromeDriver extends DriverInit {

    protected static final String PLATFORM = "chrome";

    public CommonWebChromeDriver(DesiredCapabilities caps) {
        super(caps);
    }

    @Override
    protected DesiredCapabilities getPlatformSpecificCapabilities() {

        DesiredCapabilities capabilities = DesiredCapabilities.chrome();
        Map<String, Object> prefs = new HashMap<String, Object>();
        prefs.put("profile.default_content_setting_values.notifications", 1);
        ChromeOptions options = new ChromeOptions();
        options.addArguments("window-size=1920,1080");
        options.addArguments("--start-maximized");
        options.setExperimentalOption("useAutomationExtension", false);
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-browser-side-navigation");
        options.addArguments("--disable-infobars");
        options.setExperimentalOption("prefs", prefs);
        options.addArguments("--disable-backgrounding-occluded-windows");

        if (Boolean.valueOf(TestBase.RUN_CHROME_HEADLESS)) {
            options.addArguments("headless");
            options.addArguments("no-sandbox");
            options.addArguments("window-size=1920,1080");
        }

        capabilities.setCapability(ChromeOptions.CAPABILITY, options);

        return capabilities;
    }

    @Override
    public void createDriver() throws IOException {

        DesiredCapabilities capabilities = getPlatformSpecificCapabilities();
        capabilities.merge(this.capabilities);
        logger.info("Driver capabilities: " + capabilities.toString());
        String hubURL = "http://127.0.0.1:4444/wd/hub";
        threadLocalDriver.set(new RemoteWebDriver(new URL(hubURL), capabilities));

        TestHelper.setPlatform(PLATFORM);

    }

    @Override
    public String getPlatform() {return PLATFORM;}

}
