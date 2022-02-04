package who.infra.utils;

import org.testng.Assert;

public class TestHelper {

    protected static ThreadLocal<String> threadLocalPlatform = new ThreadLocal<>();

    public static synchronized void setPlatform(String platform){
        threadLocalPlatform.set(platform);
    }
    public static synchronized String getPlatform(){
        return threadLocalPlatform.get();
    }

    public static void waitSeconds(int secs) {
        try {
            Thread.sleep(secs * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static String getDataTableColumnTitle(DataTableColumns titleSubject) {

        String result = "";
        switch (titleSubject) {
            case Name: result = "Name";
                break;
            case Cases_Cumulative: result = "Cases - cumulative total";
                break;
            case Cases_Newly_Reported: result = "Cases - newly reported in last 7 days";
                break;
            case Deaths_Cumulative: result = "Deaths - cumulative total";
                break;
            case Deaths_Newly_Reported: result = "Deaths - newly reported in last 7 days";
                break;
            case Total_Vaccine: result = "Total vaccine doses administered per 100 population";
                break;
            case Persons_Fully_Vaccinated: result = "Persons fully vaccinated per 100 population";
            default:
                Assert.fail("No Data column name found for title subject " + titleSubject.toString());
        }
        return result;
    }
}
