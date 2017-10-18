package controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import modelos.Endereco;
import org.codehaus.jettison.json.JSONObject;

@Path("/endereco")
public class Enderecos {
    public static final String[] VALUES = new String[] { "AC", "AL", "AM", "AP", "BA", "CE", "DF", "ES", "GO", "MA", "MT", "MS", "MG", "PA", "PB", "PR", "PE", "PI", "RJ", "RN", "RO", "RS", "RR", "SC", "SE", "SP", "TO"};
    
    @POST 
    @Consumes(MediaType.APPLICATION_JSON)
    public Response IsValidaEndereco(String content) throws Exception{
        
        boolean erro = false;
        
        // Validação de CEP
        JSONObject object = new JSONObject(content);
        Enderecos isvalida = new Enderecos();
        
        String cep = object.getString("CEPEndereco");
        
        String url = "http://cep.republicavirtual.com.br/web_cep.php?cep="+cep+"&formato=jsonp";
        String retorno = isvalida.sendGet(url);
        
        JSONObject cepWS = new JSONObject(retorno);
        
        // Validação com o retorno da validação de CEP
        if(cepWS.getString("resultado").equals("1")){
            
            String uf = cepWS.getString("uf");
            String cidade = cepWS.getString("cidade");
            String bairro = cepWS.getString("bairro");
            String tipo_logradouro = cepWS.getString("tipo_logradouro");
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
            
            if(erro)
                return Response.ok().build();
            else
                return Response.status(404).build();
        } else {
            return Response.status(404).build();
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
    
}