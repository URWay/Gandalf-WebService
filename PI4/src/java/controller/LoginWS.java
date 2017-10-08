package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import modelos.Cliente;
import modelos.Login;
import security.SecurityLogin; 

@Path("/login")
public class LoginWS {
    
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response Login(Cliente cliente) throws Exception {
        // Validações do Login
        SecurityLogin s = new SecurityLogin();
        
        // Validção Usuário
        if(s.validaUsuario(cliente)){
            Login login = new Login(); 
            
            try {
                // Validção Login
                if(s.validaLogin(login)){
                    return Response.status(200).entity(login).build();
                } else {
                    return Response.status(401).build();
                }

            } catch(Exception ex){
                return Response.status(500).entity(null).build();
            }
            
        } else {
            return Response.status(401).build();
        }  
    }
    
}
