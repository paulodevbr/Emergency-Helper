package app.com.bugdroidbuilder.paulo.emergencyhelper.model;

/**
 * Created by paulo on 28/06/16.
 */
public class TelefoneUtil {

    private String descricao, numero;

    public TelefoneUtil(String descricao, String numero) {
        this.descricao = descricao;
        this.numero = numero;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }
}
