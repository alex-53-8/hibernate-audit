package info.biosfood.hibernate.test;

import info.biosfood.hibernate.servers.InMemoryDatabaseServer;
import org.apache.log4j.Logger;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate4.SessionFactoryUtils;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import java.sql.Connection;

public class DatasetTestExecutionListener extends DependencyInjectionTestExecutionListener {

    private static final Logger log = Logger.getLogger(DatasetTestExecutionListener.class);

    protected Dataset datasetProperties;

    protected InMemoryDatabaseServer server;

    @Override
    public void afterTestClass(TestContext testContext) {
        if(server != null) {
            server.stop();
        }
    }

    @Override
    public void beforeTestClass(TestContext testContext) {
        datasetProperties = testContext.getTestClass().getAnnotation(Dataset.class);

        if(datasetProperties.useImMemoryServer()) {
            startServer(datasetProperties);
        }

        if(datasetProperties == null) {
            log.debug("No " + Dataset.class.getName() + " annotation specified, database has not been populated");
            return;
        }

        populateDatabase(testContext, datasetProperties);
    }

    protected void startServer(Dataset datasetProperties) {
        server = new InMemoryDatabaseServer(datasetProperties.database());
        server.start();
    }

    protected void populateDatabase(TestContext testContext, Dataset datasetProperties) {
        log.debug("Populating database...");
        try {
            DatabaseOperation.CLEAN_INSERT.execute(
                    getConnection(testContext, datasetProperties.sessionFactoryBean()),
                    getDataSet(datasetProperties.file())
            );
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
        log.debug("Done population database.");

    }

    protected IDataSet getDataSet(String fileName) throws Exception {
        return new FlatXmlDataSetBuilder()
                .setColumnSensing(true)
                .setCaseSensitiveTableNames(false)
                .build(this.getClass().getResourceAsStream(fileName));
    }

    private IDatabaseConnection getConnection(TestContext testContext, String sessionFactoryBean) throws Exception {
        SessionFactory sessionFactory =
                (SessionFactory) testContext.getApplicationContext().getBean(sessionFactoryBean);
        Connection jdbcConnection = SessionFactoryUtils.getDataSource(sessionFactory).getConnection();

        return new DatabaseConnection(jdbcConnection);
    }
}
