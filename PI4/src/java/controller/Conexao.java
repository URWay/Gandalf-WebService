package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import modelos.Usuario;

/**
 * @author jefferson
 */


public class Conexao {
      
    private static Conexao instance = null;
    private Conexao () {};
    public static Conexao get () {
		if (instance == null)
			instance = new Conexao();
		return instance;
	}
 
    
    public Connection conn () throws Exception {
            String hostName = "gandalf-pi.database.windows.net";
            String dbName = "gandalf";
            String user = "TSI";
            String password = "SistemasInternet123";
            String url = String.format("jdbc:sqlserver://%s:1433;database=%s;user=%s;password=%s;encrypt=true;hostNameInCertificate=*.database.windows.net;loginTimeout=30;", hostName, dbName, user, password);
     
        
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");	
            Connection conn = DriverManager.getConnection(url);
            return conn;
    }

   
    
}

