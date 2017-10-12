package controller;

import modelos.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import modelos.Produto;



@Path("/produtos")
public class Produtos {
    
    // RETORNA TODOS OS PRODUTOS DA CATEGORIA
    @GET
    @Path("/categoria/{param}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProdutos(@PathParam("param") String cat) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
        
        List<Produto> retorno = new ArrayList<Produto>();
        
        try{
           Connection con = Conexao.get().conn();
           int i = 0;
       

           PreparedStatement preparedStatement = null;


           String query = "SELECT"
                            + "idProduto, nomeProduto, descProduto, precProduto, descontoPromocao, idCategoria, ativoProduto, idUsuario, qtdMinEstoque, imagem "
                            + "FROM Produto"
                            + "WHERE idCategoria = ?";
           
           preparedStatement = con.prepareStatement(query);
           preparedStatement.setInt(1,Integer.parseInt(cat));

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
    
    
    // QUANTO SE TEM O ID - PRODUTO DETALHADO
    @GET
    @Path("/{param}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getProduto(@PathParam("param") String id){
        String retorno = "";
        try{
             Connection con = Conexao.get().conn();
    
      
        PreparedStatement preparedStatement = null;
       
        
        String query = "SELECT "
                            + "nomeProduto, descProduto, precProduto, descontoPromocao, idCategoria, ativoProduto, idUsuario, qtdMinEstoque, imagem "
                            + "FROM Produtos"
                            + "WHERE idProduto = "+id;
        
        preparedStatement = con.prepareStatement(query);
	preparedStatement.setInt(1, 1001);
        
        ResultSet rs = preparedStatement.executeQuery();
          
        while (rs.next()) {

                String userid = rs.getString("nomeProduto");
                String username = rs.getString("descProduto");

               retorno += "Produto : " + userid;
               retorno += "Descrição : " + username + "\n";

        }       
        
        }catch(Exception ex){
           
       }
        return(retorno);
    }
}
