package controller;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import modelos.Login;
import org.codehaus.jettison.json.JSONObject;
import security.SecurityLogin; 

@Path("/login")
public class LoginWS {
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response Login(String content) throws Exception {
       JSONObject object = new JSONObject(content);
       String email = object.getString("emailCliente");
       String senha = object.getString("senhaCliente");
       
       Login login = new Login(email, senha);
       
       // Validações do Login
       SecurityLogin s = new SecurityLogin();
       
       // Validção Usuário
       if(s.validaUsuario(login)){
           return Response.status(500).entity(null).build();
       }
       
        try {
            // Validção Login
            if(s.validaLogin(login)){
                return Response.status(200).entity(login).build();
            } else {
                return Response.status(400).entity(null).build();
            }
        } catch(Exception ex){
            return Response.status(500).entity(null).build();
        }
    } 
}
