package modelos;

public class Login {
    private String emailCliente;
    private String senhaCliente;

    public Login(String emailCliente, String senhaCliente) {
        this.emailCliente = emailCliente;
        this.senhaCliente = senhaCliente;
    }
    
    public String getEmailCliente() {
        return emailCliente;
    }

    public void setEmailCliente(String emailCliente) {
        this.emailCliente = emailCliente;
    }

    public String getSenhaCliente() {
        return senhaCliente;
    }

    public void setSenhaCliente(String senhaCliente) {
        this.senhaCliente = senhaCliente;
    }
}
