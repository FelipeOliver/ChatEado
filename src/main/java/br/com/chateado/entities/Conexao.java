package br.com.chateado.entities;

import org.springframework.data.annotation.Id;

public class Conexao {

	@Id
	private Long id;
	private String descricao;
	private Long idConversa;

	public Conexao(){}
	
	public Conexao(Long id, String descricao, Long idConversa) {
		this.id = id;
		this.descricao = descricao;
		this.idConversa = idConversa;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public Long getIdConversa() {
		return idConversa;
	}
	public void setIdConversa(Long idConversa) {
		this.idConversa = idConversa;
	}

	@Override
	public String toString() {
		return "Conexao [id=" + id + ", descricao=" + descricao + ", idConversa=" + idConversa + "]";
	}
}
