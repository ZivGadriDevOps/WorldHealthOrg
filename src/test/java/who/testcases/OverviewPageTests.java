package who.testcases;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriverException;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import who.infra.core.TestBase;
import who.infra.pageobjects.OverviewPage;
import who.infra.utils.RunMode;

import static who.infra.utils.PageLinks.BASE_URL;

public class OverviewPageTests extends TestBase {

    protected OverviewPage overviewPage;

    @Override
    protected DriverType getDriverType() {
        return getWebDriverType();
    }

    @BeforeMethod(alwaysRun = true)
    public void beforeMethod() {

        try {
            logger.info("BeforeMethod: open URL: " + BASE_URL);
            getDriver().get(BASE_URL);
        } catch (WebDriverException wde) {
            wde.printStackTrace();
        }

    }

    @Test(groups = {RunMode.SANITY, RunMode.OVERVIEW_PAGE}, description = "Test whether the number of deaths is bigger than 4,000,000")
    public void testNumberOfDeaths() {

        int minNumOfDeaths = 4000000;

        overviewPage = new OverviewPage(getDriver());

        String actualNumOfDeaths = overviewPage.getNumberOfDeaths();

        verifyTestedParameterNumberIsHigherThanAMinNumber(actualNumOfDeaths, minNumOfDeaths);
    }

    @Test(groups = {RunMode.SANITY, RunMode.OVERVIEW_PAGE}, description = "Test whether the number of confirmed cases is bigger than 100,000")
    public void testNumberOfConfirmedCases() {

        int minNumOfConfirmedCases = 100000;

        overviewPage = new OverviewPage(getDriver());

        String actualNumOfConfirmedCases = overviewPage.getNumberOfConfirmedCases();

        verifyTestedParameterNumberIsHigherThanAMinNumber(actualNumOfConfirmedCases, minNumOfConfirmedCases);
    }


    @Step("Verify the number of tested parameter is larger than a minimum expected.")
    private void verifyTestedParameterNumberIsHigherThanAMinNumber(String actual, int minExpected) {

        int actualNumProcessed = Integer.parseInt(actual.replace(",", ""));

        Assert.assertTrue(actualNumProcessed > minExpected, "The actual number of tested parameter is smaller than the minimum expected.\n" +
                "Expected more than " + minExpected + ", but found " + actualNumProcessed + "." );

    }

}
