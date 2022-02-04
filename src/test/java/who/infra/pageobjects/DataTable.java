package who.infra.pageobjects;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;
import who.infra.utils.DataTableColumns;
import who.infra.utils.Extended_Actions;
import who.infra.utils.TestHelper;

import java.util.List;

public class DataTable extends Extended_Actions {

    public DataTable(RemoteWebDriver driver) {
        super();
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    private String latestBtnClassAttribute;

    @FindAll({@FindBy(xpath = "//div[@class='thead']//div[@class='th']")})
    public List<WebElement> tableColumns;

    @FindAll({@FindBy(xpath = "//div[@data-id='totals-header']/div/div")})
    public List<WebElement> globalDataColumns;

    @FindAll({@FindBy(xpath = "//div[@role='rowgroup']//div[@role='row']")})
    public List<WebElement> countriesColumns;

    @FindBy(xpath = "//button[@title='Yesterday']")
    public WebElement yesterdayBtn;

    @FindBy(xpath = "//button[@title='Latest']")
    public WebElement latestBtn;

    private int getColumnIndexInTable(DataTableColumns title) {
        int index = 0;

        for (WebElement column : tableColumns) {
            if (column.getText().equalsIgnoreCase(TestHelper.getDataTableColumnTitle(title))) {
                return index;
            }
            index++;
        }
        Assert.fail("Title provided was not found in table");
        return index;
    }

    public String getGlobalDataByTitle(DataTableColumns title) {
        return getElementText(globalDataColumns.get(getColumnIndexInTable(title)));

    }

    public String getDataFromColumnByCountryAndTitleNames(String countryName, DataTableColumns title) {

        if (countryName.equalsIgnoreCase("global")) {
            return getGlobalDataByTitle(title);
        } else {

            for (WebElement row : countriesColumns) {
                String iterationCountryName = getElementText(row.findElement(By.xpath("./div[@class='column_name td']/div")));
                if (iterationCountryName.equalsIgnoreCase(countryName)) {
                    String xpath = "//span[text()='" + countryName + "']/parent::*/parent::*/parent::*/div[" + (getColumnIndexInTable(title) + 1) + "]";
                    return getElementText(row.findElement(By.xpath(xpath)));
                }
            }
        }
        return "";
    }

    @Step("Clicking yesterday button")
    public DataTable clickYesterdayButton() {
        latestBtnClassAttribute = yesterdayBtn.getAttribute("class");
        clickOnElement(yesterdayBtn);
        TestHelper.waitSeconds(5);
        return this;
    }

    @Step("Wait for data table to load yesterday's data")
    public DataTable waitForDataToLoad() {
        int numOfCycles = 0;
        List<WebElement> yesterdayColoredBlue = driver.findElements(By.xpath("//button[@title='Yesterday' and @class='" + latestBtnClassAttribute + "']"));
        TestHelper.waitSeconds(5);
        while (!yesterdayColoredBlue.isEmpty() && numOfCycles < 12) {
            clickOnElement(yesterdayBtn);
            numOfCycles++;
            TestHelper.waitSeconds(5);
            yesterdayColoredBlue = driver.findElements(By.xpath("//button[@title='Yesterday' and @class='" + latestBtnClassAttribute + "']"));
        }
        if (!yesterdayColoredBlue.isEmpty()) {
            Assert.fail("Data in table was not refreshed after more than a minute.\nFailing test.");
        }
        return this;
    }

}
