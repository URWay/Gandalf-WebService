package controller;


import modelos.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
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
    @Path("/{param}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProdutos(@PathParam("param") String cat, @QueryParam("order") String order, @QueryParam("ap") int ap, @QueryParam("pesq") String pesq, @QueryParam("desc") int desc ) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
        
        List<Produto> retorno = new ArrayList<>();
                
       if(pesq == null || pesq.isEmpty()){
            pesq = "%%";
        }else{
            pesq = "%"+pesq+"%";
        }
        
        try{
           Connection con = Conexao.get().conn();
           int i = 0;
       
           PreparedStatement preparedStatement = null;

           String query = "SELECT TOP(15)"
                            + "idProduto, "
                            + "nomeProduto, "
                            + "descProduto, "
                            + "precProduto, "
                            + "descontoPromocao, "
                            + "idCategoria, "
                            + "ativoProduto, "
                            + "idUsuario, "
                            + "qtdMinEstoque, "
                            + "imagem "
                        + "FROM Produto "
                        + " WHERE idProduto > ? AND nomeProduto like ?";
           
           //trazer todos 
           if(Integer.parseInt(cat) != 0){
               query = query + "AND idCategoria = ?";
           }
           
           // ORDER BY
           if(order != null){
              query = query + " ORDER BY " + order + " ";
               if(desc == 1){
                   query = query + "DESC";
               }
           }else{
               query = query + " ORDER BY idProduto DESC ";
          }
                     
           
           // SUBSTITU 
           preparedStatement = con.prepareStatement(query);
           
           preparedStatement.setInt(1,ap);
           preparedStatement.setString(2, pesq);
           
           if(Integer.parseInt(cat) != 0){
                preparedStatement.setInt(3,Integer.parseInt(cat));
           }
           
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
                retorno.get(i).setImagem(Base64.getEncoder().encodeToString(rs.getBytes("imagem")));
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
    @Path("/desc/{param}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getProduto(@PathParam("param") String id){
        Produto prod = new Produto();
        try{
            
            Connection con = Conexao.get().conn();
            int i = 0;
      
            PreparedStatement preparedStatement = null;
       
        
        String query = "SELECT "
                            + "idProduto, nomeProduto, descProduto, precProduto, descontoPromocao, idCategoria, ativoProduto, idUsuario, qtdMinEstoque, imagem "
                            + "FROM Produto"
                            + " WHERE idProduto = ?";
        
        preparedStatement = con.prepareStatement(query);
	preparedStatement.setInt(1, Integer.parseInt(id));
       
        ResultSet rs = preparedStatement.executeQuery();
        
            
         
          while (rs.next()) {
               
                prod.setIdProduto(rs.getInt("idProduto"));
                prod.setNomeProduto(rs.getString("nomeProduto"));
                prod.setDescProduto(rs.getString("descProduto"));
                prod.setPrecProduto(rs.getDouble("precProduto"));
                prod.setDescontoPromocao(rs.getDouble("descontoPromocao"));
                prod.setIdCategoria(rs.getInt("idCategoria"));
                prod.setAtivoProduto(rs.getString("ativoProduto"));
                prod.setIdUsuario(rs.getInt("idUsuario"));
                prod.setQtdMinEstoque(rs.getInt("qtdMinEstoque"));
                prod.setImagem(Base64.getEncoder().encodeToString(rs.getBytes("imagem")));
                i+=1;
            }       
        
        }catch(Exception ex){
           
        }
        
        return Response.status(200).entity(prod).build();
    }
}
