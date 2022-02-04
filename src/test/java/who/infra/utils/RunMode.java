package who.infra.utils;

// RunMode can be used to group tests and can be controlled through Jenkinsfile and/or testNG xmls
public class RunMode {

        public static final String SMOKE = "smoke";
        public static final String SANITY = "sanity";
        public static final String REGRESSION = "regression";

        public static final String OVERVIEW_PAGE = "overview_page";
        public static final String DATA_TABLE_PAGE = "regression";


}
