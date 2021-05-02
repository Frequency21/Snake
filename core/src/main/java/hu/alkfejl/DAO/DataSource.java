package hu.alkfejl.DAO;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import javafx.scene.control.Alert;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Singleton class for connection pooling with hikariCP
 * source: https://www.baeldung.com/hikaricp
 */
public class DataSource {

    private static HikariConfig config = new HikariConfig();
    private static HikariDataSource ds;
    private static Properties properties = new Properties();

    static {
        try {
            /* needs for the web part, desktop part is fine without this
             * i have no clue why.. */
            Class.forName("org.sqlite.JDBC");
            properties.load(DataSource.class.getResourceAsStream("/application.properties"));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Configuration error");
            alert.setContentText("Missing application.properties");
            alert.showAndWait();
            System.exit(1);
        }
        config.setJdbcUrl(properties.getProperty("db.url"));
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        ds = new HikariDataSource(config);
    }

    private DataSource() { }

    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }
}