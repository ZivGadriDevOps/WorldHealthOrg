package who.infra.pageobjects;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import who.infra.utils.Extended_Actions;

public class OverviewPage extends Extended_Actions {

    public OverviewPage(RemoteWebDriver driver) {
        super();
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//span[text()='cumulative Deaths']/parent::*/span[@data-id='metric']")
    public WebElement numOfDeaths;

    @FindBy(xpath = "//h2[@class='sc-qQmou jqDvEh']//span[text()[contains(.,'confirmed cases')]]")
    public WebElement numOfConfirmedCases;

    public String getNumberOfDeaths() {
        return getElementText(numOfDeaths);
    }

    public String getNumberOfConfirmedCases() {
        String fullElemText = getElementText(numOfConfirmedCases);
        return fullElemText.split(" ")[0];
    }

}
