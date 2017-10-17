package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import modelos.Cliente;
import modelos.Conexao;
import modelos.Endereco;
import modelos.ItemPedido;
import modelos.Pedido;
import modelos.Produto;
import modelos.StatusPedido;

@Path("/painel")
public class PainelUsuario {

    @GET
    @Path("/minhaconta/{param}")
    //falta terminar
    @Produces(MediaType.APPLICATION_JSON)
    public Response getConta(@PathParam("param") String id){
        
        // Função ser o painel principal do Status do Cliente
        // Exibindo apenas as informações de compras recentes (máximo 30 dias) 
        // e os dados de entrega
        
        Cliente cliente = new Cliente();
        Endereco endereco = new Endereco();
        
        String sql = "SELECT cli.idCliente, cli.nomeCompletoCliente,"
                + "end.idEndereco, end.nomeEndereco, end.numeroEndereco, end.CEPEndereco, end.cidadeEndereco,"
                + "end.complementoEndereco, end.paisEndereco, end.UFEndereco, end.logradouroEndereco "
                + "FROM Cliente AS cli "
                + "LEFT JOIN Endereco AS end ON end.idCliente = ped.idEndereco ";
        
        // Será exibido apenas se houver algum pedido recente (30 dias)
        Produto produto = new Produto();
        ItemPedido item = new ItemPedido();
        StatusPedido status = new StatusPedido();
        Pedido pedido = new Pedido();
        
        String sqlPedido = "SELECT item.qtdProduto, item.precoVendaItem,"
                + "ped.dataPedido,"
                + "sta.descStatus,"
                + "pag.descTipoPagto,"
                + "pro.nomeProduto, pro.descProduto, pro.precoProduto, pro.descontoPromocao, pro.imagem"
                + "FROM itemPedido AS item "
                + "LEFT JOIN Pedido AS ped ON ped.idPedido = item.idPedido "
                + "LEFT JOIN StatusPedido AS sta ON sta.idStatus = ped.idStatus "
                + "LEFT JOIN TipoPagamento AS pag ON pag.idTipoPagto = ped.idTipoPagto"
                + "LEFT JOIN Produto AS pro ON pro.idProduto = item.idProduto "
                + "WHERE ped.dataPedido = DATEDIFF(day, getdate(), ped.dataPedido-30) AND ped.idCliente = ?";
        
        List<String> minhaConta = new ArrayList<>();
        
        try {
            
            Connection con = Conexao.get().conn();    
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, Integer.parseInt(id));
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                minhaConta.add(rs.getString("idCliente"));
                
            }
            
        } catch(Exception ex){
            return Response.status(500).entity(null).build();
        }
        
        if (minhaConta.size() < 1) {
            return Response.status(404).entity(minhaConta).build();
        } else {
            return Response.status(200).entity(minhaConta).build();
        }    
    }
    
    @GET
    @Path("/pedidos/{param}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPedidos(@PathParam("param") String id){
        
        Pedido pedido = new Pedido();
        
        String sql = "SELECT item.qtdProduto, item.precoVendaItem,"
                + "ped.dataPedido,"
                + "sta.descStatus,"
                + "pag.descTipoPagto,"
                + "pro.nomeProduto, pro.descProduto, pro.precoProduto, pro.descontoPromocao, pro.imagem"
                + "FROM itemPedido AS item "
                + "LEFT JOIN Pedido AS ped ON ped.idPedido = item.idPedido "
                + "LEFT JOIN StatusPedido AS sta ON sta.idStatus = ped.idStatus "
                + "LEFT JOIN TipoPagamento AS pag ON pag.idTipoPagto = ped.idTipoPagto"
                + "LEFT JOIN Produto AS pro ON pro.idProduto = item.idProduto "
                + "WHERE ped.dataPedido = DATEDIFF(day, getdate(), ped.dataPedido-30) AND ped.idCliente = ?";
        
        try {
            
            Connection con = Conexao.get().conn();    
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, Integer.parseInt(id));
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()){
                
                
            }
            
        } catch(Exception ex){
            return Response.status(500).entity(null).build();
        }
        
        return Response.status(200).build();
    }
    
    @GET
    @Path("/detalhes/{param}")
    // Falta terminar
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDetalhes(@PathParam("param") String id){
        
        if(id.equals("")){
            return Response.status(404).build();
        }
        
        String sql = "SELECT" +
                "ite.idProduto" +
                ",ite.qtdProduto" +
                ",ite.precoVendaItem" +
                ",ped.idPedido" +
                ",ped.dataPedido" +
                ",ped.idCliente" +
                ",ped.idEndereco" +
                ",sta.idStatus" +
                ",sta.descStatus" +
                ",tip.idTipoPagto" +
                ",tip.descTipoPagto" +
                ",apl.idAplicacao" +
                ",apl.DescAplicacao" +
                ",apl.TipoAplicacao " +
                "FROM ItemPedido AS ite" +
                "LEFT JOIN Pedido AS ped ON ped.idPedido = ite.idPedido " +
                "LEFT JOIN Cliente AS cli ON cli.idCliente = ped.idCliente " +
                "LEFT JOIN Endereco AS ende ON ende.idEndereco = ped.idEndereco " +
                "LEFT JOIN StatusPedido AS sta ON sta.idStatus = ped.idStatus " +
                "LEFT JOIN TipoPagamento AS tip ON tip.idTipoPagto = ped.idTipoPagto " +
                "LEFT JOIN Aplicacao AS apl ON apl.idAplicacao = ped.idAplicacao " +
                "LEFT JOIN Produto AS pro ON pro.idProduto = ite.idProduto "
                + "WHERE "
                + "cli.idCliente = ? "
                + "ORDER BY ped.dataPedido DESC";
        
        return Response.status(200).build();
    }
    
    @GET
    @Path("/enderecos/{param}")
    public Response getEnderecos(@PathParam("param") String id){
        
        List<Endereco> enderecos = new ArrayList();
        
        String sql = "SELECT idEndereco, nomeEndereco, numeroEndereco, CEPEndereco, cidadeEndereco,"
                + "complementoEndereco, paisEndereco, UFEndereco, logradouroEndereco "
                + "FROM Endereco WHERE idCliente = ?";
        
        try {
            
            Connection con = Conexao.get().conn();    
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, Integer.parseInt(id));
            ResultSet rs = ps.executeQuery();
            
            int i = 0;
            
            while(rs.next()){
                enderecos.add(new Endereco());
                enderecos.get(i).setIdEndereco(rs.getInt("idEndereco"));
                enderecos.get(i).setIdEndereco(rs.getInt("nomeEndereco"));
                enderecos.get(i).setIdEndereco(rs.getInt("numeroEndereco"));
                enderecos.get(i).setIdEndereco(rs.getInt("CEPEndereco"));
                enderecos.get(i).setIdEndereco(rs.getInt("cidadeEndereco"));
                enderecos.get(i).setIdEndereco(rs.getInt("complementoEndereco"));
                enderecos.get(i).setIdEndereco(rs.getInt("paisEndereco"));
                enderecos.get(i).setIdEndereco(rs.getInt("UFEndereco"));
                enderecos.get(i).setIdEndereco(rs.getInt("logradouroEndereco"));
                i++;
            }
        
            if (enderecos.size() < 1) {
                return Response.status(404).entity(enderecos).build();
            } else {
                return Response.status(200).entity(enderecos).build();
            }
            
        } catch(Exception ex){
            return Response.status(500).entity(null).build();
        }
    }
    
}
