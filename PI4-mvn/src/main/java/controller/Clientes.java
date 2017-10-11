package controller;

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
import javax.ws.rs.PathParam;

import modelos.Cliente;

@Path("/cliente")
public class Clientes {
    
    @GET
    @Path("/get/{idCliente}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCliente(@PathParam("idCliente") String idCliente) throws Exception{
        
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
                    c.setNomeCompletoCliente(rs.getString("nomeCompletoCliente"));
                    c.setEmailCliente(rs.getString("emailCliente"));
                    c.setCPFCliente(rs.getString("CPFCliente"));
                    c.setCelularCliente(rs.getString("celularCliente"));
                    c.setTelComercialCliente(rs.getString("telComercialCliente"));
                    c.setTelResidencialCliente(rs.getString("telResidencialCliente"));
                    c.setDtNascCliente(rs.getString("dtNascCliente"));
                    c.setRecebeNewsLetter(rs.getString("receberNewsLetter"));
                }
            }   
            
        } catch(Exception ex){
            return Response.status(500).entity(null).build();
        }
        
        if(c == null) {
            return Response.status(404).entity(c).build();
        } else {
            return Response.status(200).entity(c).build();
        }
    }
    
    @GET
    @Path("/teste")
    public String getTeste(){
        return "Olá";
    }
    
    @POST
    @Path("/inserir")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response inserirUsuario(Cliente cliente){
        
        String sql = "INSERT INTO cliente ("
                + "nomeCompletoCliente, "
                + "emailCliente, "
                + "senhaCliente,"
                + "CPFCliente,"
                + "celularCliente,"
                + "telComercialCliente,"
                + "telResidencialCliente,"
                + "dtNascCliente,"
                + "receberNewsLetter)"
                + "VALUES(?,?,?,?,?,?,?,?,?)";

        try {
            Connection con = Conexao.get().conn();

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, cliente.getNomeCompletoCliente());
            ps.setString(2, cliente.getEmailCliente()); 
            ps.setString(3, cliente.getSenhaCliente());
            ps.setString(4, cliente.getCPFCliente());
            ps.setString(5, cliente.getCelularCliente());
            ps.setString(6, cliente.getTelComercialCliente());
            ps.setString(7, cliente.getTelResidencialCliente());
            ps.setString(8, cliente.getDtNascCliente());
            ps.setString(9, cliente.getRecebeNewsLetter());

            if(ps.executeUpdate() > 0){
                return Response.status(200).entity(cliente).build();
            } else {
                return Response.status(406).entity(cliente).build();
            }

        } catch(Exception ex){
            return Response.status(500).entity(null).build();
        }
    }
    
    @POST
    @Path("/atualizar")
    @Consumes(MediaType.APPLICATION_JSON) 
    public Response alterarUsuario(Cliente cliente){
        
        if(cliente.getIdCliente() > 0) {
            
            String sql = "UPDATE Cliente set " 
                    + "nomeCompletoCliente = ?, "
                    + "emailCliente = ?, "
                    + "senhaCliente = ?,"
                    + "CPFCliente = ? ,"
                    + "celularCliente = ? ,"
                    + "telComercialCliente = ? ,"
                    + "telResidencialCliente = ? ,"
                    + "dtNascCliente = ? ,"
                    + "receberNewsLetter = ?"
                    + "WHERE idCliente = ?"; 
            
            try {
                Connection con = Conexao.get().conn();
                
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setString(1, cliente.getNomeCompletoCliente());
                ps.setString(2, cliente.getEmailCliente()); 
                ps.setString(3, cliente.getSenhaCliente());
                ps.setString(4, cliente.getCPFCliente());
                ps.setString(5, cliente.getCelularCliente());
                ps.setString(6, cliente.getTelComercialCliente());
                ps.setString(7, cliente.getTelResidencialCliente());
                ps.setString(8, cliente.getDtNascCliente());
                ps.setString(9, cliente.getRecebeNewsLetter());
                ps.setInt(10, cliente.getIdCliente());

                if(ps.executeUpdate() > 0){
                    return Response.status(200).entity(cliente).build();
                } else {
                    return Response.status(406).entity(cliente).build();
                }
                
            } catch(Exception ex){
                return Response.status(500).entity(null).build();
            }
            
        } else {
            return Response.status(406).entity("").build();
        }
        
    }
    
    @DELETE
    @Path("/deletar")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteCliente(Cliente cliente){
        
        if (cliente.getIdCliente() > 0) {
            String sql = "DELETE FROM Cliente WHERE idCliente = ?";
            
            try {
                Connection con = Conexao.get().conn();    
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setInt(1, cliente.getIdCliente());
                
                if(ps.executeUpdate() > 0){
                    return Response.status(200).entity(cliente).build();
                } else {
                    return Response.status(406).entity("").build();
                }
                
            } catch(Exception ex){
                return Response.status(500).entity(null).build();
            }
        } else {
            return Response.status(406).entity("").build();
        }
    }
}