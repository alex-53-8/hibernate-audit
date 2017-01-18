package info.biosfood.hibernate.servers;

import org.apache.log4j.Logger;
import org.dbunit.PropertiesBasedJdbcDatabaseTester;
import org.hsqldb.Server;
import org.hsqldb.persist.HsqlProperties;
import org.hsqldb.server.ServerAcl;
import java.io.IOException;

public final class InMemoryDatabaseServer {
  
    private static final Logger log = Logger.getLogger(InMemoryDatabaseServer.class);
  
    private Server hsqlServer;
    private String dbName;
    private int port;

    public InMemoryDatabaseServer(String dbName) {
        this(dbName, -1);
    }

    public InMemoryDatabaseServer(String dbName, int port) {
        this("jdbc:hsqldb:mem:" + dbName, "org.hsqldb.jdbcDriver", "sa", "", dbName, port);
    }

    public InMemoryDatabaseServer(String url, String driverName, String user, String password, String dbName, int port) {
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_DRIVER_CLASS, driverName);
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_CONNECTION_URL, url);
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_USERNAME, user);
        System.setProperty(PropertiesBasedJdbcDatabaseTester.DBUNIT_PASSWORD, password);

        this.dbName = dbName;
        this.port = port;
    }  

    protected void start(final HsqlProperties props) throws IOException, ServerAcl.AclFormatException {
        log.info("Configuring the in-memory server...");

        hsqlServer = new Server();  
        hsqlServer.setRestartOnShutdown(false);  
        hsqlServer.setNoSystemExit(true);  
        hsqlServer.setProperties(props);  
  
        log.info("Configured the in-memory server.");
        log.info("Starting the in-memory server...");

        hsqlServer.start();

        log.info("In-memory server started on port " + hsqlServer.getPort());
    }

    public void start() {
        HsqlProperties props = new HsqlProperties();

        if(port > 0) {
            props.setProperty("server.port", port);
        }

        props.setProperty("server.database.0", dbName);
        props.setProperty("server.dbname.0", dbName);

        try {
            start(props);
        } catch (IOException | ServerAcl.AclFormatException e) {
            throw new RuntimeException("Unable to start an in-memory server", e);
        }
    }

    /**
     * shutdown the started instance.
     */
    public void stop() {
        log.info("In-memory server shutting down...");

        hsqlServer.stop();

        log.info("In-memory server shutting down... done.");
    }

} 