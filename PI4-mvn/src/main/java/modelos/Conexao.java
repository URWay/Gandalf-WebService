package modelos;

import java.sql.Connection;
import java.sql.DriverManager;

public class Conexao {
      
    private static Conexao instance = null;
    private Conexao () {};
    public static Conexao get () {
        if (instance == null)
                instance = new Conexao();
        return instance;
    }
    public Connection conn () throws Exception {
        String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
        String user = "TSI@gandalf-pi.database.windows.net";
        String password = "SistemasInternet123";
        String url = String.format("jdbc:sqlserver://gandalf-pi.database.windows.net;database=gandalf");

        Class.forName(driver);
        Connection conn = DriverManager.getConnection(url, user, password);

        return conn;
    }
}

