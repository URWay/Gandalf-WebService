package controller;

import modelos.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.DELETE;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;

import modelos.Cliente;
import modelos.Login;
import org.codehaus.jettison.json.JSONObject;

@Path("/cliente")
public class Clientes {
    
    @GET
    @Path("/{param}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCliente(@PathParam("param") String idCliente) throws Exception{
        
        String id = idCliente;
        Cliente c = null;
        
        try {
            
            Connection con = Conexao.get().conn();
            PreparedStatement preparedStatement = con.prepareStatement("SELECT * FROM cliente WHERE idCliente = ?");
            preparedStatement.setInt(1, Integer.parseInt(id));
            ResultSet rs = preparedStatement.executeQuery();
            
            if(rs != null){
                if(rs.next()){
                    c = new Cliente();
                    c.setIdCliente(rs.getInt("idCliente"));
                    c.setSenhaCliente("senhaCliente");
                    c.setNomeCompletoCliente(rs.getString("nomeCompletoCliente"));
                    c.setEmailCliente(rs.getString("emailCliente"));
                    c.setCPFCliente(rs.getString("CPFCliente"));
                    c.setCelularCliente(rs.getString("celularCliente"));
                    c.setTelComercialCliente(rs.getString("telComercialCliente"));
                    c.setTelResidencialCliente(rs.getString("telResidencialCliente"));
                    c.setDtNascCliente(rs.getString("dtNascCliente"));
                    c.setRecebeNewsLetter(rs.getInt("recebeNewsLetter"));
                }
            }   
            
        } catch(Exception ex){
            return Response.status(500).entity(null).build();
        }
        
        if(c == null) {
            return Response.status(406).entity(c).build();
        } else {
            return Response.status(200).entity(c).build();
        }
    }
    
    @POST
    @Path("/inserir")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response inserirUsuario(String content) throws Exception{
        JSONObject object = new JSONObject(content);
        
        if (!object.isNull(content)){
            return Response.status(400).build();
        }
        
        String email = object.getString("emailCliente");
        String senha = object.getString("senhaCliente");
       
        Login login = new Login(email, senha, 0);
        
        String sql = "INSERT INTO cliente ("
                + "nomeCompletoCliente, "
                + "emailCliente, "
                + "senhaCliente, "
                + "CPFCliente, "
                + "celularCliente, "
                + "telComercialCliente, "
                + "telResidencialCliente, "
                + "dtNascCliente, "
                + "recebeNewsLetter) "
                + "VALUES(?,?,?,?,?,?,?,?,?)";
        
        try {
            Connection con = Conexao.get().conn();

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, object.getString("nomeCompletoCliente"));
            ps.setString(2, object.getString("emailCliente")); 
            ps.setString(3, object.getString("senhaCliente"));
            ps.setString(4, object.getString("CPFCliente"));
            ps.setString(5, object.getString("celularCliente"));
            ps.setString(6, object.getString("telComercialCliente"));
            ps.setString(7, object.getString("telResidencialCliente"));
            ps.setString(8, object.getString("dtNascCliente"));
            ps.setInt(9, object.getInt("recebeNewsLetter"));
       
            if(ps.executeUpdate() > 0){
                return Response.status(200).entity(login).build();
            } else {
                return Response.status(404).build();
            }

        } catch(Exception ex){
            return Response.status(500).entity(null).build();
        }       
    }
    
    @POST
    @Path("/isEmail")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response isEmail(String email) throws Exception{
        // Verificar se já exite email igual
        try {
            JSONObject object = new JSONObject(email);
            Connection con = Conexao.get().conn();
            
            PreparedStatement preparedStatement = con.prepareStatement("SELECT emailCliente FROM Cliente WHERE emailCliente = ?");
            preparedStatement.setString(1, object.getString("emailCliente"));
            ResultSet rs = preparedStatement.executeQuery();
            
            if(rs != null){
                if(rs.next()){
                    return Response.ok().build();
                }else {
                    return Response.status(204).build();
                }
            }   
            
        } catch(Exception ex){
           return Response.status(500).build();
        }
        
        return Response.status(404).build();
    }
    
    @PUT
    @Path("/atualizar")
    @Consumes(MediaType.APPLICATION_JSON) 
    public Response alterarUsuario(String content) throws Exception{
                
        JSONObject object = new JSONObject(content);
        
        if (!object.isNull(content)){
            return Response.status(400).build();
        }
            
        String sql = "UPDATE Cliente SET " 
                + "nomeCompletoCliente = ?, "
                + "emailCliente = ?, "
                + "senhaCliente = ?, "
                + "CPFCliente = ?, "
                + "celularCliente = ?, "
                + "telComercialCliente = ?, "
                + "telResidencialCliente = ?, "
                + "dtNascCliente = ?, "
                + "recebeNewsLetter = ? "
                + "WHERE idCliente = ?"; 

        try {
            Connection con = Conexao.get().conn();

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, object.getString("nomeCompletoCliente"));
            ps.setString(2, object.getString("emailCliente")); 
            ps.setString(3, object.getString("senhaCliente"));
            ps.setString(4, object.getString("CPFCliente"));
            ps.setString(5, object.getString("celularCliente"));
            ps.setString(6, object.getString("telComercialCliente"));
            ps.setString(7, object.getString("telResidencialCliente"));
            ps.setString(8, object.getString("dtNascCliente"));
            ps.setInt(9, object.getInt("recebeNewsLetter"));
            ps.setInt(10, object.getInt("idCliente"));

            if(ps.executeUpdate() > 0){
                return Response.ok().build();
            } else {
                return Response.status(404).build();
            }

        } catch(Exception ex){
            return Response.status(500).entity(null).build();
        }
    }
    
    @DELETE
    @Path("/deletar/{param}")
    public Response deleteCliente(@PathParam("param") String idCliente){
        
        if (!idCliente.equals("")) {
            String sql = "DELETE FROM Cliente WHERE idCliente = ?";
            
            try {
                Connection con = Conexao.get().conn();    
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setInt(1, Integer.parseInt(idCliente));
                
                if(ps.executeUpdate() > 0){
                    return Response.ok().build();
                } else {
                    return Response.status(404).build();
                }
                
            } catch(Exception ex){
                return Response.status(500).entity(null).build();
            }
        } else {
            return Response.status(406).build();
        }
    }
}