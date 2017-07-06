package br.com.chateado.entities;

import java.util.List;

public class Conversa {

	private Long id;
//	private List<Usuario> usuarios;
	private List<Message> mensagens;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
//	public List<Usuario> getUsuarios() {
//		return usuarios;
//	}
//	public void setUsuarios(List<Usuario> usuarios) {
//		this.usuarios = usuarios;
//	}
	public List<Message> getMensagens() {
		return mensagens;
	}
	public void setMensagens(List<Message> mensagens) {
		this.mensagens = mensagens;
	}
}
