package modelos;

/**
 * @author jefferson.ysantos
 */
public class Estoque {
    private int idProduto;
    private int qtdProdutoDisponivel;

    public int getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(int idProduto) {
        this.idProduto = idProduto;
    }

    public int getQtdProdutoDisponivel() {
        return qtdProdutoDisponivel;
    }

    public void setQtdProdutoDisponivel(int qtdProdutoDisponivel) {
        this.qtdProdutoDisponivel = qtdProdutoDisponivel;
    }
}
