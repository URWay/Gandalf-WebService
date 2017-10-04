package modelos;

/**
 * @author jefferson.ysantos
 */
public class Pedido {
   private int idPedido;
   private int idCliente;
   private int idStatus;
   private String dataPedido;
   private int idTipoPagto;
   private int idEndereco;
   private int idAplicacao;

    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }

    public int getIdStatus() {
        return idStatus;
    }

    public void setIdStatus(int idStatus) {
        this.idStatus = idStatus;
    }

    public String getDataPedido() {
        return dataPedido;
    }

    public void setDataPedido(String dataPedido) {
        this.dataPedido = dataPedido;
    }

    public int getIdTipoPagto() {
        return idTipoPagto;
    }

    public void setIdTipoPagto(int idTipoPagto) {
        this.idTipoPagto = idTipoPagto;
    }

    public int getIdEndereco() {
        return idEndereco;
    }

    public void setIdEndereco(int idEndereco) {
        this.idEndereco = idEndereco;
    }

    public int getIdAplicacao() {
        return idAplicacao;
    }

    public void setIdAplicacao(int idAplicacao) {
        this.idAplicacao = idAplicacao;
    }
   
}
