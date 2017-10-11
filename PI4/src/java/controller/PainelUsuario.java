package controller;

import com.google.gson.Gson;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import modelos.Cliente;
import modelos.Endereco;
import modelos.Estoque;
import modelos.ItemPedido;
import modelos.Pedido;
import modelos.Produto;
import modelos.StatusPedido;
import modelos.TipoPagamento;

@Path("/painel")
public class PainelUsuario {

    @GET
    @Path("/{pedidos}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPedidos(@PathParam("pedidos") String id){
        
        if(id.equals("")){
            return Response.status(401).entity("").build();
        }
        
        Cliente cliente = new Cliente();
        Endereco endereco = new Endereco();
        Produto produto = new Produto();
        Estoque estoque = new Estoque();
        ItemPedido item = new ItemPedido();
        StatusPedido status = new StatusPedido();
        TipoPagamento pagamento = new TipoPagamento();
        Pedido pedido = new Pedido();
        
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
                ",apl.TipoAplicacao" +
                "FROM" +
                "ItemPedido AS ite" +
                "LEFT JOIN Pedido AS ped ON ped.idPedido = ite.idPedido" +
                "LEFT JOIN Cliente AS cli ON cli.idCliente = ped.idCliente" +
                "LEFT JOIN Endereco AS ende ON ende.idEndereco = ped.idEndereco" +
                "LEFT JOIN StatusPedido AS sta ON sta.idStatus = ped.idStatus" +
                "LEFT JOIN TipoPagamento AS tip ON tip.idTipoPagto = ped.idTipoPagto" +
                "LEFT JOIN Aplicacao AS apl ON apl.idAplicacao = ped.idAplicacao"
                + "WHERE "
                + "cli.idCliente = ?"
                + "ORDER BY ped.dataPedido DESC";
        
        try {
            
            Connection con = Conexao.get().conn();    
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, cliente.getNomeCompletoCliente());
            
            ResultSet rs = ps.executeQuery();
            
            List<ItemPedido> pedidos = new ArrayList<ItemPedido>();
            
            while(rs.next()){
                // Lista de pedidos
                pedidos.get(0).setIdPedido(rs.getInt(""));
                
                Gson gson = new Gson();
                String jsonPedido = gson.toJson(pedidos);
                pedido = gson.fromJson(jsonPedido, Pedido.class);
                
            }
            
            return Response.status(200).entity(pedido).build();
            
        } catch(Exception ex){
            return Response.status(500).entity(null).build();
        }
    }
    
    @GET
    @Path("/{detalhes}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDetalhes(@PathParam("detalhes") String id){
        
        if(!id.equals("")){
            
            Cliente cliente = new Cliente();
            Endereco endereco = new Endereco();
            Produto produto = new Produto();
            Estoque estoque = new Estoque();
            ItemPedido item = new ItemPedido();
            StatusPedido status = new StatusPedido();
            TipoPagamento pagamento = new TipoPagamento();
            Pedido pedido = new Pedido();
            
                
            
            
        }
        
        return Response.status(401).entity("").build();
    }
    
}
