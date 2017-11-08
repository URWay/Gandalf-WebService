package security;

import modelos.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import modelos.Login;

public class SecurityLogin {
    
    public boolean validaLogin(Login login) throws Exception{
        
        boolean retorno = false;
        
        Connection con = Conexao.get().conn();
        String sql = "SELECT idCliente, emailCliente, senhaCliente FROM Cliente WHERE emailCliente = ? AND senhaCliente = ?";

        PreparedStatement ps = con.prepareStatement(sql); 
        ps.setString(1, login.getEmailCliente());
        ps.setString(2, login.getSenhaCliente());
        ResultSet rs = ps.executeQuery();
        
        boolean hasResult = rs.next();
        
        if(hasResult){
            String email = rs.getString("emailCliente");
            String senha = rs.getString("senhaCliente");
            int id = rs.getInt("idCliente");
            
            if(!email.equals("") && !senha.equals("") ){          
                retorno = true;
                login.setEmailCliente(email); 
                login.setSenhaCliente(email); 
                login.setIdCliente(id);
            }
        }
        
        return retorno;
    }
    
    public boolean validaUsuario(Login login){
        boolean retorno = true;
        
        if(!login.getEmailCliente().equals("") && !login.getSenhaCliente().equals("")){
            retorno = false;
        }
        return retorno;
    }
    
}
