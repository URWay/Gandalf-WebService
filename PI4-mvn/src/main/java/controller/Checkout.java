package controller;
import modelos.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import modelos.Pedido;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;

@Path("/checkout")
public class Checkout {
       
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response insertPedido(String content) throws Exception{
        JSONObject object = new JSONObject(content);
        
        if (!object.isNull(content)){
            return Response.status(400).build();
        }
        
        if(!isValid(object)){
            return Response.status(400).build();
        }
        
        String sql = "INSERT INTO pedido ("
                + "idCliente, "
                + "idStatus, "
                + "dataPedido, "
                + "idTipoPagto, "
                + "idEndereco, "
                + "idAplicacao) "
                + "VALUES(?,?,?,?,?,?)";
        
        try {
            Connection con = Conexao.get().conn();

            PreparedStatement ps = con.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setInt(1, object.getInt("idCliente"));
            ps.setInt(2, object.getInt("idStatus")); 
            ps.setString(3, object.getString("dataPedido"));
            ps.setInt(4, object.getInt("idTipoPagto"));
            ps.setInt(5, object.getInt("idEndereco"));
            ps.setInt(6, object.getInt("idAplicacao"));
            
            if(ps.executeUpdate() > 0){
                
                ResultSet generatedKeysResultSet = ps.getGeneratedKeys();
                
                generatedKeysResultSet.next(); 
                long id = generatedKeysResultSet.getLong(1);

                // Retorno do pedido
                Pedido pedido = new Pedido();
                pedido.setIdPedido((int)id);
                pedido.setIdCliente(object.getInt("idCliente"));
                pedido.setIdStatus(object.getInt("idStatus"));
                pedido.setDataPedido(object.getString("dataPedido"));
                pedido.setIdTipoPagto(object.getInt("idTipoPagto"));
                pedido.setIdEndereco(object.getInt("idEndereco"));
                pedido.setIdAplicacao(object.getInt("idAplicacao"));
                
                return Response.ok(pedido).build();
            } else {
                return Response.status(404).build();
            }

        } catch(Exception ex){
            System.out.println(ex.getMessage());
            return Response.status(500).entity(null).build();
        }       
    }

    @POST
    @Path("/inserirItem")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response inserirItemPedido(String content) throws Exception{
        JSONArray object = new JSONArray(content);
        
        int qtdInseridos = 0;
        
        String sql = "INSERT INTO itemPedido ("
                + "idProduto, "
                + "idPedido, "
                + "qtdProduto, "
                + "precoVendaItem) "
                + "VALUES(?,?,?,?)";
        
        try {
            
            for(int i = 0; i < object.length(); i++){
                Connection con = Conexao.get().conn();

                PreparedStatement ps = con.prepareStatement(sql);
                ps.setInt(1, object.getJSONObject(i).getInt("idProduto"));
                ps.setInt(2, object.getJSONObject(i).getInt("idPedido")); 
                ps.setInt(3, object.getJSONObject(i).getInt("qtdProduto"));
                ps.setDouble(4, object.getJSONObject(i).getDouble("precoVendaItem"));
                
                if(ps.executeUpdate() > 0){
                    qtdInseridos++;
                }
            }
            
            if(qtdInseridos == object.length() || qtdInseridos > 0){
                return Response.ok().build();
            } else {
                return Response.status(404).build();
            }

        } catch(Exception ex){
            System.out.println(ex.getMessage());
            return Response.status(500).entity(null).build();
        }       
    }
    
    //Not needed
    public boolean isValid(JSONObject content)throws Exception{
        boolean erro = true;       
        //Valida cartão - Não precisa
       return erro;      
    }
    
    
    
        
}
