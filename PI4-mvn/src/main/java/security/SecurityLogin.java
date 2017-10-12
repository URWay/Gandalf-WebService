package security;

import controller.Clientes;
import modelos.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import modelos.Login;
import modelos.Cliente;

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
            String id = rs.getString("idCliente");
            
            if(!email.equals("") && !senha.equals("") ){
                Clientes cliente = new Clientes();
                // resgata o cliente logado
                //cliente.getCliente(id);              
                retorno = true;
            }
        }
        
        return retorno;
    }
    
    public boolean validaUsuario(Cliente cliente){
        boolean retorno = true;
      //  Gson gson = new Gson();
        //String json = gson.toJson(cliente);
        //Login login = gson.fromJson(json, Login.class);
        
      //  if(!login.getEmailCliente().equals("") && !login.getSenhaCliente().equals("")){
            retorno = false;
       // }
        
        return retorno;
    }
    
}
