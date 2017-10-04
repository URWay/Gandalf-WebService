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
    Connection con = null;
    
    public Conexao() throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
        String url = "gandalf-pi.database.windows.net";
        String user = "TSI";
        String password = "SistemasInternet123";
        
        Class.forName("com.mysql.jdbc.Driver").newInstance();
        con = DriverManager.getConnection(url, user, password);
    }

    PreparedStatement prepareStatement(String query) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
