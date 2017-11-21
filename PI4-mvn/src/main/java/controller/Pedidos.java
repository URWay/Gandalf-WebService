package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import modelos.Conexao;
import modelos.Pedido;
import modelos.PedidosItens;
import modelos.Produto;
import org.codehaus.jettison.json.JSONObject;

@Path("/pedido")
public class Pedidos {
    @GET
    @Path("/single/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response onePedido(@PathParam("id") String id){
       
        if(id.isEmpty()){
            return Response.status(400).build();
        }
           List<PedidosItens> retorno = new ArrayList<PedidosItens>();   
          try{
              
          
           Connection con = Conexao.get().conn();
           int i = 0;
       
           PreparedStatement preparedStatement = null;

           String query = "SELECT "
                            + "ped.idPedido, ped.idCliente, ped.idStatus, ped.dataPedido, ped.idTipoPagto, ped.idEndereco, ped.idAplicacao, iped.precoVendaItem, iped.qtdProduto, prod.nomeProduto, prod.descProduto, prod.imagem "
                            + "FROM Pedido ped INNER JOIN ItemPedido iped on iped.idPedido = ped.idPedido INNER JOIN Produto prod ON prod.idProduto = iped.idProduto"
                            + " WHERE ped.idPedido = ?";
                     
           // SUBSTITUI 
           preparedStatement = con.prepareStatement(query);
           preparedStatement.setInt(1,Integer.parseInt(id));


           ResultSet rs = preparedStatement.executeQuery();
          
            while (rs.next()) {
                retorno.add(new PedidosItens());
                retorno.get(i).setIdPedido(rs.getInt("idPedido"));
                retorno.get(i).setIdCliente(rs.getInt("idCliente"));
                retorno.get(i).setIdStatus(rs.getInt("idStatus"));
                retorno.get(i).setDataPedido(rs.getString("dataPedido"));
                retorno.get(i).setIdTipoPagto(rs.getInt("idTipoPagto"));
                retorno.get(i).setIdEndereco(rs.getInt("idEndereco"));
                retorno.get(i).setIdAplicacao(rs.getInt("idAplicacao"));
                retorno.get(i).setPrecoVendaItem(rs.getDouble("precoVendaItem"));
                retorno.get(i).setQtdProduto(rs.getInt("qtdProduto"));
                retorno.get(i).setNomeProduto(rs.getString("nomeProduto"));
                retorno.get(i).setDescProduto(rs.getString("descProduto"));
                retorno.get(i).setImagem(rs.getString("imagem"));
                             
                i+=1;
            }       
        
        }catch(Exception ex){
            
        }
        
        if (retorno.size() < 1)
            return Response.status(404).entity(retorno).build();
        else
            return Response.status(200).entity(retorno).build();
        
    }
    
    @GET
    @Path("/all/{id}")
     @Produces(MediaType.APPLICATION_JSON)
    public Response allPedido(@PathParam("id") String id){
        List<Pedido> retorno = new ArrayList<>();
        
        if(id.isEmpty()){
            return Response.status(400).build();
        }
        
        try{
            Connection con = Conexao.get().conn();
            
            PreparedStatement preparedStatement = null;

            String query = "SELECT "
                            + "ped.idPedido, ped.idCliente, ped.idStatus, ped.dataPedido, ped.idTipoPagto, ped.idEndereco, ped.idAplicacao "
                            + "FROM Pedido ped "
                            + " WHERE ped.idCliente = ?";
           
            // SUBSTITU 
            preparedStatement = con.prepareStatement(query);
            preparedStatement.setInt(1,Integer.parseInt(id));
            ResultSet rs = preparedStatement.executeQuery();
          
            int i = 0;
            
            while (rs.next()) {
                retorno.add(new Pedido());
                retorno.get(i).setIdPedido(rs.getInt("idPedido"));
                retorno.get(i).setIdCliente(rs.getInt("idCliente"));
                retorno.get(i).setIdStatus(rs.getInt("idStatus"));
                retorno.get(i).setDataPedido(rs.getString("dataPedido"));
                retorno.get(i).setIdTipoPagto(rs.getInt("idTipoPagto"));
                retorno.get(i).setIdEndereco(rs.getInt("idEndereco"));
                retorno.get(i).setIdAplicacao(rs.getInt("idAplicacao"));
                i++;
            }       
            
            return Response.status(200).entity(retorno).build();
        }catch(Exception ex){
            System.out.println(ex.getMessage());
            return Response.status(500).entity(null).build();
        }
       
    }
    
   
    
}
