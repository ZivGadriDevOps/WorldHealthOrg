package who.infra.testlisteners;

import org.testng.IAlterSuiteListener;
import org.testng.xml.XmlSuite;

import java.util.List;

public class SuiteModify implements IAlterSuiteListener {

    @Override
    public void alter(List<XmlSuite> suites) {
        for(XmlSuite suite:suites) {
            suite.setName(System.getProperty("stageName","LocalSuite"));
            //suite.getTests().get(0).setParameters();

        }
    }
}
