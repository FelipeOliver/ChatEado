package br.com.chateado.entities;

public class Conexao {

	private Long id;
	private Conversa conversa;

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Conversa getConversa() {
		return conversa;
	}
	public void setConversa(Conversa conversa) {
		this.conversa = conversa;
	}

	@Override
	public String toString() {
		return "Conexao [id=" + id + ", conversa=" + conversa + "]";
	}
}
