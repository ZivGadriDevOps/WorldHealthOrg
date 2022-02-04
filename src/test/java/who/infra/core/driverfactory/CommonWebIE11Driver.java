package who.infra.core.driverfactory;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import who.infra.utils.TestHelper;

import java.io.IOException;
import java.net.URL;

public class CommonWebIE11Driver extends DriverInit {

    protected static final String PLATFORM = "ie11";

    public CommonWebIE11Driver(DesiredCapabilities caps) {
        super(caps);
    }

    @Override
    protected DesiredCapabilities getPlatformSpecificCapabilities() {

        DesiredCapabilities capabilities = DesiredCapabilities.internetExplorer();

        capabilities.setCapability("ignoreProtectedModeSettings", true);
        capabilities.setCapability("ensureCleanSession", true);
        capabilities.setCapability("requireWindowFocus", true);
        capabilities.setCapability("nativeEvents", false);

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
