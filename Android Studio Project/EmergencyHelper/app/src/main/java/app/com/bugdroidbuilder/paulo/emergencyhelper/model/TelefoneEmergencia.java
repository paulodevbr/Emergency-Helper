package app.com.bugdroidbuilder.paulo.emergencyhelper.model;

/**
 * Created by paulo on 28/06/16.
 */
public class TelefoneEmergencia {

    private String descricao, numero;

    public TelefoneEmergencia(String descricao, String numero) {
        this.descricao = descricao;
        this.numero = numero;
    }

    public String getDescricao() {
        return descricao;
    }


    public String getNumero() {
        return numero;
    }

}
