package controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import modelos.Cliente;
import modelos.Conexao;
import modelos.Endereco;
import modelos.Produto;
import org.codehaus.jettison.json.JSONObject;

@Path("/endereco")
public class Enderecos {
    public static final String[] VALUES = new String[] { "AC", "AL", "AM", "AP", "BA", "CE", "DF", "ES", "GO", "MA", "MT", "MS", "MG", "PA", "PB", "PR", "PE", "PI", "RJ", "RN", "RO", "RS", "RR", "SC", "SE", "SP", "TO"};
    
    @POST 
    @Consumes(MediaType.APPLICATION_JSON)
    public Response setEndereco(String content) throws Exception{
        
        String sql = "";
               
        // Validação de CEP
        JSONObject object = new JSONObject(content);
        
        if(!isValid(object)){
            return Response.status(404).build();
        }
        sql = "INSERT INTO Endereco ("
                   + "idCliente, "
                   + "nomeEndereco, "
                   + "logradouroEndereco, "
                   + "numeroEndereco, "
                   + "CEPEndereco, "
                   + "complementoEndereco, "
                   + "cidadeEndereco, "
                   + "paisEndereo, "
                   + "UFEndereco) "
                   + "VALUES(?,?,?,?,?,?,?,?,?)";
        try {
            Connection con = Conexao.get().conn();

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, object.getInt("idCliente"));
            ps.setString(2, object.getString("nomeEndereco")); 
            ps.setString(3, object.getString("logradouroEndereco"));
            ps.setInt(4, object.getInt("numeroEndereco"));
            ps.setString(5, object.getString("CEPEndereco"));
            ps.setString(6, object.getString("complementoEndereco"));
            ps.setString(7, object.getString("cidadeEndereco"));
            ps.setString(8, object.getString("paisEndereo"));
            ps.setString(9, object.getString("UFEndereco"));

            if(ps.executeUpdate() > 0){
               return Response.ok().build();
            } else {
                return Response.status(404).build();
            }

        } catch(Exception ex){
            return Response.status(500).entity(null).build();
        }              
    }
    
    private String sendGet(String url) throws Exception {

        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", "Mozilla/5.0");

        int responseCode = con.getResponseCode();
        
        System.out.println("\nSending 'GET' request to URL : " + url);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        return response.toString();
    }
    
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response putEndereco(String content) throws Exception{
             
        String sql = "";
               
        // Validação de CEP
        JSONObject object = new JSONObject(content);
        
        if(!isValid(object)){
            return Response.status(404).build();
        }
        sql = "UPDATE Endereco SET "
                   + "nomeEndereco = ?, "
                   + "logradouroEndereco = ?, "
                   + "numeroEndereco = ?, "
                   + "CEPEndereco = ?, "
                   + "complementoEndereco = ?, "
                   + "cidadeEndereco = ?, "
                   + "paisEndereo = ?, "
                   + "UFEndereco = ? "
                   + "WHERE idEndereco = ? ";
        try {
            Connection con = Conexao.get().conn();

            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, object.getString("nomeEndereco")); 
            ps.setString(2, object.getString("logradouroEndereco"));
            ps.setInt(3, object.getInt("numeroEndereco"));
            ps.setString(4, object.getString("CEPEndereco"));
            ps.setString(5, object.getString("complementoEndereco"));
            ps.setString(6, object.getString("cidadeEndereco"));
            ps.setString(7, object.getString("paisEndereo"));
            ps.setString(8, object.getString("UFEndereco"));
            ps.setInt(9, object.getInt("idEndereco"));


            if(ps.executeUpdate() > 0){
               return Response.ok().build();
            } else {
                return Response.status(404).build();
            }

        } catch(Exception ex){
            return Response.status(500).entity(null).build();
        }              
    }
    
    @DELETE
    @Path("/deletar/{param}")
    public Response deleteCliente(@PathParam("param") String idEndereco){
        
        if (!idEndereco.equals("")) {
            String sql = "DELETE FROM Endereco WHERE idEndereco = ?";
            
            try {
                Connection con = Conexao.get().conn();    
                PreparedStatement ps = con.prepareStatement(sql);
                ps.setInt(1, Integer.parseInt(idEndereco));
                
                if(ps.executeUpdate() > 0){
                    return Response.ok().build();
                } else {
                    return Response.status(404).build();
                }
                
            } catch(Exception ex){
                return Response.status(500).entity(null).build();
            }
        } else {
            return Response.status(406).build();
        }
    }
    
    
    public boolean isValid(JSONObject object)throws Exception{
         Enderecos isvalida = new Enderecos();
         boolean erro = false;     
        
        //Valida CLIENTE
        if(!erro && !isCliente(object.getInt("idCliente"))){
            erro = true;
        }
     
        //CEP
        String cep = object.getString("CEPEndereco");
        String url = "http://cep.republicavirtual.com.br/web_cep.php?cep="+cep+"&formato=jsonp";
        String retorno = isvalida.sendGet(url);
        
        JSONObject cepWS = new JSONObject(retorno);
        
        // Validação com o retorno da validação de CEP
        if(!erro && cepWS.getString("resultado").equals("1")){
            
            String uf = cepWS.getString("uf");
            String cidade = cepWS.getString("cidade");
            String logradouro = cepWS.getString("logradouro");
           
            // Validação de UF
            if(!erro && object.getString("UFEndereco").trim().isEmpty()){
                erro = true;
            }
            if(!erro && !uf.equals(object.getString("UFEndereco"))){
               erro = true;
            }         
            if(!erro && !Arrays.asList(VALUES).contains(object.getString("UFEndereco"))){
                erro = true;
            }
  
            // Validação da Cidade
            if(!erro && object.getString("cidadeEndereco").trim().isEmpty()){
                erro = true;
            }
            if(!erro && !cidade.equals(object.getString("cidadeEndereco"))){
                erro = true;
            }
            
            // Validação do logradouro
            if(!erro && object.getString("logradouroEndereco").trim().isEmpty()){
                erro = true;
            }
            if(!erro && logradouro.equals(object.getString("logradouroEndereco"))){
                erro = true;
            }      
            //Validação do país
            if(!erro && object.getString("paisEndereco").trim().isEmpty()){
                erro = true;
            }
            if(!erro && object.getString("paisEndereco").trim().isEmpty()){
               erro = true;
            }
        }else{
            erro = true;
        }
        return erro;
    }
    
    public boolean isCliente(int id){
         List<Cliente> retorno = new ArrayList<Cliente>();
         try{
           Connection con = Conexao.get().conn();
           int i = 0;
       
           PreparedStatement preparedStatement = null;

           String query = "SELECT TOP(1)"
                            + "idCliente, "
                        + "FROM Cliente "
                        + " WHERE idCliente = ? ";    
           
           // SUBSTITU 
           preparedStatement = con.prepareStatement(query);
           preparedStatement.setInt(1,id);

           ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                retorno.add(new Cliente());
                retorno.get(0).setIdCliente(rs.getInt("idCliente"));             
            }  

        }catch(Exception ex){
            return false;
        }
        if(retorno.size() < 1){
           return false;
        }
        return true;
    }
     
}