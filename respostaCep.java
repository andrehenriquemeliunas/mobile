package com.example.ac2atv5;

import org.json.JSONException;
import org.json.JSONObject;
public class respostaCep {

    private String logradouro;
    private String complemento;

    private String bairro;

    private String cidade;
    private String localidade; // Cidade
    private String uf; // Estado

    public respostaCep(String logradouro, String complemento, String bairro, String localidade, String uf) {
        this.logradouro = logradouro;
        this.complemento = complemento;
        this.bairro = bairro;

        this.localidade = localidade;
        this.uf = uf;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public String getComplemento() {
        return complemento;
    }

    public String getBairro() {
        return bairro;
    }
    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getLocalidade() {
        return localidade;
    }

    public String getUF() {
        return uf;
    }

    public void parseJSON(JSONObject json) {
        try {
            logradouro = json.getString("logradouro");
            complemento = json.getString("complemento");
            bairro = json.getString("bairro");
            cidade = json.getString("localidade");
            uf = json.getString("uf");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
