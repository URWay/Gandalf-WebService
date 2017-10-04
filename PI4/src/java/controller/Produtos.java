package controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

@Path("/produtos")
public class Produtos {
    
    @GET
    public String getProdutos() throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {
       
        return "";

    }
    
    @GET
    public String getProduto(@QueryParam("id") String id)  throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException{
       try{
             Connection con = Conexao.get().conn();
       }catch(Exception ex){
           
       }
      
        PreparedStatement preparedStatement = null;
        String retorno = "";
        
        String query = "SELECT * FROM Produtos WHERE idProduto = "+id;
        
        preparedStatement = con.prepareStatement(query);
	preparedStatement.setInt(1, 1001);
        
        ResultSet rs = preparedStatement.executeQuery();
          
        while (rs.next()) {

                String userid = rs.getString("nomeProduto");
                String username = rs.getString("descProduto");

               retorno += "Produto : " + userid;
               retorno += "Descrição : " + username + "\n";

        }       
        return(retorno);
    }
}
