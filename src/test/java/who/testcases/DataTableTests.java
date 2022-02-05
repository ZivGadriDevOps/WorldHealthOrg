package who.testcases;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriverException;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import who.infra.core.TestBase;
import who.infra.pageobjects.DataTable;
import who.infra.utils.DataTableColumns;
import who.infra.utils.RunMode;

import static who.infra.utils.PageLinks.BASE_URL;
import static who.infra.utils.PageLinks.DATA_TABLE_PAGE;

public class DataTableTests extends TestBase {

    protected DataTable dataTable;

    @Override
    protected DriverType getDriverType() {
        return getWebDriverType();
    }

    @BeforeMethod(alwaysRun = true)
    public void beforeMethod() {

        try {
            logger.info("BeforeMethod: open URL: " + BASE_URL);
            getDriver().get(DATA_TABLE_PAGE);
        } catch (WebDriverException wde) {
            wde.printStackTrace();
        }

    }

    @Test(groups = {RunMode.SANITY, RunMode.DATA_TABLE_PAGE}, description = "Verify the number of cases cumulative in Global is bigger than in the United States of America.")
    public void testNumberOfGlobalCasesIsBiggerThanInUSA() {

        String globalNameInTable = "Global";
        String UsaNameInTable = "United States of America";

        dataTable = new DataTable(getDriver());

        String globalCases = dataTable.getDataFromColumnByCountryAndTitleNames(globalNameInTable, DataTableColumns.Cases_Cumulative);
        String UsaCases = dataTable.getDataFromColumnByCountryAndTitleNames(UsaNameInTable, DataTableColumns.Cases_Cumulative);

        verifyTestedParameterNumberIsHigherThanAnotherNumber(globalCases, UsaCases);
    }

    @TestID(id = "1111")
    @Test(groups = {RunMode.REGRESSION, RunMode.DATA_TABLE_PAGE}, description = "Verify the number of cases cumulative in Global Latest is bigger than in yesterday")
    public void testNumberOfGlobalCasesTodayIsBiggerThanYesterday() {

        String globalNameInTable = "Global";

        dataTable = new DataTable(getDriver());

        String globalCasesToday = dataTable.getDataFromColumnByCountryAndTitleNames(globalNameInTable, DataTableColumns.Cases_Cumulative);

        String globalCasesYesterday = dataTable
                .clickYesterdayButton()
                .waitForDataToLoad()
                .getDataFromColumnByCountryAndTitleNames(globalNameInTable, DataTableColumns.Cases_Cumulative);

        verifyTestedParameterNumberIsHigherThanAnotherNumber(globalCasesToday, globalCasesYesterday);
    }


    @TestID(id = "2222")
    @Step("Verify the number of tested parameter is larger than a minimum expected.")
    private void verifyTestedParameterNumberIsHigherThanAnotherNumber(String higherExpected, String lowerExpected) {

        int higherExpectedInt = Integer.parseInt(higherExpected.replace(",", ""));
        int lowerExpectedInt = Integer.parseInt(lowerExpected.replace(",", ""));

        Assert.assertTrue(higherExpectedInt > lowerExpectedInt, "The expected higher number is lower or equals to the expected lower number.");

    }

}
