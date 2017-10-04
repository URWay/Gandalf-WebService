/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

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
        Conexao con = new Conexao();
        PreparedStatement preparedStatement = null;
        String retorno = "";
        
        String query = "SELECT * FROM Produtos";
        
        
        preparedStatement = con.prepareStatement(query);
	preparedStatement.setInt(1, 1001);
        
        ResultSet rs = preparedStatement.executeQuery();
          
        while (rs.next()) {

                String userid = rs.getString("nomeProduto");
                String username = rs.getString("descProduto");

               retorno += "Produto : " + userid;
               retorno += "Descrição : " + username + "\n";

        }
        
        return retorno;
    }
    
}
