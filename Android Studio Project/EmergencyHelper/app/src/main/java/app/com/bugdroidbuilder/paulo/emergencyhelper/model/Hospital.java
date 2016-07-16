package app.com.bugdroidbuilder.paulo.emergencyhelper.model;

import java.util.Map;

/**
 * Created by pedro on 17/06/16.
 */
public class Hospital {

    private String storageId;
    private String tipo;

    private double latitude;
    private double longitude;

    private String nome;
    private String telefone;
    private String endereco;
    private String descricao;

    private Map<String,String> fotos;
    private int nota;

    public Hospital() {
    }

    public Hospital(String _nome, String _descricao, String _endereco){
        this.nome = _nome;
        this.descricao = _descricao;
        this.endereco = _endereco;
    }

    public String getStorageId() {
        return storageId;
    }

    public String getTipo() {
        return tipo;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getNome() {
        return nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getEndereco() {
        return endereco;
    }

    public String getDescricao() {
        return descricao;
    }

    public Map<String, String> getFotos() {
        return fotos;
    }

    public int getNota() {
        return nota;
    }
}
