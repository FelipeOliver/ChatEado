package br.com.chateado.entities;

import java.util.List;

public class Usuario {
	
	private String codigo;
	private String senha;
	private boolean status;
	private Long idConversa;
	private List<Conexao> conexoes;
	
	public Usuario(String codigo, String senha, String status) {
		this.setCodigo(codigo);
		this.setSenha(senha);
		this.setStatusString(status);
	}
	public Usuario(){
		
	}
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public boolean getStatus() {
		return status;
	}
	public void setStatusString(String status) {
		this.status = status == "S";
	}
	public void setStatus(boolean status){
		this.status = status;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public Long getIdConversa() {
		return idConversa;
	}
	public void setIdConversa(Long idConversa) {
		this.idConversa = idConversa;
	}
	public List<Conexao> getConexoes() {
		return conexoes;
	}
	public void setConexoes(List<Conexao> conexoes) {
		this.conexoes = conexoes;
	}
	
	@Override
	public String toString() {
		return "Usuario [codigo=" + codigo + ", status=" + status + "]";
	}
}
