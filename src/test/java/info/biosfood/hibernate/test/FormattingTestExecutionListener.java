package info.biosfood.hibernate.test;

import org.apache.log4j.Logger;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

public class FormattingTestExecutionListener extends DependencyInjectionTestExecutionListener {

    private static final Logger LOG = Logger.getLogger(FormattingTestExecutionListener.class);

    public void beforeTestClass(TestContext testContext) {
        LOG.debug(Formatting.formatTestClass(testContext.getTestClass().getSimpleName()));
    }

    public void beforeTestMethod(TestContext testContext) throws Exception {
        LOG.debug(Formatting.formatTestMethod(testContext.getTestMethod().getName()));
    }

}
