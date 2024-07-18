package model;

public class Loja {

	private int id_loja;
	private String descricao;
	private String fantasia;
	private String cnpj;
	private String status;
	
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
}
