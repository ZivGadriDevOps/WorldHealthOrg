package who.infra.core.driverfactory;

import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import who.infra.utils.TestHelper;

import java.io.IOException;
import java.net.URL;

public class CommonWebFirefoxDriver extends DriverInit {

    protected static final String PLATFORM = "firefox";

    public CommonWebFirefoxDriver(DesiredCapabilities caps) {
        super(caps);
    }

    @Override
    protected DesiredCapabilities getPlatformSpecificCapabilities() {

        DesiredCapabilities capabilities = DesiredCapabilities.firefox();
        FirefoxOptions options = new FirefoxOptions();
        options.setProfile(new FirefoxProfile());
        options.addPreference("dom.webnotifications.enabled", false);

        capabilities.setCapability(FirefoxOptions.FIREFOX_OPTIONS, options);

        return capabilities;
    }

    @Override
    public void createDriver() throws IOException {

        DesiredCapabilities capabilities = getPlatformSpecificCapabilities();
        capabilities.merge(this.capabilities);

        threadLocalDriver.set(new RemoteWebDriver(new URL("http://127.0.0.1:4444/wd/hub"), capabilities));

        TestHelper.setPlatform(PLATFORM);
    }

    @Override
    public String getPlatform() {
        return PLATFORM;
    }

}
