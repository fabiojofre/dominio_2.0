package model;

import java.util.Date;

public class LojaToken {

	private int id_loja;
	private String descricao;
	private String fantasia;
	private String cnpj;
	private String status;
	private String integration_key;
	private Date data_inicio;
	private String token;
	
	public int getId_loja() {
		return id_loja;
	}
	public void setId_loja(int id_loja) {
		this.id_loja = id_loja;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public String getFantasia() {
		return fantasia;
	}
	public void setFantasia(String fantasia) {
		this.fantasia = fantasia;
	}
	public String getCnpj() {
		return cnpj;
	}
	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getIntegration_key() {
		return integration_key;
	}
	public void setIntegration_key(String integration_key) {
		this.integration_key = integration_key;
	}
	public Date getData_inicio() {
		return data_inicio;
	}
	public void setData_inicio(Date data_inicio) {
		this.data_inicio = data_inicio;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	
}
