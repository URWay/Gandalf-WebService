package modelos;

public class ItemPedido {
   private int idProduto;
   private int idPedido;
   private double qtdProduto;
   private double precoVendaItem;

    public int getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(int idProduto) {
        this.idProduto = idProduto;
    }

    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public double getQtdProduto() {
        return qtdProduto;
    }

    public void setQtdProduto(double qtdProduto) {
        this.qtdProduto = qtdProduto;
    }

    public double getPrecoVendaItem() {
        return precoVendaItem;
    }

    public void setPrecoVendaItem(double precoVendaItem) {
        this.precoVendaItem = precoVendaItem;
    }
}
