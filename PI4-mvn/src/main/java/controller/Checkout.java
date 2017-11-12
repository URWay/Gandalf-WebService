/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
import org.codehaus.jettison.json.JSONObject;



@Path("/checkout")
public class Checkout {
       
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response inserirUsuario(String content) throws Exception{
        JSONObject object = new JSONObject(content);
        
        if (object.isNull(content)){
            return Response.status(400).build();
        }
        
        if(!isValid(object)){
            return Response.status(400).build();
        }
        
        String sql = "INSERT INTO pedido ("
                + "idCliente, "
                + "idStatus, "
                + "dataPedido, "
                + "idTipoPagto, "
                + "idEndereco, "
                + "idAplicativo) "
                + "VALUES(?,?,?,?,?,?)";
        
        try {
            Connection con = Conexao.get().conn();

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, object.getInt("idCliente"));
            ps.setInt(2, object.getInt("idStatus")); 
            ps.setString(3, object.getString("dataPedido"));
            ps.setInt(4, object.getInt("idTipoPagto"));
            ps.setInt(5, object.getInt("idEndereco"));
            ps.setInt(6, object.getInt("idAplicativo"));

            int lastId = ps.executeUpdate();
            if(lastId > 0){                     
                return Response.ok().build();
            } else {
                return Response.status(404).build();
            }

        } catch(Exception ex){
            return Response.status(500).entity(null).build();
        }       
    }

    
    
    //Not needed
    public boolean isValid(JSONObject content)throws Exception{
        boolean erro = true;       
        //Valida cartão - Não precisa
       return erro;      
    }
    
    
    
        
}
