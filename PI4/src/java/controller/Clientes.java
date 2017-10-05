package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import modelos.Cliente;

@Path("/cliente")
public class Clientes {
    
    @GET
    @Path("/{idCliente}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCliente(@QueryParam("idCliente") String idCliente) throws Exception{
        String id = idCliente;
        Cliente cliente = null;
        
        try {
            
            Connection con = Conexao.get().conn();
            PreparedStatement preparedStatement = con.prepareStatement("SELECT * FROM cliente WHERE idCliente = ?");
            preparedStatement.setInt(1, Integer.parseInt(id));
            ResultSet rs = preparedStatement.executeQuery();
            
            if(rs != null){
                if(rs.next()){
                    cliente = new Cliente();
                    cliente.setIdCliente(rs.getInt("idCliente"));
                    cliente.setNomeCompletoCliente(rs.getString("nomeCompletoCliente"));
                }
            }   
            
        } catch(Exception ex){
            return Response.status(500).entity(null).build();
        }
        
        if(cliente == null) {
            return Response.status(404).entity(cliente).build();
        } else {
            return Response.status(200).entity(cliente).build();
        }
    }
}
