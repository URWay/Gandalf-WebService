/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import modelos.Conexao;
import modelos.Pedido;
import modelos.Produto;

/**
 *
 * @author augus
 */
public class Pedidos {
    @GET
    @Path("/single/{id}")
    public Response onePedido(@PathParam("id") String id){
       
        if(id.isEmpty()){
            return Response.status(400).build();
        }
        
          try{
           Connection con = Conexao.get().conn();
           int i = 0;
       
           PreparedStatement preparedStatement = null;

           String query = "SELECT "
                            + "ped.idPedido, ped.idCliente, ped.idStatus, ped.dataPedido, ped.idTipoPagto, ped.idEndereco, ped.idAplicacao, iped.precoVendaItem, iped.qtdProduto, prod.nomeProduto, prod.descProduto, prod.imagem "
                            + "FROM Pedido ped INNER JOIN ItemPedido iped on iped.idPedido = ped.idPedido INNER JOIN Produtos prod ON prod.idProduto = iped.idProduto"
                            + " WHERE idPedido = ?";
           
           
           // SUBSTITU 
           preparedStatement = con.prepareStatement(query);
           preparedStatement.setInt(1,Integer.parseInt(id));


           ResultSet rs = preparedStatement.executeQuery();
          
            while (rs.next()) {
                retorno.add(new Produto());
                retorno.get(i).setIdProduto(rs.getInt("idProduto"));
                retorno.get(i).setNomeProduto(rs.getString("nomeProduto"));
                retorno.get(i).setDescProduto(rs.getString("descProduto"));
                retorno.get(i).setPrecProduto(rs.getDouble("precProduto"));
                retorno.get(i).setDescontoPromocao(rs.getDouble("descontoPromocao"));
                retorno.get(i).setIdCategoria(rs.getInt("idCategoria"));
                retorno.get(i).setAtivoProduto(rs.getString("ativoProduto"));
                retorno.get(i).setIdUsuario(rs.getInt("idUsuario"));
                retorno.get(i).setQtdMinEstoque(rs.getInt("qtdMinEstoque"));
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
    
}
