package who.infra.core.driverfactory;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.IOException;

public abstract class DriverInit {

    protected static Logger logger = LogManager.getLogger("DriverFactory");
    protected static ThreadLocal<RemoteWebDriver> threadLocalDriver = new ThreadLocal<>();
    protected DesiredCapabilities capabilities;


    public DriverInit(DesiredCapabilities capabilities) {
        this.capabilities = capabilities;
    }

    public synchronized RemoteWebDriver getDriver() {
        return threadLocalDriver.get();
    }

    public synchronized void removeDriver() {
        threadLocalDriver.remove();
    }

    public abstract void createDriver() throws IOException;

    public abstract String getPlatform();

    protected abstract DesiredCapabilities getPlatformSpecificCapabilities();


}
