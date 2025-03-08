package vn.edu.hcmuaf.fit.webbanquanao.database;

import com.mysql.cj.jdbc.MysqlDataSource;
import org.jdbi.v3.core.Jdbi;

import java.sql.SQLException;

public class JDBIConnector {
    private static Jdbi jdbi;

    public static Jdbi get() {
        if(jdbi == null) connect();
        return jdbi;
    }
    public static void connect() {
        MysqlDataSource ds = new MysqlDataSource();
        ds.setURL("jdbc:mysql://" + Properties.HOST + ":" + Properties.PORT + "/" + Properties.DBNAME);
        ds.setUser(Properties.USERNAME);
        ds.setPassword(Properties.PASSWORD);
        try {
            ds.setAutoReconnect(true);
            ds.setUseCompression(true);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        jdbi = Jdbi.create(ds);
    }


}