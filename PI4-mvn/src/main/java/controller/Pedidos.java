/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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

/**
 *
 * @author augus
 */
public class Pedidos {
    @GET
    @Path("/pedido/single/{id}")
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
                            + "FROM Pedido ped INNER JOIN ItemPedido iped on iped.idPedido = ped.idPedido INNER JOIN Produtos prod ON prod.idProduto = iped.idProduto"
                            + " WHERE idPedido = ?";
                     
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
    @Path("/pedido/all/{id}")
    public Response allPedido(@PathParam("id") String id){
       Pedido retorno = new Pedido();
        if(id.isEmpty()){
            return Response.status(400).build();
        }
        
          try{
           Connection con = Conexao.get().conn();
           int i = 0;
       
           PreparedStatement preparedStatement = null;

           String query = "SELECT "
                            + "ped.idPedido, ped.idCliente, ped.idStatus, ped.dataPedido, ped.idTipoPagto, ped.idEndereco, ped.idAplicacao"
                            + "FROM Pedido ped "
                            + " WHERE idPedido = ?";
           
           
           // SUBSTITU 
           preparedStatement = con.prepareStatement(query);
           preparedStatement.setInt(1,Integer.parseInt(id));


           ResultSet rs = preparedStatement.executeQuery();
          
            while (rs.next()) {
                retorno.setIdPedido(rs.getInt("idPedido"));
                retorno.setIdCliente(rs.getInt("idCliente"));
                retorno.setIdStatus(rs.getInt("idStatus"));
                retorno.setDataPedido(rs.getString("dataPedido"));
                retorno.setIdTipoPagto(rs.getInt("idTipoPagto"));
                retorno.setIdEndereco(rs.getInt("idEndereco"));
                retorno.setIdAplicacao(rs.getInt("idAplicacao"));
                    
            }       
             return Response.status(200).entity(retorno).build();
        }catch(Exception ex){
            return Response.status(404).entity(retorno).build();
        }
       
    }
    
    //INSERIR
    @POST
    @Path("/pedido")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response pedidoIn(String content) throws Exception{
                
        String sql = "";
        // Validação de CEP
        JSONObject object = new JSONObject(content);

        sql = "INSERT INTO Endereco ("
                   + "idCliente, "
                   + "idStatus, "
                   + "dataPedido, "
                   + "idTipoPagto, "
                   + "idEndereco, "
                   + "idAplicacao "
                   + "VALUES(?,?,?,?,?,?)";
        try {
            Connection con = Conexao.get().conn();
  
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, object.getInt("idCliente"));
            ps.setInt(2, object.getInt("idStatus")); 
            ps.setString(3, object.getString("dataPedido"));
            ps.setInt(4, object.getInt("idTipoPagto"));
            ps.setInt(5, object.getInt("idEndereco"));
            ps.setInt(6, object.getInt("idAplicacao"));         
            
            if(ps.executeUpdate() < 1){
              return Response.status(404).build();
            }
            
            
           
            
            
            return Response.status(200).build();
        } catch(Exception ex){
            return Response.status(500).entity(null).build();
        }      

    }
    
    
    
    
    //MUDAR STATUS
    @PUT
    @Path("/status/{idPedido}/{idStatus}")
    public Response statusPedido(@PathParam("idPedido") String idPedido, @PathParam("idStatus") int idStatus){
        
        //CHECA SE O PEDIDO EXISTE E SE ELE JÁ NÃO ESTÁ COM ESSE STATUS (SE FOR CANCELADO N PODE MUDAR)
        try{
           Connection con = Conexao.get().conn();
           PreparedStatement preparedStatement = null;
           String query = "SELECT "
                            + "ped.idPedido, ped.idStatus"
                            + "FROM Pedido ped "
                            + " WHERE idPedido = ?";
            preparedStatement = con.prepareStatement(query);
            preparedStatement.setInt(1,Integer.parseInt(idPedido));

            ResultSet rs = preparedStatement.executeQuery();
             
            if(rs.getInt("idStatus") == idStatus){
                return Response.status(400).build();
            }
            if(rs.getInt("idStatus") > idStatus){
                return Response.status(400).build();
            }
            
            
            query = "UPDATE Pedido SET "
                    + "idStatus = ?"
                    + " WHERE idPedido = ?";
            
            preparedStatement = con.prepareStatement(query);
            preparedStatement.setInt(1,idStatus);
            preparedStatement.setInt(2,Integer.parseInt(idPedido));

            if(preparedStatement.executeUpdate() > 0){
               return Response.ok().build();
            } else {
                return Response.status(404).build();
            }

            
            
        }catch(Exception ex){
            return Response.status(400).build();
        }
    }
}
